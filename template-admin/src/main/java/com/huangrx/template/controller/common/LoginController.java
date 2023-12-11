package com.huangrx.template.controller.common;

import com.huangrx.template.core.dto.ResponseDTO;
import com.huangrx.template.dto.AddUserDTO;
import com.huangrx.template.service.ISysUserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangrx
 * @since 2023/12/8 16:04
 */
@RestController
public class LoginController {

    @Resource
    private ISysUserService userService;

    /**
     * 用户注册接口
     *
     * @param request 用户填写信息
     * @return 是否注册成功
     */
    @PostMapping("/register")
    public ResponseDTO<Long> register(@RequestBody @Valid AddUserDTO request) {
        userService.register(request);
        return ResponseDTO.success();
    }

}
