package com.maygul.order.mapper;

import com.maygul.order.persistence.entity.TransactionEntity;
import com.maygul.order.service.dto.TransactionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDto toDto(TransactionEntity entity);

    TransactionEntity toEntity(TransactionDto dto);
}
