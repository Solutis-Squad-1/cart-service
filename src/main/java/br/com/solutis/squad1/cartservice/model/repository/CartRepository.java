package br.com.solutis.squad1.cartservice.model.repository;

import br.com.solutis.squad1.cartservice.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
