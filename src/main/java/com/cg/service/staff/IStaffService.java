package com.cg.service.staff;
import com.cg.model.Staff;
import com.cg.model.dto.staff.StaffCreReqDTO;
import com.cg.model.dto.staff.StaffDTO;
import com.cg.model.dto.staff.StaffUpReqDTO;
import com.cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IStaffService extends IGeneralService<Staff,Long> {

    Optional<Staff> findByIdAndDeletedFalse(Long id);
    Staff create(StaffCreReqDTO staffCreReqDTO);

    List<StaffDTO> findAllStaffDTO();
    void deleteByIdTrue(Staff staff);
    List<StaffDTO> findStaffByTitle(String keySearch);
    Page<StaffDTO> findAllStaffDTOPage(Pageable pageable);

    Staff update(StaffUpReqDTO staffUpReqDTO,Long staffId);
}
