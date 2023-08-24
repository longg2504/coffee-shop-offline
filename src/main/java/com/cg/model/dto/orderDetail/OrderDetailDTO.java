package com.cg.model.dto.orderDetail;

import com.cg.model.dto.product.ProductDTO;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OrderDetailDTO {
    private Long orderDetailId;
    private ProductDTO product;
    private String quantity;
    private BigDecimal price;
    private BigDecimal amount;
    private String note;
}
