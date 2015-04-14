package chinapub.cf.recommander.model.table;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.similarity.GenericItemSimilarity;

import chinapub.cf.recommander.model.UserSimilarity;
import chinapub.cf.recommander.utils.DBUtil;
public class UserSimilarityTable implements Serializable{

	/**
	 * @date Jul 31,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = 3403180702191678530L;
	
	public final static String TABLE_NAME = "usersimilarity";
	public final static String USER_ID_1 = "userId1";
	public final static String USER_ID_2 = "userId2";
	public final static String USIMILARITY = "usimilarity";
	
	public static void insertSimilarity(List<UserSimilarity> similarities){
		Connection conn = DBUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "insert into "
				+ TABLE_NAME + " ( "
				+ USER_ID_1 + ", "
				+ USER_ID_2 + ", "
				+ USIMILARITY 
				+ ") values (?, ?, ?)";
		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql);

			for (UserSimilarity similarity : similarities) {
				ps.setInt(1, similarity.getUserId1());
				ps.setInt(2, similarity.getUserId2());
				ps.setFloat(3, similarity.getUsimilarity());
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
	
	public static GenericItemSimilarity.ItemItemSimilarity constructUserSimilarityFromResultSet(ResultSet rs){
		try {
			long userId1 = rs.getInt(USER_ID_1);
			long userId2 = rs.getInt(USER_ID_2);
			float rel = rs.getFloat(USIMILARITY);
			GenericItemSimilarity.ItemItemSimilarity similarity = new GenericItemSimilarity.ItemItemSimilarity(userId1, userId2, rel);
			return similarity;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<GenericItemSimilarity.ItemItemSimilarity> getAllUserSimilarities(){
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
				GenericItemSimilarity.ItemItemSimilarity similarity = constructUserSimilarityFromResultSet(rs);
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
