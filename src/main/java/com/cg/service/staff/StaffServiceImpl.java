package com.cg.service.staff;


import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.model.*;
import com.cg.model.dto.locationRegion.LocationRegionUpReqDTO;
import com.cg.model.dto.staff.StaffCreReqDTO;
import com.cg.model.dto.staff.StaffDTO;
import com.cg.model.dto.staff.StaffUpReqDTO;
import com.cg.repository.LocationRegionRepository;
import com.cg.repository.StaffAvatarRepository;
import com.cg.repository.StaffRepository;
import com.cg.service.role.IRoleService;
import com.cg.service.upload.IUploadService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private IStaffService staffService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private AppUtils appUtils;

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }

    @Override
    public Optional<Staff> findByIdAndDeletedFalse(Long id) {
        return staffRepository.findByIdAndDeletedFalse(id);
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

    @Override
    public void deleteByIdTrue(Staff staff) {
        staff.setDeleted(true);
        staffRepository.save(staff);
    }

    @Override
    public List<StaffDTO> findStaffByTitle(String keySearch) {
        return staffRepository.findStaffByTitle(keySearch);
    }

    @Override
    public Page<StaffDTO> findAllStaffDTOPage(Pageable pageable) {
        return staffRepository.findAllStaffDTOPage(pageable);
    }

    private void uploadAndSaveStaffImage(StaffAvatar staffAvatar, MultipartFile file) {
        try {
            Map uploadResult = uploadService.uploadImage(file, uploadUtils.buildImageUploadParamsStaff(staffAvatar));
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
    public Staff create(StaffCreReqDTO staffCreReqDTO) {
        MultipartFile file = staffCreReqDTO.getStaffAvatar();

        LocationRegion locationRegion = staffCreReqDTO.toLocationRegion();
        locationRegionRepository.save(locationRegion);

        StaffAvatar staffAvatar = new StaffAvatar();
        staffAvatarRepository.save(staffAvatar);

        uploadAndSaveStaffImage(staffAvatar, file);

        Boolean existsByUsername = userService.existsByUsername(staffCreReqDTO.getUsername());
        if (existsByUsername) {
            throw new EmailExistsException("UserName đã tồn tại");
        }
        Optional<Role> optRole = roleService.findById(staffCreReqDTO.getRoleId());
        if (!optRole.isPresent()) {
            throw new DataInputException("Role không tồn tại");
        }
        try {
            User user = userService.save(staffCreReqDTO.toUser(optRole.get()));
            Staff staff = staffCreReqDTO.toStaff();
            staff.setLocationRegion(locationRegion);
            staff.setStaffAvatar(staffAvatar);
            staff.setUser(user);
            staffRepository.save(staff);

            return staff;
        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Account information is not valid, please check the information again");
        }
    }

    @Override
    public Staff update(StaffUpReqDTO staffUpReqDTO, Long staffId) {
        MultipartFile file = staffUpReqDTO.getStaffAvatar();
        Optional<Staff> staffOptional = staffService.findById(staffId);
        Long locationRegionId = staffOptional.get().getLocationRegion().getId();
        LocationRegionUpReqDTO locationRegionUpReqDTO = staffUpReqDTO.getLocationRegion();
        LocationRegion locationRegion = locationRegionUpReqDTO.toLocationRegionUp(locationRegionId);
        locationRegionRepository.save(locationRegion);

        StaffAvatar staffAvatar = new StaffAvatar();
        staffAvatarRepository.save(staffAvatar);

        uploadAndSaveStaffImage( staffAvatar,file);

        Staff staffUpdate = staffUpReqDTO.toStaffChangeImage();

        staffUpdate.setId(staffId);
        staffUpdate.setStaffAvatar(staffAvatar);
        staffUpdate.setLocationRegion(locationRegion);
        staffUpdate.setUser(staffOptional.get().getUser());

        staffRepository.save(staffUpdate);

        return staffUpdate;

    }
}