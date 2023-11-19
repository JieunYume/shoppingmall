package heo.web.assignment.service.auth;

import heo.web.assignment.entity.Member;
import heo.web.assignment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * username을 가지고 사용자 정보를 조회하고 session에 저장될 사용자 주체 정보인 UserDetails를 반환하는 Interface
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            return createUserDetails(memberOptional.get());
        } else {
            throw new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다.");
        }
    }

    // DB 에 Member 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthorities().toString());
        return new org.springframework.security.core.userdetails.User(
            member.getUsername(),
            member.getPassword(),
            Collections.singleton(grantedAuthority)
        );
    }
}
