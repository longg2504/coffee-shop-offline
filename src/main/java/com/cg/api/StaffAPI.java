package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.model.Staff;
import com.cg.model.User;
import com.cg.model.dto.staff.StaffCreReqDTO;
import com.cg.model.dto.staff.StaffDTO;
import com.cg.service.locationRegion.ILocationRegionService;
import com.cg.service.staff.IStaffService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
public class StaffAPI {
    @Autowired
    private IStaffService staffService;
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private IUserService userService;
    @Autowired
    private ILocationRegionService locationRegionService;

    @GetMapping("/{staffId}")
    public ResponseEntity<?> getById(@PathVariable Long staffId) {
        Optional<Staff> optionalStaff = staffService.findById(staffId);
        if (!optionalStaff.isPresent()) {
            throw new DataInputException("ID nhân viên không hợp lệ");
        }
        Staff staff = optionalStaff.get();
        StaffDTO staffDTO = staff.toStaffDTO();
        return new ResponseEntity<>(staffDTO, HttpStatus.OK);
    }



    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute StaffCreReqDTO staffCreReqDTO, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }
        User user = userService.findById(Long.valueOf(staffCreReqDTO.getUserId())).orElseThrow(() -> {
            throw new DataInputException("UserId không tồn tại");
        });
        Staff staff = staffService.create(staffCreReqDTO,user);
        StaffDTO staffCreReqDTO1 = staff.toStaffDTO();
       return  new ResponseEntity<>(staffCreReqDTO1,HttpStatus.CREATED);
    }
}
