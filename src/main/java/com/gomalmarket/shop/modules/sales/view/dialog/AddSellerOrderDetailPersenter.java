package com.gomalmarket.shop.modules.sales.view.dialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.glyphfont.FontAwesome;

import com.gomalmarket.shop.core.Enum.ProductTypeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.entities.basic.Product;
import com.gomalmarket.shop.core.entities.basic.Store;
import com.gomalmarket.shop.core.entities.customers.CustomerOrder;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.validator.Validator;
import com.gomalmarket.shop.modules.sales.action.SalesAction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddSellerOrderDetailPersenter extends SalesAction implements Initializable {
	
	 @FXML
	    private HBox buttonsPane;

	    private GridPane gridPane;
	    
	   
	
	    @FXML
	    private AnchorPane inputFormLoc;

	    @FXML
	    private AnchorPane rootPane;
	    
	    
    	Validator myvaValidator;

	    private JFXButton save_btn;
	    private JFXButton cancel_btn;
	    private  JFXComboBox<ComboBoxItem> productType;
	    private  JFXComboBox<ComboBoxItem> customer_cb;
	    private  JFXComboBox<ComboBoxItem> store_cb;
	    private JFXSnackbar snackBar;

	    private Label productTypeLabel=new Label(this.getMessage("label.product"));
	    private Label storeIdLabel=new Label(this.getMessage("label.store.name"));
	    private Label grossWeightLabel=new Label(this.getMessage("label.grossWeight"));
	    private Label boxCountLabel=new Label(this.getMessage("label.box"));
	    private Label unitePriceLabel=new Label(this.getMessage("label.invoice.unitePrice"));
	    private Label netWeightLabel=new Label(this.getMessage("label.netWeight"));
	    private Label amountLabel=new Label(this.getMessage("label.money.amount"));
	    private Label customerLabel=new Label(this.getMessage("label.customer"));
	    private Label cartoonBoxLabel=new Label(this.getMessage("label.box.cartoon"));

	    
	    
	    private   JFXTextField grossWeight;
	    private   JFXTextField netWeight;
	    private   JFXTextField count;
	    private   JFXTextField unitePrice;
	    private   JFXTextField amount;
	    private Map<String, Double> orderWithDrawalQuantity;
 	    private Map<Integer, Double> OrderDetailCash;
	    
	    public AddSellerOrderDetailPersenter() {
	    	
	    	OrderDetailCash=(Map<Integer, Double>) this.request.get("OrderDetailCashKey");
	    	
 		}
	    
	    
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gridPane=new GridPane();
		myvaValidator=new Validator();
		snackBar=new JFXSnackbar(rootPane);
				
        orderDataMap = new HashMap<>();
        orderWithDrawalQuantity= new HashMap<>();
		
		

		grossWeight=new JFXTextField();
		grossWeight.getStyleClass().add("TextField");
		grossWeight.textProperty().addListener((observable, oldValue, newValue) -> {
		    System.out.println("grossWeight changed from " + oldValue + " to " + newValue);
		    myvaValidator=new Validator();
		    if(newValue.length()>0) {
			    myvaValidator.getValidDouble(newValue, 0, Double.MAX_VALUE, "grossWeightValue", true);
			    if(myvaValidator.noException()) {
			    
					weightValueTracker();
			    }
			    else {
			    	
			    	newValue=oldValue;
			    	grossWeight.setText(newValue);
			    	
			    }
		    	
		    }
		
			
		});
		 
		 
		netWeight=new JFXTextField();
		netWeight.getStyleClass().add("TextField");
		
		 
		unitePrice=new JFXTextField();
		unitePrice.getStyleClass().add("TextField");
		unitePrice.textProperty().addListener((observable, oldValue, newValue) -> {
			    System.out.println("unitePrice changed from " + oldValue + " to " + newValue);
				
			    myvaValidator=new Validator();
			    if(newValue.length()>0) {
			    myvaValidator.getValidDouble(newValue, 0, Integer.MAX_VALUE, "unitePriceValue", true);
			    if(myvaValidator.noException()) {
			    	
			    costValueTracker();

			    }
			    
			    else {
			    	newValue=oldValue;
			    	unitePrice.setText(newValue);
			    }
			    
			    }
			    
			 
			 
		 });
		
		
		amount=new JFXTextField();
		amount.getStyleClass().add("TextField");
		
		 
		 count=new JFXTextField();
		 count.getStyleClass().add("TextField");
		 count.textProperty().addListener((observable, oldValue, newValue) -> {
			    System.out.println("count changed from " + oldValue + " to " + newValue);
			    myvaValidator=new Validator();
			    if(newValue.length()>0) {
			    myvaValidator.getValidInt(newValue, 0, Integer.MAX_VALUE, "countValue", true);
			    if(myvaValidator.noException()) {
			    	
				    packageNumberTracker();

			    }
			    
			    else {
			    	newValue=oldValue;
			    	count.setText(newValue);
			    }
			    
			    }
			    });
		
		
		
		
		
		
		
		
		// TODO Auto-generated method stub
		 
		 customer_cb=new <ComboBoxItem> JFXComboBox();
		 customer_cb.getStyleClass().add("comboBox");
			
			
		productType=new <ComboBoxItem> JFXComboBox();
		productType.getStyleClass().add("comboBox");
		productType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov,
                    final Number oldvalue, final Number newvalue)
            {
            	clearInputForm();
            	ComboBoxItem item=productType.getSelectionModel().getSelectedItem();
            	fillInputForm(item.getId());
            	loadCustomerOrders();
            	
            }
        });
		
		
		store_cb=new <ComboBoxItem> JFXComboBox();
		store_cb.getStyleClass().add("comboBox");

		store_cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov,
                    final Number oldvalue, final Number newvalue)
            {

            	loadCustomerOrders();

            	System.out.print("product type selected");
            	
            }
        });
		try {
			List products=this.getBaseService().findAllBeans(Product.class);
			for (Object p : products) {
				Product prod=(Product) p;
				productType.getItems().add(new ComboBoxItem(prod.getId(),prod.getName()));

			}
			
			productType.getSelectionModel().selectFirst();

			
			
			
			List stores=this.getBaseService().findAllBeans(Store.class);
			for (Object it : stores) {
				Store store=(Store) it;
				store_cb.getItems().add(new ComboBoxItem(store.getId(),String.valueOf(store.getId())));

			}
			
			
			store_cb.getSelectionModel().selectFirst();
			
			
		}catch (Exception e) {
				e.printStackTrace();		}
		
		//----------------------------------------------------------------------
		
		save_btn=new JFXButton(this.getMessage("button.save"));
      	save_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SAVE));
    	 save_btn.getStyleClass().setAll("btn","btn-primary","btn-sm");  
    	    save_btn.setOnMouseClicked((new EventHandler<MouseEvent>() { 
 	    	   public void handle(MouseEvent event) { 
 	    		      System.out.println("add has been clicked"); 
 	    		      saveData(); 
 	    		   } 
 	    		}));
    	    
    	    
    	    
    	cancel_btn=new JFXButton(this.getMessage("button.cancel"));
         cancel_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.CLOSE));
        cancel_btn.getStyleClass().setAll("btn","btn-primary","btn-sm"); 
        cancel_btn.setOnMouseClicked((new EventHandler<MouseEvent>() { 
	    	   public void handle(MouseEvent event) { 
	    		      System.out.println("add has been clicked"); 
	    	            orderDataMap.put("save", false);

	    		      Stage stage = (Stage) cancel_btn.getScene().getWindow();
	    		      // do what you have to do
	    		      stage.close();
	    		   } 
	    		}));
        
        
        
        buttonsPane.getChildren().addAll(Arrays.asList(save_btn,cancel_btn));
		//----------------------------------------------------------------------

        gridPane.setCenterShape(true);
	    gridPane.setAlignment(gridPane.getAlignment().CENTER);
	   // fillInputForm(1);
	   //productType.getSelectionModel().select(0);
	    loadCustomerOrders();
	}
	
	
	private void fillInputForm(int mode) {
		gridPane.getChildren().removeAll();
		gridPane.getChildren().clear();
		 gridPane.add(productTypeLabel, 0, 0);
		    gridPane.add(productType, 1, 0);
		
		switch (mode) {
		
		case 1:
			
			  

			    gridPane.add(storeIdLabel, 0, 1);
			    gridPane.add(store_cb, 1, 1);
			    
			    gridPane.add(grossWeightLabel, 0, 2);
			    gridPane.add(grossWeight, 1, 2);
			    
			    
			    gridPane.add(boxCountLabel, 0, 3);
			    gridPane.add(count, 1, 3);
			    
			    
			    gridPane.add(unitePriceLabel, 0, 4);
			    gridPane.add(unitePrice, 1, 4);
			    
			    gridPane.add(netWeightLabel, 0, 5);
			    gridPane.add(netWeight, 1, 5);
			    
			    gridPane.add(amountLabel, 0, 6);
			    gridPane.add(amount, 1, 6);
			    
			    gridPane.add(customerLabel, 0, 7);
			    gridPane.add(customer_cb, 1, 7);
			    
			break;
		case 2:
		    gridPane.add(storeIdLabel, 0, 1);
		    gridPane.add(store_cb, 1, 1);
		    
		    gridPane.add(cartoonBoxLabel, 0, 2);
		    gridPane.add(count, 1, 2);
			
			
		    gridPane.add(unitePriceLabel, 0, 3);
		    gridPane.add(unitePrice, 1, 3);
		    
		    gridPane.add(amountLabel, 0, 4);
		    gridPane.add(amount, 1, 4);
		   	    
		    gridPane.add(customerLabel, 0, 5);
		    gridPane.add(customer_cb, 1, 5);
			
			
			break;
		case 3:
			
		    gridPane.add(boxCountLabel, 0, 1);
		    gridPane.add(count, 1, 1);
			
			
		    gridPane.add(unitePriceLabel, 0, 2);
		    gridPane.add(unitePrice, 1, 2);
		    
		    gridPane.add(amountLabel, 0, 3);
		    gridPane.add(amount, 1, 3);
			
			
			
			break;
		case 4:
		  
		    
		    gridPane.add(grossWeightLabel, 0, 1);
		    gridPane.add(grossWeight, 1, 1);
		    
		    
		    gridPane.add(boxCountLabel, 0, 2);
		    gridPane.add(count, 1,2);
		    
		    
		    gridPane.add(unitePriceLabel, 0, 3);
		    gridPane.add(unitePrice, 1, 3);
		    
		    gridPane.add(netWeightLabel, 0, 4);
		    gridPane.add(netWeight, 1, 4);
		    
		    gridPane.add(amountLabel, 0, 5);
		    gridPane.add(amount, 1, 5);
			
			
			
			
			
			break;
		
		default :break;
		}
		fitToAnchorePane(gridPane);
		gridPane.setVgap(7);
		inputFormLoc.getChildren().clear();
		inputFormLoc.getChildren().setAll(gridPane);
		
		
		
		
		
		
	}

	private void clearInputForm() {
		
		this.amount.setText("");
		this.count.setText("");
		this.unitePrice.setText("");
		this.netWeight.setText("");
		this.grossWeight.setText("");
		this.amount.setText("");
 
		
		
		
		
		
		
		
	}
    private void fitToAnchorePane(Node node) {
    	
    	
    	AnchorPane.setTopAnchor(node,  0.0); 
    	AnchorPane.setLeftAnchor(node,  0.0); 
    	AnchorPane.setRightAnchor(node,  0.0); 
    	AnchorPane.setBottomAnchor(node,  0.0); 
    	
    	
    	
    } 

    private void saveData() {
	
	
    	int productId = productType.getSelectionModel().getSelectedItem().getId();
    	String productName=productType.getSelectionModel().getSelectedItem().getText();
        if (validateForm()) {
            String weight = grossWeight.getText();
            String package_number = (count.getText().length()==0)?"0":count.getText();
            String netWeight = this.netWeight.getText();
            String UnitePrice = unitePrice.getText();
            int orderId = 0;// customerNames_CB.getSelectedItem().toString();
            String totalCost = amount.getText();
            totalCost = String.valueOf(Math.rint(Double.parseDouble(totalCost)));
            String customerOrderName="";
            switch (productId) {

                case 1:
                	orderId = customer_cb.getSelectionModel().getSelectedItem().getId();
                	customerOrderName=customer_cb.getSelectionModel().getSelectedItem().getText();
                	addOrderWithDrawalQuantity(String.valueOf(orderId), Double.parseDouble(weight));
                    break;

                case 2:
                    weight = String.valueOf("0.0");
                    netWeight = "0.0";
                	orderId = customer_cb.getSelectionModel().getSelectedItem().getId();
                	customerOrderName=customer_cb.getSelectionModel().getSelectedItem().getText();

                	addOrderWithDrawalQuantity(String.valueOf(orderId), Double.parseDouble(package_number));

                    break;
                case 3:
                    weight = String.valueOf("0.0");
                    netWeight = "0.0";
                    orderId = 0;

                    break;
                case 4:
                	orderId = 0;

                    break;

            }

            orderDataMap.put("productId", String.valueOf(productId));
            orderDataMap.put("productName", productName);

            
            orderDataMap.put("grossWeight", weight);
            orderDataMap.put("count", package_number);
            orderDataMap.put("netWeight", netWeight);
            orderDataMap.put("unitePrice", UnitePrice);
            orderDataMap.put("customerOrderId",String.valueOf(orderId));
            orderDataMap.put("amount", totalCost);
            orderDataMap.put("customerOrderName", customerOrderName);
            orderDataMap.put("save", true);

            
            
         //   this.setOrderDataMap(orderDataMap);
            Stage stage = (Stage) cancel_btn.getScene().getWindow();
		      // do what you have to do
		      stage.close();        }
	
	
}
    
    
    
    
    
private void weightValueTracker() {
	

    if (!grossWeight.getText().isEmpty()) {
        double weight = Double.parseDouble(grossWeight.getText());

        if (weight != 0) {
            int index = productType.getSelectionModel().getSelectedItem().getId();
            double commission = 0.0;
            switch (index) {
                case 1:
                    commission = .935;
            netWeight.setText(String.valueOf(Math.rint(weight * commission)));
                    break;
                case 2:
                    commission = 0;
                    netWeight.setText(String.valueOf(Math.rint(weight * 1)));
                    break;
                case 3:
                	netWeight.setText(String.valueOf(Math.rint(weight * 1)));
                    break;
              

            }
          

        }
    }
	    
	
	
}







private void costValueTracker() {
	
	  int index = productType.getSelectionModel().getSelectedItem().getId();
      switch (index) {

          case 1:
              if (!netWeight.getText().isEmpty() && !unitePrice.getText().isEmpty()) {
                  double netWeightValue = Double.parseDouble(netWeight.getText());
                  double unitePrice = Double.parseDouble(this.unitePrice.getText());

                  if (netWeightValue != 0 && unitePrice != 0) {

                      double x = netWeightValue * unitePrice;
                      x = Math.rint(x);
                      this.amount.setText(String.valueOf(x));

                  }
              }

              break;
          case 2:
              if (!count.getText().isEmpty() && !unitePrice.getText().isEmpty()) {
                  double number = Double.parseDouble(count.getText());
                  double unitePrice = Double.parseDouble(this.unitePrice.getText());

                  if (number != 0 && unitePrice != 0) {

                      double x = number * unitePrice;
                      amount.setText(String.valueOf(x));

                  }
              }
              break;
          case 3:
              if (!count.getText().isEmpty() && !unitePrice.getText().isEmpty()) {
                  double number = Double.parseDouble(count.getText());
                  double unitePrice = Double.parseDouble(this.unitePrice.getText());

                  if (number != 0 && unitePrice != 0) {

                      double x = number * unitePrice;
                      amount.setText(String.valueOf(x));

                  }
              }
              break;

          case 4:
              if (!netWeight.getText().isEmpty() && !unitePrice.getText().isEmpty()) {
                  double netWeight = Double.parseDouble(this.netWeight.getText());
                  double unitePrice = Double.parseDouble(this.unitePrice.getText());

                  if (netWeight != 0 && unitePrice != 0) {

                      double x = netWeight * unitePrice;
                      amount.setText(String.valueOf(x));

                  }
              }

              break;

      }
	
	
}

private void packageNumberTracker() {
	
    
    if(productType.getSelectionModel().getSelectedItem().getId()==3){
           String weight=grossWeight.getText();
        String number=count.getText();
        if(!weight.isEmpty()&&!number.isEmpty()){
       
                             int packageNumber=Integer.parseInt(count.getText());
                             int x=packageNumber*3;
                              int gross_weight = Integer.parseInt(grossWeight.getText());
                              int netWeight=gross_weight-x;
                              this.netWeight.setText(String.valueOf(Math.rint(netWeight)));}
    
    
    
    }
    //imported banana
    if(productType.getSelectionModel().getSelectedItem().getId()==2){
           String unitePriceValue=unitePrice.getText();
        String number=count.getText();
        if(!unitePriceValue.isEmpty()&&!number.isEmpty()){
       
                             int packageNumber=Integer.parseInt(count.getText());
                             int totalAmount=Integer.parseInt(unitePriceValue)*packageNumber;
                               this.amount.setText(String.valueOf(totalAmount));}
    
    
    
    }
}
	private void loadCustomerOrders() {
		
		customer_cb.getItems().clear();
		if(productType.getItems().size()>0&&store_cb.getItems().size()>0) {
			
			int productId=productType.getSelectionModel().getSelectedItem().getId();
		
		int storeId=store_cb.getSelectionModel().getSelectedItem().getId();

	List result=this.getSalesService().getAllCustomersOrdersTags(getAppContext().getSeason().getId(), this.getAppContext().getFridage().getId(), productId, storeId);
	List data=new ArrayList();
	customer_cb.getItems().clear();
	for (Iterator iterator = result.iterator(); iterator.hasNext();) {
		CustomerOrder order = (CustomerOrder) iterator.next();
		ComboBoxItem item=new ComboBoxItem(order.getId(), order.getOrderTag());
		customer_cb.getItems().add(item);

	}
	customer_cb.getSelectionModel().selectFirst();
		}
	
	
	
}



    boolean validateForm() {

        String weight = grossWeight.getText();
        String packageNumber = count.getText();
        String UnitePrice = unitePrice.getText();
        
        int customerId=customer_cb.getSelectionModel().getSelectedItem().getId();
        double cashedQuantity=(OrderDetailCash!=null&&OrderDetailCash.get(customerId)!=null)?OrderDetailCash.get(customerId):0.0;
        int productId = productType.getSelectionModel().getSelectedItem().getId();

        switch (productId) {
            case 1:
            	
                int orderId = ((customer_cb.getSelectionModel().getSelectedItem())==null)?0:customer_cb.getSelectionModel().getSelectedItem().getId();
                   if (orderId==0) {
                	snackBar.show(this.getMessage("msg.err.cutomerOrder"), 1000);

                    return false;

                }

                else  if (weight.isEmpty()) {
                	snackBar.show(this.getMessage("msg.err.required.grossWeight"), 1000);
                    return false;
                }
                else   if (UnitePrice.isEmpty()) {
                	snackBar.show(this.getMessage("msg.err.required.unitePrice"), 1000);

                    return false;

                }
                 
            

                
                else   if (productId==ProductTypeEnum.local_bannana && !confirmWeight(orderId, Double.parseDouble(weight)+cashedQuantity, productId)) {
                	snackBar.show(this.getMessage("msg.err.notEnough.weight"), 1000);

                    return false;
                } 
                   
                   
            
                 break;
            case 2:
                 orderId = ((customer_cb.getSelectionModel().getSelectedItem())==null)?0:customer_cb.getSelectionModel().getSelectedItem().getId();
                if (orderId==0) {
             	snackBar.show(this.getMessage("msg.err.cutomerOrder"), 1000);

                 return false;

             }

            	
                if (packageNumber.isEmpty()) {

                	snackBar.show(this.getMessage("msg.err.required.count"), 1000);
                    return false;

                }
                if (UnitePrice.isEmpty()) {
                	
                	snackBar.show(this.getMessage("msg.err.required.unitePrice"), 1000);

                    return false;

                }
            
              
                else   if (!confirmWeight(orderId, Double.parseDouble(packageNumber)+cashedQuantity, productId)) {
                	snackBar.show(this.getMessage("msg.err.notEnough.count"), 1000);

                    return false;
                } 

                break;     
            case 3:

                if (packageNumber.isEmpty()) {
                	snackBar.show(this.getMessage("msg.err.required.count"), 1000);

                    return false;

                }
                else   if (UnitePrice.isEmpty()) {

                	snackBar.show(this.getMessage("msg.err.required.unitePrice"), 1000);
                    return false;

                } 
                break;
            case 4:

                if (weight.isEmpty()) {

                	snackBar.show(this.getMessage("msg.err.required.grossWeight"), 1000);
                    return false;
                }
                if (packageNumber.isEmpty()) {
                	snackBar.show(this.getMessage("msg.err.required.count"), 1000);

                    return false;

                }
                if (UnitePrice.isEmpty()) {
                	
                	
                	snackBar.show(this.getMessage("msg.err.required.unitePrice"), 1000);

                    return false;

                }
                break;
                default:break;

        }

        return true;

    }
   
	private boolean confirmWeight(int orderId, double quantity,int productId) {
		
	Map <String,Object>m=new HashMap<String,Object>();
	m.put("customerOrderId =", orderId);
 	String columnName=(productId==ProductTypeEnum.local_bannana)?"grossQuantity":"packageNumber";
		try {
			CustomerOrder order=(CustomerOrder) this.getBaseService().findBean(CustomerOrder.class, orderId);
	
			
			try {
				Object tmp=this.getSalesService().aggregate("SellerOrderWeight", "sum", columnName, m);
				double withDrawaled=Double.parseDouble((tmp==null)?"0.0":String.valueOf(tmp));
 
				double avaliableQuantity=(order.getGrossweight() - withDrawaled);
			return(quantity<=avaliableQuantity);
			} catch (EmptyResultSetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		} catch (DataBaseException | InvalidReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return false;
		
		
		
		
		
}
void addOrderWithDrawalQuantity(String name, Double quantity) {

    if (orderWithDrawalQuantity.containsKey(name)) {

        Double tempQuantity = orderWithDrawalQuantity.get(name);
        tempQuantity += quantity;
        orderWithDrawalQuantity.put(name, tempQuantity);

    } else {
        orderWithDrawalQuantity.put(name, quantity);

    }

}
}
