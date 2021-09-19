package com.gomalmarket.shop.modules.sales.reports.view.periodicReport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.glyphfont.FontAwesome;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.config.ShopAppContext;
import com.gomalmarket.shop.core.entities.sellers.SellerLoanBag;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.billing.view.beans.InvoiceWeight;
import com.gomalmarket.shop.modules.sales.action.SalesAction;
import com.gomalmarket.shop.modules.sales.reports.view.beans.SellerLoanVB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

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

public class PeriodicReportPersenter extends SalesAction implements Initializable {

	
	
	@FXML
    private AnchorPane fromDate_Panel;

    @FXML
    private JFXButton cancel_Btn;

    @FXML
    private JFXButton showReport_Btn;

    @FXML
    private AnchorPane toDate_Panel;

    @FXML
    private Label fromDate_label;

    @FXML
    private Label toDate_label;

    @FXML
    private AnchorPane gridLocation_Panel;

    @FXML
    private JFXButton search_Btn;
	
    private JFXDatePicker fromDate;
    private JFXDatePicker toDate;

	private CustomTable<SellerLoanVB> loans;


	Logger logger = Logger.getLogger(this.getClass().getName());	

    public PeriodicReportPersenter() {

    	logger.log(Level.INFO,"============================================================================================================");		
    
    
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
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

		   	
		   	loans=new CustomTable<SellerLoanVB>(columns, null, null, null, null, CustomTable.tableCard, InvoiceWeight.class);
		   	 
		   	fitToAnchorePane(loans.getCutomTableComponent());
			//invoiceWeights.getCutomTableComponent().setPrefSize(100, 150);
			gridLocation_Panel.getChildren().addAll(loans.getCutomTableComponent());
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
		
		this.loans.loadTableData(new ArrayList());

		
	}

	private void  search() {
		
		
		
		Date fromDateValue=(fromDate.getValue()==null)? getAppContext().getSeason().getStartDate() : this.getBaseService().convertToDateViaInstant(fromDate.getValue());
		Date toDateValue=(toDate.getValue()==null)?new Date():this.getBaseService().convertToDateViaInstant(toDate.getValue());

		
		if(toDateValue.before(fromDateValue)) {
			  alert(AlertType.ERROR, this.getMessage("msg.err"),this.getMessage("msg.err"),getMessage("msg.err.toDateAfterFromDate"));
			return;
			
		}		
		
		List<SellerLoanVB> data=new ArrayList<SellerLoanVB>();

		int seasonId=getAppContext().getSeason().getId();
		try {
			List result=this.getSalesService().getSellersLoanSummary(fromDateValue, toDateValue, seasonId);
		
		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
//			Object [] object = (Object []) iterator.next();
//			double priorLoan=((BigDecimal) object[0]).doubleValue();
//			double ordersAmount=((BigDecimal) object[1]).doubleValue();
//			double paidAmount=((BigDecimal) object[2]).doubleValue();
//			double currentLoan=((BigDecimal) object[3]).doubleValue();
//			String name=(String) object[4];
			
			SellerLoanBag slb=(SellerLoanBag) iterator.next();
			
			
			SellerLoanVB row=new  SellerLoanVB();
			row.setDueAmount(slb.getCurrentLoan());
			row.setPaidAmount(slb.getPaidAmount());
			row.setPriorLoan(slb.getPriorLoan());
			row.setTotalOrdersAmount(slb.getDueLoan());

			row.setName(slb.getSeller().getName());
			data.add(row);
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		} catch (EmptyResultSetException | DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		this.loans.loadTableData(data);

		
		
		
		
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

	    
	  
	     Column  c=new Column(this.getMessage("seller.name"), "name", "سفقهىل", 40, true);
	     columns.add(c);
	      
	     c=new Column(this.getMessage("seller.loan.prior"), "priorLoan", "double", 15, true);
	     columns.add(c);
	   
	     c=new Column(this.getMessage("label.banana.Price"), "totalOrdersAmount", "double", 15, true);
	     columns.add(c);
	     
	       c=new Column(this.getMessage("label.money.paidAmount"), "paidAmount", "double", 15, true);
	     columns.add(c);
	    
	     c=new Column(this.getMessage("label.money.dueAmount"), "dueAmount", "double", 15, true);
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
	 
	 

	 Date fromDateValue=(fromDate.getValue()==null)? getAppContext().getSeason().getStartDate() : this.getBaseService().convertToDateViaInstant(fromDate.getValue());
		Date toDateValue=(toDate.getValue()==null)?new Date():this.getBaseService().convertToDateViaInstant(toDate.getValue());

		
		if(toDateValue.before(fromDateValue)) {
			  alert(AlertType.ERROR, this.getMessage("msg.err"),this.getMessage("msg.err"),getMessage("msg.err.toDateAfterFromDate"));
			return;
			
		}		
		
 
		int seasonId=getAppContext().getSeason().getId();
		
		
		
 		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 

			
		
		  try { Resource r=new
		  ClassPathResource("reports/sales/priodicSellerLoans.jrxml");
		  
		  InputStream report = null; try { report = new FileInputStream (
		  r.getFile().getPath()); } catch (FileNotFoundException e) {
		  
		  // TODO Auto-generated catch block 
			  e.printStackTrace(); 
			  } catch (IOException  e) {
 			  e.printStackTrace(); }
		 
		  
		  Map<String,Object> param = new HashMap<String, Object>();
		  
		  param.put("fromDate", sdf.format(fromDateValue));
		  
		  param.put("toDate", sdf.format(toDateValue)); param.put("seasonId",seasonId);
		  
		  getBaseService().printReport(param, report);
		  
		  } catch (DataBaseException e) { // TODO Auto-generated catch block
		  e.printStackTrace(); }
		  catch (JRException e) { // TODO Auto-generated catch
		   e.printStackTrace(); }
		 
			
			
		}  
}
