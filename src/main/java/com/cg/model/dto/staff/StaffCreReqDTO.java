package com.cg.model.dto.staff;

import com.cg.model.Staff;
import com.cg.model.User;
import com.cg.model.dto.locationRegion.LocationRegionCreReqDTO;
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
public class StaffCreReqDTO implements Validator {
    private String title;
    private String phone;
    private LocationRegionCreReqDTO locationRegion;
    private MultipartFile staffAvatar;
    private String userId;


    public Staff toStaff(User user) {
        return new Staff()
                .setTitle(title)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegion())
                .setUser(user)
                ;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return StaffCreReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StaffCreReqDTO staffCreReqDTO = (StaffCreReqDTO) target;
        String title = staffCreReqDTO.title;
        String phone =staffCreReqDTO.phone;

        if (title.isEmpty()) {
            errors.rejectValue("title","title.null","Tên không được phép rỗng");
            return;
        }
        if (title.length() >= 25 || title.length() <= 5){
            errors.rejectValue("title","title.length","Tên không ít hơn 5 kí tự và dài hơn 25 kí tự");
        }
        if (phone.isEmpty()) {
            errors.rejectValue("title","title.null","Tên không được phép rỗng");
        } else {
            if ( phone.matches("^0\\d{9}$")) {
                errors.rejectValue("phone", "phone.matches", "Vui lòng nhập số điện thoại bắt đầu ký tự 0(10 số)");
            }

        }
    }
}
