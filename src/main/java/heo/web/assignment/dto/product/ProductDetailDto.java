package heo.web.assignment.dto.product;

import heo.web.assignment.dto.inquiry.InquiryDetailDto;
import heo.web.assignment.entity.Inquiry;
import heo.web.assignment.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ProductDetailDto {
    private Long id;
    private String name;
    private String price;
    private String image;
    private String categoryName;

    public static ProductDetailDto toDto(Product product) {
        return ProductDetailDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .image(product.getImage())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
