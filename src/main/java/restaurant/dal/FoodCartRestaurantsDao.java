package restaurant.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import restaurant.model.*;

public class FoodCartRestaurantsDao extends RestaurantsDao{

	private static FoodCartRestaurantsDao instance = null;
	protected  FoodCartRestaurantsDao() {
		super();
	}
	public static FoodCartRestaurantsDao getInstance() {
		if(instance == null) {
			instance = new FoodCartRestaurantsDao();
		}
		return instance;
	}
	
	public FoodCartRestaurants create(FoodCartRestaurants fcr) throws SQLException {
		// Insert into the superclass table first.
		Restaurants restaurant = create(new Restaurants(
		 fcr.getName(), fcr.getDescription(),fcr.getMenu(),
		 fcr.getHours(), fcr.isActive(), fcr.getCuisin(),fcr.getStreet1(),
		 fcr.getStreet2(),fcr.getCity(),fcr.getState(),fcr.getZip(),
		 fcr.getCompany()
		));

		String insertFoodCartRestaurant = "INSERT INTO FoodCartRestaurant(RestaurantId,licensed) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFoodCartRestaurant);
			
			insertStmt.setInt(1, restaurant.getRestaurantId());
			insertStmt.setBoolean(2, fcr.isLicensed());
			insertStmt.executeUpdate();
			
			// NOTE: Remember to set the passed-in sdr's Id as well, before returning!
			fcr.setRestaurantId(restaurant.getRestaurantId());
			return fcr;
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
	
	public FoodCartRestaurants getFoodCartRestaurantById(int FoodCartRestaurantId) throws SQLException {
		String selectfcr = "SELECT Restaurants.RestaurantId AS RestaurantId, Name,Description,Menu,Hours,Active,"
				+ "CuisineType,Street1,Street2,City,State,Zip,CompanyName, licensed"
				+ " FROM FoodCartRestaurant INNER JOIN Restaurants ON "
				+ "Restaurants.RestaurantId = FoodCartRestaurant.RestaurantId "
				+ "WHERE FoodCartRestaurant.RestaurantId=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectfcr);
			selectStmt.setInt(1, FoodCartRestaurantId);
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
				
				boolean licensed = results.getBoolean("licensed");
				
				FoodCartRestaurants fcr = new FoodCartRestaurants(resultId, name, description,
						menu, hours, active, resultCuisine, street1, street2, city, state, zip, company, licensed);
				return fcr;
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
	
	public List<FoodCartRestaurants> getFoodCartRestaurantsByCompanyName(String companyName) throws SQLException {
		String selectfcr = "SELECT Restaurants.RestaurantId AS RestaurantId, Name,Description,Menu,Hours,Active,"
				+ "CuisineType,Street1,Street2,City,State,Zip,CompanyName, licensed"
				+ " FROM FoodCartRestaurant INNER JOIN Restaurants ON "
				+ "Restaurants.RestaurantId = FoodCartRestaurant.RestaurantId "
				+ "WHERE CompanyName=?";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		List<FoodCartRestaurants> fcrs = new ArrayList<FoodCartRestaurants>();
		CompaniesDao companiesDao = CompaniesDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectfcr);
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
				
				boolean licensed = results.getBoolean("licensed");
				
				FoodCartRestaurants fcr = new FoodCartRestaurants(restaurantId, name, description,
						menu, hours, active, resultCuisine, street1, street2, city, state, zip, company, licensed);
				fcrs.add(fcr);
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
		return fcrs;
	}
	
	
	public FoodCartRestaurants delete(FoodCartRestaurants FoodCartRestaurant) throws SQLException {
		try {
			super.delete(FoodCartRestaurant);
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}   
  