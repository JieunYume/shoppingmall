package heo.web.assignment.dto.product;

import heo.web.assignment.dto.inquiry.InquiryInListDto;
import heo.web.assignment.entity.Inquiry;
import heo.web.assignment.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ProductInListDto {
    private Long id;
    private String name;
    private String price;
    private String image;
    private String categoryName;

    public static ProductInListDto toDto(Product product) {
        return ProductInListDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .image(product.getImage())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
