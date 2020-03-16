package org.com.friday.carlookup.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class InputDataProvider {
	
	private static final String PROJECT_LOCATION = "src/test/resources/TestData/";
	
	private static Connection connection;
	
	/**
	 * This method uses fillo class to read input data from 'InputDataSheet.xlsx'
	 * @return filloconnectionObject
	 * @throws FilloException
	 */
	private static Connection getConnection() throws FilloException {
		try {
			Fillo fillo = new Fillo();
			connection = fillo.getConnection(new File(System.getProperty("user.dir")).getAbsolutePath()+"\\"+PROJECT_LOCATION+"\\"+FileUtil.readPropertyFile().getProperty("inputDataFileName"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	/**
	 * This method helps in parameterization by reading excel using
	 * fillo libraries , stores & returns the data in array.
	 * @param m
	 * @return array
	 */
	    @SuppressWarnings("deprecation")
		@DataProvider(name = "data-provider",parallel=false)
		public static  Object[][] getDataFromDataprovider(Method m){
	    	
	    	Object[][] testInput=null;
	    	String scenario= m.getName();
	    	String strQuery = "Select * from TestData where Scenario = '"+scenario+"'";
	    	Recordset rs = null;
		 	try{
	    	System.out.println("Query "+strQuery);
	    	rs= getConnection().executeQuery(strQuery);
	    	int row=0;
	    
	    	testInput= new Object[rs.getCount()][rs.getFieldNames().size()];
	    	while (rs.hasNext()) {
	    		rs.next();
	    		int col=0;
	    		
	    		for(String header : rs.getFieldNames()){
	    			testInput[row][col]=rs.getField(header);
	    			col=col+1;
	    		}
	    		row=row+1;
			}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally {
	    		rs.close();
	    		connection.close();
	    	}
	        
	        return testInput;     
	 
	    }

}
