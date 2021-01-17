package com.gomalmarket.shop.modules.billing.view.invoicePayment;

import com.gomalmarket.shop.modules.billing.action.BillingAction;

public class InvoicePaymentPersenter extends BillingAction //implements Initializable, CustomTableActions 
{
	/*
	 * 
	 * @FXML private JFXButton suggestedInvoices_btn;
	 * 
	 * @FXML private AnchorPane invoicesTable_loc;
	 * 
	 * @FXML private JFXComboBox<ComboBoxItem> customerType_combo;
	 * 
	 * @FXML private AnchorPane customersTable_loc;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * private CustomTable<CustomerNameViewBean> customersCustomeTable; private
	 * CustomTable<InvoiceViewbean> invoiceCustomeTable;
	 * 
	 * 
	 * 
	 * 
	 * @Override public void initialize(URL arg0, ResourceBundle arg1) { // TODO
	 * Auto-generated method stub init(); }
	 * 
	 * 
	 * 
	 * private void init() {
	 * 
	 * 
	 * 
	 * 
	 * customerType_combo.setPromptText(this.getMessage("label.customer.Type"));
	 * customerType_combo.getStyleClass().add("comboBox");
	 * 
	 * customerType_combo.getItems().add(new
	 * ComboBoxItem(CustomerTypeEnum.kareem,this.getMessage("customer.type.karrem"))
	 * ); customerType_combo.getItems().add(new
	 * ComboBoxItem(CustomerTypeEnum.mahmed,this.getMessage("customer.type.mahmed"))
	 * ); customerType_combo.getItems().add(new
	 * ComboBoxItem(CustomerTypeEnum.normal,this.getMessage("customer.type.normal"))
	 * ); customerType_combo.getItems().add(new
	 * ComboBoxItem(CustomerTypeEnum.purchases,this.getMessage(
	 * "customer.type.purchaes")));
	 * customerType_combo.getSelectionModel().select(0);
	 * 
	 * customerType_combo.setOnAction(e -> {
	 * loadsuggestedCustomers(customerType_combo.getSelectionModel().getSelectedItem
	 * ().getValue());
	 * 
	 * 
	 * 
	 * 
	 * });
	 * //===========================================================================
	 * ==================================================================
	 * List<Column>customersColumns=prepareCustomerTabelColumns();
	 * List<Column>invoiceColumns=prepareInvoiceTabelColumns(); List
	 * invoiceTableControl=prepareInvoiceControles(); invoiceCustomeTable=new
	 * CustomTable<InvoiceViewbean>(invoiceColumns,invoiceTableControl,null,null,
	 * null,CustomTable.headTableCard,InvoiceViewbean.class);
	 * customersCustomeTable=new
	 * CustomTable<CustomerNameViewBean>(customersColumns,null,null,null,this,
	 * CustomTable.tableCard,CustomerNameViewBean.class);
	 * 
	 * AnchorPane customersTable=customersCustomeTable.getCutomTableComponent();
	 * AnchorPane invoiceTable=invoiceCustomeTable.getCutomTableComponent();
	 * customersTable.setPrefSize(100, 500);
	 * 
	 * fitToAnchorePane(customersTable); fitToAnchorePane(invoiceTable);
	 * 
	 * customersTable_loc.getChildren().addAll(customersTable);
	 * invoicesTable_loc.getChildren().addAll(invoiceTable);
	 * //===========================================================================
	 * ==================================================================
	 * suggestedInvoices_btn.setText(getMessage("button.invoice.suggestedInvoices"))
	 * ; suggestedInvoices_btn.setGraphic(new
	 * FontAwesome().create(FontAwesome.Glyph.ARROW_RIGHT));
	 * suggestedInvoices_btn.getStyleClass().setAll("btn","btn-sm","btn-info");
	 * 
	 * suggestedInvoices_btn.setOnAction(e -> {
	 * 
	 * loadSuggestedInvoices();
	 * 
	 * 
	 * });
	 * 
	 * //===========================================================================
	 * ==================================================================
	 * loadSuggestedInvoices();
	 * loadsuggestedCustomers(customerType_combo.getSelectionModel().getSelectedItem
	 * ().getValue()); }
	 * 
	 * 
	 * @SuppressWarnings("unchecked") private void loadsuggestedCustomers(int
	 * typeId) {
	 * 
	 * List customerViewBeans=new ArrayList<>(); List result=null; try { result =
	 * this.getBillingService().getSuggestedCustomersOrders(0, 0,
	 * ApplicationContext.season.getId(), ApplicationContext.fridage.getId(),
	 * typeId);
	 * 
	 * 
	 * for (Object it : result) { Customer customer=(Customer) it;
	 * CustomerNameViewBean viewBean=new
	 * CustomerNameViewBean(customer.getId(),customer.getName(),customer.getName());
	 * customerViewBeans.add(viewBean);
	 * 
	 * 
	 * }
	 * 
	 * 
	 * customersCustomeTable.loadTableData(customerViewBeans);
	 * 
	 * 
	 * } catch (EmptyResultSetException e) { // TODO Auto-generated catch block
	 * 
	 * alert(AlertType.WARNING, "", "", this.getMessage("msg.warning.noData"));
	 * 
	 * customersCustomeTable.getTable().getItems().clear(); }
	 * 
	 * catch ( DataBaseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); alert(AlertType.ERROR,
	 * this.getMessage("msg.err"),this.getMessage("msg.err"),
	 * this.getMessage("msg.err.general"));
	 * customersCustomeTable.getTable().getItems().clear();
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * private void alert(AlertType alertType,String title,String headerText,String
	 * message) { Alert a = new Alert(alertType);
	 * a.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	 * a.setTitle(title ); a.setHeaderText(headerText); a.setContentText(message);
	 * a.show();
	 * 
	 * }
	 * 
	 * private void loadSuggestedInvoices() {
	 * 
	 * 
	 * SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
	 * 
	 * List invoices=new ArrayList<>(); List<InvoiceViewbean> invoicsViewBeans=new
	 * ArrayList(); try { // public List getSuggestedOrders(int customerId,int
	 * finished, int dued, int seasonId,int typeId,int fridageId) throws
	 * DataBaseException, EmptyResultSetException{
	 * 
	 * invoices = this.getBillingService().getSuggestedOrders(0,1, 0,
	 * ApplicationContext.season.getId(), 0, ApplicationContext.fridage.getId());
	 * 
	 * 
	 * 
	 * for (Object it : invoices) { CustomerOrder order=(CustomerOrder) it;
	 * InvoiceViewbean viewBean=new InvoiceViewbean();
	 * viewBean.setId(order.getId());
	 * viewBean.setProductName(order.getProduct().getName());
	 * viewBean.setGrossWeight(order.getGrossweight());
	 * viewBean.setNetWeight(order.getNetWeight());
	 * viewBean.setNolun(order.getNolun()); viewBean.setTips(order.getNolun());
	 * viewBean.setOrderTag(order.getOrderTag()); invoicsViewBeans.add(viewBean);
	 * 
	 * 
	 * }
	 * 
	 * invoiceCustomeTable.loadTableData(invoicsViewBeans);
	 * 
	 * } catch ( EmptyResultSetException e) { // TODO Auto-generated catch block
	 * //alert(AlertType.WARNING, "", "", getMessage("msg.warning.noData"));
	 * 
	 * invoiceCustomeTable.getTable().getItems().clear() ; } catch
	 * (DataBaseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); alert(AlertType.ERROR,
	 * this.getMessage("msg.err"),this.getMessage("msg.err"),
	 * this.getMessage("msg.err.general"));
	 * invoiceCustomeTable.getTable().getItems().clear();
	 * 
	 * }
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * private void loadSuggestedInvoices(int customerId) {
	 * 
	 * 
	 * SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
	 * 
	 * List invoices=new ArrayList<>(); List<InvoiceViewbean> invoicsViewBeans=new
	 * ArrayList(); try {
	 * 
	 * invoices = this.getBillingService().getSuggestedOrders(customerId,1, 0,
	 * ApplicationContext.season.getId(), 0, ApplicationContext.fridage.getId());
	 * 
	 * 
	 * 
	 * for (Object it : invoices) { CustomerOrder order=(CustomerOrder) it;
	 * InvoiceViewbean viewBean=new InvoiceViewbean();
	 * viewBean.setId(order.getId()); //
	 * viewBean.setInvoiceDate(sdf.format(order.getDueDate()));
	 * viewBean.setProductName(order.getProduct().getName());
	 * viewBean.setGrossWeight(order.getGrossweight());
	 * viewBean.setNetWeight(order.getNetWeight());
	 * viewBean.setNolun(order.getNolun()); //
	 * viewBean.setTotalAmount(order.getTotalPrice());
	 * viewBean.setTips(order.getNolun()); //
	 * viewBean.setCommision(order.getCommision()); //
	 * viewBean.setNetAmount(order.getNetPrice());
	 * viewBean.setOrderTag(order.getOrderTag()); invoicsViewBeans.add(viewBean);
	 * 
	 * 
	 * }
	 * 
	 * invoiceCustomeTable.loadTableData(invoicsViewBeans);
	 * 
	 * } catch ( EmptyResultSetException e) { // TODO Auto-generated catch block
	 * alert(AlertType.WARNING, "", "", this.getMessage("msg.warning.noData"));
	 * 
	 * invoiceCustomeTable.getTable().getItems().clear() ; } catch
	 * (DataBaseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); alert(AlertType.ERROR,
	 * this.getMessage("msg.err"),this.getMessage("msg.err"),
	 * this.getMessage("msg.err.general"));
	 * invoiceCustomeTable.getTable().getItems().clear();
	 * 
	 * }
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * private List prepareInvoiceControles(){
	 * 
	 * 
	 * JFXButton addBtn=new JFXButton(this.getMessage("button.invoice.generate"));
	 * addBtn.setGraphic(new FontAwesome().create(FontAwesome.Glyph.PLUS));
	 * addBtn.getStyleClass().setAll("btn","btn-info","btn-sm"); //(2)
	 * addBtn.setOnMouseClicked((new EventHandler<MouseEvent>() { public void
	 * handle(MouseEvent event) { System.out.println("add has been clicked");
	 * initGenerateInvoice();
	 * 
	 * 
	 * } }));
	 * 
	 * 
	 * 
	 * 
	 * List buttons =new ArrayList<JFXButton>(Arrays.asList(addBtn)) ;
	 * 
	 * return buttons;
	 * 
	 * }
	 * 
	 * 
	 * protected void initGenerateInvoice() {
	 * 
	 * 
	 * InvoiceViewbean item=(InvoiceViewbean)
	 * this.invoiceCustomeTable.getTable().getSelectionModel().getSelectedItem();
	 * 
	 * this.request=new HashMap<String,Object>(); request.put("invoiceId",
	 * item.getId()); request.put("typeId",
	 * customerType_combo.getSelectionModel().getSelectedItem().getValue());
	 * request.put("action", 1);
	 * 
	 * InvoiceView form=new InvoiceView(); URL u=
	 * getClass().getClassLoader().getResource("appResources/custom.css");
	 * 
	 * Scene scene1= new Scene(form.getView(), 850, 600); Stage popupwindow=new
	 * Stage(); popupwindow.setMinHeight(400); popupwindow.setMinWidth(900);
	 * 
	 * popupwindow.setResizable(true); String css =u.toExternalForm();
	 * scene1.getStylesheets().addAll(css);
	 * popupwindow.initModality(Modality.APPLICATION_MODAL);
	 * popupwindow.setTitle(this.getMessage("button.invoice.generate"));
	 * 
	 * popupwindow.setScene(scene1); popupwindow.setOnHiding( ev -> {
	 * 
	 * 
	 * System.out.println("window closes"); //boolean valid=(boolean)
	 * this.response.get("valid");
	 * 
	 * //if(valid) //loadData(fromLocalDateToDate(bookDatePicker.getValue()));
	 * 
	 * 
	 * 
	 * });
	 * 
	 * popupwindow.showAndWait();
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * private List<Column> prepareCustomerTabelColumns(){
	 * 
	 * List<Column> columns=new ArrayList<Column>();
	 * 
	 * Column c1=new Column("id", "id", "int", 0, false); columns.add(c1); Column
	 * c2=new Column(this.getMessage("customer.name"), "name", "string", 100, true);
	 * columns.add(c2);
	 * 
	 * 
	 * 
	 * return columns;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * private void fitToAnchorePane(Node node) {
	 * 
	 * 
	 * AnchorPane.setTopAnchor(node, 0.0); AnchorPane.setLeftAnchor(node, 0.0);
	 * AnchorPane.setRightAnchor(node, 0.0); AnchorPane.setBottomAnchor(node, 0.0);
	 * 
	 * 
	 * 
	 * } private List<Column> prepareInvoiceTabelColumns(){
	 * 
	 * 
	 * List<Column> columns=new ArrayList<Column>();
	 * 
	 * Column c=new Column(this.getMessage("invoice.No"), "id", "int", 20, true);
	 * columns.add(c);
	 * 
	 * c=new Column(this.getMessage("label.invoice.tag"), "orderTag", "double", 65,
	 * true); columns.add(c);
	 * 
	 * c=new Column(this.getMessage("label.grossWeight"), "grossWeight", "double",
	 * 15, true); columns.add(c);
	 * 
	 * 
	 * 
	 * return columns;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @Override public void rowSelected() { CustomerNameViewBean
	 * item=(CustomerNameViewBean)
	 * customersCustomeTable.getTable().getSelectionModel().getSelectedItem();
	 * 
	 * loadSuggestedInvoices(item.getId()); }
	 * 
	 * 
	 * 
	 * @Override public void rowSelected(Object o) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 * 
	 * 
	 * 
	 */}
