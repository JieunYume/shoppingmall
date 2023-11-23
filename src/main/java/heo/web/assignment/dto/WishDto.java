package heo.web.assignment.dto;

import heo.web.assignment.entity.Product;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class WishDto {

    private Long productId;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String price;

    private String image;

    public static WishDto toDto(Product product) {
        return WishDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .image(product.getImage())
                .build();
    }
}
