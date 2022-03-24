package restaurant.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import restaurant.model.Companies;
import restaurant.model.Restaurants;

public class RestaurantsDao {
	protected ConnectionManager connectionManager;

	private static RestaurantsDao instance = null;
	protected RestaurantsDao() {
		connectionManager = new ConnectionManager();
	}
	public static RestaurantsDao getInstance() {
		if(instance == null) {
			instance = new RestaurantsDao();
		}
		return instance;
	}
	
	
	public Restaurants create(Restaurants restaurant) throws SQLException {
		String insertRestaurant = "INSERT INTO Restaurants"
				+ "(Name,Description,Menu,Hours,Active,CuisineType,Street1,Street2,City,State,Zip,CompanyName) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		
		try {
			connection = connectionManager.getConnection();
			// Restaurant has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertRestaurant,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, restaurant.getName());
			
			insertStmt.setString(2, restaurant.getDescription());
			insertStmt.setString(3, restaurant.getMenu());
			insertStmt.setString(4, restaurant.getHours());
			insertStmt.setBoolean(5, restaurant.isActive());
			insertStmt.setString(6, restaurant.getCuisin().name());
			insertStmt.setString(7, restaurant.getStreet1());
			insertStmt.setString(8, restaurant.getStreet2());
			insertStmt.setString(9, restaurant.getCity());
			insertStmt.setString(10, restaurant.getState());
			insertStmt.setInt(11, restaurant.getZip());
			insertStmt.setString(12, restaurant.getCompany().getCompanyName());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int restaurantId = -1;
			if(resultKey.next()) {
				restaurantId = resultKey.getInt(1);  
				// resultKey is a ResultKey object,not an integer value.
				// So to retrieve the postId, which is the 1st and only column in the ResultSet,
				// we need to pass in index 1 to getInt().
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			restaurant.setRestaurantId(restaurantId);
			return restaurant;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	public Restaurants getRestaurantById(int restaurantId) throws SQLException {
		String selectRestaurant = "SELECT RestaurantId, Name,Description,Menu,Hours,Active,CuisineType,"
				+ "Street1,Street2,City,State,Zip,CompanyName " 
				+ "FROM Restaurants WHERE RestaurantId=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurant);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			if(results.next()) {
				int resultRestaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.Cuisine cuisine = Restaurants.Cuisine.valueOf(
						results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
			
				String companyName = results.getString("CompanyName");
				Companies company = companiesDao.getCompanyByCompanyName(companyName);
				
				Restaurants restaurant = new Restaurants(resultRestaurantId, name, description,
						menu, hours, active, cuisine, street1, street2, city, state, zip, company);
				return restaurant;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	public List<Restaurants> getRestaurantsByCuisine(Restaurants.Cuisine cuisine) throws SQLException {
		String selectRestaurants = "SELECT RestaurantId, Name,Description,Menu,Hours,Active,CuisineType,"
				+ "Street1,Street2,City,State,Zip,CompanyName " 
				+ "FROM Restaurants WHERE CuisineType=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		List<Restaurants> restaurants = new ArrayList<Restaurants>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurants);
			selectStmt.setString(1, cuisine.name());
			results = selectStmt.executeQuery();
			
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			while(results.next()) {
				int restaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.Cuisine resultCuisine = Restaurants.Cuisine.valueOf(
						results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
			
				String companyName = results.getString("CompanyName");
				Companies company = companiesDao.getCompanyByCompanyName(companyName);
				
				Restaurants restaurant = new Restaurants(restaurantId, name, description,
						menu, hours, active, resultCuisine, street1, street2, city, state, zip, company);
				restaurants.add(restaurant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return restaurants;
		
	}
	
	
	public List<Restaurants> getRestaurantsByCompanyName(String companyName) throws SQLException {
		String selectRestaurants = "SELECT RestaurantId, Name,Description,Menu,Hours,Active,CuisineType,"
				+ "Street1,Street2,City,State,Zip,CompanyName " 
				+ "FROM Restaurants WHERE CompanyName=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		List<Restaurants> restaurants = new ArrayList<Restaurants>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurants);
			selectStmt.setString(1, companyName);
			results = selectStmt.executeQuery();
			
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			while(results.next()) {
				int restaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.Cuisine cuisine = Restaurants.Cuisine.valueOf(
						results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
			
				String resultCompanyName = results.getString("CompanyName");
				Companies company = companiesDao.getCompanyByCompanyName(resultCompanyName);
				
				Restaurants restaurant = new Restaurants(restaurantId, name, description,
						menu, hours, active, cuisine, street1, street2, city, state, zip, company);
				restaurants.add(restaurant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return restaurants;
	}
	
	
	public Restaurants delete(Restaurants restaurant) throws SQLException {
		String deleteRestaurant = "DELETE FROM Restaurants WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRestaurant);
			deleteStmt.setInt(1, restaurant.getRestaurantId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
	

}
