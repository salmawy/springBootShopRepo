package com.gomalmarket.shop.modules.expanses.view.payLoan;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome;

import com.gomalmarket.shop.core.Enum.LoanTypeEnum;
import com.gomalmarket.shop.core.Enum.NavigationResponseCodeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.action.navigation.Request;
import com.gomalmarket.shop.core.action.navigation.Response;
import com.gomalmarket.shop.core.entities.shopLoan.LoanAccount;
import com.gomalmarket.shop.core.entities.shopLoan.Loaner;
import com.gomalmarket.shop.core.entities.shopLoan.ShopLoanTransaction;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.validator.Validator;
import com.gomalmarket.shop.modules.expanses.action.ExpansesAction;
import com.gomalmarket.shop.modules.expanses.view.beans.LoanTransaction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PayLoanPersenter extends ExpansesAction implements Initializable {

	Logger logger = Logger.getLogger(this.getClass().getName());

	@FXML
	private Label title_label;

	@FXML
	private JFXButton cancel_btn;

	@FXML
	private Label loanType_label;

	@FXML
	private JFXTextField name_TF;

	@FXML
	private Label name_label;

	@FXML
	private Label amount_label;

	@FXML
	private JFXTextArea note_TA;

	@FXML
	private JFXTextField amount_TF;

	@FXML
	private Label date_label;

	@FXML
	private AnchorPane root_pane;

	@FXML
	private JFXComboBox<ComboBoxItem<String>> loanType_combo;

	@FXML
	private Pane coloredPane;

	@FXML
	private HBox datePicker_loc;

	@FXML
	private JFXButton saveBtn;

	private JFXDatePicker datePicker;
	private JFXSnackbar snackBar;

	private List<ComboBoxItem<String>> loanTypes;
	private LoanTypeEnum loanType;
	private int loanerId;
	private int mode;
	private LoanTransaction editedTrx;
	private Validator myvaValidator;

	public PayLoanPersenter() {
		loanerId = this.requestObj.getEditedObjectId();
		mode = this.requestObj.getMode();
		loanType = (LoanTypeEnum) requestObj.getMap().get("loanType");
		ComboBoxItem<String> selectedLoanType = (loanType.equals(LoanTypeEnum.IN_LOAN))
				? new ComboBoxItem<String>(LoanTypeEnum.IN_LOAN.getId(), this.getMessage("label.inLoan"))
				: new ComboBoxItem<String>(LoanTypeEnum.OUT_LOAN.getId(), this.getMessage("label.inLoan"));
		loanTypes = new ArrayList<ComboBoxItem<String>>(Arrays.asList(selectedLoanType));
		editedTrx = (LoanTransaction) requestObj.getEditedObject();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.log(Level.INFO,
				"============================================================================================================");

		init();
	}

	private void init() {

//============================================================================================================

		datePicker = new JFXDatePicker();
		datePicker.getEditor().setAlignment(Pos.CENTER);
		datePicker.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		datePicker.setConverter(new StringConverter<LocalDate>() {
			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

			@Override
			public String toString(LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		});
		datePicker_loc.getChildren().add(datePicker);

//============================================================================================================
		amount_label.setText(this.getMessage("label.money.amount"));
		amount_TF.textProperty().addListener((observable, oldValue, newValue) -> {
			myvaValidator = new Validator();
			if (newValue.length() > 0) {
				myvaValidator.getValidDouble(newValue, 0, Double.MAX_VALUE, "amountValue", true);
				if (!myvaValidator.noException()) {

					newValue = oldValue;
					amount_TF.setText(newValue);
				}

			}
		});
//============================================================================================================

		name_label.setText(this.getMessage("label.name"));
		date_label.setText(this.getMessage("label.date"));
		note_TA.setPromptText(this.getMessage("label.notes"));

		saveBtn.setText(this.getMessage("button.save"));
		saveBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SAVE));
		saveBtn.getStyleClass().setAll("btn", "btn-primary");
		saveBtn.setOnAction(e -> {
			save();

		});

		cancel_btn.setText(this.getMessage("button.cancel"));
		cancel_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.CLOSE));
		cancel_btn.getStyleClass().setAll("btn", "btn-danger");
		cancel_btn.setOnAction(e -> {
			cancel();

		});

//============================================================================================================

		loanType_label.setText(this.getMessage("label.type"));

		loanType_combo.getItems().addAll(loanTypes);
		loanType_combo.setEditable(false);
		loanType_combo.getSelectionModel().selectFirst();

		String styleColoredPane = (loanType.equals(LoanTypeEnum.IN_LOAN)) ? "-fx-background-color: #00A65A"
				: "-fx-background-color: #DD4B39";
		coloredPane.setStyle(styleColoredPane);

		// ============================================================================================================
		snackBar = new JFXSnackbar(root_pane);

//============================================================================================================
		TextFields.bindAutoCompletion(name_TF, t -> {

			return getExpansesServices().getfindLoaners(t.getUserText());

		});

		// ============================================================================================================
		intiateScreen();
	}

	private void cancel() {

		Stage stage = (Stage) cancel_btn.getScene().getWindow();
		// do what you have to do

		this.responseObj = prepareResponse(NavigationResponseCodeEnum.EXIT);

		stage.close();

	}

	private boolean validateForm() {

		String name = name_TF.getText();
		String amount = amount_TF.getText();
		double safaBalance = this.getExpansesServices().getSafeBalance(getAppContext().getSeason());

		if (amount.isEmpty()) {

			snackBar.show(this.getMessage("msg.err.required.amount"), 1000);

			return false;

		} else if (name.isEmpty()) {
			snackBar.show(this.getMessage("msg.err.required.name"), 1000);

			return false;

		}

		if (datePicker.getValue() == null) {
			snackBar.show(this.getMessage("msg.err.required.date"), 1000);

			return false;

		}

		LoanAccount account = null;
		try {
			account = this.getExpansesServices().getLoanerAccount(editedTrx.getLoanerId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (account == null) {

			snackBar.show(this.getMessage("msg.err.notfound.name"), 1000);

			return false;

		}

		if (account.getAmount() > 0 && account.getType().equals("OUT_LOAN")) {
			snackBar.show(this.getMessage("msg.err.amountShouldBecollestedFromLoaner") + " : " + account.getAmount(),
					1000);

			return false;

		}
		if (Double.parseDouble(amount) > account.getAmount()) {
			snackBar.show(this.getMessage("msg.err.input.amount.greather") + " : " + account.getAmount(), 1000);

			return false;
		}

		if (loanType.equals(LoanTypeEnum.IN_LOAN) && safaBalance < Double.parseDouble(amount)) {

			snackBar.show(this.getMessage("msg.err.notEnough.safeBalance"), 1000);
			return false;
		}

		return true;

	}

	void intiateScreen() {

		if (mode == Request.MODE_EDIT) {

			try {

				ShopLoanTransaction trx = (ShopLoanTransaction) this.getBaseService()
						.findBean(ShopLoanTransaction.class, editedTrx.getId());
				this.note_TA.setText(trx.getNotes());
				this.name_TF.setText(trx.getLoaner().getName());
				name_TF.setEditable(false);
				this.amount_TF.setText(String.valueOf(trx.getAmount()));
				datePicker.setValue(this.getBaseService().convertToLocalDateViaMilisecond(trx.getTransactionDate()));

			} catch (Exception e) {
				alert(AlertType.ERROR, "", "", this.getMessage("msg.err.cannot.load.data"));
				this.responseObj = prepareResponse(NavigationResponseCodeEnum.CANNOT_LOAD_DATA);

				cancel();
			}
		} else if (mode == Request.MODE_ADD) {
			try {
				Loaner loaner = (Loaner) this.getBaseService().findBean(Loaner.class, loanerId);
				this.name_TF.setText(loaner.getName());
				name_TF.setEditable(false);

			} catch (Exception e) {
				alert(AlertType.ERROR, "", "", this.getMessage("msg.err.cannot.load.data"));
				this.responseMap = new HashMap<String, Object>();
				responseMap.put("code", "500");

				cancel();
			}

		}
	}

	// ---------------------------------------------------------------------------------------------------------------

	/*
	 * private void save() { if (validateForm())
	 * snackBar.show(this.getMessage("button.save"), 1000);
	 * 
	 * }
	 */
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

	@FXML
	private void save() {

		double amount = Double.parseDouble(this.amount_TF.getText());
		String notes = note_TA.getText();
		String loanerName = (this.name_TF.getText());
		Date trxDate = getBaseService().convertToDateViaInstant(this.datePicker.getValue());

		switch (mode) {
		case Request.MODE_ADD:
			try {
				this.getExpansesServices().loanPayTansaction(loanerId, loanerName, amount, notes, trxDate, loanType,
						getAppContext().getFridage(), getAppContext().getSeason());
				this.responseObj = prepareResponse(NavigationResponseCodeEnum.SUCCESS);

				Stage stage = (Stage) cancel_btn.getScene().getWindow();
				stage.close();

			} catch (DataBaseException e) {
				alert(AlertType.ERROR, this.getMessage("msg.err"), this.getMessage("msg.err"),
						this.getMessage("msg.err.general"));
				e.printStackTrace();
			}
			alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.save"));

			break;
		case Request.MODE_EDIT:
			try {
				this.getExpansesServices().editLoanPayTansaction(editedTrx.getId(), amount, notes, trxDate, loanType,
						getAppContext().getFridage(), getAppContext().getSeason());
				this.responseObj = prepareResponse(NavigationResponseCodeEnum.SUCCESS);
				Stage stage = (Stage) cancel_btn.getScene().getWindow();
				stage.close();

				alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.edit"));

			} catch (DataBaseException | InvalidReferenceException | EmptyResultSetException e) {
				alert(AlertType.ERROR, this.getMessage("msg.err"), this.getMessage("msg.err"),
						this.getMessage("msg.err.general"));

			}
			break;

		default:
			break;
		}
	}

	public Response prepareResponse(NavigationResponseCodeEnum reponseStatusCode) {
		return new Response() {

			@Override
			public Map getResults() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public NavigationResponseCodeEnum getResponseCode() {
				// TODO Auto-generated method stub
				return reponseStatusCode;
			}

		};

	}

}
