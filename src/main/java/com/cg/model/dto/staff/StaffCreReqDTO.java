package com.cg.model.dto.staff;
import com.cg.model.Staff;
import com.cg.model.User;
import com.cg.model.dto.locationRegion.LocationRegionCreReqDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class StaffCreReqDTO {
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

}
