package com.gomalmarket.shop.modules.expanses.view.addIncome;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.glyphfont.FontAwesome;

import com.gomalmarket.shop.core.Enum.IncomeTypeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.entities.contractor.Contractor;
import com.gomalmarket.shop.core.entities.expanses.Income;
import com.gomalmarket.shop.core.entities.expanses.IncomeDetail;
import com.gomalmarket.shop.modules.expanses.action.ExpansesAction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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

public class AddIncomePersenter extends ExpansesAction implements Initializable { 
 
	Logger logger = Logger.getLogger(this.getClass().getName());	

	    @FXML
	    private JFXComboBox<ComboBoxItem> incomeType_combo;

	    @FXML
	    private JFXButton cancel_btn;

	    @FXML
	    private JFXTextField incomeAmount_TF;

	    @FXML
	    private Label incomeName_label;

	    @FXML
	    private Label incomeType_label;

	    @FXML
	    private JFXTextField incomeName_TF;

  
	    @FXML
	    private HBox date_loc;

	    @FXML
	    private JFXTextArea note_TA;

	    @FXML
	    private Label date_label;

	    @FXML
	    private AnchorPane root_pane;

	    @FXML
	    private Pane coloredPane;

	    @FXML
	    private Label title_label;

	    @FXML
	    private HBox datePicker_loc1;

	    @FXML
	    private Label incomeAmount_label;

	    @FXML
	    private HBox datePicker_loc;

	    @FXML
	    private JFXButton saveBtn;
//---------------------------------------------------------------------------------------------
    
	List<ComboBoxItem> incomeTypes;
	int incomeDetailId;
	final 	int Add=0;
	final 	int edit=1;
	int mode;
    private JFXDatePicker datePicker;
    public AddIncomePersenter() {

    	incomeDetailId=(int) request.get("incomeDetailId");
    	mode=(incomeDetailId==0)?Add:edit;
    	incomeTypes=new ArrayList<ComboBoxItem>(Arrays.asList(new ComboBoxItem(IncomeTypeEnum.IN_PAY_LOAN.getId(),this.getMessage(" label.pay.inloan") )));
    
    
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
 
			incomeType_combo.getItems().addAll(incomeTypes);
			
			incomeType_combo.getSelectionModel().selectFirst();
			
			 
//==============================================================================================================

		
		incomeAmount_label.setText(this.getMessage("label.money.amount"));
		incomeType_label.setText(this.getMessage("label.safe.income.kind"));
 		incomeName_label.setText(this.getMessage("label.name"));

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

		/*
		 * TextFields.bindAutoCompletion(incomeName_TF, t-> {
		 * 
		 * 
		 * return "";// getLoanerNames(t.getUserText(), LoanTypeEnum.IN_LOAN);
		 * 
		 * });
		 */
	}
    








//---------------------------------------------------------------------------------------------------------------


	 
		boolean validateInForm() {
			
			   String name = incomeName_TF.getText(); 
			   String inAmount = incomeAmount_TF.getText();
			 
			  if (name.isEmpty()) {
				  
				  
			  alert(AlertType.ERROR,this.getMessage("msg.err"),this.getMessage("msg.err"),
			  this.getMessage("msg.err.required.name"));
			  
			  return false; } 
			  else if (inAmount.isEmpty()) 
			  {
				  
			  alert(AlertType.ERROR,this.getMessage("msg.err"),this.getMessage("msg.err"),
			  this.getMessage("msg.err.required.amount"));
			  
			  return false; }
			  
			  /*  if (account == null) {
			  
			  alert(AlertType.ERROR,this.getMessage("msg.err"),this.getMessage("msg.err"),
			  this.getMessage("msg.err.notfound.name")); return false;//err.notfound.name
			  
			  }
			  
		
		 * if (Double.parseDouble(inAmount) > 0 && account.getType().equals("IN_LOAN"))
		 * {
		 * 
		 * 
		 * alert(AlertType.ERROR,this.getMessage("msg.err"),this.getMessage("msg.err"),
		 * this.getMessage("err.amountShouldBePayedFromLoaner"));
		 * 
		 * return false;
		 * 
		 * } if (Double.parseDouble(inAmount) > account.getDueAmount()) {
		 * 
		 * alert(AlertType.ERROR,this.getMessage("msg.err"),this.getMessage("msg.err"),
		 * this.getMessage(" msg.err.input.amount.greather"));
		 * 
		 * return false; }
		 */
			  
			   
			  return true;
			}

	   
//---------------------------------------------------------------------------------------------------------------

		  void intiateInPage() {
		     switch (mode) {
			case edit:
				
				
				try {
					
					IncomeDetail detail=(IncomeDetail) this.getBaseService().findBean(Income.class, incomeDetailId);
					Contractor contractor=(Contractor) this.getBaseService().findBean(Contractor.class, detail.getSellerId());
					this.note_TA.setText(detail.getNotes());
					this.incomeName_TF.setText(contractor.getName());
					this.incomeAmount_TF.setText(String.valueOf(detail.getAmount()));
					datePicker.setValue(this.getBaseService().convertToLocalDateViaMilisecond(detail.getChangeDate()));
					
					
				}catch (Exception e) {
					// TODO: handle exception
				}
				
				
				
				
				
				
				break;

			default:
				break;
			}
		        
		        
		        
		        
		        
		        
 
		    }

//---------------------------------------------------------------------------------------------------------------
	  
			@FXML
			private void saveIncome() {
				
				  
				  
				  int typeId =
				  incomeType_combo.getSelectionModel().getSelectedItem().getId(); double
				  amount = Double.parseDouble(this.incomeAmount_TF.getText());
				  String notes =  note_TA.getText();
				  
			/*	  try {
				  
				  
				  this.getExpansesServices().loanPayTansaction(incomeName_TF.getText(), new
				  Date(), amount, typeId, notes, getAppContext().getFridage() );
				  alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.save"));
				  
				  }catch (Exception ex) { 
					  alert(AlertType.ERROR, this.getMessage("msg.err"),this.getMessage("msg.err"),
				       this.getMessage("msg.err.general"));
				   }
			   */
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
				
				
	private List<String> getLoanerNames(String loanerName, String loanerType) {
		/*
		 * 
		 * List <String >names=new ArrayList<String>(); try { names=
		 * getExpansesServices().inExactMatchSearchloanerName(loanerName, loanerType); }
		 * catch (EmptyResultSetException e) { // TODO Auto-generated catch block //
		 * e.printStackTrace(); } catch (DataBaseException e) { // TODO Auto-generated
		 * catch block // e.printStackTrace(); } catch (Exception e) {
		 * e.printStackTrace();
		 * 
		 * 
		 * } return names;
		 * 
		 */return null;}

}
