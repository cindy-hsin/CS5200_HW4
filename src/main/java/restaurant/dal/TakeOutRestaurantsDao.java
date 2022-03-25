package restaurant.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import restaurant.model.*;

public class TakeOutRestaurantsDao extends RestaurantsDao{

	private static TakeOutRestaurantsDao instance = null;
	protected  TakeOutRestaurantsDao() {
		super();
	}
	public static TakeOutRestaurantsDao getInstance() {
		if(instance == null) {
			instance = new TakeOutRestaurantsDao();
		}
		return instance;
	}
	
	public TakeOutRestaurants create(TakeOutRestaurants tor) throws SQLException {
		// Insert into the superclass table first.
		Restaurants restaurant = create(new Restaurants(
		 tor.getName(), tor.getDescription(),tor.getMenu(),
		 tor.getHours(), tor.isActive(), tor.getCuisin(),tor.getStreet1(),
		 tor.getStreet2(),tor.getCity(),tor.getState(),tor.getZip(),
		 tor.getCompany()
		));

		String insertTakeOutRestaurant = "INSERT INTO TakeOutRestaurant(RestaurantId,MaxWaitTime) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertTakeOutRestaurant);
			
			insertStmt.setInt(1, restaurant.getRestaurantId());
			insertStmt.setInt(2, tor.getMaxWaitTime());
			insertStmt.executeUpdate();
			
			// NOTE: Remember to set the passed-in sdr's Id as well, before returning!
			tor.setRestaurantId(restaurant.getRestaurantId());
			return tor;
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
		}
	}
	
	public TakeOutRestaurants getTakeOutRestaurantById(int takeOutRestaurantId) throws SQLException {
		String selectTor = "SELECT Restaurants.RestaurantId AS RestaurantId, Name,Description,Menu,Hours,Active,"
				+ "CuisineType,Street1,Street2,City,State,Zip,CompanyName, MaxWaitTime"
				+ " FROM TakeOutRestaurant INNER JOIN Restaurants ON "
				+ "Restaurants.RestaurantId = TakeOutRestaurant.RestaurantId "
				+ "WHERE TakeOutRestaurant.RestaurantId=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTor);
			selectStmt.setInt(1, takeOutRestaurantId);
			results = selectStmt.executeQuery();
			
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			if(results.next()) {
				int resultId = results.getInt("RestaurantId");
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
				
				int maxWaitTime = results.getInt("MaxWaitTime");
				
				TakeOutRestaurants tor = new TakeOutRestaurants(resultId, name, description,
						menu, hours, active, resultCuisine, street1, street2, city, state, zip, company, maxWaitTime);
				return tor;
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
	
	public List<TakeOutRestaurants> getTakeOutRestaurantsByCompanyName(String companyName) throws SQLException {
		String selectTor = "SELECT Restaurants.RestaurantId AS RestaurantId, Name,Description,Menu,Hours,Active,"
				+ "CuisineType,Street1,Street2,City,State,Zip,CompanyName, MaxWaitTime"
				+ " FROM TakeOutRestaurant INNER JOIN Restaurants ON "
				+ "Restaurants.RestaurantId = TakeOutRestaurant.RestaurantId "
				+ "WHERE CompanyName=?";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		List<TakeOutRestaurants> tors = new ArrayList<TakeOutRestaurants>();
		CompaniesDao companiesDao = CompaniesDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTor);
			selectStmt.setString(1, companyName);
			results = selectStmt.executeQuery();
			
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
			
				String resultCompanyName = results.getString("CompanyName");
				Companies company = companiesDao.getCompanyByCompanyName(resultCompanyName);
				
				int maxWaitTime = results.getInt("MaxWaitTime");
				
				TakeOutRestaurants tor = new TakeOutRestaurants(restaurantId, name, description,
						menu, hours, active, resultCuisine, street1, street2, city, state, zip, company, maxWaitTime);
				tors.add(tor);
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
		return tors;
	}
	
	
	
	public TakeOutRestaurants delete(TakeOutRestaurants takeOutRestaurant) throws SQLException {
		try {
			super.delete(takeOutRestaurant);
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}   
  