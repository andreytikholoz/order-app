package com.company.orderapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ORDERED_PRODUCT_ENTITY_T01")
public class OrderedProductEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "UUID")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "ORDER_ID")
    private OrderEntity order;

    @ManyToOne()
    @JoinColumn(name = "PRODUCT_ID")
    private ProductEntity product;

    @Column(name = "QUANTITY")
    private int quantity;
}
