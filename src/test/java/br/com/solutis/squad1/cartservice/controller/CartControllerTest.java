package br.com.solutis.squad1.cartservice.controller;

import br.com.solutis.squad1.cartservice.dto.cart.CartPostDto;
import br.com.solutis.squad1.cartservice.dto.cart.CartResponseDto;
import br.com.solutis.squad1.cartservice.model.build.CartBuilder;
import br.com.solutis.squad1.cartservice.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    @DisplayName("\"Returns 4xx Client Error when attempting to access cart without find authority")
    @WithAnonymousUser
    void findAll_ShouldReturnClienteError() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        List<CartResponseDto> listCarts = List.of(createCartResponseDto(), createCartResponseDto());
        Page<CartResponseDto> carts = new PageImpl<>(listCarts);

        when(service.findAll(pageable)).thenReturn(carts);

        mvc.perform(get("/api/v1/carts"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Returns a order by id")
    @WithMockUser
    void findById_ShouldReturnCartById() throws Exception {
        Long cartId = 1L;

        mvc.perform(get("/api/v1/carts/" + cartId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("\"Returns 4xx Client Error when attempting to access cart without find authority")
    @WithAnonymousUser
    void findById_ShouldReturnClienteError() throws Exception {
        Long cartId = 1L;

        mvc.perform(get("/api/v1/carts/" + cartId))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Should return HTTP 201 Created when creating a new cart")
    @WithMockUser(authorities = "cart:create")
    void save_ShouldReturnCreateStatus() throws Exception {
        CartPostDto dto = createCartPostDto();

        mvc.perform(post("/api/v1/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should return HTTP 403 Forbidden if user lacks save authority")
    @WithAnonymousUser
    void save_ShouldReturnForbiddenStatus() throws Exception {
        CartPostDto dto = createCartPostDto();

        mvc.perform(post("/api/v1/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Returns 204 after successful deletion")
    @WithMockUser(authorities = "cart:delete")
    void delete_ShouldReturnNoContentStatus() throws Exception {
        Long cartId = 1L;

        mvc.perform(delete("/api/v1/carts/" + cartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Returns 404 if ID is missing in the URL")
    @WithMockUser(authorities = "cart:delete")
    void delete_ShouldReturnBadRequestStatus() throws Exception {
        mvc.perform(delete("/api/v1/carts/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private CartResponseDto createCartResponseDto() {
        CartBuilder builder = new CartBuilder();

        return builder
                .setId(1L)
                .setUserId(1L)
                .setProductsIds(List.of(1L, 2L))
                .buildResponseDto();
    }

    private CartPostDto createCartPostDto() {
        CartBuilder builder = new CartBuilder();

        return builder
                .setUserId(1L)
                .setProductsIds(List.of(1L, 2L))
                .buildPostDto();
    }
}
