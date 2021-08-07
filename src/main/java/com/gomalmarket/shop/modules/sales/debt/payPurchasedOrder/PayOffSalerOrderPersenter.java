package com.gomalmarket.shop.modules.sales.debt.payPurchasedOrder;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.glyphfont.FontAwesome;

import com.gomalmarket.shop.core.entities.basic.Fridage;
import com.gomalmarket.shop.core.entities.sellers.SellerLoanBag;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.modules.sales.action.SalesAction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PayOffSalerOrderPersenter extends SalesAction implements Initializable {
	
	
	    Logger logger = Logger.getLogger(this.getClass().getName());	
	    
	    @FXML
	    private Label title_label;

	    @FXML
	    private JFXButton cancel_btn;

	    

	    @FXML
	    private JFXTextField name_TF;

	    @FXML
	    private Label name_label;

	    @FXML
	    private Label amount_label;

	    @FXML
	    private JFXTextArea note_TA;

	    @FXML
	    private JFXTextField amount_TF;

	    @FXML
	    private Label date_label;

	    @FXML
	    private AnchorPane root_pane;

	   
	    @FXML
	    private Pane coloredPane;

	    @FXML
	    private HBox datePicker_loc;

	    @FXML
	    private JFXButton saveBtn;
	    
	    
	    private JFXDatePicker datePicker;
	    private JFXSnackbar snackBar;
	    private final int add=1;
	    private final int edit=2;

		private int sellerId;
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	  	  logger.log(Level.INFO,"============================================================================================================");

		init();
	}
	
	  
	private void init() {
		
//============================================================================================================

	  	datePicker=new JFXDatePicker();
	   	datePicker.getEditor().setAlignment(Pos.CENTER);
	   	datePicker.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	   	datePicker.setConverter(new StringConverter<LocalDate>()
	   	{
	   	    private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy/MM/dd");

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
	   	datePicker_loc.getChildren().add(datePicker);
	   	
//============================================================================================================
	   	amount_label.setText(this.getMessage("label.money.amount"));
	   	name_label.setText(this.getMessage("label.name"));
	    date_label.setText(this.getMessage("label.date"));
	    note_TA.setPromptText(this.getMessage("label.notes"));
	    
	    saveBtn.setText(this.getMessage("button.save"));
  	    saveBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SAVE));
	    saveBtn.getStyleClass().setAll("btn","btn-primary");  
	    saveBtn.setOnAction(e -> {
	    	try {
				save();
				
				
			} catch ( DataBaseException e1) {
 		    	snackBar.show(this.getMessage("msg.err.general"),1000);
				logger.log(Level.WARNING ,e1.getMessage());
			}
	    	
	    });	 
	    
	    
	    cancel_btn.setText(this.getMessage("button.cancel"));
 	    cancel_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.CLOSE));
	    cancel_btn.getStyleClass().setAll("btn","btn-danger");  
	    cancel_btn.setOnAction(e -> {
	    	cancel();
	    	
	    });	
	    
//============================================================================================================
	    
        title_label.setText(getMessage("button.sellers.payOffOrders"));
        title_label.setMinWidth(200);
 		coloredPane.setStyle("-fx-background-color: #00A65A");
  
 //============================================================================================================
	    snackBar=new JFXSnackbar(root_pane);
	    
	  int action=(int) request.get("action");
	  String name=(String) request.get("name");
	   sellerId=(int) request.get("sellerId");

	  name_TF.setText(name);
	  switch (action) {
		/*
		 * case add:
		 * 
		 * break;
		 */
case edit:
	
	double amount=(double) request.get("amount");
	  String notes=(String) request.get("notes");
	  Date payDate=	(Date) request.get("payDate");
	  
	  this.amount_TF.setText(String.valueOf(amount));
	  this.note_TA.setText(notes);
	  this.datePicker.setValue(this.getBaseService().convertToLocalDateViaMilisecond(payDate));
	  
	
		
		break;
	default:
		break;
	}
	    
	    
//============================================================================================================

	}

   private void cancel() {
		
	   

	      Stage stage = (Stage) cancel_btn.getScene().getWindow();
	      // do what you have to do
	      stage.close();
	   
	   
	}


private  boolean validateForm() throws DataBaseException {
    	
 	    String amount =amount_TF.getText();
	    
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("sellerId", sellerId);
		map.put("seasonId", getAppContext().getSeason().getId());
        SellerLoanBag loan=this.getSalesService().getSellerLoanBag(sellerId,  getAppContext().getSeason().getId());
       
        if (amount.isEmpty()){
    	    
       	
    	snackBar.show(this.getMessage("msg.err.required.amount"), 1000);

       return false;
       
       }
       
        
        
        if (datePicker.getValue()==null) {
        	snackBar.show(this.getMessage("msg.err.required.date"), 1000);

               return false;

           }
        

       
     
      
       if (Double.parseDouble(amount) > loan.getDueLoan()) {
    	snackBar.show(this.getMessage("msg.err.input.amount.greather")+" : "+loan.getDueLoan(),1000);

           return false;
       }
   
    	
    	return true;
    	
    }


	private void save() throws NumberFormatException, DataBaseException {
		if(validateForm()) {
	        SellerLoanBag loanBag=this.getSalesService().getSellerLoanBag(sellerId,  getAppContext().getSeason().getId());

			double amount=Double.parseDouble(amount_TF.getText());
			Date date=this.getBaseService().convertToDateViaInstant(datePicker.getValue());
			String notes=note_TA.getText();
			
			int seasonId= getAppContext().getSeason().getId();
			Fridage fridage= getAppContext().getFridage();
		 
				try {
					this.getSalesService().saveSellerInstalment(sellerId, 0, loanBag.getId(), fridage,getAppContext().getSeason(), amount, date, notes);
				this.getSalesService().recalculeAdSaveSellerLoanBag(seasonId, loanBag.getSeller());
				
				} catch (InvalidReferenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 		    	snackBar.show(this.getMessage("msg.done.save"),1000);

			 
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
