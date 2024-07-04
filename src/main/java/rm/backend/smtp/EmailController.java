package rm.backend.smtp;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class EmailController {

    private final EmailService emailService;

    // 그냥 테스트용 이메일 전송
    @PostMapping("/email/test")
    public String simpleEmail(@RequestBody EmailDto.EmailRequest request) {

        emailService.sendSimpleEmail(request);

        return "심플 이메일 발송 완료";
    }

    // 인증코드 이메일 전송 (html파일 전송)
    @PostMapping("/email/html")
    public String htmlEmail(@RequestBody EmailDto.EmailRequest request) {

        emailService.sendHTMLEmail(request);

        return "인증번호 이메일 발송 완료";
    }

    // 이메일 인증코드 검증(확인)
    @PostMapping("/email/code/check")
    public ResponseEntity<String> codeCheck(@RequestBody EmailDto.EmailCodeRequest request){

        String result = emailService.codeCheck(request);

        if (result.equals("인증 성공")){
            return ResponseEntity.ok("GOOD");
        } else if (result.equals("인증 실패")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TIME_OUT_or_BAD_EMAIL");
        }
    }
}
