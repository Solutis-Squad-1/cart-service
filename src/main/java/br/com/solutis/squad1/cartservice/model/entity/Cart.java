package br.com.solutis.squad1.cartservice.model.entity;

import br.com.solutis.squad1.cartservice.dto.cart.CartPutDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user", unique = true, nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "cart")
    private Set<OrderItem> items = new HashSet<>();

    @Column(nullable = false)
    private boolean deleted = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Cart(Cart cart) {
        this.id = cart.id;
        this.userId = cart.userId;
        this.deleted = cart.deleted;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void delete() {
        deleted = true;
        deletedAt = LocalDateTime.now();
    }

    public void update(Cart cart){
        if (cart.getUserId() != null) setUserId(cart.getUserId());
    }
}
