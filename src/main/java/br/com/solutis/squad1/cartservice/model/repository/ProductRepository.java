package br.com.solutis.squad1.cartservice.model.repository;

import br.com.solutis.squad1.cartservice.model.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager em;

    public Set<Product> findAllById(List<Long> ids) {
        String jpql = "SELECT p FROM Product p WHERE p.id IN :ids";

        return new HashSet<>(em.createQuery(jpql, Product.class)
                .setParameter("ids", ids)
                .getResultList());
    }
}
