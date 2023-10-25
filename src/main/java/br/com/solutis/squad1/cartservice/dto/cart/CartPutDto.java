package br.com.solutis.squad1.cartservice.dto.cart;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CartPutDto(

        Long userId,
        List<Long> products) {
}
