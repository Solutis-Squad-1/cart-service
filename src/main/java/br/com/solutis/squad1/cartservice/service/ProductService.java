package br.com.solutis.squad1.cartservice.service;

import br.com.solutis.squad1.cartservice.mapper.ProductMapper;
import br.com.solutis.squad1.cartservice.model.entity.Product;
import br.com.solutis.squad1.cartservice.model.repository.ProductRepositoryCustom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepositoryCustom productRepository;
    private final ProductMapper mapper;

    /**
     * Find all products
     * @return List<Product>
     */
    public Set<Product> findAllById(List<Long> productsId) {
        try {
            return productRepository.findAllById(productsId);
        } catch (Exception e) {
            throw e;
        }
    }
}
