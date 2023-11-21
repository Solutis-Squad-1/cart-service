package br.com.solutis.squad1.cartservice.service;


import br.com.solutis.squad1.cartservice.dto.cart.CartResponseDto;
import br.com.solutis.squad1.cartservice.mapper.CartMapper;
import br.com.solutis.squad1.cartservice.model.build.CartBuilder;
import br.com.solutis.squad1.cartservice.model.entity.Cart;
import br.com.solutis.squad1.cartservice.model.repository.CartRepository;
import br.com.solutis.squad1.cartservice.model.repository.CartRepositoryCustom;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private OrderItemService orderItemService;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartRepositoryCustom cartRepositoryCustom;
    @Mock
    private CartMapper mapper;


    @Test
    @DisplayName("Returns a list of carts")
    @Transactional
    public void findAll_ShouldReturnListOfCarts() {

        Cart cart = createCart();
        Page<Cart> page = new PageImpl<>(List.of(cart));

        when(cartRepositoryCustom.findAll(any(Pageable.class))).thenReturn(page);

        Page<CartResponseDto> result = cartService.findAll(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    @DisplayName("Returns an empty cart")
    void findAll_ShouldReturnAnEmptyListOfCarts() {
        Pageable pageable = PageRequest.of(0, 10);

        when(cartRepositoryCustom.findAll(pageable)).thenReturn(new PageImpl<>(Collections.EMPTY_LIST, pageable, 0));
        Page<CartResponseDto> response = cartService.findAll(pageable);

        assertEquals(0, response.getContent().size());
        verify(cartRepositoryCustom, times(1)).findAll(pageable);
    }
    @Test
    @DisplayName("Return CartResponseDto if cart is found")
    void findById_ShouldReturnCart() {
        Cart cart = createCart();
        List<Long> cartsId = new ArrayList<>();
        CartResponseDto cartResponseDto = new CartResponseDto(cart.getId(), cart.getUserId(), new ArrayList<>());

        when(cartRepositoryCustom.findById(cart.getId())).thenReturn(cart);
        when(orderItemService.findByCart(cart)).thenReturn(cartsId);
        when(mapper.toResponseDto(cart, cartsId)).thenReturn(cartResponseDto);

        CartResponseDto response = cartService.findById(cart.getId());

        assertNotNull(response);
        assertEquals(cartResponseDto, response);
        verify(cartRepositoryCustom, times(1)).findById(cart.getId());
        verify(orderItemService, times(1)).findByCart(cart);
        verify(mapper, times(1)).toResponseDto(cart, cartsId);
    }

    @Test
    @DisplayName("Throws EntityNotFoundException if cart is not found")
    void findById_WithInvalidOrderId_ShouldThrowEntityNotFoundException() {
        Long id = 999L;

        when(cartRepositoryCustom.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> cartService.findById(id));
    }

    @Test
    @DisplayName("Should delete cart")
    void delete_WithValidId_ShouldDeleteCart() {
        Long id = 1L;

        Cart cart = mock(Cart.class);
        when(cartRepositoryCustom.findById(id)).thenReturn(cart);

        cartService.delete(id);

        verify(cart).delete();
        verify(cartRepository).save(cart);
    }

    @Test
    @DisplayName("Throws EntityNotFoundException if cart is not found")
    void delete_WithInvalidId_ShouldThrowException() {
        Long id = 999L;

        when(cartRepositoryCustom.findById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> cartService.delete(id));
    }

    private Cart createCart() {
        CartBuilder build = new CartBuilder();

        return build
                .setId(1L)
                .setUserId(1L)
                .setItems(new HashSet<>())
                .setDeleted(false)
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(null)
                .setDeletedAt(null)
                .build();
    }
}
