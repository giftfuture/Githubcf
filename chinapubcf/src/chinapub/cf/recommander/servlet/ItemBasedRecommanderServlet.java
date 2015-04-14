package chinapub.cf.recommander.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import chinapub.cf.recommander.model.RecommandBook;
import chinapub.cf.recommander.model.RecommandBookList;
import chinapub.cf.recommander.recommander.ItemBasedRecommander;
import chinapub.cf.recommander.recommander.SlopeOneRecommander;

public class ItemBasedRecommanderServlet extends HttpServlet {
	
	 /**
	 * @date Jul 31,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = 2347028114337517238L;
	
	private static final int NUM_TOP_PREFERENCES = 20; 
    private static final int DEFAULT_HOW_MANY = 20; 

	    private Recommender itemrecommander; 
    	  
	    @Override 
	    public void init(ServletConfig config) throws ServletException { 
	        super.init(config); 
	        
			 // 浠�web.xml 涓鍙栭渶瑕佸垱寤虹殑鎺ㄨ崘寮曟搸绫诲悕
	        /* 
	         * <servlet> 
	         * 	 <servlet-name>movie-recommender</servlet-name> 
	         * 	 <display-name>Movie Recommender</display-name> 
	         * 	 <description>Movie recommender servlet</description> 
	         * 	 <servlet-class> 
	         *      com.ibm.taste.example.movie.servlet.MovieRecommenderServlet 
	         *  </servlet-class> 
	         * 	 <init-param> 
	         * 		 <param-name>recommender-class</param-name> 
	         * 		 <param-value> 
	         *          com.ibm.taste.example.movie.recommender.UserBasedRecommender 
	         *      </param-value> 
	         * 	 </init-param> 
	         * 	 <load-on-startup>1</load-on-startup> 
	         * </servlet> 
	         */ 
	        
	        //String recommenderClassName = config.getInitParameter("recommander-class"); 
	        String itemrecommenderClassName = config.getInitParameter("itemrecommander"); 
	        if(itemrecommenderClassName == null) { 
	            throw new ServletException("Servlet init-param \"recommander-class\"  at least has one  has been defined"); 
	        } 
			 try { 
	            ItemBasedRecommanderSingleton.initializeIfNeeded(itemrecommenderClassName); 
	            itemrecommander = ItemBasedRecommanderSingleton.getInstance().getRecommender(); 
	        } catch (TasteException te) { 
	            throw new ServletException(te); 
	        } 
	    } 

	    @Override 
	    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException { 
			 //Parameters.USER_ID = "userID"
	        String userIDString = request.getParameter(Parameters.USER_ID);  
	        if (userIDString == null) { 
	            throw new ServletException("userId was not specified"); 
	        } 
	        long userID = Long.parseLong(userIDString); 
	        String howManyString = request.getParameter(Parameters.COUNT);  	
	        //Parameters.COUNT = "count"
	        int howMany = howManyString == null ? DEFAULT_HOW_MANY : Integer.parseInt(howManyString); 
	        String format = request.getParameter(Parameters.FORMAT);  	
	        boolean debug = Boolean.parseBoolean(request.getParameter("debug"));
	        //Parameters.FORMAT = "format"
	        if (format == null) { 
	            format = "text"; 
	        } 
	        try { 
	            // 涓烘寚瀹氱敤鎴疯绠楁帹鑽愮殑鍥句功
	        	//List<RecommendedItem> items = new ArrayList<RecommendedItem>();
	        	List<RecommendedItem> items = itemrecommander.recommend(userID, howMany); 
	            // 鍔犺浇鍥句功鐨勭浉鍏充俊鎭紝RecommendBookList 鏄繚瀛樹簡涓�粍鎺ㄨ崘鍥句功鐨勭浉鍏充俊鎭拰
	            // 寮曟搸璁＄畻寰楀埌鐨勪粬浠殑 ranking 
	            RecommandBookList bookList = new RecommandBookList(items); 
	            if ("text".equals(format)) { 
	                writePlainText(response, userID, debug, items, bookList); 
	            } else if ("xml".equals(format)) {
					writeXML(response, items);
				} else if ("json".equals(format)) {
					writeJSON(response, bookList);
				} else {
					throw new ServletException("Bad format parameter: " + format);
				}
	        } catch (TasteException te) { 
	            throw new ServletException(te); 
	        } catch (IOException ioe) { 
	            throw new ServletException(ioe); 
	        } 
	        
	    } 
	    //details please refer to the src code 
	    /**
	     * 
	     * @param response
	     * @param booklist
	     */
	    public void writePlainText(HttpServletResponse response,RecommandBookList booklist) {
	    	response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.print(booklist.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
	    /**
	     * 
	     * @param response
	     * @param booklist
	     */
	    private void writeJSON(HttpServletResponse response,RecommandBookList booklist) {
	    	response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
				writer.print(booklist.toJSON());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    /**
	     * 
	     * @param response
	     * @param items
	     * @throws IOException
	     */
		private void writeXML(HttpServletResponse response,
				Iterable<RecommendedItem> items) throws IOException {
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer
					.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?><recommendedItems>");
			for (RecommendedItem recommendedItem : items) {
				writer.print("<item><value>");
				writer.print(recommendedItem.getValue());
				writer.print("</value><id>");
				writer.print(recommendedItem.getItemID());
				writer.print("</id></item>");
			}
			writer.println("</recommendedItems>");
		}
		/**
		 * 
		 * @param response
		 * @param userID
		 * @param debug
		 * @param items
		 * @param movieList
		 * @throws IOException
		 * @throws TasteException
		 */
		private void writePlainText(HttpServletResponse response, long userID,
				boolean debug, Iterable<RecommendedItem> items,
				RecommandBookList bookList) throws IOException, TasteException {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			if (debug) {
				writeDebugRecommendations(userID, items, writer);
			} else {
				writeRecommendations(bookList, writer);
			}
		}
		/**
		 * 
		 * @param bookList
		 * @param writer
		 */
		private static void writeRecommendations(RecommandBookList bookList,
				PrintWriter writer) {
			for (RecommandBook recommendedItem : bookList.getRecommandbooks()) {
				writer.println(recommendedItem.toString());
			}
		}
		/**
		 * 
		 * @param userID
		 * @param items
		 * @param writer
		 * @throws TasteException
		 */
		private void writeDebugRecommendations(long userID,Iterable<RecommendedItem> items, PrintWriter writer)throws TasteException {
			DataModel idataModel = itemrecommander.getDataModel();
			writer.print("User:");
			writer.println(userID);
			writer.print("ItemRecommander: ");
			writer.println(itemrecommander);
			writer.println();
			writer.print("Top ");
			writer.print(NUM_TOP_PREFERENCES);
			writer.println(" Preferences:");
			PreferenceArray irawPrefs = idataModel.getPreferencesFromUser(userID);
		//未完待补
			int length =20;// rawPrefs.length();
			PreferenceArray sortedPrefs = irawPrefs.clone();
			sortedPrefs.sortByValueReversed();
			// Cap this at NUM_TOP_PREFERENCES just to be brief
			int max = Math.min(NUM_TOP_PREFERENCES, length);
			for (int i = 0; i < max; i++) {
				Preference pref = sortedPrefs.get(i);
				writer.print(pref.getValue());
				writer.print('\t');
				writer.println(pref.getItemID());
			}
			writer.println();
			writer.println("Recommendations:");
			for (RecommendedItem recommendedItem : items) {
				writer.print(recommendedItem.getValue());
				writer.print('\t');
				writer.println(recommendedItem.getItemID());
			}
		}
		
		@Override
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException {
			doGet(request, response);
		}

		@Override
		public String toString() {
			return "RecommenderServlet[itemrecommander:"+itemrecommander+"]";
		}

		public Recommender getItemrecommander() {
			return itemrecommander;
		}

		public void setItemrecommander(Recommender itemrecommander) {
			this.itemrecommander = itemrecommander;
		}

	

}
