package com.gomalmarket.shop.modules.contractor.view.addSupplier;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome;

import com.gomalmarket.shop.core.Enum.ContractorOwnerEnum;
import com.gomalmarket.shop.core.Enum.ContractorTypeEnum;
import com.gomalmarket.shop.core.Enum.NavigationResponseCodeEnum;
import com.gomalmarket.shop.core.action.navigation.Request;
import com.gomalmarket.shop.core.action.navigation.Response;
import com.gomalmarket.shop.core.entities.contractor.ContractorTransaction;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.validator.Validator;
import com.gomalmarket.shop.modules.contractor.action.ContractorAction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

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

public class AddSupplierPersenter extends ContractorAction implements Initializable {

	Logger logger = Logger.getLogger(this.getClass().getName());

	@FXML
	private HBox paid_toogleBtn;

	@FXML
	private JFXButton cancel_btn;

	@FXML
	private JFXTextField name_TF;

	@FXML
	private JFXToggleButton paid_TBtn;

	@FXML
	private Label owner_label;
	
	@FXML
	private JFXTextField owner_TF;
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
	private Pane coloredPane;

	@FXML
	private Label title_label;

	@FXML
	private HBox datePicker_loc;

	@FXML
	private JFXButton saveBtn;

	private JFXDatePicker datePicker;
	private JFXSnackbar snackBar;

	private int mode;

	private int typeId;

	private ContractorOwnerEnum owner;

	private int trxId;

	public AddSupplierPersenter() {

		mode = request.getMode();
		int tempid = (int) request.getMap().get("ownerId");
		typeId = (int) request.getMap().get("typeId");
		owner = ContractorOwnerEnum.fromId(tempid);
		trxId = request.getEditedObjectId();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.log(Level.INFO,
				"============================================================================================================");
		init();

	}

	private void init() {

		snackBar = new JFXSnackbar(root_pane);
		// ============================================================================================================

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

		// ============================================================================================================
		amount_label.setText(this.getMessage("label.money.amount"));
		name_label.setText(this.getMessage("label.name"));
		date_label.setText(this.getMessage("label.date"));
		note_TA.setPromptText(this.getMessage("label.notes"));
		owner_label.setText(getMessage("label.owner"));
		owner_TF.setText(getMessage(owner.getLabel()));

		saveBtn.setText(this.getMessage("button.save"));
		saveBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SAVE));

		saveBtn.getStyleClass().setAll("btn", "btn-primary");
		saveBtn.setOnAction(e -> {
			save();

		});

		cancel_btn.setText(this.getMessage("button.cancel"));
		cancel_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SAVE));

		cancel_btn.getStyleClass().setAll("btn", "btn-danger");
		cancel_btn.setOnAction(e -> {
			cancel();

		});

		paid_TBtn.setText(getMessage("label.contractor.status.paid"));
		title_label.setText(getMessage("label.add.transaction"));
//============================================================================================================

		amount_TF.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("grossWeight changed from " + oldValue + " to " + newValue);
			Validator myvaValidator = new Validator();
			if (newValue.length() > 0) {
				myvaValidator.getValidDouble(newValue, 0, Double.MAX_VALUE, "grossWeightValue", true);
				if (!myvaValidator.noException()) {
					newValue = oldValue;
					amount_TF.setText(newValue);
				}

			}
		});

//============================================================================================================

		TextFields.bindAutoCompletion(name_TF, t -> {
			return autoComplete(t.getUserText());
		});
//============================================================================================================

	}

	private void save() {
		if (validateForm()) {

			String name = name_TF.getText();
			double amount = Double.parseDouble(amount_TF.getText());
			String notes = note_TA.getText();
			 
			Date date = getValueOfDatePicker();
			int paid = (paid_TBtn.isSelected()) ? 1 : 0;

			switch (mode) {
			case Request.MODE_CREATE_NEW:
				ContractorTransaction trx=null;;
				try {
					trx = this.getContractorService().AddContractorTransaction
						(name, typeId, amount, getAppContext().getFridage(), notes, paid, owner.getId(), date, getAppContext().getSeason());
					this.response = prepareResponse(NavigationResponseCodeEnum.SUCCESS);
					response.getResults().put("contractorId",trx.getContractor().getId());
					Stage stage = (Stage) cancel_btn.getScene().getWindow();
					stage.close();
					alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.save"));
				} catch (DataBaseException e) {
					alert(AlertType.ERROR, this.getMessage("msg.err"), this.getMessage("msg.err"),
							this.getMessage("msg.err.general"));
					e.printStackTrace();
					 
				} catch (InvalidReferenceException e) {
					
				}				
				
			
				break;
			case Request.MODE_ADD:

				 trx=null;
				try {
					trx = this.getContractorService().AddContractorTransaction
						(name, typeId, amount, getAppContext().getFridage(), notes, paid,  owner.getId(), date, getAppContext().getSeason());
								
				
				this.response = prepareResponse(NavigationResponseCodeEnum.SUCCESS);
				Map results=response.getResults();
				results.put("contractorId",trx.getContractor().getId());
				
				Stage	 stage = (Stage) cancel_btn.getScene().getWindow();
				stage.close();
				alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.save"));
				} catch (DataBaseException e) {
					alert(AlertType.ERROR, this.getMessage("msg.err"), this.getMessage("msg.err"),
							this.getMessage("msg.err.general"));
					e.printStackTrace();
					 
				} catch (InvalidReferenceException e) {
				 
				}
				break;
			case Request.MODE_EDIT:

				 trx=null;
					try {
						trx = this.getContractorService().editContractorTransaction
								(name, typeId, amount, getAppContext().getFridage(), notes, paid,  owner.getId(), date, getAppContext().getSeason(), trxId);
								} catch (DataBaseException e) {
						alert(AlertType.ERROR, this.getMessage("msg.err"), this.getMessage("msg.err"),
								this.getMessage("msg.err.general"));
						e.printStackTrace();
						 
					} catch (InvalidReferenceException e) {
					 
					}				
					
					this.response = prepareResponse(NavigationResponseCodeEnum.SUCCESS);
					response.getResults().put("contractorId",trx.getContractor().getId());
					Stage stage = (Stage) cancel_btn.getScene().getWindow();
					stage.close();
					alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.edit"));
					break;

			default:
				break;
			}

			 

		}
	}

	boolean validateForm() {

		double safaBalance = this.getExpansesServices().getSafeBalance(getAppContext().getSeason());

		if (name_TF.getText().isEmpty()) {

			snackBar.show(this.getMessage("msg.err.required.name"), 1000);

			return false;

		} else if (amount_TF.getText().isEmpty()) {
			snackBar.show(this.getMessage("msg.err.required.amount"), 1000);

			return false;
		} else if (paid_TBtn.isSelected() && safaBalance < Double.parseDouble(amount_TF.getText())) {
			snackBar.show(this.getMessage("msg.err.notEnough.safeBalance"), 1000);
			return false;
		}

		else if (datePicker.getValue() == null) {
			snackBar.show(this.getMessage("msg.err.required.date"), 1000);

			return false;
		}
		return true;

	}

	private void cancel() {

		Stage stage = (Stage) cancel_btn.getScene().getWindow();
		// do what you have to do
		stage.close();

	}

	private Date getValueOfDatePicker() {

		LocalDate localate = datePicker.getValue();
		Instant instant = Instant.from(localate.atStartOfDay(ZoneId.systemDefault()));

		return Date.from(instant);

	}

	private List autoComplete(String name) {

		int ownerid = (int) request_map.get("ownerId");

		return this.getContractorService().getSuggestedContractorName(name, ownerid, ContractorTypeEnum.SUPPLIER_);
	}
	public Response prepareResponse(NavigationResponseCodeEnum reponseStatusCode) {
		return new Response() {

	Map <String,Object> map=new HashMap<String,Object>();
			
			
			@Override
			public Map getResults() {
				// TODO Auto-generated method stub
				return map;
			}

			@Override
			public NavigationResponseCodeEnum getResponseCode() {
				// TODO Auto-generated method stub
				return reponseStatusCode;
			}

		};

	}
	
	
	private void alert(AlertType alertType, String title, String headerText, String message) {
		Alert a = new Alert(alertType);
		a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		a.setTitle(title);
		a.setHeaderText(headerText);
		a.setContentText(message);
		a.show();

	}
}
