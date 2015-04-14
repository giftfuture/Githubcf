package chinapub.cf.recommander.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import chinapub.cf.recommander.model.RecommandBook;
import chinapub.cf.recommander.model.RecommandBookList;

/**
 * Servlet implementation class ChinapubRecommanderServlet
 */
public class ChinapubRecommanderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int NUM_TOP_PREFERENCES = 20; 
    private static final int DEFAULT_HOW_MANY = 20; 

	    private Recommender itemrecommander; 
	    private Recommender sloperecommander; 
	    private Recommender userrecommander; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChinapubRecommanderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override 
    public void init(ServletConfig config) throws ServletException { 
        super.init(config); 
        //从web.xml配置文件获取推荐类的名称
	    String itemrecommenderClassName = config.getInitParameter("itemrecommander"); 
	    String sloperecommenderClassName = config.getInitParameter("sloperecommander");
	    String userrecommenderClassName = config.getInitParameter("userrecommander"); 
	    if(itemrecommenderClassName == null) { 
	        throw new ServletException("Servlet init-param \"itemrecommenderClassName-class\"   has not been defined"); 
	    } 
		 try { 
			 //使用单例实例化itemrecommander
	        ItemBasedRecommanderSingleton.initializeIfNeeded(itemrecommenderClassName); 
	        itemrecommander = ItemBasedRecommanderSingleton.getInstance().getRecommender(); 
	    } catch (TasteException te) { 
	        throw new ServletException(te); 
	    } 
		 if(sloperecommenderClassName == null) { 
	            throw new ServletException("Servlet init-param \"sloperecommenderClassName-class\"    has not been defined"); 
	        } 
			 try { 
				//使用单例实例化sloperecommander
	            SlopeOneRecommanderSingleton.initializeIfNeeded(sloperecommenderClassName); 
	            sloperecommander = SlopeOneRecommanderSingleton.getInstance().getRecommender(); 
	        } catch (TasteException te) { 
	            throw new ServletException(te); 
	        } 
	        if(userrecommenderClassName == null) { 
	            throw new ServletException("Servlet init-param \"userrecommenderClassName-class\"  has not been defined"); 
	        } 
			 try { 
				//使用单例实例化userrecommander
	            UserBasedRecommanderSingleton.initializeIfNeeded(userrecommenderClassName); 
	            userrecommander = UserBasedRecommanderSingleton.getInstance().getRecommender(); 
	        } catch (TasteException te) { 
	            throw new ServletException(te); 
	        }
    }
	/**把三种推荐算法的结果合并,产生图书推荐结果
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String userIDString = request.getParameter(Parameters.USER_ID);  
	        if (StringUtils.isNotEmpty(userIDString)) { 
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
	            // 基于图书的相似度推荐结果
	        	List<RecommendedItem> items = new ArrayList<RecommendedItem>();
	        	items = itemrecommander.recommend(userID, howMany); 
	        	List<RecommendedItem> newitems = new ArrayList<RecommendedItem>();
	        	//基于用户相似度推荐结果
	        	List<RecommendedItem> uitems = userrecommander.recommend(userID, howMany); 
	        	//基于评分的slopeone推荐结果
	        	List<RecommendedItem> sitems = sloperecommander.recommend(userID, howMany); 
	        	newitems.addAll(items);
	        	newitems.addAll(uitems);
	        	newitems.addAll(sitems);
	            // 鍔犺浇鍥句功鐨勭浉鍏充俊鎭紝RecommendBookList 鏄繚瀛樹簡涓�粍鎺ㄨ崘鍥句功鐨勭浉鍏充俊鎭拰
	            // 寮曟搸璁＄畻寰楀埌鐨勪粬浠殑 ranking 
	        	Set<Long> itemIds = new HashSet<Long>();
	        	List<RecommendedItem> newnewitems = new ArrayList<RecommendedItem>();
	        	Iterator<RecommendedItem> ititems = newitems.iterator();  
	        	while(ititems.hasNext()){
	        		RecommendedItem item = ititems.next();	
	        		if(itemIds.add(item.getItemID())){
	        			newnewitems.add(item);
	        		}
	        	}
	            RecommandBookList bookList = new RecommandBookList(newnewitems); 
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
        } else{
        	   throw new ServletException("userId was not specified"); 
        }
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
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
	public String toString() {
		return "RecommenderServlet[Chinapubrecommander:" + userrecommander+itemrecommander+sloperecommander +"]";
	}
	public Recommender getItemrecommander() {
		return itemrecommander;
	}
	public void setItemrecommander(Recommender itemrecommander) {
		this.itemrecommander = itemrecommander;
	}
	public Recommender getSloperecommander() {
		return sloperecommander;
	}
	public void setSloperecommander(Recommender sloperecommander) {
		this.sloperecommander = sloperecommander;
	}
	public Recommender getUserrecommander() {
		return userrecommander;
	}
	public void setUserrecommander(Recommender userrecommander) {
		this.userrecommander = userrecommander;
	}
	
}
