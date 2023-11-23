package heo.web.assignment.dto.inquiry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class InquiryListDto {
    private List<InquiryInListDto> inquiries;

    public static InquiryListDto toDto(List<InquiryInListDto> inquiriesDto) {
        return InquiryListDto.builder()
                .inquiries(inquiriesDto)
                .build();
    }
}
