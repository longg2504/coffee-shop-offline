package com.cg.service.order;


import com.cg.model.Order;
<<<<<<< HEAD
import com.cg.model.TableOrder;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IOrderService extends IGeneralService<Order,Long> {
    Optional<Order> findByTableId(Long tableId);

    List<Order> findByTableOrderAndPaid(TableOrder tableOrder, Boolean paid);
=======
import com.cg.service.IGeneralService;

public interface IOrderService extends IGeneralService<Order,Long> {
>>>>>>> dd38c27 (upload)
}
