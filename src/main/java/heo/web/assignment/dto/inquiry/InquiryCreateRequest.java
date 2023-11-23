package heo.web.assignment.dto.inquiry;

import heo.web.assignment.entity.Inquiry;
import heo.web.assignment.entity.Member;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InquiryCreateRequest {
    private String title;
    private String content;

    public Inquiry toInquiry(Member member) {
        return Inquiry.builder()
                .title(title)
                .content(content)
                .member(member)
                .date(LocalDateTime.now())
                .build();
    }
}
