package com.sharkxkd.ticket.designPattern.validator;

import com.sharkxkd.ticket.ApplicationContextHolder;
import com.sharkxkd.ticket.enums.ValidatorEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检测链执行器
 *
 * @author zc
 * @date 2024/11/19 15:46
 **/
@Component
public class ValidatorChainHandler<T>{
    private Map<String, List<AbstractValidator>> validatorMap = new HashMap<>();


    public void validate(ValidatorEnum validatorEnum,T validateData){
        List<AbstractValidator> validators = validatorMap.get(validatorEnum.name());
        if(validators.isEmpty()){
            throw new RuntimeException(String.format("[%s] Chain of Responsibility name is undefined.",validatorEnum.name()));
        }
        for (AbstractValidator validator: validators) {
            validator.validate(validateData);
        }
    }
    /**
     * 加载每个校验器，通过类型将校验器进行分类处理
     */
    @PostConstruct
    public void buildValidatorMap(){
        Map<String, AbstractValidator> validatorsMap = ApplicationContextHolder.getBeansOfType(AbstractValidator.class);
        validatorsMap.forEach((beanName,bean) -> {
            List<AbstractValidator> validators = validatorMap.get(bean.attainValidatorName());
            if(validators == null){
                validators = new ArrayList<>();
            }
            validators.add(bean);
            validatorMap.put(bean.attainValidatorName(),validators);
        });
    }
}
