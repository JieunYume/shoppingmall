package heo.web.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class WishlistDto {
    private List<WishDto> wishDtoList;

    public static WishlistDto toDto(List<WishDto> wishDtoList) {
        return WishlistDto.builder()
                .wishDtoList(wishDtoList)
                .build();
    }
}
