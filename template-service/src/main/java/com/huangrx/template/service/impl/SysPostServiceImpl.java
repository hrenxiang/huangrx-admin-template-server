package com.huangrx.template.service.impl;

import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import com.huangrx.template.po.SysPost;
import com.huangrx.template.mapper.SysPostMapper;
import com.huangrx.template.service.ISysPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 岗位信息表 服务实现类
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    @Override
    public void isPresent(Long id) {
        SysPost post = this.getById(id);
        if (Optional.ofNullable(post).isEmpty()) {
            throw new ApiException(ErrorCode.Business.ROLE_NOT_EXIST);
        }
    }
}
