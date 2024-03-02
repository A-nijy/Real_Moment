package project1.shop.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.ItemQA;
import project1.shop.domain.entity.QAComment;
import project1.shop.domain.repository.ItemQARepository;
import project1.shop.domain.repository.QACommentRepository;
import project1.shop.dto.innerDto.ItemQADto;
import project1.shop.dto.innerDto.QACommentDto;
import project1.shop.dto.innerDto.SearchDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminItemQAService {

    private final ItemQARepository itemQARepository;
    private final QACommentRepository qaCommentRepository;


    // 모든 상품 Q&A 목록 조회
    @Transactional
    public ItemQADto.ItemQAPageResponse showQAList(SearchDto.ItemQAs request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 10);

        Page<ItemQA> itemQAs = itemQARepository.searchItemQAs(request, pageRequest);

        List<ItemQADto.ItemQAResponse> itemQADto = itemQAs.stream()
                .map(ItemQADto.ItemQAResponse::new)
                .collect(Collectors.toList());

        for(ItemQADto.ItemQAResponse itemQA : itemQADto){
            QAComment qaComment = qaCommentRepository.findById(itemQA.getItemQAId()).orElse(null);

            QACommentDto.Response qaCommentDto = new QACommentDto.Response(qaComment);

            itemQA.setQAComment(qaCommentDto);
        }

        ItemQADto.ItemQAPageResponse itemQAPageDto = new ItemQADto.ItemQAPageResponse(itemQADto, itemQAs.getTotalPages(), request.getNowPage());

        return itemQAPageDto;
    }


//    // 상품 Q&A 상세 보기
//    public ItemQADto.ItemQAResponse showQA(Long itemQAId) {
//
//        ItemQA itemQA = itemQARepository.findById(itemQAId).orElse(null);
//
//        ItemQADto.ItemQAResponse itemQADto = new ItemQADto.ItemQAResponse(itemQA);
//
//        return itemQADto;
//    }
}
