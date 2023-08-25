package com.cg.repository;

import com.cg.model.Staff;

import com.cg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {
    Optional<Staff> findByUserId(Long userId);

    Optional<Staff> findByUserAndDeletedIsFalse(User user);

}
