package br.com.solutis.squad1.cartservice.controller;

import br.com.solutis.squad1.cartservice.dto.cart.CartPostDto;
import br.com.solutis.squad1.cartservice.dto.cart.CartPutDto;
import br.com.solutis.squad1.cartservice.dto.cart.CartResponseDto;
import br.com.solutis.squad1.cartservice.dto.product.ProductDetailsDto;
import br.com.solutis.squad1.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public Page<CartResponseDto> findAll(
            Pageable pageable
    ){
        return cartService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CartResponseDto finById(
            @PathVariable Long id
    ){
        return cartService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public Page<ProductDetailsDto> findProductsByUserAndNotDeleted(
            @PathVariable Long userId
    ) {
        return cartService.findProductsByUserAndNotDeleted(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponseDto save(
            @RequestBody CartPostDto cartPostDto
    ){
        return cartService.save(cartPostDto);
    }

    @PutMapping("/{id}")
    public CartResponseDto update(
            @PathVariable Long id,
            @RequestBody CartPutDto cartPutDto
    ){
        return cartService.update(id, cartPutDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        cartService.delete(id);
    }
}
