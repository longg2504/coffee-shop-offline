package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.exception.GlobalExceptionHandler;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.TableOrder;
import com.cg.model.dto.tableOrder.TableOrderCreateReqDTO;
import com.cg.model.dto.tableOrder.TableOrderCreateResDTO;
import com.cg.model.dto.tableOrder.TableOrderDTO;
import com.cg.service.tableOrder.ITableOrderService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping("/api/tableOrders")
public class TableOrderAPI {
    @Autowired
    private ITableOrderService tableOrderService;

    @Autowired
    private ValidateUtils validateUtils;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> getAllTableOrder() {
        List<TableOrderDTO> tableOrderDTO = tableOrderService.findAllTableOrderDTO();
        if(tableOrderDTO.isEmpty()) {
            throw new ResourceNotFoundException("Không có bàn nào vui lòng kiểm tra lại hệ thống");
        }
        return new ResponseEntity<>(tableOrderDTO,HttpStatus.OK);
    }

    @GetMapping("/{tableId}")
    public ResponseEntity<?> getTableOrderById(@PathVariable Long tableId){
        Optional<TableOrder> tableOrderOptional = tableOrderService.findById(tableId);

        if(!tableOrderOptional.isPresent()){
            throw new DataInputException("Bàn này không tồn tại xin vui lòng kiểm tra lại");
        }

        return new ResponseEntity<>(tableOrderOptional,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTableOrder(@RequestBody TableOrderCreateReqDTO tableOrderCreateReqDTO){
        TableOrderCreateResDTO tableOrderCreateResDTO = tableOrderService.createTableOrder(tableOrderCreateReqDTO);

        return new ResponseEntity<>(tableOrderCreateResDTO,HttpStatus.CREATED);
    }

}
