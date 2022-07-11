package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresqlDb {

		private static String url = "jdbc:postgresql://localhost:5432/wallet";
		private static String username = "---";
		private static String password = "---";
		private static Connection connection = null;
		private static Statement statement;
		private static PostgresqlDb instance;
		
		  public static PostgresqlDb getInstance(){
			  
			    if(instance == null){
			    	instance = new PostgresqlDb();
			    	connection();
			    }
			    return instance;
			    }
		
		public static void connection() {
			try {
				connection = DriverManager.getConnection(url, username, password);
				System.out.println("Baglandi");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static ResultSet getAllActivities() {
			String query = "select * from activities";
			
			try {
				getInstance();
				statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				return resultSet;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			
		}
		
		public static void addActivities(String name, String amount, String date) {
			String query = "INSERT INTO public.activities(name, amount, date) VALUES ( '" + name + "', " + Long.parseLong(amount) + ", '" + date  +"' )";
			try {
				getInstance();
				statement = connection.createStatement();
				statement.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		public static void deleteActivities(String name, String amount, String date) {
			String query = "DELETE FROM public.activities where amount = " + Long.parseLong(amount) + " and date = '" +  date  + "' and name = '" + name + "'";
			try {
				getInstance();
				statement = connection.createStatement();
				statement.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		public static void updateActivities(String name, String amount, String date) {
			String query = "UPDATE public.activities SET amount = " + Long.parseLong(amount) + ", date = '" +  date  + "' Where name = '" + name + "'";
			try {
				getInstance();
				statement = connection.createStatement();
				statement.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
}
