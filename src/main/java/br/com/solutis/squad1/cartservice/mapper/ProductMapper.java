package br.com.solutis.squad1.cartservice.mapper;

import br.com.solutis.squad1.cartservice.dto.cart.CartPostDto;
import br.com.solutis.squad1.cartservice.dto.cart.CartResponseDto;
import br.com.solutis.squad1.cartservice.model.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    CartResponseDto toResponseDto(Cart cart);

    @Mapping(target = "id", ignore = true)
    Cart postDtoToEntity(CartPostDto cartPostDto);
}
