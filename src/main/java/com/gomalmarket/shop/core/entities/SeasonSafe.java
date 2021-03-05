package com.gomalmarket.shop.core.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "SEASON_SAFE")
@Entity(name = "SeasonSafe")
@Setter
@Getter
public class SeasonSafe extends BaseEntity {
	

	@Id
	@Column(name ="SEASON_ID" )
	private int id ;

    @Column(name = "BALANCE")
    private Double balance;
    
    
 
  
	 


}
