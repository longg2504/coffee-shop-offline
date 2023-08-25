package com.cg.model.dto.bill;
import com.cg.model.Order;
import com.cg.model.TableOrder;
import com.cg.model.dto.order.OrderDTO;
import com.cg.model.dto.tableOrder.TableOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class BillCreResDTO {
    private Long id;
    private TableOrderDTO table;
    private BigDecimal totalAmount;
    private Long orderId;
    private Boolean paid;

}
