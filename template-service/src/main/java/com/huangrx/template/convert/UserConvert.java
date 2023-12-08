package com.huangrx.template.convert;

import com.huangrx.template.dto.AddUserDTO;
import com.huangrx.template.po.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


/**
 * <p> UserConvert接口是转换类，用于用户类型转换。 </p>
 *
 * @author huangrx
 * @since 2023/12/8 17:03
 */
@Mapper(componentModel = "spring")
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);


    /**
     * 将AddUserDTO转换为SysUser
     *
     * @param userDTO AddUserDTO对象
     * @return SysUser对象
     */
    @Named(value = "to_user")
    SysUser toUser(AddUserDTO userDTO);

}
