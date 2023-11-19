package heo.web.assignment.service.auth;

import heo.web.assignment.dto.LoginDto;
import heo.web.assignment.dto.findId.UserFindIdResponseDto;
import heo.web.assignment.dto.findPwd.NewPwdRequestDto;
import heo.web.assignment.dto.member.MemberRequestDto;
import heo.web.assignment.dto.member.MemberResponseDto;
import heo.web.assignment.dto.token.TokenRequestDto;
import heo.web.assignment.dto.token.TokenResponseDto;
import heo.web.assignment.entity.Member;
import heo.web.assignment.entity.Role;
import heo.web.assignment.entity.token.RefreshToken;
import heo.web.assignment.entity.token.VerificationToken;
import heo.web.assignment.exception.CustomException;
import heo.web.assignment.jwt.TokenProvider;
import heo.web.assignment.repository.MemberRepository;
import heo.web.assignment.repository.RefreshTokenRepository;
import heo.web.assignment.repository.RoleRepository;
import heo.web.assignment.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static heo.web.assignment.entity.Role.RoleName.ROLE_USER;
import static heo.web.assignment.exception.ErrorCode.*;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberAuthService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        Role userRole = roleRepository.findByName(ROLE_USER).orElseThrow(
                () -> new CustomException(ROLE_NOT_FOUND));

        // 이메일 중복 검사
        validateEmail(memberRequestDto.getEmail());

        // 전화번호 중복 검사
        validatePhoneNumber(memberRequestDto.getPhoneNumber());

        Member member = memberRequestDto.toMember(passwordEncoder);
        member.setRoles(Collections.singleton(userRole));

        return MemberResponseDto.toDto(memberRepository.save(member));
    }


    public void validateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new CustomException(ALREADY_SAVED_EMAIL);
        }
    }

    public void validatePhoneNumber(String phoneNumber) {
        if (memberRepository.existsByPhoneNumber(phoneNumber)) {
            throw new CustomException(ALREADY_SAVED_PHONE_NUMBER);
        }
    }
    @Transactional
    public TokenResponseDto login(LoginDto loginDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenResponseDto tokenResDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenResDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenResDto;
    }

    @Transactional
    public void logout(TokenRequestDto tokenReqDto) {
        // 로그아웃하려는 사용자의 정보를 가져옴
        Authentication authentication = tokenProvider.getAuthentication(tokenReqDto.getAccessToken());

        // 저장소에서 해당 사용자의 refresh token 삭제
        refreshTokenRepository.deleteByKey(authentication.getName());
    }

    public String changePassword(NewPwdRequestDto newPwdRequestDto) throws Exception{
        Member member = memberRepository.findByEmail(newPwdRequestDto.getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        VerificationToken verificationToken = verificationTokenRepository.findByVerificationCode(newPwdRequestDto.getCode());
        if (verificationToken == null)
            return "인증번호가 일치하지 않습니다.";
        if(!verificationToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            verificationTokenRepository.delete(verificationToken);
            return "인증 시간이 초과 되었습니다.";
        }
        member.setPassword(passwordEncoder.encode(newPwdRequestDto.getPassword()));
        memberRepository.save(member);
        return "비밀번호가 변경되었습니다.";
    }
}
