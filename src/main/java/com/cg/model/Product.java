package com.cg.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
@Accessors(chain = true)
public class Product extends BaseEntity {
    @Id
    private Long id;

    @Column
    private String title;

    @Column(precision = 10, scale = 0, nullable = false)
    private BigDecimal price;
    private String unit;


    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id",nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @OneToOne
    @JoinColumn(name = "product_avatar_id",referencedColumnName = "id" ,  nullable = false)
    private ProductAvatar productAvatar;

}
