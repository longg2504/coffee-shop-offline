package com.cg.repository;

import com.cg.model.Bill;
import com.cg.model.dto.bill.BillDTO;
import com.cg.model.dto.bill.BillDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT NEW com.cg.model.dto.bill.BillDTO (" +
            "b.id, " +
            "b.order.tableOrder.title, " +
            "b.order.totalAmount, " +
            "b.createdAt, " +
            "b.order.staff.title, " +
            "b.order.id " +
            ") " +
            "FROM Bill AS b "
    )
    List<BillDTO> findAllBillDTO();

    @Query("select new com.cg.model.dto.bill.BillDetailDTO (" +
            "b.id, " +
            "b.totalAmount, " +
            "od.amount, " +
            "od.note, " +
            "od.price, " +
            "od.quantity, " +
            "p.title, " +
            "od.createdAt " +
            ")" +
            "from Bill as b " +
            "join Order as o on b.order.id = o.id "  +
            "join OrderDetail as od on o.id = od.order.id " +
            "join Product as p on od.product.id = p.id " +
            "where b.id = :billId ")
    List<BillDetailDTO> findBillById(@Param("billId") Long billId);

    @Query("SELECT NEW com.cg.model.dto.bill.BillDTO (" +
            "b.id, " +
            "b.order.tableOrder.title, " +
            "b.order.totalAmount, " +
            "b.createdAt, " +
            "b.order.staff.title, " +
            "b.order.id" +
            ") " +
            "FROM Bill AS b " +
            "WHERE DATE(b.createdAt) = :eventDate"
    )
    List<BillDTO> findBillByCreatedAts(@Param("eventDate") Date eventDate);
}
