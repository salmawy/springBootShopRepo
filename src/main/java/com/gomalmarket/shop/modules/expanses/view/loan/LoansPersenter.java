package com.gomalmarket.shop.modules.expanses.view.loan;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.glyphfont.FontAwesome;

import com.gomalmarket.shop.core.Enum.LoanTransactionTypeEnum;
import com.gomalmarket.shop.core.Enum.LoanTypeEnum;
import com.gomalmarket.shop.core.Enum.NavigationResponseCodeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTableActions;
import com.gomalmarket.shop.core.UIComponents.customTable.PredicatableTable;
import com.gomalmarket.shop.core.action.navigation.Request;
import com.gomalmarket.shop.core.entities.shopLoan.LoanAccount;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.expanses.action.ExpansesAction;
import com.gomalmarket.shop.modules.expanses.view.addEditLoan.AddEditLoanPersenter;
import com.gomalmarket.shop.modules.expanses.view.addEditLoan.AddEditLoanView;
import com.gomalmarket.shop.modules.expanses.view.beans.LoanTransaction;
import com.gomalmarket.shop.modules.expanses.view.beans.LoanersNameVB;
import com.gomalmarket.shop.modules.expanses.view.payLoan.PayLoanPersenter;
import com.gomalmarket.shop.modules.expanses.view.payLoan.PayLoanView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoansPersenter extends ExpansesAction implements Initializable {

	@FXML
	private Label loanType_label;

	@FXML
	private AnchorPane debtTable_loc;

	@FXML
	private AnchorPane loanersTable_loc;

	@FXML
	private Label totalDebtValue_label;

	@FXML
	private JFXComboBox<ComboBoxItem<String>> loanType_combo;

	@FXML
	private Pane coloredPane;

	@FXML
	private AnchorPane installmentsTable;

	@FXML
	private Label totalDebt__label;

	@FXML
	private JFXTextField name_TF;

	private CustomTable<LoanTransaction> PayeesCustomTable;
	private CustomTable<LoanTransaction> debitsCustomtable;
	private PredicatableTable<LoanersNameVB> loanersCustomTable;

	private JFXButton addDebitBtn;
	private JFXButton editDebitBtn;
	private JFXButton deleteDebitBtn;

	private JFXButton addPayeeBtn;
	private JFXButton editPayeeBtn;
	private JFXButton deletePayeeBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		log.info(
				"============================================================================================================");

		init();
	}

	private void init() {

//==================================================================================================================	

		List loanersNameColumns = prepareLoanersNamesColumns();
		List payeesColumns = preparePayeesColumns();
		List loanerNamesHeaderNodes = prepareLoanersNamesHeaderNodes();
		List debitsHeaderNodes = prepareDebitsHeaderNodes();
		List payeesHeaderNodes = preparePayeesHeaderNodes();

		PayeesCustomTable = new CustomTable<LoanTransaction>(payeesColumns, payeesHeaderNodes, null, null,
				getPayeesTablesAction(), CustomTable.headTableCard, LoanTransaction.class);

		loanersCustomTable = new PredicatableTable<LoanersNameVB>(loanersNameColumns, loanerNamesHeaderNodes, null,
				getLoanersTablesAction(), PredicatableTable.headTableCard, LoanersNameVB.class);

		debitsCustomtable = new CustomTable<LoanTransaction>(payeesColumns, debitsHeaderNodes, null, null,
				getDebitsTableAction(), CustomTable.headTableCard, LoanTransaction.class);

		loanersCustomTable.getCutomTableComponent().setPrefSize(300, 300);
		debitsCustomtable.getCutomTableComponent().setPrefHeight(200);
		PayeesCustomTable.getCutomTableComponent().setPrefHeight(200);

		fitToAnchorePane(PayeesCustomTable.getCutomTableComponent());
		fitToAnchorePane(loanersCustomTable.getCutomTableComponent());
		fitToAnchorePane(debitsCustomtable.getCutomTableComponent());

		debtTable_loc.getChildren().addAll(debitsCustomtable.getCutomTableComponent());
		loanersTable_loc.getChildren().addAll(loanersCustomTable.getCutomTableComponent());
		installmentsTable.getChildren().addAll(PayeesCustomTable.getCutomTableComponent());
//==================================================================================================================	

		loanType_combo.getItems()
				.add(new ComboBoxItem<String>(LoanTypeEnum.IN_LOAN.getId(), this.getMessage("label.inLoan")));
		loanType_combo.getItems()
				.add(new ComboBoxItem<String>(LoanTypeEnum.OUT_LOAN.getId(), this.getMessage("label.outLoan")));
		loanType_combo.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, final Number oldvalue, final Number newvalue) {

				ComboBoxItem<String> item = loanType_combo.getSelectionModel().getSelectedItem();
				if (item.getId().equals(LoanTypeEnum.IN_LOAN.getId())) {

					initiateInLoanPage();
					coloredPane.setStyle("-fx-background-color: #00A65A");

				} else if (item.equals(LoanTypeEnum.OUT_LOAN.getId())) {

					initiateOutLoanPage();
					coloredPane.setStyle("-fx-background-color: #DD4B39");

				}

			}
		});

		// ==================================================================================================================

		name_TF.setPromptText(this.getMessage("label.name"));
		JFXTreeTableView<LoanersNameVB> table = loanersCustomTable.getTable();

		name_TF.textProperty().addListener((o, oldVal, newVal) -> {
			table.setPredicate(personProp -> {
				final LoanersNameVB loaner = personProp.getValue();
				return loaner.getName().get().contains(newVal);
			});
		});

//==================================================================================================================	
		totalDebt__label.setText(this.getMessage("label.total.debt"));
//======================totalDebt__label============================================================================================	

	}

	private void loadPayees(int loanerId, LoanTypeEnum type) throws EmptyResultSetException, DataBaseException {

		List<LoanTransaction> transactions = null;

		// detect type of loan
		if (type.equals(LoanTypeEnum.IN_LOAN)) {
			transactions = getExpansesServices().getLoanTransactions(loanerId,0, LoanTransactionTypeEnum.PAY_DEBIT);

		} else if (type.equals(LoanTypeEnum.OUT_LOAN)) {

			transactions = getExpansesServices().getLoanTransactions(loanerId,0, LoanTransactionTypeEnum.PAY_CREDIT);

		}

		PayeesCustomTable.getTable().getItems().clear();
		this.PayeesCustomTable.loadTableData(transactions);

	}

	private void loadDebts(int loanerId, LoanTypeEnum type) throws EmptyResultSetException, DataBaseException {

		List<LoanTransaction> transactions = null;

		// detect type of loan
		if (type.equals(LoanTypeEnum.IN_LOAN)) {
			transactions = getExpansesServices().getLoanTransactions(loanerId,0, LoanTransactionTypeEnum.LOAN_DEBET);

		} else if (type.equals(LoanTypeEnum.OUT_LOAN)) {

			transactions = getExpansesServices().getLoanTransactions(loanerId,0, LoanTransactionTypeEnum.LOAN_CREDIT);

		}

		debitsCustomtable.getTable().getItems().clear();
		this.debitsCustomtable.loadTableData(transactions);

	}

	private void loadLoanerNames(LoanTypeEnum type) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("type", type.getId());

		try {
			List data = (List) this.getBaseService().findAllBeans(LoanAccount.class, map, null);
			List tableData = new ArrayList();

			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				LoanAccount account = (LoanAccount) iterator.next();
				LoanersNameVB row = new LoanersNameVB();
				row.setLoanerId(account.getLoaner().getId());
				row.setAmount(account.getAmount());
				row.setName(account.getLoaner().getName());
				row.setId(account.getLoaner().getId());
				tableData.add(row);
			}

			this.loanersCustomTable.loadTableData(tableData);

		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated
			e.printStackTrace();
		}

	}

	private void fitToAnchorePane(Node node) {

		AnchorPane.setTopAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setBottomAnchor(node, 0.0);

	}

	void initiateInLoanPage() {
		Map<String, Object> map = new HashMap<String, Object>();

		map = new HashMap<String, Object>();
		loadLoanerNames(LoanTypeEnum.IN_LOAN);
		map.put("type=", "'" + LoanTypeEnum.IN_LOAN.getId() + "'");

		Double amount = 0.0;
		try {
			amount = (Double) this.getBaseService().aggregate("LoanAccount", "sum", "amount", map);
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.totalDebtValue_label.setText(String.valueOf(amount));

	}

	private List prepareLoanersNamesHeaderNodes() {
		// button.purchases.confirm button.save

		JFXButton addBtn = new JFXButton(this.getMessage("button.add"));
		addBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));
		addBtn.getStyleClass().setAll("btn", "btn-primary"); // (2)
		addBtn.setOnAction(e -> {

			addNewLoanerBtn();

		});

		// editBtn.getStyleClass().add("control-button");
		List buttons = new ArrayList<JFXButton>(Arrays.asList(addBtn));

		return buttons;

	}

	private List preparePayeesHeaderNodes() {
		// button.purchases.confirm button.save

		addPayeeBtn = new JFXButton(this.getMessage("button.add"));
		addPayeeBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));
		addPayeeBtn.getStyleClass().setAll("btn", "btn-primary"); // (2)
		addPayeeBtn.setDisable(true);
		addPayeeBtn.setOnAction(e -> {

			JFXTreeTableView<LoanersNameVB> mytable = (JFXTreeTableView<LoanersNameVB>) this.loanersCustomTable
					.getTable();
			LoanersNameVB loaner = mytable.getSelectionModel().getSelectedItem().getValue();
			payLoan(null, loaner.getLoanerId(), Request.MODE_ADD);

		});

		editPayeeBtn = new JFXButton(this.getMessage("button.edit"));
		editPayeeBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.EDIT));
		editPayeeBtn.setDisable(true);
		editPayeeBtn.getStyleClass().setAll("btn", "btn-primary", "btn-sm");
		editPayeeBtn.setOnAction(e -> {
			LoanTransaction loanTransaction = (LoanTransaction) this.PayeesCustomTable.getTable().getSelectionModel()
					.getSelectedItem();

			payLoan(loanTransaction, loanTransaction.getLoanerId(), Request.MODE_EDIT);

		});

		deletePayeeBtn = new JFXButton(this.getMessage("button.delete"));
		deletePayeeBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.REMOVE));
		deletePayeeBtn.setDisable(true);
		deletePayeeBtn.getStyleClass().setAll("btn", "btn-danger", "btn-sm");
		deletePayeeBtn.setOnAction(e -> {
			LoanTransaction loanTransaction = (LoanTransaction) this.PayeesCustomTable.getTable().getSelectionModel()
					.getSelectedItem();

			// payLoan(loanTransaction);

		});

		List buttons = new ArrayList<JFXButton>(Arrays.asList(addPayeeBtn, editPayeeBtn, deletePayeeBtn));

		return buttons;

	}

	private List prepareDebitsHeaderNodes() {
		// button.purchases.confirm button.save

		addDebitBtn = new JFXButton(this.getMessage("button.add"));
		addDebitBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));
		addDebitBtn.getStyleClass().setAll("btn", "btn-primary"); // (2)
		addDebitBtn.setDisable(true);
		addDebitBtn.setOnAction(e -> {
			try {
				JFXTreeTableView<LoanersNameVB> mytable = (JFXTreeTableView<LoanersNameVB>) this.loanersCustomTable
						.getTable();
				LoanersNameVB loaner = mytable.getSelectionModel().getSelectedItem().getValue();
				addEditLoan(null, loaner.getLoanerId(), Request.MODE_ADD);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		editDebitBtn = new JFXButton(this.getMessage("button.edit"));
		editDebitBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.EDIT));
		editDebitBtn.setDisable(true);
		editDebitBtn.getStyleClass().setAll("btn", "btn-primary", "btn-sm");
		editDebitBtn.setOnAction(e -> {
			LoanTransaction loanTransaction = (LoanTransaction) this.debitsCustomtable.getTable().getSelectionModel()
					.getSelectedItem();

			addEditLoan(loanTransaction, loanTransaction.getLoanerId(), Request.MODE_EDIT);

		});

		deleteDebitBtn = new JFXButton(this.getMessage("button.delete"));
		deleteDebitBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.REMOVE));
		deleteDebitBtn.setDisable(true);
		deleteDebitBtn.getStyleClass().setAll("btn", "btn-danger", "btn-sm");
		deleteDebitBtn.setOnAction(e -> {
			LoanTransaction loanTransaction = (LoanTransaction) this.debitsCustomtable.getTable().getSelectionModel()
					.getSelectedItem();

			deleteLoanTransaction(loanTransaction);

		});

		// editBtn.getStyleClass().add("control-button");
		List buttons = new ArrayList<JFXButton>(Arrays.asList(addDebitBtn, editDebitBtn, deleteDebitBtn));

		return buttons;
	}

	private void deleteLoanTransaction(LoanTransaction loanTransaction) {
		// TODO Auto-generated method stub

	}

	private void addEditLoan(LoanTransaction loanTransaction, int loanerId, int mode) {

		this.requestObj = prepareAddEditLoanRequest(loanerId, mode, loanTransaction);

		AddEditLoanView form = new AddEditLoanView();
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

			System.out.println("window closes");
			if (responseObj.getResponseCode() == NavigationResponseCodeEnum.SUCCESS) {

				try {
					refreshDebits(mode, loanerId);
				} catch (EmptyResultSetException | DataBaseException e) {
					// TODO Auto-generated catch block
					alert(AlertType.ERROR, "", "", getMessage("msg.err.cannot.load.data"));
				}

			}

		});

		popupwindow.showAndWait();

	}

	private Request prepareAddEditLoanRequest(int loanerId, int mode, LoanTransaction editedTrx) {
		LoanTypeEnum loanType = LoanTypeEnum.fromId(this.loanType_combo.getSelectionModel().getSelectedItem().getId());
		Map<String, Object> map = new HashMap();
		map.put("loanType", loanType);

		return new Request() {

			@Override
			public int getMode() {
				// TODO Auto-generated method stub
				return mode;
			}

			@Override
			public Object getEditedObject() {
				// TODO Auto-generated method stub
				return editedTrx;
			}

			@Override
			public int getEditedObjectId() {
				// TODO Auto-generated method stub
				return loanerId;
			}

			@Override
			public Map getMap() {
				// TODO Auto-generated method stub
				return map;
			}
		};
	}

	private Request prepareAddEditPayee(int loanerId, LoanTransaction editedTrx, int mode) {

		LoanTypeEnum loanType = LoanTypeEnum.fromId(this.loanType_combo.getSelectionModel().getSelectedItem().getId());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanType", loanType);

		return new Request() {

			@Override
			public int getMode() {
				// TODO Auto-generated method stub
				return mode;
			}

			@Override
			public Object getEditedObject() {
				// TODO Auto-generated method stub
				return editedTrx;
			}

			@Override
			public int getEditedObjectId() {
				// TODO Auto-generated method stub
				return loanerId;
			}

			@Override
			public Map getMap() {
				// TODO Auto-generated method stub
				return map;
			}
		};
	}

	private void refreshDebits(int mode, int loanerId) throws EmptyResultSetException, DataBaseException {
		LoanTypeEnum loanType = LoanTypeEnum.fromId(this.loanType_combo.getSelectionModel().getSelectedItem().getId());

		switch (mode) {
		case Request.MODE_CREATE_NEW:
			loadLoanerNames(loanType);
			break;
		case Request.MODE_ADD:
			loadDebts(loanerId, loanType);

			break;
		case Request.MODE_EDIT:
			loadDebts(loanerId, loanType);
			break;
		default:
			break;
		}

	}

	private void refreshPayees(int mode, int loanerId) throws EmptyResultSetException, DataBaseException {
		LoanTypeEnum loanType = LoanTypeEnum.fromId(this.loanType_combo.getSelectionModel().getSelectedItem().getId());

		JFXTreeTableView<LoanersNameVB> mytable = (JFXTreeTableView<LoanersNameVB>) this.loanersCustomTable.getTable();
		LoanersNameVB loaner = getLoaner(loanerId, loanType);
		if (loaner != null)
			mytable.getSelectionModel().getSelectedItem().setValue(loaner);
		loadPayees(loanerId, loanType);
		/*
		 * switch (mode) {
		 * 
		 * case Request.MODE_ADD: loadPayees(loanerId, loanType);
		 * 
		 * break; case Request.MODE_EDIT: loadPayees(loanerId, loanType);
		 * 
		 * break; default: break;}
		 */
	

	}

	private void payLoan(LoanTransaction loanTransaction, int loanerId, int mode) {

		this.requestObj = prepareAddEditPayee(loanerId, loanTransaction, mode);
		
		
		PayLoanView form = new PayLoanView();
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

			System.out.println("window closes");
			if (responseObj.getResponseCode() == NavigationResponseCodeEnum.SUCCESS) {

				try {
					refreshPayees(mode, loanerId);
				} catch (EmptyResultSetException | DataBaseException e) {
					// TODO Auto-generated catch block
					alert(AlertType.ERROR, "", "", getMessage("msg.err.cannot.load.data"));
				}

			}

		});

		popupwindow.showAndWait();

	}

	private List<Column> preparePayeesColumns() {

		List<Column> columns = new ArrayList<Column>();

		Column c = new Column(this.getMessage("label.money.amount"), "amount", "double", 25, true);
		columns.add(c);

		c = new Column(this.getMessage("label.date"), "transactionDate", "date", 30, true);
		columns.add(c);

		c = new Column(this.getMessage("label.notes"), "notes", "String", 45, true);
		columns.add(c);

		return columns;

	}

	private List<Column> prepareLoanersNamesColumns() {

		List<Column> columns = new ArrayList<Column>();

		Column c = new Column(this.getMessage("label.name"), "name", "string", 60, true);
		columns.add(c);

		c = new Column(this.getMessage("label.money.amount"), "amount", "double", 40, true);
		columns.add(c);

		return columns;

	}

	void initiateOutLoanPage() {

		Map<String, Object> map = new HashMap<String, Object>();

		map = new HashMap<String, Object>();
		loadLoanerNames(LoanTypeEnum.OUT_LOAN);
		map.put("type=", "'" + LoanTypeEnum.OUT_LOAN.getId() + "'");

		Double amount = 0.0;
		try {
			amount = (Double) this.getBaseService().aggregate("LoanAccount", "sum", "amount", map);
		} catch (DataBaseException | EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.totalDebtValue_label.setText(String.valueOf(amount));

	}

	private CustomTableActions getDebitsTableAction() {

		CustomTableActions d = new CustomTableActions() {

			@Override
			public void rowSelected(Object o) {
				// TODO Auto-generated method stub

			}

			@Override
			public void rowSelected() {
				editDebitBtn.setDisable(false);
				deleteDebitBtn.setDisable(false);

			}
		};

		return d;

	}

	private CustomTableActions getPayeesTablesAction() {

		CustomTableActions d = new CustomTableActions() {

			@Override
			public void rowSelected(Object o) {
				// TODO Auto-generated method stub

			}

			@Override
			public void rowSelected() {
				editPayeeBtn.setDisable(false);
				deletePayeeBtn.setDisable(false);

			}
		};

		return d;

	}

	private CustomTableActions getLoanersTablesAction() {

		CustomTableActions d = new CustomTableActions() {

			@Override
			public void rowSelected(Object table) {

				debitsCustomtable.getTable().getItems().clear();
				PayeesCustomTable.getTable().getItems().clear();

				editPayeeBtn.setDisable(true);
				deletePayeeBtn.setDisable(true);
				editDebitBtn.setDisable(true);
				deleteDebitBtn.setDisable(true);

				addDebitBtn.setDisable(false);
				addPayeeBtn.setDisable(false);
				LoanersNameVB loaner = null;
				try {
					JFXTreeTableView<LoanersNameVB> mytable = (JFXTreeTableView<LoanersNameVB>) table;
					loaner = mytable.getSelectionModel().getSelectedItem().getValue();
				} catch (Exception e2) {
					return;
				}
				LoanTypeEnum type = (loanType_combo.getSelectionModel().getSelectedItem().getId()
						.equals(LoanTypeEnum.IN_LOAN.getId())) ? LoanTypeEnum.IN_LOAN : LoanTypeEnum.OUT_LOAN;
				try {
					loadDebts(loaner.getId(), type);
				} catch (EmptyResultSetException e1) {
					// TODO Auto-generated catch block
					log.info("no Debits found");
				} catch (DataBaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					loadPayees(loaner.getId(), type);
				} catch (EmptyResultSetException e) {
					// TODO Auto-generated catch block
					log.info("no Payees found");

				} catch (DataBaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void rowSelected() {
				// TODO Auto-generated method stub

			}
		};

		return d;

	}

	private void addNewLoanerBtn() {

		try {
			LoanTypeEnum.fromId(this.loanType_combo.getSelectionModel().getSelectedItem().getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return;
		}

		addEditLoan(null, 0, Request.MODE_CREATE_NEW);

	}
	// ---------------------------------------------------------------------------------------------------------------

	private void alert(AlertType alertType, String title, String headerText, String message) {
		Alert a = new Alert(alertType);
		a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		a.setTitle(title);
		a.setHeaderText(headerText);
		a.setContentText(message);
		a.show();

	}

	// ---------------------------------------------------------------------------------------------------------------
	private LoanersNameVB getLoaner(int loanerId, LoanTypeEnum type) {

		LoanersNameVB nameVB = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type.getId());
		params.put("id", loanerId);
		LoanAccount loanerAccount;
		try {
			loanerAccount = (LoanAccount) this.getBaseService().findBean(LoanAccount.class, params);
			nameVB=new LoanersNameVB();
			nameVB.setAmount(loanerAccount.getAmount());
			nameVB.setLoanerId(loanerAccount.getLoaner().getId());
			nameVB.setAmount(loanerAccount.getAmount());
			nameVB.setName(loanerAccount.getLoaner().getName());
			nameVB.setId(loanerAccount.getLoaner().getId());
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nameVB;
	}
}
