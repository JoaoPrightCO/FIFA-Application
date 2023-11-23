package com.aroska.fifa.util;

import java.util.ArrayList;

public class QueryUtil {

	//--------------//
	//PUBLIC METHODS//
	//--------------//
	
	public static String createInsertQuery (String tableName, String[] columns) {
		
		String insertQuery = "INSERT INTO " + tableName
							+ " (" + generateColumnString(columns) + ")"
							+ " VALUES"
							+ " (" + generateValuesString(columns) + ")";

		
		return insertQuery;
	}

	public static String createInsertQuery (String tableName, String column) {
		
		String insertQuery = "INSERT INTO " + tableName
							+ " (" + column + ")"
							+ " VALUES"
							+ " (?)";

		return insertQuery;
	}
	
	public static String createFindByIdQuery(String tableName, int id) {
		
		String findByIdQuery = "SELECT * FROM " + tableName +
								" WHERE id=" + id;
		
		return findByIdQuery;
	}
	
	public static String createFindByUsernameQuery(String tableName, String username) {
		
		String findByUsernameQuery = "SELECT * FROM " + tableName +
									" WHERE username='" + username + "'";
		
		return findByUsernameQuery;
	}
	
	public static String createFindByEmailQuery(String tableName, String email) {
		
		String findByEmailQuery = "SELECT * FROM " + tableName +
								" WHERE email='" + email + "'";
		
		return findByEmailQuery;
	}
	
	public static String createFindByUsernameOrEmailQuery(String tableName, String usernameOrEmail) {
		
		String findByUsernameOrEmailQuery = "SELECT * FROM " + tableName +
											" WHERE username='" + usernameOrEmail + "'" +
											" OR email='" + usernameOrEmail + "'";
		
		return findByUsernameOrEmailQuery;
	}
	
	public static String createFindByColumn(String tableName, String columnName, String columnValue) {
		
		String findByNameQuery = "SELECT * FROM " + tableName +
								" WHERE " + columnName + "=\'" + columnValue + "\'";
		
		System.out.println("query: " + findByNameQuery);
		
		return findByNameQuery;
	}
	
	public static String createFindByColumn(String tableName, String columnName, int columnValue) {
		
		String findByNameQuery = "SELECT * FROM " + tableName +
								" WHERE " + columnName + "=\'" + columnValue + "\'";
		
		System.out.println("query: " + findByNameQuery);
		
		return findByNameQuery;
	}
	
	//TODO
	public static String createEditQuery(String tableName, ArrayList<String> columns, ArrayList<String> values, String identifier) {
		
		String editQuery = "UPDATE " + tableName +
							" SET ";
		
		for(int i = 0; i<columns.size(); i++) {
			if(i!=0) { editQuery+=", "; }
			editQuery+=columns.get(i)+"='"+values.get(i)+"'";
		}
			
		editQuery+= " WHERE id='" + identifier + "'";
		
		return editQuery;
	}
	
	public static String createDeleteQuery(String tableName, String columnName, String columnValue) {
		
		String deleteQuery = "DELETE FROM " + tableName +
							" WHERE " + columnName + "=\'" + columnValue + "\'";
		
		System.out.println("delete query: " + deleteQuery);
		
		return deleteQuery;
							
	}
	
	public static String createDeleteQuery(String tableName, String columnName, int columnValue) {
		
		String deleteQuery = "DELETE FROM " + tableName +
							" WHERE " + columnName + "=" + columnValue;
		
		System.out.println("delete query: " + deleteQuery);
		
		return deleteQuery;
							
	}
	
	public static String createGetAllQuery(String tableName) {
		
		String getAllQuery = "SELECT * FROM " + tableName;
		
		System.out.println("get all query: " + getAllQuery);
		
		return getAllQuery;
	}
	
	
	
	//---------------//
	//PRIVATE METHODS//
	//---------------//	
	
	private static String generateColumnString(String[] columns) {
		
		StringBuilder columnString = new StringBuilder();
		
		for(int i = 0; i<columns.length; i++) {
			if(i!=0) { columnString.append(", "); }
			columnString.append(columns[i]);
		}
		
		return columnString.toString();
	}
	
	private static String generateValuesString(String[] values) {
		
		StringBuilder valuesString = new StringBuilder();
		
		for(int i = 0; i<values.length; i++) {
			if(i!=0) { valuesString.append(", "); }
			valuesString.append("?");
		}
		
		return valuesString.toString();
	}
	
}
