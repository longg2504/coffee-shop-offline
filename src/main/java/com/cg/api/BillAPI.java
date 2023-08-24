package com.cg.api;

import com.cg.model.dto.bill.BillDTO;
import com.cg.repository.BillRepository;
import com.cg.service.bill.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillAPI {

    @Autowired
    private IBillService billService;

    @GetMapping
    public ResponseEntity<?> showBill(){

        List<BillDTO> billDTOS = billService.findAllBillDTO();

        return new ResponseEntity<>(billDTOS,HttpStatus.OK);
    }
}
