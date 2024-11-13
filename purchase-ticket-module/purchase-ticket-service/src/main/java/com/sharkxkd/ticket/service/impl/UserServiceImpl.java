package com.sharkxkd.ticket.service.impl;

import com.sharkxkd.ticket.dao.UserMapper;
import com.sharkxkd.ticket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户业务层具体实现
 *
 * @author zc
 * @date 2024/11/13 22:09
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

}
