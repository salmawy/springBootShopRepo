/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gomalmarket.shop.modules.Customer.discharge.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.glyphfont.FontAwesome;
import org.springframework.context.ApplicationContext;

import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.entities.customers.CustomerOrder;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.validator.Validator;
import com.gomalmarket.shop.modules.Customer.action.CustomerBaseAction;
import com.gomalmarket.shop.modules.Customer.discharge.view.beans.CustomerViewBean;
import com.gomalmarket.shop.modules.Customer.discharge.view.edit.EditCustomerOrderView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author ahmed
 */
public class InitCustomerDischargePresenter extends CustomerBaseAction  implements Initializable {
    
	Logger logger = Logger.getLogger(this.getClass().getName());	

	 @FXML
	    private AnchorPane gridLoc;
    private GridPane gridPane;

    private CustomTable<CustomerViewBean> gride;
    private JFXDatePicker datePicker;
    private   JFXTextField name_TF;
    private   JFXTextField phone;
    private   JFXTextField address;
    private   JFXTextField grossWeight_TF;
    private   JFXTextField count_TF;
    private   JFXTextField nolun_TF;
    private   JFXTextField gift_TF;
    private   JFXTextField code_TF;
    private   JFXTextArea notes_TA;
    private Validator myValidator;
    private  JFXComboBox<ComboBoxItem> productTyp_CB;
    private  JFXComboBox<ComboBoxItem> storeLocation_CB;
    private   JFXComboBox<ComboBoxItem> vehicleType_CB;
    private JFXComboBox<ComboBoxItem> cutomerBox;
    private Image errIcon;
    private  JFXDatePicker bookDatePicker;
   
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	errIcon=loadImage("icons/error-icon.png");
  	  logger.log(Level.INFO,"============================================================================================================");

    	
   	init();
   	getBook();
  }
    
    
    
    private void init()  {
    	
    	
    	
		 
   
		  List<Column> columns=prepareTabelColumns(); 
		  List<JFXButton> buttons=prepareheaderNodes();
 		  gride=new CustomTable<CustomerViewBean>(columns,buttons,null,null,null,CustomTable.headTableCard,CustomerViewBean.class); 
		  gride.getCutomTableComponent().setPrefHeight(400);
   
		  fitToAnchorePane(gride.getCutomTableComponent());
		
		  gridLoc.getChildren().setAll(gride.getCutomTableComponent());
 		 
    	
    }
     
    
    
    private List<Column> prepareTabelColumns(){
    
    
         List<Column> columns=new ArrayList<Column>();
       
         
   
       
         
         Column   c=new Column(this.getMessage("customer.name"), "customerName", "string", 20, true);
           columns.add(c);
        
           c=new Column(this.getMessage("label.customer.Type"), "customerType", "string", 10, true);
           columns.add(c); 
           
          c=new Column(this.getMessage("label.nolun"), "nowlun", "double", 10, true);
           columns.add(c);
          
           
           c=new Column(this.getMessage("lanel.quantity"), "quantity", "double", 15, true);
           columns.add(c);
          
           
           c=new Column(this.getMessage("label.count"), "count", "int", 15, true);
           columns.add(c);
          
           c=new Column(this.getMessage("label.gift"), "gift", "double", 15, true);
           columns.add(c);
          
           c=new Column(this.getMessage("label.store.name"), "storeName", "string", 10, true);
           columns.add(c);
          
           c=new Column(this.getMessage("finished"), "finishedLabel", "string", 5, true);
           columns.add(c);
    
    
    return columns;
    
    
    
    
    
    
    }
    
    
    private List prepareheaderNodes(){
    	
    	JFXButton addBtn=new JFXButton(this.getMessage("button.add"));
    	addBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));
    	addBtn.getStyleClass().setAll("btn","btn-primary");  
	    
    	addBtn.setOnAction(e -> {
			  
	    	addEditCustomerOrder(0);
			  
			  
			  
		  });
	  //----------------------------------------------------------------------------- 
    	JFXButton editBtn=new JFXButton(this.getMessage("button.edit"));
     	    editBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.EDIT));
    	    editBtn.getStyleClass().setAll("btn","btn-primary");  
    	    
    	    editBtn.setOnAction(e -> {
    	    	if(!this.gride.getTable().getSelectionModel().isEmpty())
    	    	{
    	    		CustomerViewBean item=(CustomerViewBean) this.gride.getTable().getSelectionModel().getSelectedItem();
        	    	addEditCustomerOrder(item.getOrderId());

    	    	}

  			  
  			  
  			  
  		  });
    	  //-----------------------------------------------------------------------------  
    	     bookDatePicker=new JFXDatePicker();
    	    bookDatePicker.setPadding(new Insets(0, 0, 0, 100));

    	    bookDatePicker.getEditor().setAlignment(Pos.CENTER);
    	    bookDatePicker.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    	    bookDatePicker.setConverter(new StringConverter<LocalDate>()
    	   	{
    	   	    private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");

    	   	    @Override
    	   	    public String toString(LocalDate localDate)
    	   	    {
    	   	        if(localDate==null)
    	   	            return "";
    	   	        return dateTimeFormatter.format(localDate);
    	   	    }

    	   	    @Override
    	   	    public LocalDate fromString(String dateString)
    	   	    {
    	   	        if(dateString==null || dateString.trim().isEmpty())
    	   	        {
    	   	            return null;
    	   	        }
    	   	        return LocalDate.parse(dateString,dateTimeFormatter);
    	   	    }
    	   	});    

    	    bookDatePicker.setOnAction(e -> {
    	   		
    	    	 LocalDate localate =bookDatePicker.getValue();
    	        Instant instant=Instant.from(localate.atStartOfDay(ZoneId.systemDefault()));
    	    	
    	    	Date date= Date.from(instant);
    	   			loadData(date);
    	   		});
    			

    	    //editBtn.getStyleClass().add("control-button");
    	    List nodes =new ArrayList(Arrays.asList(addBtn,editBtn,bookDatePicker))  ;

    	return nodes;
    	
    }


List <CustomerViewBean> loadData(Date date) {
	
	this.gride.getTable().getItems().clear();

		 
	List customerOrders=new ArrayList<>();
	List customerViewBeans=new LinkedList<>();
	try {
			 customerOrders = this.getCustomerService().getCustomerOrders(date);
				
		

		for (Object it : customerOrders) {
			CustomerOrder order=(CustomerOrder) it;
			CustomerViewBean viewBean=new CustomerViewBean();
			viewBean.setOrderId(order.getId());
			viewBean.setCount(order.getUnits());
			viewBean.setCustomerName(order.getCustomer().getName());
			viewBean.setFinished(order.getFinished());
			viewBean.setCustomerType(order.getCustomer().getType().getName());

			viewBean.setFinishedLabel((order.getFinished()==1)?getMessage("label.yes"):getMessage("label.no"));

			viewBean.setGift(order.getTips());
			viewBean.setQuantity(order.getGrossweight());
			viewBean.setNowlun(order.getNolun());
			viewBean.setProductName(order.getProduct().getName());
			viewBean.setStoreName("1");
			
			customerViewBeans.add(viewBean);

			
		}
		
		this.gride.loadTableData(customerViewBeans);

	} catch (EmptyResultSetException e1) {
		// TODO Auto-generated catch block
		alert(AlertType.WARNING, "", "", this.getMessage("msg.warning.noData"));
	} catch (DataBaseException e1) {
		// TODO Auto-generated catch block
		alert(AlertType.ERROR, "", "", this.getMessage("msg.err.cannot.load.data"));
	}
	
	
	
	return customerViewBeans;
} 
private void fitToAnchorePane(Node node) {
	
	
	AnchorPane.setTopAnchor(node,  0.0); 
	AnchorPane.setLeftAnchor(node,  0.0); 
	AnchorPane.setRightAnchor(node,  0.0); 
	AnchorPane.setBottomAnchor(node,  0.0); 
	
	
	
} 
    private Image loadImage(String path) {
    	
    	Image icn=null;
    	
    	 
   	 try {
   			File file = new File(getClass().getClassLoader().getResource(path).getFile());
   			
   			
   			icn=new Image(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	 
    	
    	
    	return 	 icn;
    	
    	
    	
    }
    
    

    boolean validateForm() {

       String customerName=name_TF.getText();
        String noloun = nolun_TF.getText();
        String tips = gift_TF.getText();
        if (customerName.isEmpty()||noloun.isEmpty()||tips.isEmpty()) {
        	alert(AlertType.INFORMATION, "", "", this.getMessage("msg.err.required.values"));
            return false;
        } 

        double d_tips = Double.parseDouble(tips);
        double nol = Double.parseDouble(noloun);
        if (!vallidateSafeWithdrawal(nol+d_tips)) {
        	
        	alert(AlertType.INFORMATION, "", "", this.getMessage("msg.err.notEnough.safeBalance"));

            return false;
        } 

        return true;

    }

  
    
    
    
    private void alert(AlertType alertType,String title,String headerText,String message) {
		 Alert a = new Alert(alertType);
		 a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		 a.setTitle(title );
		 a.setHeaderText(headerText);
		 a.setContentText(message); 
        a.show(); 
	  
 }
    
    
    
    
    private boolean vallidateSafeWithdrawal(double amount) {
    	
    double balance=0.0;
	 
		
		
		balance = this.getExpansesService().getSafeBalance(getAppContext().getSeason());
	 
    if (amount > balance) {

        return false;
    }
    return true;
    	
    }
 
    
    private Date fromLocalDateToDate(LocalDate localDate) {
    	
    	
        Instant instant=Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
    	
    	return Date.from(instant);
    	
    }
    
    public void inatiatePage() {
        
        
        this.name_TF.setText("");
        this.nolun_TF.setText("");
        this.notes_TA.setText("");
        this.grossWeight_TF.setText("");
        this.count_TF.setText("");
        this.gift_TF.setText("");
        code_TF.setText("");
        this.loadData(this.getBaseService().convertToDateViaInstant(datePicker.getValue()));

    }
    
    
    
    private void getBook() {
    	
    	

    	
    	try {
    		Date date= (Date) this.getBaseService().aggregate("CustomerOrder", "max", "orderDate", null);
    		
    		loadData(date);
    		
    		
      	    this.bookDatePicker.setValue(getBaseService().convertToLocalDateViaMilisecond(new Date()));
     	
    	} catch (DataBaseException | EmptyResultSetException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
        	
    }
    private void addEditCustomerOrder(int orderId) {
    	
     	
    	this.request=new HashMap<String,Object>();
    	request.put("orderId", orderId);
    	
    	
    	EditCustomerOrderView form=new EditCustomerOrderView();
    	URL u=	 getClass().getClassLoader().getResource("appResources/custom.css");

    	Scene scene1= new Scene(form.getView(), 1000, 400);
    	Stage popupwindow=new Stage();
    	popupwindow.setMinHeight(400);
    	popupwindow.setMinWidth(900);

    	popupwindow.setResizable(true);
        String css =u.toExternalForm();
    	scene1.getStylesheets().addAll(css); 
    	popupwindow.initModality(Modality.APPLICATION_MODAL);
    	
    	String title=(orderId==0)?getMessage("msg.info.add.customerOrder"):getMessage("msg.info.edit.customerOrder"); 
    	
    	popupwindow.setTitle(title);
    	      
    	popupwindow.setScene(scene1);
    	popupwindow.setOnHiding( ev -> {
    		
try {
    		System.out.println("window closes");
    		boolean valid=(boolean) this.response.get("valid");
    		
    		if(valid)
    			loadData(fromLocalDateToDate(bookDatePicker.getValue()));
        
}catch (Exception e) {
	// TODO: handle exception
}
    		
    	});
    	
    	popupwindow.showAndWait();

    	
    	
    }
}
