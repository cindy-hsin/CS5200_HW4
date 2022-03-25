package restaurant.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

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
		


	public Reviews getReviewById(int reviewId) {
		
	}
//	public List<Reviews> getReviewsByUserName(String userName) {
//		
//	}
//	public List<Reviews> getReviewsByRestaurantId(int restaurantId) {
//		
//	}
//	public Reviews delete(Reviews review) {
//		
//	}
	
}
