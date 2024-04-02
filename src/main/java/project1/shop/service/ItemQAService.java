package project1.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.*;
import project1.shop.domain.repository.*;
import project1.shop.dto.innerDto.ItemDto;
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
    private final ItemFileRepository itemFileRepository;


    // 상품 상세 정보에 응답할 특정 상품의 문의 목록 조회
    @Transactional
    public ItemQADto.ItemQAPageResponse showItemQAList(SearchDto.ItemInItemQA request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 5);

        Page<ItemQA> itemQAs = itemQARepository.searchItemInItemQAs(request, pageRequest);

        List<ItemQADto.ItemQAResponse> itemQADto = itemQAs.stream()
                .map(ItemQADto.ItemQAResponse::new)
                .collect(Collectors.toList());

        for(ItemQADto.ItemQAResponse itemQA : itemQADto){
            QAComment qaComment = qaCommentRepository.findById(itemQA.getItemQAId()).orElse(null);

            QACommentDto.Response qaCommentDto = null;

            if(qaComment != null){
                qaCommentDto = new QACommentDto.Response(qaComment);
            }

            itemQA.setQAComment(qaCommentDto);
        }

        ItemQADto.ItemQAPageResponse itemQAPageDto = new ItemQADto.ItemQAPageResponse(itemQADto, itemQAs.getTotalPages(), request.getNowPage());

        return itemQAPageDto;
    }



    // 내가 작성한 상품 Q&A 목록 보기
    @Transactional
    public ItemQADto.MyItemQAPageResponse showMyQAList(Long id, SearchDto.Page request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 10);

        Page<ItemQA> myItemQAs = itemQARepository.searchMyItemQAs(id, pageRequest);

        List<ItemQADto.MyItemQAResponse> myItemQADto = myItemQAs.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        ItemQADto.MyItemQAPageResponse myItemQAPageDto = new ItemQADto.MyItemQAPageResponse(myItemQADto, myItemQAs.getTotalPages(), request.getNowPage());

        return myItemQAPageDto;
    }


    // DTO 변환
    public ItemQADto.MyItemQAResponse mapToDto(ItemQA itemQA){

        ItemFile itemFile = itemFileRepository.searchFirstMainImg(itemQA.getItem()).orElse(null);

        ItemDto.SimpleItemResponse simpleItemDto = null;

        if (itemFile == null){
            simpleItemDto = new ItemDto.SimpleItemResponse(itemQA.getItem(), null);
        } else {
            simpleItemDto = new ItemDto.SimpleItemResponse(itemQA.getItem(), itemFile.getS3File().getFileUrl());
        }

        QAComment qaComment = qaCommentRepository.findByItemQA_ItemQAId(itemQA.getItemQAId()).orElse(null);

        QACommentDto.Response qaCommentDto = null;

        if(qaComment != null){
            qaCommentDto = new QACommentDto.Response(qaComment);
        }

        ItemQADto.MyItemQAResponse myItemQAResponse = new ItemQADto.MyItemQAResponse(itemQA, simpleItemDto, qaCommentDto);

        return myItemQAResponse;
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
            throw new IllegalArgumentException("답변이 달려있는 Q&A는 수정할 수 없습니다.");
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
