package com.gomalmarket.shop.modules.expanses.view.addEditLoan;

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

public class AddEditLoanPersenter extends ExpansesAction implements Initializable {

	Logger logger = Logger.getLogger(this.getClass().getName());

	@FXML
	private JFXComboBox<ComboBoxItem<String>> loanType_combo;

	@FXML
	private JFXButton cancel_btn;

	@FXML
	private JFXTextField amount_TF;

	@FXML
	private Label loanerName_label;

	@FXML
	private Label type_label;

	@FXML
	private JFXTextField loanerName_TF;

	@FXML
	private HBox date_loc;

	@FXML
	private JFXTextArea note_TA;

	@FXML
	private Label date_label;

	@FXML
	private AnchorPane root_pane;

	@FXML
	private Pane coloredPane;

	@FXML
	private Label title_label;

	@FXML
	private HBox datePicker_loc1;

	@FXML
	private Label amount_label;

	@FXML
	private HBox datePicker_loc;

	@FXML
	private JFXButton saveBtn;

//---------------------------------------------------------------------------------------------
	private JFXSnackbar snackBar;
	Validator myvaValidator;

	List<ComboBoxItem<String>> loanTypes;

	LoanTypeEnum loanType;
	int loanerId = 0;;
	int mode;
	LoanTransaction editedTrx;
	private JFXDatePicker datePicker;

	public AddEditLoanPersenter() {

		mode = this.requestObj.getMode();
		loanType = (LoanTypeEnum) this.requestObj.getMap().get("loanType");
		ComboBoxItem<String> selectedLoanType = (loanType.equals(LoanTypeEnum.IN_LOAN))
				? new ComboBoxItem<String>(LoanTypeEnum.IN_LOAN.getId(), this.getMessage("label.inLoan"))
				: new ComboBoxItem<String>(LoanTypeEnum.OUT_LOAN.getId(), this.getMessage("label.outLoan"));
		loanTypes = new ArrayList<ComboBoxItem<String>>(Arrays.asList(selectedLoanType));
		editedTrx = (LoanTransaction) this.requestObj.getEditedObject();
		loanerId = this.requestObj.getEditedObjectId();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		logger.log(Level.INFO,
				"============================================================================================================");

		datePicker = new JFXDatePicker();
		datePicker.getEditor().setAlignment(Pos.CENTER_LEFT);
		datePicker.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		datePicker.setPrefWidth(317);

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
		date_loc.getChildren().add(datePicker);
		date_loc.setPrefWidth(317);
		date_label.setText(getMessage("label.date"));
		// ==============================================================================================================

		loanType_combo.getItems().addAll(loanTypes);
		loanType_combo.setEditable(false);
		loanType_combo.getSelectionModel().selectFirst();
		String styleColoredPane = (loanType.equals(LoanTypeEnum.IN_LOAN)) ? "-fx-background-color: #00A65A"
				: "-fx-background-color: #DD4B39";
		coloredPane.setStyle(styleColoredPane);

//==============================================================================================================

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
		// ============================================================================================================
				snackBar = new JFXSnackbar(root_pane);
//==============================================================================================================

		amount_label.setText(this.getMessage("label.money.amount"));
		type_label.setText(this.getMessage("label.type"));
		loanerName_label.setText(this.getMessage("label.name"));

		// ==============================================================================================================
		saveBtn.setText(this.getMessage("button.save"));
		saveBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SAVE));
		saveBtn.getStyleClass().setAll("btn", "btn-primary");

		// -----------------------------
		cancel_btn.setText(this.getMessage("button.cancel"));
		cancel_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.REMOVE));
		cancel_btn.getStyleClass().setAll("btn", "btn-danger");
		cancel_btn.setOnAction(e -> {
			cancel();

		});

		// ==============================================================================================================
		note_TA.setPromptText(getMessage("label.notes"));// label.loan
		String title = (this.mode == Request.MODE_EDIT)
				? this.getMessage("label.edit") + " " + this.getMessage("label.loan")
				: this.getMessage("label.add") + " " + this.getMessage("label.loan");
		title_label.setText(title);
		// ==============================================================================================================

		TextFields.bindAutoCompletion(loanerName_TF, t -> {

			return getExpansesServices().getfindLoaners(t.getUserText());

		});

		intiateScreen();
	}

//---------------------------------------------------------------------------------------------------------------

	void intiateScreen() {

		switch (mode) {
		case Request.MODE_EDIT:

			try {

				ShopLoanTransaction trx = (ShopLoanTransaction) this.getBaseService()
						.findBean(ShopLoanTransaction.class, editedTrx.getId());
				this.note_TA.setText(trx.getNotes());
				this.loanerName_TF.setText(trx.getLoaner().getName());
				loanerName_TF.setEditable(false);
				this.amount_TF.setText(String.valueOf(trx.getAmount()));
				datePicker.setValue(this.getBaseService().convertToLocalDateViaMilisecond(trx.getTransactionDate()));

			} catch (Exception e) {
				alert(AlertType.ERROR, "", "", this.getMessage("msg.err.cannot.load.data"));
				this.responseMap = new HashMap<String, Object>();
				responseMap.put("code", "500");

				cancel();
			}

			break;
		case Request.MODE_ADD:

			try {
				int loanerId = this.requestObj.getEditedObjectId();
				Loaner loaner = (Loaner) this.getBaseService().findBean(Loaner.class, loanerId);
				this.loanerName_TF.setText(loaner.getName());
				loanerName_TF.setEditable(false);

			} catch (Exception e) {
				alert(AlertType.ERROR, "", "", this.getMessage("msg.err.cannot.load.data"));
				this.responseMap = new HashMap<String, Object>();
				responseMap.put("code", "500");

				cancel();
			}

			break;
		default:
			break;
		}

	}

//---------------------------------------------------------------------------------------------------------------
	private LoanAccount getLoanAccount(int loanerId, String name) {
		LoanAccount account = null;

		switch (mode) {
		case Request.MODE_CREATE_NEW:
			
			account=getExpansesServices().getLoanAccount(0, name);
			break;
		case Request.MODE_ADD:
			account=getExpansesServices().getLoanAccount(loanerId, null);
			break;

		case Request.MODE_EDIT:
			account=getExpansesServices().getLoanAccount(loanerId, null);
			break;
		}
		return account;

	}

	@FXML
	private void save() {

		if (!validateForm())
			return;

		double amount = Double.parseDouble(this.amount_TF.getText());
		String notes = note_TA.getText();
		String loanerName = (this.loanerName_TF.getText());
		Date trxDate = getBaseService().convertToDateViaInstant(this.datePicker.getValue());

		switch (mode) {
		case Request.MODE_CREATE_NEW:
			try {

				this.getExpansesServices().loanTansaction(0, loanerName, amount, notes, trxDate, loanType,
						getAppContext().getFridage(), getAppContext().getSeason());
				this.responseObj = prepareResponse(NavigationResponseCodeEnum.SUCCESS);
				
				LoanAccount acc= getLoanAccount(0, loanerName);
				responseObj.getResults().put("loanerId",acc.getLoaner().getId());
				Stage stage = (Stage) cancel_btn.getScene().getWindow();
				stage.close();
				alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.save"));

			} catch (DataBaseException e) {
				alert(AlertType.ERROR, this.getMessage("msg.err"), this.getMessage("msg.err"),
						this.getMessage("msg.err.general"));
				e.printStackTrace();
				 
			}

			break;
		case Request.MODE_ADD:
			try {
				this.getExpansesServices().loanTansaction(0, loanerName, amount, notes, trxDate, loanType,
						getAppContext().getFridage(), getAppContext().getSeason());

				this.responseObj = prepareResponse(NavigationResponseCodeEnum.SUCCESS);
				responseObj.getResults().put("loanerId",loanerId);
				Stage stage = (Stage) cancel_btn.getScene().getWindow();
				stage.close();
				alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.save"));

			} catch (DataBaseException e) {
				snackBar.show(this.getMessage("msg.err.general"), 2000);
				logger.log(Level.WARNING, e.getMessage());
			}

			break;
		case Request.MODE_EDIT:
			try {
				this.getExpansesServices().editLoanTansaction(editedTrx.getId(), amount, notes, trxDate, loanType,
						getAppContext().getFridage(), getAppContext().getSeason());
				this.responseObj = prepareResponse(NavigationResponseCodeEnum.SUCCESS);
				responseObj.getResults().put("loanerId",loanerId);
				Stage stage = (Stage) cancel_btn.getScene().getWindow();
				stage.close();

				alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.edit"));

			} catch (DataBaseException | InvalidReferenceException | EmptyResultSetException e) {
				snackBar.show(this.getMessage("msg.err.general"), 2000);

			}

			alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.edit"));

			break;

		default:
			break;
		}
	}

//---------------------------------------------------------------------------------------------------------------

	private void alert(AlertType alertType, String title, String headerText, String message) {
		Alert a = new Alert(alertType);
		a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		a.setTitle(title);
		a.setHeaderText(headerText);
		a.setContentText(message);
		a.show();

	}
//---------------------------------------------------------------------------------------------------------------

	private void cancel() {

		this.responseObj = prepareResponse(NavigationResponseCodeEnum.EXIT);
		Stage stage = (Stage) cancel_btn.getScene().getWindow();
		// do what you have to do
		stage.close();

	}

	private boolean validateForm() {
		String name = loanerName_TF.getText();
		String amount = amount_TF.getText();
		double safaBalance = this.getExpansesServices().getSafeBalance(getAppContext().getSeason());
		LoanAccount account = getLoanAccount(loanerId, name);
			
		if (amount.isEmpty()) {

			snackBar.show(this.getMessage("msg.err.required.amount"), 2000);

			return false;

		} else if (name.isEmpty()) {
			snackBar.show(this.getMessage("msg.err.required.name"), 2000);

			return false;

		}

		if (datePicker.getValue() == null) {
			snackBar.show(this.getMessage("msg.err.required.date"), 2000);

			return false;

		}
		
		 

		if (account != null && loanType.equals(LoanTypeEnum.IN_LOAN)
				&& account.getType().equals(LoanTypeEnum.OUT_LOAN.getId())) {
			snackBar.show(this.getMessage("msg.warnning.loaner.exist.outloan"), 2000);

			return false;

		}

		if (account != null && loanType.equals(LoanTypeEnum.OUT_LOAN)
				&& account.getType().equals(LoanTypeEnum.IN_LOAN.getId())) {
			snackBar.show(this.getMessage("msg.warnning.loaner.exist.inloan"), 2000);

			return false;
		}

		if (loanType.equals(LoanTypeEnum.OUT_LOAN) && safaBalance < Double.parseDouble(amount)) {

			snackBar.show(this.getMessage("msg.err.notEnough.safeBalance"), 2000);
			return false;
		}
		return true;

	}

	public Response prepareResponse(NavigationResponseCodeEnum reponseStatusCode) {
		return new Response() {

			@Override
			public Map getResults() {
				// TODO Auto-generated method stub
				return new HashMap<String,Object>();
			}

			@Override
			public NavigationResponseCodeEnum getResponseCode() {
				// TODO Auto-generated method stub
				return reponseStatusCode;
			}

		};

	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}