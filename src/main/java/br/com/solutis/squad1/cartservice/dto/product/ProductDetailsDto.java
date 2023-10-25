package br.com.solutis.squad1.cartservice.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProductDetailsDto(
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
        ImageResponseDto image,
        @NotNull
        int quantity
) {

    public ProductDetailsDto(ProductResponseDto product, Integer quantity) {
        this(
                product.id(),
                product.name(),
                product.description(),
                product.price(),
                product.sellerId(),
                product.categories(),
                product.image(),
                quantity
        );
    }
}
