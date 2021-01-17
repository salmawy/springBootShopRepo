package com.gomalmarket.shop.modules.billing.view.invoice.archived;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
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

import com.gomalmarket.shop.core.Enum.CustomerTypeEnum;
import com.gomalmarket.shop.core.Enum.InvoiceStatusEnum;
import com.gomalmarket.shop.core.Enum.ProductTypeEnum;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.entities.CustomerOrder;
import com.gomalmarket.shop.core.exception.BusinessLogicViolationException;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.modules.billing.action.BillingAction;
import com.gomalmarket.shop.modules.billing.view.beans.InvoiceWeight;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;

public class InvoiceArchivePersenter extends BillingAction  implements Initializable {
	
	Logger logger = Logger.getLogger(this.getClass().getName());	

	@FXML
    private AnchorPane root_pane;
	
	@FXML
    private Label gift_label;

    @FXML
    private JFXTextField gift_TF;

    @FXML
    private Label lost_label;

    @FXML
    private JFXTextField netAmount_TF;

    @FXML
    private JFXTextField invoiceDate_TF;

    @FXML
    private Label invoiceId_label;

    @FXML
    private Label nolun_label;

    @FXML
    private Label netWeight_label;

    @FXML
    private Label count_label;

    @FXML
    private Label netAmount_label;

    @FXML
    private Label commision_label;

    @FXML
    private JFXTextField productName_TF;

    @FXML
    private JFXButton generate_btn;

    @FXML

    private JFXButton   printInvoice_btn;
    
    @FXML
    private AnchorPane weightTable_loc;

    @FXML
    private JFXTextField totalAmount_TF;

    @FXML
    private Label weights_label;

    @FXML
    private JFXTextArea notes_TA;

    @FXML
    private JFXTextField nolun_TF;

    @FXML
    private Label grossWeight_label;

    @FXML
    private Label vehicleType_label;

    @FXML
    private Label invoiceDate_label;

    @FXML
    private JFXTextField vehicleType_TF;

    @FXML
    private JFXTextField netWeight_TF;

    @FXML
    private Label totalAmount_label;

    @FXML
    private JFXTextField invoiceId_TF;

    @FXML
    private JFXTextField count_TF;

    @FXML
    private Label productName_label;

    @FXML
    private JFXTextField commision_TF;

    @FXML
    private JFXTextField lost_TF;

    @FXML
    private JFXTextField grossWeight_TF;
    
    
    
     private CustomerOrder invoice;


	private int typeId;


	private int invoiceId;

    private JFXSnackbar snackBar;

	private CustomTable<InvoiceWeight> invoiceWeights;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
   logger.setLevel(Level.INFO);

logger.log(logger.getLevel(),"============================================================================================================");
   		

		  invoiceId=(int) this.request.get("invoiceId");
		  typeId=(int) this.request.get("typeId");
		
		
		init();
	}

 private  void init() {
 
		snackBar=new JFXSnackbar(root_pane);

	
//======================================================labels names=========================================================================
	  
 
		invoiceId_label.setText(this.getMessage("invoice.No"));
		invoiceDate_label.setText(this.getMessage("invoice.date"));
		productName_label.setText(this.getMessage("label.product"));
		nolun_label.setText(this.getMessage("label.nolun"));
		grossWeight_label.setText(this.getMessage("label.grossWeight"));
		netWeight_label.setText(this.getMessage("label.netWeight"));
		lost_label.setText(this.getMessage("label.empty"));
		count_label.setText(this.getMessage("label.count.sabait"));
		totalAmount_label.setText(this.getMessage("label.total.amount"));
		gift_label.setText(this.getMessage("label.gift"));
		commision_label.setText(this.getMessage("label.commision"));
		netAmount_label.setText((typeId==CustomerTypeEnum.purchases)?this.getMessage("button.purchases.price"):this.getMessage("label.netAmount"));
		vehicleType_label.setText(this.getMessage("label.vehicle.type"));
	

//===============================================================================================================================
		
		
		List invoiceWeightsColumns=prepareInvoiceWeightsColumns();
		List invoiceWeightsData=loadInvoiceWeights(invoiceId);
		invoiceWeights=new CustomTable<InvoiceWeight>(invoiceWeightsColumns, null, null, invoiceWeightsData, null, CustomTable.tableCard, InvoiceWeight.class);
		fitToAnchorePane(invoiceWeights.getCutomTableComponent());
		invoiceWeights.getCutomTableComponent().setPrefSize(100, 150);
		weightTable_loc.getChildren().addAll(invoiceWeights.getCutomTableComponent());


		
//===============================================================================================================================
	
		
		
		
		
		
		
		
		render();

		  	 
 }
 
 private List prepareInvoiceWeightsColumns() {

     

     List<Column> columns=new ArrayList<Column>();

    
  
     Column  c=new Column(this.getMessage("label.money.amount"), "amount", "double", 40, true);
     columns.add(c);
      
     c=new Column(this.getMessage("label.invoice.unitePrice"), "unitePrice", "double", 30, true);
     columns.add(c);
   
       c=new Column(this.getMessage("label.weight"), "weight", "double", 30, true);
     columns.add(c);
    
 
           
     
return columns;






 }

private void render() {
	 {                                                   

		 
		 
		 
		 
		 SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd"); 
		 weights_label.setText(getMessage("invoice.weightsSummary"));
		 weights_label.setMinWidth(200);
	
//========================================================================================
		 
		 printInvoice_btn.setText(getMessage("button.print"));
		 printInvoice_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SAVE));
		 printInvoice_btn.getStyleClass().setAll("btn","btn-info","btn-sm");                     //(2)
		 printInvoice_btn.setOnMouseClicked((new EventHandler<MouseEvent>() { 
		    	   public void handle(MouseEvent event) { 
		    		      System.out.println("printInvoice_btn has been clicked"); 
		    		      
		    		   //   int action =(int) request.get("action");
		    		    
		    		      
		    		    //  initGenerateInvoice();
		    		      
		    		      
		    		   } 
		    		}));
		 
		 
		 printInvoice_btn.setDisable(true);

		 generate_btn.setText(getMessage("button.generate"));
 		generate_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SAVE));
 	    generate_btn.getStyleClass().setAll("btn","btn-info","btn-sm");                     //(2)
	    generate_btn.setOnMouseClicked((new EventHandler<MouseEvent>() { 
	    	   public void handle(MouseEvent event) { 
	    		      System.out.println("generate_btn has been clicked"); 
	    		      
	    		      int action =(int) request.get("action");
	    		      switch (action) {
					case InvoiceStatusEnum.UNDER_EDIT:
						try {
							generate();
							alert(AlertType.CONFIRMATION, "", "", getMessage("msg.billing.invoiceHasbeenGenerated"));
							
						} catch (DataBaseException e) {
							e.printStackTrace();
					    	   alert(AlertType.ERROR, getMessage("msg.err"),getMessage("msg.err"), getMessage("msg.err.general"));

						} catch (BusinessLogicViolationException e) {
							///HandlerException hErr=new HandlerException(e);
							System.out.print(getMessage("msg.error.inputData"));
							//e.printStackTrace();
							
						}
						break;
					case InvoiceStatusEnum.UNDER_DELIVERY:
						payInvoice();
						break;
					default:
						break;
					}
	    		      
	    		    //  initGenerateInvoice();
	    		      
	    		      
	    		   } 
	    		}));
			
	    
	    
	    try{
				
				invoice=	(CustomerOrder) this.getBaseService().findBean(CustomerOrder.class, invoiceId);
			 
			} catch (DataBaseException | InvalidReferenceException e) {
				   alert(AlertType.ERROR, this.getMessage("msg.err"),
							  this.getMessage("msg.err"), 
							 this.getMessage("msg.err.general"));
				e.printStackTrace();
			}
 //========================================================================================
			 
	       // List orerWeights  = this.getBillingService().getCustomersOrderWeights(invoice.getId());

	        int product_id = invoice.getProduct().getId();
	        if (product_id == ProductTypeEnum.imported) {
	            this.netWeight_TF.setVisible(false);
	            this.netWeight_label.setVisible(false);
	            this.lost_label.setVisible(false);
	            this.count_TF.setVisible(false);
	            this.lost_TF.setVisible(false);
	            this.count_label.setVisible(false);
	            this.grossWeight_label.setText(this.getMessage("label.count"));

	       

	        } 

	        
//========================================================================================
 	        double totalAmount = getCustomerOrderTotalPrice(invoice.getId());
 	       double netWeight=getCustomerOrderNetWeight(invoice.getId());
	   	 this.invoiceId_TF.setText(String.valueOf(invoice.getId()));
		 this.invoiceDate_TF.setText(sdf.format(invoice.getOrderDate()));
		 this.productName_TF.setText(invoice.getProduct().getName());
		 this.nolun_TF.setText(String.valueOf(invoice.getNolun()));
		 this.grossWeight_TF.setText(String.valueOf(invoice.getGrossweight()));
		 this.count_TF.setText(String.valueOf(invoice.getUnits()));
 		 this.netWeight_TF.setText(String.valueOf(netWeight));
	     this.totalAmount_TF.setText(String.valueOf(totalAmount));
 		 this.vehicleType_TF.setText(String.valueOf(invoice.getVehicleType().getName()));
		 this.notes_TA.setText(invoice.getNotes());
		 

	        double commision = Math.rint(totalAmount * getAppContext().getCustomerOrderRatio());
	        this.commision_TF.setText(String.valueOf(commision));

	        double netPrice = totalAmount - (invoice.getNolun() + commision);
 	        this.netAmount_TF.setText(String.valueOf(netPrice));

	        

	        if (typeId==CustomerTypeEnum.purchases) {
	            commision = totalAmount - (invoice.getBuyPrice() + invoice.getNolun());
 	            this.commision_TF.setText(String.valueOf(commision));
	            this.netAmount_TF.setText(String.valueOf(invoice.getBuyPrice()));

	        }

  	        double ration = invoice.getGrossweight() - netWeight;
	        this.lost_TF.setText(String.valueOf(ration));
  }
 
 
 
  }
 
 
 
 
 
 
 
		 protected void generate() throws DataBaseException, BusinessLogicViolationException {
			 
			 
		     
		// TODO add your handling code here:
		if (validateForm()) {
		double netWeight = Double.parseDouble(netWeight_TF.getText());
		double totalPrice = Double.parseDouble(totalAmount_TF.getText());
		double netPrice = Double.parseDouble(netAmount_TF.getText());
		double tips = Double.parseDouble(gift_TF.getText());
		double commision = Double.parseDouble(commision_TF.getText());
		int order_id = Integer.parseInt(invoiceId_TF.getText());
		double ratio = Double.parseDouble(lost_TF.getText());
		String notes = notes_TA.getText();
		
		invoice.setNetWeight(netWeight);
		invoice.setNetPrice(netPrice);
		invoice.setTips(tips);
		invoice.setCommision(commision);
		invoice.setRatio(ratio);
		invoice.setNotes(notes);
		//invoice.setFinished(1);
		invoice.setEditeDate(new Date());
		invoice.setTotalPrice(totalPrice);
			
			    this.getBaseService().editBean(invoice);  
			 
			 
		
	
		
 		}
		else {
			throw new BusinessLogicViolationException("msg.error.inputData");
			
		}
		
			 
			 
			
}

		 
 private void  print(int orderId) {
			 
				
				
				
				if (invoice.getProduct().getId() == 1) {
					

		 			try {
						 Resource r=new ClassPathResource("reports/billing/invoice.jrxml"); 

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

					param.put("orderID", orderId);
					
		 		    param.put("SUBREPORT_DIR", new ClassPathResource("reports/billing").getPath() + "/");
		 			param.put("order_id",invoice.getId());

				 	getBaseService().printReport(param, report);
			 		
					} catch (DataBaseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				} else if (invoice.getProduct().getId()  == 2) { }
			 
			 
		 }
		 
		 
		 
		 
		 
protected void payInvoice() {
	// TODO Auto-generated method stub
	
}

private List loadInvoiceWeights(int id) {
	 
	 
	 
		List data=new ArrayList();
     try {
		List orerWeights  = this.getBillingService().getCustomersOrderWeights(id);
		
		for (Iterator iterator = orerWeights.iterator(); iterator.hasNext();) {
			Object[] row = (Object[]) iterator.next();
			InvoiceWeight weight=new InvoiceWeight((double)row[0],(double)row[1],(double)row[2]);
			data.add(weight);
			
			
		}
	
	} catch (EmptyResultSetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (DataBaseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
return data;
}

private double getCustomerOrderNetWeight(int orderId) {
	 
 
 
 

		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("customerOrderId=", orderId);
	 
		Double netWeight=0.0;
		try {
			netWeight=(Double) this.getBaseService().aggregate("SellerOrderWeight", "sum", "netQuantity", map);
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			netWeight=0.0;
		}
		
		
	return netWeight;
		 
 
  
 }
 
 

private void fitToAnchorePane(Node node) {
	
	
	AnchorPane.setTopAnchor(node,  0.0); 
	AnchorPane.setLeftAnchor(node,  0.0); 
	AnchorPane.setRightAnchor(node,  0.0); 
	AnchorPane.setBottomAnchor(node,  0.0); 
	
	
	
} 
 private double getCustomerOrderTotalPrice(int orderId) {
	 
   Map<String,Object> map=new HashMap<String, Object>();
		map.put("customerOrderId=", orderId);
	 
		Double netWeight=0.0;
		try {
			netWeight=(Double) this.getBaseService().aggregate("SellerOrderWeight", "sum", "amount", map);
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			netWeight=0.0;
		}
		
		
	return netWeight;
		 
 
  
 }
 
 
	private void alert(AlertType alertType,String title,String headerText,String message) {
		 Alert a = new Alert(alertType);
		 a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		 a.setTitle(title );
		 a.setHeaderText(headerText);
		 a.setContentText(message); 
	    a.show(); 
	 
	}
	
	
    boolean validateForm() {
	   
        if (gift_TF.getText().isEmpty()) {
        	snackBar.show(this.getMessage("msg.err.required.gift"), 1000);

             return false;
        } 
        
        
        
             return true;
        
    }
    
}
