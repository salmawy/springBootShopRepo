package com.gomalmarket.shop.modules.Customer.purchases.view;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.controlsfx.glyphfont.FontAwesome;
import org.hibernate.criterion.Order;

import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTableActions;
import com.gomalmarket.shop.core.UIComponents.customTable.EditCell;
import com.gomalmarket.shop.core.UIComponents.customTable.MyDoubleStringConverter;
import com.gomalmarket.shop.core.entities.CustomerOrder;
import com.gomalmarket.shop.core.entities.Fridage;
import com.gomalmarket.shop.core.entities.PurchasedCustomerInst;
import com.gomalmarket.shop.core.entities.Season;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.modules.Customer.action.CustomerBaseAction;
import com.gomalmarket.shop.modules.Customer.purchases.view.beans.PurchasedCustomersDataVB;
import com.gomalmarket.shop.modules.Customer.purchases.view.beans.PurchasedInstsViewBean;
import com.gomalmarket.shop.modules.Customer.purchases.view.beans.PurchasedInvoicesVB;
import com.gomalmarket.shop.modules.Customer.purchases.view.beans.PurchasedOrdersViewBean;
import com.gomalmarket.shop.modules.Customer.purchases.view.payPurchasedOrder.PayPurchasedOrderView;
import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
  import javafx.scene.input.KeyCode;
public class CustomerPurchasesPresenter extends CustomerBaseAction implements Initializable,CustomTableActions {

	
	Logger logger = Logger.getLogger(this.getClass().getName());	


	   @FXML
	    private AnchorPane instalments_loc;

	    @FXML
	    private Tab purchasesOrders_tab;

	    @FXML
	    private AnchorPane orderDetail_loc;

	    @FXML
	    private JFXButton refresh_btn;

	    
	    @FXML
	    private AnchorPane purchasesOrders_loc;

	    @FXML
	    private AnchorPane purchasedCustomer_loc;

	    @FXML
	    private Tab instalments_tab;
	private CustomTable<PurchasedInstsViewBean>instalmentsCustomTable;
	private CustomTable<PurchasedOrdersViewBean>purchasesOrdersCustomTable;
	private CustomTable<PurchasedCustomersDataVB>purchasesCustomerDataCustomTable;
	private CustomTable<PurchasedCustomersDataVB>purchasesInvoiceCustomTable;
	
	private TableColumn<PurchasedOrdersViewBean, Double> unitePriceColumn;
	private final String unitePriceColumnName="unitePrice";
 	
	@Override
	
	public void initialize(URL arg0, ResourceBundle arg1) {
	  	  logger.log(Level.INFO,"============================================================================================================");

		
		
		
		
		
		
		
		// TODO Auto-generated method stub
		instalments_tab.setText(this.getMessage("customer.purchases.instalments"));
		purchasesOrders_tab.setText(this.getMessage("customer.purchases.order.prices"));
		
		refresh_btn=new JFXButton(this.getMessage("button.add"));
     	    refresh_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.REFRESH));
    	    refresh_btn.getStyleClass().setAll("btn","btn-primary");  
		
		
		List <Column>instalmentsColumns=preparePurchasedInstsTabelColumns();
		List <Column>purchasesOrdersColumns=preparePurchasedOrdersTabelColumns();
		List <Column>purchasesCustomerDataColumns=preparePurchasedCustomerDataTabelColumns();
		List <Column>purchasesInvoicesColumns=prepareInvoiceTabelColumns();

		List<JFXButton>purchasesOrdersButtons=preparePurchasedOrdersButtons();
		List<JFXButton>instalmentsButtons=preparePurchasedInstsButtons();
		List customersData=loadCustomerData() ;
		instalmentsCustomTable=new CustomTable<PurchasedInstsViewBean>(instalmentsColumns, instalmentsButtons, null, null, null, CustomTable.headTableCard, PurchasedInstsViewBean.class);
		purchasesOrdersCustomTable=new CustomTable<PurchasedOrdersViewBean>(purchasesOrdersColumns, purchasesOrdersButtons, null, null, null, CustomTable.headTableCard, PurchasedOrdersViewBean.class);
		purchasesCustomerDataCustomTable=new CustomTable<PurchasedCustomersDataVB>(purchasesCustomerDataColumns, null, null, customersData, this, CustomTable.tableCard, PurchasedCustomersDataVB.class);
		purchasesInvoiceCustomTable=new CustomTable<PurchasedCustomersDataVB>(purchasesInvoicesColumns, null, null, null, null, CustomTable.tableCard, PurchasedInvoicesVB.class);
		fitToAnchorePane(instalmentsCustomTable.getCutomTableComponent());
        //============================================================================================================================================================================

		fitToAnchorePane(purchasesOrdersCustomTable.getCutomTableComponent());
		unitePriceColumn=purchasesOrdersCustomTable.getTableColumnById(unitePriceColumnName);
		setupUnitePriceColumn();
		setTableEditable();
	
	 //===============================================================================================================================================================
		fitToAnchorePane(purchasesCustomerDataCustomTable.getCutomTableComponent());
		fitToAnchorePane(purchasesInvoiceCustomTable.getCutomTableComponent());
		purchasesCustomerDataCustomTable.getCutomTableComponent().setPrefWidth(420);
		
		 //===============================================================================================================================================================

		instalments_loc.getChildren().addAll(instalmentsCustomTable.getCutomTableComponent());
		purchasesOrders_loc.getChildren().addAll(purchasesOrdersCustomTable.getCutomTableComponent());
		purchasedCustomer_loc.getChildren().addAll(purchasesCustomerDataCustomTable.getCutomTableComponent());
		orderDetail_loc.getChildren().addAll(purchasesInvoiceCustomTable.getCutomTableComponent());
		 //===============================================================================================================================================================

 	}

	
	
	
	List <PurchasedCustomersDataVB> loadCustomerData() {
		List customerViewBeans=new ArrayList<>();	
	Season season=this.getAppContext().getSeason();
	Fridage fridage=this.getAppContext().getFridage();
	
	List result;
	try {
		result = this.getCustomerService().getPurchasedCustomerData(getAppContext().getSeason(),null);
	
	
			for (Object it : result) {
				List row=(List) it;
				PurchasedCustomersDataVB viewBean=new PurchasedCustomersDataVB();
				viewBean.setId((int) row.get(0));
				viewBean.setName((String) row.get(1));
				viewBean.setOrderCost((double) row.get(2));
				viewBean.setPaidAmount((double) row.get(3));
				viewBean.setDueAmount((double) row.get(4));
				customerViewBeans.add(viewBean);

				
			}
			
			
	} catch (EmptyResultSetException | DataBaseException e) {
		// TODO Auto-generated catch block
			
		e.printStackTrace();
	}
		
		
		
		return customerViewBeans;
	} 
 
	
	
	
	List <PurchasedCustomersDataVB> loadCustomerInsts(int customerId) {
		List customerViewBeans=new ArrayList<>();	
	Season season=getAppContext().getSeason();
	Fridage fridage=this.getAppContext().getFridage();
	Map <String,Object>map=new HashMap <String,Object>();
	map.put("seasonId", season.getId());
	map.put("customerId", customerId);

	List result;
	try {
	//	result = this.getBaseService().findAllBeans(PurchasedCustomerInst.class, map, Order.desc("instalmentDate"));
	
		result = this.getBaseService().findAllBeans(PurchasedCustomerInst.class, map,null);

			for (Object it : result) {
				PurchasedCustomerInst row=(PurchasedCustomerInst) it;
				PurchasedInstsViewBean viewBean=new PurchasedInstsViewBean();
				viewBean.setId( row.getId());
				viewBean.setDate(PurchasedInstsViewBean.sdf.format(row.getInstalmentDate()));
				viewBean.setAmount( row.getAmount());
				viewBean.setNotes( row.getNotes());

				
				
				customerViewBeans.add(viewBean);

				
			}
			
			
	} catch (EmptyResultSetException | DataBaseException e) {
		// TODO Auto-generated catch block
			
		e.printStackTrace();
	}
		
		
		
		return customerViewBeans;
	} 
	
	List <PurchasedCustomersDataVB> loadCustomerOrders(int customerId) {
		List customerViewBeans=new ArrayList<>();	
	Season season=this.getAppContext().getSeason();
	Fridage fridage=this.getAppContext().getFridage();
	Map <String,Object>map=new HashMap <String,Object>();
	map.put("seasonId", season.getId());
	map.put("customerId", customerId);

	List result;
	try {
		result=	this.getCustomerService().getCustomerInvoices(season, customerId, null);
	
	
			for (Object it : result) {
				CustomerOrder row=(CustomerOrder) it;
				PurchasedOrdersViewBean viewBean=new PurchasedOrdersViewBean();
				viewBean.setId( row.getId());
				viewBean.setDate(PurchasedInstsViewBean.sdf.format(row.getOrderDate()));
				viewBean.setGrossWeight(row.getGrossweight());
				viewBean.setNolun(row.getNolun());
				viewBean.setUnitePrice(row.getUnitePrice());
				viewBean.setBuyPrice(row.getBuyPrice());

				
				
				customerViewBeans.add(viewBean);

				
			}
			
			
	} catch (EmptyResultSetException | DataBaseException e) {
		// TODO Auto-generated catch block
			
		e.printStackTrace();
	}
		
		
		
		return customerViewBeans;
	} 
 
	
    
	
    
    private List<Column> preparePurchasedInstsTabelColumns(){
    

         List<Column> columns=new ArrayList<Column>();
   
         Column  c=new Column("id", "id", "int", 0, false);
         columns.add(c);
         c=new Column(this.getMessage("label.date"), "date", "date", 30, true);
           columns.add(c);
          c=new Column(this.getMessage("label.money.amount"), "amount", "String", 20, true);
           columns.add(c);
          c=new Column(this.getMessage("label.notes"), "notes", "double", 50, true);
           columns.add(c);
         
    return columns;
    
    
    
    
    
    
    }
    
	
    
    private List<Column> preparePurchasedOrdersTabelColumns(){
        

        List<Column> columns=new ArrayList<Column>();
  
        Column  c=new Column("id", "id", "int", 0, false);
        columns.add(c);
        
        c=new Column(this.getMessage("invoice.date"), "date", "String", 30, true);
        columns.add(c);
       
        c=new Column(this.getMessage("label.grossWeight"), "grossWeight", "String", 20, true);
        columns.add(c);

        c=new Column(this.getMessage("label.nolun"), "nolun", "double", 10, true);
        columns.add(c);
       
        c=new Column(this.getMessage("label.invoice.unitePrice"), unitePriceColumnName, "double", 10, true,true);
        columns.add(c);
        
        c=new Column(this.getMessage("label.invoice.price"), "buyPrice", "double", 30, true,true);
        
          columns.add(c);
       
       
          
          
          
          
   return columns;
   
   
   
   
   
   
   }
  
   
    private List<Column> preparePurchasedCustomerDataTabelColumns(){
        

        List<Column> columns=new ArrayList<Column>();
  
        Column  c=new Column("id", "id", "int", 0, false);
        columns.add(c);
        c=new Column(this.getMessage("customer.name"), "name", "String", 30, true);
        columns.add(c);
        c=new Column(this.getMessage("label.bananaPrice"), "orderCost", "String", 30, true);
        columns.add(c);
       
        c=new Column(this.getMessage("label.money.paidAmount"), "paidAmount", "String", 20, true);
        columns.add(c);

        c=new Column(this.getMessage("label.money.dueAmount"), "dueAmount", "double", 20, true);
        columns.add(c);
        return columns;
   }
   
 
    private List<JFXButton>preparePurchasedOrdersButtons(){
    	//button.purchases.confirm  button.save
    	
    	JFXButton confirmBtn=new JFXButton(this.getMessage("button.purchases.confirm"));
     	    confirmBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.BARS));
    	    confirmBtn.getStyleClass().setAll("btn","btn-primary");                     //(2)


    	    confirmBtn.setOnAction(e -> {
    	    	@SuppressWarnings("unchecked")
				List<PurchasedOrdersViewBean> toBeEditedRecords=(List<PurchasedOrdersViewBean>) purchasesOrdersCustomTable.getTable().getItems().stream().filter(bean->((PurchasedOrdersViewBean) bean).isEdited()).collect(Collectors.toList());
    	    	if(toBeEditedRecords!=null&&toBeEditedRecords.size()>0) {
    	    		
    	    	System.out.println("confirm prices ");	
    	    	List <Object>beans=new ArrayList<Object>();
//----------------------------------------------------------------------------------------------------------------

    	    	for (Iterator iterator = toBeEditedRecords.iterator(); iterator.hasNext();) {
					PurchasedOrdersViewBean purchasedOrdersViewBean = (PurchasedOrdersViewBean) iterator.next();
					int id =purchasedOrdersViewBean.getId();
					try {
						CustomerOrder bean=(CustomerOrder) this.getBaseService().findBean(CustomerOrder.class, id);
						bean.setUnitePrice(purchasedOrdersViewBean.getUnitePrice());
						bean.setPeriodId(-1);
						bean.setBuyPrice(purchasedOrdersViewBean.getBuyPrice());
						beans.add(bean);
						
					} catch (DataBaseException | InvalidReferenceException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
//----------------------------------------------------------------------------------------------------------------
    	    	try {
					this.getBaseService().addEditBeans(beans);
				} catch (DataBaseException e1) {
					alert(AlertType.ERROR, getMessage("msg.err"),
							getMessage("msg.err"), 
							getMessage("msg.err.general"));
				}
//----------------------------------------------------------------------------------------------------------------

    	    	}
    	    	
    	    });
    	        List buttons =new ArrayList<JFXButton>(Arrays.asList(new JFXButton [] {confirmBtn}))  ;

    	return buttons;
    	
    }
 //===============================================================================================================

    private List<JFXButton>preparePurchasedInstsButtons(){
    	
    	JFXButton addBtn=new JFXButton(this.getMessage("button.add"));
     	    addBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));
    	    addBtn.getStyleClass().setAll("btn","btn-primary");                     //(2)

    	    addBtn.setOnAction(e -> {
    	    	if(!purchasesCustomerDataCustomTable.getTable().getSelectionModel().isEmpty())
    	    	   	payOrder();
    	    	
    	    });
    	    
    	    
    	    
    		
        	JFXButton editBtn=new JFXButton(this.getMessage("button.edit"));
         	    editBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.EDIT));
        	    editBtn.getStyleClass().setAll("btn","btn-primary");  
        	    
        	    editBtn.setOnAction(e -> {
        	    	if(!instalmentsCustomTable.getTable().getSelectionModel().isEmpty())
        	    	editPayOrder();
      			  
      			  
      			  
      		  });
    	    
    	    
    	    
    	    
     	    List buttons =new ArrayList<JFXButton>(Arrays.asList(new JFXButton [] {addBtn,editBtn}))  ;

    	return buttons;
    	
    }

  //===============================================================================================================
    private void editPayOrder() {


      	 
		String name=((PurchasedCustomersDataVB)purchasesCustomerDataCustomTable.getTable().getSelectionModel().getSelectedItem()).getName();
		int id=((PurchasedInstsViewBean)instalmentsCustomTable.getTable().getSelectionModel().getSelectedItem()).getId();
		double  amount=((PurchasedInstsViewBean)instalmentsCustomTable.getTable().getSelectionModel().getSelectedItem()).getAmount();
		String notes=((PurchasedInstsViewBean)instalmentsCustomTable.getTable().getSelectionModel().getSelectedItem()).getNotes();
		Date payDate=null;
		try {
			payDate = PurchasedInstsViewBean.sdf.parse(((PurchasedInstsViewBean)instalmentsCustomTable.getTable().getSelectionModel().getSelectedItem()).getDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		PayPurchasedOrderView form=new PayPurchasedOrderView();
		URL u=	 getClass().getClassLoader().getResource("appResources/custom.css");
		
		
		request=new HashMap<String, Object>();
		request.put("action",2);
		request.put("name",name);
		request.put("payDate",payDate);
		request.put("notes",notes);
		request.put("amount",amount);
		request.put("id",id);

		Scene scene1= new Scene(form.getView(), 350, 420);
		Stage popupwindow=new Stage();
		popupwindow.setResizable(false);
		popupwindow.initStyle(StageStyle.TRANSPARENT);

	    String css =u.toExternalForm();
		scene1.getStylesheets().addAll(css); 
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		      
		popupwindow.setScene(scene1);
	popupwindow.setOnHiding( ev -> {
			

			System.out.println("PayPurchasedOrderView   closes");
			
	    
			
			
		});
		      
		popupwindow.showAndWait();
		
			
	}
//===============================================================================================================
	private void payOrder() {

   	 
		PurchasedCustomersDataVB customer=((PurchasedCustomersDataVB)purchasesCustomerDataCustomTable.getTable().getSelectionModel().getSelectedItem());
		PayPurchasedOrderView form=new PayPurchasedOrderView();
		URL u=	 getClass().getClassLoader().getResource("appResources/custom.css");
		
		
		request=new HashMap<String, Object>();
		request.put("action", 1);
		request.put("name",customer.getName());
		request.put("id",customer.getId());

		
		
		Scene scene1= new Scene(form.getView(), 350, 420);
		Stage popupwindow=new Stage();
		popupwindow.setResizable(false);
		popupwindow.initStyle(StageStyle.TRANSPARENT);

	    String css =u.toExternalForm();
		scene1.getStylesheets().addAll(css); 
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		      
		popupwindow.setScene(scene1);
	popupwindow.setOnHiding( ev -> {
			

			System.out.println("PayPurchasedOrderView   closes");
			
	    
			
			
		});
		      
		popupwindow.showAndWait();
		
	}
//===============================================================================================================



	private void fitToAnchorePane(Node node) {
    	
    	
    	AnchorPane.setTopAnchor(node,  0.0); 
    	AnchorPane.setLeftAnchor(node,  0.0); 
    	AnchorPane.setRightAnchor(node,  0.0); 
    	AnchorPane.setBottomAnchor(node,  0.0); 
    	
    	
    	
    }  
	
    


List <PurchasedInvoicesVB> loadPurchasedInvoices(int customerId ) {
	

	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	Map<String,Object> map=new HashMap<String,Object>();
	List invoices=new ArrayList<>();
	List invoicsViewBeans=new ArrayList<>();
	try {
			
		invoices = this.getCustomerService().getCustomerInvoices(getAppContext().getSeason(), customerId,getAppContext().getFridage());
				
		

		for (Object it : invoices) {
			CustomerOrder order=(CustomerOrder) it;
			PurchasedInvoicesVB viewBean=new PurchasedInvoicesVB();
			viewBean.setId(order.getId());
			viewBean.setInvoiceDate(PurchasedInvoicesVB.sdf.format(order.getOrderDate()));
			viewBean.setBuyPrice(order.getBuyPrice());
			viewBean.setGrossWeight(order.getGrossweight());
			viewBean.setNetWeight(order.getNetWeight());
			viewBean.setNolun(order.getNolun());
			viewBean.setTotalAmount(order.getTotalPrice());
			viewBean.setTips(order.getNolun());
			viewBean.setCommision(order.getCommision());
			viewBean.setUnitePrice(order.getUnitePrice());
			viewBean.setVehicelType(order.getVehicleType().getName());

			invoicsViewBeans.add(viewBean);
		
		}
		
		
		
	} catch (DataBaseException | EmptyResultSetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
	return invoicsViewBeans;
} 
    
    


private List<Column> prepareInvoiceTabelColumns(){


     List<Column> columns=new ArrayList<Column>();

     Column  c=new Column("id", "id", "int", 0, false);
     columns.add(c);
     c=new Column(this.getMessage("invoice.date"), "invoiceDate", "date", 20, true);
       columns.add(c);
      c=new Column(this.getMessage("label.vehicle.type"), "vehicelType", "String", 12, true);
       columns.add(c);
      c=new Column(this.getMessage("label.grossWeight"), "grossWeight", "double", 10, true);
       columns.add(c);
      c=new Column(this.getMessage("label.netWeight"), "netWeight", "double", 10, true);
       columns.add(c);
      c=new Column(this.getMessage("label.nolun"), "nolun", "double", 7, true);
       columns.add(c);
      c=new Column(this.getMessage("label.total.amount"), "totalAmount", "string", 10, true);
      columns.add(c);
      
      c=new Column(this.getMessage("label.invoice.unitePrice"), "unitePrice", "string", 7, true);
      columns.add(c);
      
      c=new Column(this.getMessage("label.gift"), "tips", "double", 7, true);
      columns.add(c);
      c=new Column(this.getMessage("label.commision"), "commision", "double", 7, true);
        columns.add(c);
     c=new Column(this.getMessage("label.invoice.price"), "buyPrice", "double", 10, true);
        columns.add(c);


return columns;






}










@Override
public void rowSelected() {
	PurchasedCustomersDataVB item= (PurchasedCustomersDataVB) this.purchasesCustomerDataCustomTable.getTable().getSelectionModel().getSelectedItem();
	int customerId=item.getId();
	List data=null;
	
	List invoices=loadPurchasedInvoices(customerId);
	List orders=loadCustomerOrders(customerId);
	List insts=loadCustomerInsts(customerId);
	this.instalmentsCustomTable.loadTableData(insts);
	this.purchasesInvoiceCustomTable.loadTableData(invoices);
	this.purchasesOrdersCustomTable.loadTableData(orders);
	
}




@Override
public void rowSelected(Object o) {
	// TODO Auto-generated method stub
	
}




private void setupUnitePriceColumn() {
	unitePriceColumn.setCellFactory(
			EditCell.<PurchasedOrdersViewBean, Double>forTableColumn(
					new MyDoubleStringConverter()));
	// updates the salary field on the PersonTableData object to the
	// committed value 
	//validate edit cell here 
	unitePriceColumn.setOnEditCommit(event -> {
		purchasesOrdersCustomTable.getTable().setEditable(false);

		boolean invoiceGenerated=false;
		int customerOrederId=((PurchasedOrdersViewBean)(this.purchasesOrdersCustomTable.getTable().getSelectionModel().getSelectedItem())).getId();
	
		try {
			CustomerOrder order=(CustomerOrder) getBaseService().findBean(CustomerOrder.class, customerOrederId);
			invoiceGenerated=order.getEditeDate()!=null;
				
		
		} catch (DataBaseException | InvalidReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	boolean newValue=	event.getNewValue() != null&&!event.getNewValue().equals(event.getOldValue());
		
		if (newValue&&invoiceGenerated) {
			((PurchasedOrdersViewBean) event.getTableView().getItems().get(event.getTablePosition().getRow())).setUnitePrice(event.getOldValue());
			((PurchasedOrdersViewBean) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEdited(false);

			purchasesOrdersCustomTable.getTable().refresh();

				
				  alert(AlertType.ERROR, getMessage("msg.err"), getMessage("msg.err"),
				  getMessage("err.cannot.edit.invoiceGenerated"));
				 
				/*
				 * try { Thread.sleep(10000); } catch (InterruptedException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 */
		}
		else {
		
		
 		final Double value = (newValue)? event.getNewValue() : event.getOldValue();
 		
		((PurchasedOrdersViewBean) event.getTableView().getItems().get(event.getTablePosition().getRow())).setUnitePrice(value);
		((PurchasedOrdersViewBean) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEdited(newValue);
		purchasesOrdersCustomTable.getTable().refresh();}
		
		
		purchasesOrdersCustomTable.getTable().setEditable(true);

	});
}

private void setTableEditable() {
	purchasesOrdersCustomTable.getTable().setEditable(true);
	// allows the individual cells to be selected
	purchasesOrdersCustomTable.getTable().getSelectionModel().cellSelectionEnabledProperty().set(true);
	// when character or numbers pressed it will start edit in editable
	// fields
	purchasesOrdersCustomTable.getTable().setOnKeyPressed(event -> {
		System.out.println("isDigitKey=>"+event.getCode().isDigitKey());

		if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
			System.out.println("isDigitKey=>"+event.getCode().isDigitKey());
			System.out.println("isLetterKey=>"+event.getCode().isLetterKey());

			editFocusedCell();
		} else if (event.getCode() == KeyCode.RIGHT
				|| event.getCode() == KeyCode.TAB) {
			purchasesOrdersCustomTable.getTable().getSelectionModel().selectNext();
			event.consume();
		} else if (event.getCode() == KeyCode.LEFT) {
			// work around due to
			// TableView.getSelectionModel().selectPrevious() due to a bug
			// stopping it from working on
			// the first column in the last row of the table
			selectPrevious();
			event.consume();
		}
	});
}
//========================================
@SuppressWarnings("unchecked")
private void editFocusedCell() {
	final TablePosition<PurchasedOrdersViewBean, ?> focusedCell = (TablePosition<PurchasedOrdersViewBean, ?>) ((TableViewFocusModel) purchasesOrdersCustomTable.getTable()
			.focusModelProperty().get()).focusedCellProperty().get();
	purchasesOrdersCustomTable.getTable().edit(focusedCell.getRow(), focusedCell.getTableColumn());
}
//=========================================================

private void selectPrevious() {
	if (purchasesOrdersCustomTable.getTable().getSelectionModel().isCellSelectionEnabled()) {
		// in cell selection mode, we have to wrap around, going from
		// right-to-left, and then wrapping to the end of the previous line
		TablePosition<PurchasedOrdersViewBean, ?> pos = purchasesOrdersCustomTable.getTable().getFocusModel()
				.getFocusedCell();
		if (pos.getColumn() - 1 >= 0) {
			// go to previous row
			purchasesOrdersCustomTable.getTable().getSelectionModel().select(pos.getRow(),
					getTableColumn(pos.getTableColumn(), -1));
		} else if (pos.getRow() < purchasesOrdersCustomTable.getTable().getItems().size()) {
			// wrap to end of previous row
			purchasesOrdersCustomTable.getTable().getSelectionModel().select(pos.getRow() - 1,
					purchasesOrdersCustomTable.getTable().getVisibleLeafColumn(
							purchasesOrdersCustomTable.getTable().getVisibleLeafColumns().size() - 1));
		}
	} else {
		int focusIndex = purchasesOrdersCustomTable.getTable().getFocusModel().getFocusedIndex();
		if (focusIndex == -1) {
			purchasesOrdersCustomTable.getTable().getSelectionModel().select(purchasesOrdersCustomTable.getTable().getItems().size() - 1);
		} else if (focusIndex > 0) {
			purchasesOrdersCustomTable.getTable().getSelectionModel().select(focusIndex - 1);
		}
	}
}
//====================================================================================================
private TableColumn<PurchasedOrdersViewBean, ?> getTableColumn(
		final TableColumn<PurchasedOrdersViewBean, ?> column, int offset) {
	int columnIndex = purchasesOrdersCustomTable.getTable().getVisibleLeafIndex(column);
	int newColumnIndex = columnIndex + offset;
	return purchasesOrdersCustomTable.getTable().getVisibleLeafColumn(newColumnIndex);
}
//===========================================================================================================
private void alert(AlertType alertType,String title,String headerText,String message) {
	 Alert a = new Alert(alertType);
	 a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	 a.setTitle(title );
	 a.setHeaderText(headerText);
	 a.setContentText(message); 
	 a.initModality(Modality.WINDOW_MODAL);
   a.show(); 

}
}
