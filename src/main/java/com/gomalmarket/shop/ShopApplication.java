package com.gomalmarket.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableSpringConfigured
@Slf4j
@EnableTransactionManagement 
public class ShopApplication   {
	
	

	
    public static ApplicationContext applicationContext;

 

	
	
	
	
	
	
	
	public static void main(String[] args) {
		applicationContext=SpringApplication.run(ShopApplication.class, args);
        displayAllBeans();

		
		  try { App.run(args); } catch (Exception e) { 
			  // TODO Auto-generated catch block 
			  e.printStackTrace(); }
		 
	}

    public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }
 
}
