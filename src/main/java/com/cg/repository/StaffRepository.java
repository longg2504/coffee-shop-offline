package com.cg.repository;

import com.cg.model.Staff;
import com.cg.model.dto.staff.StaffDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {
    @Query("SELECT NEW com.cg.model.dto.staff.StaffDTO " +
            "(" +
            "st.id," +
            "st.title," +
            "st.phone," +
            "st.locationRegion," +
            "st.staffAvatar," +
            "st.user" +
            ") " +
            "FROM Staff as st " +
            "WHERE st.deleted=false "
    )
    List<StaffDTO> findAllStaffDTO();

}
