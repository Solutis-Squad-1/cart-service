package br.com.solutis.squad1.cartservice.dto;

import br.com.solutis.squad1.cartservice.model.entity.Cart;

import java.util.List;

public record CartResponseDto(

        Long id,

        Long userId,

        List<Long> products) {

    public CartResponseDto(Cart cart, List<Long> products) {
        this(
                cart.getId(),
                cart.getUserId(),
                products
        );
    }
}