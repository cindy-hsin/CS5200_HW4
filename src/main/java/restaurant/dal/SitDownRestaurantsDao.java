package restaurant.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import restaurant.model.*;

public class SitDownRestaurantsDao extends RestaurantsDao{

	private static SitDownRestaurantsDao instance = null;
	protected  SitDownRestaurantsDao() {
		super();
	}
	public static SitDownRestaurantsDao getInstance() {
		if(instance == null) {
			instance = new SitDownRestaurantsDao();
		}
		return instance;
	}
	
	public SitDownRestaurants create(SitDownRestaurants sdr) throws SQLException {
		// Insert into the superclass table first.
		create(new Restaurants(
		 sdr.getName(), sdr.getDescription(),sdr.getMenu(),
		 sdr.getHours(), sdr.isActive(), sdr.getCuisin(),sdr.getStreet1(),
		 sdr.getStreet2(),sdr.getCity(),sdr.getState(),sdr.getZip(),
		 sdr.getCompany()
		));

		String insertSitDownRestaurant = "INSERT INTO SitDownRestaurants(RestaurantId,Capacity) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertSitDownRestaurant);
			insertStmt.setInt(1, sdr.getRestaurantId());
			insertStmt.setInt(2, sdr.getCapacity());
			insertStmt.executeUpdate();
			return sdr;
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
	
	public SitDownRestaurants getSitDownRestaurantById(int sitDownRestaurantId) throws SQLException {
		String selectSdr = "SELECT Restaurants.RestaurantId AS RestaurantId, Name,Description,Menu,Hours,Active,"
				+ "CuisineType,Street1,Street2,City,State,Zip,CompanyName, Capacity"
				+ " FROM SitDownRestaurants INNER JOIN Restaurants ON "
				+ "Restaurants.RestaurantId = SitDownRestaurants.RestaurantId "
				+ "WHERE SitDownRestaurants.RestaurantId=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSdr);
			selectStmt.setInt(1, sitDownRestaurantId);
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
				
				int capacity = results.getInt("Capacity");
				
				SitDownRestaurants sdr = new SitDownRestaurants(resultId, name, description,
						menu, hours, active, resultCuisine, street1, street2, city, state, zip, company, capacity);
				return sdr;
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
	
	public List<SitDownRestaurants> getSitDownRestaurantsByCompanyName(String companyName) throws SQLException {
		String selectSdr = "SELECT Restaurants.RestaurantId AS RestaurantId, Capacity, CompanyName"
				+ " FROM SitDownRestaurants INNER JOIN Restaurants ON "
				+ "Restaurants.RestaurantId = SitDownRestaurants.RestaurantId "
				+ "WHERE CompanyName=?";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		List<SitDownRestaurants> sdrs = new ArrayList<SitDownRestaurants>();
		CompaniesDao companiesDao = CompaniesDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSdr);
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
				
				int capacity = results.getInt("Capacity");
				
				SitDownRestaurants sdr = new SitDownRestaurants(restaurantId, name, description,
						menu, hours, active, resultCuisine, street1, street2, city, state, zip, company, capacity);
				sdrs.add(sdr);
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
	
	
	
	public SitDownRestaurants delete(SitDownRestaurants sitDownRestaurant) throws SQLException {
		try {
			super.delete(sitDownRestaurant);
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}   
  