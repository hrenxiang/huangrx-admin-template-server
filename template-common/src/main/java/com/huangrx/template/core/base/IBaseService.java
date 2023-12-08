package com.huangrx.template.core.base;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author huangrx
 * @since 2023/12/8 16:53
 */
public interface IBaseService<T> extends IService<T> {

    /**
     * 判断数据是否存在
     *
     * @param id 记录ID
     */
    void isPresent(Long id);

}
