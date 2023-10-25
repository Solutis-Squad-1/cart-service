package br.com.solutis.squad1.cartservice.model.repository;

import br.com.solutis.squad1.cartservice.model.entity.Cart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartRepository {

    @PersistenceContext
    private EntityManager em;

    public Page<Cart> findAll(Pageable pageable){
        String jpql = "SELECT c FROM Cart c WHERE c.deleted = false";

        List<Cart> resultList = em.createQuery(jpql, Cart.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(resultList);
    }

    public Cart findById(Long id) {
        String jpql = "SELECT c FROM Cart c WHERE c.deleted = false AND c.id = :id";
        return em.createQuery(jpql, Cart.class).setParameter("id", id).getSingleResult();
    }

    public List<Long> findProductsByUserAndNotDeleted(Long userId){
        String jpql = "SELECT oi.product.id FROM Cart c JOIN c.items oi WHERE c.userId = :userId AND c.deleted = false";
        return em.createQuery(jpql, Long.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Map<Long, Integer> findProductsAndQuantityByUserId(Long userId){
        String jpql = "SELECT oi.product.id, oi.product.quantity FROM Cart c JOIN c.items oi WHERE c.userId = :userId AND c.deleted = false";

        List<Object[]> results = em.createQuery(jpql, Object[].class)
                .setParameter("userId", userId)
                .getResultList();

        Map<Long, Integer> products = new HashMap<>();

        for (Object[] result : results) {
            Long productId = (Long) result[0];
            Integer quantity = (Integer) result[1];
            products.put(productId, quantity);
        }

        return products;
    }

    public Cart save(Cart cart){
        em.persist(cart);
        return em.find(Cart.class, cart.getId());
    }

    public Cart update(Cart cart){
        em.merge(cart);
        return em.find(Cart.class, cart.getId());
    }

    public void delete(Cart cart) {
        em.merge(cart);
    }
}
