package chinapub.cf.recommander.model;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;
import chinapub.cf.recommander.utils.StringUtil;

public class Book implements Serializable{
	
	/**
	 * @date Jul 31,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = -1366167527886318171L;
	
	public static final String BOOKID = "bookId";
	public static final String ORIGINALNAME = "originalName";
	public static final String AUTHOR = "author";
	public static final String NAME = "name";
	public static final String TRANSLATOR = "translator";
	public static final String PRESS = "press";
	public static final String SERIESNAME = "seriesName";
	public static final String ISBN = "isbn";
	public static final String PRESSTIME = "pressTime";
	public static final String VERSION = "version";
	public static final String SHELFTIME = "shelfTime";
	public static final String CATEGORY = "category";
	public static final String PRICE = "price";
	public static final String VIPPRICE = "vipPrice";
	public static final String SCHOOLPRICE = "schoolPrice";
	public static final String ACTIVITY = "activity";
	public static final String SALES = "sales";
	
	private int bookId;
	private String originalName;
	private String author;
	private String name;
	private String translator;
	private String press;
	private String seriesName;
	private String isbn;
	private String pressTime;
	private String version;
	private String shelfTime;
	private String category;
	private float price;
	private float vipPrice;
	private float schoolPrice;
	private String activity;
	private int sales;
	
	public Book(){
		
	}
	
	public Book(int bookId,String originalName,String author, String name, String translator, String press,
			String seriesName,String isbn,String pressTime,String version,String shelfTime,String category,
			float price,float vipPrice,float schoolPrice,String activity,int sales){
		this.bookId = bookId;
		this.originalName = originalName;
		this.author = author;
		this.name = name;
		this.translator = translator;
		this.press = press;
		this.seriesName = seriesName;
		this.isbn = isbn;
		this.pressTime = pressTime;
		this.version = version;
		this.shelfTime = shelfTime;
		this.category = category;
		this.price = price;
		this.vipPrice = vipPrice;
		this.schoolPrice = schoolPrice;
		this.activity = activity;
		this.sales = sales;
	}
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTranslator() {
		return translator;
	}

	public void setTranslator(String translator) {
		this.translator = translator;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPressTime() {
		return pressTime;
	}

	public void setPressTime(String pressTime) {
		this.pressTime = pressTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getShelfTime() {
		return shelfTime;
	}

	public void setShelfTime(String shelfTime) {
		this.shelfTime = shelfTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(float vipPrice) {
		this.vipPrice = vipPrice;
	}

	public float getSchoolPrice() {
		return schoolPrice;
	}

	public void setSchoolPrice(float schoolPrice) {
		this.schoolPrice = schoolPrice;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

/*	public double relevance(Book b){
		String patternString = StringUtil.connectString(type, "|");
		Pattern pattern = Pattern.compile(patternString);
		int count = 0;
		for(String mType : b.getType()){
			Matcher matcher = pattern.matcher(mType);
			if( matcher.matches()){
				count ++;
			}
		}
		return Math.log10(count + 1);
	}*/
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Name: " + name + "  ");
	/*	sb.append("Publish Year: " + year + "  ");
		sb.append("Type: " + StringUtil.connectString(type, ", "));*/
		return sb.toString();
	}
	
	public String toJSON(){
		//JSONObject jo = JSONObject.fromObject(object);
		StringBuilder sb = new StringBuilder();
	/*	sb.append("{\"" + BOOKID + "\":" + bookId + ", ");
		sb.append("\"" + ORIGINALNAME + "\":\"" + originalName + "\", ");
		sb.append("\"" + AUTHOR + "\":\"" + author + "\", ");
		sb.append("\"" + NAME + "\":\"" + name + "\", ");
		sb.append("\"" + TRANSLATOR + "\":\"" + translator + "\", ");
		sb.append("\"" + PRESS + "\":\"" + press + "\", ");
		sb.append("\"" + SERIESNAME + "\":\"" + seriesName + "\", ");
		sb.append("\"" + ISBN + "\":\"" + isbn + "\", ");
		sb.append("\"" + PRESSTIME + "\":\"" + pressTime + "\", ");
		sb.append("\"" + VERSION + "\":\"" + version + "\", ");
		sb.append("\"" + SHELFTIME + "\":\"" + shelfTime + "\", ");
		sb.append("\"" + CATEGORY + "\":\"" + category + "\", ");
		sb.append("\"" + PRICE + "\":\"" + price + "\", ");
		sb.append("\"" + VIPPRICE + "\":\"" + vipPrice + "\", ");
		sb.append("\"" + SCHOOLPRICE + "\":\"" + schoolPrice + "\", ");
		sb.append("\"" + ACTIVITY + "\":\"" + activity + "\", ");
		sb.append("\"" + SALES + "\":\"" + sales+ "\"}");*/
		sb.append("{'" + BOOKID + "':" + bookId + ", ");
		sb.append("'" + ORIGINALNAME + "':'" + (originalName==null?"":originalName) + "', ");
		sb.append("'" + AUTHOR + "':'" + (author==null?"":author) + "', ");
		sb.append("'" + NAME + "':'" + (name==null?"":name) + "', ");
		sb.append("'" + TRANSLATOR + "':'" + (translator==null?"":translator) + "', ");
		sb.append("'" + PRESS + "':'" + (press==null?"":press) + "', ");
		sb.append("'" + SERIESNAME + "':'" + (seriesName==null?"":seriesName) + "', ");
		sb.append("'" + ISBN + "':'" + (isbn==null?"":isbn) + "', ");
		sb.append("'" + PRESSTIME + "':'" + (pressTime==null?"":pressTime) + "', ");
		sb.append("'" + VERSION + "':'" + (version==null?"":version) + "', ");
		sb.append("'" + SHELFTIME + "':'" + (shelfTime==null?"":shelfTime) + "', ");
		sb.append("'" + CATEGORY + "':'" + (category==null?"":category) + "', ");
		sb.append("'" + PRICE + "':" + (price=='\u0000'?0:price) + ", ");
		sb.append("'" + VIPPRICE + "':" + (vipPrice=='\u0000'?0:vipPrice) + ", ");
		sb.append("'" + SCHOOLPRICE + "':" + (schoolPrice=='\u0000'?0:schoolPrice) + ", ");
		sb.append("'" + ACTIVITY + "':'" + (activity==null?"":activity) + "', ");
		sb.append("'" + SALES + "':" + (sales=='\u0000'?0:sales)+ "}");
/*		sb.append("{'" + BOOKID + "':" + bookId + ", ");
		sb.append("'" + ORIGINALNAME + "':'" + originalName + "', ");
		sb.append("'" + AUTHOR + "':'" + author + "', ");
		sb.append("'" + NAME + "':'" + name + "', ");
		sb.append("'" + TRANSLATOR + "':'" + translator + "', ");
		sb.append("'" + PRESS + "':'" + press + "', ");
		sb.append("'" + SERIESNAME + "':'" + seriesName + "', ");
		sb.append("'" + ISBN + "':'" + isbn + "', ");
		sb.append("'" + PRESSTIME + "':'" + pressTime + "', ");
		sb.append("'" + VERSION + "':'" + version + "', ");
		sb.append("'" + SHELFTIME + "':'" + shelfTime + "', ");
		sb.append("'" + CATEGORY + "':'" + category + "', ");
		sb.append("'" + PRICE + "':" + price + ", ");
		sb.append("'" + VIPPRICE + "':" + vipPrice + ", ");
		sb.append("'" + SCHOOLPRICE + "':" + schoolPrice + ", ");
		sb.append("'" + ACTIVITY + "':'" + activity + "', ");
		sb.append("'" + SALES + "':" + sales+ "}");*/
		return sb.toString();
	}
}
