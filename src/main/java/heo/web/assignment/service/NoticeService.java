package heo.web.assignment.service;

import heo.web.assignment.dto.inquiry.*;
import heo.web.assignment.dto.notice.*;
import heo.web.assignment.entity.Inquiry;
import heo.web.assignment.entity.Member;
import heo.web.assignment.entity.Notice;
import heo.web.assignment.exception.CustomException;
import heo.web.assignment.repository.MemberRepository;
import heo.web.assignment.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static heo.web.assignment.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {
    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;

    // 임시로 공지사항 추가 메소드 만듬 -> 관리자만 가능하게 수정하거나 없애야 함
    @Transactional
    public NoticeCreateResponse createNotice(NoticeCreateRequest noticeCreateRequest) throws CustomException {
        Notice notice = noticeRepository.save(noticeCreateRequest.toNotice());
        return NoticeCreateResponse.toDto(notice);
    }

    public NoticeListDto findNoticeList() {
        List<Notice> noticeList = noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));

        List<NoticeInListDto> noticeListDto = new ArrayList<>();
        for (Notice notice : noticeList) {
            noticeListDto.add(NoticeInListDto.toDto(notice));
        }

        return NoticeListDto.toDto(noticeListDto);
    }

    @Transactional
    public NoticeDetailDto findNoticeDetail(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new CustomException(NOTICE_NOT_FOUND));

        // 조회수 업
        notice.addViews();
        noticeRepository.save(notice);

        return NoticeDetailDto.toDto(notice);
    }
}
