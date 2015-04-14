package chinapub.cf.recommander.model;

import java.io.Serializable;
public class UserSimilarity implements Serializable{

	/**
	 * @date Jul 30,2014
	 */
	private static final long serialVersionUID = -5123582926007002948L;
	
	
	
	public final static String USIMILARITYID = "usimilarityId";
	public final static String USERID1 = "userId1";
	public final static String USERID2 = "userId2";
	public final static String USIMILARITY = "usimilarity";
	
	private int usimilarityId; //记录主键
	private int userId1;
	private int userId2;	   //用户２ＩＤ
	private float usimilarity;
	
	public UserSimilarity(int usimilarityId,int userId1,int userId2,float usimilarity){
		this.usimilarityId = usimilarityId ;
		this.userId1 = userId1;
		this.userId2 = userId2;
		this.usimilarity = usimilarity;
	}
	public UserSimilarity(){}
	
	public int getUsimilarityId() {
		return usimilarityId;
	}
	public void setUsimilarityId(int usimilarityId) {
		this.usimilarityId = usimilarityId;
	}
	public int getUserId1() {
		return userId1;
	}
	public void setUserId1(int userId1) {
		this.userId1 = userId1;
	}
	public int getUserId2() {
		return userId2;
	}
	public void setUserId2(int userId2) {
		this.userId2 = userId2;
	}
	public float getUsimilarity() {
		return usimilarity;
	}
	public void setUsimilarity(float usimilarity) {
		this.usimilarity = usimilarity;
	}
	public String toJSON(){
		StringBuilder sb = new StringBuilder();
		sb.append("{'" + USIMILARITYID + "':'" + usimilarityId + "', ");
		sb.append("'" + USERID1 + "':'" + userId1 + "', ");
		sb.append("'" + USERID2 + "':'" + userId2 + "', ");
		sb.append("'" + USIMILARITY + "':'" + usimilarity + "'} ");
		return sb.toString();
	}
}
