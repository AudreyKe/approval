package com.weshareholdings.approval;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@ImportResource(locations = {"classpath*:/application-snakerflow.xml"})
public class ConfigClass {

}
