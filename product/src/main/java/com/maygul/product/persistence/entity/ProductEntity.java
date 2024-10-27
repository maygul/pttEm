package com.maygul.product.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "default_gen", sequenceName = "SEQ_PRODUCT", allocationSize = 1)
public class ProductEntity {

    @Id
    @GeneratedValue(generator = "default_gen", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long stockCount;
}
