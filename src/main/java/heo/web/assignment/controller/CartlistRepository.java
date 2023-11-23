package heo.web.assignment.controller;

import heo.web.assignment.dto.success.SuccessDto;
import heo.web.assignment.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartlistRepository {
    private final CartService cartService;
    @PostMapping("/")
    public ResponseEntity<SuccessDto> addWish(Principal principal, @RequestParam Long productId) {
        return ResponseEntity.ok(cartService.addCart(principal.getName(), productId));
    }

    @GetMapping("/")
    public ResponseEntity<?> findWishlist(Principal principal) {
        return ResponseEntity.ok(cartService.findCartlist(principal.getName()));
    }

    @DeleteMapping("/")
    public ResponseEntity<SuccessDto> deleteWishlist(Principal principal, @RequestParam Long productId) {
        return ResponseEntity.ok(cartService.deleteCart(principal.getName(), productId));
    }


}
