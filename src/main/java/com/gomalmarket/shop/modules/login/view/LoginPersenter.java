/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gomalmarket.shop.modules.login.view;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomalmarket.shop.App;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.action.BaseAction;
import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.basic.Season;
import com.gomalmarket.shop.core.entities.basic.User;
import com.gomalmarket.shop.core.entities.repos.UserRepo;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.appcontainer.view.ApplicationView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ahmed
 */
@Slf4j

public class LoginPersenter extends BaseAction implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXTextField usernameTF;

	@FXML
	private JFXButton loginBtn;

	@FXML
	private JFXPasswordField passwordPass;

	@FXML
	private JFXButton close_btn;

	@FXML
	private JFXComboBox<ComboBoxItem> fridage_CB;

	@Autowired
	UserRepo userRepo;

	public LoginPersenter() {

		super();

	}

	@FXML
	void makeLogin(ActionEvent event) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", usernameTF.getText());
		map.put("password", passwordPass.getText());
		System.out.println(passwordPass.getText());
		System.out.println(usernameTF.getText());
		try {

//	List users= userRepo.
			User user = (User) this.getBaseService().findAllBeans(User.class).get(0);

			this.getAppContext().setCurrentUser(user);

			URL u = this.getClass().getClassLoader().getResource("appResources/custom.css");
			String css = u.toExternalForm();
			Stage application = new Stage();
			this.getAppContext().setAppStage(application);

			ApplicationView appView = new ApplicationView();

			this.getAppContext().setCurrentUser(user);
			Fridage fridage = (Fridage) fridage_CB.getSelectionModel().getSelectedItem().getValueObject();
			Season season=getBaseService().getCurrentSeason();
			this.getAppContext().setFridage(fridage);
			this.getAppContext().setSeason(season);

			Scene scene = new Scene(appView.getView(), 1000, 600);

			application.setScene(scene);

			// popupwindow.initStyle(StageStyle.TRANSPARENT);
			application.setTitle("A&D Accountant and Management System");
			scene.getStylesheets().addAll(css);
			application.initModality(Modality.WINDOW_MODAL);
			application.show();

			
			 
App.loginStage.hide();

		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void close(ActionEvent event) {
		Stage stage = (Stage) close_btn.getScene().getWindow();
		// do what you have to do
		stage.close();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		log.info(
				"============================================================================================================");

		/*
		 * usernameTF.setPromptText(loadMessagesBundle.getString("login.username"));
		 * passwordPass.setPromptText(loadMessagesBundle.getString("login.password"));
		 */

		usernameTF.setPromptText("user name");
		passwordPass.setPromptText(" password");
//========================================================================================================================================================
		List fridages;
		try {
			fridages = this.getBaseService().findAllBeans(Fridage.class);
			for (int i = 0; i < fridages.size(); i++) {
				Fridage fridage = (Fridage) fridages.get(i);
				fridage_CB.getItems().add(new ComboBoxItem(fridage.getId(), fridage.getName(), fridage));

			}
			fridage_CB.getSelectionModel().selectFirst();
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//========================================================================================================================================================

		Glyph glyph = new FontAwesome().create(FontAwesome.Glyph.SIGN_IN);
		glyph.setColor(Color.WHITESMOKE);
		loginBtn.setGraphic(glyph);
		loginBtn.setText(getMessage("label.login"));
		loginBtn.getStyleClass().setAll("btn", "btn-info", "btn-xs");

		// ------------------------------------------------------------------------
		glyph = new FontAwesome().create(FontAwesome.Glyph.CLOSE);
		glyph.setColor(Color.WHITESMOKE);
		close_btn.setGraphic(glyph);
		close_btn.setText(getMessage("label.close"));
		close_btn.getStyleClass().setAll("btn", "btn-info", "btn-xs");

	}
}