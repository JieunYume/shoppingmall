package heo.web.assignment.service;

import heo.web.assignment.dto.WishDto;
import heo.web.assignment.dto.WishlistDto;
import heo.web.assignment.dto.success.SuccessDto;
import heo.web.assignment.entity.Member;
import heo.web.assignment.entity.Product;
import heo.web.assignment.entity.Wish;
import heo.web.assignment.exception.CustomException;
import heo.web.assignment.repository.MemberRepository;
import heo.web.assignment.repository.ProductRepository;
import heo.web.assignment.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static heo.web.assignment.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WishService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final WishRepository wishRepository;
    @Transactional
    public SuccessDto addWish(String email, Long productId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));

        if(wishRepository.findByMemberAndProduct(member, product).isEmpty() == false){
            throw new CustomException(ALREADY_SAVED_WISH_PRODUCT);
        }

        Wish wishlist = Wish.builder()
                .member(member)
                .product(product)
                .build();

        wishRepository.save(wishlist);
        return new SuccessDto("위시리스트에 상품을 성공적으로 추가했습니다.");
    }

    public WishlistDto findWishlist(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        List<Wish> wishlist = member.getWishlists();
        List<WishDto> wishDtoList = new ArrayList<>();

        for (Wish wish : wishlist) {
            wishDtoList.add(WishDto.toDto(wish.getProduct()));
        }
        return WishlistDto.toDto(wishDtoList);
    }

    @Transactional
    public SuccessDto deleteWish(String email, Long productId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));

        Wish wish = wishRepository.findByMemberAndProduct(member, product)
                .orElseThrow(() -> new CustomException(WISH_NOT_FOUND));

        wishRepository.delete(wish);
        return new SuccessDto("위시리스트에 상품을 성공적으로 삭제했습니다.");

    }

}
