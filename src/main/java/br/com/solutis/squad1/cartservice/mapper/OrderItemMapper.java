package br.com.solutis.squad1.cartservice.mapper;

import br.com.solutis.squad1.cartservice.dto.orderItem.OrderItemResponseDto;
import br.com.solutis.squad1.cartservice.model.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemResponseDto toResponseDto(OrderItem orderItem);
}
