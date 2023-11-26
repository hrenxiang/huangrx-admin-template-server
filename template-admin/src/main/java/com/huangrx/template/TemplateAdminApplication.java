package com.huangrx.template;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动程序
 *
 * @author huangrx
 * @since 2023/11/22 23:27
 */
@SpringBootApplication
@MapperScan("com.huangrx.template.mapper")
public class TemplateAdminApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(TemplateAdminApplication.class, args);
        String ip = InetAddress.getLocalHost().getHostAddress();
        Environment environment = configurableApplicationContext.getBean(Environment.class);
        String port = environment.getProperty("server.port");
        String baseUrl = environment.getProperty("server.servlet.context-path");

        System.out.println("\n" +
                "                     ,--,                                                       \n" +
                "  .--.--.          ,'_ /|                                 .--.--.    .--.--.    \n" +
                " /  /    '    .--. |  | :    ,---.     ,---.     ,---.   /  /    '  /  /    '   \n" +
                "|  :  /`./  ,'_ /| :  . |   /     \\   /     \\   /     \\ |  :  /`./ |  :  /`./   \n" +
                "|  :  ;_    |  ' | |  . .  /    / '  /    / '  /    /  ||  :  ;_   |  :  ;_     \n" +
                " \\  \\    `. |  | ' |  | | .    ' /  .    ' /  .    ' / | \\  \\    `. \\  \\    `.  \n" +
                "  `----.   \\:  | : ;  ; | '   ; :__ '   ; :__ '   ;   /|  `----.   \\ `----.   \\ \n" +
                " /  /`--'  /'  :  `--'   \\'   | '.'|'   | '.'|'   |  / | /  /`--'  //  /`--'  / \n" +
                "'--'.     / :  ,      .-./|   :    :|   :    :|   :    |'--'.     /'--'.     /  \n" +
                "  `--'---'   `--`----'     \\   \\  /  \\   \\  /  \\   \\  /   `--'---'   `--'---'   \n" +
                "                            `----'    `----'    `----'                          \n" +
                "\n\n" +
                "[管理平台启动成功,后端服务地址：" + "http://" + ip + ":" + port + baseUrl + "]");
    }

}
