package rm.backend.adminService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rm.backend.domain.entity.ItemQA;
import rm.backend.domain.entity.QAComment;
import rm.backend.domain.repository.ItemQARepository;
import rm.backend.domain.repository.QACommentRepository;
import rm.backend.dto.innerDto.ItemQADto;
import rm.backend.dto.innerDto.QACommentDto;
import rm.backend.dto.innerDto.SearchDto;

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
    public ItemQADto.ItemQAPageResponse showQAList(SearchDto.AdminItemQA request) {

        PageRequest pageRequest = PageRequest.of(request.getNowPage() - 1, 10);

        Page<ItemQA> itemQAs = itemQARepository.searchAdminItemQAs(request, pageRequest);

        List<ItemQADto.ItemQAResponse> itemQADto = itemQAs.stream()
                .map(ItemQADto.ItemQAResponse::new)
                .collect(Collectors.toList());

        for(ItemQADto.ItemQAResponse itemQA : itemQADto){
            QAComment qaComment = qaCommentRepository.findById(itemQA.getItemQAId()).orElse(null);

            QACommentDto.Response qaCommentDto = null;

            if (qaComment != null){
                qaCommentDto = new QACommentDto.Response(qaComment);
            }

            itemQA.setQAComment(qaCommentDto);
        }

        ItemQADto.ItemQAPageResponse itemQAPageDto = new ItemQADto.ItemQAPageResponse(itemQADto, itemQAs.getTotalPages(), request.getNowPage());

        return itemQAPageDto;
    }

}
