package br.com.solutis.squad1.cartservice.dto.cart;

import br.com.solutis.squad1.cartservice.model.entity.Cart;

import java.util.List;

public record CartResponseDto(

        Long id,

        Long userId,

        List<Long> productsIds
) {

    public CartResponseDto(Cart cart, List<Long> products) {
        this(
                cart.getId(),
                cart.getUserId(),
                products
        );
    }
}
