/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gomalmarket.shop.core.UIComponents.customTable;

import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


/**
 *
 * @author salmawy
 * 
 *         cutsome tabel is not controller so i can send parameters to it
 */
public class CustomTable<RowClass>  {

	private FlowPane head;
	private AnchorPane tableContainer;
	
	
	private AnchorPane tablePane;
	private AnchorPane headTablePane;

	private AnchorPane cutomTableComponent;
	
	
	
	
	
	
	private TableView<RowClass> table;
	private CheckBox selectAllCheckBox;
	private Class myclass;
	private final String actionsPanelId = "actionsPanel";
	private final String confirmActionPanelId = "confirmActionPanel";
	private final String tableContainerId = "tableContainer";
	public static final int headTableCard=1;
	public static final int tableCard=2;
	private int activeCard;
	private CustomTableActions actions;
	public CustomTable(List columns, List nodes, List keyColumns, List<RowClass> data, CustomTableActions actions,int cardType,Class<?> beanClass) {
		this.activeCard=cardType;
	    this.actions=actions;
	  
	    
		    CustomeTableView tableView=new CustomeTableView();
			this.cutomTableComponent=(AnchorPane) tableView.getView();
			
			tablePane=(AnchorPane) cutomTableComponent.getChildren().get(0);
			headTablePane=(AnchorPane) cutomTableComponent.getChildren().get(1);
			
			switchCards();
		
			
			
			this.myclass=beanClass;
		    table = new <RowClass>TableView();
		    
		  
			
		setColumnsConfiguration(columns);
		
		setButtonsConfiguration(nodes);
		  if(actions!=null)
		    	setTableActionListner();
		if(data!=null&&data.size()>0) {
			
			
			loadTableData(data);
			
		}
			

	
	System.out.print("custom table has been loaded succeffuly ");
	
	}


private void setTableActionListner(){
	
	
	
	table.setOnMouseClicked(new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {

			actions.rowSelected();
		}
		
		
	});
	
	
	
	
	
	
}
	private void setColumnsConfiguration(List columns) {

	

		for (int i = 0; i <columns.size(); i++) {

			Column column = (Column) columns.get(i);
			
			if(column.getType().equals("chk")){
				
				TableColumn<RowClass, Boolean> selectedCol = new TableColumn<RowClass, Boolean>();
				selectedCol.setMinWidth(50);
				selectedCol.setGraphic(getSelectAllCheckBox());
				selectedCol.setCellValueFactory(new PropertyValueFactory<RowClass, Boolean>(column.getId()));
				selectedCol.setCellFactory(new Callback<TableColumn<RowClass, Boolean>, TableCell<RowClass, Boolean>>() {
					public TableCell<RowClass, Boolean> call(TableColumn<RowClass, Boolean> p) {
						final TableCell<RowClass, Boolean> cell = new TableCell<RowClass, Boolean>() {
							@Override
							public void updateItem(final Boolean item, boolean empty) {
								if (item == null)
									return;
								super.updateItem(item, empty);
								if (!isEmpty()) {
									final RowClass item1 = getTableView().getItems().get(getIndex());
									CheckBox checkBox = new CheckBox();
									
								
									BooleanProperty mychk=(BooleanProperty) invokeMethode(item1, "chkProperty");
									
									checkBox.selectedProperty().bindBidirectional(mychk);
									setGraphic(checkBox);
								}
							}
						};
						cell.setAlignment(Pos.CENTER);
						return cell;
					}
				});
				
				
						 
				 System.out.println("==============================="+table.widthProperty());
				selectedCol.prefWidthProperty().bind(table.widthProperty().multiply(column.getSize() / 100.0).subtract(((table.getInsets().getLeft() + table.getInsets().getRight()) / columns.size())));	
			
		
				table.getColumns().add(i, selectedCol);
			}
			
			else {
			TableColumn col = new TableColumn(column.getName());
			 col.setPrefWidth(column.getSize());
			 col.setVisible(column.getShow());
			 col.setEditable(column.isEditable());		 
			col.setId(column.getId());
			 col.prefWidthProperty().bind(table.widthProperty().multiply(column.getSize() / 100.0).subtract(((table.getInsets().getLeft() + table.getInsets().getRight()) / columns.size())));
		
			 
		
			 
			 col.setCellValueFactory(new PropertyValueFactory<RowClass, String>(column.getId()));
			table.getColumns().add(i, col);
			}

		}

		
	table.setNodeOrientation(table.getNodeOrientation().RIGHT_TO_LEFT) ;

		/*
		 * AnchorPane.setTopAnchor(table, 0.0); AnchorPane.setLeftAnchor(table, 0.0);
		 * AnchorPane.setRightAnchor(table, 0.0); AnchorPane.setBottomAnchor(table,
		 * 0.0);
		 */

	  
	  table.prefHeightProperty().bind( tableContainer.heightProperty());
      table.prefWidthProperty().bind ( tableContainer.widthProperty());
      
 
	
		tableContainer.getChildren().clear();
		tableContainer.getChildren().addAll(table);

	}

	private void setButtonsConfiguration(List nodes) {

		
		if (nodes==null ||nodes.size()>0)

		if (nodes!=null &&nodes.size()>0)
		for (int i = 0; i < nodes.size(); i++) {
			Node btn=(Node) nodes.get(i);
			this.head.getChildren().add( btn );

		}

	}

	public void loadTableData(List<RowClass> data) {
		
		table.getItems().clear();

		ObservableList<RowClass> observableList = FXCollections.observableList(data);
		
		this.table.setItems(observableList);

	}

	/**
	 * @return the actionsPanel
	 */
	public FlowPane getActionsPanel() {
		return head;
	}

	/**
	 * @param actionsPanel the actionsPanel to set
	 */
	public void setActionsPanel(FlowPane actionsPanel) {
		this.head = actionsPanel;
	}

	/*    *//**
			 * @return the tableContainer
			 *//*
				 * public JFXScrollPane getTableContainer() { return tableContainer; }
				 */

	/**
	 * @param tableContainer the tableContainer to set
	 */
	public void setTableContainer(AnchorPane tableContainer) {
		this.tableContainer = tableContainer;
	}

	/**
	 * @return the table
	 */
	public TableView getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */

	public AnchorPane getCutomTableComponent() {
		return cutomTableComponent;
	}

	public void setCutomTableComponent(AnchorPane cutomTableComponent) {
		this.cutomTableComponent = cutomTableComponent;
	}

	public String getActionsPanelId() {
		return actionsPanelId;
	}

	public String getConfirmActionPanelId() {
		return confirmActionPanelId;
	}

	public String getTableContainerId() {
		return tableContainerId;
	}

	public void setTable(TableView<RowClass> table) {
		this.table = table;
	}
	
	
	/**
	 * Lazy getter for the selectAllCheckBox.
	 * 
	 * @return selectAllCheckBox
	 */
	public CheckBox getSelectAllCheckBox() {
		if (selectAllCheckBox == null) {
			final CheckBox selectAllCheckBox = new CheckBox();

			// Adding EventHandler to the CheckBox to select/deselect all employees in table.
			selectAllCheckBox.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// Setting the value in all the employees.
					for (RowClass item : table.getItems()) {
					invokeMethode(item, "setChk",selectAllCheckBox.isSelected());	
					}
				}
			});

			this.selectAllCheckBox = selectAllCheckBox;
		}
		return selectAllCheckBox;
	}
	

	private Object invokeMethode(Object instance,String methodeName) {
		Object returnObj=null;
		try {
		Method methode= myclass.getMethod(methodeName);
		returnObj=	methode.invoke(instance);
		
		
		
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
			
		
		return returnObj;

		
		
		
	}
	private Object invokeMethode(Object instance,String methodeName,Object arg) {
		
		Object returnObj=null;
		try {
			Method methode= myclass.getMethod(methodeName,boolean.class);

			returnObj =methode.invoke(instance,arg);
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return returnObj;
		
		
		
		
	}
	
	
	private void switchCards() {
		cutomTableComponent.getChildren().clear();

		switch(activeCard) {
		
		case tableCard:
			
			
			AnchorPane.setTopAnchor(tablePane,  0.0); 
			AnchorPane.setLeftAnchor(tablePane,  0.0); 
			AnchorPane.setRightAnchor(tablePane,  0.0); 
			AnchorPane.setBottomAnchor(tablePane,  0.0); 
			 
			
			this.tableContainer=tablePane;
			cutomTableComponent.getChildren().add((Node)tablePane );
			head=null;
	         
		
			break;
		case headTableCard:
			
			
			AnchorPane.setTopAnchor(headTablePane,  0.0); 
			AnchorPane.setLeftAnchor(headTablePane,  0.0); 
			AnchorPane.setRightAnchor(headTablePane,  0.0); 
			AnchorPane.setBottomAnchor(headTablePane,  0.0); 
			 
			
			
			this.tableContainer=(AnchorPane) headTablePane.getChildren().get(1);
			head=(FlowPane) headTablePane.getChildren().get(0);
			head.setHgap(10);  
			cutomTableComponent.getChildren().add((Node)headTablePane );

	         
			break;
			default: 
				break;
			
		
		
		}
		
		
		
	}
	
	

 
public  TableColumn getTableColumnById( String id) {
 	for ( TableColumn col : this.table.getColumns())
        if (col.getId().equals(id)) return  col ;
    return null ;
}
}
