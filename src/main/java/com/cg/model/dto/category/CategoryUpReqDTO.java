package com.cg.model.dto.category;

import com.cg.model.Category;
import com.cg.utils.ValidateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryUpReqDTO implements Validator {


    private String id;
    private String title;

    @Override
    public boolean supports(Class<?> clazz) {
           return CategoryUpReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CategoryUpReqDTO categoryUpReqDTO = (CategoryUpReqDTO) target;


        String title = categoryUpReqDTO.title;




        if (title.isEmpty()) {
            errors.rejectValue("title", "title.null", "Tên không được phép rỗng");
        }


    }

    public Category toCategoryUpreqDTO() {
        return new Category()
                .setId(Long.valueOf(id))
                .setTitle(title);
    }
}
