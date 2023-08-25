package com.cg.service.orderDetail;

import com.cg.model.Order;
import com.cg.model.OrderDetail;
import com.cg.model.dto.orderDetail.OrderDetailByTableResDTO;
import com.cg.repository.OrderDetailRepository;
import com.cg.repository.OrderRepository;
import com.cg.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderDetailServiceImpl implements IOrderDetailService{
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private IOrderService orderService;
    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public Optional<OrderDetail> findByOrderDetailByIdProductAndIdOrder(Long idProduct, Long idOrder, String note) {
        return orderDetailRepository.findByProductIdAndOrderIdAndNote(idProduct, idOrder, note);
    }

    @Override
    public List<OrderDetailByTableResDTO> getOrderDetailByTableResDTO(Long orderId) {
        return orderDetailRepository.getOrderDetailByTableResDTO(orderId);
    }

    @Override
    public OrderDetail findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void delete(OrderDetail orderDetail) {
        Order order = orderDetail.getOrder();
        orderDetailRepository.delete(orderDetail);

        order.setOrderDetails(order.getOrderDetails()
                .stream().filter(e -> !Objects.equals(e.getId(), orderDetail.getId()))
                .collect(Collectors.toList()));

        if (order.getOrderDetails().size() == 0) {
            orderRepository.delete(order);
        }
        else {
            BigDecimal totalAmount = orderService.getOrderTotalAmount(order.getId());
            order.setTotalAmount(totalAmount);
            orderService.save(order);
        }
    }

    @Override
    public void deleteById(Long id) {

    }




}
