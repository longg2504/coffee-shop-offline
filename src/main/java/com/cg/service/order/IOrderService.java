package com.cg.service.order;


import com.cg.model.Order;
import com.cg.model.TableOrder;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IOrderService extends IGeneralService<Order,Long> {
    Optional<Order> findByTableId(Long tableId);

    List<Order> findByTableOrderAndPaid(TableOrder tableOrder, Boolean paid);
<<<<<<< HEAD

=======
>>>>>>> origin/main
}
