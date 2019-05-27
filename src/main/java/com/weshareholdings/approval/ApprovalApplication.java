package com.weshareholdings.approval;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.util.Properties;

@SpringBootApplication
@MapperScan("com.weshareholdings.approval.repository")
@EnableTransactionManagement
// @CasClient
@Slf4j
public class ApprovalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApprovalApplication.class, args);
    }

    /**
     * 配置mybatis的分页插件pageHelper
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        //配置postgresql数据库的方言支持Oracle,Mysql,MariaDB,SQLite,Hsqldb,PostgreSQL六种数据库
        properties.setProperty("dialect", "postgresql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        log.info("进入文件上传配置。。");
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大//KB,MB
        factory.setMaxFileSize("16000KB");
        // 设置总上传数据总大小
        factory.setMaxRequestSize("102400KB");
        // 设置临时目录
        String location = System.getProperty("user.dir") + "/data/temp";
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        log.info("建立临时目录：" + location);
        return factory.createMultipartConfig();
    }
}
