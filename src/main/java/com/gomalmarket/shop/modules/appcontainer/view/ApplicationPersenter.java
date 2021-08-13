/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gomalmarket.shop.modules.appcontainer.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import com.airhacks.afterburner.views.FXMLView;
import com.gomalmarket.shop.core.action.BaseAction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ahmed
 */
@SuppressWarnings("restriction")
@Slf4j
public class ApplicationPersenter extends BaseAction implements Initializable {


	 @FXML
	 private VBox vboxMainContainer;

	    @FXML
	    private TitledPane contractorComp;

	    @FXML
	    private Menu settings_menu;

	    @FXML
	    private HBox spinnerPane;

	    @FXML
	    private JFXButton arcSellersLoan_btn;

	    @FXML
	    private TitledPane inventoryComp;

	    @FXML
	    private JFXButton purchasesBTN;

	    @FXML
	    private JFXButton sellerDebts_btn;

	    @FXML
	    private JFXButton varaities_btn;

	    @FXML
	    private TitledPane invoicesComp;

	    @FXML
	    private JFXButton suppliers_btn;

	    @FXML
	    private AnchorPane appContainer;

	    @FXML
	    private JFXButton transactionsBTN;

	    @FXML
	    private AnchorPane anchorPane;

	    @FXML
	    private TitledPane expansesComp;

	    @FXML
	    private Accordion App_Components;

	    @FXML
	    private JFXButton dailySelling_btn;

	    @FXML
	    private Label title_label;

	    @FXML
	    private JFXSpinner spinner;

	    @FXML
	    private JFXButton sellersLoanReport_btn;

	    @FXML
	    private JFXButton dischargingBTN;

	    @FXML
	    private JFXButton notes_btn;

	    @FXML
	    private JFXButton shopDebts_btn;

	    @FXML
	    private MenuBar menuBar_mb;

	    @FXML
	    private Menu show_menu;

	    @FXML
	    private JFXButton payedInvoice_btn;

	    @FXML
	    private JFXButton payInvoice_btn;

	    @FXML
	    private TitledPane sellerComp;

	    @FXML
	    private JFXButton generateInvoice_btn;

	    @FXML
	    private StackPane mystackPane;

	    @FXML
	    private JFXButton labour_btn;

	    @FXML
	    private JFXButton contractorPerdiocReport_btn;

	    @FXML
	    private JFXButton Expanses_btn;

	    @FXML
	    private TitledPane customersComp;

	    @FXML
	    private AnchorPane appPage;

	    @FXML
	    private MenuItem saveDB_MI;
	    @FXML
	    private MenuItem settleDay_MI;
	    @FXML
	    private MenuItem changePassword_MI;
	    @FXML
	    private MenuItem restoreDB_MI;
	    @FXML
	    private MenuItem archive_MI;
	    
	    @FXML
	    private HBox buttom_box;

	    @FXML
	    private JFXButton shopArcivedDebts_btn;
	    

   //========================================================================================================================================
   Map<String,String[]> panelPathes;

	Logger logger = Logger.getLogger(this.getClass().getName());	
   
	private String title="";
  @Override
    public void initialize(URL location, ResourceBundle resources){ 
  	  logger.log(Level.INFO,"============================================================================================================");

	  
    	vboxMainContainer.heightProperty().addListener(( obs,oldvalue,newValue)->{
  		  log.info("=======================================================================================================================");
  		
   		  appPage.setPrefHeight(newValue.doubleValue());
   		  mystackPane.setPrefHeight(newValue.doubleValue());

  		  
   		  appContainer.setPrefHeight(newValue.doubleValue());
   		  
  		  log.info("heightProperty old value =>"+oldvalue);
  		  log.info("heightProperty newValue  =>"+newValue);
  			  log.info("=======================================================================================================================");

  	  });
     
	  
	  fillPanelsMap();
        intiateApp();  
    }
    

  private void intiateApp(){
	
	//  this.getAppStage().getStylesheets().add("appCssFile.css");

	  sellerComp.setText(this.getMessage("title.selling"));
	  customersComp.setText(this.getMessage("title.customers"));
	  expansesComp.setText(this.getMessage("label.expanses"));
	  contractorComp.setText(this.getMessage("label.ownerWithdrawles"));
	  invoicesComp.setText(this.getMessage("label.invoices"));
	  inventoryComp.setText(this.getMessage("label.inventory"));
	  
	  
	  purchasesBTN.setText(this.getMessage("button.purchases"));
	  transactionsBTN.setText(this.getMessage("button.transaction"));
	  dischargingBTN.setText(this.getMessage("button.discharge"));
	  
	  
	  sellerDebts_btn.setText(this.getMessage("button.sellersLoans"));
	  dailySelling_btn.setText(this.getMessage("button.dailySelling"));
	  arcSellersLoan_btn.setText(this.getMessage("button.sellersLoans"));
	  
	  
	  Expanses_btn.setText(this.getMessage("label.expanses"));
	  shopDebts_btn.setText(this.getMessage("label.shopDebts"));
	  shopArcivedDebts_btn.setText(getMessage("label.shop.loan.archived"));
	  
	  
	  labour_btn.setText(this.getMessage("button.labour"));
	  suppliers_btn.setText(this.getMessage("button.suppliers"));
	  varaities_btn.setText(this.getMessage("button.varaties"));
	  notes_btn.setText(this.getMessage("button.note"));

	  generateInvoice_btn.setText(this.getMessage("button.invoice.generate"));
	  payInvoice_btn.setText(this.getMessage("button.invoice.give"));
	  payInvoice_btn.setVisible(false);
	  payedInvoice_btn.setText(this.getMessage("button.invoice.archive"));
	  payedInvoice_btn.setVisible(false);
	  sellersLoanReport_btn.setText(getMessage("sales.report.periodic"));
	 // 
	  contractorPerdiocReport_btn.setText(getMessage("contractors.report.periodic"));
	  
	  appContainer.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	//================================================================================================================
	  Glyph glyph=new FontAwesome().create(FontAwesome.Glyph.GEAR);
	  glyph.setColor(Color.WHITE);
 	 settings_menu.setGraphic(glyph);
 	 //============================================================
 	   glyph=new FontAwesome().create( FontAwesome.Glyph.TH_LIST);
	  glyph.setColor(Color.WHITE);
 	 show_menu.setGraphic(glyph);
 	 //============================================================
 	  glyph=new FontAwesome().create( FontAwesome.Glyph.TH_LIST);
	  glyph.setColor(Color.WHITE);
 	 show_menu.setGraphic(glyph);
 	 //============================================================
 	 
 	  glyph=new FontAwesome().create( FontAwesome.Glyph.ARCHIVE);
	 glyph.setColor(Color.BLACK);
	  archive_MI.setGraphic(glyph);
 	 //============================================================
	  glyph=new FontAwesome().create( FontAwesome.Glyph.RECYCLE);
	  glyph.setColor(Color.BLACK);
	  restoreDB_MI.setGraphic(glyph);
 	 //============================================================
 	  glyph=new FontAwesome().create( FontAwesome.Glyph.SAVE);
	   glyph.setColor(Color.BLACK);
 	saveDB_MI.setGraphic(glyph);
 	 //============================================================
	  glyph=new FontAwesome().create( FontAwesome.Glyph.EDIT);
	   glyph.setColor(Color.BLACK);
	changePassword_MI.setGraphic(glyph);
	 //============================================================
	 glyph=new FontAwesome().create( FontAwesome.Glyph.FILE);
	   glyph.setColor(Color.BLACK);
	settleDay_MI.setGraphic(glyph);
	 //============================================================
	  this.getAppContext().getAppStage().setResizable(true);
	  setCardLayout(this.mystackPane); 
	//============================================================

	  Rectangle rec =new Rectangle();
 	   rec.setFill(Color.RED);
 	  rec.setWidth(30);	  
 	  rec.setWidth(20);	  
 	  rec.setLayoutX(appContainer.getLayoutX());
 	  rec.setLayoutY(appContainer.getLayoutY());
 	  TranslateTransition transition=new TranslateTransition();
 	 transition.setDuration(Duration.seconds(2));
 	transition.setToX(appContainer.getWidth()+appContainer.getLayoutX());
 	transition.setToY(appContainer.getHeight()+appContainer.getLayoutY());

 	transition.setNode(rec);	 
 	transition.play();
 	
 	appContainer.getChildren().add(rec);
  }  
     
  

private void fitToAnchorePane(Node node) {
	
	
	AnchorPane.setTopAnchor(node,  0.0); 
	AnchorPane.setLeftAnchor(node,  0.0); 
	AnchorPane.setRightAnchor(node,  0.0); 
	AnchorPane.setBottomAnchor(node,  0.0); 
	
	
	
} 





	@FXML 
	private void LoadPanel(ActionEvent event) {
		
		
		
		try {
		
		
		
		 changeTop();
		 
		 
		 
		 
		Task task = new Task < Void > () {
		 @Override public void run() {
			
			
	           System.out.println("loading => " +((Control)event.getSource()).getId());
			
			  FXMLView view=null;
			
				try {
					view = loadView( ((Control)event.getSource()).getId());

					  AnchorPane anchorPane= (AnchorPane) view.getView();
					 
					  
					  Platform.runLater(new Runnable() {

					   @Override public void run() {
							  changeTop();
							  
				

								  FadeTransition ft = new FadeTransition(Duration.millis(1500));
								  ft.setNode(anchorPane); ft.setFromValue(0.1); ft.setToValue(1);
								  ft.setCycleCount(1); ft.setAutoReverse(false); ft.play();
								 
								
							  fitToAnchorePane(anchorPane);
							  appContainer.getChildren().setAll(anchorPane);
							  title_label.setText(title);

					   }
					  });




				} catch (Exception e) {
					
					e.printStackTrace();
					  Platform.runLater(new Runnable() {

						   @Override public void run() {
							   
							   
							   
							   alert(AlertType.ERROR, ApplicationPersenter.this.getMessage("msg.err"),
									ApplicationPersenter.this.getMessage("msg.err"), 
									ApplicationPersenter.this.getMessage("msg.err.general"));

							 changeTop();}
						  });
					
				}
				
			 
			
			 }


			 @Override
			 protected Void call() throws Exception {

			  return null;
			 }
			};


			Thread t = new Thread(task);
			t.setDaemon(true);
			t.start();
			
		
		
		}catch (Exception e) {
			changeTop();
			
		}
		
	
	}
 

	
	
	private FXMLView loadView(String id ) throws ClassNotFoundException {
		String className=panelPathes.get(id)[0];
		  title=panelPathes.get(id)[1];
		
		
		try {
			

			Class<?> clazz = Class.forName(className);
			Constructor<?> ctor = clazz.getConstructor();
			Object object = ctor.newInstance( );
			return (FXMLView) object;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
		
	}
    private void fillPanelsMap() {
	
	
	
	  panelPathes=new HashMap<String,String[]>();

	 // panelPathes.put("arcSellersLoan_btn", new String [] {" com.gomalmarket.shop.modules.Customer.discharge.view.InitCustomerDischargeView",getMessage("title.customer.collecting")});
	 ;
	  panelPathes.put("dischargingBTN", new String [] {"com.gomalmarket.shop.modules.Customer.discharge.view.InitCustomerDischargeView",getMessage("title.customer.collecting")});
	  panelPathes.put("purchasesBTN", new String [] {"com.gomalmarket.shop.modules.Customer.purchases.view.CustomerPurchasesView",getMessage("title.customer.transactions")});
	  panelPathes.put("transactionsBTN", new String [] {"com.gomalmarket.shop.modules.Customer.transactions.view.TransactionsView",getMessage("title.customer.purchases")});
	  panelPathes.put("dailySelling_btn",new String [] { "com.gomalmarket.shop.modules.sales.view.DailySalesView",getMessage("title.seller.dailySales")});
	  panelPathes.put("sellerDebts_btn", new String [] {"com.gomalmarket.shop.modules.sales.debt.view.DebtsView",getMessage("title.seller.debts")});
	  
	  panelPathes.put("Expanses_btn", new String [] {"com.gomalmarket.shop.modules.expanses.view.expanses.ExpansesView",getMessage("title.expanses.Safe")});
	  panelPathes.put("shopDebts_btn",new String [] { "com.gomalmarket.shop.modules.expanses.view.loan.LoansView",getMessage("title.expanses.shopDebts")});
	  panelPathes.put("shopArcivedDebts_btn",new String [] { "com.gomalmarket.shop.modules.expanses.view.archivedLoans.ArchivedLoansView",getMessage("title.expanses.shopDebts")});
	  
	  panelPathes.put("notes_btn", new String [] {"com.gomalmarket.shop.modules.contractor.view.notes.NotesView",getMessage("title.withdrawls.notes")});
	  panelPathes.put("varaities_btn",new String [] { "com.gomalmarket.shop.modules.contractor.view.varaities.VaraityView",getMessage("title.withdrawls.varaities")});
	  panelPathes.put("suppliers_btn",new String [] { "com.gomalmarket.shop.modules.contractor.view.suppliers.SupplierView",getMessage("title.withdrawls.suppliers")});
	  panelPathes.put("generateInvoice_btn",new String [] { "com.gomalmarket.shop.modules.billing.view.generateInvoice.InitGenerateInvoiceView",getMessage("title.invoice.generate")});
	  panelPathes.put("labour_btn", new String [] {"com.gomalmarket.shop.modules.contractor.view.labours.LabourView",getMessage("title.withdrawls.labur")});
	  
	  panelPathes.put("payInvoice_btn", new String [] {"com.gomalmarket.shop.modules.billing.view.invoicePayment.InvoicePaymentView",getMessage("title.invoice.generate")});
	  panelPathes.put("sellersLoanReport_btn", new String [] {"com.gomalmarket.shop.modules.sales.reports.view.periodicReport.PeriodicReportView",getMessage("sales.report.periodic")});
	  panelPathes.put("contractorPerdiocReport_btn",new String [] { "com.gomalmarket.shop.modules.contractor.view.periodicReport.PeriodicReportView",getMessage("contractors.report.periodic")});

 
	  
	 
	
	
	
	
}
	private void changeTop() {
    ObservableList<Node> childs = this.mystackPane.getChildren();
    mystackPane.setBackground(Background.EMPTY);

try {
    if (childs.size() > 1) {
        //
        Node topNode = childs.get(childs.size()-1);
        topNode.toBack();
    }
    
}catch (Exception e) {
	e.printStackTrace();
	
}
}
	private void alert(AlertType alertType,String title,String headerText,String message) {
	 Alert a = new Alert(alertType);
	 a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	 a.setTitle(title );
	 a.setHeaderText(headerText);
	 a.setContentText(message); 
    a.show(); 
 
}






}
