package br.com.solutis.squad1.cartservice.controller;

import br.com.solutis.squad1.cartservice.dto.cart.CartResponseDto;
import br.com.solutis.squad1.cartservice.model.build.CartBuilder;
import br.com.solutis.squad1.cartservice.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartService service;

    @Test
    @DisplayName("Returns a list of carts")
    @WithMockUser
    void findAll_ShouldReturnListOfOrders() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        List<CartResponseDto> listCarts = List.of(createCartResponseDto(), createCartResponseDto());
        Page<CartResponseDto> carts = new PageImpl<>(listCarts);

        when(service.findAll(pageable)).thenReturn(carts);

        mvc.perform(get("/api/v1/carts"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Returns 403 if user lacks find authority")
    @WithAnonymousUser
    void findAll_ShouldReturnForbiddenStatus() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        List<CartResponseDto> listCarts = List.of(createCartResponseDto(), createCartResponseDto());
        Page<CartResponseDto> carts = new PageImpl<>(listCarts);

        when(service.findAll(pageable)).thenReturn(carts);

        mvc.perform(get("/api/v1/carts"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Returns a order by id")
    @WithMockUser
    void findById_ShouldReturnCartById() throws Exception {
        Long cartId = 1L;

        mvc.perform(get("/api/v1/carts/" + cartId))
                .andExpect(status().isOk());
    }


    private CartResponseDto createCartResponseDto() {
        CartBuilder builder = new CartBuilder();

        return builder
                .setId(1L)
                .setUserId(1L)
                .setProductsIds(List.of(1L, 2L))
                .buildResponseDto();
    }
}
