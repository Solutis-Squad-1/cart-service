package br.com.solutis.squad1.cartservice.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for getting a product.
 */
public record ProductResponseDto(
        @NotNull
        Long id,
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotNull
        BigDecimal price,
        @NotNull
        Long sellerId,
        @NotEmpty
        List<CategoryResponseDto> categories,
        @NotNull
        ImageResponseDto image
) {
}
