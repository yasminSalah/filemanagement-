package com.javatpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.javatpoint.repository")

public class SpringBootFileManagementApplication 
{
public static void main(String[] args) 
{
SpringApplication.run(SpringBootFileManagementApplication.class, args);
}
}
