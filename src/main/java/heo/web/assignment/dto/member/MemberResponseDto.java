package heo.web.assignment.dto.member;

import heo.web.assignment.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private String email;
    private String name;

// 코드의 재사용성과 가독성을 위한 Member 객체를 Dto 객체로 변환하는 일종의 캡슐화 전략
    public static MemberResponseDto toDto(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }

}
