package com.sharkxkd.ticket.controller;

import com.sharkxkd.ticket.common.JsonResult;
import com.sharkxkd.ticket.dto.UserRegisterDTO;
import com.sharkxkd.ticket.service.UserService;
import com.sharkxkd.ticket.vo.UserRegisterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制层
 *
 * @author zc
 * @date 2024/11/19 20:01
 **/
@RestController
@Slf4j
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/username/{username}")
    public JsonResult<Boolean> validateUserNameDuplicated(@PathVariable String username){
        return JsonResult.success(userService.validateUserNameDuplicated(username));
    }

    @PostMapping("/register")
    public JsonResult<UserRegisterVO> register(@RequestBody UserRegisterDTO userRegisterDTO){
        return JsonResult.success(userService.register(userRegisterDTO));
    }
}
