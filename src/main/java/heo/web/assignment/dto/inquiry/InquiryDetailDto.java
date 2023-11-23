package heo.web.assignment.dto.inquiry;

import heo.web.assignment.entity.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class InquiryDetailDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime date;
    private boolean canBeModified;

    public static InquiryDetailDto toDto(Inquiry inquiry, boolean canBeModified) {
        return InquiryDetailDto.builder()
                .id(inquiry.getId())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .date(inquiry.getDate())
                .canBeModified(canBeModified)
                .build();
    }
}
