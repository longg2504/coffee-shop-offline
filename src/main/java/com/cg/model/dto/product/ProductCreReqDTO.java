package com.cg.model.dto.product;


import com.cg.model.Category;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductCreReqDTO implements Validator {

    private String title;
    private String price;
    private String unit;
    private String categoryId;
    private MultipartFile avatar;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductCreReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductCreReqDTO productCreReqDTO = (ProductCreReqDTO) target;
        String title = productCreReqDTO.title;
        String priceStr = productCreReqDTO.price;

        if (title.isEmpty()) {
            errors.rejectValue("title","title.null","Tên không được phép rỗng");
            return;
        }
        if (title.length() >= 40 || title.length() <= 5){
            errors.rejectValue("title","title.length","Tên món không ít hơn 5 kí tự và dài hơn 40 kí tự");
        }

        if (priceStr.isEmpty()){
            errors.rejectValue("price","price.length","vui lòng nhập giá tiền");
        } else {
            if (!priceStr.matches("^-?\\d+$")){
                errors.rejectValue("price", "price.matches", "Vui lòng nhập giá trị tiền bằng chữ số");
            } else {
                BigDecimal price = BigDecimal.valueOf(Long.parseLong(priceStr));
                if(price.compareTo(BigDecimal.ZERO) <= 0) {
                    errors.rejectValue("price", "price.min", "Số tiền phải lớn hơn 0");
                }
            }
        }
    }

    public Product toProduct(Category category) {
        return new Product()
                .setTitle(title)
                .setPrice(BigDecimal.valueOf(Long.parseLong(price)))
                .setUnit(unit)
                .setCategory(category);
    }
}
