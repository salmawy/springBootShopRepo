package com.gomalmarket.shop.core.action;


import java.lang.reflect.Constructor;
import java.util.ResourceBundle;

import org.springframework.beans.factory.BeanFactory;

import com.gomalmarket.shop.ShopApplication;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.service.IBaseService;
import com.gomalmarket.shop.core.service.impl.BaseService;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Getter
@Slf4j
@Setter
public class BaseAction {
	

 
	private BeanFactory springBeanFactory;
 
	IBaseService baseService;

 private ResourceBundle messages;
 
private ShopAppContext  appContext;




  public   static   StackPane cardLayout;

	public BaseAction() {
	
		appContext=	(ShopAppContext) ShopApplication.applicationContext.getBean("shopAppContext");
		springBeanFactory=appContext.getApplicationContext();
		messages=appContext.getMessages();
		log.info("Base Action=============== "+ messages.getString("login.username"));	
		baseService=(BaseService) appContext.getBean("baseService");
		log.info("Base Service has been loaded succeffully =============== ");	




	}



public String getMessage(String keyMessage){

		return this.messages.getString(keyMessage);
}

	public void loadScene(String className) {
		
		try{Class<?> clazz = Class.forName(className);
		Constructor<?> ctor = clazz.getConstructor(String.class);
		Object object = ctor.newInstance();
		
		
	}catch(Exception e) {
		
		
		e.printStackTrace();
		
		
		
	}}












	
	
	public void 	toggelLoadingView() {

	    ObservableList<Node> childs = cardLayout.getChildren();
	   // cardLayout.setBackground(Background.EMPTY);


	    if (childs.size() > 1) {
	        //
	        Node topNode = childs.get(childs.size()-1);
	        topNode.toBack();
	    }

		
		
	}






	public StackPane getCardLayout() {
		return cardLayout;
	}



	public void setCardLayout(StackPane cardLayout) {
		this.cardLayout = cardLayout;
	}

	
	
	
	
	

}
