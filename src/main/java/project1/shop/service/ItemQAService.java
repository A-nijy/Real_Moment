package project1.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.ItemQA;
import project1.shop.domain.entity.Member;
import project1.shop.domain.entity.QAComment;
import project1.shop.domain.repository.ItemQARepository;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.domain.repository.QACommentRepository;
import project1.shop.dto.innerDto.ItemQADto;
import project1.shop.dto.innerDto.QACommentDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemQAService {

    private final ItemQARepository itemQARepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final QACommentRepository qaCommentRepository;


    // 상품 상세 정보에 응답할 특정 상품의 문의 목록 조회
    @Transactional
    public List<ItemQADto.ItemQAResponse> showItemQAList(SearchDto.ItemQAs request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 5);

        Page<ItemQA> itemQAs = itemQARepository.searchItemQAs(request, pageRequest);

        List<ItemQADto.ItemQAResponse> itemQADto = itemQAs.stream()
                .map(ItemQADto.ItemQAResponse::new)
                .collect(Collectors.toList());

        for(ItemQADto.ItemQAResponse itemQA : itemQADto){
            QAComment qaComment = qaCommentRepository.findById(itemQA.getItemQAId()).orElse(null);

            QACommentDto.Response qaCommentDto = new QACommentDto.Response(qaComment);

            itemQA.setQAComment(qaCommentDto);
        }

        return itemQADto;
    }



    // 내가 작성한 상품 Q&A 목록 보기
    @Transactional
    public List<ItemQADto.MyItemQAResponse> showMyQAList(Long id, SearchDto.Page nowPage) {

        PageRequest pageRequest = PageRequest.of(nowPage.getNowPage() - 1, 10);

        Page<ItemQA> myItemQAs = itemQARepository.searchMyItemQAs(id, pageRequest);

        List<ItemQADto.MyItemQAResponse> myItemQADto = myItemQAs.stream()
                .map(ItemQADto.MyItemQAResponse::new)
                .collect(Collectors.toList());

        for(ItemQADto.MyItemQAResponse myItemQA : myItemQADto){
            QAComment qaComment = qaCommentRepository.findById(myItemQA.getItemQAId()).orElse(null);

            QACommentDto.Response qaCommentDto = new QACommentDto.Response(qaComment);

            myItemQA.setQAComment(qaCommentDto);
        }

        return myItemQADto;
    }

    // Q&A 작성하기
    @Transactional
    public void saveItemQA(Long id, ItemQADto.ItemQARequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        ItemQA itemQA = new ItemQA(member, item, request);

        itemQARepository.save(itemQA);
    }


    // 문의 수정하기 버튼 클릭시 해당 문의 데이터 반환하기
    @Transactional
    public ItemQADto.UpdateItemQAResponse getItemQA(Long id, Long itemQAId) {

        ItemQA itemQA = itemQARepository.findById(itemQAId).orElseThrow(IllegalArgumentException::new);

        if(itemQA.isAnswer() == true){
            throw new IllegalArgumentException("답변이 달려서 해당 Q&A를 수정할 수 없습니다.");
        }

        ItemQADto.UpdateItemQAResponse itemQADto = new ItemQADto.UpdateItemQAResponse(itemQA);

        return itemQADto;
    }



    // Q&A 수정하기 (답변이 달리지 않았을 경우에만)
    @Transactional
    public void updateItemQA(Long id, ItemQADto.UpdateItemQARequest request) {

        ItemQA itemQA = itemQARepository.findById(request.getItemQAId()).orElseThrow(IllegalArgumentException::new);

        if(itemQA.isAnswer() == true){
            throw new IllegalArgumentException("답변이 달려서 해당 Q&A를 수정할 수 없습니다.");
        }

        itemQA.update(request);
    }



    // Q&A 삭제하기 (답변이 달리지 않았을 경우에만)
    @Transactional
    public void deleteItemQA(Long id, Long itemQAId) {

        ItemQA itemQA = itemQARepository.findById(itemQAId).orElseThrow(IllegalArgumentException::new);

        if(itemQA.isAnswer() == true){
            throw new IllegalArgumentException("답변이 달려서 해당 Q&A를 삭제할 수 없습니다.");
        }

        itemQARepository.delete(itemQA);
    }



}
