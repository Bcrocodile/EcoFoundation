package com.eco.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.eco.common.enums.DataDeletedEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class FieldFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("deleted", DataDeletedEnum.ZERO.getValue(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

}
