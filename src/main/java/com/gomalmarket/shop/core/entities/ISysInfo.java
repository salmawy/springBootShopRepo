package com.gomalmarket.shop.core.entities;

import java.sql.Timestamp;
import java.util.Date;

public interface ISysInfo {
	
	
	public Integer getChangerId() ;


	public void setChangerId(Integer changerId);


	public Timestamp getTimestamp();


	public void setTimestamp(Timestamp timestamp);


	public Date getChangeDate();


	public void setChangeDate(Date changeDate) ;


	public Integer getChanged();


	public void setChanged(Integer changed) ;

	
	

}
