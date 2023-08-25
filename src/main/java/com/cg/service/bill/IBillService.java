package com.cg.service.bill;

import com.cg.model.Bill;
import com.cg.model.dto.bill.BillDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IBillService extends IGeneralService<Bill,Long> {
    List<BillDTO> findAllBillDTO();

}
