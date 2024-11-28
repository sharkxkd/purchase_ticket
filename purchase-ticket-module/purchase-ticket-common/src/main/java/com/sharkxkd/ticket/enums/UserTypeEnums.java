package com.sharkxkd.ticket.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserTypeEnums {
    USER("USER"), ADMIN("ADMIN");
    @EnumValue
    private final String userType;

}
