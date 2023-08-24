package com.cg.service.staff;
import com.cg.model.Staff;
import com.cg.model.User;
import com.cg.model.dto.staff.StaffCreReqDTO;
import com.cg.model.dto.staff.StaffDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IStaffService extends IGeneralService<Staff,Long> {
    Staff create(StaffCreReqDTO staffCreReqDTO, User user);
}
