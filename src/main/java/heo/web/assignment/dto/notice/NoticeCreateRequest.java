package heo.web.assignment.dto.notice;

import heo.web.assignment.entity.Inquiry;
import heo.web.assignment.entity.Member;
import heo.web.assignment.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NoticeCreateRequest {
    private String title;
    private String content;

    public Notice toNotice() {
        return Notice.builder()
                .title(title)
                .content(content)
                .views(0)
                .date(LocalDateTime.now())
                .build();
    }
}
