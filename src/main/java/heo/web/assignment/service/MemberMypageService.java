package heo.web.assignment.service;

import heo.web.assignment.dto.member.MypageDto;
import heo.web.assignment.entity.Member;
import heo.web.assignment.exception.CustomException;
import heo.web.assignment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static heo.web.assignment.exception.ErrorCode.USER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberMypageService {
    private final MemberRepository memberRepository;
    @Transactional
    public MypageDto findMypage(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND));
        return MypageDto.toDto(member);
    }


}
