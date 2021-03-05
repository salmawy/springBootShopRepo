package com.gomalmarket.shop.modules.shopLoans.view.debit;

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

import com.gomalmarket.shop.core.Enum.ContractorTypeEnum;
import com.gomalmarket.shop.core.Enum.LoanTypeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTableActions;
import com.gomalmarket.shop.core.UIComponents.customTable.PredicatableTable;
import com.gomalmarket.shop.core.entities.Contractor;
import com.gomalmarket.shop.core.entities.ContractorAccountDetail;
import com.gomalmarket.shop.core.entities.ShopLoan;
import com.gomalmarket.shop.core.entities.LoanAccount;
import com.gomalmarket.shop.core.entities.LoanPaying;
import com.gomalmarket.shop.core.entities.Loaner;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.contractor.view.addLabour.AddLabourView;
import com.gomalmarket.shop.modules.contractor.view.beans.ContractorDataVB;
import com.gomalmarket.shop.modules.expanses.view.addOutcome.AddOutcomeView;
import com.gomalmarket.shop.modules.shopLoans.action.ShoploanAction;
import com.gomalmarket.shop.modules.shopLoans.view.beans.LoanVB;
import com.gomalmarket.shop.modules.shopLoans.view.beans.LoanerVB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DebitorPresenter extends ShoploanAction implements Initializable, CustomTableActions {

	@FXML
	private JFXTextField name_TF;

	@FXML
	private AnchorPane loanersTable_loc;

	@FXML
	private Pane owner_coloredPane;

	@FXML
	private Label totalDebit_label;

	@FXML
	private Label totalDebitValue_label;

	@FXML
	private AnchorPane loansContainer_loc;

	@FXML
	private Label name_label;

	@FXML
	private AnchorPane installmentsContainer_loc;

	private PredicatableTable<LoanerVB<LoanAccount>> loanerPredicatableTable;

	private CustomTable<LoanVB<ShopLoan>> loansCustomeTable;
	private CustomTable<LoanVB<LoanPaying>> LoanInstallmentsCustomeTable;

	private  final LoanTypeEnum  loanType = LoanTypeEnum.IN_LOAN;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		log.info(
				"==========================================================DebitorPresenter==================================================");
		init();
	}

	private void init() {

		// =========================================================================================================================================

		List LoanersColumns = prepareLoanerColumns();
		List LoansColumns = prepareLoanColumns();
		List instsColumns = prepareInstsColumns();
		List LoanerHeadNodes = prepareLoanerHeaderNodes();
		List LoansHeadNodes = prepareLoansHeaderNodes();
		List LoanInstallmentsHeadNodes = prepareLoaneInstllmentsHeaderNodes();

		loanerPredicatableTable = new PredicatableTable<LoanerVB<LoanAccount>>(LoanersColumns, LoanerHeadNodes, null,new LoanerTableActionListner(), CustomTable.headTableCard, LoanerVB.class);
		loansCustomeTable = new CustomTable<LoanVB<ShopLoan>>(LoansColumns, LoansHeadNodes, null, null, null,CustomTable.headTableCard, LoanVB.class);
		LoanInstallmentsCustomeTable = new CustomTable<LoanVB<LoanPaying>>(LoansColumns, LoanInstallmentsHeadNodes, null, null, null,CustomTable.headTableCard, LoanVB.class);

		// =========================================================================================================================================
		fitToAnchorePane(loanerPredicatableTable.getCutomTableComponent());
		fitToAnchorePane(loansCustomeTable.getCutomTableComponent());
		fitToAnchorePane(LoanInstallmentsCustomeTable.getCutomTableComponent());
		
		
		loanerPredicatableTable.getCutomTableComponent().setPrefSize(150, 500);
		loanersTable_loc.getChildren().addAll(loanerPredicatableTable.getCutomTableComponent());
		installmentsContainer_loc.getChildren().addAll(LoanInstallmentsCustomeTable.getCutomTableComponent());
		loansContainer_loc.getChildren().addAll(loansCustomeTable.getCutomTableComponent());

		// =========================================================================================================================================
		JFXTreeTableView<LoanerVB<LoanAccount>> table = loanerPredicatableTable.getTable();
		name_TF.setPromptText(getMessage("label.name"));
		name_TF.textProperty().addListener((o, oldVal, newVal) -> {
			table.setPredicate(personProp -> {
				final LoanerVB loaner = personProp.getValue();
				return loaner.getName().get().contains(newVal);
			});
		});

//=========================================================================================================================================
		totalDebit_label.setText(this.getMessage("label.shopLoans.totalLoan"));
		totalDebitValue_label.setText(this.getMessage("label.name"));
	 

//=========================================================================================================================================
	//	calculateTotalShopAmount();
	//	calculateTotalOwnerAmount();
		// ======================================================================================================================================
		
		LoadLoanersNames();
	}
	private void LoadLoanersNames() {

 
		
		  try {
		  
		  List loaners=this.getShopLoanService().getLoaners(loanType);//
		  List tableData=new LinkedList();
		  
		  
		  for (Iterator iterator = loaners.iterator(); iterator.hasNext();) {
			LoanAccount account = (LoanAccount) iterator.next();
			LoanerVB loanerVB=LoanerVB.builder()
					.amount(new SimpleDoubleProperty(account.getDueAmount()))
					.entity(account)
					.id(account.getLoaner().getId())
					.name(new SimpleStringProperty(account.getLoaner().getName()))
					.build();			
			tableData.add(loanerVB);
		}
		  
		  
		  this.loanerPredicatableTable.loadTableData(tableData);
		  
		  
		  } catch (DataBaseException | EmptyResultSetException e) { 
			  // TODO		  Auto-generated catch block
			  e.printStackTrace(); }
		 

	}

	private List<Column> prepareLoanerColumns() {

		List<Column> columns = new ArrayList<Column>();

		Column c = new Column(this.getMessage("label.name"), "name", "date", 70, true);
		columns.add(c);

		c = new Column(this.getMessage("label.money.amount"), "amount", "date", 30, true);
		columns.add(c);

		return columns;

	}

	private List<Column> prepareLoanColumns() {

		List<Column> columns = new ArrayList<Column>();

		Column c = new Column(this.getMessage("label.date"), "date", "date", 30, true);
		columns.add(c);

		c = new Column(this.getMessage("label.money.amount"), "amount", "double", 20, true);
		columns.add(c);

		c = new Column(this.getMessage("label.notes"), "notes", "String", 40, true);
		columns.add(c);

		return columns;

	}

	private List<Column> prepareInstsColumns() {

		List<Column> columns = new ArrayList<Column>();

		Column c = new Column(this.getMessage("label.date"), "date", "date", 30, true);
		columns.add(c);

		c = new Column(this.getMessage("label.money.amount"), "amount", "double", 20, true);
		columns.add(c);

		c = new Column(this.getMessage("label.notes"), "notes", "String", 40, true);
		columns.add(c);

		return columns;

	}

	private List prepareLoanerHeaderNodes() {
		// button.purchases.confirm button.save

		JFXButton addBtn = new JFXButton(this.getMessage("button.add"));
		addBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));

		addBtn.getStyleClass().setAll("btn", "btn-primary"); // (2)
		addBtn.setOnAction(e -> {

			addTransaction();

		});

		List buttons = new ArrayList<JFXButton>(Arrays.asList(addBtn));

		return buttons;

	}

	private List prepareLoansHeaderNodes() {
		// button.purchases.confirm button.save
		// ================================================================================

		JFXButton addBtn = new JFXButton(this.getMessage("button.add"));
		addBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));

		addBtn.getStyleClass().setAll("btn", "btn-primary"); // (2)
		addBtn.setOnAction(e -> {

			addLoan();

		});
		// ================================================================================
		JFXButton editBtn = new JFXButton(this.getMessage("button.edit"));
		editBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.EDIT));

		editBtn.getStyleClass().setAll("btn", "btn-primary"); // (2)
		editBtn.setOnAction(e -> {

			editLoan();

		});
		// ================================================================================

		List buttons = new ArrayList<JFXButton>(Arrays.asList(addBtn, editBtn));

		return buttons;

	}

	private List prepareLoaneInstllmentsHeaderNodes() {
		// button.purchases.confirm button.save
		// ================================================================================

		JFXButton addBtn = new JFXButton(this.getMessage("button.add"));
		addBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));

		addBtn.getStyleClass().setAll("btn", "btn-primary"); // (1)
		addBtn.setOnAction(e -> {

			addInstallmest();

		});
//================================================================================
		JFXButton editBtn = new JFXButton(this.getMessage("button.edit"));
		editBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.EDIT));

		editBtn.getStyleClass().setAll("btn", "btn-primary"); // (2)
		editBtn.setOnAction(e -> {

			editInstallment();

		});
		// ================================================================================

		List buttons = new ArrayList<JFXButton>(Arrays.asList(addBtn, editBtn));

		return buttons;

	}

	private void editLoan() {
		// TODO Auto-generated method stub

	}

	private void addLoan(int accountId) {
      	
 
		this.request=new HashMap<String,Object>();
		
		request.put("loanAccountId", accountId);
		request.put("loanType", this.loanType);

 		AddOutcomeView form=new AddOutcomeView();
		URL u=	 getClass().getClassLoader().getResource("appResources/custom.css");

   	    String css =u.toExternalForm();
		Scene scene1= new Scene(form.getView(), 463, 420);
		Stage popupwindow=new Stage();
		popupwindow.setResizable(false);
		popupwindow.initStyle(StageStyle.TRANSPARENT);

 		scene1.getStylesheets().addAll(css); 
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		      
		popupwindow.setScene(scene1);
	popupwindow.setOnHiding( ev -> {
			
    	//ComboBoxItem day=outcomeDay_CB.getSelectionModel().getSelectedItem();

// loadOutcomeData(day.getId());			
    	fillOutcomeMonthes();    
    	//loadOutcomeData(day.getValue());		
			
		});
		      
		popupwindow.showAndWait();
		
		

	
	
		
}

	private void editInstallment() {
		// TODO Auto-generated method stub

	}

	private void addInstallmest() {
		// TODO Auto-generated method stub

	}

	private void addTransaction() {
		/*
		 * 
		 * int ownerId = owner_combo.getSelectionModel().getSelectedItem().getId();
		 * AddLabourView form = new AddLabourView(); URL u =
		 * getClass().getClassLoader().getResource("appResources/custom.css");
		 * this.request = new HashMap<String, Object>(); request.put("ownerId",
		 * ownerId); Scene scene1 = new Scene(form.getView(), 350, 420); Stage
		 * popupwindow = new Stage(); popupwindow.setResizable(false);
		 * popupwindow.initStyle(StageStyle.TRANSPARENT);
		 * 
		 * String css = u.toExternalForm(); scene1.getStylesheets().addAll(css);
		 * popupwindow.initModality(Modality.APPLICATION_MODAL);
		 * 
		 * popupwindow.setScene(scene1); popupwindow.setOnHiding(ev -> {
		 * 
		 * if (response != null && response.get("valid") != null) { boolean valid =
		 * (boolean) response.get("valid");
		 * 
		 * int paid = -1; if (shop_radioBtn.isSelected()) paid = 1; else if
		 * (owner_radioBtn.isSelected()) paid = 0; String contractorName = (String)
		 * response.get("name"); Map<String, Object> map = new HashMap<String,
		 * Object>();
		 * 
		 * map.put("name", contractorName); map.put("typeId", contractorTypeId);
		 * map.put("ownerId", ownerId); try { Contractor contractor = (Contractor)
		 * this.getBaseService().findBean(Contractor.class, map);
		 * LoadLaboursNames(ownerId);
		 * 
		 * this.loadContractorTransactions(contractor.getId(), paid, ownerId); //
		 * setContractorSeleted(contractor.getId());
		 * this.name_TF.setText(contractor.getName());
		 * loanerPredicatableTable.getTable().requestFocus();
		 * loanerPredicatableTable.getTable().getSelectionModel().select(0);
		 * loanerPredicatableTable.getTable().getFocusModel().focus(0); } catch
		 * (DataBaseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (EmptyResultSetException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * 
		 * alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.save")); }
		 * 
		 * });
		 * 
		 * popupwindow.showAndWait();
		 * 
		 */}

	private void fitToAnchorePane(Node node) {

		AnchorPane.setTopAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setBottomAnchor(node, 0.0);

	}

	private void loadLoans(int accountId) {
		loansCustomeTable.getTable().getItems().clear();

		List<LoanVB<ShopLoan>> loansTableData=new ArrayList<LoanVB<ShopLoan>>();
		
		try {
			List Loans=this.getShopLoanService().getLoans(accountId);
			
			for (Iterator iterator = Loans.iterator(); iterator.hasNext();) {
				ShopLoan loan = (ShopLoan) iterator.next();
				LoanVB<ShopLoan> viewBean=new LoanVB<ShopLoan>();
				viewBean.setAmount(loan.getAmount());
				viewBean.setDate(loan.getLoanDate());
				viewBean.setNote(loan.getNotes());
				
				loansTableData.add(viewBean);
			}
			
			loansCustomeTable.loadTableData(loansTableData);

			
		} catch (DataBaseException e) {
			alert(AlertType.ERROR, this.getMessage("msg.err"), this.getMessage("msg.err"),
					this.getMessage("msg.err.general"));
		} catch (EmptyResultSetException e) {
			alert(AlertType.WARNING, "", "", this.getMessage("msg.warning.noData"));
		}

		
		
		
		
		
		
	}
	private void loadLoansInstallments(int accountId) { 
		
		

		this.LoanInstallmentsCustomeTable.getTable().getItems().clear();

		List<LoanVB<LoanPaying>> loansInstallmentTableData=new ArrayList<LoanVB<LoanPaying>>();
		
		try {
			List Loans=this.getShopLoanService().getLoanerInst(accountId);
			
			for (Iterator iterator = Loans.iterator(); iterator.hasNext();) {
				LoanPaying loanPay = (LoanPaying) iterator.next();
				LoanVB<LoanPaying> viewBean=new LoanVB<LoanPaying>();
				viewBean.setAmount(loanPay.getPaidAmunt());
				viewBean.setDate(loanPay.getPayingDate());
				viewBean.setNote(loanPay.getNotes());
				
				loansInstallmentTableData.add(viewBean);
			}
			
			LoanInstallmentsCustomeTable.loadTableData(loansInstallmentTableData);

			
		} catch (DataBaseException e) {
			alert(AlertType.ERROR, this.getMessage("msg.err"), this.getMessage("msg.err"),
					this.getMessage("msg.err.general"));
		} catch (EmptyResultSetException e) {
			alert(AlertType.WARNING, "", "", this.getMessage("msg.warning.noData"));
		}
	
	}

	private void alert(AlertType alertType, String title, String headerText, String message) {
		Alert a = new Alert(alertType);
		a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		a.setTitle(title);
		a.setHeaderText(headerText);
		a.setContentText(message);
		a.show();

	}

	private void setContractorSeleted(int id) {
		JFXTreeTableView<LoanerVB<LoanAccount>> table = loanerPredicatableTable.getTable();
		ObservableList<TreeItem<LoanerVB<LoanAccount>>> data = table.getRoot().getChildren();
		int index = 0;
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			TreeItem<LoanerVB<LoanAccount>> treeItem = (TreeItem<LoanerVB<LoanAccount>>) iterator.next();
			if (treeItem.getValue().getId() == id) {
				// table.getSelectionModel().select(index);
				table.requestFocus();
				table.getSelectionModel().select(index);
				table.getFocusModel().focus(index);
			}
			index += 1;
		}

	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	class LoanerTableActionListner implements CustomTableActions {
		JFXTreeTableView<LoanerVB<LoanAccount>> mytable;

		@Override
		public void rowSelected() {
		}

		@SuppressWarnings("unchecked")
		@Override
		public void rowSelected(Object table) {

			loansCustomeTable.getTable().getItems().clear();

			mytable = (JFXTreeTableView<LoanerVB<LoanAccount>>) table;

			LoanerVB<LoanAccount> LoanAccount = mytable.getSelectionModel().getSelectedItem().getValue();

		 
			loadLoans(LoanAccount.getEntity().getId());
			loadLoansInstallments(LoanAccount.getEntity().getId());
		}

	}
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	@Override
	public void rowSelected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rowSelected(Object o) {
		// TODO Auto-generated method stub

	}

}
