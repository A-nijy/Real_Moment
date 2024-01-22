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


    @Transactional
    public List<ItemQADto.ItemQAResponse> showQAList(Long itemId) {

        List<ItemQA> QAList = itemQARepository.findByItem_ItemId(itemId);

        List<ItemQADto.ItemQAResponse> QAListDto = QAList.stream()
                .map(ItemQADto.ItemQAResponse::new)
                .collect(Collectors.toList());

        return QAListDto;
    }

    @Transactional
    public List<ItemQADto.MyItemQAResponse> showMyQAList(Long id) {

        List<ItemQA> myQAList = itemQARepository.findByMember_MemberId(id);

        List<ItemQADto.MyItemQAResponse> myQAListDto = myQAList.stream()
                .map(ItemQADto.MyItemQAResponse::new)
                .collect(Collectors.toList());

        return myQAListDto;
    }

    @Transactional
    public void saveItemQA(Long id, ItemQADto.ItemQARequest request) {

        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        ItemQA itemQA = new ItemQA(member, item, request);

        itemQARepository.save(itemQA);
    }

    @Transactional
    public void updateItemQA(Long id, ItemQADto.UpdateItemQARequest request) {

        log.info("itemQA객체 생성");
        ItemQA itemQA = itemQARepository.findById(request.getItemQAId()).orElseThrow(IllegalArgumentException::new);

        log.info("itemQA객체 내용 수정");
        itemQA.update(request);
        log.info("itemQA객체 수정 완료");
    }

    @Transactional
    public void deleteItemQA(Long id, Long itemQAId) {

        ItemQA itemQA = itemQARepository.findById(itemQAId).orElseThrow(IllegalArgumentException::new);

        itemQARepository.delete(itemQA);
    }
}
