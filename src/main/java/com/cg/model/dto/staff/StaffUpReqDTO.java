package com.cg.model.dto.staff;

import com.cg.model.Staff;
import com.cg.model.dto.locationRegion.LocationRegionUpReqDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class StaffUpReqDTO implements Validator{
    private String title;
    private String phone;
    private LocationRegionUpReqDTO locationRegion;
    private MultipartFile staffAvatar;

    @Override
    public boolean supports(Class<?> clazz) {
        return StaffUpReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StaffUpReqDTO staffUpReqDTO = (StaffUpReqDTO) target;
        String title = staffUpReqDTO.title;
        String phone = staffUpReqDTO.phone;

        if (title.isEmpty()) {
            errors.rejectValue("title", "title.null", "Tên không được phép rỗng");
            return;
        }
        if (title.length() >= 50 || title.length() <= 5) {
            errors.rejectValue("title", "title.length", "Tên không ít hơn 5 kí tự và dài hơn 50 kí tự");
        }
        if (phone.isEmpty()) {
            errors.rejectValue("title", "title.null", "Số điện thoại không được phép rỗng");
        }
    }

    public Staff toStaffChangeImage() {
        return new Staff()
                .setTitle(title)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegion())
                ;
    }

    public Staff toStaffUpReqDTO(Long staffId){
        return new Staff()
                .setId(staffId)
                .setTitle(title)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegion());

    }
}