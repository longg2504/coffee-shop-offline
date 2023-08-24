package com.cg.model.dto.orderDetail;
import com.cg.model.dto.productAvatar.ProductAvatarResDTO;
import com.cg.model.dto.tableOrder.TableOrderResDTO;
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
public class OrderDetailCreResDTO {
    private Long orderDetailId;
    private TableOrderResDTO table;
    private Long productId;
    private String title;
    private BigDecimal price;
    private Long quantity;
    private BigDecimal amount;
    private String note;
    private BigDecimal totalAmount;
    private ProductAvatarResDTO avatar;
}
