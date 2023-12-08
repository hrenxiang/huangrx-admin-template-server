package com.huangrx.template.enums;

/**
 * 通用枚举接口
 *
 * @author huangrx
 * @since 2023/11/27 14:26
 */
public interface BasicEnum<T> {

    /**
     * 值
     *
     * @return 值
     */
    T value();

    /**
     * 描述
     *
     * @return 描述
     */
    String description();

    /**
     * 从枚举类中根据值获取枚举常量的方法。
     *
     * @param <E>       枚举类型
     * @param <T>       值类型
     * @param enumClass 枚举类
     * @param value     值
     * @return 匹配的枚举常量
     * @throws IllegalArgumentException 如果没有匹配的枚举常量
     */
    static <E extends Enum<E> & BasicEnum<T>, T> E convertByValue(Class<E> enumClass, T value) {
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.value().equals(value)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("No enum Constants with value: " + value);
    }
}
