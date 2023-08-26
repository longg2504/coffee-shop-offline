package com.cg.repository;

import com.cg.model.Staff;
import com.cg.model.dto.staff.StaffDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Query("SELECT NEW com.cg.model.dto.staff.StaffDTO(" +
            "st.id," +
            "st.title," +
            "st.phone," +
            "st.locationRegion," +
            "st.staffAvatar," +
            "st.user" +
            ") " +
            "FROM Staff as st " +
            "WHERE st.title like :keySearch "
    )
    List<StaffDTO> findStaffByTitle(String keySearch);

    @Query("SELECT NEW com.cg.model.dto.staff.StaffDTO(" +
            "st.id," +
            "st.title," +
            "st.phone," +
            "st.locationRegion," +
            "st.staffAvatar," +
            "st.user" +
            ") " +
            "FROM Staff as st " +
            "WHERE st.deleted = false " +
            "ORDER BY st.id ASC"
    )
    Page<StaffDTO> findAllStaffDTOPage(Pageable pageable);

    Optional<Staff> findByIdAndDeletedFalse(Long id);
}
