package com.gomalmarket.shop.modules.shopLoans.view.dialogue.loans;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome;
import org.springframework.context.ApplicationContext;

import com.gomalmarket.shop.core.Enum.IncomeTypeEnum;
import com.gomalmarket.shop.core.Enum.LoanTypeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.entities.Contractor;
import com.gomalmarket.shop.core.entities.ShopLoan;
import com.gomalmarket.shop.core.entities.Income;
import com.gomalmarket.shop.core.entities.IncomeDetail;
import com.gomalmarket.shop.core.entities.LoanAccount;
import com.gomalmarket.shop.core.entities.Loaner;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.modules.expanses.action.ExpansesAction;
import com.gomalmarket.shop.modules.shopLoans.action.ShoploanAction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
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

public class AddEditLoanPersenter extends ShoploanAction implements Initializable { 
 
	Logger logger = Logger.getLogger(this.getClass().getName());	

	@FXML
    private JFXButton cancel_btn;

    @FXML
    private JFXTextField name_TF;

    @FXML
    private Label name_label;

    @FXML
    private HBox date_loc;

    @FXML
    private Label amount_label;

    @FXML
    private JFXTextArea note_TA;

    @FXML
    private Label date_label;

    @FXML
    private JFXTextField amount_TF;

    @FXML
    private AnchorPane root_pane;

    @FXML
    private Pane coloredPane;

    @FXML
    private Label title_label;

    @FXML
    private HBox datePicker_loc;

    @FXML
    private HBox datePicker_loc1;

    @FXML
    private JFXButton saveBtn;

    @FXML
    void saveIncome(ActionEvent event) {

		
		  
		   
 		  double amount = Double.parseDouble(this.incomeAmount_TF.getText());
		  String notes =  note_TA.getText();
		  
		  try {
		  
		  
		  this.getShopLoanService().loanPayTansaction(na.getText(), new
		  Date(), amount, typeId, notes, getAppContext().getFridage() );
		  alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.save"));
		  
		  }catch (Exception ex) { 
			  alert(AlertType.ERROR, this.getMessage("msg.err"),this.getMessage("msg.err"),
		       this.getMessage("msg.err.general"));
		   }
	   
		 
    }
    
    //---------------------------------------------------------------------------------------------
    private final LoanTypeEnum loanType=LoanTypeEnum.IN_LOAN;
 	int loanAccountId;
	final 	int Add=0;
	final 	int edit=1;
	final 	int addNew=2;
	int mode;
    private JFXDatePicker datePicker;
    public  AddEditLoanPersenter() {

    	loanAccountId=(int) request.get("loanAccountId");
    	mode=(loanAccountId==0)?Add:edit;
     
    }
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	  	  logger.log(Level.INFO,"============================================================================================================");

		
		datePicker=new JFXDatePicker();
	   	datePicker.getEditor().setAlignment(Pos.CENTER_LEFT);
	   	datePicker.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	   	datePicker.setPrefWidth(317);
	   	 
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
	   	date_loc.getChildren().add(datePicker);
	   	date_loc.setPrefWidth(317);
	   	date_label.setText(getMessage("label.date"));
 //==============================================================================================================

		
		amount_label.setText(this.getMessage("label.money.amount"));
  		name_label.setText(this.getMessage("label.name"));

		 //==============================================================================================================
		saveBtn.setText(this.getMessage("button.save"));
		saveBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SAVE));
		saveBtn.getStyleClass().setAll("btn","btn-primary");  
		
		//-----------------------------
		cancel_btn.setText(this.getMessage("button.cancel"));
		  cancel_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.REMOVE));
	    cancel_btn.getStyleClass().setAll("btn","btn-danger");
	    cancel_btn.setOnAction(e -> {
	    	cancel();
	    	
	    });	
	    
		//==============================================================================================================
		note_TA.setPromptText(getMessage("label.notes"));
	     title_label.setText(getMessage("label.safe.income.data"));
	    //==============================================================================================================

		
		  TextFields.bindAutoCompletion(name_TF, t-> {
		  
		  
		  return  getLoanerNames(t.getUserText(), loanType);
		  
		  });
		 
	}
    








//---------------------------------------------------------------------------------------------------------------


	 
		boolean validateInForm() {
			
			   String name = name_TF.getText(); 
			   String amount = amount_TF.getText();
			   LoanAccount account = null;
			try {
				account = this.getShopLoanService().getLoanerAccount(name);
			} catch (DataBaseException | EmptyResultSetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
			  if (name.isEmpty()) {
				  
				  
			  alert(AlertType.ERROR,this.getMessage("msg.err"),this.getMessage("msg.err"),
			  this.getMessage("msg.err.required.name"));
			  
			  return false; } 
			  else if (amount.isEmpty()) 
			  {
				  
			  alert(AlertType.ERROR,this.getMessage("msg.err"),this.getMessage("msg.err"),
			  this.getMessage("msg.err.required.amount"));
			  
			  return false; }
			  
			  if (account == null) {
			  
			  alert(AlertType.ERROR,this.getMessage("msg.err"),this.getMessage("msg.err"),
			  this.getMessage("msg.err.notfound.name")); return false;//err.notfound.name
			  
			  }
			  
			  if (Double.parseDouble(amount) > 0 && account.getType().equals("IN_LOAN"))
			  {
				  
				  
			  alert(AlertType.ERROR,this.getMessage("msg.err"),this.getMessage("msg.err"),
			  this.getMessage("err.amountShouldBePayedFromLoaner"));
			  
			  return false;
			  
			  } 
			  if (Double.parseDouble(amount) > account.getDueAmount()) {
				  
			  alert(AlertType.ERROR,this.getMessage("msg.err"),this.getMessage("msg.err"),
			  this.getMessage(" msg.err.input.amount.greather"));
			  
			  return false; 
			  }
			  
			   
			  return true;
			}

	   
//---------------------------------------------------------------------------------------------------------------

		  void intiateInPage() {
		     switch (mode) {
			case edit:
				
				int loanId=(int) request.get("LoanId");
				try {
					
  					ShopLoan loan=(ShopLoan) this.getBaseService().findBean(ShopLoan.class,loanId);

					this.note_TA.setText(loan.getNotes());
					this.name_TF.setText(loan.getLoanAccount().getLoaner().getName());
					this.amount_TF.setText(String.valueOf(loan.getAmount()));
					datePicker.setValue(this.getBaseService().convertToLocalDateViaMilisecond(loan.getLoanDate()));
					
					
				}catch (Exception e) {
					// TODO: handle exception
				}
				
				
				
				
				
				
				break;
			case Add:
				int loanerId=(int) request.get("LoanerId");
				Loaner loaner=null;
				try {
					loaner = (Loaner) this.getBaseService().findBean(Loaner.class,loanerId);
				} catch (DataBaseException | InvalidReferenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.name_TF.setText(loaner.getName());


			default:
				break;
			}
		         
		    }

 
//---------------------------------------------------------------------------------------------------------------

			  private void alert(AlertType alertType,String title,String headerText,String message) {
					 Alert a = new Alert(alertType);
					 a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					 a.setTitle(title );
					 a.setHeaderText(headerText);
					 a.setContentText(message); 
			      a.show(); 
				  
			}
//---------------------------------------------------------------------------------------------------------------

				private void cancel() {
					
					   

				      Stage stage = (Stage) cancel_btn.getScene().getWindow();
				      // do what you have to do
				      stage.close();
				   
				   
				}
				
				
private List<String> getLoanerNames(String loanerName, LoanTypeEnum loanerType) {
			
			List <String >names=new ArrayList<String>();
			try {
				names=	getShopLoanService().inExactMatchSearchloanerName(loanerName, loanerType);
			} catch (EmptyResultSetException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			} catch (DataBaseException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();

			
			}
			return names;
			
		}

}
