package com.gomalmarket.shop.modules.contractor.view.periodicReport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.gomalmarket.shop.core.Enum.ContractorTypeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.entities.contractor.ContractorTransaction;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.contractor.action.ContractorAction;
import com.gomalmarket.shop.modules.contractor.view.beans.ContractorTransactionVB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JRException;

public class PeriodicReportPersenter extends ContractorAction implements Initializable {

	
	@FXML
    private AnchorPane fromDate_Panel;

    @FXML
    private JFXButton cancel_Btn;

    @FXML
    private JFXComboBox<ComboBoxItem<Integer>> type_CB;

    @FXML
    private Label owner_label;

    @FXML
    private JFXTextField name_TF;

    @FXML
    private JFXButton showReport_Btn;

    @FXML
    private AnchorPane toDate_Panel;

    @FXML
    private Label fromDate_label;

    @FXML
    private JFXComboBox<ComboBoxItem<Integer>> owner_CB;

    @FXML
    private Label toDate_label;

    @FXML
    private Label name_label;

    @FXML
    private JFXButton search_Btn;

    @FXML
    private JFXComboBox<ComboBoxItem<Integer>> paidType_CB;

    @FXML
    private Label type_label;

    @FXML
    private AnchorPane gridLocation_Panel;

    @FXML
    private Label paidType_label;
	
    private JFXDatePicker fromDate;
    private JFXDatePicker toDate;

	private CustomTable<ContractorTransactionVB> transactions;
	
	 

	
private List<ComboBoxItem<Integer>> contractorTypes;
private List<ComboBoxItem<Integer>> paidTypes;
private final int shopPaid=1;
private final int kareemPaid=0;
private final int allPaid=-1;

Logger logger = Logger.getLogger(this.getClass().getName());	

	public PeriodicReportPersenter() {
		
		contractorTypes=new ArrayList<ComboBoxItem<Integer>>();
		contractorTypes.add(new ComboBoxItem<Integer>(ContractorTypeEnum.LABOUR,getMessage("button.labour")  ));
		contractorTypes.add(new ComboBoxItem<Integer>(ContractorTypeEnum.SUPPLIER_, getMessage("button.suppliers") ));
		contractorTypes.add(new ComboBoxItem<Integer>(ContractorTypeEnum.VARAITY, getMessage("button.varaties") ));

		
		paidTypes=new ArrayList<ComboBoxItem<Integer>>();
		paidTypes.add(new ComboBoxItem(allPaid,getMessage("label.all")  ));
		paidTypes.add(new ComboBoxItem(kareemPaid, getMessage("label.owner.name.kareem") ));
		paidTypes.add(new ComboBoxItem(shopPaid, getMessage("label.shop") ));
	
		
	
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
 
		
		
		logger.log(Level.INFO,"============================================================================================================");

	  	  
	  //=============================================================================================================

		toDate_label.setText(getMessage("label.toDate"));
		fromDate_label.setText(getMessage("label.fromDate"));
	//=============================================================================================================
		
		 search_Btn.setText(getMessage("button.search"));
		 search_Btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SEARCH));
		 search_Btn.getStyleClass().setAll("btn","btn-info","btn-xs");  
		 
		 
		 cancel_Btn.setText(getMessage("button.cancelSearch"));
		 cancel_Btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.REMOVE));
		 cancel_Btn.getStyleClass().setAll("btn","btn-info","btn-xs"); 
		 
		 
		 
		 showReport_Btn.setText(getMessage("button.showReport"));
		 showReport_Btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.BOOK));
		 showReport_Btn.getStyleClass().setAll("btn","btn-info","btn-xs"); 
		 
//=============================================================================================================
		  	fromDate=new JFXDatePicker();
		  	fromDate.getEditor().setAlignment(Pos.CENTER);
		  	fromDate.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		  	fromDate.setMinWidth(270);

		  	fromDate.setConverter(new StringConverter<LocalDate>()
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
		   	fromDate_Panel.getChildren().add(fromDate);
	//---------------------------------------------------------------------	   	
		   	toDate=new JFXDatePicker();
		   	toDate.getEditor().setAlignment(Pos.CENTER);
		  	toDate.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		  	toDate.setMinWidth(270);
		  	toDate.setConverter(new StringConverter<LocalDate>()
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
		   	toDate_Panel.getChildren().add(toDate);
		
 //=============================================================================================================
			List columns=prepareGridColumns();

		   	
			transactions=new CustomTable<ContractorTransactionVB>(columns, null, null, null, null, CustomTable.tableCard, ContractorTransactionVB.class);
			transactions.getTable().setEditable(false);
		   	fitToAnchorePane(transactions.getCutomTableComponent());
			gridLocation_Panel.getChildren().addAll(transactions.getCutomTableComponent());
 //=============================================================================================================
			 name_label.setText(getMessage("label.name"));
			name_TF.setPromptText(getMessage("label.name"));
			 name_TF.textProperty().addListener((o, oldVal, newVal) -> {	
				 name_TF.getStyleClass().add("TextField");
				 
				 TextFields.bindAutoCompletion(name_TF, t-> {
					 	int ownerId=owner_CB.getSelectionModel().getSelectedItem().getId();
					 	int typeId=type_CB.getSelectionModel().getSelectedItem().getId();
			          //  return this.getSalesService().getSuggestedSellerName( t.getUserText());
					 return this.getContractorService().getSuggestedContractorName(name_TF.getText(), ownerId, typeId);
			        });
				 
			 });
 //=============================================================================================================
				
				 owner_label.setText(getMessage("label.owner"));
				 owner_CB.setPromptText(this.getMessage("label.owner"));
				 owner_CB.getStyleClass().add("comboBox");
				 
				 
				 
					for (Iterator iterator = this.owners.iterator(); iterator.hasNext();) {
						ComboBoxItem object = (ComboBoxItem) iterator.next();
						owner_CB.getItems().add(object);}
					owner_CB.getSelectionModel().selectFirst();
  //=============================================================================================================
		type_label.setText(getMessage("label.contractor.type"));
		 type_CB.getItems().addAll(contractorTypes);
		 type_CB.getSelectionModel().selectFirst();

		  paidType_label.setText(getMessage("label.contractor.payer"));
		 paidType_CB.getItems().addAll(paidTypes);		
		 paidType_CB.getSelectionModel().selectFirst();
			
					
  //=============================================================================================================
		 search_Btn.setOnAction(e -> {
		        	search();
		        	enableInputes(false);
					showReport_Btn.setDisable(false);

		        });	
		   
			

			 cancel_Btn.setOnAction(e -> {
				 cancel();
					showReport_Btn.setDisable(true);

			 });	
		   
			
			 showReport_Btn.setOnAction(e -> {
				 
				 printReport();
			 });	
		   
			
			
			
	}
	 
	//------------------------------------------------------------------------------------------------------------------------------------------	
	
	private void  enableInputes(boolean value) {
		this.fromDate.setDisable(!value);
		this.toDate.setDisable(!value);

		
	}
	private void  cancel() {
		enableInputes(true);
		
		this.transactions.loadTableData(new ArrayList());

		
	}

	private void search() {
		
		  
		  
		  
		  Date fromDateValue=(fromDate.getValue()==null)?null :
		  this.getBaseService().convertToDateViaInstant(fromDate.getValue()); Date
		  toDateValue=(toDate.getValue()==null)?null:this.getBaseService().convertToDateViaInstant(toDate.getValue());
		  
		  
		  String name=(name_TF.getText().length()>0)?name_TF.getText():null;
		  int paid=paidType_CB.getSelectionModel().getSelectedItem().getId();
		  int ownerId=owner_CB.getSelectionModel().getSelectedItem().getId();
		  int typeId=type_CB.getSelectionModel().getSelectedItem().getId();
		  
		   if(toDateValue!=null&&fromDateValue!=null&&toDateValue.before(fromDateValue)) { 
			   alert(AlertType.ERROR,this.getMessage("msg.err"),this.getMessage("msg.err"),getMessage("msg.err.toDateAfterFromDate")); return;
		  
		  }
		  
		  List<ContractorTransactionVB> data=new LinkedList<ContractorTransactionVB>();
		  
		  int seasonId=getAppContext().getSeason().getId(); 
		  try {  
			 List result=this.getContractorService().getContractorTransactions(name, typeId, fromDateValue, toDateValue, paid, ownerId);

		  for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			 
		  

					
					ContractorTransaction transaction = (ContractorTransaction) iterator.next();
					ContractorTransactionVB viewBean=new ContractorTransactionVB();
					viewBean.setDate(ContractorTransactionVB.sdf.format(transaction.getTransactionDate()));
					viewBean.setName(transaction.getContractor().getName());

					viewBean.setId(transaction.getId());
					viewBean.setAmount(transaction.getAmount());
					viewBean.setNotes(transaction.getReport());
					viewBean.setPaid((transaction.getPaid()==1)?getAppContext().getMessages().getString("label.contractor.status.paid.no"):getAppContext().getMessages().getString("label.contractor.status.paid.no"));
 				 
					
					data.add(viewBean);
						  
		  
		  }
		  
		  
		  } catch (EmptyResultSetException | DataBaseException e) {}		  
		  
		  
		  
		  this.transactions.loadTableData(data);
		  
		  
		  
		  
		  
		 }
	
	
	
	
	
	
	
	
	
	//------------------------------------------------------------------------------------------------------------------------------------------	

	private void alert(AlertType alertType,String title,String headerText,String message) {
		 Alert a = new Alert(alertType);
		 a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		 a.setTitle(title );
		 a.setHeaderText(headerText);
		 a.setContentText(message); 
	    a.show(); 
	 
	}
	
	
//------------------------------------------------------------------------------------------------------------------------------------------	
	 private List prepareGridColumns() {
	      

	      List<Column> columns=new ArrayList<Column>();

	     
	   
	      Column  c=new Column(this.getMessage("label.date"), "date", "date", 25, true);
	      columns.add(c);
	       
	      
	      c=new Column(this.getMessage("label.name"), "name", "String", 20, true);
	      columns.add(c);
	    
	        c=new Column(this.getMessage("label.money.amount"), "amount", "double", 15, true);
	      columns.add(c);
	    
	      c=new Column(this.getMessage("label.contractor.status.paid"), "paid", "String", 15, true);
	      columns.add(c);
	  
	      c=new Column(this.getMessage("label.notes"), "notes", "String", 40, true);
	      columns.add(c);      
	      
	 return columns;
	 
	 
	 
	 
	 
	 
	 }
	//------------------------------------------------------------------------------------------------------------------------------------------	

private void fitToAnchorePane(Node node) {
	
	
	AnchorPane.setTopAnchor(node,  0.0); 
	AnchorPane.setLeftAnchor(node,  0.0); 
	AnchorPane.setRightAnchor(node,  0.0); 
	AnchorPane.setBottomAnchor(node,  0.0); 
	
	
	
} 
//------------------------------------------------------------------------------------------------------------------------------------------	
 private void printReport() {
	 
	 

	  Date fromDateValue=(fromDate.getValue()==null)?null : this.getBaseService().convertToDateViaInstant(fromDate.getValue());
	 	Date toDateValue=(toDate.getValue()==null)?null:this.getBaseService().convertToDateViaInstant(toDate.getValue());

		
		if(toDateValue!=null&&fromDateValue!=null&&toDateValue.before(fromDateValue)) {
			  alert(AlertType.ERROR, this.getMessage("msg.err"),this.getMessage("msg.err"),getMessage("msg.err.toDateAfterFromDate"));
			return;
			
		}		
		
 
		int seasonId=getAppContext().getSeason().getId();
		
		
		
 		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 

			

			try {
				 Resource r=new ClassPathResource("reports/contractors/priodicContractorLoans.jrxml"); 

	 		InputStream report = null;
			try {
				report = new FileInputStream ( r.getFile().getPath());
			} catch (FileNotFoundException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		Map<String, Object> param = new HashMap<String, Object>();

			param.put("fromDate", (fromDateValue==null)?"":sdf.format(fromDateValue));
			
		    param.put("toDate",   (toDateValue==null)?"":sdf.format(toDateValue));
			param.put("seasonId",seasonId);
			param.put("ownerId",owner_CB.getSelectionModel().getSelectedItem().getId());
			param.put("typeId",type_CB.getSelectionModel().getSelectedItem().getId());
			param.put("paid",paidType_CB.getSelectionModel().getSelectedItem().getId());
			param.put("name",name_TF.getText());

			
			
		 
			
			
			
		 	getBaseService().printReport(param, report);
	 		
			} catch (DataBaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}  
}
