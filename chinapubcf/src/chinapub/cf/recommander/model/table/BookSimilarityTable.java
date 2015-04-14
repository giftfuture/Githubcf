package chinapub.cf.recommander.model.table;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.similarity.GenericItemSimilarity;

import chinapub.cf.recommander.model.BookSimilarity;
import chinapub.cf.recommander.utils.DBUtil;
public class BookSimilarityTable implements Serializable{

	/**
	 * @date Jul 31,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = 4140102571960515611L;
	
	public final static String TABLE_NAME = "booksimilarity";
	public final static String BOOK_ID_1 = "bookId1";
	public final static String BOOK_ID_2 = "bookId2";
	public final static String SIMILARITY = "similarity";
	
	public static void insertSimilarity(List<BookSimilarity> similarities){
		Connection conn = DBUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "insert into "
				+ TABLE_NAME + " ( "
				+ BOOK_ID_1 + ", "
				+ BOOK_ID_2 + ", "
				+ SIMILARITY 
				+ ") values (?, ?, ?)";
		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql);

			for (BookSimilarity similarity : similarities) {
				ps.setInt(1, similarity.getBookId1());
				ps.setInt(2, similarity.getBookId2());
				ps.setDouble(3, similarity.getSimilarity());
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
	
	public static GenericItemSimilarity.ItemItemSimilarity constructBookSimilarityFromResultSet(ResultSet rs){
		try {
			long book1 = rs.getInt(BOOK_ID_1);
			long book2 = rs.getInt(BOOK_ID_2);
			float rel = rs.getFloat(SIMILARITY);
			GenericItemSimilarity.ItemItemSimilarity similarity = new GenericItemSimilarity.ItemItemSimilarity(book1, book2, rel);
			return similarity;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<GenericItemSimilarity.ItemItemSimilarity> getAllBookSimilarities(){
		List<GenericItemSimilarity.ItemItemSimilarity> similarities = new ArrayList<GenericItemSimilarity.ItemItemSimilarity>();
		
		String sql = "SELECT * FROM " + TABLE_NAME;
		
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
				GenericItemSimilarity.ItemItemSimilarity similarity = constructBookSimilarityFromResultSet(rs);
				if(similarity != null){
					similarities.add(similarity);
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
		return similarities;
	}
	
}
