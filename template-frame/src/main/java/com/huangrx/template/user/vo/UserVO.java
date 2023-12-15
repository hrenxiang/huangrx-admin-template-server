package com.huangrx.template.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p> 用户详细信息VO </p>
 *
 * @author huangrx
 * @since 2023/12/15 13:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    private Long userId;

    private Long postId;

    private String postName;

    private Long roleId;

    private String roleName;

    private Long deptId;

    private String deptName;

    private String username;

    private String nickname;

    private Integer userType;

    private String email;

    private String phoneNumber;

    private Integer sex;

    private String avatar;

    private Integer status;

    private String loginIp;

    private LocalDateTime loginDate;

    private Long creatorId;

    private String creatorName;

    private LocalDateTime createTime;

    private Long updaterId;

    private String updaterName;

    private LocalDateTime updateTime;

    private String remark;

}
