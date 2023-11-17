package br.com.solutis.squad1.cartservice.dto.cart;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * DTO for creating a cart.
 */
public record CartPostDto(

        @NotNull
        Long userId,

        @NotNull
        List<Long> productsIds
) {
}
