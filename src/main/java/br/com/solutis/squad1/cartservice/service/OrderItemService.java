package br.com.solutis.squad1.cartservice.service;

import br.com.solutis.squad1.cartservice.dto.orderItem.OrderItemResponseDto;
import br.com.solutis.squad1.cartservice.mapper.OrderItemMapper;
import br.com.solutis.squad1.cartservice.model.entity.Cart;
import br.com.solutis.squad1.cartservice.model.entity.OrderItem;
import br.com.solutis.squad1.cartservice.model.entity.Product;
import br.com.solutis.squad1.cartservice.model.repository.OrderItemRepository;
import br.com.solutis.squad1.cartservice.model.repository.OrderItemRepositoryCustom;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepositoryCustom orderItemRepositoryCustom;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper mapper;

    /**
     * Update products
     * @param cart
     * @param products
     */
    public void updateProducts(Cart cart, Set<Product> products) {
        for (Product product : products) {
            OrderItem existingItem = orderItemRepositoryCustom.findIdByCartAndProduct(cart, product);

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + 1);
            } else {
                orderItemRepository.save(new OrderItem(cart, product, 1));
            }
        }
    }

    /**
     * Find all order items
     * @param pageable
     * @return Page<OrderItemResponseDto>
     */
    public Page<OrderItemResponseDto> findAll(Pageable pageable) {
        try {
            return orderItemRepository.findAll(pageable).map(mapper::toResponseDto);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Find order item by id
     * @param id
     * @return OrderItemResponseDto
     */
    public OrderItemResponseDto findById(Long id) {
        try {
            OrderItem orderItem = orderItemRepository.getReferenceById(id);
            return mapper.toResponseDto(orderItem);
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Order item not found");
        }
    }

    /**
     * Find order item by cart
     * @param cart
     * @return List<Long>
     */
    public List<Long> findByCart(Cart cart) {
        try {
            return orderItemRepositoryCustom.findByCart(cart);
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Order item not found");
        }
    }
}
