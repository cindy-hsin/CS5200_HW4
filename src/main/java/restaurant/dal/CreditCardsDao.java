package restaurant.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import restaurant.model.CreditCards;
import restaurant.model.Users;
public class CreditCardsDao {

	protected ConnectionManager connectionManager;
	
	// Singleton pattern:
	private static CreditCardsDao instance = null;
	protected CreditCardsDao() {
		connectionManager = new ConnectionManager();
	}
	public static CreditCardsDao getInstance() {
		if(instance == null) {
			instance = new CreditCardsDao();
		}
		return instance;
	}
	
	
	public CreditCards create(CreditCards creditCard) throws SQLException {
		String insertCreditCard = "INSERT INTO CreditCards(CardNumber,Expiration,UserName) VALUES(?,?,?)";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCreditCard);
			
			insertStmt.setLong(1, creditCard.getCardNumber());
			// setDate:
			// Sets the designated parameter to the given java.sql.Date value using the default time zone of the virtual machine that is running the application. 
			// The driver converts this to an SQL DATE value when it sends it to the database.
			insertStmt.setDate(2, new java.sql.Date(creditCard.getExpiration().getTime()));
			insertStmt.setString(3, creditCard.getUser().getUserName());
			
			insertStmt.executeUpdate();
			return creditCard;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
				System.out.println("Credit card connection closed!");
			}
			if(insertStmt != null) {
				insertStmt.close();
				System.out.println("Credit card stmt closed!");
			}
		}
	}
	
	
	
	public CreditCards getCreditCardByCardNumber(long cardNumber) throws SQLException {
		String selectCreditCard = "SELECT CardNumber, Expiration, UserName "
				+ "FROM CreditCards WHERE CardNumber=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCreditCard);
			
		    selectStmt.setLong(1, cardNumber);
		    results = selectStmt.executeQuery();
		    
		    UsersDao usersDao = UsersDao.getInstance();
		    if (results.next()) {
		    	long resultCardNumber = results.getLong("CardNumber");
		    	java.util.Date expiration = new java.util.Date(results.getDate("Expiration").getTime());
		    	Users user = usersDao.getUserByUserName(results.getString("UserName"));
		    	CreditCards creditCard = new CreditCards(resultCardNumber, expiration, user);
		    	
		    	return creditCard;
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
		return null; 	// When no result is found, results.next() is false, the logic will come to this line.
	}

	public List<CreditCards> getCreditCardsByUserName(String userName) throws SQLException{
		String selectCreditCard = "SELECT CardNumber, Expiration, UserName "
				+ "FROM CreditCards WHERE UserName=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<CreditCards> creditCards = new ArrayList<CreditCards>();
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCreditCard);
			
		    selectStmt.setString(1, userName);
		    results = selectStmt.executeQuery();
		    
		    UsersDao usersDao = UsersDao.getInstance();
		    
		    while (results.next()) {
		    	long resultCardNumber = results.getLong("CardNumber");
		    	java.util.Date expiration = new java.util.Date(results.getDate("Expiration").getTime());
		    	Users user = usersDao.getUserByUserName(results.getString("UserName"));
		    	CreditCards creditCard = new CreditCards(resultCardNumber, expiration, user);
		    	creditCards.add(creditCard);	
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
		return creditCards;
	}
	
	public CreditCards updateExpiration(CreditCards creditCard, java.util.Date newExpiration) throws SQLException {
		String updateCreditCard = "UPDATE CreditCards SET Expiration=?";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCreditCard);
			
			updateStmt.setDate(1, new java.sql.Date(newExpiration.getTime()));
			updateStmt.executeUpdate();
			
			// Update the creditCard parameters before returning it
			creditCard.setExpiration(newExpiration);
			return creditCard;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
		
	}
	
	public CreditCards delete(CreditCards creditCard) throws SQLException {
		String deleteCreditCard = "DELETE FROM CreditCards WHERE CardNumber=?";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCreditCard);
			
			deleteStmt.setLong(1, creditCard.getCardNumber());
			deleteStmt.executeUpdate();
			
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

