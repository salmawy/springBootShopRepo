package com.gomalmarket.shop.core.UIComponents.titledPanel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import lombok.Getter;


@Getter
public class TitledPanelPresenter implements Initializable {
	
	@FXML
    private StackPane content_StackPane;

    @FXML
    private AnchorPane content_pane;

    @FXML
    private Label title_label;

    
    
    @FXML
    private HBox titlebox_panel;

    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
    
    
    
    
	
	

}
