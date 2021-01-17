package com.gomalmarket.shop.core.UIComponents.comboBox;

import org.jfree.util.Log;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ComboBoxItem {
	
	private int id ;
	private String text;
	private String parentKey;
	
	 private Object valueObject;
	
	
public ComboBoxItem(int value,String text) {
		this.text=text;
		this.id=value;
		

}


public ComboBoxItem(int value,String text,Object valueObject) {
	this.text=text;
	this.id=value;
	this.setValueObject(valueObject);

}



public ComboBoxItem(int value,String text,String parentKey) {
	this.text=text;
	this.id=value;
	this.parentKey=parentKey;
	this.setValueObject(valueObject);

}	

public ComboBoxItem(int value,String text,String parentKey,Object valueObject) {
	this.text=text;
	this.id=value;
	this.parentKey=parentKey;
	this.setValueObject(valueObject);

}	

	
 

 


@Override
public String toString() {
	// TODO Auto-generated method stub
	return this.getText();
}



 

 
@Override
public boolean equals(Object obj) {
	
	ComboBoxItem item=null;
	try {
		item = (ComboBoxItem)obj;
		return this.getId()==item.getId();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		Log.error(e.getMessage());
	}
	 return super.equals(obj);

}


}
