package br.com.solutis.squad1.cartservice.http;

import br.com.solutis.squad1.cartservice.dto.product.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Set;

@FeignClient("catalog-service")
public interface CatalogHttpClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/products/cart")
    List<ProductResponseDto> findProductsList(@RequestBody List<Long> productsId);
}
