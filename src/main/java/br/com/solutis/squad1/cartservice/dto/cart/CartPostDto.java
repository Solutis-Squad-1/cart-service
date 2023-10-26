package br.com.solutis.squad1.cartservice.dto.cart;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CartPostDto(

        @NotNull
        Long userId,

        @NotNull
        List<Long> productsIds
) {
}
