package com.cg.model.dto.order;
import com.cg.model.dto.orderDetail.OrderDetailDTO;
import com.cg.model.dto.staff.StaffDTO;
import com.cg.model.dto.tableOrder.TableOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OrderReqDTO implements Validator {
    private Long idOrder;
    private StaffDTO staff;
    private TableOrderDTO tableOrder;
    private BigDecimal totalAmount;
    private OrderDetailDTO orderDetail;
    private Boolean paid;

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderReqDTO orderReqDTO = (OrderReqDTO) target;
        String idTableStr = orderReqDTO.tableOrder.getId();
        String quantityStr = orderReqDTO.orderDetail.getQuantity();
        String note = orderReqDTO.orderDetail.getNote();
        String idProductStr = orderReqDTO.orderDetail.getProduct().getId();
        String idStaffStr = orderReqDTO.staff.getId();

        if(idStaffStr.isEmpty()){
            errors.rejectValue("staff.id","staffId.null","Mã nhân viên không được bỏ trống");

        }

        if(!idStaffStr.matches("^-?\\d+$")){
            errors.rejectValue("staff.id","staffId.string","Mã nhân viên không được nhập chữ");
        }


        if(idProductStr.isEmpty()){
            errors.rejectValue("orderDetail.product.id","orderDetail.productId.null","Mã sản phẩm không được bỏ trống");

        }

        if(!idProductStr.matches("^-?\\d+$")){
            errors.rejectValue("orderDetail.product.id","orderDetail.productID.string","Mã sản phẩm không được nhập chữ");
        }else {
            Long idProduct = Long.valueOf(idProductStr);
            if (idProduct < 0L) {
                errors.rejectValue("orderDetail.product.id", "orderDetail.product.id.min", "Mã sản phẩm phải lơn hơn 0");
            }
        }

        if (note.length() >= 100){
            errors.rejectValue("orderDetail.note","orderDetail.note.length","Ghi chú không được vượt quá 100 kí tự ");
        }

        if (quantityStr.isEmpty()){
            errors.rejectValue("orderDetail.quantity","orderDetail.quantity.null","Số lượng không được bỏ trống");

        }
        if(!quantityStr.matches("^-?\\d+$")){
            errors.rejectValue("orderDetail.quantity","orderDetail.quantity.string","Số lượng không được nhập chữ");
        }else {
            Integer quantity = Integer.valueOf(quantityStr);
            if (quantity <= 0 || quantity > 30) {
                errors.rejectValue("orderDetail.quantity", "orderDetail.quantity.minAndMax", "Số lượng phải từ 1 đến 30");
            }
        }
        if(idTableStr.isEmpty()){
            errors.rejectValue("tableOrder","tableOrder.null","Mã bàn không được bỏ trống");

        }

        if(!idTableStr.matches("^-?\\d+$")){
            errors.rejectValue("tableOrder","tableOrder.string","Mã bàn không được nhập chữ");
        }


    }

}
