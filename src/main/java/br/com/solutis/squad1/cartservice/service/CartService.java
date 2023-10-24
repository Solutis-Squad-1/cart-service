package br.com.solutis.squad1.cartservice.service;

import br.com.solutis.squad1.cartservice.mapper.CartMapper;
import br.com.solutis.squad1.cartservice.model.repository.CartRepository;
import br.com.solutis.squad1.cartservice.model.repository.Repository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper mapper;

//    public Page<CartResponseDto> findAll(Pageable pageable) {
//       return cartRepository.
//    }

   /*public CartResponseDto findById(Long id){
        try {
            Cart cart = cartRepository.findByIdAndDeletedIsFalse(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            return mapper.toResponseDto(product);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }*/














//    public CartResponseDto findByUserIdWithProducts(Long id) {
//        // Função para recuperar o carrinho do banco de dados
//        Cart cart = cartRepository.findByUserId(id);
//
//        // Função para pegar todos os produtos do carrinho
//        List<Product> products = cartRepository.findProducts(cart);

}
