package com.cg.model;

import com.cg.model.dto.bill.BillCreResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bills")
@Accessors(chain = true)
public class Bill extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @OneToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id",nullable = false)
    private Order order;

    public BillCreResDTO toBillResDTO() {
        return new BillCreResDTO()
                .setId(null)
                .setTable(order.getTableOrder().toTableOrderDTO())
                .setTotalAmount(order.getTotalAmount())
                .setPaid(getOrder().getPaid())
                .setOrderId(getOrder().getId())
                ;
    }
}
