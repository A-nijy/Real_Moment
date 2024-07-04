package rm.backend.adminController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rm.backend.adminService.AdminImageService;
import rm.backend.dto.innerDto.ImageDto;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AdminImageController {

    private final AdminImageService adminImageService;

    // 이미지 조회 (show의 값이 true, false 모두 조회)
    @GetMapping("/admin/image")
    public ImageDto.ImageListResponse showImages(ImageDto.ImageRequest request){

        ImageDto.ImageListResponse imageList = adminImageService.showImages(request);

        return imageList;
    }

//    // 이미지 저장 (리스트 (처음 추가용)) [클라이언트에서 구현 안함 (한개씩 추가하는 방식 사용함)]
//    @PostMapping("/admin/save/image")
//    public String saveImage(ImageDto.saveImageRequest request) throws IOException {
//
//        adminImageService.saveImage(request);
//
//        return "이미지 등록 완료";
//    }
    

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

    // 이미지 수정 (링크)
    @PostMapping("/admin/change/image/link")
    public String changeImageLink(@RequestBody ImageDto.ChangeImageLink request){

        adminImageService.changeImageLink(request);

        return "이미지 링크 변경 완료";
    }

    // 이미지 보이기 여부 수정
    @PostMapping("/admin/change/image/show")
    public String changeImageShow(@RequestBody ImageDto.ChangeImageShow request){

        adminImageService.changeImageShow(request);

        return "이미지 보이기 여부 변경 완료";
    }

    // 이미지 삭제
    @DeleteMapping("/admin/delete/image")
    public String deleteImage(ImageDto.deleteImage request){

        adminImageService.deleteImage(request);

        return "이미지 삭제 완료";
    }
}
