package com.huangrx.template.service.impl;

import com.huangrx.template.po.SysOperationLog;
import com.huangrx.template.mapper.SysOperationLogMapper;
import com.huangrx.template.service.ISysOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
@Service
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements ISysOperationLogService {

}
