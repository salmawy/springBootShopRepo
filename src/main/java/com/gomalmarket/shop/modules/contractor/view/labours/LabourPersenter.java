package com.gomalmarket.shop.modules.contractor.view.labours;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.glyphfont.FontAwesome;
import org.hibernate.criterion.Order;
import org.springframework.context.ApplicationContext;

import com.gomalmarket.shop.core.JPAOrderBy;
import com.gomalmarket.shop.core.Enum.ContractorTypeEnum;
import com.gomalmarket.shop.core.Enum.JPAOrderByEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTableActions;
import com.gomalmarket.shop.core.UIComponents.customTable.PredicatableTable;
import com.gomalmarket.shop.core.entities.contractor.Contractor;
import com.gomalmarket.shop.core.entities.contractor.ContractorTransaction;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.modules.contractor.action.ContractorAction;
import com.gomalmarket.shop.modules.contractor.view.addLabour.AddLabourView;
import com.gomalmarket.shop.modules.contractor.view.beans.ContractorDataVB;
import com.gomalmarket.shop.modules.contractor.view.beans.ContractorVB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LabourPersenter extends ContractorAction implements Initializable {
	
	Logger logger = Logger.getLogger(this.getClass().getName());	

	

	  @FXML
	    private Label ownerAmount_label;

	    @FXML
	    private TextField ownerAmountValue_label;

	    @FXML
	    private JFXTextField name_TF;

	    @FXML
	    private Label owner1_label;

	    @FXML
	    private Label shopValue_label;

	    @FXML
	    private Pane shop_coloredPane;

	    @FXML
	    private AnchorPane contractorTable_loc;

	    @FXML
	    private Label shop_label;

	    @FXML
	    private AnchorPane transactions_table;

	    @FXML
	    private Pane owner_coloredPane;

	    @FXML
	    private TextField shopAmountValue_label;

	    @FXML
	    private ToggleGroup g1;

	    @FXML
	    private Label owner1Value_label;

	    @FXML
	    private JFXRadioButton owner_radioBtn;

	    @FXML
	    private JFXRadioButton all_radioBtn;

	    @FXML
	    private Label shopAmount_label;

	    @FXML
	    private JFXRadioButton shop_radioBtn;

	    @FXML
	    private JFXComboBox<ComboBoxItem> owner_combo;
	private PredicatableTable<ContractorVB> contractorPredicatableTable;

	private CustomTable<ContractorDataVB> transactionsCustomeTable;
	private final int contractorTypeId=ContractorTypeEnum.LABOUR;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	  	  logger.log(Level.INFO,"============================================================================================================");
		init() ;
	}
	
	private void init() {
		
		
		//=========================================================================================================================================

		List contractorColumns=prepareContractorColumns();
		List transactionsColumns=prepareTransactionsColumns();
		List contratorHeadNodes=prepareContractorHeaderNodes();
		contractorPredicatableTable=new PredicatableTable<ContractorVB>(contractorColumns, contratorHeadNodes, null, new ContractorableActionListner(), CustomTable.headTableCard, ContractorVB.class);
		transactionsCustomeTable=new CustomTable<ContractorDataVB>(transactionsColumns, null, null, null, null, CustomTable.tableCard, ContractorDataVB.class);

		//=========================================================================================================================================
		fitToAnchorePane(contractorPredicatableTable.getCutomTableComponent());
		fitToAnchorePane(transactionsCustomeTable.getCutomTableComponent());

		contractorPredicatableTable.getCutomTableComponent().setPrefSize(150, 500);
		contractorTable_loc.getChildren().addAll(contractorPredicatableTable.getCutomTableComponent());
		transactions_table.getChildren().addAll(transactionsCustomeTable.getCutomTableComponent());

		//=========================================================================================================================================
		JFXTreeTableView <ContractorVB>table=contractorPredicatableTable.getTable();
		name_TF.setPromptText(getMessage("label.name"));
		 name_TF.textProperty().addListener((o, oldVal, newVal) -> {	
			  table.setPredicate(personProp -> {
            final ContractorVB contractor = personProp.getValue();
            return contractor.getName().get().contains(newVal);
        });
	});
		
//=========================================================================================================================================
	owner1_label.setText(this.getMessage("label.owner.name.kareem"));
	ownerAmount_label.setText(this.getMessage("label.owner.name.kareem"));
	shop_label.setText(this.getMessage("label.shop"));
	shopAmount_label.setText(this.getMessage("label.shop"));
	
	owner_radioBtn.setText(this.getMessage("label.owner.name.kareem"));
	shop_radioBtn.setText(this.getMessage("label.shop"));	  
	all_radioBtn.setText(this.getMessage("label.all"));	  
	all_radioBtn.setSelected(true);
		  
//=========================================================================================================================================
	
	owner_combo.setPromptText(this.getMessage("label.owner"));
	owner_combo.getStyleClass().add("comboBox");
	for (Iterator iterator = this.owners.iterator(); iterator.hasNext();) {
		ComboBoxItem object = (ComboBoxItem) iterator.next();
	owner_combo.getItems().add(object);}
	owner_combo.getSelectionModel().selectFirst();
	LoadLaboursNames(((ComboBoxItem)owners.get(0)).getId());
	
//=========================================================================================================================================
	calculateTotalShopAmount();
	calculateTotalOwnerAmount();
	//=========================================================================================================================================

	g1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
	      public void changed(ObservableValue<? extends Toggle> ov,
	          Toggle old_toggle, Toggle new_toggle) {
	        if (g1.getSelectedToggle() != null) {
	        	
	        	JFXRadioButton r=(JFXRadioButton) g1.getSelectedToggle();
	        int paid=-1;
	        	if(owner_radioBtn.getId()==r.getId()) {
	        		paid=0;
	        	}
	        	else if (shop_radioBtn.getId()==r.getId()) {
	        		paid=1;
	        	}
	        	
	        	JFXTreeTableView<ContractorVB>table=contractorPredicatableTable.getTable();
				 
	        	try {
	        		ContractorVB contractor= (ContractorVB) table.getSelectionModel().getSelectedItem().getValue();
					  int ownerId=owner_combo.getSelectionModel().getSelectedItem().getId();
		        	  loadContractorTransactions(contractor.getId(), paid,ownerId);
	        	}catch (NullPointerException e) {}
	        	
	        }
	      }
	    });	
	}
	
	
	
  private void LoadLaboursNames(int ownerId) {
  	
  	//contractorPredicatableTable.getTable().getRoot().getChildren().clear();

  	try {
			List contractors=this.getContractorService().getContractorAccount(0, getAppContext().getSeason().getId(), contractorTypeId);
		    List tableData=new LinkedList();
			
		    
		    for (Iterator iterator = contractors.iterator(); iterator.hasNext();) {
			
		    	Object[] contractor = (Object[]) iterator.next();
			
		    	ContractorVB  viewBean=new ContractorVB();
		    	int id =(Integer) contractor[0];
			    String name =(String) contractor[1];
		     	double amount =(Double) contractor[2];
		     	
		     	viewBean.setId(id);
		    	viewBean.setName((name) );
		    	viewBean.setAmount(amount);
		    	tableData.add(viewBean);
		    	
		}
  	
  	
  	
			this.contractorPredicatableTable.loadTableData(tableData);

  	
  	} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private List<Column> prepareContractorColumns(){
      

      List<Column> columns=new ArrayList<Column>();

     
   
      Column  c=new Column(this.getMessage("label.name"), "name", "date", 70, true);
      columns.add(c);
     
    
        c=new Column(this.getMessage("label.money.amount"), "amount", "date", 30, true);
      columns.add(c);
     
  
            
      
 return columns;
 
 
 
 
 
 
 }


	
  private List<Column> prepareTransactionsColumns(){
      

      List<Column> columns=new ArrayList<Column>();

     
   
      Column  c=new Column(this.getMessage("label.date"), "date", "date", 30, true);
      columns.add(c);
     
    
        c=new Column(this.getMessage("label.money.amount"), "amount", "double", 20, true);
      columns.add(c);
    
      c=new Column(this.getMessage("label.contractor.status.paid"), "paid", "String", 10, true);
      columns.add(c);
  
      c=new Column(this.getMessage("label.notes"), "notes", "String", 40, true);
      columns.add(c);      
      
 return columns;
 
 
 
 
 
 
 }

  private List prepareContractorHeaderNodes(){
	//button.purchases.confirm  button.save
	
	JFXButton addBtn=new JFXButton(this.getMessage("button.add"));
	addBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));

 	    addBtn.getStyleClass().setAll("btn","btn-primary");                     //(2)
	    addBtn.setOnAction(e -> {

	    	addTransaction();
	    	
	    	
	    	
	    });

	    List buttons =new ArrayList<JFXButton>(Arrays.asList(addBtn))  ;

	return buttons;
	
}

	private void addTransaction() {
	 
			int ownerId=owner_combo.getSelectionModel().getSelectedItem().getId();
			AddLabourView form=new AddLabourView();
			URL u=getClass().getClassLoader().getResource("appResources/custom.css");
			this.request=new HashMap<String, Object>();
			request.put("ownerId", ownerId);
			Scene scene1= new Scene(form.getView(), 350, 420);
			Stage popupwindow=new Stage();
			popupwindow.setResizable(false);
			popupwindow.initStyle(StageStyle.TRANSPARENT);

		    String css =u.toExternalForm();
			scene1.getStylesheets().addAll(css); 
			popupwindow.initModality(Modality.APPLICATION_MODAL);
			      
			popupwindow.setScene(scene1);
		popupwindow.setOnHiding( ev -> {
				

			if(response!=null&&response.get("valid")!=null) {
				boolean valid=(boolean) response.get("valid");
				
				  int paid=-1;
				  if(shop_radioBtn.isSelected())
					  paid=1;
				  else  if(owner_radioBtn.isSelected())
					  paid=0;
				String contractorName=(String) response.get("name");
				Map<String,Object> map=new HashMap<String, Object>();
				
				map.put("name", contractorName);
				map.put("typeId", contractorTypeId);
				map.put("ownerId", ownerId);
				try {
 					Contractor contractor=(Contractor) this.getBaseService().findBean(Contractor.class, map);
					  LoadLaboursNames(ownerId);

					this.loadContractorTransactions(contractor.getId(), paid,ownerId);
					//setContractorSeleted(contractor.getId());
					this.name_TF.setText(contractor.getName());
					    contractorPredicatableTable.getTable().requestFocus();
					    contractorPredicatableTable.getTable().getSelectionModel().select(0);
					    contractorPredicatableTable.getTable().getFocusModel().focus(0);
				} catch (DataBaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   catch (EmptyResultSetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.save"));
			}
		    
				
				
			});
			      
			popupwindow.showAndWait();
			
	
	  
			
	}

	
	
	
	
	
	
	
	
	
	
	
	private void fitToAnchorePane(Node node) {
		
		
		AnchorPane.setTopAnchor(node,  0.0); 
		AnchorPane.setLeftAnchor(node,  0.0); 
		AnchorPane.setRightAnchor(node,  0.0); 
		AnchorPane.setBottomAnchor(node,  0.0); 
		
		
		
	} 
	
	
	
	
	private void loadContractorTransactions(int id,int paid,int ownerId) {

		double ownerTotalAmount=0.0;
		double shopTotalPaidAmount=0.0;
		transactionsCustomeTable.getTable().getItems().clear();
		
		g1.getSelectedToggle();
		List tableData=new LinkedList();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("contractorAccount.contractor.id", id);
		map.put("contractorAccount.contractor.typeId", contractorTypeId);
		map.put("season.id", getAppContext().getSeason().getId());
		map.put("contractorAccount.contractor.ownerId", ownerId);

	if(paid>-1)
		map.put("paid", paid);

		List order=new LinkedList(Arrays.asList("detailDate"));
		
			try {
			List transactions=	this.getBaseService().findAllBeansWithDepthMapping(ContractorTransaction.class, map,order);
		for (Iterator iterator = transactions.iterator(); iterator.hasNext();) {
			
			ContractorTransaction transaction = (ContractorTransaction) iterator.next();
			ContractorDataVB viewBean=new ContractorDataVB();
			viewBean.setDate(ContractorDataVB.sdf.format(transaction.getTransactionDate()));
			viewBean.setId(transaction.getId());
			viewBean.setAmount(transaction.getAmount());
			viewBean.setNotes(transaction.getReport());
			
			viewBean.setPaid((transaction.getPaid()==1)?getAppContext().getMessages().getString("label.contractor.status.paid.no"):getAppContext().getMessages().getString("label.contractor.status.paid.no"));
			if(transaction.getPaid()==0)
				ownerTotalAmount+=transaction.getAmount();
			else
				shopTotalPaidAmount+=transaction.getAmount();
		
			
			tableData.add(viewBean);
		}
		
			transactionsCustomeTable.loadTableData(tableData);
			} catch (DataBaseException  e) {
		    	   alert(AlertType.ERROR, this.getMessage("msg.err"),this.getMessage("msg.err"), this.getMessage("msg.err.general"));
            } catch (EmptyResultSetException e) {
					alert(AlertType.WARNING, "", "", this.getMessage("msg.warning.noData"));}
			
			this.shopAmountValue_label.setText(String.valueOf(shopTotalPaidAmount));
			this.ownerAmountValue_label.setText(String.valueOf(ownerTotalAmount));


	} 
	
	
	
	
	
	
	
	private void alert(AlertType alertType,String title,String headerText,String message) {
		 Alert a = new Alert(alertType);
		 a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		 a.setTitle(title );
		 a.setHeaderText(headerText);
		 a.setContentText(message); 
	    a.show(); 
	 
	}
	
	
	private void calculateTotalShopAmount() {
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("contractorAccount.contractor.typeId =", contractorTypeId);
		map.put("season.id =", getAppContext().getSeason().getId());
		map.put("paid=", 1);
		map.put("contractorAccount.contractor.ownerId= ", owner_combo.getSelectionModel().getSelectedItem().getId());

		Double amount=0.0;
		try {
			 amount=(Double) this.getBaseService().aggregate("ContractorAccountDetail", "sum", "amount", map);
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			amount=0.0;
		}
		
		
		this.shopValue_label.setText(String.valueOf(amount));
		
		
	}
	
	
	
	private void calculateTotalOwnerAmount() {
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("contractorAccount.contractor.typeId=", contractorTypeId);
		map.put("season.id=", getAppContext().getSeason().getId());
		map.put("paid=", 0);
		map.put("contractorAccount.contractor.ownerId=", owner_combo.getSelectionModel().getSelectedItem().getId());

		Double amount=0.0;
		try {
			 amount=(Double) this.getBaseService().aggregate("ContractorAccountDetail", "sum", "amount", map);
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			amount=0.0;
		}
		
		
		this.owner1Value_label.setText(String.valueOf(amount));
		
		
	}
	
	
	
	
	
	
	
	
	private void setContractorSeleted(int id ) {
  	JFXTreeTableView<ContractorVB>table=contractorPredicatableTable.getTable();
  	ObservableList<TreeItem<ContractorVB>> data=	table.getRoot().getChildren();
  	int index=0;
  	for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			TreeItem<ContractorVB> treeItem = (TreeItem<ContractorVB>) iterator.next();
			if(treeItem.getValue().getId()==id)
				{
				//table.getSelectionModel().select(index);
				  table.requestFocus();
	    	        table.getSelectionModel().select(index);
	    	        table.getFocusModel().focus(index);
				}
			index+=1;
		}
  	
  	
  	

  	
  	
  	
  	
	}
	
	
	
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	class ContractorableActionListner implements CustomTableActions 
	{
		JFXTreeTableView<ContractorVB> mytable;
		


		@Override
		public void rowSelected() {
		}

		@SuppressWarnings("unchecked")
		@Override
		public void rowSelected(Object table) {
			
			  
			  
			  transactionsCustomeTable.getTable().getItems().clear();
			  
			  
			  mytable=(JFXTreeTableView<ContractorVB>) table; 
			  
			  ContractorVB contractor= mytable.getSelectionModel().getSelectedItem().getValue();
			  
			  
			  int paid=-1;
			  if(shop_radioBtn.isSelected())
				  paid=1;
			  else  if(owner_radioBtn.isSelected())
				  paid=0;
			  int ownerId=owner_combo.getSelectionModel().getSelectedItem().getId();
			  loadContractorTransactions(contractor.getId(),paid,ownerId);
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			 }

	
	    
	}
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


	
	
	

}
