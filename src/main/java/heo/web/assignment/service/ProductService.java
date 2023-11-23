package heo.web.assignment.service;

import heo.web.assignment.dto.product.ProductDetailDto;
import heo.web.assignment.dto.product.ProductInListDto;
import heo.web.assignment.dto.product.ProductListDto;
import heo.web.assignment.entity.Product;
import heo.web.assignment.exception.CustomException;
import heo.web.assignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static heo.web.assignment.exception.ErrorCode.PRODUCT_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public ProductListDto findProductList() {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));

        List<ProductInListDto> productsDto = new ArrayList<>();
        for (Product product : products) {
            productsDto.add(ProductInListDto.toDto(product));
        }

        return ProductListDto.toDto(productsDto);
    }

    public ProductDetailDto findProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
        return ProductDetailDto.toDto(product);
    }
}
