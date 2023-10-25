package br.com.solutis.squad1.cartservice.mapper;

import br.com.solutis.squad1.cartservice.dto.cart.CartPostDto;
import br.com.solutis.squad1.cartservice.dto.cart.CartPutDto;
import br.com.solutis.squad1.cartservice.dto.cart.CartResponseDto;
import br.com.solutis.squad1.cartservice.model.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartResponseDto toResponseDto(Cart cart);

    @Mapping(target = "id", ignore = true)
    Cart responseDtoToEntity(CartResponseDto cartResponseDto);

    @Mapping(target = "id", ignore = true)
    Set<CartResponseDto> toResponseDto(Set<Cart> carts);

    @Mapping(target = "id", ignore = true)
    Cart postDtoToEntity(CartPostDto cartPostDto);

    @Mapping(target = "id", ignore = true)
    Cart putDtoToEntity(CartPutDto cartPutDto);
}
