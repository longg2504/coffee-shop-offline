package com.cg.model;

import com.cg.model.dto.category.CategoryCreResDTO;
import com.cg.model.dto.category.CategoryDTO;
import com.cg.model.dto.category.CategoryUpResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="categories")
@Accessors(chain = true)
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;


    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public CategoryCreResDTO toCategoryCreResDTO() {
        return new CategoryCreResDTO()
                .setId(id)
                .setTitle(title)
                ;
    }

    public CategoryUpResDTO toCategoryUpResDTO() {
        return new CategoryUpResDTO()
                .setId(id)
                .setTitle(title)
                ;
    }

    public CategoryDTO toCategoryDTO() {
        return new CategoryDTO()
                .setId(id)
                .setTitle(title);
    }
}
