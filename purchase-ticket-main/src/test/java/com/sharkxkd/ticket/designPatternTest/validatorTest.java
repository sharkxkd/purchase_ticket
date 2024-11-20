package com.sharkxkd.ticket.designPatternTest;

import com.sharkxkd.ticket.enums.ValidatorEnum;
import com.sharkxkd.ticket.designPattern.validator.ValidatorChainHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 检测器测试类
 *
 * @author zc
 * @date 2024/11/19 15:54
 **/
@SpringBootTest()
public class validatorTest {
    @Autowired
    private ValidatorChainHandler validatorChainHandler;
    @Test
    public void testValidator(){
        //validatorChainHandler.validate(ValidatorEnum.USERINFO_VALIDATOR);
    }
}
