package com.huangrx.template.command;

import cn.hutool.core.collection.CollUtil;
import com.huangrx.template.exception.ApiException;
import com.huangrx.template.exception.error.ErrorCode;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 批量操作命令
 *
 * @author huangrx
 * @since 2023/11/28 14:13
 */
@Data
public class BulkOperationCommand<T> {

    public BulkOperationCommand(List<T> idList) {
        if (CollUtil.isEmpty(idList)) {
            throw new ApiException(ErrorCode.Business.COMMON_BULK_DELETE_IDS_IS_INVALID);
        }
        // 移除重复元素
        this.ids = new HashSet<>(idList);
    }

    private Set<T> ids;

}
