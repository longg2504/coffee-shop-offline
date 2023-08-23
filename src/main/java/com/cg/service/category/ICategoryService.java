package com.cg.service.category;

import com.cg.model.Category;
import com.cg.model.dto.category.*;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICategoryService extends IGeneralService<Category,Long> {

        CategoryCreResDTO createCategory(CategoryCreReqDTO categoryCreReqDTO);

        List<CategoryDTO> findAllCategoryDTO();

        CategoryUpResDTO updateCategory(CategoryUpReqDTO categoryUpReqDTO);

        void deleteByIdTrue(Category category);
}
