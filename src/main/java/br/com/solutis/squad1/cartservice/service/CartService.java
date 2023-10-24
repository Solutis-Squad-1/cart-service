package br.com.solutis.squad1.cartservice.service;

import br.com.solutis.squad1.cartservice.dto.CartResponseDto;
import br.com.solutis.squad1.cartservice.mapper.CartMapper;
import br.com.solutis.squad1.cartservice.model.entity.Cart;
import br.com.solutis.squad1.cartservice.model.entity.Product;
import br.com.solutis.squad1.cartservice.model.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper mapper;


    public Page<CartResponseDto> findAll(Pageable pageable) {
        return null;
    }
/*
    public CartResponseDto findByUserIdWithProducts(Long id) {
        // Função para recuperar o carrinho do banco de dados
        Cart cart = cartRepository.findByUserId(id);

        // Função para pegar todos os produtos do carrinho
        List<Product> products = cartRepository.findProducts(cart);
    }*/

}
