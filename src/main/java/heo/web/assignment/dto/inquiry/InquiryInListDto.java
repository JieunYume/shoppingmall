package heo.web.assignment.dto.inquiry;

import heo.web.assignment.entity.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class InquiryInListDto {
    private Long id;
    private String title;
    private LocalDateTime date;

    public static InquiryInListDto toDto(Inquiry inquiry) {
        return InquiryInListDto.builder()
                .id(inquiry.getId())
                .title(inquiry.getTitle())
                .date(inquiry.getDate())
                .build();
    }
}
