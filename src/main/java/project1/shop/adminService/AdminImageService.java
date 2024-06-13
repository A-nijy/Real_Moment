package project1.shop.adminService;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.ItemFile;
import project1.shop.domain.entity.PageFile;
import project1.shop.domain.entity.S3File;
import project1.shop.domain.repository.PageFileRepository;
import project1.shop.domain.repository.S3FileRepository;
import project1.shop.dto.innerDto.ImageDto;
import project1.shop.enumeration.ImageLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminImageService {

    private final AmazonS3Client amazonS3Client;
    private final S3FileRepository s3FileRepository;
    private final PageFileRepository pageFileRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public ImageDto.ImageListResponse showImages(ImageDto.ImageRequest request) {

        ImageLocation imageLocation = ImageLocation.fromString(request.getImageLocation());

        List<PageFile> pageFileList = pageFileRepository.findByImageLocationOrderByNumberAsc(imageLocation);

        List<ImageDto.ImageResponse> imageList = pageFileList.stream()
                .map(ImageDto.ImageResponse::new)
                .collect(Collectors.toList());

        ImageDto.ImageListResponse imageListDto = new ImageDto.ImageListResponse(imageList);

        return imageListDto;
    }


    // 이미지 저장
    @Transactional
    public void saveImage(ImageDto.saveImageRequest request) throws IOException {

        if (request.getImgList() == null || request.getImgList().isEmpty()){
            throw new IllegalArgumentException("추가할 이미지가 존재하지 않습니다.");
        }

        S3DataListSave(request.getImgList(), request.getImageLocation());
    }


    // 이미지 추가
    @Transactional
    public void addImage(ImageDto.AddImageRequest request) throws IOException {

        ImageLocation imageLocation = ImageLocation.fromString(request.getImageLocation());

        List<PageFile> pageFileList = pageFileRepository.findByImageLocationOrderByNumberAsc(imageLocation);

        int number = pageFileList.size();

        S3DataSave(request.getImg(), request.getImageLocation(), number);
    }


    // 이미지 순서 변경
    @Transactional
    public void changeImage(ImageDto.ChangeNumberImageList request) {

        int num = 0;

        for (ImageDto.ChangeNumberImage image : request.getChangeNumberImageList()){

            PageFile pageFile = pageFileRepository.findById(image.getPageFileId()).orElseThrow(IllegalArgumentException::new);

            pageFile.changeNumber(num);

            num++;
        }
    }


    // 이미지 삭제
    @Transactional
    public void deleteImage(ImageDto.deleteImage request) {

        PageFile pageFile = pageFileRepository.findById(request.getPageFileId()).orElseThrow(IllegalArgumentException::new);
        S3File s3File = pageFile.getS3File();

        int number = pageFile.getNumber();

        List<PageFile> pageFileList = pageFileRepository.searchNumberMoveImgList(pageFile.getImageLocation(), pageFile.getNumber());

        for (PageFile file : pageFileList){

            file.subNumber();
        }

        // PageFile에 저장된 이미지 데이터 삭제
        pageFileRepository.delete(pageFile);
        // S3File에 저장된 이미지 데이터 삭제
        s3FileRepository.delete(s3File);
        // S3에 저장된 이미지 삭제
        amazonS3Client.deleteObject(bucket, s3File.getFileName());
    }


    //------------------------------------------------------

    // 파일 추출 & S3 업로드 후 S3에 저장된 파일 정보를 반환
    public ImageDto.S3Data S3Upload(MultipartFile multipartFile) throws IOException {

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

        ImageDto.S3Data data = new ImageDto.S3Data(uuidFileName, s3Url);

        return data;
    }

    // S3에 저장된 파일 정보를 가지고 DB에 저장하기 (단일)
    public void S3DataSave(MultipartFile multipartFile, String imageLocation, int number) throws IOException {

        // 파일 추출 및 처리 / S3에 업로드 / 파일 명 & 주소 객체 반환
        ImageDto.S3Data imgData = S3Upload(multipartFile);

        S3File s3File = new S3File(imgData.getUuidFileName(), imgData.getS3Url());

        PageFile pageFile = new PageFile(s3File, imageLocation, number);

        // 한 번에 저장
        s3FileRepository.save(s3File);
        pageFileRepository.save(pageFile);
    }

    // S3에 저장된 파일 정보를 가지고 DB에 저장하기 (리스트)
    public void S3DataListSave(List<MultipartFile> multipartFileList, String imageLocation) throws IOException {

        // 서브 이미지를 한 번에 처리(save)를 위한 리스트 초기화
        List<S3File> s3FileList = new ArrayList<>();
        List<PageFile> pageFileList = new ArrayList<>();

        int number = 0;

        // 여러 개인 서브 이미지 처리
        for (MultipartFile multipartFile : multipartFileList){

            ImageDto.S3Data imgData = S3Upload(multipartFile);

            S3File s3File = new S3File(imgData.getUuidFileName(), imgData.getS3Url());
            s3FileList.add(s3File);

            PageFile pageFile = new PageFile(s3File, imageLocation, number);

            pageFileList.add(pageFile);

            number++;
        }

        // 한 번에 저장
        s3FileRepository.saveAll(s3FileList);
        pageFileRepository.saveAll(pageFileList);
    }



}
