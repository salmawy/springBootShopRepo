
package com.gomalmarket.shop;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.gomalmarket.shop.core.service.impl.BaseService;
import com.gomalmarket.shop.modules.login.view.LoginView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application  {
	
 
 public static 	Stage	loginStage;

@Override
public void start(Stage primaryStage) {
this.loginStage =primaryStage;
 


	loginStage.setTitle("FXML Login Sample");
	loginStage.setResizable(false);
	loginStage.initStyle(StageStyle.TRANSPARENT);
	loginStage.setMinWidth(300);

	URL u=	 getClass().getClassLoader().getResource("appResources/custom.css");
	String css =u.toExternalForm();


	System.out.println(primaryStage.getMaxHeight());

	LoginView login = new LoginView();

	Scene scene = new Scene(login.getView(), 466, 309);
	scene.getStylesheets().addAll(css);

	//   scene.getStylesheets().add(css); //(3)

	loginStage.setScene(scene);
	loginStage.sizeToScene();
	loginStage.show();

}




	public static void run(String... args) throws Exception {

		launch(args);
		
	}

}