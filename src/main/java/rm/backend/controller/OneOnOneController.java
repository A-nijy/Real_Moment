package rm.backend.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import rm.backend.dto.innerDto.OneOnOneDto;
import rm.backend.dto.innerDto.SearchDto;
import rm.backend.service.OneOnOneService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class OneOnOneController {

    private final OneOnOneService oneOnOneService;


    // 1대1 문의 목록 조회 (내꺼)
    @GetMapping("/member/{id}/oneOnOneList")
    private OneOnOneDto.MyPageResponse showOneOnOneList(@PathVariable Long id, SearchDto.OneOnOnes request){

        OneOnOneDto.MyPageResponse oneOnOneDtoList = oneOnOneService.showOneOnOneList(id, request);

        return oneOnOneDtoList;
    }


    // 1대1 문의 추가
    @PostMapping("/member/{id}/oneOnOne")
    public String createOneOnOne(@PathVariable Long id, @RequestBody OneOnOneDto.Request request){

        oneOnOneService.createOneOnOne(id, request);

        return "문의 작성 완료";
    }


    // 1대1 문의 수정 버튼 (1대1 문의 데이터 가져옴)
    @GetMapping("/member/{id}/oneOnOne")
    public OneOnOneDto.UpdateResponse getOneOnOne(@PathVariable Long id, @RequestParam("oneOnOneId") Long oneOnOneId){

        OneOnOneDto.UpdateResponse oneOnOneDto = oneOnOneService.getOneOnOne(id, oneOnOneId);

        return oneOnOneDto;
    }


    // 1대1 문의 수정
    @PatchMapping("/member/{id}/oneOnOne")
    public String updateOneOnOne(@PathVariable Long id, @RequestBody OneOnOneDto.UpdateRequest request){

        oneOnOneService.updateOneOnOne(id, request);

        return "문의 수정 완료";
    }

    // 1대1 문의 삭제
    @DeleteMapping("/member/{id}/oneOnOne")
    public String deleteOneOnOne(@PathVariable Long id, @RequestParam("oneOnOneId") Long oneOnOneId){

        oneOnOneService.deleteOneOnOne(id, oneOnOneId);

        return "문의 삭제 완료";
    }
}
