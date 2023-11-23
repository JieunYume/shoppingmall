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
public class NoticeInListDto {
    private Long id;
    private String title;
    private int views;
    private LocalDateTime date;

    public static NoticeInListDto toDto(Notice notice) {
        return NoticeInListDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .views(notice.getViews())
                .date(notice.getDate())
                .build();
    }
}
