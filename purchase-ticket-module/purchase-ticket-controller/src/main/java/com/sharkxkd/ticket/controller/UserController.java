package com.sharkxkd.ticket.controller;

import com.sharkxkd.ticket.dto.UserLoginDTO;
import com.sharkxkd.ticket.entity.JsonResult;
import com.sharkxkd.ticket.dto.UserRegisterDTO;
import com.sharkxkd.ticket.service.UserService;
import com.sharkxkd.ticket.vo.UserLoginVO;
import com.sharkxkd.ticket.vo.UserRegisterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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
    public JsonResult<UserRegisterVO> register(@RequestBody @Validated UserRegisterDTO userRegisterDTO){
        return JsonResult.success(userService.register(userRegisterDTO));
    }
    @PostMapping("/login")
    public JsonResult<UserLoginVO> login(@RequestBody @Validated UserLoginDTO userLoginDTO){
        return JsonResult.success(userService.login(userLoginDTO));
    }
}
