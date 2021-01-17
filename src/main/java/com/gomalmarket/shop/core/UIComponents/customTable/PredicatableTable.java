package com.gomalmarket.shop.core.UIComponents.customTable;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class PredicatableTable <RowClass extends RecursiveTreeObject<RowClass> > {

	private FlowPane head;
	private AnchorPane tableContainer;
	
	
	private AnchorPane tablePane;
	private AnchorPane headTablePane;

	private AnchorPane cutomTableComponent;
	
	
	
	
	
	
	private JFXTreeTableView <RowClass>table ;
	private TreeItem<RowClass> root ;

	private Class myclass;
	private final String actionsPanelId = "actionsPanel";
	private final String confirmActionPanelId = "confirmActionPanel";
	private final String tableContainerId = "tableContainer";
	public static final int headTableCard=1;
	public static final int tableCard=2;
	private int activeCard;
	private CustomTableActions actions;
	public PredicatableTable(List columns, List nodes, List data, CustomTableActions actions,
			int cardType,Class<?> beanClass) {
		this.activeCard=cardType;
	    this.actions=actions;
	  
	    
		    CustomeTableView tableView=new CustomeTableView();
			this.cutomTableComponent=(AnchorPane) tableView.getView();
			
			tablePane=(AnchorPane) cutomTableComponent.getChildren().get(0);
			headTablePane=(AnchorPane) cutomTableComponent.getChildren().get(1);
			
			switchCards();
		
			
//=========================// ***************************///==========================================================================
		
			this.myclass=beanClass;
	        ObservableList<RowClass> dummy = FXCollections.observableArrayList();


	         table = new JFXTreeTableView<>();
	         table.setShowRoot(false);

//=========================// ***************************///==========================================================================

	 		setColumnsConfiguration(columns);
//=========================// ***************************///==========================================================================
            loadTableData(data);
//=========================// ***************************///==========================================================================
		setButtonsConfiguration(nodes);
//=========================// ***************************///==========================================================================
		 
		if(actions!=null)
		    	setTableActionListner();
		
			

	
	System.out.print("custom table has been loaded succeffuly ");
	
	}


private void setTableActionListner(){
	
	
	
	table.setOnMouseClicked(new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {

			actions.rowSelected(table);
		}
		
		
	});
	
	
	
	
	
	
}
	@SuppressWarnings("unchecked")
	private void setColumnsConfiguration(List columns) {

	

		for (int i = 0; i <columns.size(); i++) {

			Column columnConfig = (Column) columns.get(i);
			    JFXTreeTableColumn<RowClass, Object> column = new JFXTreeTableColumn<>(columnConfig.getName());
		     //   column.setPrefWidth(150);
		        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<RowClass, Object> param) -> {
		            if (column.validateValue(param)) {
		            	 try {
//=========================//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&///==========================================================================
							
							
			            	RowClass c=(RowClass) param.getValue().getValue();

							Object o=callGetter(c, columnConfig.getId());
			                return (ObservableValue<Object>) o;
			                
 //=========================//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&///==========================================================================

					} catch ( SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
		                return null;
		            } else {
		                return column.getComputedValue(param);
		            }
		        });
					 
			
		        column.prefWidthProperty().bind(table.widthProperty().multiply(columnConfig.getSize() / 100.0).subtract(((table.getInsets().getLeft() + table.getInsets().getRight()) / columns.size())));
		
			 
		
			 
			table.getColumns().add(i, column);
			

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

	public void loadTableData(List data) {
		if (data!=null)
		{  
		        ObservableList<RowClass> tableData = FXCollections.observableArrayList();
		        tableData.setAll(data);
		         root = new RecursiveTreeItem<>(tableData, RecursiveTreeObject::getChildren);
		         table.setRoot(root);
			
			 //  root.getChildren().addAll(data);
		
		
		
		}
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
	public JFXTreeTableView getTable() {
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

	public void setTable(JFXTreeTableView table) {
		this.table = table;
	}
	
	
	private Object invokeMethode(Object instance,String methodeName) {
		Object returnObj=null;
		try {
		Method methode= myclass.getMethod(methodeName);
		returnObj=	methode.invoke(instance);
		
		
		
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

	private Object callGetter(Object obj, String fieldName){
		  PropertyDescriptor pd;
		  try {
		   pd = new PropertyDescriptor(fieldName, obj.getClass());
		   return  pd.getReadMethod().invoke(obj);
		  } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		return null;
		 }
	
	public void setTablePrefTableHeight(double value ) {
 
		
		if(activeCard==tableCard) {
			
			tablePane.setPrefHeight(value);
			
		}else if (activeCard==headTableCard) {
			
			this.tableContainer.setPrefHeight(value);
		}
		}

	
	public void setTablePrefTableWidth(double value ) {
 
		
		if(activeCard==tableCard) {
			
			tablePane.setPrefWidth(value);
			
		}else if (activeCard==headTableCard) {
			
			this.tableContainer.setPrefWidth(value);
		}
		}


	public void setTableMaxTableHeight(double value ) {
 
		
		if(activeCard==tableCard) {
			
			tablePane.setPrefHeight(value);
			
		}else if (activeCard==headTableCard) {
			
			this.tableContainer.setMaxHeight(value);
		}
		}

	
	public void setTableMaxTableWidth(double value ) {
 
		
		if(activeCard==tableCard) {
			
			tablePane.setPrefWidth(value);
			
		}else if (activeCard==headTableCard) {
			
			this.tableContainer.setMaxWidth(value);
		}
		}



}
