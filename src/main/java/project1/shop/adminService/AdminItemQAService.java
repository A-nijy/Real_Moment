package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.ItemQA;
import project1.shop.domain.repository.ItemQARepository;
import project1.shop.dto.innerDto.ItemQADto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminItemQAService {

    private final ItemQARepository itemQARepository;


    // 모든 상품 Q&A 목록 조회
    @Transactional
    public List<ItemQADto.ItemQAResponse> showQAList() {

        List<ItemQA> QAList = itemQARepository.findAll();

        List<ItemQADto.ItemQAResponse> QAListDto = QAList.stream()
                .map(ItemQADto.ItemQAResponse::new)
                .collect(Collectors.toList());

        return QAListDto;
    }


    // 상품 Q&A 상세 보기
    public ItemQADto.ItemQAResponse showQA(Long itemQAId) {

        ItemQA itemQA = itemQARepository.findById(itemQAId).orElse(null);

        ItemQADto.ItemQAResponse itemQADto = new ItemQADto.ItemQAResponse(itemQA);

        return itemQADto;
    }
}
