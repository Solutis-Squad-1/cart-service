package br.com.solutis.squad1.cartservice.service;

import br.com.solutis.squad1.cartservice.dto.cart.CartPostDto;
import br.com.solutis.squad1.cartservice.dto.cart.CartPutDto;
import br.com.solutis.squad1.cartservice.dto.cart.CartResponseDto;
import br.com.solutis.squad1.cartservice.dto.product.ProductDetailsDto;
import br.com.solutis.squad1.cartservice.dto.product.ProductResponseDto;
import br.com.solutis.squad1.cartservice.http.CatalogHttpClient;
import br.com.solutis.squad1.cartservice.mapper.CartMapper;
import br.com.solutis.squad1.cartservice.model.entity.Cart;
import br.com.solutis.squad1.cartservice.model.entity.Product;
import br.com.solutis.squad1.cartservice.model.repository.CartRepository;
import br.com.solutis.squad1.cartservice.model.repository.CartRepositoryCustom;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepositoryCustom cartRepositoryCustom;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final CartMapper mapper;
    private final CatalogHttpClient catalogHttpClient;

    /**
     * Find all carts
     * @param pageable
     * @return Page<CartResponseDto>
     */
    public Page<CartResponseDto> findAll(Pageable pageable) {
        try {
            return cartRepositoryCustom.findAll(pageable).map(carts -> mapper.toResponseDto(carts, orderItemService.findByCart(carts)));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Find cart by id
     * @param id
     * @return CartResponseDto
     */
    public CartResponseDto findById(Long id) {
        try {
            Cart cart = cartRepositoryCustom.findById(id);
            List<Long> cartsId = orderItemService.findByCart(cart);

            return mapper.toResponseDto(cart, cartsId);
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Cart not found");
        }
    }

    /**
     * Find products by user and not deleted
     * @param userId
     * @return ProductDetailsDto
     */
    public Page<ProductDetailsDto> findProductsByUserAndNotDeleted(Long userId) {
        try {
            List<Long> productsId = cartRepositoryCustom.findProductsByUserAndNotDeleted(userId);
            Map<Long, Integer> productsMap = cartRepositoryCustom.findProductsAndQuantityByUserId(userId);

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

    /**
     * Save cart
     * @param cartPostDto
     * @return CartResponseDto
     */
    public CartResponseDto save(CartPostDto cartPostDto) {
        try {
            Cart cart = mapper.postDtoToEntity(cartPostDto);

            Set<Product> products = productService.findAllById(cartPostDto.productsIds());
            if (products.isEmpty()) {
                throw new EntityNotFoundException("Product not found");
            }

            cart = cartRepository.save(cart);
            orderItemService.updateProducts(cart, products);

            return new CartResponseDto(cart, orderItemService.findByCart(cart));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Update cart
     * @param id
     * @param cartPutDto
     * @return CartResponseDto
     */
    public CartResponseDto update(Long id, CartPutDto cartPutDto) {
        try {
            Cart cart = cartRepositoryCustom.findById(id);
            cart.update(mapper.putDtoToEntity(cartPutDto));
            cartRepository.save(cart);

            if (cartPutDto.productsIds() != null) {
                Set<Product> products = productService.findAllById(cartPutDto.productsIds());
                orderItemService.updateProducts(cart, products);
            }

            return mapper.toResponseDto(cart, orderItemService.findByCart(cart));
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Delete cart
     * @param id
     */
    public void delete(Long id) {
        try {
            Cart cart = cartRepositoryCustom.findById(id);
            cart.delete();
            cartRepository.save(cart);
        } catch (Exception e) {
            throw e;
        }
    }
}
