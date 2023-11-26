package com.huangrx.template.mp;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.huangrx.template.core.base.BaseController;
import com.huangrx.template.core.base.BaseEntity;
import lombok.Data;

import java.util.Arrays;
import java.util.List;


/**
 * 代码生成器
 *
 * @author huangrx
 * @since 2023-11-25 13:34
 */
@Data
public class CodeGenerator {

    /**
     * 作者
     */
    private static final String AUTHOR = "huangrx";
    /**
     * 模块名
     */
    private static final String MODULE = "/template-frame";
    /**
     * 表名数据，可以用逗号分隔，也可以用 all 表示所有表
     * <p>
     * t_sys_config,t_sys_login_info,t_sys_menu,t_sys_notice,t_sys_operation_log,t_sys_post,t_sys_role,t_sys_role_menu,t_sys_user
     */
    private static final String TABLE_NAME_STR = "t_sys_dept,t_sys_config,t_sys_login_info,t_sys_menu,t_sys_notice,t_sys_operation_log,t_sys_post,t_sys_role,t_sys_role_menu,t_sys_user";
    /**
     * 表名前缀
     */
    private static final String TABLE_NAME_PREFIX = "t_";
    /**
     * 数据库连接
     */
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/huangrx-template-admin?useUnicode=true&characterEncoding=utf-8";
    /**
     * 数据库用户名
     */
    private static final String USERNAME = "root";
    /**
     * 数据库密码
     */
    private static final String PASSWORD = "123456";
    /**
     * 包名
     */
    private static final String PARENT_PACKAGE = "com.huangrx.template.mp.result";

    /**
     * 避免覆盖掉原有生成的类  生成的类 放在frame子模块下的com.huangrx.template.mp.result目录底下
     * 有需要更新的实体自己在手动覆盖  或者 挪动过去
     */
    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        generator.generateCode();
    }

    /**
     * 生成代码
     */
    public void generateCode() {
        FastAutoGenerator generator = FastAutoGenerator.create(
                dataSourceConfig()
                        // 指定类型转换器
                        .typeConvertHandler(new CustomTypeConvertHandler())
                        // 设置数据库关键字处理器
                        .keyWordsHandler(new MySqlKeyWordsHandler()));

        globalConfig(generator);
        packageConfig(generator);
        injectionConfig(generator);
        strategyConfig(generator);
        // 默认的是Velocity引擎模板
        generator.templateEngine(new VelocityTemplateEngine());
        generator.execute();
    }

    /**
     * 数据库连接
     *
     * @return DataSourceConfig.Builder
     */
    private DataSourceConfig.Builder dataSourceConfig() {
        return new DataSourceConfig.Builder(DATABASE_URL, USERNAME, PASSWORD);
    }


    /**
     * 为了避免  覆盖掉service中的方法
     *
     * @param generator 生成器
     */
    private void globalConfig(FastAutoGenerator generator) {
        generator.globalConfig(
                builder -> builder
                        // huangrx-admin-template-server + module + /src/main/java
                        .outputDir(System.getProperty("user.dir") + MODULE + "/src/main/java")
                        // use date type under package of java utils
                        .dateType(DateType.ONLY_DATE)
                        // 配置生成文件中的 author
                        .author(AUTHOR)
                        // 自动生成swagger注解
                        .enableSwagger()
                        // 时间类型对应策略
                        .dateType(DateType.TIME_PACK)
                        // 注释日期的格式
                        .commentDate("yyyy-MM-dd")
                        .build());
    }


    /**
     * 包配置
     *
     * @param generator 生成器
     */
    private void packageConfig(FastAutoGenerator generator) {
        generator.packageConfig(builder -> builder
                .parent(PARENT_PACKAGE)
                .entity("entity")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("mapper.xml")
                .controller("controller")
                .build());
    }

    private void injectionConfig(FastAutoGenerator generator) {
        //  注入配置
        generator.injectionConfig(builder -> {
            // 输出文件之前消费者
            builder.beforeOutputFile((tableInfo, objectMap) ->
                            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size()))
                    .build();
        });
    }


    /**
     * 策略配置
     *
     * @param generator 生成器
     */
    private void strategyConfig(FastAutoGenerator generator) {
        generator.strategyConfig(builder -> {
            builder.addInclude(getTables())
                    // 设置表名前缀
                    .addTablePrefix(TABLE_NAME_PREFIX)
                    // 开启全局大写命名
                    .enableCapitalMode();

            entityConfig(builder);
            controllerConfig(builder);
            serviceConfig(builder);
            mapperConfig(builder);
        });
    }


    private void entityConfig(StrategyConfig.Builder builder) {
        Entity.Builder entityBuilder = builder.entityBuilder();
        // 实体构建
        entityBuilder
                // 继承BaseEntity  请注释掉以下两行
                .superClass("com.huangrx.template.core.BaseEntity")
                // 开启Lombok
                .enableLombok()
                // 开启生成实体时生成字段注解
                .enableTableFieldAnnotation()
                // 开启 ActiveRecord 模式
                .enableActiveRecord()
                // 添加逻辑删除字段
                .logicDeleteColumnName("deleted")
                // 数据库表映射到实体的命名策略
                .naming(NamingStrategy.underline_to_camel)
                // 数据库表字段映射到实体的命名策略
                .columnNaming(NamingStrategy.underline_to_camel)
                // 指定生成的主键的ID类型 AUTO, NONE, INPUT, ASSIGN_ID, ASSIGN_UUID
                .idType(IdType.AUTO)
                .addSuperEntityColumns("creator_id", "create_time", "updater_id", "update_time", "deleted")
                .addIgnoreColumns("age")
                // 添加表字段填充
                .addTableFills(
                        new Column("creator_id", FieldFill.INSERT),
                        new Column("create_time", FieldFill.INSERT),
                        new Column("updater_id", FieldFill.INSERT_UPDATE),
                        new Column("update_time", FieldFill.INSERT_UPDATE)
                )
                // 覆盖已有文件
                .enableFileOverride()
                .build();
    }


    private void controllerConfig(StrategyConfig.Builder builder) {
        builder.controllerBuilder()
                // 父类控制器
                .superClass(BaseController.class)
                // 开启驼峰转连字符
                .enableHyphenStyle()
                // 开启RestController
                .enableRestStyle()
                // 格式化文件名称
                .convertFileName(entityName -> entityName + ConstVal.CONTROLLER)
                // 覆盖已有文件
                .enableFileOverride()
                .build();
    }

    private void serviceConfig(StrategyConfig.Builder builder) {
        builder.serviceBuilder()
                // 转换输出service接口文件名称
                .convertServiceFileName(entityName -> "I" + entityName + ConstVal.SERVICE)
                // 转换输出service实现类文件名称
                .convertServiceImplFileName(entityName -> entityName + ConstVal.SERVICE_IMPL)
                // 覆盖已有文件
                .enableFileOverride()
                .build();
    }

    private void mapperConfig(StrategyConfig.Builder builder) {
        builder.mapperBuilder()
                // 转换Xml文件名称处理
                .convertXmlFileName(entityName -> entityName + ConstVal.MAPPER)
                // 输出Mapper文件名称转换
                .convertMapperFileName(entityName -> entityName + ConstVal.MAPPER)
                // 覆盖已有文件
                .enableFileOverride()
                .build();
    }

    /**
     * 处理 多个表的 情况
     *
     * @return 表名
     */
    protected static List<String> getTables() {
        return Arrays.asList(TABLE_NAME_STR.split(","));
    }
}