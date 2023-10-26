package br.com.solutis.squad1.cartservice.controller;

import br.com.solutis.squad1.cartservice.dto.cart.CartResponseDto;
import br.com.solutis.squad1.cartservice.dto.orderItem.OrderItemResponseDto;
import br.com.solutis.squad1.cartservice.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orderItem")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping
    public Page<OrderItemResponseDto> findAll(
            Pageable pageable
    ){
        return orderItemService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public OrderItemResponseDto finById(
            @PathVariable Long id
    ){
        return orderItemService.findById(id);
    }
}
