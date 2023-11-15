package com.ssafy.i5i.hotelAPI.domain.user.service;

import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
import com.ssafy.i5i.hotelAPI.domain.user.entity.Email;
import com.ssafy.i5i.hotelAPI.domain.user.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;

    @Transactional
    public void sendEmail(String email) {
        Optional<Email> authorizedEmail = emailRepository.selectAuthorizedEmail(email);
        if(authorizedEmail.isPresent()) {
            throw new CommonException(ExceptionType.EMAIL_EXIST_EXCEPTION);
        }

        long verifiedCode = Math.round(100000 + Math.random() * 899999);
        Optional<Email> checkEmail = emailRepository.selectUnAuthorizedEmail(email);
        if(checkEmail.isEmpty()) {
            Email newEmail = Email.builder()
                    .email(email)
                    .createdTime(LocalDateTime.now())
                    .isAuthorized(false)
                    .code(verifiedCode)
                    .build();
        }
        else {
            emailRepository.setCode(verifiedCode, checkEmail.get().getEmailId());
            emailRepository.setCreatedTime(LocalDateTime.now(), checkEmail.get().getEmailId());
        }

        MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), "UTF-8");
        try {
            messageHelper.setTo(email);
            messageHelper.setSubject("SSAFY OPEN API 이메일 인증코드 안내");
            messageHelper.setText("    <div style=\"width: 700px; height: 500px; margin: 50px\">\n" +
                    "      <h2 style=\"font-weight: 900\">이메일 확인을 위해 인증번호를 보내드려요</h2>\n" +
                    "      <p>이메일 인증 화면에서 아래의 인증번호를 입력하고 인증을 완료해주세요.</p>\n" +
                    "      <h1>" + verifiedCode + "</h1>\n" +
                    "\n" +
                    "      <hr />\n" +
                    "      <pre>\n" +
                    "혹시 요청하지 않은 인증 메일을 받으셨나요?\n" +
                    "누군가 실수로 메일 주소를 잘못 입력했을 수 있어요. 계정이 도용된 것은 아니니 안심하세요.\n" +
                    "직접요청한 인증 메일이 아닌 경우 무시해주세요.\n" +
                    "      </pre>\n" +
                    "      <hr style=\"border: 0; height: 3px; background: #ccc\" />\n" +
                    "\n" +
                    "<pre>\n" +
                    "이 메일은 발신 전용 메일이에요.\n" +
                    "SSAFY OPEN API에 궁금한 점이 있으시면 답장을 통해 질문해주세요. © SSAFY OPEN API Inc.\n" +
                    "</pre>\n" +
                    "    </div>", true);
            javaMailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new CommonException(ExceptionType.EMAIL_SEND_FAIL);
        }

    }
}
