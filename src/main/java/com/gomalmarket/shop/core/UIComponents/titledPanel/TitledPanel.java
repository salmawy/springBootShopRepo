package com.gomalmarket.shop.core.UIComponents.titledPanel;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;


 
public class TitledPanel {
	
	private StackPane rootStackPane;
	
	private AnchorPane contentPane;
	private StackPane intermidiateStackPane;
	private Label titleLabel;
	private TitledPanelView titledPanelView;
	public TitledPanel(String title,Node content) {
		 titledPanelView=new TitledPanelView();
		 this.rootStackPane=(StackPane) titledPanelView.getView();
		 this.titleLabel=(Label) ((HBox) rootStackPane.getChildren().get(0)).getChildren().get(0);
		 this.intermidiateStackPane=(StackPane) rootStackPane.getChildren().get(1);
		 this.contentPane=(AnchorPane) intermidiateStackPane.getChildren().get(0);
		 
		 this.titleLabel.setText(title);
		 fitToAnchorePane(content);
		 this.contentPane.getChildren().addAll(content);
		 
		 
		 
	}
	
	
	private void fitToAnchorePane(Node node) {

		AnchorPane.setTopAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setBottomAnchor(node, 0.0);

	}
	
	
	public StackPane getRootStackPane() {
		return rootStackPane;
	}


	public AnchorPane getContentPane() {
		return contentPane;
	}


	public Label getTitleLabel() {
		return titleLabel;
	}


	

}
