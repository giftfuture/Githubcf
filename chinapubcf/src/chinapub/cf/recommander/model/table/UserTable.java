package chinapub.cf.recommander.model.table;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mortbay.jetty.security.Credential.MD5;

import chinapub.cf.recommander.model.User;
import chinapub.cf.recommander.utils.DBUtil;
import chinapub.cf.recommander.utils.MD5Util;
import chinapub.cf.recommander.utils.StringUtil;
public class UserTable implements Serializable {
	/**
	 * @dateAug 1,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = 1640595581439587852L;
	public final static String TABLE_NAME = "user";
	public final static String ID_COLUMN = "userId";
	public final static String NAME_COLUMN = "name";
	public final static String USRPWD_COLUMN = "usrpwd";
	public final static String EMAIL_COLUMN = "email";
	public final static String STATUS_COLUMN = "status";
	public final static String ADMIREFIELD_COLUMN = "admirefield";
	public final static String EXPERTAT_COLUMN = "expertat";
	public final static String TAG_COLUMN = "tag";
	public final static String FROM_COLUMN = "knowfrom";
	
	
	/**
	 * 批量添加用户
	 * @param users
	 */
	public static void insertUsers(List<User> users){
		Connection conn = DBUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "insert into "
				+ TABLE_NAME + " ( "
				//+ ID_COLUMN + ", "
				+ NAME_COLUMN + ", "
				+ EMAIL_COLUMN +","
				+USRPWD_COLUMN + ","
				+STATUS_COLUMN+","
				+ADMIREFIELD_COLUMN+","
				+EXPERTAT_COLUMN +","
				+TAG_COLUMN +","
				+FROM_COLUMN
				+ ") values (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql);

			for (User user : users) {
				//ps.setInt(1, user.getUserId());
				ps.setString(1, user.getName());
				ps.setString(2, user.getEmail());
				ps.setString(3, MD5.digest(user.getUsrpwd()));
				ps.setString(4, user.getStatus());
				ps.setString(5, user.getAdmireField());
				ps.setString(6, user.getExpertat());
				ps.setString(7, user.getTag());
				ps.setString(8, user.getKnowfrom());
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
	
	/**
	 * 注册用户
	 * @param user
	 */
	public static void insertUser(User user){
		Connection conn = DBUtil.getConnection();
		PreparedStatement ps = null;
		String sql = "insert into "
				+ TABLE_NAME + " ( "
				//+ ID_COLUMN + ", "
				+ NAME_COLUMN + ", "
				+ EMAIL_COLUMN +","
				+USRPWD_COLUMN + ","
				+STATUS_COLUMN+","
				+ADMIREFIELD_COLUMN+","
				+EXPERTAT_COLUMN +","
				+TAG_COLUMN +","
				+FROM_COLUMN
				+ ") values (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement(sql);

				//ps.setInt(1, user.getUserId());
				ps.setString(1, user.getName()==null?"":user.getName());
				ps.setString(2, user.getEmail()==null?"":user.getEmail());
				ps.setString(3, user.getUsrpwd());
				ps.setString(4, user.getStatus()==null?"":user.getStatus());
				ps.setString(5, user.getAdmireField()==null?"":user.getAdmireField());
				ps.setString(6, user.getExpertat()==null?"":user.getExpertat());
				ps.setString(7, user.getTag()==null?"":user.getTag());
				ps.setString(8, user.getKnowfrom()==null?"":user.getKnowfrom());
				ps.execute();
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
	
	/**
	 * 通过ＩＤ获取用户
	 * @param userID
	 * @return
	 */
	public static User getUserByID(String userID){
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " =  " + userID + " ";
		
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
			if (rs.next()) {
				User user = constructUserFromResultSet(rs);
				return user;
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
		return null;
	}
	
	/**
	 * 通过邮箱获取用户
	 * @param email
	 * @return
	 */
	public static User getUserByEmail(String email){
		
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_COLUMN + " =  '" + email + "' ";
		
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
			if (rs.next()) {
				User user = constructUserFromResultSet(rs);
				return user;
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
		return null;
	}
	/**
	 * 用户登录
	 * @param email
	 * @param usrpwd
	 * @return
	 */
	public static User usrLogin(String email,String usrpwd){
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE "+USRPWD_COLUMN+" = '"+MD5Util.MD5(usrpwd)+"' ";
		if(StringUtil.isNotNull(email) && StringUtil.isNotNull(usrpwd)){	
			if(email.indexOf("@")>0){				
				 sql += " and " + EMAIL_COLUMN + " =  '" + email + "' ";
			}else{
				sql += " and " +  NAME_COLUMN + " =  '" + email + "' ";
			}
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnectionFromDataSource();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				User user = constructUserFromResultSet(rs);
				return user;
			}		
		} catch (SQLException e) {
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
		return null;
	}
	/**
	 * 判断用户已登录
	 * @param email
	 * @param usrpwd
	 * @return
	 */
	public static User usrIsLogin(String emailorname,String usrpwd){
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE "+USRPWD_COLUMN+" = '"+usrpwd+"' ";
		if(StringUtil.isNotNull(emailorname) && StringUtil.isNotNull(usrpwd)){	
			if(emailorname.indexOf("@")>0){				
				 sql += " and " + EMAIL_COLUMN + " =  '" + emailorname + "' ";
			}else{
				sql += " and " +  NAME_COLUMN + " =  '" + emailorname + "' ";
			}
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
			if (rs.next()) {
				User user = constructUserFromResultSet(rs);
				return user;
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
		return null;
	}
	
	private static User constructUserFromResultSet(ResultSet rs) {
		try {
			User user = new User();
			user.setUserId(rs.getInt(ID_COLUMN));
			user.setName(rs.getString(NAME_COLUMN));
			user.setEmail(rs.getString(EMAIL_COLUMN));
			user.setUsrpwd(rs.getString(USRPWD_COLUMN));
			user.setStatus(rs.getString(STATUS_COLUMN));
			user.setAdmireField(rs.getString(ADMIREFIELD_COLUMN));
			user.setExpertat(rs.getString(EXPERTAT_COLUMN));
			user.setTag(rs.getString(TAG_COLUMN));
			user.setKnowfrom(rs.getString(FROM_COLUMN));
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
