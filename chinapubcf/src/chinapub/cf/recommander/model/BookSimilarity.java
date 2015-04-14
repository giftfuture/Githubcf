package chinapub.cf.recommander.model;

import java.io.Serializable;
public class BookSimilarity implements Serializable{

	/**
	 * @date Jul 30,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = -6011149478738325550L;
	
	private int similiarityId;
	private int bookId1;
	private int bookId2;
	private float similarity;
	
	public  BookSimilarity(int similiarityId,int bookId1,int bookId2,float similarity) {
		this.similiarityId = similiarityId;
		this.bookId1 = bookId1;
		this.bookId2 = bookId2;
		this.similarity = similarity;
	}
	public  BookSimilarity(){}
	
	public int getSimiliarityId() {
		return similiarityId;
	}
	public void setSimiliarityId(int similiarityId) {
		this.similiarityId = similiarityId;
	}
	public int getBookId1() {
		return bookId1;
	}
	public void setBookId1(int bookId1) {
		this.bookId1 = bookId1;
	}
	public int getBookId2() {
		return bookId2;
	}
	public void setBookId2(int bookId2) {
		this.bookId2 = bookId2;
	}
	public float getSimilarity() {
		return similarity;
	}
	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}
	
}
