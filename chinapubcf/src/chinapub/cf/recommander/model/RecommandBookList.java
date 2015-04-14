package chinapub.cf.recommander.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import chinapub.cf.recommander.model.table.BookTable;

public class RecommandBookList implements Serializable{

	/**
	 * /**
	 * @date Aug 1,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = 1063393687321695262L;
	
	private List<RecommandBook> recommandbooks = new ArrayList<RecommandBook>();
	
	public RecommandBookList(List<RecommendedItem> items){
		List<String> bookIdList = new ArrayList<String>();
		for (RecommendedItem item : items){
			bookIdList.add(String.valueOf(item.getItemID()));
		}
		
		Map<String, Book> books = BookTable.getBookMap(bookIdList);
		
		for (RecommendedItem item : items){
			String itemId = String.valueOf(item.getItemID());
			Book book = books.get(itemId);
			if(book != null){
				RecommandBook rmb = new RecommandBook(book, item.getValue());
				recommandbooks.add(rmb);
			}
		}
	}
	
	public RecommandBookList(){
	}
	
	public String toJSON() {
		int len = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(RecommandBook recommandbook:recommandbooks){
			if(len == recommandbooks.size()-1){
				sb.append(recommandbook.toJSON());
				break;
			}
			sb.append(recommandbook.toJSON()+",");
			len++;
		}
		//if(sb.lastIndexOf(",")==sb.length()-1){}
		sb.append("]");
		return sb.toString();
	}

	public List<RecommandBook> getRecommandbooks() {
		return recommandbooks;
	}

	public void setRecommandbooks(List<RecommandBook> recommandbooks) {
		this.recommandbooks = recommandbooks;
	}
	
}
