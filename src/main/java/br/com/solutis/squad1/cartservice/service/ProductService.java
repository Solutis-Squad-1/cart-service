package br.com.solutis.squad1.cartservice.service;

import br.com.solutis.squad1.cartservice.mapper.ProductMapper;
import br.com.solutis.squad1.cartservice.model.entity.Product;
import br.com.solutis.squad1.cartservice.model.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public Set<Product> findAllById(List<Long> productsId) {
        try {
            return productRepository.findAllById(productsId);
        } catch (Exception e) {
            throw e;
        }
    }
}
