package heo.web.assignment.dto.inquiry;

import heo.web.assignment.entity.Inquiry;
import heo.web.assignment.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InquiryUpdateRequest {
    private Long inquiryId;
    private String title;
    private String content;
}
