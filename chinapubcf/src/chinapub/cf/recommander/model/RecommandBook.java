package chinapub.cf.recommander.model;

import java.io.Serializable;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

public class RecommandBook implements Serializable {

	/**
	 * @date Aug 1,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = -2574236686517976401L;
	private static final String BOOK = "book";
	private static final String VALUE = "score";
	
	
	private Book book;
	private float value;
	
	public RecommandBook(Book book, float value){
		this.book = book;
		this.value = value;
	}
	
	public Book getMBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Book:\t" + book.toString() + "\t");
		sb.append("Score:\t" + value);
		return sb.toString();
	}
	
	public String toJSON(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"" + BOOK + "\":" + book.toJSON() + ",");
		sb.append("\"" + VALUE + "\":" + value + "}");
		return sb.toString();
	}
	
}
