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
    public ItemDto.SimpleItemPageResponse showItems(SearchDto.Items request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 9);

        Page<Item> items = itemRepository.searchPageSimple(request, pageRequest);

        List<ItemDto.SimpleItemResponse> itemsDto = items.stream()
                .map(ItemDto.SimpleItemResponse::new)
                .collect(Collectors.toList());

        ItemDto.SimpleItemPageResponse itemPageDto = new ItemDto.SimpleItemPageResponse(itemsDto, items.getTotalPages(), request.getNowPage());

        return itemPageDto;
    }


    // 상품 상세 조회
    @Transactional
    public ItemDto.FullItemResponse showItem(Long itemId) {

        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);

        ItemDto.FullItemResponse itemDto = new ItemDto.FullItemResponse(item);

        return itemDto;
    }


    // 상품 저장
    @Transactional
    public void saveItem(ItemDto.SaveRequest request) throws IOException {

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(IllegalArgumentException::new);

        Item item = new Item(category, request);

        // 메인 이미지 처리
        S3DataSave(item, request.getMainImgList(), "main");

        // 서브 이미지 처리
        S3DataSave(item, request.getServeImgList(), "serve");

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

        List<S3Dto.ImgDataResponse> serveImgDataResponseList = serveImgList.stream()
                .map(S3Dto.ImgDataResponse::new)
                .collect(Collectors.toList());

        ItemDto.UpdateResponse itemDto = new ItemDto.UpdateResponse(item, imgDataDataListResponse, serveImgDataResponseList);

        return itemDto;
    }


    // 상품 수정 (상품 기본 정보)
    @Transactional
    public void updateItemData(ItemDto.UpdateDataRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(IllegalArgumentException::new);

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        item.updateData(category, request);
    }


    // 상품 수정 (메인 이미지)
    @Transactional
    public void updateItemMainImg(ItemDto.UpdateMainImgRequest request) {

        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        // 삭제된 이미지 관련(ItemFile, S3File, AWS S3) 삭제
        DeleteImg(item, request.getS3FileId());


    }


    // 상품 수정 (서브 이미지)
    @Transactional
    public void updateItemServeImg(ItemDto.UpdateServeImgRequest request) {

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


    // S3에 저장된 파일 정보를 가지고 DB에 저장하기
    public void S3DataSave(Item item, List<MultipartFile> multipartFileList, String mainOrServe) throws IOException {

        // 서브 이미지를 한 번에 처리(save)를 위한 리스트 초기화
        List<S3File> s3FileList = new ArrayList<>();
        List<ItemFile> itemFileList = new ArrayList<>();

        // 여러 개인 서브 이미지 처리
        for (MultipartFile multipartFile : multipartFileList){

            ItemDto.S3Data imgData = S3Upload(multipartFile);

            S3File s3FileServe = new S3File(imgData.getUuidFileName(), imgData.getS3Url());
            s3FileList.add(s3FileServe);

            ItemFile itemFileServe = new ItemFile(item, s3FileServe, mainOrServe);

            itemFileList.add(itemFileServe);
        }

        // 한 번에 저장
        s3FileRepository.saveAll(s3FileList);
        itemFileRepository.saveAll(itemFileList);
    }


    // 제거된 이미지 삭제하기
    public void DeleteImg(Item item, List<Long> s3FileIdList) {

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

        itemFileRepository.saveAll(itemFileList);
        s3FileRepository.saveAll(s3FileList);
    }

}
