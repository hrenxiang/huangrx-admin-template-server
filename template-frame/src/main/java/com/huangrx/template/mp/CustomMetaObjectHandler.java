package com.huangrx.template.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * Mybatis Plus允许在插入或者更新的时候
 *
 * @author huangrx
 * @since 2023-11-26 9:50
 */
@Slf4j
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    public static final String CREATE_TIME_FIELD = "createTime";
    public static final String CREATOR_ID_FIELD = "creatorId";

    public static final String UPDATE_TIME_FIELD = "updateTime";
    public static final String UPDATER_ID_FIELD = "updaterId";


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("MetaObjectHandler ------ 添加填充默认值");
        // 下面两种方法都可以进行字段设置，要注意，时间类型要和实体中保持一致，不然可能会导致 填充为null
        this.strictInsertFill(metaObject, CREATE_TIME_FIELD, LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, UPDATE_TIME_FIELD, LocalDateTime::now, LocalDateTime.class);
        Long userId = getUserIdSafely();
        if (userId != null && metaObject.hasSetter(CREATOR_ID_FIELD)) {
            this.strictInsertFill(metaObject, CREATOR_ID_FIELD, Long.class, userId);
        }
        if (userId != null && metaObject.hasSetter(UPDATER_ID_FIELD)) {
            this.strictInsertFill(metaObject, UPDATER_ID_FIELD, Long.class, userId);
        }
        // 在一些比较旧的版本，为填充字段设置值的API如下，3.3.0之后已经不建议使用
        // this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, UPDATE_TIME_FIELD, LocalDateTime::now, LocalDateTime.class);

        Long userId = getUserIdSafely();
        if (userId != null && metaObject.hasSetter(UPDATER_ID_FIELD)) {
            this.strictInsertFill(metaObject, UPDATER_ID_FIELD, Long.class, userId);
        }
    }

    private Long getUserIdSafely() {
        return null;
    }

}