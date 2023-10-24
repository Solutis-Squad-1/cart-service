package br.com.solutis.squad1.cartservice.controller;

import br.com.solutis.squad1.cartservice.dto.CartResponseDto;
import br.com.solutis.squad1.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private CartService service;

    @GetMapping
    public Page<CartResponseDto> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }
/*
    @GetMapping("/{userId}")
    public CartResponseDto findByUserId(@PathVariable Long userId) {
        return service.findByUserIdWithProducts(userId);
    }*/
}
