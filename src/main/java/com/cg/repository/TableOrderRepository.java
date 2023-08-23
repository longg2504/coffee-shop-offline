package com.cg.repository;

import com.cg.model.TableOrder;
import com.cg.model.dto.tableOrder.TableOrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TableOrderRepository extends JpaRepository<TableOrder,Long> {
    @Query("SELECT NEW com.cg.model.dto.tableOrder.TableOrderDTO (" +
            "to.id, " +
            "to.title, " +
            "to.status " +
            ")" +
            "FROM TableOrder AS to")
    List<TableOrderDTO> findAllTableOrderDTO();
}
