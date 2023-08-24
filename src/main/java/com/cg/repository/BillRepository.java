package com.cg.repository;

import com.cg.model.Bill;
import com.cg.model.dto.bill.BillDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT NEW com.cg.model.dto.bill.BillDTO (" +
            "b.id, " +
            "b.order.tableOrder.title, " +
            "b.order.totalAmount, " +
            "b.createdAt, " +
            "b.order.staff.title, " +
            "b.order.id" +
            ") " +
            "FROM Bill AS b "
    )
    List<BillDTO> findAllBillDTO();

}
