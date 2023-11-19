package heo.web.assignment.service;

import heo.web.assignment.entity.token.VerificationToken;
import heo.web.assignment.exception.CustomException;
import heo.web.assignment.repository.MemberRepository;
import heo.web.assignment.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static heo.web.assignment.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final SmtpEmailService smtpEmailService;
    private final MemberRepository memberRepository;

    public void createVerificationToken(String email) {
        if (!isEmailRegistered(email)) {
            throw new CustomException(USER_NOT_FOUND);
        }
        VerificationToken token = VerificationToken.create(email);
        verificationTokenRepository.save(token);
        smtpEmailService.sendVerificationCode(email, token.getVerificationCode());
    }


    public boolean createVerificationTokenForSign(String email) {
        if (isEmailRegistered(email)) {
            throw new CustomException(ALREADY_SAVED_EMAIL);
        }

        VerificationToken token = VerificationToken.create(email);
        verificationTokenRepository.save(token);
        smtpEmailService.sendVerificationCode(email, token.getVerificationCode());//TODO: 아이디나 비밀번호를 찾는 로직과는 다르게 구성할것.
        return true;
    }

    private boolean isEmailRegistered(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 인증 코드가 유효하면 userEmail를 리턴
    public String returnUserEmail(String token)  {
        VerificationToken verificationToken = verificationTokenRepository.findByVerificationCode(token);
        return checkTokenAndGetEmail(verificationToken);

    }

//    public boolean verifyUserEmailForIdOrPwd(String Code) {
//        VerificationToken verificationToken = verificationTokenRepository.findByVerificationCode(Code);
//
//        if (checkToken(verificationToken)) {
//            verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
//            verificationTokenRepository.save(verificationToken);
//            return true;
//        }
//        else return false;
//    }

    public boolean verifyUserEmailForSign(String token)  {
        VerificationToken verificationToken = verificationTokenRepository.findByVerificationCode(token);
        return !checkTokenAndGetEmail(verificationToken).isEmpty();
    }


    public String checkTokenAndGetEmail(VerificationToken verificationToken) {
        if(verificationToken == null) {
            throw new CustomException(CODE_NOT_MATCH);
        }
        //시간이 지난 경우
        if(!verificationToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            verificationTokenRepository.delete(verificationToken);
            throw new CustomException(VERIFICATION_CODE_TIMEOUT);
        }
        String email = verificationToken.getEmail();
        if (email == null) {
            throw new CustomException(USER_NOT_FOUND);
        }
        verificationTokenRepository.delete(verificationToken);

        return email;
    }

}
