package br.com.solutis.squad1.cartservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    private Long id;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> items = new HashSet<>();

    public Product(Product product) {
        this.id = product.getId();
    }
}
