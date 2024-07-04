package rm.backend.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rm.backend.dto.innerDto.ImageDto;
import rm.backend.service.ImageService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;


    // 쇼핑몰 이미지 가져오기
    @GetMapping("/image")
    public ImageDto.ImageListResponse showImages(ImageDto.ImageRequest request){

        ImageDto.ImageListResponse imageList = imageService.showImages(request);

        return imageList;
    }
}
