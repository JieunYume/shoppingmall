package heo.web.assignment.dto.member;
import heo.web.assignment.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class MypageDto {
    private String name;
    private LocalDate birthDate;
    private char gender;
    private String address;
    private String email;
    private String phoneNumber;
    private boolean smsAcceptance;
    private boolean emailAcceptance;

    public static MypageDto toDto(Member member) {
        return MypageDto.builder()
                .name(member.getName())
                .birthDate(member.getBirthDate())
                .address(member.getAddress())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .gender(member.getGender())
                .smsAcceptance(member.isSmsAcceptance())
                .emailAcceptance(member.isEmailAcceptance())
                .build();
    }
}
