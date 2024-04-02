package project1.shop.adminService;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project1.shop.domain.entity.Category;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.ItemFile;
import project1.shop.domain.entity.S3File;
import project1.shop.domain.repository.CategoryRepository;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.domain.repository.ItemFileRepository;
import project1.shop.domain.repository.S3FileRepository;
import project1.shop.dto.innerDto.ItemDto;
import project1.shop.dto.innerDto.S3Dto;
import project1.shop.dto.innerDto.SearchDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final AmazonS3Client amazonS3Client;
    private final S3FileRepository s3FileRepository;
    private final ItemFileRepository itemFileRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    // 상품 목록 조회
    @Transactional
    public ItemDto.SimpleItemPageAdminResponse showItems(SearchDto.AdminItems request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 9);

        Page<Item> items = itemRepository.searchAdminPageSimple(request, pageRequest);

        List<ItemDto.SimpleItemAdminResponse> itemsDto = items.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        ItemDto.SimpleItemPageAdminResponse itemPageDto = new ItemDto.SimpleItemPageAdminResponse(itemsDto, items.getTotalPages(), request.getNowPage());

        return itemPageDto;
    }


    // DTO 변환
    public ItemDto.SimpleItemAdminResponse mapToDto(Item item){

        ItemFile itemFile = itemFileRepository.searchFirstMainImg(item).orElse(null);

        ItemDto.SimpleItemAdminResponse simpleItemDto = null;

        if (itemFile == null){
            simpleItemDto = new ItemDto.SimpleItemAdminResponse(item, null);
        } else {
            simpleItemDto = new ItemDto.SimpleItemAdminResponse(item, itemFile.getS3File().getFileUrl());
        }

        return simpleItemDto;
    }


    // 상품 상세 조회
    @Transactional
    public ItemDto.FullItemAdminResponse showItem(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);

        List<S3File> mainImgList = itemFileRepository.searchMainImgList(item);

        List<S3File> serveImgList = itemFileRepository.searchServeImgList(item);

        List<S3Dto.ImgDataResponse> imgDataDataListResponse = mainImgList.stream()
                .map(S3Dto.ImgDataResponse::new)
                .collect(Collectors.toList());

        List<S3Dto.ImgDataResponse> subImgDataResponseList = serveImgList.stream()
                .map(S3Dto.ImgDataResponse::new)
                .collect(Collectors.toList());

        ItemDto.FullItemAdminResponse itemDto = new ItemDto.FullItemAdminResponse(item, imgDataDataListResponse, subImgDataResponseList);

        return itemDto;
    }


    // 상품 저장
    @Transactional
    public void saveItem(ItemDto.SaveRequest request) throws IOException {

        if (request.getMainImgList() == null || request.getMainImgList().isEmpty()){
            throw new IllegalArgumentException("메인 이미지는 하나 이상 추가해야합니다.");
        }

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(IllegalArgumentException::new);

        Item item = new Item(category, request);

        // 메인 이미지 처리
        if(request.getMainImgList() != null && !request.getMainImgList().isEmpty()){
            S3DataListSave(item, request.getMainImgList(), "main");
        }

        // 서브 이미지 처리
        if(request.getSubImgList() != null && !request.getSubImgList().isEmpty()){
            S3DataListSave(item, request.getSubImgList(), "sub");
        }

        itemRepository.save(item);
    }


    // 상품 수정버튼 클릭시 해당 데이터 가져와서 반환
    @Transactional
    public ItemDto.UpdateResponse getItem(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);

        List<S3File> mainImgList = itemFileRepository.searchMainImgList(item);

        List<S3File> serveImgList = itemFileRepository.searchServeImgList(item);

        List<S3Dto.ImgDataResponse> imgDataDataListResponse = mainImgList.stream()
                .map(S3Dto.ImgDataResponse::new)
                .collect(Collectors.toList());

        List<S3Dto.ImgDataResponse> subImgDataResponseList = serveImgList.stream()
                .map(S3Dto.ImgDataResponse::new)
                .collect(Collectors.toList());

        ItemDto.UpdateResponse itemDto = new ItemDto.UpdateResponse(item, imgDataDataListResponse, subImgDataResponseList);

        return itemDto;
    }


    // 상품 수정 (상품 기본 정보)
    @Transactional
    public void updateItemData(ItemDto.UpdateDataRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(IllegalArgumentException::new);

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        item.updateData(category, request);
    }


    // 상품 이미지 교체 (메인 이미지)
    @Transactional
    public void replaceItemMainImg(ItemDto.ReplaceImgRequest request) throws IOException {

        if (request.getImgFile() == null || request.getS3FileId() == null){
            throw new IllegalArgumentException("변경될 이미지 id와 새로 추가될 이미지 파일이 있어야합니다.");
        }

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        ItemFile itemFile = itemFileRepository.findByItemAndS3File_S3FileId(item, request.getS3FileId()).orElseThrow(IllegalArgumentException::new);

        S3DataSave(item, request.getImgFile(), "main", itemFile.getNumber());

        DeleteImg(item, itemFile.getItemFileId());
    }


    // 상품 이미지 추가 (메인 이미지)
    @Transactional
    public void addItemMainImg(ItemDto.AddImgRequest request) throws IOException {

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        List<ItemFile> itemFileList = itemFileRepository.findByNumberGreaterThanEqual(request.getNumber());

        // 순서 한 칸씩 뒤로 이동하기
        for (ItemFile itemFile : itemFileList){

            itemFile.addNumber();
        }

        S3DataSave(item, request.getImgFile(), "main", request.getNumber());
    }


    // 상품 이미지 순서교체 (메인 이미지)
    @Transactional
    public void changeItemMainImg(ItemDto.ChangeImgRequest request) {

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        ItemFile itemFile1 = itemFileRepository.findByNumber(request.getNumber1()).orElseThrow(IllegalArgumentException::new);
        ItemFile itemFile2 = itemFileRepository.findByNumber(request.getNumber2()).orElseThrow(IllegalArgumentException::new);

        itemFile1.changeNumber(request.getNumber2());
        itemFile2.changeNumber(request.getNumber1());
    }


    // 상품 이미지 삭제 (메인 이미지)
    @Transactional
    public void deleteItemMainImg(ItemDto.DeleteImgRequest request) {

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        ItemFile itemFile = itemFileRepository.findByItemAndS3File_S3FileId(item, request.getS3FileId()).orElseThrow(IllegalArgumentException::new);

        if (itemFile.getNumber() == 0){
            throw new IllegalArgumentException("대표 이미지는 삭제가 불가능합니다. 위치 변경 or 이미지 교체만 가능합니다.");
        }

        // 삭제된 이미지 관련(ItemFile, S3File, AWS S3) 삭제
        DeleteImg(item, request.getS3FileId());
    }


    // 상품 이미지 교체 (서브 이미지)
    @Transactional
    public void replaceItemSubImg(ItemDto.ReplaceImgRequest request) throws IOException {

        if (request.getImgFile() == null || request.getS3FileId() == null){
            throw new IllegalArgumentException("변경될 이미지 id와 새로 추가될 이미지 파일이 있어야합니다.");
        }

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        ItemFile itemFile = itemFileRepository.findByItemAndS3File_S3FileId(item, request.getS3FileId()).orElseThrow(IllegalArgumentException::new);

        S3DataSave(item, request.getImgFile(), "sub", itemFile.getNumber());

        DeleteImg(item, itemFile.getItemFileId());
    }


    // 상품 이미지 추가 (서브 이미지)
    @Transactional
    public void addItemSubImg(ItemDto.AddImgRequest request) throws IOException {

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        List<ItemFile> itemFileList = itemFileRepository.findByNumberGreaterThanEqual(request.getNumber());

        // 순서 한 칸씩 뒤로 이동하기
        for (ItemFile itemFile : itemFileList){

            itemFile.addNumber();
        }

        S3DataSave(item, request.getImgFile(), "sub", request.getNumber());
    }


    // 상품 이미지 순서교체 (서브 이미지)
    @Transactional
    public void changeItemSubImg(ItemDto.ChangeImgRequest request) {

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        ItemFile itemFile1 = itemFileRepository.findByNumber(request.getNumber1()).orElseThrow(IllegalArgumentException::new);
        ItemFile itemFile2 = itemFileRepository.findByNumber(request.getNumber2()).orElseThrow(IllegalArgumentException::new);

        itemFile1.changeNumber(request.getNumber2());
        itemFile2.changeNumber(request.getNumber1());
    }


    // 상품 이미지 삭제 (서브 이미지)
    @Transactional
    public void deleteItemSubImg(ItemDto.DeleteImgRequest request) {

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        // 삭제된 이미지 관련(ItemFile, S3File, AWS S3) 삭제
        DeleteImg(item, request.getS3FileId());
    }



    // 상품 삭제
    @Transactional
    public void deleteItem(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);

        List<S3File> s3FileList = new ArrayList<>();
        List<ItemFile> itemFileList = itemFileRepository.findByItem(item);

        for (ItemFile itemFile : itemFileList){

            S3File s3File = s3FileRepository.findById(itemFile.getS3File().getS3FileId()).orElseThrow(IllegalArgumentException::new);

            s3FileList.add(s3File);

            // s3에 업로드된 이미지 삭제
            amazonS3Client.deleteObject(bucket, s3File.getFileName());
        }

        // ItemFile 삭제
        itemFileRepository.deleteAll(itemFileList);

        // S3File 삭제
        s3FileRepository.deleteAll(s3FileList);

        // 완전 삭제가 아닌 일부 데이터만 삭제
        item.delete();
    }



    //------------------------------------------------------------------------

    // 파일 추출 & S3 업로드 후 S3에 저장된 파일 정보를 반환
    public ItemDto.S3Data S3Upload(MultipartFile multipartFile) throws IOException {

        // 해당 파일 이름 추출 (ex : acb.jpg)
        String fileName = multipartFile.getOriginalFilename();

        // 해당 파일 확장자 추출 (ex : .jpg)
        String ext = fileName.substring(fileName.indexOf("."));

        // 이미지 파일명 변경하기 (중복을 피하기 위해서)
        String uuidFileName = UUID.randomUUID() + ext;

        // 해당 파일에 정보를 추가하는 메타데이터 작성
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        // S3에 파일 업로드하기 (putObject(버킷, 파일명, 서버에 저장할 파일, 메타데이터))
        amazonS3Client.putObject(bucket, uuidFileName, multipartFile.getInputStream(), metadata);

        // S3에 저장된 파일 주소 가져오기
        String s3Url = amazonS3Client.getUrl(bucket, uuidFileName).toString();

        ItemDto.S3Data data = new ItemDto.S3Data(uuidFileName, s3Url);

        return data;
    }


    // S3에 저장된 파일 정보를 가지고 DB에 저장하기 (리스트)
    public void S3DataListSave(Item item, List<MultipartFile> multipartFileList, String mainOrSub) throws IOException {

        // 서브 이미지를 한 번에 처리(save)를 위한 리스트 초기화
        List<S3File> s3FileList = new ArrayList<>();
        List<ItemFile> itemFileList = new ArrayList<>();

        int number = 0;

        // 여러 개인 서브 이미지 처리
        for (MultipartFile multipartFile : multipartFileList){

            ItemDto.S3Data imgData = S3Upload(multipartFile);

            S3File s3FileServe = new S3File(imgData.getUuidFileName(), imgData.getS3Url());
            s3FileList.add(s3FileServe);

            ItemFile itemFileServe = new ItemFile(item, s3FileServe, mainOrSub, number);

            itemFileList.add(itemFileServe);

            number++;
        }

        // 한 번에 저장
        s3FileRepository.saveAll(s3FileList);
        itemFileRepository.saveAll(itemFileList);
    }


    // S3에 저장된 파일 정보를 가지고 DB에 저장하기
    public void S3DataSave(Item item, MultipartFile multipartFile, String mainOrSub, int number) throws IOException {

        // 파일 추출 및 처리 / S3에 업로드 / 파일 명 & 주소 객체 반환
        ItemDto.S3Data imgData = S3Upload(multipartFile);

        S3File s3File = new S3File(imgData.getUuidFileName(), imgData.getS3Url());

        ItemFile itemFile = new ItemFile(item, s3File, mainOrSub, number);


        // 한 번에 저장
        s3FileRepository.save(s3File);
        itemFileRepository.save(itemFile);
    }


    // 제거된 이미지 삭제하기  (처리)
    public void DeleteImg(Item item, Long s3FileId) {

        // 삭제된 이미지를 참조하고 있는 엔티티
        ItemFile itemFile = itemFileRepository.findByItemAndS3File_S3FileId(item, s3FileId).orElseThrow(IllegalArgumentException::new);

        // 삭제된 이미지 엔티티
        S3File s3File = itemFile.getS3File();

        // S3 이미지 삭제하기
        amazonS3Client.deleteObject(bucket, s3File.getFileName());

        // 한 번에 삭제
        itemFileRepository.delete(itemFile);
        s3FileRepository.delete(s3File);
    }


    // 제거된 이미지 삭제하기  (리스트 처리)
    public void DeleteImgList(Item item, List<Long> s3FileIdList) {

        // 한 번에 삭제하기 위한 리스트 초기화
        List<ItemFile> itemFileList = new ArrayList<>();
        List<S3File> s3FileList = new ArrayList<>();

        for (Long s3FileId : s3FileIdList){

            // 삭제된 이미지를 참조하고 있는 엔티티
            ItemFile itemFile = itemFileRepository.findByItemAndS3File_S3FileId(item, s3FileId).orElseThrow(IllegalArgumentException::new);
            itemFileList.add(itemFile);

            // 삭제된 이미지 엔티티
            S3File s3File = itemFile.getS3File();
            s3FileList.add(s3File);

            // S3 이미지 삭제하기
            amazonS3Client.deleteObject(bucket, s3File.getFileName());
        }

        // 한 번에 삭제

        itemFileRepository.deleteAll(itemFileList);
        s3FileRepository.deleteAll(s3FileList);
    }


}
