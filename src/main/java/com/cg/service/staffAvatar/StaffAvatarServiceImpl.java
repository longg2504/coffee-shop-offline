package com.cg.service.staffAvatar;

import com.cg.model.StaffAvatar;
import com.cg.repository.StaffAvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class StaffAvatarServiceImpl implements IStaffAvatarService{
    @Autowired
    private StaffAvatarRepository staffAvatarRepository;
    @Override
    public List<StaffAvatar> findAll() {
        return staffAvatarRepository.findAll();
    }

    @Override
    public Optional<StaffAvatar> findById(Long id) {
        return staffAvatarRepository.findById(id);
    }

    @Override
    public StaffAvatar save(StaffAvatar staffAvatar) {
        return staffAvatarRepository.save(staffAvatar);
    }

    @Override
    public void delete(StaffAvatar staffAvatar) {
        staffAvatarRepository.delete(staffAvatar);
    }

    @Override
    public void deleteById(Long id) {
        staffAvatarRepository.deleteById(id);
    }
}
