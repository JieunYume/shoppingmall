package heo.web.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CartlistDto {
    private List<CartDto> cartDtoList;

    public static CartlistDto toDto(List<CartDto> cartDtoList) {
        return CartlistDto.builder()
                .cartDtoList(cartDtoList)
                .build();
    }
}
