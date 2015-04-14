package chinapub.cf.recommander.model.table;

import java.io.Serializable;
//import java.io.Externalizable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chinapub.cf.recommander.model.Book;
import chinapub.cf.recommander.utils.DBUtil;
import chinapub.cf.recommander.utils.StringUtil;

public class BookTable  implements Serializable{

	/**
	 * @date Jul 31,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = -8077577413392254810L;
	
	public final static String TABLE_NAME = "book";
	public final static String ID_COLUMN = "bookId";
	public final static String NAME_COLUMN = "name";
	public final static String ORIGINALNAME_COLUMN = "originalName";
	public final static String AUTHOR_COLUMN = "author";
	public final static String TRANSLATOR_COLUMN = "translator";
	public final static String PRESS_COLUMN = "press";
	public final static String SERIESNAME_COLUMN = "seriesName";
	public final static String ISBN_COLUMN = "isbn";
	public final static String VERSION_COLUMN = "version";
	public final static String PRESSTIME_COLUMN = "pressTime";
	public final static String SHELFTIME_COLUMN = "shelfTime";
	public final static String CATEGORY_COLUMN = "category";
	public final static String PRICE_COLUMN = "price";
	public final static String VIPPRICE_COLUMN = "vipPrice";
	public final static String SCHOOLPRICE_COLUMN = "schoolPrice";
	public final static String ACTIVITY_COLUMN = "activity";
	public final static String SALES_COLUMN = "sales";
	
	public static void insertBook(Book book){
		
	}
	
	public static void insertBooks(List<Book> books){
		Connection conn = DBUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "insert into "
				+ TABLE_NAME + " ( "
				//+ ID_COLUMN + ", "
				+ ORIGINALNAME_COLUMN + ", "
				+ AUTHOR_COLUMN + ", "
				+ NAME_COLUMN + ", "
				+ TRANSLATOR_COLUMN + ", "
				+ PRESS_COLUMN + ", "
				+ SERIESNAME_COLUMN + ", "
				+ ISBN_COLUMN + ", "
				+ PRESSTIME_COLUMN + ", "
				+ VERSION_COLUMN + ", "
				+ SHELFTIME_COLUMN + ", "
				+ CATEGORY_COLUMN + ", "
				+ PRICE_COLUMN + ", "
				+ VIPPRICE_COLUMN + ", "
				+ SCHOOLPRICE_COLUMN + ", "
				+ ACTIVITY_COLUMN + ", "
				+ SALES_COLUMN 
				+ ") values (?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql);

			for (Book book : books) {
				ps.setString(1, book.getOriginalName());
				ps.setString(2, book.getAuthor());
				ps.setString(3, book.getName());
				ps.setString(4, book.getTranslator());
				ps.setString(5, book.getPress());
				ps.setString(6, book.getSeriesName());
				ps.setString(7, book.getIsbn());
				ps.setString(8, book.getPressTime());
				ps.setString(9, book.getVersion());
				ps.setString(10, book.getShelfTime());
				ps.setString(11, book.getCategory());
				ps.setFloat(12, book.getPrice());
				ps.setFloat(4, book.getVipPrice());
				ps.setFloat(2, book.getSchoolPrice());
				ps.setString(3, book.getActivity());
				ps.setInt(4, book.getSales());
				ps.addBatch();
			}
			
			ps.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Book constructBookFromResultSet(ResultSet rs){
		try {
			Book book = new Book();
			book.setBookId(rs.getInt(ID_COLUMN));
			book.setName(rs.getString(NAME_COLUMN));
			book.setOriginalName(rs.getString(ORIGINALNAME_COLUMN));
			book.setAuthor(rs.getString(AUTHOR_COLUMN));
			book.setTranslator(rs.getString(TRANSLATOR_COLUMN));
			book.setCategory(rs.getString(CATEGORY_COLUMN));
			book.setSeriesName(rs.getString(SERIESNAME_COLUMN));
			book.setPress(rs.getString(PRESS_COLUMN));
			book.setPressTime(rs.getString(PRESSTIME_COLUMN));
			book.setIsbn(rs.getString(ISBN_COLUMN));
			book.setPrice(rs.getFloat(PRICE_COLUMN));
			book.setVersion(rs.getString(VERSION_COLUMN));
			book.setSales(rs.getInt(SALES_COLUMN));
			book.setSchoolPrice(rs.getFloat(SCHOOLPRICE_COLUMN));
			book.setShelfTime(rs.getString(SHELFTIME_COLUMN));
			book.setActivity(rs.getString(ACTIVITY_COLUMN));
			book.setVipPrice(rs.getFloat(VIPPRICE_COLUMN));
			return book;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Book> getBooks(Collection<String> bookIds){
		List<Book> books = new ArrayList<Book>();
		String bookIdString = StringUtil.connectString(bookIds, ", ");
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " IN ( " + bookIdString + " )";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnectionFromDataSource();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
//			conn = DBUtil.getConnection();
//			pstmt = conn.prepareStatement(sql);
//			rs = pstmt.executeQuery();
			while (rs.next()) {
				Book book = constructBookFromResultSet(rs);
				if(book != null){
					books.add(book);
				}
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return books;
	}
	
	public static Map<String, Book> getBookMap(Collection<String> bookIds){
		Map<String, Book> books = new HashMap<String, Book>();
		String bookIdString = StringUtil.connectString(bookIds, ", ");
		if(!bookIdString.trim().equals("") && bookIdString != null){
			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " IN ( " + bookIdString + " )";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnectionFromDataSource();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
//			conn = DBUtil.getConnection();
//			pstmt = conn.prepareStatement(sql);
//			rs = pstmt.executeQuery();
			while (rs.next()) {
				Book book = constructBookFromResultSet(rs);
				if(book != null){
					books.put(String.valueOf(book.getBookId()), book);
				}
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		}
		return books;
	}
	
	public static List<Book> getAllBooks(Integer pageNo,Integer limit){
		List<Book> books = new ArrayList<Book>();
		
		String sql = "SELECT * FROM " + TABLE_NAME+" limit " +(pageNo-1)*limit+","+limit;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
//			conn = DBUtil.getConnectionFromDataSource();
//			pstmt = conn.prepareStatement(sql);
//			rs = pstmt.executeQuery();
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Book book = constructBookFromResultSet(rs);
				if(book != null){
					books.add(book);
				}
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return books;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int countAllBooks(){
		
		String sql = "SELECT count(*) FROM " + TABLE_NAME;
		Connection conn = null;  
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
//			conn = DBUtil.getConnectionFromDataSource();
//			pstmt = conn.prepareStatement(sql);
//			rs = pstmt.executeQuery();
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
		        int numberOfRows = rs.getInt(1);
		        System.out.println("numberOfRows= " + numberOfRows);
		        return numberOfRows;
		      } else {
		        System.out.println("error: could not get the record counts");
		      }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					rs.close();
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return 0;
	}
	
}
