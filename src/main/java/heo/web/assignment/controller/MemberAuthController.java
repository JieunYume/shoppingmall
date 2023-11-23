package heo.web.assignment.controller;

import heo.web.assignment.dto.LoginDto;
import heo.web.assignment.dto.findId.SendEmailRequestDto;
import heo.web.assignment.dto.findPwd.ChangePwdResponseDto;
import heo.web.assignment.dto.findPwd.NewPwdRequestDto;
import heo.web.assignment.dto.member.MemberRequestDto;
import heo.web.assignment.dto.member.MemberResponseDto;
import heo.web.assignment.dto.token.TokenRequestDto;
import heo.web.assignment.dto.token.TokenResponseDto;
import heo.web.assignment.dto.verificationToken.SendEmailResponseDto;
import heo.web.assignment.dto.verificationToken.VerificationTokenResponseDto;
import heo.web.assignment.entity.Member;
import heo.web.assignment.exception.CustomException;
import heo.web.assignment.repository.MemberRepository;
import heo.web.assignment.service.VerificationTokenService;
import heo.web.assignment.service.auth.MemberAuthService;
import heo.web.assignment.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static heo.web.assignment.exception.ErrorCode.USER_NOT_FOUND;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberAuthController {
    private final MemberAuthService memberAuthService;
    private final VerificationTokenService verificationTokenService;
    private final MemberRepository memberRepository;


    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberAuthService.signup(memberRequestDto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(memberAuthService.login(loginDto));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenRequestDto requestDto) {
        memberAuthService.logout(requestDto);
        return ResponseEntity.ok().build();
    }

    //TODO: 회원가입시 이메일 인증을 받는 로직이라면 추가적인 로직의 변형이 필요합니다.
    @PostMapping("/sign/verification-code")
    public ResponseEntity<?> sendEmailForSign(@RequestBody SendEmailRequestDto sendEmailRequestDto) {
        String email = sendEmailRequestDto.getEmail();
        verificationTokenService.createVerificationTokenForSign(email);
        return ResponseEntity.ok(new ApiUtils.ApiSuccess<>("인증 번호가 전송되었습니다."));
    }
    @GetMapping("/sign/verification-code")
    public ResponseEntity<?> checkCodeForSign(@RequestParam String code) {
        verificationTokenService.verifyUserEmailForSign(code);
        return ResponseEntity.ok(new ApiUtils.ApiSuccess<>("인증 번호가 확인되었습니다."));
    }

    @PostMapping("/search/verification-code")
    public ResponseEntity<?> sendEmailForFind(@RequestBody SendEmailRequestDto sendEmailRequestDto) {
        String email = sendEmailRequestDto.getEmail();
        verificationTokenService.createVerificationToken(email);
        return ResponseEntity.ok(new ApiUtils.ApiSuccess<>("인증 번호가 전송되었습니다."));
    }

//    @GetMapping("/search/verification-code") //구현의 이유가 무엇인가요?
//    public ResponseEntity<VerificationTokenResponseDto> checkCodeForIdOrPwd(@RequestParam String code) {
//        return ResponseEntity.ok(new VerificationTokenResponseDto(verificationTokenService.verifyUserEmailForIdOrPwd(code)));
//    }

    @GetMapping("/search/id")
    public ResponseEntity<?> returnId(@RequestParam String code) {
        return ResponseEntity.ok(ApiUtils.success(verificationTokenService.returnUserEmail(code)));
    }

    @PatchMapping("/search/password") //Restful 한 Url 설계가 필요해보입니다!
    public ResponseEntity<?> checkCodeForPwd(@RequestBody NewPwdRequestDto newPwdRequestDto) throws Exception {
        return ResponseEntity.ok(ApiUtils.success(memberAuthService.changePassword(newPwdRequestDto)));
    }
}
