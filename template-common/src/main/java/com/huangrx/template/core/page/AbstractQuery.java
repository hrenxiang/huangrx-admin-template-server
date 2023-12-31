package com.huangrx.template.core.page;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 如果是简单的排序 和 时间范围筛选  可以使用内置的这几个字段
 *
 * @author huangrx
 * @since 2023-11-26 10:29
 */
@Data
public abstract class AbstractQuery<T> {

    protected String orderColumn;

    protected String orderDirection;

    protected String timeRangeColumn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime beginTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime endTime;

    private static final String ASC = "ascending";

    private static final String DESC = "descending";

    /**
     * 生成query conditions
     *
     * @return 添加条件后的QueryWrapper
     */
    public QueryWrapper<T> toQueryWrapper() {
        QueryWrapper<T> queryWrapper = addQueryCondition();
        addSortCondition(queryWrapper);
        addTimeCondition(queryWrapper);
        return queryWrapper;
    }

    /**
     * 添加查询条件
     *
     * @return 添加条件后的QueryWrapper
     */
    public abstract QueryWrapper<T> addQueryCondition();

    public void addSortCondition(QueryWrapper<T> queryWrapper) {
        if (queryWrapper == null || CharSequenceUtil.isEmpty(orderColumn)) {
            return;
        }

        if (CharSequenceUtil.isEmpty(this.orderDirection)) {
            return;
        }

        Boolean sortDirection = convertSortDirection();
        if (sortDirection != null) {
            queryWrapper.orderBy(CharSequenceUtil.isNotEmpty(orderColumn), sortDirection, CharSequenceUtil.toUnderlineCase(orderColumn));
        }
    }

    public void addTimeCondition(QueryWrapper<T> queryWrapper) {
        if (queryWrapper != null
                && CharSequenceUtil.isNotEmpty(this.timeRangeColumn)) {
            queryWrapper
                    .ge(beginTime != null, CharSequenceUtil.toUnderlineCase(timeRangeColumn), beginTime)
                    .le(endTime != null, CharSequenceUtil.toUnderlineCase(timeRangeColumn), endTime);
        }
    }

    /**
     * 获取前端传来的排序方向  转换成MyBatisPlus所需的排序参数 boolean=isAsc
     *
     * @return 排序顺序， null为无排序
     */
    public Boolean convertSortDirection() {

        return ASC.equals(this.orderDirection);
    }

}
