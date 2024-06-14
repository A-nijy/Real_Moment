package project1.shop.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project1.shop.adminService.AdminImageService;
import project1.shop.dto.innerDto.ImageDto;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminImageController {

    private final AdminImageService adminImageService;

    // 이미지 조회
    @GetMapping("/admin/image")
    public ImageDto.ImageListResponse showImages(ImageDto.ImageRequest request){

        ImageDto.ImageListResponse imageList = adminImageService.showImages(request);

        return imageList;
    }

    // 이미지 저장 (처음)
    @PostMapping("/admin/save/image")
    public String saveImage(ImageDto.saveImageRequest request) throws IOException {

        adminImageService.saveImage(request);

        return "이미지 등록 완료";
    }

    // 이미지 수정 (추가)
    @PostMapping("/admin/add/image")
    public String addImage(ImageDto.AddImageRequest request) throws IOException {

        adminImageService.addImage(request);

        return "이미지 추가 완료";
    }

    // 이미지 수정 (순서)
    @PostMapping("/admin/change/image")
    public String changeImage(@RequestBody ImageDto.ChangeNumberImageList request){

        adminImageService.changeImage(request);

        return "이미지 순서 변경 완료";
    }

    // 이미지 삭제
    @DeleteMapping("/admin/delete/image")
    public String deleteImage(ImageDto.deleteImage request){

        adminImageService.deleteImage(request);

        return "이미지 삭제 완료";
    }
}