package com.gomalmarket.shop.core.config;


import java.util.ResourceBundle;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.basic.User;
import com.gomalmarket.shop.core.entities.repos.RepoSupplier;

import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Configuration
@Getter
@Setter
@Slf4j
@Qualifier("ShopAppContext")
public class ShopAppContext {
    private Season season;
    private Fridage fridage;
    private User currentUser;
    private Stage AppStage;
    private float customerOrderRatio;
    
    
    
    @Autowired
    RepoSupplier repoSupplier;
    
    private Stage loginStage;
    
    @Autowired
    private ResourceBundle messages;

    @Autowired
    private ApplicationContext applicationContext;
  
    @Autowired
    private DataSource dataSource;
    @Bean 
    public ResourceBundle messages() {
    	ResourceBundle bundle=ResourceBundle.getBundle("appResources.myBundel_ar");
    	log.info("login.username => " +bundle.getString("login.username"));
        return bundle;
    }

    
    
    public Object getBean(String name) {
    	
    	
    	return applicationContext.getBean(name);
    	
    	
    }
 
    @Autowired
    EntityManagerFactory emf;
   
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(emf);
        tm.setDataSource(dataSource);
        return tm;
    }
    
}
