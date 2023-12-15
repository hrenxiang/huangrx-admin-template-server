package com.huangrx.template.user.vo;

import com.huangrx.template.user.base.SystemLoginUser;
import com.huangrx.template.user.dto.TokenDTO;
import lombok.Data;

/**
 * <p> 登录响应 </p>
 *
 * @author huangrx
 * @since 2023/12/15 13:31
 */
@Data
public class LoginVO {

    private TokenDTO token;

    private LoginUserVO user;

}
