package heo.web.assignment.controller;

import heo.web.assignment.dto.product.ProductDetailDto;
import heo.web.assignment.dto.product.ProductInListDto;
import heo.web.assignment.dto.product.ProductListDto;
import heo.web.assignment.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/list")
    public ResponseEntity<ProductListDto> findProductList() {
        return ResponseEntity.ok(productService.findProductList());
    }

    @GetMapping("/")
    public ResponseEntity<ProductDetailDto> findProductDetail(@RequestParam Long productId) {
        return ResponseEntity.ok(productService.findProductDetail(productId));
    }
}
