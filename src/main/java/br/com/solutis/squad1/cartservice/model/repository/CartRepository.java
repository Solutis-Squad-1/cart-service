package br.com.solutis.squad1.cartservice.model.repository;

import br.com.solutis.squad1.cartservice.model.entity.Cart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        return em.find(Cart.class, id);
    }

    public List<Long> findProductsByUserAndNotDeleted(Long userId){
        String jpql = "SELECT oi.product.id FROM Cart c JOIN c.items oi WHERE c.userId = :userId AND c.deleted = false";
        return em.createQuery(jpql, Long.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void delete(Long id) {
        String jpql = "UPDATE Cart c SET c.deleted = true WHERE c.id = :id";
        em.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();
    }

    public Cart save(Cart cart){
        em.persist(cart);
        return em.find(Cart.class, cart.getId());
    }

    //Finalizar
    public Cart update(Cart cart){
        return null;
    }
}
