package br.com.solutis.squad1.cartservice.dto.cart;

import java.util.List;

/**
 * DTO for updating a cart.
 */
public record CartPutDto(
        Long userId,
        List<Long> productsIds
) {
}
