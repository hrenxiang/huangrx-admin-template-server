package com.huangrx.template.convert;

import com.huangrx.template.dto.MetaDTO;
import com.huangrx.template.dto.RouterDTO;
import com.huangrx.template.po.SysMenu;
import com.huangrx.template.utils.jackson.JacksonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


/**
 * 路由转换器
 *
 * @author   huangrx
 * @since   2023-12-16 18:47
 */
@Mapper
public interface RouterConvert {

    RouterConvert INSTANCE = Mappers.getMapper(RouterConvert.class);

    /**
     * SysMenu对象转换为RouterDTO
     * @param sysMenu 菜单对象
     * @return 路由对象
     */
    @Mapping(source = "routerName", target = "name")
    @Mapping(source = "path", target = "path")
    @Mapping(source = "metaInfo", target = "meta", qualifiedByName = "convertMetaInfo")
    RouterDTO sysMenuToRouterDto(SysMenu sysMenu);

    /**
     * 将metaInfo字符串转换为MetaDTO
     * @param metaInfo metaInfo字符串
     * @return MetaDTO
     */
    @Named("convertMetaInfo")
    default MetaDTO convertMetaInfo(String metaInfo) {
        MetaDTO metaDTO = new MetaDTO();
        if (JacksonUtil.isJson(metaInfo)) {
            metaDTO = JacksonUtil.parseString(metaInfo, MetaDTO.class);
        }
        return metaDTO;
    }
}