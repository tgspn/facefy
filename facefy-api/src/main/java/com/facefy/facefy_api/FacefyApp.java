package com.facefy.facefy_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class FacefyApp 
{
    public static void main( String[] args )
    {
        SpringApplication.run(FacefyApp.class, args);
    }
}
