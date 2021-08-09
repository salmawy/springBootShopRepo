package com.gomalmarket.shop.modules.Customer.discharge.view.edit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.glyphfont.FontAwesome;

import com.gomalmarket.shop.core.Enum.ProductTypeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.entities.basic.Product;
import com.gomalmarket.shop.core.entities.basic.Store;
import com.gomalmarket.shop.core.entities.customers.Customer;
import com.gomalmarket.shop.core.entities.customers.CustomerOrder;
import com.gomalmarket.shop.core.entities.customers.CustomerType;
import com.gomalmarket.shop.core.entities.customers.VehicleType;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.validator.Validator;
import com.gomalmarket.shop.modules.Customer.action.CustomerBaseAction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class EditCustomerOrderPersenter extends CustomerBaseAction implements Initializable {

	Logger logger = Logger.getLogger(this.getClass().getName());

	@FXML
	private AnchorPane inputForm_loc;

	@FXML
	private JFXButton saveBtn;

	private GridPane gridPane;

	private JFXDatePicker datePicker;
	private JFXTextField name_TF;
	private JFXTextField phone_TF;
	private JFXTextField address_TF;
	private JFXTextField grossWeight_TF;
	private JFXTextField count_TF;
	private JFXTextField nolun_TF;
	private JFXTextField gift_TF;
	private JFXTextField code_TF;
	private JFXTextArea notes_TA;
	private Validator myValidator;
	private JFXComboBox<ComboBoxItem<Integer>> productTyp_CB;
	private JFXComboBox<ComboBoxItem<Integer>> storeLocation_CB;
	private JFXComboBox<ComboBoxItem<Integer>> vehicleType_CB;

	private JFXComboBox<ComboBoxItem<Integer>> cutomerBox;
	private Image errIcon;
	private CustomerOrder oldOrder;
	private int orderId;

	private int mode;
	private final int Add_mode = 0;
	private final int edit_mode = 1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		errIcon = loadImage("icons/error-icon.png");
		logger.log(Level.INFO,
				"============================================================================================================");

		init();
	}

	public EditCustomerOrderPersenter() {
		orderId = (int) this.request.get("orderId");
		mode = (orderId == 0) ? Add_mode : edit_mode;

	}

	private void init() {

		gridPane = new GridPane();
		gridPane.setCenterShape(true);
		gridPane.setAlignment(gridPane.getAlignment().BASELINE_RIGHT);
		gridPane.setVgap(20);
		initInputGridPane();
		saveBtn.setOnAction(e -> {
			if (mode == edit_mode)
				editOrder();
			else
				saveCustomerOrder();

		});

//======================================================================================================================================

		if (mode == edit_mode) {
			loadOrderData();
		}

	}

	private void loadOrderData() {

		try {
			oldOrder = (CustomerOrder) this.getBaseService().findBean(CustomerOrder.class, orderId);

			
			ComboBoxItem item=new ComboBoxItem(oldOrder.getCustomer().getType().getId(), oldOrder.getCustomer().getType().getName(), oldOrder.getCustomer().getType());
			cutomerBox.getSelectionModel().select(item);
			name_TF.setText(oldOrder.getCustomer().getName());
			phone_TF.setText(oldOrder.getCustomer().getPhone());
			address_TF.setText(oldOrder.getCustomer().getAddress());

			grossWeight_TF.setText(oldOrder.getGrossweight().toString());
			nolun_TF.setText(String.valueOf(oldOrder.getNolun()));
			gift_TF.setText(String.valueOf(oldOrder.getTips()));
			count_TF.setText(String.valueOf(oldOrder.getUnits()));
			code_TF.setText(oldOrder.getOrderTag());
			vehicleType_CB.getSelectionModel().select(oldOrder.getVehicleType().getId() - 1);
			notes_TA.setText(oldOrder.getNotes());

			this.datePicker.setValue(getBaseService().convertToLocalDateViaMilisecond(oldOrder.getOrderDate()));

		} catch (DataBaseException | InvalidReferenceException e) {
			alert(AlertType.ERROR, this.getMessage("msg.err"), this.getMessage("msg.err"),
					this.getMessage("msg.err.cannot.load.data"));
			e.printStackTrace();
		}

	}

	private void initInputGridPane() {

		Label typeLabel = new Label(this.getMessage("label.customer.Type"));

		Label nameLabel = new Label(this.getMessage("customer.name"));

		Label productType_Label = new Label(this.getMessage("label.product"));

		Label grossWeight_Label = new Label(this.getMessage("label.grossWeight"));

		Label count_Label = new Label(this.getMessage("label.count.sabait"));

		Label storeId_Label = new Label(this.getMessage("label.store.name"));

		Label gift_Label = new Label(this.getMessage("label.gift"));

		Label vehicelType_Label = new Label(this.getMessage("label.vehicle.type"));

		Label notes_Label = new Label(this.getMessage("label.notes"));

		Label code_Label = new Label(this.getMessage("label.code"));

		Label nolun_Label = new Label(this.getMessage("label.nolun"));
		Label date_Label = new Label(this.getMessage("label.date"));

		cutomerBox = new JFXComboBox();
		cutomerBox.getStyleClass().add("comboBox");
 try {
	getBaseService().findAllBeans(CustomerType.class).stream().forEach(e ->{
		CustomerType customertype=(CustomerType)e;
		cutomerBox.getItems().add(new ComboBoxItem(customertype.getId(), customertype.getName(),customertype));	
	});
} catch (DataBaseException | EmptyResultSetException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
	
		cutomerBox.getSelectionModel().selectFirst();

		
		
		
	
				
		vehicleType_CB = new JFXComboBox();
		vehicleType_CB.getStyleClass().add("comboBox");
		 try {
			getBaseService().findAllBeans(VehicleType.class).stream().forEach(e ->{
				
		
				 VehicleType vehicleType=(VehicleType)e;
				 log.info("vehicle type >>>>"+vehicleType.getName());
				 vehicleType_CB.getItems().add(new ComboBoxItem(vehicleType.getId(), vehicleType.getName(),vehicleType));	
				});
		} catch (DataBaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (EmptyResultSetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 
		vehicleType_CB.getSelectionModel().selectFirst();

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

		this.datePicker.setValue(getBaseService().convertToLocalDateViaMilisecond(new Date()));

//=============================name ===============================================================================

		name_TF = new JFXTextField();
		name_TF.getStyleClass().add("TextField");
		RequiredFieldValidator nameValidator = new RequiredFieldValidator();
		nameValidator.setMessage(this.getMessage("msg.err.required.value"));
		nameValidator.setIcon(new ImageView(errIcon));

		name_TF.getValidators().add(nameValidator);
		
		/*
		 * 		int customerTypeId = cutomerBox.getSelectionModel().getSelectedItem().getValue();

			return "";// this.getCustomerService().getSuggestedCustomerName(
						// t.getUserText(),customerTypeId) ;*/
		TextFields.bindAutoCompletion(name_TF, t -> {
			int customerTypeId = cutomerBox.getSelectionModel().getSelectedItem().getId();
	return this.getCustomerService().getSuggestedCustomerName(t.getUserText(),customerTypeId) ;

		});

		name_TF.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					name_TF.validate();
				}

			}
		});
		// ============================phone================================================================================

		phone_TF = new JFXTextField();
		phone_TF.getStyleClass().add("jfx-text-field");

		address_TF = new JFXTextField();
		address_TF.getStyleClass().add("jfx-text-field");
//*********************************gross weight****************************************************************************************************** 
		grossWeight_TF = new JFXTextField();
		grossWeight_TF.getStyleClass().add("TextField");
		RequiredFieldValidator grossWeightValidator = new RequiredFieldValidator();
		grossWeightValidator.setMessage(this.getMessage("msg.err.required.value"));
		grossWeightValidator.setIcon(new ImageView(errIcon));

		grossWeight_TF.getValidators().add(grossWeightValidator);
		grossWeight_TF.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("grossWeight changed from " + oldValue + " to " + newValue);
			myValidator = new Validator();
			if (newValue.length() > 0) {
				myValidator.getValidDouble(newValue, 0, Double.MAX_VALUE, "grossWeightValue", true);
				if (!myValidator.noException()) {

					newValue = oldValue;
					grossWeight_TF.setText(newValue);
				}

			}

		});
		grossWeight_TF.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					grossWeight_TF.validate();

				}
			}
		});
//*********************************count****************************************************************************************************** 

		count_TF = new JFXTextField();
		count_TF.getStyleClass().add("TextField");
		count_TF.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("count changed from " + oldValue + " to " + newValue);
			myValidator = new Validator();
			if (newValue.length() > 0) {
				myValidator.getValidInt(newValue, 0, Integer.MAX_VALUE, "count", false);
				if (!myValidator.noException()) {

					newValue = oldValue;
					count_TF.setText(newValue);
				}

			}

		});
//*********************************nolun****************************************************************************************************** 

		nolun_TF = new JFXTextField();
		nolun_TF.getStyleClass().add("TextField");
		RequiredFieldValidator noluntValidator = new RequiredFieldValidator();
		noluntValidator.setMessage(this.getMessage("msg.err.required.value"));
		noluntValidator.setIcon(new ImageView(errIcon));

		nolun_TF.getValidators().add(noluntValidator);
		nolun_TF.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					nolun_TF.validate();

				}
			}
		});

		nolun_TF.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("nolun changed from " + oldValue + " to " + newValue);
			myValidator = new Validator();
			if (newValue.length() > 0) {
				myValidator.getValidDouble(newValue, 0, Double.MAX_VALUE, "grossWeightValue", true);
				if (!myValidator.noException()) {

					newValue = oldValue;
					nolun_TF.setText(newValue);
				}

			}

		});

		// *********************************tips******************************************************************************************************

		gift_TF = new JFXTextField();
		gift_TF.getStyleClass().add("TextField");
		RequiredFieldValidator giftValidator = new RequiredFieldValidator();
		giftValidator.setMessage(this.getMessage("msg.err.required.value"));
		giftValidator.setIcon(new ImageView(errIcon));

		gift_TF.getValidators().add(giftValidator);
		gift_TF.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("gift changed from " + oldValue + " to " + newValue);
			myValidator = new Validator();
			if (newValue.length() > 0) {
				myValidator.getValidDouble(newValue, 0, Double.MAX_VALUE, "grossWeightValue", true);
				if (!myValidator.noException()) {

					newValue = oldValue;
					gift_TF.setText(newValue);
				}

			}

		});
		gift_TF.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					gift_TF.validate();

				}
			}
		});
		// *********************************code******************************************************************************************************

		code_TF = new JFXTextField();
//code.setFocusColor(Color.valueOf("#365fda"));
// code.setUnFocusColor(Color.valueOf("#090e5b"));

		code_TF.getStyleClass().add("jfx-text-field");
//*********************************notes****************************************************************************************************** 

		notes_TA = new JFXTextArea();
		notes_TA.getStyleClass().add("textArea");
//*********************************productTyp****************************************************************************************************** 

		productTyp_CB = new <ComboBoxItem>JFXComboBox();
		productTyp_CB.getStyleClass().add("comboBox");
//*********************************save btn****************************************************************************************************** 

		saveBtn.setText(this.getMessage("button.save"));
		saveBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SAVE));
		saveBtn.setStyle("-fx-margin:100px");
		saveBtn.getStyleClass().setAll("btn", "btn-primary");

//*********************************storeLocation****************************************************************************************************** 

		storeLocation_CB = new <ComboBoxItem>JFXComboBox();
		productTyp_CB.getStyleClass().add("comboBox");

		storeLocation_CB.getStyleClass().add("comboBox");
		try {
			List products = this.getBaseService().findAllBeans(Product.class);
			for (Object p : products) {
				Product prod = (Product) p;
				if (prod.getId() == ProductTypeEnum.local_bannana || prod.getId() == ProductTypeEnum.imported)
					productTyp_CB.getItems().add(new ComboBoxItem(prod.getId(), prod.getName(), p));

			}
			productTyp_CB.getSelectionModel().selectFirst();
			productTyp_CB.setOnAction(e -> {

				String temp = (productTyp_CB.getSelectionModel().getSelectedItem()
						.getId() == ProductTypeEnum.local_bannana) ? getMessage("label.grossWeight")
								: getMessage("label.count") + "/" + getMessage("label.box.cartoon");
				grossWeight_Label.setText(temp);

			});
			// ==============================================================================================================================
			List stores = this.getBaseService().findAllBeans(Store.class);
			for (Object it : stores) {
				Store store = (Store) it;
				storeLocation_CB.getItems().add(new ComboBoxItem(store.getId(), String.valueOf(store.getId()), it));

			}

			storeLocation_CB.getSelectionModel().selectFirst();
			// ==============================================================================================================================

		} catch (DataBaseException | EmptyResultSetException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gridPane.setCenterShape(true);
		gridPane.setHgap(10);
		gridPane.setAlignment(gridPane.getAlignment().CENTER);

		gridPane.getChildren().removeAll();
		gridPane.getChildren().clear();
		int rowIndex = 0;
		int columnIndex = 0;

		gridPane.add(typeLabel, columnIndex, rowIndex);
		columnIndex++;
		gridPane.add(cutomerBox, columnIndex, rowIndex);
		columnIndex++;

		gridPane.add(nolun_Label, columnIndex, rowIndex);
		columnIndex++;
		gridPane.add(nolun_TF, columnIndex, rowIndex);
		columnIndex++;

		gridPane.add(gift_Label, columnIndex, rowIndex);
		columnIndex++;
		gridPane.add(gift_TF, columnIndex, rowIndex);
		columnIndex++;

//----------------------------
		columnIndex = 0;
		rowIndex++;
		// --------------------------

		gridPane.add(nameLabel, columnIndex, rowIndex);
		columnIndex++;
		gridPane.add(name_TF, columnIndex, rowIndex);
		columnIndex++;

		gridPane.add(grossWeight_Label, columnIndex, rowIndex);
		columnIndex++;
		gridPane.add(grossWeight_TF, columnIndex, rowIndex);
		columnIndex++;

		gridPane.add(code_Label, columnIndex, rowIndex);
		columnIndex++;
		gridPane.add(code_TF, columnIndex, rowIndex);
		columnIndex++;

//----------------------------
		columnIndex = 0;
		rowIndex++;
		// --------------------------

		gridPane.add(productType_Label, columnIndex, rowIndex);
		columnIndex++;
		gridPane.add(productTyp_CB, columnIndex, rowIndex);
		columnIndex++;

		gridPane.add(count_Label, columnIndex, rowIndex);
		columnIndex++;
		gridPane.add(count_TF, columnIndex, rowIndex);
		columnIndex++;

		gridPane.add(vehicelType_Label, columnIndex, rowIndex);
		columnIndex++;
		gridPane.add(vehicleType_CB, columnIndex, rowIndex);
		columnIndex++;

		// ----------------------------
		columnIndex = 0;
		rowIndex++;
		// --------------------------

		gridPane.add(date_Label, columnIndex, rowIndex);
		columnIndex++;
		gridPane.add(datePicker, columnIndex, rowIndex);
		columnIndex++;

		gridPane.add(storeId_Label, columnIndex, rowIndex);
		columnIndex++;
		gridPane.add(storeLocation_CB, columnIndex, rowIndex);
		columnIndex++;

		gridPane.add(notes_Label, columnIndex, rowIndex);
		columnIndex++;
		gridPane.add(notes_TA, columnIndex, rowIndex);
		columnIndex++;

//==================================================

		AnchorPane.setTopAnchor(gridPane, 0.0);
		AnchorPane.setLeftAnchor(gridPane, 0.0);
		AnchorPane.setBottomAnchor(gridPane, 0.0);

		inputForm_loc.getChildren().clear();
		inputForm_loc.getChildren().setAll(gridPane);
	}

	private Image loadImage(String path) {

		Image icn = null;

		try {
			File file = new File(getClass().getClassLoader().getResource(path).getFile());

			icn = new Image(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return icn;

	}

	boolean validateForm() {

		String customerName = name_TF.getText();
		String noloun = nolun_TF.getText();
		String tips = gift_TF.getText();
		if (customerName.isEmpty() || noloun.isEmpty() || tips.isEmpty()) {
			alert(AlertType.INFORMATION, "", "", this.getMessage("msg.err.required.values"));
			return false;
		}

		double d_tips = Double.parseDouble(tips);
		double nol = Double.parseDouble(noloun);
		if (!vallidateSafeWithdrawal(nol + d_tips)) {

			alert(AlertType.INFORMATION, "", "", this.getMessage("msg.err.notEnough.safeBalance"));

			return false;
		}

		return true;

	}

	private void alert(AlertType alertType, String title, String headerText, String message) {
		Alert a = new Alert(alertType);
		a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		a.setTitle(title);
		a.setHeaderText(headerText);
		a.setContentText(message);
		a.show();

	}

	private boolean vallidateSafeWithdrawal(double amount) {

		double balance=0.0;;
	 
			balance = this.getExpansesService().getSafeBalance(getAppContext().getSeason());
		 
		if (amount > balance) {

			return false;
		}
		return true;

	}

	private void editOrder() {

		if (validateForm()) {
			try {

				String customerName = name_TF.getText();
				CustomerType customerType = (CustomerType) cutomerBox.getSelectionModel().getSelectedItem().getValueObject();
				String addressValue = address_TF.getText();
				String phoneValue = phone_TF.getText();
				String tagValue = code_TF.getText();
				Product product = (Product) productTyp_CB.getSelectionModel().getSelectedItem().getValueObject();
				double weightValue = (grossWeight_TF.getText().isEmpty()) ? 0
						: Double.parseDouble(grossWeight_TF.getText());
				double noloun = Double.parseDouble(nolun_TF.getText());
				Store store =(Store) storeLocation_CB.getSelectionModel().getSelectedItem().getValueObject();
				double tips = Double.parseDouble(gift_TF.getText());
				VehicleType vechileType = (VehicleType)vehicleType_CB.getSelectionModel().getSelectedItem().getValueObject();
				String notesValue = notes_TA.getText();
				Date orderDate = getValueOfDatePicker();
				int unites = (count_TF.getText().isEmpty()) ? 0 : Integer.parseInt(count_TF.getText());

			
				
				CustomerOrder order = (CustomerOrder) this.getBaseService().getBean(CustomerOrder.class, this.orderId);
				
				Customer customer = order.getCustomer();
				customer.setName(customerName);
				customer.setType(customerType); 
				customer.setPhone(phoneValue);
				customer.setAddress(addressValue);
 		 		this.getBaseService().saveEntity(this.getAppContext().getRepoSupplier().getCustomerRepo(), customer);

				
				order.setProduct(product);
				order.setVehicleType(vechileType);
				order.setGrossweight(weightValue);
				order.setNolun(noloun);
				order.setTips(tips);
				order.setStore(store);
				order.setSeason(getAppContext().getSeason() );
				order.setFridage(getAppContext().getFridage() );
				order.setNotes(notesValue);
				order.setCustomer(customer);
				order.setFinished(0);
				order.setOrderDate(orderDate);
				order.setOrderDate(orderDate);
				order.setUnits(unites);
				order.setOrderTag(tagValue);

				this.getCustomerService().editCustomerOrder(order, oldOrder);
				alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.edit"));

				Stage stage = (Stage) saveBtn.getScene().getWindow();
				this.response = new HashMap<String, Object>();
				response.put("valid", true);

				stage.close();
			} catch (DataBaseException | InvalidReferenceException e) {
				alert(AlertType.ERROR, this.getMessage("msg.err"), this.getMessage("msg.err"),
						this.getMessage("msg.err.general"));
				e.printStackTrace();
			}
			// inatiatePage();
		}

	}

	private Date getValueOfDatePicker() {

		LocalDate localate = datePicker.getValue();
		Instant instant = Instant.from(localate.atStartOfDay(ZoneId.systemDefault()));

		return Date.from(instant);

	}

	public void inatiatePage() {

		this.name_TF.setText("");
		this.nolun_TF.setText("");
		this.notes_TA.setText("");
		this.grossWeight_TF.setText("");
		this.count_TF.setText("");
		this.gift_TF.setText("");

	}

	private void saveCustomerOrder() {

		if (validateForm()) {
			try {

				String customerName = name_TF.getText();
				CustomerType customerType = (CustomerType) cutomerBox.getSelectionModel().getSelectedItem().getValueObject();
				String addressValue = address_TF.getText();
				String phoneValue = phone_TF.getText();
				String tagValue = code_TF.getText();
				Product product = (Product) productTyp_CB.getSelectionModel().getSelectedItem().getValueObject();
				double weightValue = (grossWeight_TF.getText().isEmpty()) ? 0
						: Double.parseDouble(grossWeight_TF.getText());
				double noloun = Double.parseDouble(nolun_TF.getText());
				Store store =(Store) storeLocation_CB.getSelectionModel().getSelectedItem().getValueObject();
				double tips = Double.parseDouble(gift_TF.getText());
				VehicleType vechileType = (VehicleType)vehicleType_CB.getSelectionModel().getSelectedItem().getValueObject();
				String notesValue = notes_TA.getText();
				Date orderDate = getValueOfDatePicker();
				int unites = (count_TF.getText().isEmpty()) ? 0 : Integer.parseInt(count_TF.getText());

				Customer customer = new Customer();
				customer.setName(customerName);
				customer.setType(customerType);
				customer.setPhone(phoneValue);
				customer.setAddress(addressValue);

				CustomerOrder order = new CustomerOrder();
				order.setProduct(product);
				order.setVehicleType(vechileType);
				order.setGrossweight(weightValue);
				order.setNolun(noloun);
				order.setTips(tips);
				order.setStore(store);
				order.setSeason(getAppContext().getSeason() );
				order.setFridage(getAppContext().getFridage() );
				order.setNotes(notesValue);
				order.setCustomer(customer);
				order.setFinished(0);
				order.setOrderDate(orderDate);
				order.setOrderDate(orderDate);
				order.setUnits(unites);
				order.setOrderTag(tagValue);

				try {
					this.getCustomerService().saveCustomerOrder(order);
				} catch (InvalidReferenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				alert(AlertType.INFORMATION, "", "", this.getMessage("msg.done.save"));
//====================================================================================================================

				this.response = new HashMap<String, Object>();
				response.put("valid", true);
				close();
				inatiatePage();
			} catch (DataBaseException e) {
				alert(AlertType.ERROR, this.getMessage("msg.err"), this.getMessage("msg.err"),
						this.getMessage("msg.err.general"));
				e.printStackTrace();
			}
			// inatiatePage();
		}

	}

	private void close() {

		Stage stage = (Stage) saveBtn.getScene().getWindow();
		// do what you have to do
		stage.close();

	}
}
