package heo.web.assignment.controller;

import heo.web.assignment.dto.inquiry.InquiryCreateRequest;
import heo.web.assignment.dto.inquiry.InquiryCreateResponse;
import heo.web.assignment.dto.inquiry.InquiryDeleteRequest;
import heo.web.assignment.dto.inquiry.InquiryUpdateRequest;
import heo.web.assignment.service.InquiryService;
import heo.web.assignment.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;
    // 작성
    @PostMapping("/")
    public ResponseEntity<InquiryCreateResponse> createInquiry(Principal principal, @RequestBody InquiryCreateRequest inquiryCreateRequest) {
        return ResponseEntity.ok(inquiryService.createInquiry(principal.getName(), inquiryCreateRequest));

    }

    // 조회 1. 게시판
    @GetMapping("/list")
    public ResponseEntity<Object> findInquiryList(Principal principal) {
        return ResponseEntity.ok(inquiryService.findInquiryList(principal.getName()));
    }


    // 조회 2. 상세
    @GetMapping("/detail")
    public ResponseEntity<Object> findInquiryDetail(Principal principal, @RequestParam Long inquiryId) {
        return ResponseEntity.ok(inquiryService.findInquiryDetail(principal.getName(), inquiryId));
    }

    // 수정
    @PatchMapping("/")
    public ResponseEntity<Object> updateInquiry(Principal principal, @RequestBody InquiryUpdateRequest inquiryUpdateRequest) {
        return ResponseEntity.ok(inquiryService.updateInquiry(principal.getName(), inquiryUpdateRequest));
    }

    // 삭제
    @DeleteMapping("/")
    public ResponseEntity<?> deleteInquiry(Principal principal, @RequestParam Long inquiryId) {
        inquiryService.deleteInquiry(principal.getName(),inquiryId);
        return ResponseEntity.ok(ApiUtils.success("문의글 삭제가 완료되었습니다."));
    }
}
