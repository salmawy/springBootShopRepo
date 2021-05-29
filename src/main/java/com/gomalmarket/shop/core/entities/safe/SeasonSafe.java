package com.gomalmarket.shop.core.entities.safe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "SEASON_SAFE")
@Entity(name = "SeasonSafe")
@Setter
@Getter
public class SeasonSafe  {
	

	@Id
	@Column(name ="SEASON_ID" )
	private int id ;

    @Column(name = "BALANCE",columnDefinition = " NUMBER(8) ")
    private Double balance;
    
    
 
  
	 


}
