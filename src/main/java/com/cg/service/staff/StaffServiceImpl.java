package com.cg.service.staff;


import com.cg.exception.DataInputException;
import com.cg.model.LocationRegion;
import com.cg.model.Staff;
import com.cg.model.StaffAvatar;
import com.cg.model.User;
import com.cg.model.dto.locationRegion.LocationRegionCreReqDTO;
import com.cg.model.dto.locationRegion.LocationRegionUpReqDTO;
import com.cg.model.dto.staff.StaffCreReqDTO;
import com.cg.model.dto.staff.StaffDTO;
import com.cg.model.dto.staff.StaffUpReqDTO;
import com.cg.repository.LocationRegionRepository;
import com.cg.repository.StaffAvatarRepository;
import com.cg.repository.StaffRepository;
import com.cg.service.upload.IUploadService;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService {
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private IUploadService uploadService;
    @Autowired
    private UploadUtils uploadUtils;
    @Autowired
    private StaffAvatarRepository staffAvatarRepository;
    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }

    @Override
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public void delete(Staff staff) {
        staffRepository.delete(staff);
    }

    @Override
    public void deleteById(Long id) {
        staffRepository.deleteById(id);
    }

    @Override
    public List<StaffDTO> findAllStaffDTO() {
        return staffRepository.findAllStaffDTO();
    }

    private void uploadAndSaveStaffImage(StaffCreReqDTO staffCreReqDTO, StaffAvatar staffAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(staffCreReqDTO.getStaffAvatar(), uploadUtils.buildImageUploadParamsStaff(staffAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            staffAvatar.setFileName(staffAvatar.getId() + "." + fileFormat);
            staffAvatar.setFileUrl(fileUrl);
            staffAvatar.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
            staffAvatar.setCloudId(staffAvatar.getFileFolder() + "/" + staffAvatar.getId());
            staffAvatarRepository.save(staffAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }



    @Override
    public Staff create(StaffCreReqDTO staffCreReqDTO, User user) {

        LocationRegionCreReqDTO locationRegionCreReqDTO = staffCreReqDTO.getLocationRegion();
        LocationRegion locationRegion = locationRegionCreReqDTO.toLocationRegion();
        locationRegionRepository.save(locationRegion);

        StaffAvatar staffAvatar = new StaffAvatar();
        staffAvatarRepository.save(staffAvatar);

        uploadAndSaveStaffImage(staffCreReqDTO, staffAvatar);
        Staff staff = staffCreReqDTO.toStaff(user);
        staff.setLocationRegion(locationRegion);
        staff.setStaffAvatar(staffAvatar);
        staffRepository.save(staff);

        return staff;

    }

}
