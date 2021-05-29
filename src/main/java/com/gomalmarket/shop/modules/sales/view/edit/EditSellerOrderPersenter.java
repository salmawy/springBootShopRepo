package com.gomalmarket.shop.modules.sales.view.edit;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome;

import com.gomalmarket.shop.core.Enum.SellerTypeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.entities.customers.CustomerOrder;
import com.gomalmarket.shop.core.entities.expanses.IncomeDetail;
import com.gomalmarket.shop.core.entities.sellers.Seller;
import com.gomalmarket.shop.core.entities.sellers.SellerOrder;
import com.gomalmarket.shop.core.entities.sellers.SellerOrderWeight;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.validator.Validator;
import com.gomalmarket.shop.modules.sales.action.SalesAction;
import com.gomalmarket.shop.modules.sales.view.beans.SellerOrderDetailVB;
import com.gomalmarket.shop.modules.sales.view.dialog.AddSellerOrderDetailView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class EditSellerOrderPersenter extends SalesAction implements Initializable {

    @FXML
    private AnchorPane orderDetail_loc;

    @FXML
    private HBox buttonPane;

    @FXML
    private AnchorPane gridPane_loc;

    @FXML
    private VBox SaveButtonPane;

    @FXML
    private JFXButton saveBtn;
    
    
    
    private GridPane gridPane;
private SellerOrder oldOrder;

	Logger logger = Logger.getLogger(this.getClass().getName());	

private JFXDatePicker datePicker;

private   JFXTextField name;
private   JFXTextField phone;
private   JFXTextField address;
private   JFXTextField totalAmount;
private   JFXTextField paidAmount;
private   JFXTextField restAmount;

private  JFXComboBox<ComboBoxItem> sellerType_CB;
private CustomTable <SellerOrderDetailVB> orderDetail_CT;

private Label sellerTypeLabel=new Label(this.getMessage("seller.type"));

private Label nameLabel=new Label(this.getMessage("seller.name"));

private Label phoneLabel=new Label(this.getMessage("customer.phone"));

private Label addressLabel=new Label(this.getMessage("customer.address"));

private  Label totalLabel=new Label(this.getMessage("label.total"));

private  Label paidAmountLabel=new Label(this.getMessage("label.money.paidAmount"));

private  Label restLabel=new Label(this.getMessage("label.money.rest"));

private  Label dateLabel=new Label(this.getMessage("label.date"));


Validator myvaValidator;

	private int mode;
  private final int Add_mode=0;
private final int edit_mode=1;
private int orderId;
public EditSellerOrderPersenter() {
 
	  orderId=	(int) this.request.get("orderId");
mode=(orderId==0)?Add_mode:edit_mode;

}
@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	// TODO Auto-generated method stub
	gridPane=new GridPane();

	sellerTypeLabel.getStyleClass().addAll("label-h1");
	nameLabel.getStyleClass().addAll("label-h1");
	phoneLabel.getStyleClass().addAll("label-h1");
	addressLabel.getStyleClass().addAll("label-h1");
	totalLabel.getStyleClass().addAll("label-h1");
	paidAmountLabel.getStyleClass().addAll("label-h1");
	restLabel.getStyleClass().addAll("label-h1");
	sellerTypeLabel.getStyleClass().addAll("label-h1");
	sellerTypeLabel.getStyleClass().addAll("label-h1");

	    


	
	datePicker=new JFXDatePicker();
	//datePicker.setPadding(new Insets(0, 0, 0, 100));

	datePicker.getEditor().setAlignment(Pos.CENTER);
	datePicker.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	datePicker.setConverter(new StringConverter<LocalDate>()
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
	List <Column>SellerOrderDetailColumns=prepareSellerOrderDetailColumns();
	List orderDetailControles=prepareOrderDetailcontrolles();
	orderDetail_CT=new CustomTable<SellerOrderDetailVB>(SellerOrderDetailColumns, orderDetailControles, null, null, null, CustomTable.headTableCard, SellerOrderDetailVB.class);

	
	fitToAnchorePane(orderDetail_CT.getCutomTableComponent());


	
	//===============upper section=====================================
	orderDetail_CT.getCutomTableComponent().setPrefSize(700, 270);
	orderDetail_loc.getChildren().setAll(orderDetail_CT.getCutomTableComponent());
	//================================

	// TODO Auto-generated method stub
	
	sellerType_CB=new JFXComboBox();
	sellerType_CB.getStyleClass().add("comboBox");
	sellerType_CB.getItems().add(new ComboBoxItem(SellerTypeEnum.cash,this.getMessage("seller.type.cash")));
	sellerType_CB.getItems().add(new ComboBoxItem(SellerTypeEnum.permenant,this.getMessage("seller.type.permenant")));
	sellerType_CB.getSelectionModel().selectFirst();
	sellerType_CB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
    {
        public void changed(ObservableValue<? extends Number> ov,
                final Number oldvalue, final Number newvalue)
        {
        	
        	 sellerTypeChangeHandler();
        	
        	
        }
    });
	
	
	name=new JFXTextField();
	 name.getStyleClass().add("TextField");
	 
	 TextFields.bindAutoCompletion(name, t-> {

            return this.getSalesService().getSuggestedSellerName( t.getUserText());

        });
	 
	 
	 phone=new JFXTextField();
	 phone.getStyleClass().add("TextField");
	
	 
	 address=new JFXTextField();
	 address.getStyleClass().add("TextField");
	 
	 totalAmount=new JFXTextField();
	 totalAmount.getStyleClass().add("TextField");
	 totalAmount.setDisable(true);
	 totalAmount.textProperty().addListener((observable, oldValue, newValue) -> {
		    System.out.println("totalAmount changed from " + oldValue + " to " + newValue);
		    myvaValidator=new Validator();
		    if(newValue.length()>0) {
			    myvaValidator.getValidDouble(newValue, 0, Double.MAX_VALUE, "grossWeightValue", true);
			    if(myvaValidator.noException()) {
			    
			    	trackPaidValue();
			    }
			    else {
			    	
			    	newValue=oldValue;
			    	totalAmount.setText(newValue);
			    	
			    }
		    	
		    }
	 });
	 paidAmount=new JFXTextField();
	 paidAmount.getStyleClass().add("TextField");
	 paidAmount.textProperty().addListener((observable, oldValue, newValue) -> {
		System.out.println("totalAmount changed from " + oldValue + " to " + newValue);
		    myvaValidator=new Validator();
		    if(newValue.length()>0) {
			    myvaValidator.getValidDouble(newValue, 0, Double.MAX_VALUE, "grossWeightValue", true);
			    if(myvaValidator.noException()) {
			    
			    	trackRestValue();
			    }
			    else {
			    	
			    	newValue=oldValue;
			    	paidAmount.setText(newValue);
			    	
			    }
		    	
		    }
	 });
	 
	 restAmount=new JFXTextField();
	 restAmount.getStyleClass().add("TextField");
	 restAmount.setDisable(true);

	 saveBtn.setText(this.getMessage("button.save"));
 	    saveBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SAVE));
	    saveBtn.getStyleClass().setAll("btn","btn-primary");  
	    saveBtn.setOnAction(e -> {
	    	
	    	save();
	    });
	   // buttonPane.getChildren().addAll(saveBtn);
	    
	    
//====================================================================================================
	    gridPane.setCenterShape(true);
	    gridPane.setAlignment(gridPane.getAlignment().CENTER);
	    
	    gridPane.add(sellerTypeLabel, 0, 0);
	    gridPane.add(sellerType_CB, 1, 0);
	    
	    
	    gridPane.add(nameLabel, 0, 1);
	    gridPane.add(name, 1, 1);
	    
	    gridPane.add(phoneLabel, 0, 2);
	    gridPane.add(phone, 1, 2);
	    
	    
	    gridPane.add(addressLabel, 0, 3);
	    gridPane.add(address, 1, 3);
	    
	    gridPane.add(totalLabel, 0, 4);
	    gridPane.add(totalAmount, 1, 4);
	    
	    gridPane.add(paidAmountLabel, 0, 5);
	    gridPane.add(paidAmount, 1, 5);
	    
	    
	    gridPane.add(restLabel, 0, 6);
	    gridPane.add(restAmount, 1, 6);
	    
		gridPane.add(dateLabel, 0, 7);
	    gridPane.add(datePicker, 1, 7);

	    
	    
		gridPane.setVgap(5);
		gridPane_loc.getChildren().clear();
		fitToAnchorePane(gridPane);
		gridPane_loc.getChildren().setAll(gridPane);
	    //================================================================================================================================
	   
		if(mode==edit_mode)
	  	loadOrderData();

		sellerTypeChangeHandler();

	
}

private void save() {

   	
    
    if(validateInputData()) {
 	   
 	   
 	   try {
 	   
 	   double paidAmount_=(paidAmount.getText().isEmpty())?0.0:Double.parseDouble(paidAmount.getText());
 	   int type=sellerType_CB.getSelectionModel().getSelectedItem().getId();
 	   Date orderDate=getDatePickerValue();
 	   
 	   
 	   Seller seller=new Seller();
 	   seller.setName(name.getText());
 	   seller.setAddress(address.getText());
 	   seller.setPhone(phone.getText());
 	   seller.setTypeId(type);
 
 	   SellerOrder order=new SellerOrder();
 	   order.setOrderDate(orderDate);
 	   order.setFridage(this.getAppContext().getFridage());
 	   order.setSeason(getAppContext().getSeason());
 	   order.setTotalCost(Double.parseDouble(totalAmount.getText()));
 	  
 	   List orderDetails=orderDetail_CT.getTable().getItems();
 	   Set<SellerOrderWeight>orerDetail=new HashSet<SellerOrderWeight>();
 	   for (Iterator iterator = orderDetails.iterator(); iterator.hasNext();) {
			SellerOrderDetailVB row = (SellerOrderDetailVB) iterator.next();
			SellerOrderWeight temp=new SellerOrderWeight();
			temp.setAmount(row.getAmount());
			
 			temp.setCustomerOrder(row.getCustomerOrder());
			
			temp.setGrossQuantity(row.getGrossWeight());
			temp.setNetQuantity(row.getNetWeight());
			temp.setPackageNumber(row.getCount());
			
 			temp.setProduct(row.getProduct());
			
			temp.setUnitePrice(row.getUnitePrice());
			orerDetail.add(temp);
			
		}
 	   
 	   order.setOrderWeights(orerDetail);
 	 if(this.orderId!=0)
 	   this.getSalesService().editeSellerOrder(seller, order, paidAmount_, this.oldOrder, getAppContext().getSeason().getId());
 	 else
 		 this.getSalesService().saveSellerOrder(seller, order, paidAmount_);
 	   
 	   clear();
 	   alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.edit"));
 	   Stage stage = (Stage) saveBtn.getScene().getWindow();
		      // do what you have to do
		      stage.close();
 	   
 	   
 	   
 	   }catch (Exception ex) {
	    	   alert(AlertType.ERROR, this.getMessage("msg.err"),this.getMessage("msg.err"), this.getMessage("msg.err.general"));

	
		}
 	   
 	   
 	   
 	   
    }
        	
        
}

private List<Column> prepareSellerOrderDetailColumns(){
    

    List<Column> columns=new ArrayList<Column>();

   
 
    Column  c=new Column(this.getMessage("label.product"), "productName", "date", 20, true);
    columns.add(c);
   
  
      c=new Column(this.getMessage("label.count"), "count", "date", 10, true);
    columns.add(c);
   
      c=new Column(this.getMessage("label.grossWeight"), "grossWeight", "date", 15, true);
    columns.add(c);
   
  
    c=new Column(this.getMessage("label.netWeight"), "netWeight", "date", 15, true);
      columns.add(c);
     
      c=new Column(this.getMessage("label.invoice.unitePrice"), "unitePrice", "String", 10, true);
      columns.add(c);
 
      c=new Column(this.getMessage("customer.name"), "customerOrderName", "double", 30, true);
      columns.add(c);
      
      
     
    
return columns;






}


private void fitToAnchorePane(Node node) {
	
	
	AnchorPane.setTopAnchor(node,  0.0); 
	AnchorPane.setLeftAnchor(node,  0.0); 
	AnchorPane.setRightAnchor(node,  0.0); 
	AnchorPane.setBottomAnchor(node,  0.0); 
	
	
	
}  


private List<JFXButton>prepareOrderDetailcontrolles(){
	
	
	JFXButton addBtn=new JFXButton(this.getMessage("button.add"));
  	    addBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));
	    addBtn.getStyleClass().setAll("btn","btn-info","btn-sm");                     //(2)
	    addBtn.setOnMouseClicked((new EventHandler<MouseEvent>() { 
	    	   public void handle(MouseEvent event) { 
	    		      System.out.println("add has been clicked"); 
	    		      addOrderDetail(); 
	    		   } 
	    		}));
	    
	    JFXButton deleteBtn=new JFXButton(this.getMessage("button.delete"));
      	    deleteBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.TRASH));
    	    deleteBtn.getStyleClass().setAll("btn","btn-danger","btn-sm");                     //(2)
    	    deleteBtn.setOnAction(e -> {
    	    	
    	    	
    	    	
    	        SellerOrderDetailVB selectedItem =(SellerOrderDetailVB) orderDetail_CT.getTable().getSelectionModel().getSelectedItem();
    	        try {
    	        	//valiate that customer order dosenot had an invoice 
					CustomerOrder customerOrder=(CustomerOrder) this.getBaseService().findBean(CustomerOrder.class, selectedItem.getCustomerOrderId());
					if(customerOrder.getEditeDate()!=null) {
						
						
						   orderDetail_CT.getTable().getItems().remove(selectedItem);
			    	     updateTotalAmountValue(selectedItem.getAmount(), -1);
					}
					
					else {
						
						alert(AlertType.WARNING, "", "", this.getMessage("msg.err.cannot.delete.weightCustomerOrder"));
						
					}
					
					
				} catch (DataBaseException | InvalidReferenceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    	        
    	        
    	     

    	    });


	    List buttons =new ArrayList<JFXButton>(Arrays.asList(new JFXButton [] {addBtn,deleteBtn}))  ;

	return buttons;
	
}


private void addOrderDetail() {
	
	AddSellerOrderDetailView form=new AddSellerOrderDetailView();
	URL u=	 getClass().getClassLoader().getResource("appResources/custom.css");

	Scene scene1= new Scene(form.getView(), 350, 420);
	Stage popupwindow=new Stage();
	popupwindow.setResizable(false);
    String css =u.toExternalForm();
	scene1.getStylesheets().addAll(css); 
	popupwindow.initModality(Modality.APPLICATION_MODAL);
	popupwindow.setTitle("");
	      
	popupwindow.setScene(scene1);
	popupwindow.setOnHiding( ev -> {
		

		System.out.println("window closes");
		
        addOrderDetailRow();
    
		
		
	});
	      
	popupwindow.showAndWait();

	


	
	
	
	
	
	
	
	
}

@SuppressWarnings("unchecked")
private void addOrderDetailRow() {
   
  
   Map m =this.getOrderDataMap();
   boolean save=(m.get("save")!=null)? (boolean) m.get("save"):false;
   if(save ){
   SellerOrderDetailVB viewBean=new SellerOrderDetailVB();
  
   viewBean.setAmount(Double.parseDouble(String.valueOf(m.get("amount")))  );
   viewBean.setCount(Integer.parseInt(String.valueOf( m.get("count")))  );
   viewBean.setCustomerOrderName(String.valueOf( m.get("customerOrderName")));
   viewBean.setGrossWeight(Double.parseDouble(String.valueOf(m.get("grossWeight") ))  );
   viewBean.setNetWeight(Double.parseDouble(String.valueOf( m.get("netWeight")))  );
   viewBean.setProductName(String.valueOf( m.get("productName"))  );
   viewBean.setUnitePrice(Double.parseDouble(String.valueOf(m.get("unitePrice")))  );
   viewBean.setCustomerOrderId(Integer.parseInt(String.valueOf(m.get("customerOrderId")))   );
   viewBean.setProductId(Integer.parseInt(String.valueOf(m.get("productId") ))    );
   
   
 this.orderDetail_CT.getTable().getItems().add(viewBean);
 updateTotalAmountValue(viewBean.getAmount(), 1);
   }

}

private void updateTotalAmountValue(double value,int mode) {
		//mode 1 => addition else subtract
		double  oldValue=(totalAmount.getText()!=null&&totalAmount.getText()!=""&&totalAmount.getText().length()>0)?Double.parseDouble(totalAmount.getText()):0.0;
		
		double newValue=(mode==1)?oldValue+value:oldValue-value;
		
		totalAmount.setText(String.valueOf(newValue));
		
		
		
		
		
}


private void trackPaidValue() {
if (!totalAmount.getText().isEmpty()) {
    double total = Double.parseDouble(totalAmount.getText());

    if (total != 0) {
        int index = sellerType_CB.getSelectionModel().getSelectedItem().getId();
        if (index == 1) {
            paidAmount.setText(paidAmount.getText());

        }

    }
} else {
	paidAmount.setText("");

}

}

private void	trackRestValue(){
		
		String total = totalAmount.getText();
		String paid = paidAmount.getText();
		double x = 0;
		if (total != null && paid != null) {
		if (!total.isEmpty() && !paid.isEmpty()) {
		    double paidvalue = Double.parseDouble(paid);
		    double totalvalue = Double.parseDouble(total);
		    x = totalvalue - paidvalue;
		
		}
		if (!total.isEmpty() && paid.isEmpty()) {
		    x = Double.parseDouble(total);
		}
		
		}
		
		restAmount.setText(String.valueOf(x));
}



	boolean validateInputData() {
		boolean valid = false;
		 Alert a = new Alert(AlertType.ERROR);
		 a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		a.setTitle(this.getMessage("msg.err"));
			a.setHeaderText(this.getMessage("msg.err"));
		int index = sellerType_CB.getSelectionModel().getSelectedIndex();
		double rest = (restAmount.getText().isEmpty())?0.0:Double.parseDouble(restAmount.getText());
		
		if (totalAmount.getText().isEmpty() || totalAmount.getText().equals("0.0")) {
		
				
				 a.setContentText(this.getMessage("msg.err.required.order.data")); 
				              a.show(); 
				   		   return false;

		}      
		
		else if (datePicker.getValue()==null||  getDatePickerValue()==null) {
			 a.setContentText(this.getMessage("msg.err.required.date")); 
			  
			    // show the dialog 
			    a.show();
			
				   return false;

			
			
		}
		
		else if (getDatePickerValue().after(new Date())) {
			
			
			 a.setContentText(this.getMessage("msg.err.should.dategreatherThan")); 
			  
			    // show the dialog 
			    a.show();
			
			
				   return false;

			
		}
		else  if (name.getText().isEmpty() && index == 1) {
		
			
			 a.setContentText(this.getMessage("msg.err.required.sellerName")); 
			  
		    // show the dialog 
		    a.show(); 
		    return false;
		}
		else  if (rest < 0) {
		    
		    a.setContentText(this.getMessage("msg.err.input.amount.greather")+totalAmount.getText()); 
			  
		    // show the dialog 
		    a.show(); 
		       
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

	public void clear() {
		this.address.setText("");
		this.paidAmount.setText("");
		this.phone.setText("");
		this.totalAmount.setText("");
		this.restAmount.setText("");
		this.name.setText("");
		
		orderDetail_CT.getTable().getItems().clear();
		
}



	
	
	private void loadOrderData() {
		
		
 		try {
		 oldOrder=	(SellerOrder) this.getBaseService().findBean(SellerOrder.class, orderId);
//==================================================================================
		if(oldOrder.getSeller().getTypeId()==1) 
		{	sellerType_CB.getSelectionModel().select(0);
			name.setText("");
			name.setDisable(true);
			paidAmount.setText(String.valueOf(oldOrder.getTotalCost()));
		}
		
		else
		{	sellerType_CB.getSelectionModel().select(1);
			name.setText(oldOrder.getSeller().getName());
			name.setDisable(false);
			
			//==================================================================================
			
			double paidAmount=getPaidAmount(orderId);
			this.paidAmount.setText(String.valueOf(paidAmount));
			//==================================================================================

 			this.datePicker.setValue(this.getBaseService().convertToLocalDateViaMilisecond(oldOrder.getOrderDate()));
			//==================================================================================
			
			totalAmount.setText(String.valueOf(oldOrder.getTotalCost()));
			
			//==================================================================================
				
			this.restAmount.setText(String.valueOf(oldOrder.getTotalCost()-paidAmount));
			
		
		}


//==================================================================================
	loadOrderDetail(orderId);
		
		
		
		
		
		} catch (DataBaseException e) {
			
			alert(AlertType.ERROR, this.getMessage("msg.err"),this.getMessage("msg.err"), this.getMessage("msg.err.general"));

			
			
		} catch (InvalidReferenceException e) {
			alert(AlertType.ERROR, this.getMessage("msg.err"),this.getMessage("msg.err"), this.getMessage("msg.err.cannot.load.data"));
		}
		
		
		this.loadOrderDetail(orderId);
		
		
		
		
		
		
		
	}

	
	private double getPaidAmount(int orderId) {
	
		
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("sellerOrderId", orderId);
		try {
			IncomeDetail income=	(IncomeDetail)this.getBaseService().findAllBeans(IncomeDetail.class, map, null).get(0);
		return income.getAmount();
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		
		
		
		
		
		return 0.0;
		
		
		
		
	}
	
	
	
	
	
	
	
@SuppressWarnings("unchecked")
private void loadOrderDetail(int orderId) {
	
	SellerOrder order;
	try {
		order = (SellerOrder) this.getBaseService().findBean(SellerOrder.class, orderId);
	
	List data=new ArrayList();
	
	for (Iterator iterator = order.getOrderWeights().iterator(); iterator.hasNext();) {
		SellerOrderWeight weight = (SellerOrderWeight) iterator.next();
		SellerOrderDetailVB viewBean=new SellerOrderDetailVB();
		viewBean.setAmount(weight.getAmount());
		viewBean.setCount(weight.getPackageNumber());
		viewBean.setCustomerOrderId(weight.getCustomerOrder().getId());
		viewBean.setCustomerOrder(weight.getCustomerOrder());

		
		viewBean.setCustomerOrderName(weight.getCustomerOrder().getOrderTag());
		viewBean.setGrossWeight(weight.getGrossQuantity());
		viewBean.setNetWeight(weight.getNetQuantity());
		
		viewBean.setProductId(weight.getProduct().getId());
		viewBean.setProduct(weight.getProduct());
		
		
		viewBean.setProductName(weight.getProduct().getName());
		
		
		viewBean.setSellerWeightId(weight.getId());
		viewBean.setOrderWeight(weight);
		
		
		viewBean.setUnitePrice(weight.getUnitePrice());
		data.add(viewBean);
	}
this.orderDetail_CT.loadTableData(data);
	
	} catch (DataBaseException | InvalidReferenceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
}
	


	private Date getDatePickerValue() {
		
		

   		
   		
   		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

   		Date date=new Date();
   		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
   		try {
   			return sdf.parse(datePicker.getValue().toString());
   			
		} catch (ParseException e2) {
			e2.printStackTrace();
		   	return null;

		}
   		
   		
		
		
		
	}

	
	private void sellerTypeChangeHandler() {
		
		ComboBoxItem item=sellerType_CB.getSelectionModel().getSelectedItem();
    	if(item.getId()==SellerTypeEnum.cash) {
    		
    		name.setDisable(true);
    		name.setText("");
    		paidAmount.setText(totalAmount.getText());
    		restAmount.setText("0.0");

    		
    	}
    	else {
    		
    		name.setDisable(false);
    		paidAmount.setText("0.0");
    		restAmount.setText("0.0");
    		
    		
    	}
		
		
	}
}
