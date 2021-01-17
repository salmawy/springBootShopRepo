package com.gomalmarket.shop.core.UIComponents.customTable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomeTablePresenter implements Initializable  {

	
	
	 @FXML
	    private AnchorPane tableLocPane2;

	    @FXML
	    private FlowPane headpane1;

	    @FXML
	    private AnchorPane rootAnchorePan;

	    @FXML
	    private AnchorPane headTablePan;

	    @FXML
	    private AnchorPane tableLocPane1;

		public AnchorPane getTableLocPane2() {
			return tableLocPane2;
		}

		public void setTableLocPane2(AnchorPane tableLocPane2) {
			this.tableLocPane2 = tableLocPane2;
		}

		public FlowPane getHeadpane1() {
			return headpane1;
		}

		public void setHeadpane1(FlowPane headpane1) {
			this.headpane1 = headpane1;
		}

		public AnchorPane getRootAnchorePan() {
			return rootAnchorePan;
		}

		public void setRootAnchorePan(AnchorPane rootAnchorePan) {
			this.rootAnchorePan = rootAnchorePan;
		}

		public AnchorPane getHeadTablePan() {
			return headTablePan;
		}

		public void setHeadTablePan(AnchorPane headTablePan) {
			this.headTablePan = headTablePan;
		}

		public AnchorPane getTableLocPane1() {
			return tableLocPane1;
		}

		public void setTableLocPane1(AnchorPane tableLocPane1) {
			this.tableLocPane1 = tableLocPane1;
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			
		}
	
	

	
	
	
}
