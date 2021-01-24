package com.gomalmarket.shop.modules.contractor.view.AddVaraity;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome;
import org.springframework.context.ApplicationContext;

import com.gomalmarket.shop.core.Enum.ContractorTypeEnum;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.validator.Validator;
import com.gomalmarket.shop.modules.contractor.action.ContractorAction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

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

public class AddVaraityPersenter extends ContractorAction implements Initializable {
	
	
    Logger logger = Logger.getLogger(this.getClass().getName());	

    @FXML
    private HBox paid_toogleBtn;

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
    private Label title_label;

    @FXML
    private HBox datePicker_loc;

    @FXML
    private JFXButton saveBtn;
	    
	    
	    private JFXDatePicker datePicker;
	    private JFXSnackbar snackBar;
	    private final int paid=1;
	    
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	  	  logger.log(Level.INFO,"============================================================================================================");
		init();
		 
		 
	}



	private void init() {

		  snackBar=new JFXSnackbar(root_pane);
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
  			    saveBtn.getStyleClass().addAll("btn","btn-primary");  
			    saveBtn.setOnAction(e -> {
			    	save();
			    	
			    });	 
			    
			    
			    cancel_btn.setText(this.getMessage("button.cancel"));
			    cancel_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.APPLE));
			    cancel_btn.getStyleClass().addAll("btn","btn-danger");  
			       
			    
			    cancel_btn.setOnAction(e -> {
			    	cancel();
			    	
			    });	
			    
			    
			    title_label.setText(getMessage("label.add.transaction"));
//============================================================================================================
			  
			    amount_TF.textProperty().addListener((observable, oldValue, newValue) -> {
				    System.out.println("grossWeight changed from " + oldValue + " to " + newValue);
				    Validator    myvaValidator=new Validator();
				    if(newValue.length()>0) {
					    myvaValidator.getValidDouble(newValue, 0, Double.MAX_VALUE, "grossWeightValue", true);
					    if(!myvaValidator.noException()) 
					    	{newValue=oldValue;
					    	amount_TF.setText(newValue);
					    	}
				    	
				    }});
			  
			   
			    
//============================================================================================================

			    TextFields.bindAutoCompletion(name_TF, t-> {
					 return autoComplete( t.getUserText());
			        });
//============================================================================================================

	}

 private void save() {
	if (validateForm()) {
		
		
		
		String name=name_TF.getText();
		double amount=Double.parseDouble(amount_TF.getText());
		String notes=note_TA.getText();
		int ownerId=(Integer)this.request.get("ownerId"); 
		Date date=getValueOfDatePicker();
		try {
			this.getContractorService().contractorTransaction(name, ContractorTypeEnum.LABOUR, amount, getAppContext().getFridage().getId(), notes, paid, ownerId, date, getAppContext().getSeason().getId());
				this.response=new HashMap<String, Object>();
				response.put("valid", true);
				response.put("name", name);

		      Stage stage = (Stage) saveBtn.getScene().getWindow();
		      stage.close();		
		      
		} catch (DataBaseException e) {

			
			
			e.printStackTrace();
		}

	 }		
}


	    boolean validateForm() {
	    	
	        double safaBalance=this.getExpansesServices().getSafeBalance(getAppContext().getSeason());


	    	
	        if (name_TF.getText().isEmpty()) {
	            
		    	   snackBar.show(this.getMessage("msg.err.required.name"), 1000);

	              return false;

	        } else if (amount_TF.getText().isEmpty()) {
		    	   snackBar.show(this.getMessage("msg.err.required.amount"), 1000);

		    	   return false;
	        } else if (safaBalance<Double.parseDouble(amount_TF.getText())) {
		    	   snackBar.show(this.getMessage("msg.err.notEnough.safeBalance"), 1000);
                   return false;
	        } 
	        
	        else if (datePicker.getValue()==null) {
		    	   snackBar.show(this.getMessage("msg.err.required.date"), 1000);

		    	   return false;
	        }
	        return true;

	    }

	   

	private void cancel() {
			
		   

		      Stage stage = (Stage) cancel_btn.getScene().getWindow();
		      // do what you have to do
		      stage.close();
		   
		   
		}

	

    private Date getValueOfDatePicker() {
    	
    	
        LocalDate localate =datePicker.getValue();
        Instant instant=Instant.from(localate.atStartOfDay(ZoneId.systemDefault()));
    	
    	return Date.from(instant);
    	
    }
    
    
    
    


    
    
    
  private  List autoComplete(String name) {
	  
	  int ownerid=(int) request.get("ownerId");
	  
	 return this.getContractorService().getSuggestedContractorName(name, ownerid, ContractorTypeEnum.LABOUR);
  }  
    
    
    
    
    
    
    
}
