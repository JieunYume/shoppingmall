package heo.web.assignment.controller;

import heo.web.assignment.dto.inquiry.InquiryCreateRequest;
import heo.web.assignment.dto.inquiry.InquiryCreateResponse;
import heo.web.assignment.dto.inquiry.InquiryUpdateRequest;
import heo.web.assignment.dto.notice.NoticeCreateRequest;
import heo.web.assignment.dto.notice.NoticeCreateResponse;
import heo.web.assignment.service.NoticeService;
import heo.web.assignment.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/notice")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;



    // 작성
    @PostMapping("/")
    public ResponseEntity<NoticeCreateResponse> createNotice(@RequestBody NoticeCreateRequest noticeCreateRequest) {
        return ResponseEntity.ok(noticeService.createNotice(noticeCreateRequest));

    }

    // 조회 1. 게시판
    @CrossOrigin(origins = "http://localhost:5500")
    @GetMapping("/list")
    public ResponseEntity<Object> findNoticeList() {
        return ResponseEntity.ok(noticeService.findNoticeList());
    }


    // 조회 2. 상세
    @GetMapping("/detail")
    public ResponseEntity<Object> findInquiryDetail(@RequestParam Long noticeId) {
        return ResponseEntity.ok(noticeService.findNoticeDetail(noticeId));
    }
}
