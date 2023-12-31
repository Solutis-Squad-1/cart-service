package br.com.solutis.squad1.cartservice.controller;

import br.com.solutis.squad1.cartservice.dto.cart.CartPostDto;
import br.com.solutis.squad1.cartservice.dto.cart.CartPutDto;
import br.com.solutis.squad1.cartservice.dto.cart.CartResponseDto;
import br.com.solutis.squad1.cartservice.dto.product.ProductDetailsDto;
import br.com.solutis.squad1.cartservice.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Find all carts.
     *
     * @param pageable
     * @return Page<CartResponseDto>
     */
    @Operation(summary = "Find all carts")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<CartResponseDto> findAll(
            Pageable pageable
    ) {
        return cartService.findAll(pageable);
    }

    /**
     * Find cart by id.
     *
     * @param id
     * @return CartResponseDto
     */
    @Operation(summary = "Find cart by id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('cart:read')")
    public CartResponseDto finById(
            @PathVariable Long id
    ) {
        return cartService.findById(id);
    }

    /**
     * Find products by user id
     *
     * @param userId
     * @return Page<ProductDetailsDto>
     */
    @Operation
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('cart:read')")
    public Page<ProductDetailsDto> findProductsByUserAndNotDeleted(
            @PathVariable Long userId
    ) {
        return cartService.findProductsByUserAndNotDeleted(userId);
    }

    /**
     * Create a cart.
     *
     * @param cartPostDto
     * @return CartResponseDto
     */
    @Operation(summary = "Create a cart")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('cart:create')")
    public CartResponseDto save(
            @RequestBody CartPostDto cartPostDto
    ) {
        return cartService.save(cartPostDto);
    }

    /**
     * Update a cart.
     *
     * @param id
     * @param cartPutDto
     * @return CartResponseDto
     */
    @Operation(summary = "Update a cart")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('cart:update:status')")
    public CartResponseDto update(
            @PathVariable Long id,
            @RequestBody CartPutDto cartPutDto
    ) {
        return cartService.update(id, cartPutDto);
    }

    /**
     * Delete a cart.
     *
     * @param id
     */
    @Operation(summary = "Delete a cart")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('cart:delete')")
    public void delete(
            @PathVariable Long id
    ) {
        cartService.delete(id);
    }
}
