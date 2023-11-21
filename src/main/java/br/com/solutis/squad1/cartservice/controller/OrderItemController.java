package br.com.solutis.squad1.cartservice.controller;

import br.com.solutis.squad1.cartservice.dto.orderItem.OrderItemResponseDto;
import br.com.solutis.squad1.cartservice.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
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

    /**
     * Find all orderItems.
     *
     * @param pageable
     * @return Page<OrderItemResponseDto>
     */
    @Operation(summary = "Find all orderItems")
    @GetMapping
    public Page<OrderItemResponseDto> findAll(
            Pageable pageable
    ) {
        return orderItemService.findAll(pageable);
    }

    /**
     * Find orderItem by id.
     *
     * @param id
     * @return OrderItemResponseDto
     */
    @Operation(summary = "Find orderItem by id")
    @GetMapping("/{id}")
    public OrderItemResponseDto finById(
            @PathVariable Long id
    ) {
        return orderItemService.findById(id);
    }
}
