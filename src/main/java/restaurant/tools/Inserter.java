package restaurant.tools;

import restaurant.dal.*;
import restaurant.model.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;



public class Inserter {


	public static void main(String[] args) throws SQLException, ParseException {
		// Instantiate DAO instances.
		UsersDao usersDao = UsersDao.getInstance();
		CreditCardsDao creditCardsDao = CreditCardsDao.getInstance();
		CompaniesDao companiesDao = CompaniesDao.getInstance();
		RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
		SitDownRestaurantsDao sdrDao = SitDownRestaurantsDao.getInstance();
		TakeOutRestaurantsDao torDao = TakeOutRestaurantsDao.getInstance();
		ReviewsDao reviewsDao = ReviewsDao.getInstance();
		
		// INSERT objects from our model.
		// dao.create <==> INSERT statement 
		Users user1 = new Users("Bruce","password","Bruce","C","bruce@mail.com","5555555");
		Users user2 = new Users("TT","password","Tony","D","tony@mail.com","5555555");
		Users user3 = new Users("DK","password","Daniel","K","dan@mail.com","5555555");
		Users user4 = new Users("James","password","James","M","james@mail.com","5555555");
		Users user5 = new Users("Steve","password","Steve","N","steve@mail.com","5555555");
		Users user6 = new Users("XX","password","Someone","X","somex@mail.com","5555555");
		user1 = usersDao.create(user1);
		user2 = usersDao.create(user2);
		user3 = usersDao.create(user3);
		user4 = usersDao.create(user4);
		user5 = usersDao.create(user5);
		user5 = usersDao.create(user6);
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date expDate = formatter.parse("2018-10-01");
		CreditCards creditCard1 = new CreditCards(Long.parseLong("3499432187650987"),
				expDate,user1);
		CreditCards creditCard2 = new CreditCards(Long.parseLong("6011432187650987"),
				expDate,user1);
		creditCard1 = creditCardsDao.create(creditCard1);
		creditCard2 = creditCardsDao.create(creditCard2);
		
		Companies company1 = new Companies("company1","about company1");
		Companies company2 = new Companies("company2","about company2");
		Companies company3 = new Companies("company3","about company3");
		Companies companyX = new Companies("companyX","about companyX");
		company1 = companiesDao.create(company1);
		company2 = companiesDao.create(company2);
		company3 = companiesDao.create(company3);
		companyX = companiesDao.create(companyX);


		SitDownRestaurants sdr1 = new SitDownRestaurants("restaurant1","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("AFRICAN"),"street1","street2","seattle","wa",98195,company1, 100);
		SitDownRestaurants sdr2= new SitDownRestaurants("restaurant2","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("AMERICAN"),"street1","street2","seattle","wa",98195,company1, 200);
		SitDownRestaurants sdr3 = new SitDownRestaurants("restaurant3","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("ASIAN"),"street1","street2","seattle","wa",98195,company1, 200);
		sdr1 = sdrDao.create(sdr1);
		// System.out.println("!!! sdr1:"+ sdr1.getName()+ " " + sdr1.getRestaurantId());

		sdr2 = sdrDao.create(sdr2);
		// System.out.println("!!! sdr2:"+ sdr2.getName()+ " " + sdr1.getRestaurantId());
		sdr3 = sdrDao.create(sdr3);
		
		TakeOutRestaurants tor1 = new TakeOutRestaurants("restaurant4","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("EUROPEAN"),"street1","street2","seattle","wa",98195,company1, 60);
		TakeOutRestaurants tor2 = new TakeOutRestaurants("restaurant5","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("HISPANIC"),"street1","street2","seattle","wa",98195,company1, 90);
		tor1 = torDao.create(tor1);
		tor2 = torDao.create(tor2);
		
		
//		Restaurants restaurant1 = new Restaurants("restaurant1","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("AFRICAN"),"street1","street2","seattle","wa",98195,company1);
//		Restaurants restaurant2 = new Restaurants("restaurant2","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("AMERICAN"),"street1","street2","seattle","wa",98195,company1);
//		Restaurants restaurant3 = new Restaurants("restaurant3","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("ASIAN"),"street1","street2","seattle","wa",98195,company1);
//		Restaurants restaurant4 = new Restaurants("restaurant4","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("EUROPEAN"),"street1","street2","seattle","wa",98195,company1);
//		Restaurants restaurant5 = new Restaurants("restaurant5","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("HISPANIC"),"street1","street2","seattle","wa",98195,company1);
		Restaurants restaurant6 = new Restaurants("restaurant6","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("HISPANIC"),"street1","street2","bellevue","wa",98008,company2);
		Restaurants restaurant7 = new Restaurants("restaurant7","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("HISPANIC"),"street1","street2","bellevue","wa",98008,company2);
		Restaurants restaurant8 = new Restaurants("restaurant8","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("HISPANIC"),"street1","street2","bellevue","wa",98008,company2);
		Restaurants restaurant9 = new Restaurants("restaurant9","about restaurant","menu","hours",false,Restaurants.Cuisine.valueOf("AMERICAN"),"street1","street2","bellevue","wa",98008,company2);
		Restaurants restaurant10 = new Restaurants("restaurant10","about restaurant","menu","hours",true,Restaurants.Cuisine.valueOf("AMERICAN"),"street1","street2","bellevue","wa",98008,company3);
//		restaurant1 = restaurantsDao.create(restaurant1);
//		restaurant2 = restaurantsDao.create(restaurant2);
//		restaurant3 = restaurantsDao.create(restaurant3);
//		restaurant4 = restaurantsDao.create(restaurant4);
//		restaurant5 = restaurantsDao.create(restaurant5);
		restaurant6 = restaurantsDao.create(restaurant6);
		restaurant7 = restaurantsDao.create(restaurant7);
		restaurant8 = restaurantsDao.create(restaurant8);
		restaurant9 = restaurantsDao.create(restaurant9);
		restaurant10 = restaurantsDao.create(restaurant10);
		
		
		Reviews review1 = new Reviews("Delightful!",BigDecimal.valueOf(5.0),user1,sdr1);
		Reviews review2 = new Reviews("Superb!",BigDecimal.valueOf(5.0),user1,sdr2);
		Reviews review3 = new Reviews("Superb!",BigDecimal.valueOf(5.0),user1, restaurant9);
		Reviews review4 = new Reviews("Not good",BigDecimal.valueOf(1.0),user4,restaurant9);
		review1 = reviewsDao.create(review1);
		review2 = reviewsDao.create(review2);
		review3 = reviewsDao.create(review3);
		review4 = reviewsDao.create(review4);

		// READ/SELECT.
		// dao.getxxxx <==> SELECT statement
		Users u1 = usersDao.getUserByUserName("James");
		System.out.format("Reading user: u:%s pw:%s f:%s l:%s email:%s tel:%s\n",
				u1.getUserName(), u1.getPassword(), u1.getFirstName(), u1.getLastName(),
				u1.getEmail(), u1.getPhone()
		);
		
		CreditCards card1 = creditCardsDao.getCreditCardByCardNumber(Long.parseLong("6011432187650987"));
		System.out.format("Reading credit card: No.:%s Exp:%s UserName:%s\n",
				card1.getCardNumber(), card1.getExpiration(), card1.getUser().getUserName()
		);
		List<CreditCards> cardList1 = creditCardsDao.getCreditCardsByUserName("Bruce");
		for (CreditCards card: cardList1 ) {
			System.out.format("Reading credit card: No.:%s Exp:%s UserName:%s\n",
					card.getCardNumber(), card.getExpiration(), card.getUser().getUserName()
			);
		}
		
		Companies cmpn1 = companiesDao.getCompanyByCompanyName("company1");
		System.out.format("Reading company: name:%s about:%s\n",
			cmpn1.getCompanyName(), cmpn1.getAbout()
		);
		
		Restaurants rstrnt1 = restaurantsDao.getRestaurantById(10);
		System.out.format("Reading restaurant by Id: id:%s name:%s description:%s\n",
				rstrnt1.getRestaurantId(),rstrnt1.getName(), rstrnt1.getDescription()
		);
		List<Restaurants> rstrntList1 = restaurantsDao.getRestaurantsByCuisine(Restaurants.Cuisine.valueOf("HISPANIC"));
		for (Restaurants r: rstrntList1 ) {
			System.out.format("Reading restaurant by Cuisine: name:%s cuisine:%s\n",
				r.getName(), r.getCuisin().name()
			);
		}
		List<Restaurants> rstrntList2 = restaurantsDao.getRestaurantsByCompanyName("company1");
		for (Restaurants r: rstrntList2 ) {
			System.out.format("Reading restaurant by CompanyName: id:%s name:%s company:%s\n",
				r.getRestaurantId(),r.getName(), r.getCompany().getCompanyName()
			);
		}
		
		SitDownRestaurants sdr = sdrDao.getSitDownRestaurantById(2);
		System.out.format("Reading SitDownRestaurant by Id: id:%s name:%s capacity:%s\n",
				sdr.getRestaurantId(),sdr.getName(), sdr.getCapacity()
		);
		
		List<SitDownRestaurants> sdrList = sdrDao.getSitDownRestaurantsByCompanyName("company1");
		for (Restaurants r: sdrList) {
			System.out.format("Reading SitDownRestaurant by CompanyName: id:%s name:%s company:%s\n",
				r.getRestaurantId(),r.getName(), r.getCompany().getCompanyName()
			);
		}
		
		TakeOutRestaurants tor = torDao.getTakeOutRestaurantById(5);
		System.out.format("Reading TakeOutRestaurant by Id: id:%s name:%s maxWaitTime:%s\n",
				tor.getRestaurantId(),tor.getName(), tor.getMaxWaitTime()
		);
		
		List<TakeOutRestaurants> torList = torDao.getTakeOutRestaurantsByCompanyName("company1");
		for (Restaurants r: torList) {
			System.out.format("Reading TakeOutRestaurant by CompanyName: id:%s name:%s company:%s\n",
				r.getRestaurantId(),r.getName(), r.getCompany().getCompanyName()
			);
		}
		
		Reviews review = reviewsDao.getReviewById(2);
		System.out.format("Reading Review by Id: id:%s created:%s content:%s username:%s restaurantId:%s\n",
			review.getReviewId(), review.getCreated(), review.getContent(),
			review.getUser().getUserName(), review.getRestaurant().getRestaurantId()
		);
		
		List<Reviews> reviewList1 = reviewsDao.getReviewsByUserName("Bruce");
		for (Reviews r: reviewList1) {
			System.out.format("Reading Reviews by UserName: Reviewid:%s content:%s username:%s restaurantId:%s\n",
				r.getReviewId(), r.getContent(), r.getUser().getUserName(), r.getRestaurant().getRestaurantId()
			);
		}
		
		List<Reviews> reviewList2 = reviewsDao.getReviewsByRestaurantId(9);
		for (Reviews r: reviewList2) {
			System.out.format("Reading Reviews by UserName: Reviewid:%s content:%s username:%s restaurantId:%s\n",
				r.getReviewId(), r.getContent(), r.getUser().getUserName(), r.getRestaurant().getRestaurantId()
			);
		}


		// UPDATE
		// dao.updatexxxx <==> UPDATE statement
		creditCardsDao.updateExpiration(creditCard2, new Date());
		System.out.format("Updating credit card exp date: No.:%s Exp:%s UserName:%s\n",
				creditCard2.getCardNumber(), creditCard2.getExpiration(), creditCard2.getUser().getUserName()
		);
		
		companiesDao.updateAbout(company1, "Sth new about Company1!!");
		System.out.format("Updating company: name:%s about:%s\n",
				company1.getCompanyName(), company1.getAbout()
		);
		
		
		// DELETE
		//dao.delete <==> DELETE statement
		usersDao.delete(user6);
		creditCardsDao.delete(creditCard1);
		companiesDao.delete(companyX);
		restaurantsDao.delete(restaurant8);
		sdrDao.delete(sdr2);
		torDao.delete(tor2);
		reviewsDao.delete(review2);
	}
}
