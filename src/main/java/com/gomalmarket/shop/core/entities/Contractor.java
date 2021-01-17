package com.gomalmarket.shop.core.entities;


import javax.persistence.*;

@Table(name ="CONTRACTORS")
@Entity(name ="Contractor")
public class Contractor  extends BaseBean{
	
	@TableGenerator(name = "TABLE_GENERATOR",table = "ID_TABLE",
			pkColumnName = "ID_TABLE_NAME",
			pkColumnValue = "CONTRACTOR_ID",
			valueColumnName = "ID_TABLE_VALUE",allocationSize = 1)
	@GeneratedValue(strategy =  GenerationType.TABLE,generator = "TABLE_GENERATOR")
	@Id
	@Column(name ="ID" )
	private int  id=-1;

	@Column(name ="NAME" )
	private String name;

	@Column(name ="ADDRESS" )
	private String address;

	@Column(name ="PHONE" )
	private String phone;

	@Column(name ="TYPE_NAME" )
	private String typeName;

	@Column(name ="OWNER_ID" )
	private int ownerId;

	@Column(name ="TYPE_ID" )
	private int typeId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	
	

}
