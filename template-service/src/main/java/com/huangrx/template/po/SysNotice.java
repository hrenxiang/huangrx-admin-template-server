package com.huangrx.template.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.huangrx.template.core.base.BaseEntity;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 通知公告表
 * </p>
 *
 * @author huangrx
 * @since 2023-11-26
 */
@Getter
@Setter
@TableName("t_sys_notice")
@ApiModel(value = "SysNotice对象", description = "通知公告表")
@EqualsAndHashCode(callSuper = true)
public class SysNotice extends BaseEntity<SysNotice> {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("公告ID")
    @TableId(value = "notice_id", type = IdType.AUTO)
    private Integer noticeId;

    @ApiModelProperty("公告标题")
    @TableField("notice_title")
    private String noticeTitle;

    @ApiModelProperty("公告类型（1通知 2公告）")
    @TableField("notice_type")
    private Integer noticeType;

    @ApiModelProperty("公告内容")
    @TableField("notice_content")
    private String noticeContent;

    @ApiModelProperty("公告状态（1正常 0关闭）")
    @TableField("`status`")
    private Integer status;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @Override
    public Serializable pkVal() {
        return this.noticeId;
    }
}
