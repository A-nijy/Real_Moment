package rm.backend.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.PageFile;
import rm.backend.domain.repository.PageFileRepository;
import rm.backend.dto.innerDto.ImageDto;
import rm.backend.enumeration.ImageLocation;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final PageFileRepository pageFileRepository;


    // 이미지 응답
    @Transactional
    public ImageDto.ImageListResponse showImages(ImageDto.ImageRequest request) {

        ImageLocation imageLocation = ImageLocation.fromString(request.getImageLocation());

        List<PageFile> pageFileList = pageFileRepository.findByImageLocationAndIsShowOrderByNumberAsc(imageLocation, true);

        List<ImageDto.ImageResponse> imageList = pageFileList.stream()
                .map(ImageDto.ImageResponse::new)
                .collect(Collectors.toList());

        ImageDto.ImageListResponse imageListDto = new ImageDto.ImageListResponse(imageList);

        return imageListDto;
    }
}
