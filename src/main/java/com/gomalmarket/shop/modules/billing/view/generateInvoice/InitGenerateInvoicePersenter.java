package com.gomalmarket.shop.modules.billing.view.generateInvoice;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.controlsfx.glyphfont.FontAwesome;

import com.gomalmarket.shop.App;
import com.gomalmarket.shop.core.Enum.CustomerTypeEnum;
import com.gomalmarket.shop.core.Enum.InvoiceStatusEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTableActions;
import com.gomalmarket.shop.core.entities.CustomerOrder;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.Customer.transactions.view.beans.InvoiceViewbean;
import com.gomalmarket.shop.modules.billing.action.BillingAction;
import com.gomalmarket.shop.modules.billing.view.invoice.archived.InvoiceArchiveView;
import com.gomalmarket.shop.modules.billing.view.invoice.generate.InvoiceView;
import com.gomalmarket.shop.modules.billing.view.invoicePayment.InvoicePaymentView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
@SuppressWarnings("unchecked")

public class InitGenerateInvoicePersenter extends BillingAction implements Initializable, CustomTableActions {

	@FXML
    private AnchorPane fromDate_Panel;

    @FXML
    private JFXButton cancel_Btn;

    @FXML
    private JFXComboBox<ComboBoxItem> customerType_CB;

    @FXML
    private JFXComboBox<ComboBoxItem> invoiceStatus_CB;

    @FXML
    private Label customerType_label;

    @FXML
    private Label invoiceStatus_label;

    @FXML
    private AnchorPane toDate_Panel;

    @FXML
    private Pane owner_coloredPane;

    @FXML
    private Label fromDate_label;

    @FXML
    private Label toDate_label;

    @FXML
    private JFXButton search_Btn;

    @FXML
    private JFXComboBox<ComboBoxItem> customerName_CB;

    @FXML
    private AnchorPane gridLocation_Panel;

    @FXML
    private Label customerName_label;

   
 	    private CustomTable<InvoiceViewbean> invoiceCustomeTable;

		private List<ComboBoxItem> customerTypes;
		private List<ComboBoxItem> invoiceStatus;
		private List <ComboBoxItem> customers;

		
		private JFXDatePicker fromDate;
		private JFXDatePicker toDate;

		private JFXButton generateInvoice_Btn;
 
	    
		Logger logger = Logger.getLogger(this.getClass().getName());
		public InitGenerateInvoicePersenter() {
			
			
			logger.log(Level.INFO,"============================================================================================================");

	    	customers=new ArrayList<>();
	    	
	    	
	    	@SuppressWarnings("rawtypes")
			List Result;
			try {
				Result = this.getBillingService().getSuggestedCustomersOrders(getAppContext().getSeason().getId(), getAppContext().getFridage().getId());
		
	    for (@SuppressWarnings("rawtypes")
		Iterator iterator = Result.iterator(); iterator.hasNext();) {
			Object[]row = (Object[]) iterator.next();
			/*
			 * customerid=>[0]
			 * customer name =>[1]
			 * customer type=>[2]
			 * invoice invoiceStatus =>[3]
 			 * */
			
			
			String parentKey;
			if((int)row[3] ==0) {
				parentKey=row[2]+"_"+InvoiceStatusEnum.UNDER_EDIT;
  
			}
			else if((int)row[3] ==1) {
				parentKey=row[2]+"_"+InvoiceStatusEnum.UNDER_DELIVERY;
				//System.out.println("custmer Type = "+row[2]+"InvoiceStatusEnum.UNDER_DELIVERY");

			}
			else {
				parentKey=(int)row[2] +"_"+InvoiceStatusEnum.ARCHIVED;
				//System.out.println("InvoiceStatusEnum.ARCHIVED");


			}
			
			
			ComboBoxItem boxItem=new  ComboBoxItem((int)row[0] , (String)row[1], parentKey);
			customers.add(boxItem);
		}
	    			
			} catch (EmptyResultSetException | DataBaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
//=============================================================================================================================================
	    	customerTypes=new ArrayList<ComboBoxItem>();
	    	customerTypes.add(new ComboBoxItem(CustomerTypeEnum.kareem,this.getMessage("customer.type.karrem")));
	    	customerTypes.add(new ComboBoxItem(CustomerTypeEnum.mahmed,this.getMessage("customer.type.mahmed")));
	    	customerTypes.add(new ComboBoxItem(CustomerTypeEnum.normal,this.getMessage("customer.type.normal")));
	    	customerTypes.add(new ComboBoxItem(CustomerTypeEnum.purchases,this.getMessage("customer.type.purchaes")));
			
			
			invoiceStatus=new ArrayList<ComboBoxItem>();
			invoiceStatus.add(new ComboBoxItem(InvoiceStatusEnum.UNDER_EDIT,getMessage("invoice.status.underEdit")  ));
			invoiceStatus.add(new ComboBoxItem(InvoiceStatusEnum.UNDER_DELIVERY, getMessage("invoice.status.underDelivery") ));
			invoiceStatus.add(new ComboBoxItem(InvoiceStatusEnum.ARCHIVED, getMessage("invoice.status.archvied") ));
		
					}
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		init();
	}

 	
	private void init() {
		
		
		//=============================================================================================================
	
		toDate_label.setText(getMessage("label.toDate"));
		fromDate_label.setText(getMessage("label.fromDate"));
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
		customerType_label.setText(this.getMessage("label.customer.Type"));
		customerType_CB.getItems().addAll(customerTypes);
	 	customerType_CB.getStyleClass().add("comboBox");
		customerType_CB.setOnAction(e -> {
	    	String key=customerType_CB.getSelectionModel().getSelectedItem().getId()+"_"+ invoiceStatus_CB.getSelectionModel().getSelectedItem().getId();
			 setCustomerParentKey(key);	


	        });	
		customerType_CB.getSelectionModel().selectFirst();

	  //------------------------------------------------------------------------------------------------------------------------------------------
	  	invoiceStatus_label.setText(getMessage("invoice.status"));
	    invoiceStatus_CB.getItems().addAll(invoiceStatus);		
	  	invoiceStatus_CB.getSelectionModel().selectFirst();
	  	invoiceStatus_CB.setOnAction(e -> {
	  		
	    	String key=customerType_CB.getSelectionModel().getSelectedItem().getId()+"_"+ invoiceStatus_CB.getSelectionModel().getSelectedItem().getId();
			 setCustomerParentKey(key);	
		
			 try {
			 int invoiceStatusId =invoiceStatus_CB.getSelectionModel().getSelectedItem().getId();
		
			
			if(invoiceStatusId==InvoiceStatusEnum.ARCHIVED) {
			 generateInvoice_Btn.setVisible(false);
		 }
		 else if(invoiceStatusId==InvoiceStatusEnum.UNDER_DELIVERY) {
			 generateInvoice_Btn.setVisible(true);
			 generateInvoice_Btn.setText(getMessage("button.invoice.give"));

		 }
		 else if(invoiceStatusId==InvoiceStatusEnum.UNDER_EDIT) {
			 generateInvoice_Btn.setVisible(true);
			 generateInvoice_Btn.setText(getMessage("button.invoice.generate"));

		 }}catch (Exception ex) {
			 
			 
 		}
	        });			
  //------------------------------------------------------------------------------------------------------------------------------------------
			
	  	customerName_label.setText(getMessage("customer.name"));
    	customerName_CB.getStyleClass().add("comboBox");
    	String key=customerTypes.get(0).getId()+"_"+ invoiceStatus.get(0).getId();
		 setCustomerParentKey(key);	
		 
 //=============================================================================================================

			 
 //=============================================================================================================
		 
			 
 		  List<Column>invoiceColumns=prepareInvoiceTabelColumns(); 
		  List invoiceTableControl=prepareInvoiceControles();
		  invoiceCustomeTable=new CustomTable<InvoiceViewbean>(invoiceColumns,invoiceTableControl,null,null,null,CustomTable.headTableCard,InvoiceViewbean.class); 
 		
 		AnchorPane invoiceTable=invoiceCustomeTable.getCutomTableComponent();
 
 		fitToAnchorePane(invoiceTable);
		
 		gridLocation_Panel.getChildren().addAll(invoiceTable);
//=============================================================================================================================================
 		 search_Btn.setText(getMessage("button.search"));
		 search_Btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SEARCH));
		 search_Btn.getStyleClass().setAll("btn","btn-info","btn-xs");  
		 search_Btn.setOnAction(e -> {
	        	search();
	        	enableInputes(false);

	        });	
	   //------------------------------------------------------------------------------------------------------
		 
		 cancel_Btn.setText(getMessage("button.cancelSearch"));
		 cancel_Btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.REMOVE));
		 cancel_Btn.getStyleClass().setAll("btn","btn-info","btn-xs"); 
	
		

		 cancel_Btn.setOnAction(e -> {
			 cancel();

		 });
 //=============================================================================================================================================

   	}
	
	
	 
		private void alert(AlertType alertType,String title,String headerText,String message) {
			 Alert a = new Alert(alertType);
			 a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			 a.setTitle(title );
			 a.setHeaderText(headerText);
			 a.setContentText(message); 
		    a.show(); 
		 
		}

	private void search() {
		

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");

		List invoices=new ArrayList<>();
		List invoicsViewBeans=new ArrayList();
		int customerId=(customerName_CB.getSelectionModel().getSelectedItem()!=null)?customerName_CB.getSelectionModel().getSelectedItem().getId():0;
		int status=invoiceStatus_CB.getSelectionModel().getSelectedItem().getId();
 		int typeId=customerType_CB.getSelectionModel().getSelectedItem().getId();
		try {
				
			invoices = this.getBillingService().getSuggestedOrders(customerId,status, getAppContext().getSeason().getId(), typeId, getAppContext().getFridage().getId());
					
			

			for (Object it : invoices) {
				CustomerOrder order=(CustomerOrder) it;
				InvoiceViewbean viewBean=new InvoiceViewbean();
				viewBean.setId(order.getId());
			//	viewBean.setInvoiceDate(sdf.format(order.getDueDate()));
				viewBean.setProductName(order.getProduct().getName());
				viewBean.setGrossWeight(order.getGrossweight());
				viewBean.setNetWeight(order.getNetWeight());
				viewBean.setNolun(order.getNolun());
			//	viewBean.setTotalAmount(order.getTotalPrice());
				viewBean.setTips(order.getNolun());
			//	viewBean.setCommision(order.getCommision());
			//	viewBean.setNetAmount(order.getNetPrice());
				viewBean.setOrderTag(order.getOrderTag());
				invoicsViewBeans.add(viewBean);

				
			}
			
			invoiceCustomeTable.loadTableData(invoicsViewBeans);
			
		} catch ( EmptyResultSetException e) {
			// TODO Auto-generated catch block
		alert(AlertType.WARNING, "", "", this.getMessage("msg.warning.noData"));
			
			invoiceCustomeTable.getTable().getItems().clear()	;	}
		catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			  alert(AlertType.ERROR, this.getMessage("msg.err"),this.getMessage("msg.err"), this.getMessage("msg.err.general"));
			  invoiceCustomeTable.getTable().getItems().clear();
	
		}
		
		
		
}

 
	private List prepareInvoiceControles(){
	    	
	    	
	    	generateInvoice_Btn=new JFXButton(this.getMessage("button.invoice.generate"));
 	    	generateInvoice_Btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));
 	    	generateInvoice_Btn.getStyleClass().setAll("btn","btn-info","btn-sm");                     //(2)
	    	generateInvoice_Btn.setOnMouseClicked((new EventHandler<MouseEvent>() { 
	 	    	   public void handle(MouseEvent event) { 
 	 	    		      initGenerateInvoice();
	 	    		      
	 	    		      
	 	    		   } 
	 	    		}));
	    	    
	    


	    	    List buttons =new ArrayList<JFXButton>(Arrays.asList(generateInvoice_Btn))  ;

	    	return buttons;
	    	
	    }


	@SuppressWarnings("static-access")
	protected void initGenerateInvoice() {

    	
		InvoiceViewbean item=(InvoiceViewbean) this.invoiceCustomeTable.getTable().getSelectionModel().getSelectedItem();
     	this.request=new HashMap<String,Object>();
    	request.put("invoiceId", item.getId());
    	request.put("typeId", customerType_CB.getSelectionModel().getSelectedItem().getId());
    	int invoiceStatus=invoiceStatus_CB.getSelectionModel().getSelectedItem().getId();
    	String title="";
    	Scene scene1=null;
     	if(invoiceStatus==InvoiceStatusEnum.UNDER_EDIT) {
    		
    	InvoiceView  form=new InvoiceView();
        	 scene1= new Scene(form.getView(), 850, 600);
         title=getMessage("button.invoice.generate")  ;

    	}
     	else if(invoiceStatus==InvoiceStatusEnum.UNDER_DELIVERY) {

    		
    		InvoicePaymentView form=new InvoicePaymentView();
        	 scene1= new Scene(form.getView(), 850, 600);
        	 title=getMessage("button.invoice.give");
    	
     	}
     	else if (invoiceStatus==InvoiceStatusEnum.ARCHIVED) {

    		   //
    		InvoiceArchiveView form=new InvoiceArchiveView ();
        	 scene1= new Scene(form.getView(), 850, 600);
        	 title=getMessage("button.invoice.archive");
    	
     	}
     	else {
     		
     		return;
     	}
      	
     	URL u=	 getClass().getClassLoader().getResource("appResources/custom.css");

     	Stage popupwindow=new Stage();
    	popupwindow.setMinHeight(400);
    	popupwindow.setMinWidth(900);

    	popupwindow.setResizable(true);
        String css =u.toExternalForm();
    	scene1.getStylesheets().addAll(css); 
    	popupwindow.initModality(Modality.APPLICATION_MODAL);
    	popupwindow.setTitle(title);
    	      
    	popupwindow.setScene(scene1);
    	popupwindow.setOnHiding( ev -> {
    		

    		System.out.println("window closes");
    		search();
        
    		
    		
    	});
    	
    	popupwindow.showAndWait();

    	
    	
    		
	}

 
	private void fitToAnchorePane(Node node) {
	
	
	AnchorPane.setTopAnchor(node,  0.0); 
	AnchorPane.setLeftAnchor(node,  0.0); 
	AnchorPane.setRightAnchor(node,  0.0); 
	AnchorPane.setBottomAnchor(node,  0.0); 
	
	
	
} 
    private List<Column> prepareInvoiceTabelColumns(){
    

         List<Column> columns=new ArrayList<Column>();
   
         Column  c=new Column(this.getMessage("invoice.No"), "id", "int", 20, true);
         columns.add(c);
        
         c=new Column(this.getMessage("label.invoice.tag"), "orderTag", "double", 65, true);
         columns.add(c);
         
         c=new Column(this.getMessage("label.grossWeight"), "grossWeight", "double", 15, true);
           columns.add(c);
         
    
    
    return columns;
    
    
    
    
    
    
    }

private void setCustomerParentKey( String key ) {
	
	
		/*
		 * for (Iterator iterator = customers.iterator(); iterator.hasNext();) {
		 * ComboBoxItem comboBoxItem = (ComboBoxItem) iterator.next();
		 * if(comboBoxItem.getParentKey().equals(key)) {
		 * 
		 * System.out.println(comboBoxItem); } }
		 */
	
 		  List<ComboBoxItem> result = customers.stream()  
		  .filter(customer -> customer.getParentKey().equals(key)) .collect(Collectors.toList());
		  
		  customerName_CB.getItems().setAll(result);
		  if(result.size()>0)
			  customerName_CB.getSelectionModel().selectFirst();
		 
}

//00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
	@Override
	public void rowSelected() {
 		
 	}



	@Override
	public void rowSelected(Object o) {
		// TODO Auto-generated method stub
		
	}
    
  
	private void  enableInputes(boolean value) {
		this.fromDate.setDisable(!value);
		this.toDate.setDisable(!value);
		invoiceStatus_CB.setDisable(!value);
		customerName_CB.setDisable(!value);
		customerType_CB.setDisable(!value);

		
	}
	private void  cancel() {
		enableInputes(true);
		
	this.invoiceCustomeTable.loadTableData(new ArrayList());

		
	}
}
