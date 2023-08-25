package com.cg.model;

import com.cg.model.dto.tableOrder.TableOrderCreateResDTO;
import com.cg.model.dto.tableOrder.TableOrderDTO;
import com.cg.model.dto.tableOrder.TableOrderResDTO;
import com.cg.model.enums.ETableStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;


import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tables")
@Accessors(chain = true)
public class TableOrder extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private ETableStatus status;

    public TableOrderDTO toTableOrderDTO() {
        return new TableOrderDTO()
                .setId(String.valueOf(id))
                .setTitle(title)
                .setStatus(status.getValue())
                ;
    }

    public TableOrderCreateResDTO toTableOrderCreateResDTO() {
        return new TableOrderCreateResDTO()
                .setId(null)
                .setTitle(title)
                .setStatus(ETableStatus.EMPTY);
    }
    public TableOrderResDTO toTableOrderResDTO() {
        return new TableOrderResDTO()
                .setId(id)
                .setTitle(title)
                .setStatus(status)
                ;
    }

    public TableOrderResDTO toUpdateTableOrderResDTO(Long tableOrderId) {
        return new TableOrderResDTO()
                .setId(tableOrderId)
                .setTitle(title)
                .setStatus(ETableStatus.EMPTY)
                ;
    }
}
