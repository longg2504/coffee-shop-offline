package com.cg.model.dto.tableOrder;

import com.cg.model.enums.ETableStatus;
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
public class TableOrderCreateResDTO {
    private Long id;
    private String title;
    private ETableStatus status;
}
