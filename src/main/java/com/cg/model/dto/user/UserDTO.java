package com.cg.model.dto.user;

import com.cg.model.dto.role.RoleDTO;
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
public class UserDTO {
    private Long id;
    private String username;

    private String password;

    private RoleDTO role;
}
