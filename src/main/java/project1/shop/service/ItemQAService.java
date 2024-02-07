package project1.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.ItemQA;
import project1.shop.domain.entity.Member;
import project1.shop.domain.repository.ItemQARepository;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.dto.innerDto.ItemQADto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemQAService {

    private final ItemQARepository itemQARepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;


    // 상품 Q&A 목록 보기
    @Transactional
    public List<ItemQADto.ItemQAResponse> showQAList(Long itemId) {

        List<ItemQA> QAList = itemQARepository.findByItem_ItemId(itemId);

        List<ItemQADto.ItemQAResponse> QAListDto = QAList.stream()
                .map(ItemQADto.ItemQAResponse::new)
                .collect(Collectors.toList());

        return QAListDto;
    }

    // 내가 작성한 상품 Q&A 목록 보기
    @Transactional
    public List<ItemQADto.MyItemQAResponse> showMyQAList(Long id) {

        List<ItemQA> myQAList = itemQARepository.findByMember_MemberId(id);

        List<ItemQADto.MyItemQAResponse> myQAListDto = myQAList.stream()
                .map(ItemQADto.MyItemQAResponse::new)
                .collect(Collectors.toList());

        return myQAListDto;
    }

    // Q&A 작성하기
    @Transactional
    public void saveItemQA(Long id, ItemQADto.ItemQARequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        ItemQA itemQA = new ItemQA(member, item, request);

        itemQARepository.save(itemQA);
    }

    // Q&A 수정하기 (답변이 달리지 않았을 경우에만)
    @Transactional
    public void updateItemQA(Long id, ItemQADto.UpdateItemQARequest request) {

        ItemQA itemQA = itemQARepository.findById(request.getItemQAId()).orElseThrow(IllegalArgumentException::new);

        itemQA.update(request);
    }

    // Q&A 삭제하기 (답변이 달리지 않았을 경우에만)
    @Transactional
    public void deleteItemQA(Long id, Long itemQAId) {

        ItemQA itemQA = itemQARepository.findById(itemQAId).orElseThrow(IllegalArgumentException::new);

        itemQARepository.delete(itemQA);
    }
}
