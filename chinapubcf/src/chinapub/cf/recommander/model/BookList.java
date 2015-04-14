package chinapub.cf.recommander.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BookList implements Serializable{
	/**
	 * @date Aug 1,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = -7255440427376589909L;
	public static final String BOOK = "book";
	public static final String VALUE = "score";
	
	private Map<Book, Float> books = new HashMap<Book, Float>();
	
	public void add(Book b, Float f){
		books.put(b, f);
	}
	
	public Float get(Book m){
		return books.get(m);
	}
	
	public Float remove(Book b){
		return books.remove(b);
	}
	
	public String toJSON(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean flag = false;
		for(Map.Entry<Book, Float> item: books.entrySet()){
			if(flag){
				sb.append(", ");
			} else {
				flag = true;
			}
			/*sb.append("{\"" + BOOK + "\":" + item.getKey().toJSON() + ", ");
			sb.append("\"" + VALUE + "\":" + item.getValue() + "}");*/
			sb.append("{'" + BOOK + "':" + item.getKey().toJSON() + ", ");
			sb.append("'" + VALUE + "':" + (item.getValue()=='\u0000'?'0':item.getValue()) + "}");
		}
		sb.append("]");
		return sb.toString();
	}
}
