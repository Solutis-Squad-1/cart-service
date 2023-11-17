package br.com.solutis.squad1.cartservice.model.repository;

import br.com.solutis.squad1.cartservice.model.entity.Cart;
import br.com.solutis.squad1.cartservice.model.entity.OrderItem;
import br.com.solutis.squad1.cartservice.model.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public OrderItem save(OrderItem orderItem) {
        em.persist(orderItem);
        return em.find(OrderItem.class, orderItem.getId());
    }

    public OrderItem findIdByCartAndProduct(Cart cart, Product product) {
        try {
            String jpql = "SELECT oi FROM OrderItem oi WHERE oi.cart = :cart AND oi.product = :product";
            return em.createQuery(jpql, OrderItem.class)
                    .setParameter("cart", cart)
                    .setParameter("product", product)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Long> findByCart(Cart cart) {
        String jpql = "SELECT oi.product.id FROM OrderItem oi WHERE oi.cart = :cart";

        return em.createQuery(jpql, Long.class)
                .setParameter("cart", cart)
                .getResultList();
    }
}
