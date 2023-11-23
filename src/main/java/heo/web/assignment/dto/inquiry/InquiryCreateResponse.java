package heo.web.assignment.dto.inquiry;

import heo.web.assignment.entity.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class InquiryCreateResponse {
    private String writerName;
    private String title;
    private String content;
    private LocalDateTime date;

    public static InquiryCreateResponse toDto(Inquiry inquiry) {
        return InquiryCreateResponse.builder()
                .writerName(inquiry.getMember().getName())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .date(inquiry.getDate())
                .build();
    }
}
