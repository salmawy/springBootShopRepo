package com.gomalmarket.shop.core.exception;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;


public class HandleErr {
	private List<Object> err;
	
	
	private ResourceBundle resourceBundle =ResourceBundle.getBundle("com.sps.core.resources.ApplicationResources_ar");
	private ResourceBundle settingBundle =ResourceBundle.getBundle("com.sps.core.resources.ApplicationSettings_ar");
	
	public HandleErr()
	{
		ErrMsg errMsg=new ErrMsg();
		
		err=new ArrayList<Object>(10);
		List<Object> lst=new ArrayList<Object>(10);
		errMsg.setClassName("class org.springframework.dao.DataIntegrityViolationException");
		errMsg.setDescription("error.dataBaseException");
		
		ErrMsg temp=errMsg;
		lst.add(temp);
		
		temp=new ErrMsg();
		temp.setClassName("class java.lang.NumberFormatException");
		//temp.setDescription("Number Formate err");
		temp.setDescription("error.numberFormatException");
		lst.add(temp);
		
		temp=new ErrMsg();
		temp.setClassName("class org.springframework.dao.DataAccessException");
		//temp.setDescription("Number Formate err");
		temp.setDescription("error.dataBaseException");
		lst.add(temp);
		
		temp=new ErrMsg();
		temp.setClassName("class org.springframework.jdbc.UncategorizedSQLException");
		temp.setDescription("error.server");
		lst.add(temp);
		
		temp=new ErrMsg();
		temp.setClassName("class org.springframework.dao.DataAccessResourceFailureException");
		temp.setDescription("error.server");
		lst.add(temp);
		
		temp=new ErrMsg();
		temp.setClassName("class org.springframework.dao.DataRetrievalFailureException");
		temp.setDescription("error.dataBaseException");
		lst.add(temp);
		
		this.err=lst;
	}
	public String getDescription(String className)
	{
		//marwa
		String globalError= "global.error";//this.getResourceBundle().getString("global.error");
		
		Iterator<Object> itr=this.err.iterator();
		while(itr.hasNext())
		{
			ErrMsg temp=(ErrMsg)itr.next();
			if(temp.getClassName().equals(className))
				return temp.getDescription();
			else//maraw
				return globalError;
					
		}
		return "error.general";//?
	}
	/**
	 * 
	 * @param err the Error Message came from the Action
	 * @return the detailed error Message to be displayed in the .jsp
	 *     pages. 
	 */
	public String getDetailedErrorMessage(String err)
	{
		String detailedErrorMessage = "";
		if(err == null)
		{
			detailedErrorMessage=this.getResourceBundle().getString("global.error");
			System.out.println("Msg:"+detailedErrorMessage);
			return detailedErrorMessage;
		}
		String temp="";
		String[] arr = err.split(",");
		// now to recognise the error type we must know the arr length
		// if <3  --> the error is find error
		// if >=3 --> the error is add or delete exception (must know the violated constraint)
		int arrLength = arr.length;
		
		try{ // this is the special case of the data manipulation operation not legal on this view
			String errMsg = "data manipulation operation not legal on this view";
			if(err.contains(errMsg))
			{
				detailedErrorMessage=this.getResourceBundle().getString("replication.violation.error");
				System.out.println("Msg:"+detailedErrorMessage);
				return detailedErrorMessage;
			}
		}
		catch (Exception e)
		{
			detailedErrorMessage=this.getResourceBundle().getString("global.error");
		}
		
		try{ 
			String errMsg = "Transaction timed out: deadline was";
			if(err.contains(errMsg))
			{
				detailedErrorMessage=this.getResourceBundle().getString("transaction.timeout");
				System.out.println("Msg:"+detailedErrorMessage);
				return detailedErrorMessage;
			}
		}
		catch (Exception e)
		{
			detailedErrorMessage=this.getResourceBundle().getString("global.error");
		}
		
		// if it was not a replication problem proceed as usual
		try
		{
			// generating the first two segment in the error message
			String errMsg = "Could not execute JDBC batch update";
			if(arr[0]!=null && arr[0].equalsIgnoreCase(errMsg)){
				detailedErrorMessage = this.getResourceBundle().getString("message.err.delete");
				detailedErrorMessage += " "+this.getResourceBundle().getString("error.dataBase.delete")+" ";
			}
			else if(arr[1]!=null && arr[1].equalsIgnoreCase(errMsg)){
				detailedErrorMessage = this.getResourceBundle().getString(arr[0])+" ";
			}
			else{
					//detailedErrorMessage = this.getResourceBundle().getString(arr[0])+" "+this.getResourceBundle().getString(arr[1]);
				try{
					detailedErrorMessage = this.getResourceBundle().getString(arr[0]);
					detailedErrorMessage += " "+this.getResourceBundle().getString(arr[1])+" ";
					
				}catch(Exception e){
				}
			}
			if (arrLength >=3){// there is a Exception message from the server
				// if the Exception error message contains "," it will be splitted
				// so we must reconstruct it once again
				for (int i = 2; i < arrLength ; i++)
					temp+=arr[i];
				
				//now processing the error Message
				//temp = temp.replaceAll("(CORE.","$$");
				String[] constraints = temp.split(this.getSettingBundle().getString("DbSchema").toUpperCase());
				
				
				// now the first constraint in the secont place of the constraint array
				// and the length of this array presents the number of violated constraints
				
				//for (int i = 1 ; i < constraints.length ; i++)
					//constraints[i] = constraints[i].split(",")[0];
					String constraint = constraints[1].substring(1,constraints[1].indexOf(')'));
				// now we have a list of all the violated constraints
				// spliting it and getting the corresponding messages from the resource file
				String[] constraintSegments;
				//for (int i = 1 ; i < constraints.length ; i++){
					constraintSegments = constraint.split("_");
					// @auther: fady angelo -- just handling exceptions
					if (constraintSegments[0].equals("PK")){
							try
							{
								detailedErrorMessage+=this.getResourceBundle().getString("primaryViolation");
								detailedErrorMessage+=" "+this.getResourceBundle().getString(constraintSegments[1]);
							}
							catch (Exception e){}
						}
						else if (constraintSegments[0].equals("FK")){
							try
							{
								detailedErrorMessage+=this.getResourceBundle().getString("foreignViolation");
								if(constraints[1].contains("parent key not found"))
								{
									detailedErrorMessage+=this.getResourceBundle().getString("error.parentKey.not.found");
									detailedErrorMessage+=" "+this.getResourceBundle().getString(constraintSegments[2]);
								}
								else{
									detailedErrorMessage+=this.getResourceBundle().getString("child.records.found");
									detailedErrorMessage+=" "+this.getResourceBundle().getString(constraintSegments[1]);
								}
							}
							catch (Exception e){}
							
						}
						else if (constraintSegments[0].equals("U")){
							try
							{
								detailedErrorMessage+=this.getResourceBundle().getString("UniqueViolation");
								detailedErrorMessage+=" "+this.getResourceBundle().getString(constraintSegments[1]);
								detailedErrorMessage+=" "+this.getResourceBundle().getString(constraintSegments[2]);
							}
							catch (Exception e){}
							
						}
					
					//maraw
		
			}
			if(err.contains("DEKH"))
			{
				detailedErrorMessage+=" "+this.getResourceBundle().getString("replication.violation.delete");
				
			}
			
			if(err.contains("WAIT"))
			{
				detailedErrorMessage+="  \n  "+this.getResourceBundle().getString("replication.violation.wait");
				
			}
			
		}
		catch (Exception e)
		{
			if(detailedErrorMessage==null)
				detailedErrorMessage=this.getResourceBundle().getString("global.error");
		}
		
		System.out.println("Msg:"+detailedErrorMessage);
		return detailedErrorMessage;
	}
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
	public ResourceBundle getSettingBundle() {
		return settingBundle;
	}
	/**
	 * @author : SSamra
	 * this method handle business logic exception message
	 * split message on ',' then read first string from Resource File and concatenate it with second string
	 * and return it to caller
	 * at any Exception return original message
	 */
	public String getDetailedBusinessLogicException(String err)
	{
		String detailedErrorMessage = "";
		String[] arr = err.split(",");
		int arrLength = arr.length;
		try
		{
			detailedErrorMessage = this.getResourceBundle().getString(arr[0]);
			for(int i=1;i<arrLength;i++) {
				try {	detailedErrorMessage += " "+this.getResourceBundle().getString(arr[i]); }
				catch (Exception e) {
					detailedErrorMessage += " "+arr[i];
				}
			}
			return detailedErrorMessage;
		}catch(Exception e)
		{
			return err;
		}
	}
	/**
	 * @author relmelayati
	 * @param logger
	 * @param error
	 * this method writes in log in case the exception is not BusinessLogicViolationException
	 */
	public void writeInLog (Logger logger,String error)
	{
	  try
	  {
		String[] arr = error.split(":");
		if ((arr.length > 1)&&(!arr[0].equalsIgnoreCase("BusinessLogicViolationException")))
		{
				logger.debug(error);
		}
	  }
	  catch(Exception e)
	  {
		  
	  }
		
	}
	/**
	 * @author relmelayati
	 * @param Exception
	 * this method returns the cause of exception
	 */
	
	public String getExceptionCause (String Exception)
	{
		String Cause = "";
		try
		  {
			String[] arr = Exception.split("\\.");
			Cause = arr[arr.length - 1];
			
		  }
		  catch(Exception e)
		  {
			  
		  }
		  return Cause;
	}
}
