package com.cg.model.dto.staff;

import com.cg.model.LocationRegion;
import com.cg.model.Order;
import com.cg.model.StaffAvatar;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class StaffDTO {
    private String id;
    private  String title;
    private String phone;
    private LocationRegion locationRegion;
    private StaffAvatar staffAvatar;
    private User user;
    private List<Order> orders;
}
