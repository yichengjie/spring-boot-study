package com.yicj.study.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidatorImpl implements InitializingBean {
    private Validator validator ;


    //实现校验方法并返回校验结果
    public ValidationResult validate(Object bean){
        ValidationResult result = new ValidationResult() ;
        Set<ConstraintViolation<Object>> sets = validator.validate(bean);
        if(sets.size() > 0){
            result.setHasErrors(true);
            sets.forEach(set ->{
                String errMsg = set.getMessage() ;
                String propertyName = set.getPropertyPath().toString() ;
                result.getErrorMsgMap().put(propertyName,errMsg) ;
            });
        }
        return result ;
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过工厂的初始化方式使其实例化
        this.validator =
                Validation.buildDefaultValidatorFactory().getValidator() ;
    }
}
