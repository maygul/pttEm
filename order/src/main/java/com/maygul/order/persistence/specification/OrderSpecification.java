package com.maygul.order.persistence.specification;

import com.maygul.order.persistence.entity.OrderEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {

    public static Specification<OrderEntity> hasUserId(Long userId) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicaList = new ArrayList<>();

            if (userId != null) {
                predicaList.add(criteriaBuilder.equal(root.get("userId"), userId));
            }

            return criteriaBuilder.and(predicaList.toArray(new Predicate[0]));
        };
    }
}
