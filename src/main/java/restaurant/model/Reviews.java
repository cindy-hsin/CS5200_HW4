package restaurant.model;

import java.util.Date;
import java.math.BigDecimal;

public class Reviews {
	protected int reviewId;
	protected Date created;
	protected String content;
	protected BigDecimal rating;
	protected Users user;
	protected Restaurants restaurant;
	
	public Reviews(int reviewId, Date created, String content, BigDecimal rating, Users user, Restaurants restaurant) {
		this.reviewId = reviewId;
		this.created = created;
		this.content = content;
		this.rating = rating;
		this.user = user;
		this.restaurant = restaurant;
	}
	
	
	public Reviews(int reviewId) {
		this.reviewId = reviewId;
	}


	public Reviews(Date created, String content, BigDecimal rating, Users user, Restaurants restaurant) {
		this.created = created;
		this.content = content;
		this.rating = rating;
		this.user = user;
		this.restaurant = restaurant;
	}

	
	// This constructor is used when user creates
	// a new Review with the default "created" value(current time stamp,
	// which is enforced in MySQL). 
	public Reviews(String content, BigDecimal rating, Users user, Restaurants restaurant) {
		this.content = content;
		this.rating = rating;
		this.user = user;
		this.restaurant = restaurant;
	}

	public int getReviewId() {
		return reviewId;
	}


	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public BigDecimal getRating() {
		return rating;
	}


	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}


	public Users getUser() {
		return user;
	}


	public void setUser(Users user) {
		this.user = user;
	}


	public Restaurants getRestaurant() {
		return restaurant;
	}


	public void setRestaurant(Restaurants restaurant) {
		this.restaurant = restaurant;
	}
	
	
	
}
