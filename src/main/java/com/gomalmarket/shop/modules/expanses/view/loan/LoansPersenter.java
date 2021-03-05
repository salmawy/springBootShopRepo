package com.gomalmarket.shop.modules.expanses.view.loan;

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

import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTableActions;
import com.gomalmarket.shop.core.UIComponents.customTable.PredicatableTable;
import com.gomalmarket.shop.core.entities.ShopLoan;
import com.gomalmarket.shop.core.entities.LoanAccount;
import com.gomalmarket.shop.core.entities.LoanPaying;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.expanses.action.ExpansesAction;
import com.gomalmarket.shop.modules.expanses.view.beans.LoanersNameVB;
import com.gomalmarket.shop.modules.expanses.view.payLoan.PayLoanView;
import com.gomalmarket.shop.modules.sales.debt.view.beans.InstalmelmentVB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoansPersenter extends ExpansesAction implements Initializable , CustomTableActions{
	
	Logger logger = Logger.getLogger(this.getClass().getName());	

	
	 @FXML
	    private Label loanType_label;

	    @FXML
	    private AnchorPane debtTable_loc;

	    @FXML
	    private AnchorPane loanersTable_loc;

	    @FXML
	    private Label totalDebtValue_label;

	    @FXML
	    private JFXComboBox<ComboBoxItem> loanType_combo;

	    @FXML
	    private Pane coloredPane;

	    @FXML
	    private AnchorPane installmentsTable;

	    @FXML
	    private Label totalDebt__label;
	    
	    @FXML
	    private JFXTextField name_TF;

	    private final String inLoan="IN_LOAN";
	    private final String outLoan="OUT_LOAN";
	    private CustomTable<InstalmelmentVB>installmentsCustomTable;
	    private CustomTable<InstalmelmentVB>debtsAmountsCustomtable;
	    private PredicatableTable<LoanersNameVB>loanersCustomTable;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	  	  logger.log(Level.INFO,"============================================================================================================");

		
		
		init();		
	}
    
private void init() {
	
//==================================================================================================================	

	
	List loanersNameColumns=prepareLoanersNamesColumns();
	List installementsColumns=prepareInstallmentColumns();
	List loanerNamesHeaderNodes=prepareLoanersNamesHeaderNodes();
	
	installmentsCustomTable=new CustomTable<InstalmelmentVB>(installementsColumns, null, null, null, null, CustomTable.tableCard, InstalmelmentVB.class);
	loanersCustomTable=new PredicatableTable<LoanersNameVB>(loanersNameColumns, loanerNamesHeaderNodes, null, this, PredicatableTable.headTableCard, LoanersNameVB.class);
	debtsAmountsCustomtable=new CustomTable<InstalmelmentVB>(installementsColumns, null, null, null, null, CustomTable.tableCard, InstalmelmentVB.class);
	loanersCustomTable.getCutomTableComponent().setPrefSize(300, 300);
	debtsAmountsCustomtable.getCutomTableComponent().setPrefHeight(200);
	installmentsCustomTable.getCutomTableComponent().setPrefHeight(200);

	fitToAnchorePane(installmentsCustomTable.getCutomTableComponent());
	fitToAnchorePane(loanersCustomTable.getCutomTableComponent());
	fitToAnchorePane(debtsAmountsCustomtable.getCutomTableComponent());
	
	debtTable_loc.getChildren().addAll(debtsAmountsCustomtable.getCutomTableComponent());
	loanersTable_loc.getChildren().addAll(loanersCustomTable.getCutomTableComponent());
	installmentsTable.getChildren().addAll(installmentsCustomTable.getCutomTableComponent());
//==================================================================================================================	
	

	loanType_combo.getItems().add(new ComboBoxItem(1,this.getMessage("label.inLoan")));
	loanType_combo.getItems().add(new ComboBoxItem(2,this.getMessage("label.outLoan")));
	loanType_combo.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
    {
        public void changed(ObservableValue<? extends Number> ov,
                final Number oldvalue, final Number newvalue)
        {
        	
        	ComboBoxItem item=loanType_combo.getSelectionModel().getSelectedItem();
        	if(item.getId()==1) {
        		
        		initiateInLoanPage();
        		coloredPane.setStyle("-fx-background-color: #00A65A");
        		
        	}
        	else if(item.getId()==2) {
        		
        		initiateOutLoanPage();
        		coloredPane.setStyle("-fx-background-color: #DD4B39");

        		
        	}
        	
        	
        }
    });
	
	//==================================================================================================================	
	
	name_TF.setPromptText(this.getMessage("label.name"));
	JFXTreeTableView <LoanersNameVB>table=loanersCustomTable.getTable();
	
	name_TF.textProperty().addListener((o, oldVal, newVal) -> {	table.setPredicate(personProp -> {
        final LoanersNameVB loaner = personProp.getValue();
        return loaner.getName().get().contains(newVal);
    });
});
	
//==================================================================================================================	
	totalDebt__label.setText(this.getMessage("label.total.debt"));
//======================totalDebt__label============================================================================================	

	
}





private void loadInstallments(int id,String type) {
	
	installmentsCustomTable.getTable().getItems().clear();
	
	try {
		
		List installments=this.getExpansesServices().getLoanerInstalments(id, type);
	   List data=new ArrayList();
		for (Iterator iterator = installments.iterator(); iterator.hasNext();) {
			LoanPaying paying = (LoanPaying) iterator.next();
			InstalmelmentVB row=new InstalmelmentVB();
			row.setId(paying.getId());
			row.setAmount(paying.getPaidAmunt());
			row.setInstDate(InstalmelmentVB.sdf.format(paying.getPayingDate()));
			row.setNotes(paying.getNotes());
			
		
			data.add(row);
			
		}
	this.installmentsCustomTable.loadTableData(data);
	
	
	} catch (EmptyResultSetException | DataBaseException e) {
		logger.warning("not instalments Found for loanerID = "+id);
	}
	
	
	
}


private void loadDebts(int id,String type) {
	
	debtsAmountsCustomtable.getTable().getItems().clear();
	
	try {
		
		List installments=this.getExpansesServices().getLoanerDebts(id, type);
	   List data=new ArrayList();
		for (Iterator iterator = installments.iterator(); iterator.hasNext();) {
			ShopLoan installment = (ShopLoan) iterator.next();
			InstalmelmentVB row=new InstalmelmentVB();
			row.setId(installment.getId());
			row.setAmount(installment.getAmount());
			row.setInstDate(InstalmelmentVB.sdf.format(installment.getLoanDate()));
			row.setNotes(installment.getNotes());
			
		
			data.add(row);
			
		}
	this.debtsAmountsCustomtable.loadTableData(data);
	
	
	} catch (EmptyResultSetException | DataBaseException e) {
		logger.warning("not debts found  Found for loanerId = "+id);
	}
	
	
	
}

private void loadLoanerNames(String type) {
	
	//loanersCustomTable.getTable().getRoot().getChildren().clear();
	
	Map<String,Object> map=new HashMap<String, Object>();

	map.put("type", type);
	map.put("finished", 0);

    try {
		List data=(List) this.getBaseService().findAllBeans(LoanAccount.class, map,null);
		List tableData=new ArrayList();
		
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			LoanAccount account = (LoanAccount) iterator.next();
			LoanersNameVB row=new LoanersNameVB();
			row.setAmount(account.getDueAmount());
			row.setName(account.getLoaner().getName());
			row.setId(account.getLoaner().getId());
			tableData.add(row);
		}
		
		this.loanersCustomTable.loadTableData(tableData);
		
	} catch (DataBaseException | EmptyResultSetException e) {
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



void initiateInLoanPage(){
	Map<String,Object> map=new HashMap<String, Object>();

	
    map=new HashMap<String, Object>();
    loadLoanerNames(inLoan);
	map.put("type=", "'"+inLoan+"'");
	map.put("finished=", 0);
    
    Double amount=0.0;
	try {
		amount = (Double) this.getBaseService().aggregate("LoanAccount", "sum", "dueAmount", map);
	} catch (DataBaseException | EmptyResultSetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        this.totalDebtValue_label.setText(String.valueOf(amount));
    
    
    
    
}   

private List prepareLoanersNamesHeaderNodes(){
	//button.purchases.confirm  button.save
	
	JFXButton addBtn=new JFXButton(this.getMessage("button.add"));
  	    addBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));
	    addBtn.getStyleClass().setAll("btn","btn-primary");                     //(2)
	    addBtn.setOnAction(e -> {
	    	payLoan();
	    	
	    });

	    //editBtn.getStyleClass().add("control-button");
	    List buttons =new ArrayList<JFXButton>(Arrays.asList(addBtn))  ;

	return buttons;
	
}

private void payLoan() {
	 
			
			PayLoanView form=new PayLoanView();
			URL u=	 getClass().getClassLoader().getResource("appResources/custom.css");

			Scene scene1= new Scene(form.getView(), 350, 420);
			Stage popupwindow=new Stage();
			popupwindow.setResizable(false);
			popupwindow.initStyle(StageStyle.TRANSPARENT);

		    String css =u.toExternalForm();
			scene1.getStylesheets().addAll(css); 
			popupwindow.initModality(Modality.APPLICATION_MODAL);
			      
			popupwindow.setScene(scene1);
		popupwindow.setOnHiding( ev -> {
				

				System.out.println("window closes");
				
		    
				
				
			});
			      
			popupwindow.showAndWait();
			
	}

	  
		


private List<Column> prepareInstallmentColumns(){
    

    List<Column> columns=new ArrayList<Column>();

   
 
    Column  c=new Column(this.getMessage("label.money.amount"), "amount", "double", 25, true);
    columns.add(c);
   
  
      c=new Column(this.getMessage("label.date"), "instDate", "date", 30, true);
    columns.add(c);
   
      c=new Column(this.getMessage("label.notes"), "notes", "String", 45, true);
    columns.add(c);
    
return columns;

}



private List<Column> prepareLoanersNamesColumns(){
    

    List<Column> columns=new ArrayList<Column>();

   
    Column c=new Column(this.getMessage("label.name"), "name", "date", 60, true);
    columns.add(c);
    
    
    
      c=new Column(this.getMessage("label.money.amount"), "amount", "double", 40, true);
    columns.add(c);
    
return columns;


}

 void initiateOutLoanPage(){
		  
		    
		Map<String,Object> map=new HashMap<String, Object>();
		
		
		map=new HashMap<String, Object>();
		loadLoanerNames(outLoan);
		map.put("type=","'"+outLoan+"'");
		map.put("finished=", 0);
		
		Double amount=0.0;
		try {
			amount = (Double) this.getBaseService().aggregate("LoanAccount", "sum", "dueAmount", map);
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    this.totalDebtValue_label.setText(String.valueOf(amount));
		
		
		    
}

@Override
public void rowSelected() {
	// TODO Auto-generated method stub
	
}

@Override
public void rowSelected(Object table) {

	
	
	debtsAmountsCustomtable.getTable().getItems().clear();
	installmentsCustomTable.getTable().getItems().clear();

	
	JFXTreeTableView<LoanersNameVB>	mytable=(JFXTreeTableView<LoanersNameVB>) table;
	LoanersNameVB loaner=	mytable.getSelectionModel().getSelectedItem().getValue();
	String type=(loanType_combo.getSelectionModel().getSelectedItem().getId()==1)?inLoan:outLoan;
	loadDebts(loaner.getId(), type);
	loadInstallments(loaner.getId(), type);
	
}   
    
    
    
}
