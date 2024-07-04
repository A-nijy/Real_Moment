package rm.backend.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.Comment;
import rm.backend.domain.entity.Member;
import rm.backend.domain.entity.OneOnOne;
import rm.backend.domain.repository.CommentRepository;
import rm.backend.domain.repository.MemberRepository;
import rm.backend.domain.repository.OneOnOneRepository;
import rm.backend.dto.innerDto.OneOnOneDto;
import rm.backend.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OneOnOneService {

    private final OneOnOneRepository oneOnOneRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;


    // 1대1 문의 작성
    @Transactional
    public void createOneOnOne(Long id, OneOnOneDto.Request request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        OneOnOne oneOnOne = new OneOnOne(member, request);

        oneOnOneRepository.save(oneOnOne);
    }


    // 1대1 문의 수정 버튼 (데이터 가져오기)
    @Transactional
    public OneOnOneDto.UpdateResponse getOneOnOne(Long id, Long oneOnOneId) {

        OneOnOne oneOnOne = oneOnOneRepository.findById(oneOnOneId).orElseThrow(IllegalArgumentException::new);

        if (oneOnOne.isAnswer()){
            throw new IllegalArgumentException("답변이 달려있는 문의는 수정이 불가능합니다.");
        }

        OneOnOneDto.UpdateResponse oneOnOneDto = new OneOnOneDto.UpdateResponse(oneOnOne);

        return oneOnOneDto;
    }


    // 1대1 문의 수정
    @Transactional
    public void updateOneOnOne(Long id, OneOnOneDto.UpdateRequest request) {

        OneOnOne oneOnOne = oneOnOneRepository.findById(request.getOneOnOneId()).orElseThrow(IllegalArgumentException::new);

        if (oneOnOne.isAnswer() == true){
            throw new IllegalArgumentException("답변이 달려있는 문의는 수정이 불가능합니다.");
        }

        oneOnOne.updateOneOnOne(request);
    }


    // 1대1 문의 삭제
    @Transactional
    public void deleteOneOnOne(Long id, Long oneOnOneId) {

        OneOnOne oneOnOne = oneOnOneRepository.findById(oneOnOneId).orElseThrow(IllegalArgumentException::new);

        if (oneOnOne.isAnswer() == true){
            throw new IllegalArgumentException("답변이 달려있는 문의는 수정이 불가능합니다.");
        }

        oneOnOneRepository.delete(oneOnOne);
    }


    // 내가 작성한 1대1 문의 목록 조회
    @Transactional
    public OneOnOneDto.MyPageResponse showOneOnOneList(Long id, SearchDto.OneOnOnes request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 10);

        Page<OneOnOne> oneOnOneList = oneOnOneRepository.searchMyOneOnOne(request, id, pageRequest);

        List<OneOnOneDto.MyResponse> oneOnOneDtoList = oneOnOneList.stream()
                .map(OneOnOneDto.MyResponse::new)
                .collect(Collectors.toList());

        for (OneOnOneDto.MyResponse oneOnOneDto : oneOnOneDtoList){

            Comment comment = commentRepository.findByOneOnOne_OneOnOneId(oneOnOneDto.getOneOnOneId()).orElse(null);

            if (comment != null){
                oneOnOneDto.setComment(comment);
            }

        }

        OneOnOneDto.MyPageResponse myOneOnOneList = new OneOnOneDto.MyPageResponse(oneOnOneDtoList, oneOnOneList.getTotalPages(), request.getNowPage());

        return myOneOnOneList;
    }
}
