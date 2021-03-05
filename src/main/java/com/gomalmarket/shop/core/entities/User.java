 package com.gomalmarket.shop.core.entities;


 import lombok.Getter;
 import lombok.Setter;

 import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

 @Table(name = "USERS")
 @Entity(name = "User")
 @Setter
 @Getter
public class User extends BaseEntity  implements java.io.Serializable  {
	
	 @GeneratedValue
		@Id
		@Column(name ="ID" )
		private int id ;

	private static final long serialVersionUID = 1L;

     @Column(name = "NAME")
 	private String username;

     @Column(name = "PASSWORD")
	private String password;


	
 	 


}
