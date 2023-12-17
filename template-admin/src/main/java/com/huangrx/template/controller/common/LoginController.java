package com.huangrx.template.controller.common;

import com.huangrx.template.core.dto.ResponseDTO;
import com.huangrx.template.dto.AddUserDTO;
import com.huangrx.template.dto.RouterDTO;
import com.huangrx.template.security.utils.SecurityUtils;
import com.huangrx.template.service.ISysMenuService;
import com.huangrx.template.service.ISysUserService;
import com.huangrx.template.user.base.SystemLoginUser;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huangrx
 * @since 2023/12/8 16:04
 */
@RestController
public class LoginController {

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysMenuService menuService;

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

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public ResponseDTO<List<RouterDTO>> getRouters() {
        SystemLoginUser loginUser = SecurityUtils.getSystemLoginUser();
        List<RouterDTO> routerTree = menuService.getRouterTree(loginUser);
        return ResponseDTO.success(routerTree);
    }

}
