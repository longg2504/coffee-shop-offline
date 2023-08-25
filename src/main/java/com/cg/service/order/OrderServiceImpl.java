package com.cg.service.order;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.order.*;
import com.cg.model.dto.orderDetail.OrderDetailCreResDTO;
import com.cg.model.dto.orderDetail.OrderDetailDTO;
import com.cg.model.dto.orderDetail.OrderDetailProductUpResDTO;
import com.cg.model.dto.orderDetail.OrderDetailUpResDTO;
import com.cg.model.enums.ETableStatus;
import com.cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private TableOrderRepository tableOrderRepository;

    @Autowired
    private ProductRepository productRepository;

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
        Order order = new Order();
        Optional<Staff> optionalStaff = staffRepository.findByUserAndDeletedIsFalse(user);
        order.setStaff(optionalStaff.get());
        order.setTableOrder(tableOrder);
        order.setTotalAmount(BigDecimal.ZERO);
        order.setPaid(false);
        orderRepository.save(order);

        tableOrder.setStatus(ETableStatus.BUSY);
        tableOrderRepository.save(tableOrder);

        Product product = productRepository.findById(orderCreReqDTO.getProductId()).orElseThrow(() -> {
            throw new DataInputException("Sản phẩm này không tồn tại vui lòng xem lại");
        });

        OrderDetail orderDetail = new OrderDetail();

        Long quantity = orderCreReqDTO.getQuantity();
        BigDecimal price = product.getPrice();
        BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity));

        orderDetail.setProduct(product);
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(price);
        orderDetail.setAmount(amount);
        orderDetail.setNote(orderCreReqDTO.getNote());
        orderDetail.setOrder(order);

        orderDetailRepository.save(orderDetail);

        order.setTotalAmount(amount);
        orderRepository.save(order);

        OrderDetailCreResDTO orderDetailCreResDTO = new OrderDetailCreResDTO();
        orderDetailCreResDTO.setOrderDetailId(orderDetail.getId());
        orderDetailCreResDTO.setTable(tableOrder.toTableOrderResDTO());
        orderDetailCreResDTO.setProductId(product.getId());
        orderDetailCreResDTO.setTitle(product.getTitle());
        orderDetailCreResDTO.setPrice(price);
        orderDetailCreResDTO.setQuantity(quantity);
        orderDetailCreResDTO.setAmount(amount);
        orderDetailCreResDTO.setNote(orderDetail.getNote());
        orderDetailCreResDTO.setTotalAmount(amount);
        orderDetailCreResDTO.setAvatar(product.getProductAvatar().toProductAvatarDTO());

        return orderDetailCreResDTO;
    }

    @Override
    public OrderDetailUpResDTO upOrderDetail(OrderUpReqDTO orderUpReqDTO, Order order, Product product, User user) {
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder(order);
        OrderDetail orderDetail = new OrderDetail();

        if (orderDetails.size() == 0) {
            throw new DataInputException("Hoá đơn bàn này chưa có mặt hàng nào, vui lòng liên hệ ADMIN để kiểm tra lại dữ liệu");
        }

        Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findByProductIdAndOrderIdAndNote(orderUpReqDTO.getProductId(), order.getId(), orderUpReqDTO.getNote());

        if (orderDetailOptional.isEmpty()) {
            Long quantity = orderUpReqDTO.getQuantity();
            BigDecimal price = product.getPrice();
            BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity));

            orderDetail.setProduct(product);
            orderDetail.setOrder(order);
            orderDetail.setPrice(product.getPrice());
            orderDetail.setQuantity(quantity);
            orderDetail.setAmount(amount);
            orderDetail.setNote(orderUpReqDTO.getNote());
            orderDetailRepository.save(orderDetail);

            BigDecimal newTotalAmount = getOrderTotalAmount(order.getId());
            order.setTotalAmount(newTotalAmount);
            orderRepository.save(order);
        }
        else {
            orderDetail = orderDetailOptional.get();
            long newQuantity = orderDetail.getQuantity() + orderUpReqDTO.getQuantity();
            BigDecimal price = orderDetail.getPrice();
            BigDecimal newAmount = price.multiply(BigDecimal.valueOf(newQuantity));
            orderDetail.setQuantity(newQuantity);
            orderDetail.setAmount(newAmount);
            orderDetailRepository.save(orderDetail);

            BigDecimal newTotalAmount = getOrderTotalAmount(order.getId());
            order.setTotalAmount(newTotalAmount);
            orderRepository.save(order);
        }

        List<OrderDetailProductUpResDTO> newOrderDetails = orderDetailRepository.findAllOrderDetailProductUpResDTO(order.getId());

        OrderDetailUpResDTO orderDetailUpResDTO = new OrderDetailUpResDTO();
        orderDetailUpResDTO.setTable(order.getTableOrder().toTableOrderResDTO());
        orderDetailUpResDTO.setProducts(newOrderDetails);
        orderDetailUpResDTO.setTotalAmount(order.getTotalAmount());

        return orderDetailUpResDTO;

    }

    @Override
    public OrderUpChangeToTableResDTO changeToTable(OrderUpChangeToTableReqDTO orderUpChangeToTableReqDTO, User user) {
        Order orderBusy = orderRepository.findByTableId(orderUpChangeToTableReqDTO.getTableIdBusy()).get();

        TableOrder emptyTable = tableOrderRepository.findById(orderUpChangeToTableReqDTO.getTableIdEmpty()).get();
        emptyTable.setStatus(ETableStatus.BUSY);
        tableOrderRepository.save(emptyTable);

        orderBusy.setTableOrder(emptyTable);
        orderRepository.save(orderBusy);

        TableOrder busyTable = tableOrderRepository.findById(orderUpChangeToTableReqDTO.getTableIdBusy()).get();
        busyTable.setStatus(ETableStatus.EMPTY);
        tableOrderRepository.save(busyTable);

        List<OrderDetailProductUpResDTO> newOrderDetails = orderDetailRepository.findAllOrderDetailProductUpResDTO(orderBusy.getId());

        OrderUpChangeToTableResDTO orderUpChangeToTableResDTO = new OrderUpChangeToTableResDTO();
        orderUpChangeToTableResDTO.setTable(orderBusy.getTableOrder().toTableOrderResDTO());
        orderUpChangeToTableResDTO.setTotalAmount(orderBusy.getTotalAmount());
        orderUpChangeToTableResDTO.setProducts(newOrderDetails);


        return orderUpChangeToTableResDTO;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public void deleteById(Long id) {

    }

    public BigDecimal getOrderTotalAmount(Long orderId) {
        return orderRepository.getOrderTotalAmount(orderId);
    }

    public void deleteOrderDetailById(Long orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }


}
