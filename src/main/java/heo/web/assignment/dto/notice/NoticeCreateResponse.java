package heo.web.assignment.dto.notice;

import heo.web.assignment.entity.Inquiry;
import heo.web.assignment.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class NoticeCreateResponse {
    private String title;
    private String content;
    private int views;
    private LocalDateTime date;

    public static NoticeCreateResponse toDto(Notice notice) {
        return NoticeCreateResponse.builder()
                .title(notice.getTitle())
                .content(notice.getContent())
                .views(notice.getViews())
                .date(notice.getDate())
                .build();
    }
}
