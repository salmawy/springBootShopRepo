package com.gomalmarket.shop.modules.inventory.view.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;

import com.gomalmarket.shop.core.Enum.ProductTypeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTableActions;
import com.gomalmarket.shop.core.entities.Fridage;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.sales.action.SalesAction;
import com.jfoenix.controls.JFXComboBox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class InventoryPersenter extends SalesAction implements Initializable, CustomTableActions{

	  @FXML
	    private JFXComboBox<ComboBoxItem> fridage_CB;

	    @FXML
	    private AnchorPane root_Pan;

	    @FXML
	    private AnchorPane contnent_pane;

	    @FXML
	    private Label months_label;

	    @FXML
	    private Label fridage_label;

	    @FXML
	    private JFXComboBox<ComboBoxItem> months_CB;
	    
 //==================================================================================================================
	    
	
 
	
	Logger logger = Logger.getLogger(this.getClass().getName());	

	public InventoryPersenter() {
	  	  logger.log(Level.INFO,"============================================================================================================");
 //--------------------------------------------------------------------------------------------------------------------------------------------

	  	fillDates(getAppContext().getSeason().getId());

//--------------------------------------------------------------------------------------------------------------------------------------
	  	fillfridages();
//--------------------------------------------------------------------------------------------------------------------------------------

	  	
	  	fridage_label.setText( getMessage("label.fridage.name"));
	  	fridage_label.setText( getMessage("label.month"));

 	
}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
 	
		
		
	    

 		
		
		
		List <Column>sellerOrderColumns=prepareSellerOrdersColumns();
		/*
		 * List <Column>SellerOrderDetailColumns=prepareSellerOrderDetailColumns();
		 * 
		 * List orderDetailControles=prepareOrderDetailcontrolles();
		 * sellerOrdersCustomTable=new CustomTable<SellerOrderVB>(sellerOrderColumns,
		 * null, null, null, this, CustomTable.tableCard, SellerOrderVB.class);
		 * 
		 * 
		 * fitToAnchorePane(sellerOrdersCustomTable.getCutomTableComponent());
		 * 
		 * bookMasterPane.getChildren().addAll(sellerOrdersCustomTable.
		 * getCutomTableComponent());
		 * 
		 */
		//===============upper section=====================================
 		
		//================================

		// TODO Auto-generated method stub
		
	 
	
	 
 		  
		    
 //====================================================================================================
		   


 	
		
	}
	
	
	
	
	
 
	public void doInventory( ) {
 

		String month=months_CB.getSelectionModel().getSelectedItem().getText();
		int seasonId=getAppContext().getSeason().getId();
		int fridageId=getAppContext().getFridage().getId();
		
		
            double purchases =getInventoryService().getPurchasesProfit(month, seasonId, fridageId);
            double kareemWith = getInventoryService().getkaremmTotalWithdrawal(month, seasonId,fridageId,0);
            double expenses = getInventoryService().getTotalOutcome(month, seasonId,fridageId);
            double commision = getInventoryService().getCommisionProfit(month, seasonId, fridageId);
            double salami3 = getInventoryService().getSalamiProductsProfit( month, seasonId, fridageId,ProductTypeEnum.ayoshy);
            double salami4 = getInventoryService().getSalamiProductsProfit( month, seasonId, fridageId,ProductTypeEnum.ota3);
            double k_orders = getInventoryService().getKTotalOrders(month, seasonId, fridageId);
        //    double seasonStartSellersLoan = data_source.getSeasonStartTotalSellersLoan(seasonId);
          //  double seasonEndSellersLoan = data_source.getSeasonEndTotalSellersLoan(seasonId);
            double profit = (commision + salami3 + salami4 + +purchases) - ( expenses);
         
            
            
 
         //   Vector<Object> rowFridage = new Vector<>(Arrays.asList((purchases + salami4 + salami3 + commision), purchases, salami3, salami4, commision, k_orders));

          //  Vector<Object> row = new Vector<>(Arrays.asList(profit, purchases, salami3, salami4, commision, expenses, k_orders, kareemWith, seasonEndSellersLoan, seasonStartSellersLoan));

		/*
		 * if (fridageID == 0) { dtm.setDataVector(new Vector<>(Arrays.asList(row)),
		 * inventory_columnsTitles); } else { dtm.setDataVector(new
		 * Vector<>(Arrays.asList(rowFridage)), inventoryFridage_columnsTitles);
		 * 
		 * } inventoryHeader_table.setModel(dtm);
		 */
        }
	
	
	
	
	
	
    void fillDates(int seasonId) {
    	List dates;
		try {
			dates = getInventoryService().getInventoryDates(getAppContext().getSeason().getId());
	
    	months_CB.getItems().clear();
    	months_CB.getItems().add(new ComboBoxItem(0, getMessage("label.all")));
 
        for (int i = 0; i < dates.size(); i++) {
        	months_CB.getItems().add(new ComboBoxItem(i+1, (String) dates.get(i)));
        	
        }
      
		} catch (EmptyResultSetException | DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
  //--------------------------------------------------------------------------------------------------------------------------------------
    void fillfridages() {
    	List fridages;
		try {
			fridages = this.getBaseService().findAllBeans(Fridage.class);
	
    	fridage_CB.getItems().clear();
    	fridage_CB.getItems().add(new ComboBoxItem(0, getMessage("label.all")));
 
        for (int i = 0; i < fridages.size(); i++) {
        	Fridage fridage=(Fridage) fridages.get(i);
        	fridage_CB.getItems().add(new ComboBoxItem(fridage.getId(), fridage.getName()));
        	
        }
        
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
 
    //--------------------------------------------------------------------------------------------------------------------------------------
  //--------------------------------------------------------------------------------------------------------------------------------------
  //--------------------------------------------------------------------------------------------------------------------------------------
  //--------------------------------------------------------------------------------------------------------------------------------------
  //--------------------------------------------------------------------------------------------------------------------------------------

    private List<Column> prepareSellerOrdersColumns(){
        

        List<Column> columns=new ArrayList<Column>();
  
		/*
		 *  c=new Column("chk", "chk", "ck", 5, true); columns.add(c);
		 */
        
        Column   c=new Column("id", "id", "int", 0, false);
          columns.add(c);
        
        c=new Column(this.getMessage("seller.name"), "sellerName", "date", 30, true);
          columns.add(c);
         
          c=new Column(this.getMessage("seller.type"), "sellerType", "String", 20, true);
          columns.add(c);
     
          c=new Column(this.getMessage("label.total.amount"), "totalAmount", "double", 25, true);
          columns.add(c);
          
          c=new Column(this.getMessage("label.money.paidAmount"), "paidAmount", "double", 30, true);
          columns.add(c);
        
   return columns;
   
   
   
   
   
   
   }
	
 
    private void fitToAnchorePane(Node node) {
    	
    	
    	AnchorPane.setTopAnchor(node,  0.0); 
    	AnchorPane.setLeftAnchor(node,  0.0); 
    	AnchorPane.setRightAnchor(node,  0.0); 
    	AnchorPane.setBottomAnchor(node,  0.0); 
    	
    	
    	
    }
	@Override
	public void rowSelected() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void rowSelected(Object o) {
		// TODO Auto-generated method stub
		
	}  
 
 

 
 
 







}
