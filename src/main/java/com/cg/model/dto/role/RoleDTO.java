package com.cg.model.dto.role;

import com.cg.model.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class RoleDTO {
    private Long id;
    private String code;

    private ERole name;

}
