package com.cg.service.bill;

import com.cg.model.Bill;
import com.cg.model.dto.bill.BillCreResDTO;
import com.cg.model.dto.bill.BillDTO;
import com.cg.model.dto.bill.BillDetailDTO;
import com.cg.service.IGeneralService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IBillService extends IGeneralService<Bill,Long> {
    List<BillDTO> findAllBillDTO();

    List<BillDetailDTO> findBillById(Long billId);

    List<BillDTO> findBillByCreatedAts(Date BillDate);

    BillCreResDTO createBill(Long tableId);

    List<BillDTO> getBillByDate(Integer year, Integer month, Integer day);
}
