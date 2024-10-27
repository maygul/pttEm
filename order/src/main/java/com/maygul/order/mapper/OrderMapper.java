package com.maygul.order.mapper;

import com.maygul.order.persistence.entity.OrderEntity;
import com.maygul.order.service.dto.OrderDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface OrderMapper {


    OrderDto toDto(OrderEntity orderEntity);

    List<OrderDto> toOrderDtos(List<OrderEntity> orderEntities);
}
