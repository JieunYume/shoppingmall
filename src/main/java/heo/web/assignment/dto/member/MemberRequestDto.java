package heo.web.assignment.dto.member;

import heo.web.assignment.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String name;
    private String email;
    private String password;

    private String phoneNumber;
    private char gender;
    private String address;
    private boolean smsAcceptance;
    private boolean emailAcceptance;
    private LocalDate birthDate;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .phoneNumber(phoneNumber)
                .gender(gender)
                .address(address)
                .smsAcceptance(smsAcceptance)
                .emailAcceptance(emailAcceptance)
                .birthDate(birthDate)
                .build();
    }


}
