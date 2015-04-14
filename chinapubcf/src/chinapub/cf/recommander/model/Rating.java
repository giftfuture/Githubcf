package chinapub.cf.recommander.model;

import java.io.Serializable;
public class Rating implements Serializable{
	/**
	 * @date Aug 1,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = 9152685438970301056L;
	
	private int ratingId;
	private int userId;	
	private int bookId;
	private float rating;
	private String timestamp;
	
	public Rating(){}
	public Rating(int userId,int bookId,float rating,String timestamp){
		this.userId = userId;
		this.bookId = bookId;
		this.rating = rating;
		this.timestamp = timestamp;
	}
	
	public int getRatingId() {
		return ratingId;
	}
	public void setRatingId(int ratingId) {
		this.ratingId = ratingId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
