package br.com.solutis.squad1.cartservice.dto.orderItem;

import br.com.solutis.squad1.cartservice.model.entity.Cart;
import br.com.solutis.squad1.cartservice.model.entity.Product;
import jakarta.validation.constraints.NotNull;


public record OrderItemResponseDto(
        @NotNull
        Long id,
        @NotNull
        Product product,
        @NotNull
        Cart cart,
        @NotNull
        Integer quantity
) {
}
