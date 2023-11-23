package heo.web.assignment.dto;

import heo.web.assignment.entity.Product;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CartDto {

    private Long productId;

    private String name;

    private String price;

    private String image;

    private int productCount;

    public static CartDto toDto(Product product, int productCount) {
        return CartDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .image(product.getImage())
                .productCount(productCount)
                .build();
    }
}
