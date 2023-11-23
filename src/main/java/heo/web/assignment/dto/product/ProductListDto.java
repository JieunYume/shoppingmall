package heo.web.assignment.dto.product;

import heo.web.assignment.dto.inquiry.InquiryInListDto;
import heo.web.assignment.dto.inquiry.InquiryListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductListDto {
    private List<ProductInListDto> products;

    public static ProductListDto toDto(List<ProductInListDto> productsDto) {
        return ProductListDto.builder()
                .products(productsDto)
                .build();
    }
}
