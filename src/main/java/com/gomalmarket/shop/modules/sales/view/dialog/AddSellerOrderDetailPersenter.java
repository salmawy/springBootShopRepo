package com.gomalmarket.shop.modules.sales.view.dialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.glyphfont.FontAwesome;

import com.gomalmarket.shop.core.Enum.ProductTypeEnum;
import com.gomalmarket.shop.core.UIComponents.comboBox.ComboBoxItem;
import com.gomalmarket.shop.core.entities.basic.Product;
import com.gomalmarket.shop.core.entities.basic.Store;
import com.gomalmarket.shop.core.entities.customers.CustomerOrder;
import com.gomalmarket.shop.core.exception.DataBaseException;
import com.gomalmarket.shop.core.exception.EmptyResultSetException;
import com.gomalmarket.shop.core.exception.InvalidReferenceException;
import com.gomalmarket.shop.core.validator.Validator;
import com.gomalmarket.shop.modules.sales.action.SalesAction;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddSellerOrderDetailPersenter extends SalesAction implements Initializable {

	@FXML
	private VBox form_Box;

	@FXML
	private JFXComboBox<ComboBoxItem<Integer>> customer_cb;

	@FXML
	private JFXButton cancel_btn;

	@FXML
	private Label amountLabel;

	@FXML
	private HBox buttons_box;

	@FXML
	private HBox customer_box;

	@FXML
	private Label storeIdLabel;

	@FXML
	private Label netWeightLabel;

	@FXML
	private HBox unitePrice_box;

	@FXML
	private Label customerLabel;

	@FXML
	private Label grossWeightLabel;

	@FXML
	private Pane coloredPane;

	@FXML
	private JFXComboBox<ComboBoxItem<Integer>> productType;

	@FXML
	private Label title_label;

	@FXML
	private HBox productType_box;

	@FXML
	private JFXTextField amount;

	@FXML
	private JFXComboBox<ComboBoxItem<Integer>> store_cb;
	@FXML
	private AnchorPane rootPane;

	@FXML
	private JFXTextField count;

	@FXML
	private HBox boxCount_box;

	@FXML
	private Label productTypeLabel;

	@FXML
	private JFXTextField grossWeight;

	@FXML
	private JFXTextField netWeight;

	@FXML
	private HBox store_box;

	@FXML
	private JFXTextField unitePrice;

	@FXML
	private HBox grossWeight_box;

	@FXML
	private HBox netWeight_box;

	@FXML
	private Label boxCountLabel;

	@FXML
	private Label unitePriceLabel;

	@FXML
	private HBox amountbox;

	@FXML
	private JFXButton saveBtn;

	Validator myvaValidator;

	private JFXSnackbar snackBar;

	private Map<String, Double> orderWithDrawalQuantity;
	private Map<Integer, Double> OrderDetailCash;

	public AddSellerOrderDetailPersenter() {

		OrderDetailCash = (Map<Integer, Double>) this.request.get("OrderDetailCashKey");

	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		myvaValidator = new Validator();
		snackBar = new JFXSnackbar(rootPane);

		orderDataMap = new HashMap<>();
		orderWithDrawalQuantity = new HashMap<>();
		
		
		//--------------------------------------------------------------------------------------------------------------------------------------------
		title_label.setText(getMessage("sales.addweightDetail"));
		productTypeLabel.setText(this.getMessage("label.product"));
		storeIdLabel.setText(this.getMessage("label.store.name"));
		grossWeightLabel.setText(this.getMessage("label.grossWeight"));
		boxCountLabel.setText(this.getMessage("label.box"));
		unitePriceLabel.setText(this.getMessage("label.invoice.unitePrice"));
		netWeightLabel.setText(this.getMessage("label.netWeight"));
		customerLabel.setText(this.getMessage("label.customer"));
		amountLabel.setText(this.getMessage("label.money.amount"));
//--------------------------------------------------------------------------------------------------------------------------------------------
		grossWeight.textProperty().addListener((observable, oldValue, newValue) -> {
			myvaValidator = new Validator();
			if (newValue.length() > 0) {
				myvaValidator.getValidDouble(newValue, 0, Double.MAX_VALUE, "grossWeightValue", true);
				if (myvaValidator.noException()) {

					weightValueTracker();
				} else {

					newValue = oldValue;
					grossWeight.setText(newValue);

				}

			}

		});
		//--------------------------------------------------------------------------------------------------------------------------------------------
		unitePrice.textProperty().addListener((observable, oldValue, newValue) -> {

			myvaValidator = new Validator();
			if (newValue.length() > 0) {
				myvaValidator.getValidDouble(newValue, 0, Integer.MAX_VALUE, "unitePriceValue", true);
				if (myvaValidator.noException()) {

					costValueTracker();

				}

				else {
					newValue = oldValue;
					unitePrice.setText(newValue);
				}

			}

		});
		//--------------------------------------------------------------------------------------------------------------------------------------------
		count.textProperty().addListener((observable, oldValue, newValue) -> {
			myvaValidator = new Validator();
			if (newValue.length() > 0) {
				myvaValidator.getValidInt(newValue, 0, Integer.MAX_VALUE, "countValue", true);
				if (myvaValidator.noException()) {

					packageNumberTracker();

				}

				else {
					newValue = oldValue;
					count.setText(newValue);
				}

			}
		});
		//--------------------------------------------------------------------------------------------------------------------------------------------
		productType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, final Number oldvalue, final Number newvalue) {
				clearInputForm();
				ComboBoxItem<Integer> item = productType.getSelectionModel().getSelectedItem();
				fillInputForm(item.getId());
				loadCustomerOrders();

			}
		});

 
		store_cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, final Number oldvalue, final Number newvalue) {

				loadCustomerOrders();

				System.out.print("product type selected");

			}
		});
		try {
			List products = this.getBaseService().findAllBeans(Product.class);
			for (Object p : products) {
				Product prod = (Product) p;
				productType.getItems().add(new ComboBoxItem(prod.getId(), prod.getName()));

			}

			productType.getSelectionModel().selectFirst();

			List stores = this.getBaseService().findAllBeans(Store.class);
			for (Object it : stores) {
				Store store = (Store) it;
				store_cb.getItems().add(new ComboBoxItem<Integer>(store.getId(), String.valueOf(store.getId())));

			}

			store_cb.getSelectionModel().selectFirst();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// ----------------------------------------------------------------------

		saveBtn.setText(this.getMessage("button.save"));
		saveBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.SAVE));
		saveBtn.getStyleClass().setAll("btn", "btn-primary", "btn-sm");

		cancel_btn.setText(this.getMessage("button.cancel"));
		cancel_btn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.CLOSE));
		cancel_btn.getStyleClass().setAll("btn", "btn-primary", "btn-sm");
		cancel_btn.setOnMouseClicked((new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("add has been clicked");
				orderDataMap.put("save", false);

				Stage stage = (Stage) cancel_btn.getScene().getWindow();
				// do what you have to do
				stage.close();
			}
		}));

		loadCustomerOrders();
	}

	private void fillInputForm(int mode) {
		form_Box.getChildren().clear();

		boxCountLabel.setText(getMessage("label.box"));
		form_Box.getChildren().add(productType_box);
		switch (mode) {

		case 1:
			
			form_Box.getChildren().add(store_box);
			form_Box.getChildren().add(grossWeight_box);
			form_Box.getChildren().add(boxCount_box);
			form_Box.getChildren().add(unitePrice_box);
			form_Box.getChildren().add(netWeight_box);
			form_Box.getChildren().add(amountbox);
			form_Box.getChildren().add(customer_box);

			break;
		case 2:

			boxCountLabel.setText(getMessage("label.box.cartoon"));

			form_Box.getChildren().add(store_box);
			form_Box.getChildren().add(boxCount_box);
			form_Box.getChildren().add(unitePrice_box);
			form_Box.getChildren().add(amountbox);
			form_Box.getChildren().add(customer_box);

			break;
		case 3:

			form_Box.getChildren().add(boxCount_box);
			form_Box.getChildren().add(unitePrice_box);
			form_Box.getChildren().add(amountbox);

			break;
		case 4:

			form_Box.getChildren().add(grossWeight_box);
			form_Box.getChildren().add(boxCount_box);
			form_Box.getChildren().add(unitePrice_box);
			form_Box.getChildren().add(netWeight_box);
			form_Box.getChildren().add(amountbox);

			break;

		default:
			break;
		}
		form_Box.getChildren().add(buttons_box);
	}

	private void clearInputForm() {

		this.amount.setText("");
		this.count.setText("");
		this.unitePrice.setText("");
		this.netWeight.setText("");
		this.grossWeight.setText("");
		this.amount.setText("");

	}

	private void fitToAnchorePane(Node node) {

		AnchorPane.setTopAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setBottomAnchor(node, 0.0);

	}

	@FXML
	private void saveData() {

		int productId = productType.getSelectionModel().getSelectedItem().getId();
		String productName = productType.getSelectionModel().getSelectedItem().getText();
		if (validateForm()) {
			String weight = grossWeight.getText();
			String package_number = (count.getText().length() == 0) ? "0" : count.getText();
			String netWeight = this.netWeight.getText();
			String UnitePrice = unitePrice.getText();
			int orderId = 0;// customerNames_CB.getSelectedItem().toString();
			String totalCost = amount.getText();
			totalCost = String.valueOf(Math.rint(Double.parseDouble(totalCost)));
			String customerOrderName = "";
			switch (productId) {

			case 1:
				orderId = customer_cb.getSelectionModel().getSelectedItem().getId();
				customerOrderName = customer_cb.getSelectionModel().getSelectedItem().getText();
				addOrderWithDrawalQuantity(String.valueOf(orderId), Double.parseDouble(weight));
				break;

			case 2:
				weight = String.valueOf("0.0");
				netWeight = "0.0";
				orderId = customer_cb.getSelectionModel().getSelectedItem().getId();
				customerOrderName = customer_cb.getSelectionModel().getSelectedItem().getText();

				addOrderWithDrawalQuantity(String.valueOf(orderId), Double.parseDouble(package_number));

				break;
			case 3:
				weight = String.valueOf("0.0");
				netWeight = "0.0";
				orderId = 0;

				break;
			case 4:
				orderId = 0;

				break;

			}

			orderDataMap.put("productId", String.valueOf(productId));
			orderDataMap.put("productName", productName);

			orderDataMap.put("grossWeight", weight);
			orderDataMap.put("count", package_number);
			orderDataMap.put("netWeight", netWeight);
			orderDataMap.put("unitePrice", UnitePrice);
			orderDataMap.put("customerOrderId", String.valueOf(orderId));
			orderDataMap.put("amount", totalCost);
			orderDataMap.put("customerOrderName", customerOrderName);
			orderDataMap.put("save", true);

			// this.setOrderDataMap(orderDataMap);
			Stage stage = (Stage) cancel_btn.getScene().getWindow();
			// do what you have to do
			stage.close();
		}

	}

	private void weightValueTracker() {

		if (!grossWeight.getText().isEmpty()) {
			double weight = Double.parseDouble(grossWeight.getText());

			if (weight != 0) {
				int index = productType.getSelectionModel().getSelectedItem().getId();
				double commission = 0.0;
				switch (index) {
				case 1:
					commission = .935;
					netWeight.setText(String.valueOf(Math.rint(weight * commission)));
					break;
				case 2:
					commission = 0;
					netWeight.setText(String.valueOf(Math.rint(weight * 1)));
					break;
				case 3:
					netWeight.setText(String.valueOf(Math.rint(weight * 1)));
					break;

				}

			}
		}

	}

	private void costValueTracker() {

		int index = productType.getSelectionModel().getSelectedItem().getId();
		switch (index) {

		case 1:
			if (!netWeight.getText().isEmpty() && !unitePrice.getText().isEmpty()) {
				double netWeightValue = Double.parseDouble(netWeight.getText());
				double unitePrice = Double.parseDouble(this.unitePrice.getText());

				if (netWeightValue != 0 && unitePrice != 0) {

					double x = netWeightValue * unitePrice;
					x = Math.rint(x);
					this.amount.setText(String.valueOf(x));

				}
			}

			break;
		case 2:
			if (!count.getText().isEmpty() && !unitePrice.getText().isEmpty()) {
				double number = Double.parseDouble(count.getText());
				double unitePrice = Double.parseDouble(this.unitePrice.getText());

				if (number != 0 && unitePrice != 0) {

					double x = number * unitePrice;
					amount.setText(String.valueOf(x));

				}
			}
			break;
		case 3:
			if (!count.getText().isEmpty() && !unitePrice.getText().isEmpty()) {
				double number = Double.parseDouble(count.getText());
				double unitePrice = Double.parseDouble(this.unitePrice.getText());

				if (number != 0 && unitePrice != 0) {

					double x = number * unitePrice;
					amount.setText(String.valueOf(x));

				}
			}
			break;

		case 4:
			if (!netWeight.getText().isEmpty() && !unitePrice.getText().isEmpty()) {
				double netWeight = Double.parseDouble(this.netWeight.getText());
				double unitePrice = Double.parseDouble(this.unitePrice.getText());

				if (netWeight != 0 && unitePrice != 0) {

					double x = netWeight * unitePrice;
					amount.setText(String.valueOf(x));

				}
			}

			break;

		}

	}

	private void packageNumberTracker() {

		if (productType.getSelectionModel().getSelectedItem().getId() == 3) {
			String weight = grossWeight.getText();
			String number = count.getText();
			if (!weight.isEmpty() && !number.isEmpty()) {

				int packageNumber = Integer.parseInt(count.getText());
				int x = packageNumber * 3;
				int gross_weight = Integer.parseInt(grossWeight.getText());
				int netWeight = gross_weight - x;
				this.netWeight.setText(String.valueOf(Math.rint(netWeight)));
			}

		}
		// imported banana
		if (productType.getSelectionModel().getSelectedItem().getId() == 2) {
			String unitePriceValue = unitePrice.getText();
			String number = count.getText();
			if (!unitePriceValue.isEmpty() && !number.isEmpty()) {

				int packageNumber = Integer.parseInt(count.getText());
				int totalAmount = Integer.parseInt(unitePriceValue) * packageNumber;
				this.amount.setText(String.valueOf(totalAmount));
			}

		}
	}

	private void loadCustomerOrders() {

		customer_cb.getItems().clear();
		if (productType.getItems().size() > 0 && store_cb.getItems().size() > 0) {

			int productId = productType.getSelectionModel().getSelectedItem().getId();

			int storeId = store_cb.getSelectionModel().getSelectedItem().getId();

			List result = this.getSalesService().getAllCustomersOrdersTags(getAppContext().getSeason().getId(),
					this.getAppContext().getFridage().getId(), productId, storeId);
			List data = new ArrayList();
			customer_cb.getItems().clear();
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				CustomerOrder order = (CustomerOrder) iterator.next();
				ComboBoxItem item = new ComboBoxItem(order.getId(), order.getOrderTag());
				customer_cb.getItems().add(item);

			}
			customer_cb.getSelectionModel().selectFirst();
		}

	}

	boolean validateForm() {

		String weight = grossWeight.getText();
		String packageNumber = count.getText();
		String UnitePrice = unitePrice.getText();

		int customerId = customer_cb.getSelectionModel().getSelectedItem().getId();
		double cashedQuantity = (OrderDetailCash != null && OrderDetailCash.get(customerId) != null)
				? OrderDetailCash.get(customerId)
				: 0.0;
		int productId = productType.getSelectionModel().getSelectedItem().getId();

		switch (productId) {
		case 1:

			int orderId = ((customer_cb.getSelectionModel().getSelectedItem()) == null) ? 0
					: customer_cb.getSelectionModel().getSelectedItem().getId();
			if (orderId == 0) {
				snackBar.show(this.getMessage("msg.err.cutomerOrder"), 1000);

				return false;

			}

			else if (weight.isEmpty()) {
				snackBar.show(this.getMessage("msg.err.required.grossWeight"), 1000);
				return false;
			} else if (UnitePrice.isEmpty()) {
				snackBar.show(this.getMessage("msg.err.required.unitePrice"), 1000);

				return false;

			}

			else if (productId == ProductTypeEnum.local_bannana
					&& !confirmWeight(orderId, Double.parseDouble(weight) + cashedQuantity, productId)) {
				snackBar.show(this.getMessage("msg.err.notEnough.weight"), 1000);

				return false;
			}

			break;
		case 2:
			orderId = ((customer_cb.getSelectionModel().getSelectedItem()) == null) ? 0
					: customer_cb.getSelectionModel().getSelectedItem().getId();
			if (orderId == 0) {
				snackBar.show(this.getMessage("msg.err.cutomerOrder"), 1000);

				return false;

			}

			if (packageNumber.isEmpty()) {

				snackBar.show(this.getMessage("msg.err.required.count"), 1000);
				return false;

			}
			if (UnitePrice.isEmpty()) {

				snackBar.show(this.getMessage("msg.err.required.unitePrice"), 1000);

				return false;

			}

			else if (!confirmWeight(orderId, Double.parseDouble(packageNumber) + cashedQuantity, productId)) {
				snackBar.show(this.getMessage("msg.err.notEnough.count"), 1000);

				return false;
			}

			break;
		case 3:

			if (packageNumber.isEmpty()) {
				snackBar.show(this.getMessage("msg.err.required.count"), 1000);

				return false;

			} else if (UnitePrice.isEmpty()) {

				snackBar.show(this.getMessage("msg.err.required.unitePrice"), 1000);
				return false;

			}
			break;
		case 4:

			if (weight.isEmpty()) {

				snackBar.show(this.getMessage("msg.err.required.grossWeight"), 1000);
				return false;
			}
			if (packageNumber.isEmpty()) {
				snackBar.show(this.getMessage("msg.err.required.count"), 1000);

				return false;

			}
			if (UnitePrice.isEmpty()) {

				snackBar.show(this.getMessage("msg.err.required.unitePrice"), 1000);

				return false;

			}
			break;
		default:
			break;

		}

		return true;

	}

	private boolean confirmWeight(int orderId, double quantity, int productId) {

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("customerOrderId =", orderId);
		String columnName = (productId == ProductTypeEnum.local_bannana) ? "grossQuantity" : "packageNumber";
		try {
			CustomerOrder order = (CustomerOrder) this.getBaseService().findBean(CustomerOrder.class, orderId);

			try {
				Object tmp = this.getSalesService().aggregate("SellerOrderWeight", "sum", columnName, m);
				double withDrawaled = Double.parseDouble((tmp == null) ? "0.0" : String.valueOf(tmp));

				double avaliableQuantity = (order.getGrossweight() - withDrawaled);
				return (quantity <= avaliableQuantity);
			} catch (EmptyResultSetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (DataBaseException | InvalidReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	void addOrderWithDrawalQuantity(String name, Double quantity) {

		if (orderWithDrawalQuantity.containsKey(name)) {

			Double tempQuantity = orderWithDrawalQuantity.get(name);
			tempQuantity += quantity;
			orderWithDrawalQuantity.put(name, tempQuantity);

		} else {
			orderWithDrawalQuantity.put(name, quantity);

		}

	}
}
