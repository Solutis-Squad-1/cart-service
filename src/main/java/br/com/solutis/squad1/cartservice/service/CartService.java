package br.com.solutis.squad1.cartservice.service;

import br.com.solutis.squad1.cartservice.dto.cart.CartPostDto;
import br.com.solutis.squad1.cartservice.dto.cart.CartPutDto;
import br.com.solutis.squad1.cartservice.dto.cart.CartResponseDto;
import br.com.solutis.squad1.cartservice.dto.product.ProductResponseDto;
import br.com.solutis.squad1.cartservice.dto.product.ProductDetailsDto;
import br.com.solutis.squad1.cartservice.http.CatalogHttpClient;
import br.com.solutis.squad1.cartservice.model.entity.Cart;
import br.com.solutis.squad1.cartservice.mapper.CartMapper;
import br.com.solutis.squad1.cartservice.model.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper mapper;
    private final CatalogHttpClient catalogHttpClient;

    public Page<CartResponseDto> findAll(Pageable pageable) {
       try{
           return cartRepository.findAll(pageable).map(mapper::toResponseDto);
       } catch (Exception e){
           throw e;
       }
    }

    public CartResponseDto findById(Long id){
        try {
            Cart cart = cartRepository.findById(id);
            return mapper.toResponseDto(cart);
        } catch (NoResultException e){
            throw new EntityNotFoundException("Cart not found");
        }
    }


   public Page<ProductDetailsDto> findProductsByUserAndNotDeleted(Long userId){
        try {
            List<Long> productsId = cartRepository.findProductsByUserAndNotDeleted(userId);
            Map<Long, Integer> productsMap = cartRepository.findProductsAndQuantityByUserId(userId);

            if (productsId.isEmpty()) {
                throw new EntityNotFoundException("Product not found");
            }

            List<ProductResponseDto> productsClient = catalogHttpClient.findProductsList(productsId);
            List<ProductDetailsDto> products = new ArrayList<>();

            for (ProductResponseDto product : productsClient) {
                products.add(new ProductDetailsDto(product, productsMap.get(product.id())));
            }

            return new PageImpl<>(products);
        } catch (Exception e) {
            throw e;
        }
    }

    public CartResponseDto save(CartPostDto cartPostDto) {
        try {
            Cart cart = mapper.postDtoToEntity(cartPostDto);
            cart = cartRepository.save(cart);

            return mapper.toResponseDto(cart);
        } catch (Exception e) {
            throw e;
        }
    }

    public CartResponseDto update(Long id, CartPutDto cartPutDto) {
        try {
            Cart cart = cartRepository.findById(id);
            cart.update(mapper.putDtoToEntity(cartPutDto));
            cartRepository.update(cart);

            return mapper.toResponseDto(cart);
        } catch (Exception e) {
            throw e;
        }
    }

    public void delete(Long id) {
        try {
            Cart cart = cartRepository.findById(id);
            cart.delete();
            cartRepository.delete(cart);
        } catch (Exception e) {
            throw e;
        }
    }
}
