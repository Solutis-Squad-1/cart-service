package br.com.solutis.squad1.cartservice.controller;

import br.com.solutis.squad1.cartservice.dto.cart.CartResponseDto;
import br.com.solutis.squad1.cartservice.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
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
    @WithMockUser(authorities = "ROLE_ADMIN")
    void findAll_ShouldReturnListOfOrders() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        List<CartResponseDto> listCarts = List.of(createCartResponseDto(), createCartResponseDto());
        Page<CartResponseDto> carts = new PageImpl<>(listCarts);

        when(service.findAll(pageable)).thenReturn(carts);

        mvc.perform(get("/api/v1/carts"))
                .andExpect(status().isOk());
    }

    private CartResponseDto createCartResponseDto() {
        return new CartResponseDto(
                1L,
                1L,
                List.of(1L, 2L)
        );

    }
}
