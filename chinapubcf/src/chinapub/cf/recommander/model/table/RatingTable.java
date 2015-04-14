package chinapub.cf.recommander.model.table;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;

import org.apache.commons.lang3.StringUtils;

import chinapub.cf.recommander.model.Book;
import chinapub.cf.recommander.model.BookList;
import chinapub.cf.recommander.model.Rating;
import chinapub.cf.recommander.utils.DBUtil;
public class RatingTable implements Serializable{

	/**
	 * @date Jul 31,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = -6084288525967951769L;
	
	
	public final static String TABLE_NAME = "rating";
	public final static String ID_COLUMN = "ratingId";
	public final static String USER_ID_COLUMN = "userId";
	public final static String BOOK_ID_COLUMN = "bookId";
	public final static String RATING_COLUMN = "rating";
	public final static String TIMESTAMP_COLUMN = "timestamp";
	
	public static void insertRating(Rating rating) {
		
	}

	public static void insertRatings(List<Rating> ratings) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "insert into " + TABLE_NAME + " ( " 
		+ USER_ID_COLUMN+ ", " 
		+ BOOK_ID_COLUMN + ", " 
		+ RATING_COLUMN+","
		+ TIMESTAMP_COLUMN
		+ ") values (?, ?, ?, ?)";
		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql);

			for (Rating rating : ratings) {
				ps.setInt(1, rating.getUserId());
				ps.setInt(2, rating.getBookId());
				ps.setFloat(3, rating.getRating());
				//ps.setDate(4, java.sql.Date.);
				ps.setString(4, rating.getTimestamp());
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

	public static BookList getBooksByUserID(String userID) {

		/*String sql = "SELECT * FROM " + TABLE_NAME + " RT RIGHT JOIN "
				+ BookTable.TABLE_NAME + " BT ON  RT."
				+ BOOK_ID_COLUMN + " = BT." + BookTable.ID_COLUMN + " WHERE "
				+ "RT." + USER_ID_COLUMN + " =  " + userID ;*/
		if(StringUtils.isNotEmpty(userID)){
		String sql = "SELECT * FROM (SELECT " + TABLE_NAME + ".* FROM "+TABLE_NAME+
				" WHERE "+TABLE_NAME+"."+  USER_ID_COLUMN + " =  " + userID
				+" ORDER BY "+TABLE_NAME+"."+TIMESTAMP_COLUMN+" DESC )TT RIGHT JOIN "
				+ BookTable.TABLE_NAME + " BT ON  TT."+BOOK_ID_COLUMN +"= BT."+ BookTable.ID_COLUMN 
				+" GROUP BY TT."+USER_ID_COLUMN+",TT."+BOOK_ID_COLUMN ;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BookList books = new BookList();
		try {
			conn = DBUtil.getConnectionFromDataSource();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// conn = DBUtil.getConnection();
			// pstmt = conn.prepareStatement(sql);
			// rs = pstmt.executeQuery();
			while (rs.next()) {
				constructBooksFromResultSet(rs, books);
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
		return null;
	}

	private static void constructBooksFromResultSet(ResultSet rs,BookList books) {
		try {
			Book book = new Book();
			book.setBookId(rs.getInt(BookTable.ID_COLUMN));
			book.setOriginalName(rs.getString(BookTable.ORIGINALNAME_COLUMN));
			book.setAuthor(rs.getString(BookTable.AUTHOR_COLUMN));
			book.setName(rs.getString(BookTable.NAME_COLUMN));
			book.setTranslator(rs.getString(BookTable.TRANSLATOR_COLUMN));
			book.setPress(rs.getString(BookTable.PRESS_COLUMN));
			book.setSeriesName(rs.getString(BookTable.SERIESNAME_COLUMN));
			book.setIsbn(rs.getString(BookTable.ISBN_COLUMN));
			book.setPressTime(rs.getString(BookTable.PRESSTIME_COLUMN));
			book.setVersion(rs.getString(BookTable.VERSION_COLUMN));
			book.setShelfTime(rs.getString(BookTable.SHELFTIME_COLUMN));
			book.setCategory(rs.getString(BookTable.CATEGORY_COLUMN));
			book.setPrice(rs.getFloat(BookTable.PRICE_COLUMN));
			book.setVipPrice(rs.getFloat(BookTable.VIPPRICE_COLUMN));
			book.setSchoolPrice(rs.getFloat(BookTable.SCHOOLPRICE_COLUMN));
			book.setActivity(rs.getString(BookTable.ACTIVITY_COLUMN));
			book.setSales(rs.getInt(BookTable.SALES_COLUMN));
			//String type = rs.getString(MovieTable.TYPE_COLUMN);
			//	book.setType(Arrays.asList(type.split(", ")));
			Float score = rs.getFloat(RATING_COLUMN);
			books.add(book, score);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
