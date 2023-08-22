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
@Table(name = "orders")
@Accessors(chain = true)
public class Order extends BaseEntity {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "staff_id",referencedColumnName = "id",nullable = false)
    private Staff staff;

    @OneToOne
    @JoinColumn(name = "table_order_id",referencedColumnName = "id",nullable = false)
    private TableOrder tableOrder;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    private Boolean paid;

}
