package br.com.solutis.squad1.cartservice.model.build;

import br.com.solutis.squad1.cartservice.dto.cart.CartResponseDto;
import br.com.solutis.squad1.cartservice.model.entity.Cart;
import br.com.solutis.squad1.cartservice.model.entity.OrderItem;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartBuilder {
    private Long id;
    private Long userId;
    private Set<OrderItem> items = new HashSet<>();
    private boolean deleted = false;
    private List<Long> productsIds = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public CartBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CartBuilder setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public CartBuilder setItems(Set<OrderItem> items) {
        this.items = items;
        return this;
    }

    public CartBuilder setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public CartBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CartBuilder setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public CartBuilder setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public CartBuilder setProductsIds(List<Long> productsIds) {
        this.productsIds = productsIds;
        return this;
    }

    public Cart build() {
        return new Cart(id, userId, items, deleted, createdAt, updatedAt, deletedAt);
    }

    public CartResponseDto buildResponseDto() {
        return new CartResponseDto(id, userId, productsIds);
    }
}
