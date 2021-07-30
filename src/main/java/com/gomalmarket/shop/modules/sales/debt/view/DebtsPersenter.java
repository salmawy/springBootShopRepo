package com.gomalmarket.shop.modules.sales.debt.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.glyphfont.FontAwesome;
import org.springframework.context.ApplicationContext;

import com.gomalmarket.shop.core.Enum.SellerTypeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTableActions;
import com.gomalmarket.shop.core.UIComponents.customTable.PredicatableTable;
import com.gomalmarket.shop.core.entities.expanses.Installment;
import com.gomalmarket.shop.core.entities.sellers.SellerLoanBag;
import com.gomalmarket.shop.core.entities.sellers.SellerOrder;
import com.gomalmarket.shop.core.entities.sellers.SellerOrderWeight;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.modules.sales.action.SalesAction;
import com.gomalmarket.shop.modules.sales.debt.payPurchasedOrder.PayOffSalerOrderView;
import com.gomalmarket.shop.modules.sales.debt.view.beans.InstalmelmentVB;
import com.gomalmarket.shop.modules.sales.debt.view.beans.PrifSellerOrderVB;
import com.gomalmarket.shop.modules.sales.debt.view.beans.SellerDebtVB;
import com.gomalmarket.shop.modules.sales.view.beans.SellerOrderDetailVB;
import com.gomalmarket.shop.modules.sales.view.beans.SellerOrderVB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DebtsPersenter extends SalesAction implements CustomTableActions,Initializable{
	   

	
	@FXML
    private AnchorPane sellersTable_Loc;

    @FXML
    private AnchorPane sellerInstallments_loc;

    @FXML
    private Label currentDebtValue_label;

    @FXML
    private Label initailDebt_label;

    @FXML
    private Label collecteDebt_label;

    @FXML
    private Label intialDebtValue_label;

    @FXML
    private AnchorPane sellerOders_loc;

    @FXML
    private JFXComboBox<ComboBoxItem> sellerType_CB;

    @FXML
    private Label collectedDebtValue_label;

    @FXML
    private JFXTextField sellerName_TF;

    @FXML
    private Label currentDebt_label;

    @FXML
    private AnchorPane orderData_loc;
	    
	    CustomTable<SellerOrderDetailVB> orderDataCustomTable;
	    CustomTable<PrifSellerOrderVB> sellerOrdersCustomTable;
	    CustomTable<InstalmelmentVB> sellerInstallmentsCustomTable;
	    PredicatableTable<SellerDebtVB> sellersPredicatableTable;

		Logger logger = Logger.getLogger(this.getClass().getName());	

	    public DebtsPersenter() {

	    	logger.log(Level.INFO,"============================================================================================================");		}
	    
	    
	    
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		init();
		loadSellersDebts();
		
		

	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		
		
		List<Column> orderDataColumn=prepareSellerOrderDetailColumns();
		List<Column> prifOrderColumn=preparePrifOrderColumns();
		List sellersHeadNodes=prepareSellersHeaderNodes();

		List<Column> sellerInstallmentColumn=prepareSellerinstallmentColumns();
		List<Column> orderDebtColumn=prepareSellerDeptColumns();
//=========================================================================================================================================
		
		sellerOrdersCustomTable=new CustomTable<PrifSellerOrderVB>(prifOrderColumn, null, null, null, this, CustomTable.tableCard, SellerOrderVB.class);
		sellersPredicatableTable=new PredicatableTable<SellerDebtVB>(orderDebtColumn, sellersHeadNodes, null, new sellrsTableActionListner(), PredicatableTable.headTableCard, SellerOrderVB.class);
		sellerInstallmentsCustomTable=new CustomTable<InstalmelmentVB>(sellerInstallmentColumn, null, null, null, null, CustomTable.tableCard, SellerOrderVB.class);
		orderDataCustomTable=new CustomTable<SellerOrderDetailVB>(orderDataColumn, null, null, null, this, CustomTable.tableCard, SellerOrderVB.class);
//=========================================================================================================================================
		fitToAnchorePane(sellerOrdersCustomTable.getCutomTableComponent());
		fitToAnchorePane(sellersPredicatableTable.getCutomTableComponent());
		fitToAnchorePane(sellerInstallmentsCustomTable.getCutomTableComponent());
		fitToAnchorePane(orderDataCustomTable.getCutomTableComponent());
	//	sellerOrdersCustomTable.getCutomTableComponent().setPrefSize(200, 500);
	//	sellersPredicatableTable.getCutomTableComponent().setPrefSize(150, 450);
 	//	sellersPredicatableTable.setTablePrefTableHeight(450);

//=========================================================================================================================================
		orderData_loc.getChildren().addAll(orderDataCustomTable.getCutomTableComponent());
		sellerOders_loc.getChildren().addAll(sellerOrdersCustomTable.getCutomTableComponent());
		sellersTable_Loc.getChildren().addAll(sellersPredicatableTable.getCutomTableComponent());
		sellerInstallments_loc.getChildren().addAll(sellerInstallmentsCustomTable.getCutomTableComponent());
		
//=========================================================================================================================================
		sellerName_TF.getStyleClass().add("TextField");
		sellerName_TF.setPromptText(this.getMessage("seller.name"));
		
		sellerType_CB.getStyleClass().add("comboBox");
		sellerType_CB.setPromptText(this.getMessage("seller.type"));
		sellerType_CB.getItems().add(new ComboBoxItem(SellerTypeEnum.permenant.getId(),this.getMessage("seller.type.permenant")));
		sellerType_CB.getSelectionModel().selectFirst();
		
//=========================================================================================================================================
//		orderDataCustomTable.getTable().set
		JFXTreeTableView <SellerDebtVB>table=sellersPredicatableTable.getTable();
		
		  sellerName_TF.textProperty().addListener((o, oldVal, newVal) -> {	table.setPredicate(personProp -> {
              final SellerDebtVB seller = personProp.getValue();
              return seller.getSellerName().get().contains(newVal);
          });
	});
		
//=========================================================================================================================================
	  currentDebt_label.setText(this.getMessage("label.initailDebt"));
	  initailDebt_label.setText(this.getMessage("label.currentDebt"));
	  collecteDebt_label.setText(this.getMessage("label.collecteDebt"));
	  
	  
	  double intialDebt=this.getSalesService().getSeasonStartTotalSellersLoan(getAppContext().getSeason().getId());
	  double currentDebt=this.getSalesService().getSeasoncCurrentotalSellersLoan(getAppContext().getSeason().getId());

	  
	  currentDebtValue_label.setText(String.valueOf(currentDebt));
	  intialDebtValue_label.setText(String.valueOf(intialDebt));
	  collectedDebtValue_label.setText(String.valueOf(intialDebt-currentDebt));
	  
		    
		  
	}

	   
	   private void loadSellersDebts() {
		
		try {
			
			List debts=this.getSalesService().getSellersDebts(getAppContext().getSeason().getId(),1);
		   List data=new ArrayList();
			for (Iterator iterator = debts.iterator(); iterator.hasNext();) {
				SellerLoanBag bag = (SellerLoanBag) iterator.next();
				SellerDebtVB row=new SellerDebtVB();
				row.setId(bag.getId());
				row.setSellerId(bag.getSellerId());
				row.setSellerName(bag.getSeller().getName());

				row.setDueAmount(bag.getCurrentLoan());
				row.setTotalOrdersCost(bag.getDueLoan()+bag.getPriorLoan());
				data.add(row);
				
			}
		this.sellersPredicatableTable.loadTableData(data);
		
		
		} catch (EmptyResultSetException | DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
	   
	   @SuppressWarnings("unused")
	private void loadSellerPrifOrdersDebts(int sellerLoanBagId) {
		
		   List data=new ArrayList();

			Map<String,Object> map=new HashMap<String, Object>();
			map.put("sellerLoanBagId",sellerLoanBagId);
		   
		try {
			SellerLoanBag bag=(SellerLoanBag) this.getBaseService().findBean(SellerLoanBag.class, sellerLoanBagId);
			if(bag.getPriorLoan()>0) {
			PrifSellerOrderVB row=new PrifSellerOrderVB();
			row.setId(0);
			row.setFridageName(" ");
			row.setOrderDate(this.getMessage("label.oldDebt")+" "+bag.getNotes());
			row.setTotalOrderost(bag.getPriorLoan());
			data.add(row);
			}
			List orders=this.getBaseService().findAllBeans(SellerOrder.class, map, null);
			for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
				SellerOrder order = (SellerOrder) iterator.next();
				PrifSellerOrderVB	 row=new PrifSellerOrderVB();
				row.setId(order.getId());
				row.setFridageName(order.getFridage().getName());
				row.setOrderDate(PrifSellerOrderVB.sdf.format(order.getOrderDate()));
				row.setTotalOrderost(order.getTotalCost());
			
				data.add(row);
				
			}
		this.sellerOrdersCustomTable.loadTableData(data);
		
		
		} catch (EmptyResultSetException | DataBaseException e) {
			logger.warning("not orders Found for sellerloanBagId = "+sellerLoanBagId);

		} catch (InvalidReferenceException e) {
			// TODO Auto-generated catch block
			logger.warning("not sellerLoanBag Found for sellerloanBagId = "+sellerLoanBagId);
		}
		
		
		
		
		
		
		
	}
	
	
	   
	   private void loadSellersInstallments(int sellerLoanBagId) {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("sellerLoanBagId",sellerLoanBagId);
		try {
			
			List installments=this.getBaseService().findAllBeans(Installment.class, map, null);
		   List data=new ArrayList();
			for (Iterator iterator = installments.iterator(); iterator.hasNext();) {
				Installment installment = (Installment) iterator.next();
				InstalmelmentVB row=new InstalmelmentVB();
				row.setId(installment.getId());
				row.setAmount(installment.getAmount());
				row.setInstDate(InstalmelmentVB.sdf.format(installment.getInstalmentDate()));
				row.setNotes(installment.getNotes());
				
			
				data.add(row);
				
			}
		this.sellerInstallmentsCustomTable.loadTableData(data);
		
		
		} catch (EmptyResultSetException | DataBaseException e) {
			logger.warning("not instalments Found for sellerloanBagId = "+sellerLoanBagId);
		}
		
		
		
		
		
		
		
	}
	
	


@SuppressWarnings("unchecked")
private void loadOrderDetail(int orderId) {
	if(orderId==0){
		orderDataCustomTable.getTable().getItems().clear();
	}
	SellerOrder order;
	try {
 	List weights=this.getSalesService().getSellerOrderWeights(orderId);
	List data=new ArrayList();
	
	for (Iterator iterator = weights.iterator(); iterator.hasNext();) {
		SellerOrderWeight weight = (SellerOrderWeight) iterator.next();
		SellerOrderDetailVB viewBean=new SellerOrderDetailVB();
		viewBean.setAmount(weight.getAmount());
		viewBean.setCount(weight.getPackageNumber());
		viewBean.setCustomerOrderId(weight.getCustomerOrder().getId());
		viewBean.setCustomerOrderName(weight.getCustomerOrder().getOrderTag());
		viewBean.setGrossWeight(weight.getGrossQuantity());
		viewBean.setNetWeight(weight.getNetQuantity());
		viewBean.setProductId(weight.getProduct().getId());
		viewBean.setProductName(weight.getProduct().getName());
		viewBean.setSellerWeightId(weight.getId());
		viewBean.setUnitePrice(weight.getUnitePrice());
		viewBean.setAmount(weight.getAmount());

		data.add(viewBean);
	}
this.orderDataCustomTable.loadTableData(data);
	
	} catch (DataBaseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (EmptyResultSetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
}
	



   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	private void fitToAnchorePane(Node node) {
		
		
		AnchorPane.setTopAnchor(node,  0.0); 
		AnchorPane.setLeftAnchor(node,  0.0); 
		AnchorPane.setRightAnchor(node,  0.0); 
		AnchorPane.setBottomAnchor(node,  0.0); 
		
		
		
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
     
          
          c=new Column(this.getMessage("label.money.amount"), "amount", "String", 10, true);
          columns.add(c);
          
          
          
          
          c=new Column(this.getMessage("customer.name"), "customerOrderName", "double", 30, true);
          columns.add(c);
          
          
         
        
   return columns;
   
   
   
   
   
   
   }
 
    private List<Column> prepareSellerinstallmentColumns(){
        

        List<Column> columns=new ArrayList<Column>();
  
       
     
        Column  c=new Column(this.getMessage("label.money.amount"), "amount", "double", 25, true);
        columns.add(c);
       
      
          c=new Column(this.getMessage("label.date"), "instDate", "date", 30, true);
        columns.add(c);
       
          c=new Column(this.getMessage("label.notes"), "notes", "String", 45, true);
        columns.add(c);
       
      
    
          
         
        
   return columns;
   
   
   
   
   
   
   }
 
    private List<Column> prepareSellerDeptColumns(){
        

        List<Column> columns=new ArrayList<Column>();
  
        Column  c=new Column(this.getMessage("seller.name"), "sellerName", "String", 35, true);
        columns.add(c);
       
      
          c=new Column(this.getMessage("label.money.amount"), "dueAmount", "double", 15, true);
        columns.add(c);
       
          c=new Column(this.getMessage("label.banana.Price"), "totalOrdersCost", "date", 50, true);
        columns.add(c);
              
   return columns;
    }
 
    private List<Column> preparePrifOrderColumns(){
        

        List<Column> columns=new ArrayList<Column>();
  
       
     
        Column  c=new Column(this.getMessage("label.date"), "orderDate", "date", 50, true);
        columns.add(c);
       
      
          c=new Column(this.getMessage("label.fridage.num"), "fridageName", "String", 25, true);
        columns.add(c);
       
          c=new Column(this.getMessage("label.total.amount"), "totalOrderost", "double", 25, true);
        columns.add(c);
        return columns;
    }

	
	@Override
	public void rowSelected() {

		
		PrifSellerOrderVB item=	(PrifSellerOrderVB) this.sellerOrdersCustomTable.getTable().getSelectionModel().getSelectedItem();
		loadOrderDetail(item.getId());
		
		
	}
	@Override
	public void rowSelected(Object o) {
		// TODO Auto-generated method stub
		
	} 
	

	  private List prepareSellersHeaderNodes(){
			//button.purchases.confirm  button.save
			
			JFXButton addBtn=new JFXButton(this.getMessage("button.sellers.payOff"));
			addBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));
		 	    addBtn.getStyleClass().setAll("btn-xs","btn-primary");                     //(2)
			    addBtn.setOnAction(e -> {

			    	
			    	if(!sellersPredicatableTable.getTable().getSelectionModel().isEmpty())
			    	payOffSellerOrders();
			    	else {
			    		
			    		alert(AlertType.WARNING, "", "", this.getMessage("msg.err.should.select.seller"));

			    		
			    		
			    	}
			    	
			    	
			    });

			    List buttons =new ArrayList<JFXButton>(Arrays.asList(addBtn))  ;

			return buttons;
			
		}

	private void payOffSellerOrders() {

		 
				
				
 		SellerDebtVB sellerLoanBag=(SellerDebtVB)	((TreeItem<SellerDebtVB>) sellersPredicatableTable.getTable().getSelectionModel().getSelectedItem()).getValue();
		 	// =(item.getValue());

		
		  request=new HashMap<String,Object>();
		  request.put("loanBagId", sellerLoanBag.getId());
		  request.put("sellerId", sellerLoanBag.getSellerId());

		  request.put("name", sellerLoanBag.getSellerName().get()); 
		  request.put("action", 1);
		 
		
		
 		PayOffSalerOrderView form=new PayOffSalerOrderView();
		URL u=getClass().getClassLoader().getResource("appResources/custom.css");
  		Scene scene1= new Scene(form.getView(), 350, 420);
		Stage popupwindow=new Stage();
		popupwindow.setResizable(false);
		popupwindow.initStyle(StageStyle.TRANSPARENT);

	    String css =u.toExternalForm();
		scene1.getStylesheets().addAll(css); 
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		      
		popupwindow.setScene(scene1);
	popupwindow.setOnHiding( ev -> {
			

			if (response != null && response.get("valid") != null) {
				/*
				 * boolean valid=(boolean) response.get("valid");
				 * 
				 * int paid=-1; if(shop_radioBtn.isSelected()) paid=1; else
				 * if(owner_radioBtn.isSelected()) paid=0; String contractorName=(String)
				 * response.get("name"); Map<String,Object> map=new HashMap<String, Object>();
				 * ````````````
				 * map.put("name", contractorName); map.put("typeId", contractorTypeId);
				 * map.put("ownerId", ownerId);
				 * 
				 * try { Contractor contractor=(Contractor)
				 * this.getBaseService().getBean(Contractor.class, map);
				 * LoadSuppliersNames(ownerId);
				 * 
				 * this.loadContractorTransactions(contractor.getId(), paid,ownerId);
				 * //setContractorSeleted(contractor.getId());
				 * this.name_TF.setText(contractor.getName());
				 * contractorPredicatableTable.getTable().requestFocus();
				 * contractorPredicatableTable.getTable().getSelectionModel().select(0);
				 * contractorPredicatableTable.getTable().getFocusModel().focus(0); } catch
				 * (DataBaseException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } catch (InvalidReferenceException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 * 
				 * 
				 * alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.save"));
				 */}
	    
			
			
		});
		      
		popupwindow.showAndWait();
		

  
		
		
	}

	
//=======================================================================================================================================================================	
	private void alert(AlertType alertType,String title,String headerText,String message) {
		 Alert a = new Alert(alertType);
		 a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		 a.setTitle(title );
		 a.setHeaderText(headerText);
		 a.setContentText(message); 
	    a.show(); 
	 
	}

	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	class sellrsTableActionListner implements CustomTableActions 
	{
		JFXTreeTableView<SellerDebtVB> mytable;
		


		@Override
		public void rowSelected() {
		}

		@SuppressWarnings("unchecked")
		@Override
		public void rowSelected(Object table) {
			
			
			sellerOrdersCustomTable.getTable().getItems().clear();
			sellerInstallmentsCustomTable.getTable().getItems().clear();
			orderDataCustomTable.getTable().getItems().clear();

			
			mytable=(JFXTreeTableView<SellerDebtVB>) table;
			SellerDebtVB seller=	mytable.getSelectionModel().getSelectedItem().getValue();
			
			loadSellerPrifOrdersDebts(seller.getId());
			loadSellersInstallments(seller.getId());

			

			
			
			
			
			
			
			
		} 
	    
	}
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


	
	
	
	
	
	
	
	
	
}
