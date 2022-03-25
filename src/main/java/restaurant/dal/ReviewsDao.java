package restaurant.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import restaurant.model.*;

public class ReviewsDao {
	protected ConnectionManager connectionManager;

	private static ReviewsDao instance = null;
	protected ReviewsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewsDao getInstance() {
		if(instance == null) {
			instance = new ReviewsDao();
		}
		return instance;
	}
	
	public Reviews create(Reviews review) throws SQLException {
		String insertReview =
			"INSERT INTO Reviews(Content,Rating,UserName,RestaurantId) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReview,
				Statement.RETURN_GENERATED_KEYS);

			insertStmt.setString(1, review.getContent());
			insertStmt.setBigDecimal(2, review.getRating());
			insertStmt.setString(3, review.getUser().getUserName());
			insertStmt.setInt(4, review.getRestaurant().getRestaurantId());
			System.out.println("!!!!RestaurantName:"+ review.getRestaurant().getName());
			System.out.println("!!!!RestaurantId:"+ review.getRestaurant().getRestaurantId());
			
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if(resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewId(reviewId);
			
			// Before returning review, review.created is not set.
			// Is it a problem???
			return review;
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


	public Reviews getReviewById(int reviewId) throws SQLException {
		String selectReview =
				"SELECT ReviewId,Created,Content,Rating,UserName,RestaurantId " +
				"FROM Reviews " +
				"WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, reviewId);
			results = selectStmt.executeQuery();
			
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			if(results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				BigDecimal rating = results.getBigDecimal("Rating");
				String userName = results.getString("UserName");
				
				int restaurantId = results.getInt("RestaurantId");
				
				
				Users user = usersDao.getUserByUserName(userName);
				Restaurants restaurant = restaurantsDao.getRestaurantById(restaurantId);
				Reviews review = new Reviews(resultReviewId, created, content, rating, user, restaurant);
				return review;
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
	
	
	public List<Reviews> getReviewsByUserName(String userName) throws SQLException {
		String selectReview =
				"SELECT ReviewId,Created,Content,Rating,UserName,RestaurantId " +
				"FROM Reviews " +
				"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Reviews> reviews = new ArrayList<Reviews>();
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			
			while(results.next()) {
				int reviewId = results.getInt("ReviewId");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				BigDecimal rating = results.getBigDecimal("Rating");
				String resultUserName = results.getString("UserName");
				
				int restaurantId = results.getInt("RestaurantId");
				
				
				Users user = usersDao.getUserByUserName(resultUserName);
				Restaurants restaurant = restaurantsDao.getRestaurantById(restaurantId);
				Reviews review = new Reviews(reviewId, created, content, rating, user, restaurant);
				reviews.add(review);
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
		return reviews;
	
	}
	
	public List<Reviews> getReviewsByRestaurantId(int restaurantId) throws SQLException {
		String selectReview =
				"SELECT ReviewId,Created,Content,Rating,UserName,RestaurantId " +
				"FROM Reviews " +
				"WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Reviews> reviews = new ArrayList<Reviews>();
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			
			while(results.next()) {
				int reviewId = results.getInt("ReviewId");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				BigDecimal rating = results.getBigDecimal("Rating");
				String userName = results.getString("UserName");
				
				int resultRestaurantId = results.getInt("RestaurantId");
				
				
				Users user = usersDao.getUserByUserName(userName);
				Restaurants restaurant = restaurantsDao.getRestaurantById(resultRestaurantId);
				Reviews review = new Reviews(reviewId, created, content, rating, user, restaurant);
				reviews.add(review);
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
		return reviews;
	}
	
	public Reviews delete(Reviews review) throws SQLException {
		String deleteReview = "DELETE FROM Reviews WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogComments instance.
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
