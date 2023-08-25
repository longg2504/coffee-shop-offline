package com.cg.service.order;


import com.cg.model.*;
import com.cg.model.dto.order.*;
import com.cg.model.dto.orderDetail.OrderDetailCreResDTO;
import com.cg.model.dto.orderDetail.OrderDetailUpResDTO;
import com.cg.service.IGeneralService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IOrderService extends IGeneralService<Order,Long> {
    Optional<Order> findByTableId(Long tableId);

    List<Order> findByTableOrderAndPaid(TableOrder tableOrder, Boolean paid);


    OrderDetailCreResDTO creOrder(OrderCreReqDTO orderCreReqDTO, TableOrder tableOrder, User user);

    OrderDetailUpResDTO upOrderDetail(OrderUpReqDTO orderUpReqDTO, Order order, Product product, User user);

    OrderUpChangeToTableResDTO changeToTable(OrderUpChangeToTableReqDTO orderUpChangeToTableReqDTO, User user);

    BigDecimal getOrderTotalAmount(Long orderId);

}
