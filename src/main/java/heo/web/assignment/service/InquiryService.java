package heo.web.assignment.service;

import heo.web.assignment.dto.inquiry.*;
import heo.web.assignment.entity.Inquiry;
import heo.web.assignment.entity.Member;
import heo.web.assignment.exception.CustomException;
import heo.web.assignment.repository.InquiryRepository;
import heo.web.assignment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static heo.web.assignment.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryService {
    private final MemberRepository memberRepository;
    private final InquiryRepository inquiryRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public InquiryCreateResponse createInquiry(String email, InquiryCreateRequest inquiryCreateRequest) throws CustomException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Inquiry inquiry = inquiryRepository.save(inquiryCreateRequest.toInquiry(member));
        return InquiryCreateResponse.toDto(inquiry);
    }

    public InquiryListDto findInquiryList(String email) {
        List<Inquiry> inquiries = inquiryRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));

        List<InquiryInListDto> inquiriesDto = new ArrayList<>();
        for (Inquiry inquiry : inquiries) {
            inquiriesDto.add(InquiryInListDto.toDto(inquiry));
        }

        return InquiryListDto.toDto(inquiriesDto);
    }

    public InquiryDetailDto findInquiryDetail(String email, Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomException(INQUIRY_NOT_FOUND));

        boolean canBeModified = canBeModified(email, inquiryId);
        return InquiryDetailDto.toDto(inquiry, canBeModified);
    }

    private boolean canBeModified(String email, Long inquiryId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        List<Inquiry> inquiries = member.getInquiries();
        for (Inquiry inquiry : inquiries) {
            if (inquiry.getId() == inquiryId) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public InquiryDetailDto updateInquiry(String email, InquiryUpdateRequest inquiryUpdateRequest) {
        Inquiry originalInquiry = inquiryRepository.findById(inquiryUpdateRequest.getInquiryId())
                .orElseThrow(() -> new CustomException(INQUIRY_NOT_FOUND));
        Inquiry updateInquiry = originalInquiry;

        if (!updateInquiry.getMember().getEmail().equals(email)) {
            throw new CustomException(WRITER_NOT_MATCH);
        }

        if (inquiryUpdateRequest.getTitle() != null) updateInquiry.setTitle(inquiryUpdateRequest.getTitle());
        if (inquiryUpdateRequest.getContent() != null) updateInquiry.setContent(inquiryUpdateRequest.getContent());
        updateInquiry.setDate(LocalDateTime.now());
        
        Inquiry inquiry = inquiryRepository.save(updateInquiry);
        boolean canBeModified = true;
        return InquiryDetailDto.toDto(inquiry, canBeModified);

    }

    @Transactional
    public boolean deleteInquiry(String email, Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new CustomException(INQUIRY_NOT_FOUND));

        Inquiry deleteInquiry = inquiry;

        if(deleteInquiry.getMember().getEmail().equals(email)) {
            inquiryRepository.delete(deleteInquiry);
            return true;
        }
        return false;
    }
}
