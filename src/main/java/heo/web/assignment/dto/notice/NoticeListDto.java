package heo.web.assignment.dto.notice;

import heo.web.assignment.dto.inquiry.InquiryInListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class NoticeListDto {
    private List<NoticeInListDto> noticeList;

    public static NoticeListDto toDto(List<NoticeInListDto> noticeListDto) {
        return NoticeListDto.builder()
                .noticeList(noticeListDto)
                .build();
    }
}
