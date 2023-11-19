package heo.web.assignment.controller;

import heo.web.assignment.dto.member.MypageDto;
import heo.web.assignment.dto.member.MemberRequestDto;
import heo.web.assignment.service.MemberMypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberMypageService memberMypageService;
    // 마이페이지 조회
    @GetMapping("/mypage")
    public ResponseEntity<MypageDto> mypage(Principal principal) {
        return ResponseEntity.ok(memberMypageService.mypage(principal.getName()));
    }
}
