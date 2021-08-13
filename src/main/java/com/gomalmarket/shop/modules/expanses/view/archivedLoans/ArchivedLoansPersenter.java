package com.gomalmarket.shop.modules.expanses.view.archivedLoans;

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
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.UIComponents.customTable.Column;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTable;
import com.gomalmarket.shop.core.UIComponents.customTable.CustomTableActions;
import com.gomalmarket.shop.core.UIComponents.customTable.PredicatableTable;
import com.gomalmarket.shop.core.entities.shopLoan.LoanAccount;
import com.gomalmarket.shop.core.entities.shopLoan.LoanGroup;
import com.gomalmarket.shop.core.entities.shopLoan.Loaner;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.modules.expanses.action.ExpansesAction;
import com.gomalmarket.shop.modules.expanses.view.beans.LoanTransaction;
import com.gomalmarket.shop.modules.expanses.view.beans.LoanerGroupVB;
import com.gomalmarket.shop.modules.expanses.view.beans.LoanersNameVB;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArchivedLoansPersenter extends ExpansesAction implements Initializable {

	@FXML
	private Label loanType_label;

	@FXML
	private AnchorPane loanersTable_loc;

	@FXML
	private Label totalDebtValue_label;

	@FXML
	private JFXComboBox<ComboBoxItem<String>> loanType_combo;

	@FXML
	private Pane coloredPane;

	@FXML
	private Label totalDebt__label;

	@FXML
	private JFXTextField name_TF;

	@FXML
	private StackPane stackPane;

	@FXML
	private Label loanerName_label;

	// ==========================================================================================================================================================
	private CustomTable<LoanTransaction> PayeesCustomTable;
	private CustomTable<LoanTransaction> debitsCustomtable;
	private PredicatableTable<LoanersNameVB> loanersCustomTable;
	private CustomTable<LoanerGroupVB> groupsCustomtable;
	private AnchorPane installmentsTable_loc;
	private AnchorPane groupsPane;
	private AnchorPane debtTable_loc;
	private JFXButton detail_btn;
	private JFXButton prif_btn;
	private VBox groupDetailsPane;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		log.info(
				"============================================================================================================");

		init();
	}

	private void init() {
		groupDetailsPane = new VBox();
		groupDetailsPane.setStyle("-fx-background-color: #f2f2f2");
		groupDetailsPane.setSpacing(5);

		groupsPane = new AnchorPane();
		groupsPane.setStyle("-fx-background-color: #f2f2f2");

		installmentsTable_loc = new AnchorPane();
		debtTable_loc = new AnchorPane();

//==================================================================================================================	

		List loanersNameColumns = prepareLoanersNamesColumns();
		List payeesColumns = preparePayeesColumns();
		List groupCoulmns = prepareLoanersGroupsColumns();

//========================loanersCustomTable==========================================================================================	

		loanersCustomTable = new PredicatableTable<LoanersNameVB>(loanersNameColumns, null, null,
				getLoanersTablesAction(), PredicatableTable.headTableCard, LoanersNameVB.class);

		loanersCustomTable.getCutomTableComponent().setPrefSize(300, 300);
		fitToAnchorePane(loanersCustomTable.getCutomTableComponent());
		loanersTable_loc.getChildren().addAll(loanersCustomTable.getCutomTableComponent());

//==============================groupsCustomtable====================================================================================

		groupsCustomtable = new CustomTable<LoanerGroupVB>(groupCoulmns, prepareGroupHeaderNodes(), null, null,
				getGroupsTableAction(), CustomTable.headTableCard, LoanerGroupVB.class);
		groupsCustomtable.getCutomTableComponent().setPrefHeight(200);
		fitToAnchorePane(groupsCustomtable.getCutomTableComponent());
		groupsPane.getChildren().addAll(groupsCustomtable.getCutomTableComponent());

//==========================debitsCustomtable========================================================================================	

		debitsCustomtable = new CustomTable<LoanTransaction>(payeesColumns, prepareGroupDetailHeaderNodes(), null, null,
				null, CustomTable.headTableCard, LoanTransaction.class);
		fitToAnchorePane(debitsCustomtable.getCutomTableComponent());
		debitsCustomtable.getCutomTableComponent().setPrefHeight(200);

		debtTable_loc.getChildren().addAll(debitsCustomtable.getCutomTableComponent());

		groupDetailsPane.getChildren().add(debtTable_loc);
		groupDetailsPane.setVgrow(debtTable_loc, Priority.ALWAYS);
		// ========================PayeesCustomTable==========================================================================================

		PayeesCustomTable = new CustomTable<LoanTransaction>(payeesColumns, null, null, null, null,
				CustomTable.tableCard, LoanTransaction.class);
		PayeesCustomTable.getCutomTableComponent().setPrefHeight(200);
		fitToAnchorePane(PayeesCustomTable.getCutomTableComponent());
		installmentsTable_loc.getChildren().addAll(PayeesCustomTable.getCutomTableComponent());

		groupDetailsPane.getChildren().add(installmentsTable_loc);
		groupDetailsPane.setVgrow(installmentsTable_loc, Priority.ALWAYS);
//==================================================================================================================

		stackPane.getChildren().addAll(groupsPane);
//==================================================================================================================
		loanerName_label.setText(getMessage("label.name"));
		
		loanType_label.setText(getMessage("label.loan.type"));
		// ==================================================================================================================

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

				} else if (item.getId().equals(LoanTypeEnum.OUT_LOAN.getId())) {

					initiateOutLoanPage();
					coloredPane.setStyle("-fx-background-color: #DD4B39");

				}

			}
		});

		// ==================================================================================================================

	//	name_TF.setPromptText(this.getMessage("label.name"));
		JFXTreeTableView<LoanersNameVB> table = loanersCustomTable.getTable();

		name_TF.textProperty().addListener((o, oldVal, newVal) -> {
			table.setPredicate(personProp -> {
				final LoanersNameVB loaner = personProp.getValue();
				return loaner.getName().get().contains(newVal);
			});
		});

//==================================================================================================================	
		// totalDebt__label.setText(this.getMessage("label.total.debt"));
//======================totalDebt__label============================================================================================	

	}

	private List prepareGroupHeaderNodes() {
		detail_btn = new JFXButton(this.getMessage("button.back"));
		detail_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.TABLE));
		detail_btn.getStyleClass().setAll("btn", "btn-info", "btn-sm");
		detail_btn.setDisable(true);
		detail_btn.setOnAction(e -> {

			LoanerGroupVB groupVB = (LoanerGroupVB) this.groupsCustomtable.getTable().getSelectionModel()
					.getSelectedItem();
			stackPane.getChildren().clear();
			stackPane.getChildren().addAll(groupDetailsPane);
			// fillHeaderButtons(2);
			loadGroupDetail(groupVB.getGroupId());
		});

		return new ArrayList(Arrays.asList(detail_btn));
	}

	private List prepareGroupDetailHeaderNodes() {
		prif_btn = new JFXButton(this.getMessage("button.prif"));
		prif_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.ARROW_RIGHT));
		prif_btn.getStyleClass().setAll("btn", "btn-sm", "btn-info");
		prif_btn.setOnAction(e -> {

			stackPane.getChildren().clear();
			stackPane.getChildren().addAll(groupsPane);

		});
		return new ArrayList(Arrays.asList(prif_btn));
	}

	private void loadGroupDetail(int groupId) {

		try {
			LoanerGroupVB group = (LoanerGroupVB) this.groupsCustomtable.getTable().getSelectionModel()
					.getSelectedItem();

			loadPayees(group.getLoanerId(), group.getGroupId(), group.getLoanType());
			loadDebts(group.getLoanerId(), group.getGroupId(), group.getLoanType());

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void loadPayees(int loanerId, int groupId, LoanTypeEnum type)
			throws EmptyResultSetException, DataBaseException {

		List<LoanTransaction> transactions = null;

		// detect type of loan
		if (type.equals(LoanTypeEnum.IN_LOAN)) {
			transactions = getExpansesServices().getLoanTransactions(loanerId, groupId,
					LoanTransactionTypeEnum.PAY_DEBIT);

		} else if (type.equals(LoanTypeEnum.OUT_LOAN)) {

			transactions = getExpansesServices().getLoanTransactions(loanerId, groupId,
					LoanTransactionTypeEnum.PAY_CREDIT);

		}

		PayeesCustomTable.getTable().getItems().clear();
		this.PayeesCustomTable.loadTableData(transactions);

	}

	private void loadDebts(int loanerId, int groupId, LoanTypeEnum type)
			throws EmptyResultSetException, DataBaseException {

		List<LoanTransaction> transactions = null;

		// detect type of loan
		if (type.equals(LoanTypeEnum.IN_LOAN)) {
			transactions = getExpansesServices().getLoanTransactions(loanerId, groupId,
					LoanTransactionTypeEnum.LOAN_DEBET);

		} else if (type.equals(LoanTypeEnum.OUT_LOAN)) {

			transactions = getExpansesServices().getLoanTransactions(loanerId, groupId,
					LoanTransactionTypeEnum.LOAN_CREDIT);

		}

		debitsCustomtable.getTable().getItems().clear();
		this.debitsCustomtable.loadTableData(transactions);

	}

	private void loadLoanerNames(LoanTypeEnum type) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("type", type.getId());

		try {
			List data = getExpansesServices().loadGroupsLoanerNames(type);
			List tableData = new ArrayList();

			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				Loaner loaner = (Loaner) iterator.next();
				LoanersNameVB row = new LoanersNameVB();
				row.setLoanerId(loaner.getId());
				row.setName(loaner.getName());
				row.setId(loaner.getId());
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
		// this.totalDebtValue_label.setText(String.valueOf(amount));

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

		Column c = new Column(this.getMessage("label.name"), "name", "string", 100, true);
		columns.add(c);

		return columns;

	}

	private List<Column> prepareLoanersGroupsColumns() {

		List<Column> columns = new ArrayList<Column>();

		Column c = new Column(this.getMessage("label.money.amount"), "amount", "double", 40, true);
		columns.add(c);

		c = new Column(this.getMessage("label.fromDate"), "fromDate", "double", 30, true);
		columns.add(c);

		c = new Column(this.getMessage("label.toDate"), "toDate", "double", 30, true);
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
			nameVB = new LoanersNameVB();
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

	private CustomTableActions getLoanersTablesAction() {

		CustomTableActions d = new CustomTableActions() {

			@Override
			public void rowSelected(Object table) {

				debitsCustomtable.getTable().getItems().clear();
				PayeesCustomTable.getTable().getItems().clear();
				groupsCustomtable.getTable().getItems().clear();

				LoanersNameVB loaner = null;
				try {
					JFXTreeTableView<LoanersNameVB> mytable = (JFXTreeTableView<LoanersNameVB>) table;
					loaner = mytable.getSelectionModel().getSelectedItem().getValue();
				} catch (Exception e2) {
					return;
				}

				LoanTypeEnum type = (loanType_combo.getSelectionModel().getSelectedItem().getId()
						.equals(LoanTypeEnum.IN_LOAN.getId())) ? LoanTypeEnum.IN_LOAN : LoanTypeEnum.OUT_LOAN;

				loadLoanerGroups(loaner.getId(), type);

			}

			@Override
			public void rowSelected() {
				// TODO Auto-generated method stub

			}
		};

		return d;

	}

	private void loadLoanerGroups(int loanerId, LoanTypeEnum loantype) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("loanType", loantype.getId());
		map.put("loanerId", loanerId);
		groupsCustomtable.getTable().getItems().clear();

		try {
			List data = (List) this.getBaseService().findAllBeans(LoanGroup.class, map, null);
			List tableData = new ArrayList();

			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				LoanGroup group = (LoanGroup) iterator.next();
				LoanerGroupVB row = LoanerGroupVB.builder().loanerId(group.getLoanerId()).groupId(group.getId())
						.amount(group.getAmount()).loanType(LoanTypeEnum.fromId(group.getLoanType()))
						.loanerName(group.getLoanerName()).fromDate(group.getFromDate()).toDate(group.getToDate())
						.build();

				tableData.add(row);
			}

			this.groupsCustomtable.loadTableData(tableData);

		} catch (DataBaseException e) {
			// TODO Auto-generated
			e.printStackTrace();
		} catch (EmptyResultSetException e) {
			// TODO Auto-generated
			log.info("no data found");
		}
	}

	private CustomTableActions getGroupsTableAction() {

		CustomTableActions d = new CustomTableActions() {

			@Override
			public void rowSelected(Object o) {
				// TODO Auto-generated method stub

			}

			@Override
			public void rowSelected() {
				detail_btn.setDisable(false);

			}
		};

		return d;

	}

}
