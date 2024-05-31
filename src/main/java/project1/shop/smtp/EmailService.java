package project1.shop.smtp;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;
    private final EmailCodeRepository emailCodeRepository;

    // 단순 이메일 보내기
    @Transactional
    public void sendSimpleEmail(EmailDto.EmailRequest request) {

        // 단순 문자 이메일을 보내기 위한 객체
        SimpleMailMessage message = new SimpleMailMessage();
        // 이메일 제목
        message.setSubject("임시 비밀번호가 발신되었습니다.");
        // 이메일 수신자
        message.setTo(request.getEmail());
        // 이메일 내용
        message.setText("구라지롱");

        javaMailSender.send(message);
    }

    // HTML 이메일 보내기
    @Transactional
    public void sendHTMLEmail(EmailDto.EmailRequest request) {

        MimeMessage message = javaMailSender.createMimeMessage();
        String number = createRandomNumber();

        // 해당 이메일이 이전에 인증코드를 받은 내역이 남아있는지 확인
        EmailCode emailCode = emailCodeRepository.findByEmail(request.getEmail()).orElse(null);

        // 만약 없다면 정상적으로 생성 / 있다면 기존 인증코드를 새로운 코드로 교체 & 만료시간 갱신
        if(emailCode == null){
            emailCode = new EmailCode(request.getEmail(), number, createExpiryDate());
        } else {
            emailCode.changeCode(number, createExpiryDate());
        }


        try {
            // 이메일 제목
            message.setSubject("[RealMoment] 이메일 인증");
            // 이메일 내용 (HTML 내용으로 세팅한 값을 지정)(UTF-8 = 인코딩, html = 해당 내용이 html)
            message.setText(setContext(number), "UTF-8", "html");
            // 수신자 이메일을 추가하는 부분
            message.addRecipients(MimeMessage.RecipientType.TO, request.getEmail());
            // 해당 메시지를 이메일 보냄
            javaMailSender.send(message);
            // DB에 해당 정보 저장
            emailCodeRepository.save(emailCode);

        } catch (MessagingException e){
            e.printStackTrace();
        }
    }

    // 이메일 인증코드 인증 (+만료시간 체크)
    @Transactional
    public String codeCheck(EmailDto.EmailCodeRequest request){
        EmailCode emailCode = emailCodeRepository.findByEmail(request.getEmail()).orElse(null);

        if (emailCode != null && emailCode.getExpiryDate().isAfter(LocalDateTime.now())){
            if (emailCode.getCode().equals(request.getCode())){
                emailCodeRepository.delete(emailCode);
                return "인증 성공";
            } else {
                return "인증 실패";
            }
        } else {
            return "기간 만료 or 잘못된 이메일";
        }
    }


    // HTML 이메일 내용 세팅
    @Transactional
    public String setContext(String number){
        Context context = new Context();

        // html에서 변수 ${number}에 들어갈 변수 명과 값을 작성
        context.setVariable("number", number);

        // html의 경로(이름)과 context를 작성
        return springTemplateEngine.process("index", context);
    }

    // 6자리 랜덤 비밀번호 생성하기
    @Transactional
    public String createRandomNumber() {

        String[] sign = new String[] {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "Y", "Z"
        };

        StringBuffer key = new StringBuffer();

        for(int i = 0; i < 6; i++){
            int randomIndex = (int)(Math.random() * sign.length);
            key.append(sign[randomIndex]);
        }

        return key.toString();
    }

    // 인증 만료 시간 생성
    public LocalDateTime createExpiryDate(){
        // 현재 시간에서 5분 추가된 시간
        LocalDateTime expiryDate = LocalDateTime.now().plus(5, ChronoUnit.MINUTES);
        return  expiryDate;
    }


}
