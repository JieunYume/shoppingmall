package heo.web.assignment.service;

import heo.web.assignment.dto.CartDto;
import heo.web.assignment.dto.CartlistDto;
import heo.web.assignment.dto.WishDto;
import heo.web.assignment.dto.WishlistDto;
import heo.web.assignment.dto.success.SuccessDto;
import heo.web.assignment.entity.Cart;
import heo.web.assignment.entity.Member;
import heo.web.assignment.entity.Product;
import heo.web.assignment.entity.Wish;
import heo.web.assignment.exception.CustomException;
import heo.web.assignment.repository.CartRepository;
import heo.web.assignment.repository.MemberRepository;
import heo.web.assignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static heo.web.assignment.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final int PRODUCT_COUNT_INIT = 1;

    @Transactional
    public SuccessDto addCart(String email, Long productId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));

        Cart cart = Cart.builder()
                .member(member)
                .product(product)
                .productCount(PRODUCT_COUNT_INIT)
                .build();

        cartRepository.save(cart);
        return new SuccessDto("장바구니에 상품을 성공적으로 추가했습니다.");
    }

    public CartlistDto findCartlist(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        List<Cart> cartList = member.getCarts();
        List<CartDto> cartDtoList = new ArrayList<>();

        for (Cart cart : cartList) {
            cartDtoList.add(CartDto.toDto(cart.getProduct(), cart.getProductCount()));
        }
        return CartlistDto.toDto(cartDtoList);
    }

    @Transactional
    public SuccessDto deleteCart(String email, Long productId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));

        Cart cart = cartRepository.findByMemberAndProduct(member, product)
                .orElseThrow(() -> new CustomException(CART_NOT_FOUND));

        cartRepository.delete(cart);
        return new SuccessDto("장바구니에 상품을 성공적으로 삭제했습니다.");

    }

}
