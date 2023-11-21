package br.com.solutis.squad1.cartservice.model.build;

import br.com.solutis.squad1.cartservice.dto.product.CategoryResponseDto;
import br.com.solutis.squad1.cartservice.dto.product.ImageResponseDto;
import br.com.solutis.squad1.cartservice.dto.product.ProductResponseDto;
import br.com.solutis.squad1.cartservice.model.entity.OrderItem;
import br.com.solutis.squad1.cartservice.model.entity.Product;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductBuilder {

    private Long id;
    private Set<OrderItem> items = new HashSet<>();
    String name;
    String description;
    BigDecimal price;
    Long sellerId;
    List<CategoryResponseDto> categories;
    ImageResponseDto image;

    public ProductBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setItems(Set<OrderItem> items) {
        this.items = items;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setSellerId(Long sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public ProductBuilder setCategories(List<CategoryResponseDto> categories) {
        this.categories = categories;
        return this;
    }

    public ProductBuilder setImage(ImageResponseDto image) {
        this.image = image;
        return this;
    }

    public Product build() {
      return new Product(id, items);
    }

    public ProductResponseDto buildResponseDto() {
      return new ProductResponseDto(id, name, description, price, sellerId, categories, image);
    }
}
