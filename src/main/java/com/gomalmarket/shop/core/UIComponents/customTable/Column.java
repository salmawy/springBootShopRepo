/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gomalmarket.shop.core.UIComponents.customTable;

/**
 *
 * @author ahmed
 */
public class Column {
    
    
    private String name;
    private String id;
    private String type ;
    private int size;
    private boolean show;
    private boolean editable;
    public Column(String name, String id, String type, int size, boolean show) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.size = size;
        this.show = show;
        this.editable = false;
    }
    
    public Column(String name, String id, String type, int size, boolean show,boolean editable) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.size = size;
        this.show = show;
        this.editable = editable;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the show
     */
    public boolean getShow() {
        return show;
    }

    /**
     * @param show the show to set
     */
    public void setShow(boolean show) {
        this.show = show;
    }


	public boolean isEditable() {
		return editable;
	}


	public void setEditable(boolean editable) {
		this.editable = editable;
	}
    
    
    
    
}
