package com.cg.service.order;

import com.cg.model.*;
import com.cg.model.dto.order.*;
import com.cg.model.dto.orderDetail.OrderDetailCreResDTO;
import com.cg.model.dto.orderDetail.OrderDetailUpResDTO;
import com.cg.model.enums.ETableStatus;
import com.cg.repository.OrderDetailRepository;
import com.cg.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<Order> findByTableId(Long tableId) {
        return orderRepository.findByTableId(tableId);
    }

    @Override
    public List<Order> findByTableOrderAndPaid(TableOrder tableOrder, Boolean paid) {
        return orderRepository.findByTableOrderAndPaid(tableOrder,paid);
    }

    @Override
    public OrderDetailCreResDTO creOrder(OrderCreReqDTO orderCreReqDTO, TableOrder tableOrder, User user) {
        return null;
    }

    @Override
    public OrderDetailUpResDTO upOrderDetail(OrderUpReqDTO orderUpReqDTO, Order order, Product product, User user) {
        return null;
    }

    @Override
    public OrderUpChangeToTableResDTO changeToTable(OrderUpChangeToTableReqDTO orderUpChangeToTableReqDTO, User user) {
        return null;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public void deleteById(Long id) {

    }

    public void deleteOrderDetailById(Long orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }


}
