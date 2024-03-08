package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project1.shop.domain.entity.Comment;
import project1.shop.domain.entity.OneOnOne;
import project1.shop.domain.repository.CommentRepository;
import project1.shop.domain.repository.OneOnOneRepository;
import project1.shop.dto.innerDto.OneOnOneDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminOneOnOneService {

    private final OneOnOneRepository oneOnOneRepository;
    private final CommentRepository commentRepository;


    // 1대1 문의 목록 조회
    public OneOnOneDto.PageResponse showOneOnOneList(SearchDto.OneOnOneSearch request) {

        log.info("1");
        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 10);
        log.info("2");
        Page<OneOnOne> oneOnOneList = oneOnOneRepository.searchOneOnOne(request, pageRequest);
        log.info("3");
        List<OneOnOneDto.Response> oneOnOneDtoList = oneOnOneList.stream()
                .map(OneOnOneDto.Response::new)
                .collect(Collectors.toList());
        log.info("4");
        for (OneOnOneDto.Response oneOnOne : oneOnOneDtoList){
            log.info("5");
            Comment comment = commentRepository.findByOneOnOne_OneOnOneId(oneOnOne.getOneOnOneId()).orElse(null);
            log.info("6");
            if (comment != null){
                oneOnOne.setComment(comment);
            }
        }
        log.info("7");
        OneOnOneDto.PageResponse pageOneOnOne = new OneOnOneDto.PageResponse(oneOnOneDtoList, oneOnOneList.getTotalPages(), request.getNowPage());
        log.info("8");
        return pageOneOnOne;
    }
}
