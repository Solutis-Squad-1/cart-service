package br.com.solutis.squad1.cartservice.model.repository;

import br.com.solutis.squad1.cartservice.model.entity.Cart;
import br.com.solutis.squad1.cartservice.model.entity.OrderItem;
import br.com.solutis.squad1.cartservice.model.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class OrderItemRepository {

    @PersistenceContext
    private EntityManager em;

    public OrderItem save(OrderItem orderItem){
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

    public Page<OrderItem> findAll(Pageable pageable) {
        String jpql = "SELECT oi FROM OrderItem oi";

        List<OrderItem> resultList = em.createQuery(jpql, OrderItem.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(resultList);
    }

    public OrderItem findById(Long id) {
        String jpql = "SELECT oi FROM OrderItem oi WHERE oi.id = :id";

        return em.createQuery(jpql, OrderItem.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Long> findByCart(Cart cart) {
        String jpql = "SELECT oi.product.id FROM OrderItem oi WHERE oi.cart = :cart";

        return em.createQuery(jpql, Long.class)
                .setParameter("cart", cart)
                .getResultList();
    }
}
