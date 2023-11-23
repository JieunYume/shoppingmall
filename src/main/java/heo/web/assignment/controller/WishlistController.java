package heo.web.assignment.controller;

import heo.web.assignment.dto.product.ProductDetailDto;
import heo.web.assignment.dto.success.SuccessDto;
import heo.web.assignment.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishService wishListService;
    @PostMapping("/")
    public ResponseEntity<SuccessDto> addWish(Principal principal, @RequestParam Long productId) {
        return ResponseEntity.ok(wishListService.addWish(principal.getName(), productId));
    }

    @GetMapping("/")
    public ResponseEntity<?> findWishlist(Principal principal) {
        return ResponseEntity.ok(wishListService.findWishlist(principal.getName()));
    }

    @DeleteMapping("/")
    public ResponseEntity<SuccessDto> deleteWishlist(Principal principal, @RequestParam Long productId) {
        return ResponseEntity.ok(wishListService.deleteWish(principal.getName(), productId));
    }


}
