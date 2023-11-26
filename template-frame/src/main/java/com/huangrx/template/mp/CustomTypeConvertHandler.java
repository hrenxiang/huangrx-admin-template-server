package com.huangrx.template.mp;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.converts.select.BranchBuilder;
import com.baomidou.mybatisplus.generator.config.converts.select.Selector;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.type.ITypeConvertHandler;
import com.baomidou.mybatisplus.generator.type.TypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.sql.Types;

import static com.baomidou.mybatisplus.generator.config.rules.DbColumnType.*;

/**
 * 根据源码自定义类型映射器
 *
 * @author huangrx
 * @since 2023/11/25 20:37
 */
public class CustomTypeConvertHandler implements ITypeConvertHandler {

    @NotNull
    @Override
    public IColumnType convert(GlobalConfig globalConfig, TypeRegistry typeRegistry, TableField.MetaInfo metaInfo) {
        return use(metaInfo.getJdbcType().TYPE_CODE)
                .test(contains(Types.SMALLINT).then(INTEGER))
                .test(contains(Types.TINYINT).then(BOOLEAN))
                .or(typeRegistry.getColumnType(metaInfo));
    }

    /**
     * 使用指定参数构建一个选择器
     *
     * @param param 参数
     * @return 返回选择器
     */
    static Selector<Integer, IColumnType> use(Integer param) {
        return new Selector<>(param);
    }

    /**
     * 这个分支构建器用于构建用于支持  的分支
     *
     * @param value 分支的值
     * @return 返回分支构建器
     * @see #containsAny(Integer...)
     */
    static BranchBuilder<Integer, IColumnType> contains(Integer value) {
        return BranchBuilder.of(s -> s.equals(value));
    }

    /**
     * @see #contains(Integer)
     */
    static BranchBuilder<Integer, IColumnType> containsAny(Integer... values) {
        return BranchBuilder.of(s -> {
            for (Integer value : values) {
                if (s.equals(value)) {
                    return true;
                }
            }
            return false;
        });
    }
}
