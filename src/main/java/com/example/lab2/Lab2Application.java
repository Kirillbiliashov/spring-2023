package com.example.lab2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import oracle.jdbc.pool.OracleDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

@OpenAPIDefinition(
        info = @Info(
                title = "Tale Restful Service",
                description = "This is Lab 4"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "test server"),
        }
)
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication
public class Lab2Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }
    @Bean
    DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser("KHRYSTYNA");
        dataSource.setPassword("KHRYSTYNA");
        dataSource.setURL("jdbc:oracle:thin:@localhost:1521:xe");
        dataSource.setImplicitCachingEnabled(true);
        // dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}