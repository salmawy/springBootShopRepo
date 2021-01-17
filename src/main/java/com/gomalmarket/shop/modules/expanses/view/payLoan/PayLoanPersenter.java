package com.gomalmarket.shop.modules.expanses.view.payLoan;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.glyphfont.FontAwesome;
import org.springframework.context.ApplicationContext;

import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.entities.LoanAccount;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.expanses.action.ExpansesAction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PayLoanPersenter extends ExpansesAction implements Initializable {
	
	
	    Logger logger = Logger.getLogger(this.getClass().getName());	
	    
	    @FXML
	    private Label title_label;

	    @FXML
	    private JFXButton cancel_btn;

	    @FXML
	    private Label loanType_label;

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
	    private JFXComboBox<ComboBoxItem> loanType_combo;

	    @FXML
	    private Pane coloredPane;

	    @FXML
	    private HBox datePicker_loc;

	    @FXML
	    private JFXButton saveBtn;
	    
	    
	    private JFXDatePicker datePicker;
	    private JFXSnackbar snackBar;

	    
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
	    	save();
	    	
	    });	 
	    
	    
	    cancel_btn.setText(this.getMessage("button.cancel"));
 	    cancel_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.CLOSE));
	    cancel_btn.getStyleClass().setAll("btn","btn-danger");  
	    cancel_btn.setOnAction(e -> {
	    	cancel();
	    	
	    });	
	    
//============================================================================================================
	    
	    loanType_label.setText(this.getMessage("label.type"));

		loanType_combo.getItems().add(new ComboBoxItem(1,this.getMessage("label.inLoan")));
		loanType_combo.getItems().add(new ComboBoxItem(2,this.getMessage("label.outLoan")));
		loanType_combo.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
	    {
	        public void changed(ObservableValue<? extends Number> ov,
	                final Number oldvalue, final Number newvalue)
	        {
	        	
	        	ComboBoxItem item=loanType_combo.getSelectionModel().getSelectedItem();
	        	if(item.getId()==1) {
	          title_label.setText(getMessage("label.inLoan"));
 	        		coloredPane.setStyle("-fx-background-color: #00A65A");
	        		
	        	}
	        	else if(item.getId()==2) {
	  	          title_label.setText(getMessage("label.outLoan"));

 	        		coloredPane.setStyle("-fx-background-color: #DD4B39");

	        		
	        	}
	        	
	        	
	        }
	    });
		loanType_combo.getSelectionModel().selectFirst();
		
	    
 //============================================================================================================
	    snackBar=new JFXSnackbar(root_pane);
	    
	    
	    
	    
//============================================================================================================

	}

   private void cancel() {
		
	   

	      Stage stage = (Stage) cancel_btn.getScene().getWindow();
	      // do what you have to do
	      stage.close();
	   
	   
	}


private  boolean validateForm() {
    	
	   String name=name_TF.getText() ;
	    String amount =amount_TF.getText();
        double safaBalance=this.getExpansesServices().getSafeBalance(getAppContext().getSeason());


        if (amount.isEmpty()){
    	    
       	
    	snackBar.show(this.getMessage("msg.err.required.amount"), 1000);

       return false;
       
       }
        else   if (name.isEmpty()) {
    	snackBar.show(this.getMessage("msg.err.required.name"), 1000);

           return false;

       }
        
        
        if (datePicker.getValue()==null) {
        	snackBar.show(this.getMessage("msg.err.required.date"), 1000);

               return false;

           }
        
        LoanAccount account=null;
		try {
			account = this.getExpansesServices().getLoanerAccount(name);
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

         if (safaBalance<Double.parseDouble(amount)) {
        
        	snackBar.show(this.getMessage("msg.err.notEnough.safeBalance"), 1000);
            return false;
        }
       if (account == null) {
    	   
    	snackBar.show(this.getMessage("msg.err.notfound.name"), 1000);

          return false;

       }
       if (account.getDueAmount() > 0 && account.getType().equals("OUT_LOAN")) {
    	snackBar.show(this.getMessage("msg.err.amountShouldBecollestedFromLoaner")+" : "+account.getDueAmount(),1000);

           return false;

       }
       if (Double.parseDouble(amount) > account.getDueAmount()) {
    	snackBar.show(this.getMessage("msg.err.input.amount.greather")+" : "+account.getDueAmount(),1000);

           return false;
       }
   
    	
    	return true;
    	
    }


	private void save() {
		if(validateForm())
    	snackBar.show(this.getMessage("button.save"),1000);
		
	}

}
