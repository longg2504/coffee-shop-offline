package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.order.OrderCreReqDTO;
import com.cg.model.dto.order.OrderUpChangeToTableReqDTO;
import com.cg.model.dto.order.OrderUpChangeToTableResDTO;
import com.cg.model.dto.order.OrderUpReqDTO;
import com.cg.model.dto.orderDetail.OrderDetailByTableResDTO;
import com.cg.model.dto.orderDetail.OrderDetailCreResDTO;
import com.cg.model.dto.orderDetail.OrderDetailUpResDTO;
import com.cg.model.enums.ETableStatus;
import com.cg.service.order.IOrderService;
import com.cg.service.orderDetail.IOrderDetailService;
import com.cg.service.product.IProductService;
import com.cg.service.staff.IStaffService;
import com.cg.service.tableOrder.ITableOrderService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderAPI {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private ValidateUtils validateUtils;

    @Autowired
    private ITableOrderService tableOrderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

    @Autowired
    private AppUtils appUtils;


    @GetMapping("/table/{tableId}")
    public ResponseEntity<?> getOrderByTableId(@PathVariable("tableId") String tableIdStr){
        if(!validateUtils.isNumberValid(tableIdStr)){
            throw new DataInputException("Mã số bàn không hợp lệ vui lòng xem lại");
        }

        Long tableId = Long.valueOf(tableIdStr);

        Optional<Order> orderOptional = orderService.findByTableId(tableId);

        if(!orderOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<OrderDetailByTableResDTO> orderDetails = orderDetailService.getOrderDetailByTableResDTO(orderOptional.get().getId());

        if(orderDetails.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderDetails,HttpStatus.OK);
    }

    @GetMapping("/list-order-details/{tableId}")
    public ResponseEntity<?> getListOrderDetailByTableId(@PathVariable("tableId") String tableIdStr){
        if (!validateUtils.isNumberValid(tableIdStr)) {
            throw new DataInputException("Mã bàn không hợp lệ");
        }
        Long tableId = Long.valueOf(tableIdStr);

        Optional<Order> orderOptional = orderService.findByTableId(tableId);

        if (orderOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<OrderDetailByTableResDTO> orderDetails = orderDetailService.getOrderDetailByTableResDTO(orderOptional.get().getId());

        if (orderDetails.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> creOrder(@RequestBody OrderCreReqDTO orderCreReqDTO){
        String username = appUtils.getPrincipalUsername();
        Optional<User> userOptional = userService.findByName(username);

        TableOrder tableOrder = tableOrderService.findById(orderCreReqDTO.getTableId()).orElseThrow(() -> {
            throw new DataInputException("Bàn không tồn tại");
        });

        List<Order> orders = orderService.findByTableOrderAndPaid(tableOrder, false);

        if (orders.size() > 0) {
            throw new DataInputException("Bàn này đang có hoá đơn, vui lòng kiểm tra lại thông tin");
        }

        if (tableOrder.getStatus() == ETableStatus.EMPTY) {
            OrderDetailCreResDTO orderDetailCreResDTO = orderService.creOrder(orderCreReqDTO, tableOrder, userOptional.get());

            return new ResponseEntity<>(orderDetailCreResDTO, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> upOrder(@RequestBody OrderUpReqDTO orderUpReqDTO){
        String username = appUtils.getPrincipalUsername();
        Optional<User> userOptional = userService.findByName(username);

        TableOrder tableOrder = tableOrderService.findById(orderUpReqDTO.getTableId()).orElseThrow(() -> {
            throw new DataInputException("Bàn không tồn tại");
        });

        Product product = productService.findById(orderUpReqDTO.getProductId()).orElseThrow(() -> {
            throw new DataInputException("Sản phẩm không tồn tại");
        });

        List<Order> orders = orderService.findByTableOrderAndPaid(tableOrder, false);

        if (orders.size() == 0) {
            throw new DataInputException("Bàn này không có hoá đơn, vui lòng kiểm tra lại thông tin");
        }

        if (orders.size() > 1) {
            throw new DataInputException("Lỗi hệ thống, vui lòng liên hệ ADMIN để kiểm tra lại dữ liệu");
        }

        Order order = orders.get(0);

        if (tableOrder.getStatus() == ETableStatus.BUSY){
            OrderDetailUpResDTO orderDetailUpResDTO = orderService.upOrderDetail(orderUpReqDTO, order, product, userOptional.get());
            return new ResponseEntity<>(orderDetailUpResDTO ,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{orderDetailId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderDetailId) {
        OrderDetail orderDetail = orderDetailService.findById(orderDetailId).orElseThrow(() -> {
            throw new DataInputException("Vui lòng kiểm tra lại thông tin");
        });

        Long orderId = orderDetail.getOrder().getId();
        Long tableId = orderDetail.getOrder().getTableOrder().getId();

        orderDetailService.delete(orderDetail);
        List<OrderDetailByTableResDTO> orderDetails = orderDetailService.getOrderDetailByTableResDTO(orderId);
        if(orderDetails.isEmpty()){
            Optional<TableOrder> tableOrderOptional = tableOrderService.findById(tableId);
           TableOrder tableOrder = tableOrderOptional.get();
           tableOrder.setStatus(ETableStatus.EMPTY);
           tableOrderService.save(tableOrder);
           return new ResponseEntity<>(tableOrder,HttpStatus.OK);
        }
        return new ResponseEntity<>(orderDetail.getOrder().getTableOrder(), HttpStatus.OK);
    }

    @PatchMapping("/update/change-to-table")
    public ResponseEntity<?> changeToTable(@RequestBody OrderUpChangeToTableReqDTO orderUpChangeToTableReqDTO) {
        String username = appUtils.getPrincipalUsername();
        Optional<User> userOptional = userService.findByName(username);

        TableOrder tableOrderBusy = tableOrderService.findById(orderUpChangeToTableReqDTO.getTableIdBusy()).orElseThrow(() -> {
            throw new DataInputException("Bàn không tồn tại");
        });
        TableOrder tableOrderEmpty = tableOrderService.findById(orderUpChangeToTableReqDTO.getTableIdEmpty()).orElseThrow(() -> {
            throw new DataInputException("Bàn không tồn tại");
        });

        if(tableOrderEmpty.getId().equals(tableOrderBusy.getId()) ) {
            throw new DataInputException("Bàn chuyển và bàn chuyển là một xin vui lòng kiểm tra lại");
        }

        List<Order> orderEmptys = orderService.findByTableOrderAndPaid(tableOrderEmpty, false);
        List<Order> orderBusys = orderService.findByTableOrderAndPaid(tableOrderBusy, false);

        if (orderBusys.size() == 0) {
            throw new DataInputException("Bàn chuyển này không có hoá đơn, vui lòng kiểm tra lại thông tin");
        }

        if (orderBusys.size() > 1) {
            throw new DataInputException("Lỗi hệ thống, vui lòng liên hệ ADMIN để kiểm tra lại dữ liệu");
        }

        if (orderEmptys.size() != 0) {
            throw new DataInputException("Bàn nhận này đang có hoá đơn, vui lòng kiểm tra lại thông tin");
        }


        OrderUpChangeToTableResDTO orderUpChangeToTableResDTO = orderService.changeToTable(orderUpChangeToTableReqDTO, userOptional.get());

        return new ResponseEntity<>(orderUpChangeToTableResDTO, HttpStatus.OK);

    }
}
