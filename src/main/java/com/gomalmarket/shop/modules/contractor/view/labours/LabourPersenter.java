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

import org.controlsfx.glyphfont.FontAwesome;

import com.gomalmarket.shop.core.Enum.ContractorOwnerEnum;
import com.gomalmarket.shop.core.Enum.ContractorTypeEnum;
import com.gomalmarket.shop.core.Enum.NavigationResponseCodeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTableActions;
import com.gomalmarket.shop.core.UIComponents.customTable.PredicatableTable;
import com.gomalmarket.shop.core.action.navigation.Request;
import com.gomalmarket.shop.core.action.navigation.Response;
import com.gomalmarket.shop.core.entities.contractor.Contractor;
import com.gomalmarket.shop.core.entities.contractor.ContractorAccount;
import com.gomalmarket.shop.core.entities.contractor.ContractorTransaction;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.modules.contractor.action.ContractorAction;
import com.gomalmarket.shop.modules.contractor.view.addLabour.AddLabourView;
import com.gomalmarket.shop.modules.contractor.view.beans.ContractorTransactionVB;
import com.gomalmarket.shop.modules.contractor.view.beans.ContractorVB;
import com.gomalmarket.shop.modules.expanses.view.beans.LoanersNameVB;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LabourPersenter extends ContractorAction implements Initializable {

	@FXML
	private Label ownerAmount_label;

	@FXML
	private TextField ownerAmount_TF;

	@FXML
	private JFXTextField name_TF;

	@FXML
	private Label owner_title;

	@FXML
	private Label shopAmount_title;

	@FXML
	private Pane shop_coloredPane;

	@FXML
	private AnchorPane contractorTable_loc;

	@FXML
	private Label shop_title;

	@FXML
	private AnchorPane transactions_table;

	@FXML
	private Pane owner_coloredPane;

	@FXML
	private TextField shopAmount_TF;

	@FXML
	private ToggleGroup g1;

	@FXML
	private Label ownerAmount_title;

	@FXML
	private JFXRadioButton owner_radioBtn;

	@FXML
	private JFXRadioButton all_radioBtn;

	@FXML
	private Label shopAmount_label;

	@FXML
	private JFXRadioButton shop_radioBtn;

	@FXML
	private JFXComboBox<ComboBoxItem<Integer>> owner_combo;
	private PredicatableTable<ContractorVB> contractorPredicatableTable;

	private CustomTable<ContractorTransactionVB> transactionsCustomeTable;
	private final int contractorTypeId = ContractorTypeEnum.LABOUR;

	private JFXButton deleteTransactionBtn;

	private JFXButton editTransactionBtn;

	private JFXButton addTransactionBtn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		log.info(
				"============================================================================================================");
		init();
	}

	private void init() {

		// =========================================================================================================================================

		List contractorColumns = prepareContractorColumns();
		List transactionsColumns = prepareTransactionsColumns();
		List contratorHeadNodes = prepareContractorHeaderNodes();
		List transactionsHeadNodes = prepareTransactionTableHeader();
		contractorPredicatableTable = new PredicatableTable<ContractorVB>(contractorColumns, contratorHeadNodes, null,
				new ContractorableActionListner(), CustomTable.headTableCard, ContractorVB.class);
	
		
		transactionsCustomeTable = new CustomTable<ContractorTransactionVB>(transactionsColumns, transactionsHeadNodes, null, null, getTransactionsTablesAction(),
				CustomTable.headTableCard, ContractorTransactionVB.class);

		// =========================================================================================================================================
		fitToAnchorePane(contractorPredicatableTable.getCutomTableComponent());
		fitToAnchorePane(transactionsCustomeTable.getCutomTableComponent());

		contractorPredicatableTable.getCutomTableComponent().setPrefSize(150, 500);
		contractorTable_loc.getChildren().addAll(contractorPredicatableTable.getCutomTableComponent());
		transactions_table.getChildren().addAll(transactionsCustomeTable.getCutomTableComponent());

		// =========================================================================================================================================
		JFXTreeTableView<ContractorVB> table = contractorPredicatableTable.getTable();
		name_TF.setPromptText(getMessage("label.name"));
		name_TF.textProperty().addListener((o, oldVal, newVal) -> {
			table.setPredicate(personProp -> {
				final ContractorVB contractor = personProp.getValue();
				return contractor.getName().get().contains(newVal);
			});
		});

//=========================================================================================================================================
		owner_title.setText(this.getMessage("label.owner.name.kareem"));
		ownerAmount_label.setText(this.getMessage("label.owner.name.kareem"));
		shop_title.setText(this.getMessage("label.shop"));
		shopAmount_label.setText(this.getMessage("label.shop"));

		owner_radioBtn.setText(this.getMessage("label.owner.name.kareem"));
		shop_radioBtn.setText(this.getMessage("label.shop"));
		all_radioBtn.setText(this.getMessage("label.all"));
		all_radioBtn.setSelected(true);

//=========================================================================================================================================

		owner_combo.setPromptText(this.getMessage("label.owner"));
		owner_combo.getStyleClass().add("comboBox");
		for (Iterator iterator = this.owners.iterator(); iterator.hasNext();) {
			ComboBoxItem<Integer> object = (ComboBoxItem<Integer>) iterator.next();
			owner_combo.getItems().add(object);
		}
		owner_combo.getSelectionModel().selectFirst();
		LoadLaboursNames(((ComboBoxItem<Integer>) owners.get(0)).getId());
		owner_combo.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, final Number oldvalue, final Number newvalue) {
				ComboBoxItem<Integer> item = owner_combo.getSelectionModel().getSelectedItem();
				LoadLaboursNames(item.getId());
				ownerAmount_label.setText(getMessage(ContractorOwnerEnum.fromId(item.getId()).getLabel()));
				owner_title.setText(getMessage(ContractorOwnerEnum.fromId(item.getId()).getLabel()));
				owner_radioBtn.setText(getMessage(ContractorOwnerEnum.fromId(item.getId()).getLabel()));
				calculateTotalOwnerAmount();
				calculateTotalShopAmount();
			}
		});

//=========================================================================================================================================
		calculateTotalShopAmount();
		calculateTotalOwnerAmount();
		// =========================================================================================================================================

		g1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (g1.getSelectedToggle() != null) {

					JFXRadioButton r = (JFXRadioButton) g1.getSelectedToggle();
					int paid = -1;
					if (owner_radioBtn.getId() == r.getId()) {
						paid = 0;
					} else if (shop_radioBtn.getId() == r.getId()) {
						paid = 1;
					}

					JFXTreeTableView<ContractorVB> table = contractorPredicatableTable.getTable();

					try {
						ContractorVB contractor = (ContractorVB) table.getSelectionModel().getSelectedItem().getValue();
						int ownerId = owner_combo.getSelectionModel().getSelectedItem().getId();
						loadContractorTransactions(contractor.getContractorId(), paid, ownerId);
					} catch (NullPointerException e) {
					}

				}
			}
		});
	}

	private void clearAccouantDetailData() {
		this.transactionsCustomeTable.getTable().getItems().clear();
		this.shopAmount_TF.setText(" ");
		this.ownerAmount_TF.setText(" ");
		this.all_radioBtn.setSelected(true);
		addTransactionBtn.setDisable(true);
		editTransactionBtn.setDisable(true);
		deleteTransactionBtn.setDisable(true);
		
	}

	private void clearContractorsTable() {

		try {
			contractorPredicatableTable.getTable().getRoot().getChildren().clear();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	private void LoadLaboursNames(int ownerId) {
		clearContractorsTable();

		clearAccouantDetailData();

		try {
			List contractors = this.getContractorService().getAllContractorsAccounts(this.contractorTypeId, ownerId,
					this.getAppContext().getSeason().getId());
			List tableData = new LinkedList();

			for (Iterator iterator = contractors.iterator(); iterator.hasNext();) {

				ContractorAccount acc = (ContractorAccount) iterator.next();

				ContractorVB viewBean = new ContractorVB();
				viewBean.setAccountId(acc.getId());
				viewBean.setContractorId(acc.getContractor().getId());
				viewBean.setName((acc.getContractorName()));
				viewBean.setAmount(acc.getAmount());
				tableData.add(viewBean);

			}

			this.contractorPredicatableTable.loadTableData(tableData);
			
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			alert(AlertType.ERROR, "", "", getMessage("msg.err.general"));
			log.info("no contractor found ");
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			alert(AlertType.WARNING, "", "", getMessage("msg.warning.noData"));
			log.info("no contractor found ");

		}
	}

	private List<Column> prepareContractorColumns() {

		List<Column> columns = new ArrayList<Column>();

		Column c = new Column(this.getMessage("label.name"), "name", "date", 70, true);
		columns.add(c);

		c = new Column(this.getMessage("label.money.amount"), "amount", "date", 30, true);
		columns.add(c);

		return columns;

	}

	private List<Column> prepareTransactionsColumns() {

		List<Column> columns = new ArrayList<Column>();

		Column c = new Column(this.getMessage("label.date"), "date", "date", 30, true);
		columns.add(c);

		c = new Column(this.getMessage("label.money.amount"), "amount", "double", 20, true);
		columns.add(c);

		c = new Column(this.getMessage("label.contractor.status.paid"), "paid", "String", 10, true);
		columns.add(c);

		c = new Column(this.getMessage("label.notes"), "notes", "String", 40, true);
		columns.add(c);

		return columns;

	}

	private List prepareContractorHeaderNodes() {
		// button.purchases.confirm button.save

		JFXButton addBtn = new JFXButton(this.getMessage("button.add"));
		addBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));

		addBtn.getStyleClass().setAll("btn", "btn-primary"); // (2)
		addBtn.setOnAction(e -> {

			addEditTransaction(Request.MODE_CREATE_NEW,0,0,null);

		});

		List buttons = new ArrayList<JFXButton>(Arrays.asList(addBtn));

		return buttons;

	}


	
	
	private Request prepareRequest(int mode,int contractorId,int trxId,ContractorTransactionVB trx) {
		return new Request() {
			
			@Override
			public int getMode() {
				// TODO Auto-generated method stub
				return mode;
			}
			
			@Override
			public Map getMap() {
				// TODO Auto-generated method stub
				int ownerId = owner_combo.getSelectionModel().getSelectedItem().getId();

				Map<String,Object> m=new HashMap<String,Object>();
				m.put("ownerId", ownerId);
				m.put("typeId", contractorTypeId);
				m.put("contractorId", contractorId);
				return m;
			}
			
			@Override
			public int getEditedObjectId() {
				// TODO Auto-generated method stub
				return trxId;
			}
			
			@Override
			public Object getEditedObject() {
				// TODO Auto-generated method stub
				return trx;
			}
		};
		
		
		
		
	}
	private void addEditTransaction(int mode,int contractorId,int trxId,ContractorTransactionVB trx) {
		
		
		this.request=prepareRequest(mode,contractorId, trxId, trx);
 		
		AddLabourView form = new AddLabourView();
		URL u = getClass().getClassLoader().getResource("appResources/custom.css");


		Scene scene1 = new Scene(form.getView(), 350, 420);
		Stage popupwindow = new Stage();
		popupwindow.setResizable(false);
		popupwindow.initStyle(StageStyle.TRANSPARENT);

		String css = u.toExternalForm();
		scene1.getStylesheets().addAll(css);
		popupwindow.initModality(Modality.APPLICATION_MODAL);

		popupwindow.setScene(scene1);
		popupwindow.setOnHiding(ev -> { 
			
			
			
			handelAddEditRespone(response);
		});

		popupwindow.showAndWait();

	}

	private void fitToAnchorePane(Node node) {

		AnchorPane.setTopAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setBottomAnchor(node, 0.0);

	}

	private void loadContractorTransactions(int id, int paid, int ownerId) {

		double ownerTotalAmount = 0.0;
		double shopTotalPaidAmount = 0.0;
		transactionsCustomeTable.getTable().getItems().clear();

		g1.getSelectedToggle();
		List tableData = new LinkedList();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractor.id", id);
		map.put("contractor.typeId", contractorTypeId);
		// map.put("season.id", getAppContext().getSeason().getId());
		map.put("contractor.ownerId", ownerId);

		if (paid > -1)
			map.put("paid", paid);

		List order = new LinkedList(Arrays.asList("transactionDate"));

		try {
			List transactions = this.getBaseService().findAllBeansWithDepthMapping(ContractorTransaction.class, map,
					order);
			for (Iterator iterator = transactions.iterator(); iterator.hasNext();) {

				ContractorTransaction transaction = (ContractorTransaction) iterator.next();
				ContractorTransactionVB viewBean = new ContractorTransactionVB();
				viewBean.setDate(ContractorTransactionVB.sdf.format(transaction.getTransactionDate()));
				viewBean.setId(transaction.getId());
				viewBean.setAmount(transaction.getAmount());
				viewBean.setNotes(transaction.getReport());

				viewBean.setPaid((transaction.getPaid() == 1)
						? getAppContext().getMessages().getString("label.contractor.status.paid.no")
						: getAppContext().getMessages().getString("label.contractor.status.paid.no"));
				if (transaction.getPaid() == 0)
					ownerTotalAmount += transaction.getAmount();
				else
					shopTotalPaidAmount += transaction.getAmount();

				tableData.add(viewBean);
			}

			transactionsCustomeTable.loadTableData(tableData);
		} catch (DataBaseException e) {
			alert(AlertType.ERROR, this.getMessage("msg.err"), this.getMessage("msg.err"),
					this.getMessage("msg.err.general"));
		} catch (EmptyResultSetException e) {
			alert(AlertType.WARNING, "", "", this.getMessage("msg.warning.noData"));
		}

		this.shopAmount_TF.setText(String.valueOf(shopTotalPaidAmount));
		this.ownerAmount_TF.setText(String.valueOf(ownerTotalAmount));

	}

	private void alert(AlertType alertType, String title, String headerText, String message) {
		Alert a = new Alert(alertType);
		a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		a.setTitle(title);
		a.setHeaderText(headerText);
		a.setContentText(message);
		a.show();

	}

	private void calculateTotalShopAmount() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractor.typeId =", contractorTypeId);
		// map.put("season.id =", getAppContext().getSeason().getId());
		map.put("paid=", 1);
		map.put("contractor.ownerId= ", owner_combo.getSelectionModel().getSelectedItem().getId());

		Double amount = 0.0;
		try {
			amount = (Double) this.getBaseService().aggregate("ContractorTransaction", "sum", "amount", map);
			amount=(amount==null)?0.0:amount;
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			amount = 0.0;
		}

		this.shopAmount_title.setText(String.valueOf(amount));

	}

	private void calculateTotalOwnerAmount() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractor.typeId=", contractorTypeId);
		map.put("season.id=", getAppContext().getSeason().getId());
		map.put("paid=", 0);
		map.put("contractor.ownerId=", owner_combo.getSelectionModel().getSelectedItem().getId());

		Double amount = 0.0;
		try {
			amount = (Double) this.getBaseService().aggregate("ContractorTransaction", "sum", "amount", map);
			amount=(amount==null)?0.0:amount;
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			amount = 0.0;
		}

		this.ownerAmount_title.setText(String.valueOf(amount));

	}

	private void setContractorSeleted(int id) {
		JFXTreeTableView<ContractorVB> table = contractorPredicatableTable.getTable();
		ObservableList<TreeItem<ContractorVB>> data = table.getRoot().getChildren();
		int index = 0;
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			TreeItem<ContractorVB> treeItem = (TreeItem<ContractorVB>) iterator.next();
			if (treeItem.getValue().getContractorId() == id) {
				table.requestFocus();
				table.getSelectionModel().select(index);
				table.getFocusModel().focus(index);
			}
			index += 1;
		}

	}

	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	class ContractorableActionListner implements CustomTableActions {
		JFXTreeTableView<ContractorVB> mytable;

		@Override
		public void rowSelected() {
		}

		@SuppressWarnings("unchecked")
		@Override
		public void rowSelected(Object table) {

			transactionsCustomeTable.getTable().getItems().clear();

			mytable = (JFXTreeTableView<ContractorVB>) table;

			ContractorVB contractor = mytable.getSelectionModel().getSelectedItem().getValue();

			int paid = -1;
			if (shop_radioBtn.isSelected())
				paid = 1;
			else if (owner_radioBtn.isSelected())
				paid = 0;
			int ownerId = owner_combo.getSelectionModel().getSelectedItem().getId();
			loadContractorTransactions(contractor.getContractorId(), paid, ownerId);
			addTransactionBtn.setDisable(false);
		}

	}
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	private List prepareTransactionTableHeader() {
		// button.purchases.confirm button.save

		addTransactionBtn = new JFXButton(this.getMessage("button.add"));
		addTransactionBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));
		addTransactionBtn.getStyleClass().setAll("btn", "btn-primary","btn-xs"); // (2)
		addTransactionBtn.setDisable(true);
		addTransactionBtn.setOnAction(e -> {
			JFXTreeTableView<ContractorVB> mytable = (JFXTreeTableView<ContractorVB>) this.contractorPredicatableTable.getTable();			
			ContractorVB contractor = mytable.getSelectionModel().getSelectedItem().getValue();
			addEditTransaction(Request.MODE_ADD, contractor.getContractorId(), 0, null);

		});

		editTransactionBtn = new JFXButton(this.getMessage("button.edit"));
		editTransactionBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.EDIT));
		editTransactionBtn.setDisable(true);
		editTransactionBtn.getStyleClass().setAll("btn", "btn-primary", "btn-xs");
		editTransactionBtn.setOnAction(e -> {
			JFXTreeTableView<ContractorVB> mytable = (JFXTreeTableView<ContractorVB>) this.contractorPredicatableTable.getTable();			
			ContractorVB contractor = mytable.getSelectionModel().getSelectedItem().getValue();
			ContractorTransactionVB trx = (ContractorTransactionVB) this.transactionsCustomeTable.getTable().getSelectionModel()
					.getSelectedItem();

			addEditTransaction(Request.MODE_EDIT, contractor.getContractorId(), trx.getId(), trx);
		});

		deleteTransactionBtn = new JFXButton(this.getMessage("button.delete"));
		deleteTransactionBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.REMOVE));
		deleteTransactionBtn.setDisable(true);
		deleteTransactionBtn.getStyleClass().setAll("btn", "btn-danger", "btn-xs");
		deleteTransactionBtn.setOnAction(e -> {
			ContractorTransactionVB trx = (ContractorTransactionVB) this.transactionsCustomeTable.getTable().getSelectionModel().getSelectedItem();

			deleteTransaction(trx);

		});

		List buttons = new ArrayList<JFXButton>(Arrays.asList(addTransactionBtn, editTransactionBtn, deleteTransactionBtn));

		return buttons;

	}

	
	
	private void resfreshContractorAccount(int contractorId) {
		
		
		ContractorVB contractorVB=new ContractorVB();
		  this.getContractorService().getcontractorAccount(contractorId, getAppContext().getSeason().getId());
		  
			JFXTreeTableView<ContractorVB> mytable = (JFXTreeTableView<ContractorVB>) this.contractorPredicatableTable.getTable();
 			mytable.getSelectionModel().getSelectedItem().setValue(contractorVB);

		
		
		
	}
	
	private void deleteTransaction(ContractorTransactionVB trx) {
		// TODO Auto-generated method stub
		
		try{
		getContractorService().deleteContractorTransaction(trx.getId());
		
		alert(AlertType.INFORMATION, "", "", getMessage("msg.done.delete"));
		JFXTreeTableView<ContractorVB> mytable = (JFXTreeTableView<ContractorVB>) this.contractorPredicatableTable.getTable();			
		ContractorVB contractor = mytable.getSelectionModel().getSelectedItem().getValue();
		
		
		
		
		refreshContractorDetails(contractor.getContractorId());
		}catch (Exception e) {
			// TODO: handle exception
			
			alert(AlertType.ERROR, "", "", getMessage("msg.err.general"));
		}
		
	}

	
	
	private void refreshContractorDetails(int contractorId) {
		int paid = -1;
		if (shop_radioBtn.isSelected())
			paid = 1;
		else if (owner_radioBtn.isSelected())
			paid = 0;
		
		
 		int ownerId=this.owner_combo.getSelectionModel().getSelectedItem().getId();

		try {
			Contractor contractor =  (Contractor) this.getBaseService().findBean(Contractor.class, contractorId);
			LoadLaboursNames(ownerId);
		//	resfreshContractorAccount(contractorId);
			this.loadContractorTransactions(contractor.getId(), paid, ownerId);
			setContractorSeleted(contractor.getId());
			
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
 	
	private void handelAddEditRespone(Response response) {


		System.out.println("window closes");
		if (response.getResponseCode() == NavigationResponseCodeEnum.SUCCESS) {		 
				
				int contractorId=(int) response.getResults().get("contractorId");
				refreshContractorDetails(contractorId); 		 

		}

	
	}
	
	
	private CustomTableActions getTransactionsTablesAction() {

		CustomTableActions d = new CustomTableActions() {

			@Override
			public void rowSelected(Object o) {
				// TODO Auto-generated method stub

			}

			@Override
			public void rowSelected() {
				editTransactionBtn.setDisable(false);
				deleteTransactionBtn.setDisable(false);

			}
		};

		return d;

	}
	
 
}
