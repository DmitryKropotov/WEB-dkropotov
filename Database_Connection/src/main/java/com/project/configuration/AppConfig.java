package com.project.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@EnableTransactionManagement
@ComponentScan({"com.project"})
//@ImportResource(locations={
  //      "classpath:configuration/servlet-config.xml"
//})
public class AppConfig {
}
