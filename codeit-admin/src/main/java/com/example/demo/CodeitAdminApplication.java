package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

// Spring Data JPA 라이브러리를 사용한다면 DataSource 데이터베이스 연결을 위한 설정을 찾는다 = 이 자동 설정을 제외한다
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CodeitAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeitAdminApplication.class, args);
	}

}
