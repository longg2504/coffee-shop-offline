package com.cg.model.dto.tableOrder;

import com.cg.model.enums.ETableStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class TableOrderDTO {
    private String id;
    private String title;
    private String status;

    public TableOrderDTO(Long id, String title, ETableStatus status){
        this.id= String.valueOf(id);
        this.title=title;
        this.status=status.getValue();
    }


    public TableOrderDTO toTableOrderDTO() {
        return new TableOrderDTO()
                .setId(id)
                .setTitle(title)
                .setStatus(status)
                ;
    }
}
