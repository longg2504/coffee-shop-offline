package com.cg.model.dto.staff;

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
public class StaffUpReqDTO implements Validator {
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
        if (title.length() >= 25 || title.length() <= 5) {
            errors.rejectValue("title", "title.length", "Tên không ít hơn 5 kí tự và dài hơn 25 kí tự");
        }
        if (phone.isEmpty()) {
            errors.rejectValue("title", "title.null", "Tên không được phép rỗng");
        } else {
            if (phone.matches("^0\\d{9}$")) {
                errors.rejectValue("phone", "phone.matches", "Vui lòng nhập số điện thoại bắt đầu ký tự 0(10 số)");
            }

        }
    }

}

