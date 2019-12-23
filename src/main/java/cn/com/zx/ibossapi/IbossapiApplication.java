package cn.com.zx.ibossapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("cn.com.zx.ibossapi.mapper")
@EnableAsync
public class IbossapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(IbossapiApplication.class, args);
		System.out.println("=================================================================API系统启动成功！============================================================");
	}
}
