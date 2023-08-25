package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.model.Bill;
import com.cg.model.dto.bill.BillCreResDTO;
import com.cg.model.dto.bill.BillDTO;
import com.cg.model.dto.bill.BillDetailDTO;
import com.cg.repository.BillRepository;
import com.cg.service.bill.IBillService;
import com.cg.service.tableOrder.ITableOrderService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillAPI {

    @Autowired
    private IBillService billService;

    @Autowired
    private ValidateUtils validateUtils;

    @Autowired
    private ITableOrderService tableOrderService;

    @Autowired
    private AppUtils appUtils;


    @GetMapping
    public ResponseEntity<?> showBill() {

        List<BillDTO> billDTOS = billService.findAllBillDTO();

        return new ResponseEntity<>(billDTOS, HttpStatus.OK);
    }

    @GetMapping("/{billId}")
    public ResponseEntity<?> showBillDetail(@PathVariable("billId") String billIdStr) {

        if (!validateUtils.isNumberValid(billIdStr)) {
            throw new DataInputException("Mã lịch sử không hợp lệ");
        }
        Long billId = Long.parseLong(billIdStr);

        billService.findById(billId).orElseThrow(() -> {
            throw new DataInputException("Mã lịch sử không tồn tại");
        });

        List<BillDetailDTO> billDetailDTOS = billService.findBillById(billId);

        return new ResponseEntity<>(billDetailDTOS, HttpStatus.OK);
    }

    @GetMapping("/search/day")
    public ResponseEntity<?> seachBill(@RequestParam("eventDate") String eventDate) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date dateBill;
        try {
            dateBill = sdf.parse(eventDate);
        } catch (ParseException e) {
            throw new DataInputException("vui lòng nhập đúng kiểu năm/tháng/ngày");
        }

        List<BillDTO> billDTOS = billService.findBillByCreatedAts(dateBill);

        return new ResponseEntity<>(billDTOS, HttpStatus.OK);
    }

    @PostMapping("/{tableId}")
    public ResponseEntity<?> createBill(@PathVariable("tableId") String tableIdStr){

     if (!validateUtils.isNumberValid(tableIdStr)) {
            throw new DataInputException("Mã bàn không hợp lệ");
        }

        Long tableId = Long.parseLong(tableIdStr);
        tableOrderService.findById(tableId).orElseThrow(() ->{
            throw new DataInputException("Mã bàn không tồn tại");
        });

        BillCreResDTO billResDTO = billService.createBill(tableId);

        return new ResponseEntity<>(billResDTO,HttpStatus.CREATED);

    }

}
