package chinapub.cf.recommander.model;

import java.io.Serializable;
public class User implements Serializable {
	
	/**
	 * /**
	 * @date Jul 30,2014
	 * @author Fred
	 */
	private static final long serialVersionUID = 6201043915196181074L;
	
	public static final String USERID = "userId";
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String USRPWD = "usrpwd";
	public static final String STATUS = "status";
	public static final String ADMIREFIELD = "admirefield";
	public static final String EXPERTAT = "expertat";
	public static final String TAG = "tag";
	public final static String FROM_COLUMN = "knowfrom";
	
	private int userId;
	private String name;
	private String email;
	private String usrpwd;
	private String status;
	private String admireField;
	private String expertat;
	private String tag;
	private String knowfrom;
	
	public User(){
		
	}
	public User(int uerId, String name, String email,String usrpwd,String status,String admireField,String expertat,String tag,String knowfrom){
		this.userId = uerId;
		this.name = name;
		this.email = email;
		this.usrpwd = usrpwd;
		this.status = status;
		this.expertat = expertat;
		this.admireField = admireField;
		this.tag = tag;
		this.knowfrom = knowfrom;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAdmireField() {
		return admireField;
	}
	public void setAdmireField(String admireField) {
		this.admireField = admireField;
	}
	public String getExpertat() {
		return expertat;
	}
	public void setExpertat(String expertat) {
		this.expertat = expertat;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsrpwd() {
		return usrpwd;
	}
	public void setUsrpwd(String usrpwd) {
		this.usrpwd = usrpwd;
	}
	
	public String getKnowfrom() {
		return knowfrom;
	}
	public void setKnowfrom(String knowfrom) {
		this.knowfrom = knowfrom;
	}
	public String toJSON(){
		StringBuilder sb = new StringBuilder();
		sb.append("{'" + USERID + "':'" + userId + "', ");
/*		sb.append("'" + NAME + "':'" + (name==null?"":name) + "', ");
		sb.append("'" + USRPWD + "':'" + usrpwd + "', ");
		sb.append("'" + STATUS + "':'" + (status==null?"":status) + "', ");
		sb.append("'" + ADMIREFIELD + "':'" + (admireField==null?"":admireField) + "', ");
		sb.append("'" + EXPERTAT + "':'" + (expertat==null?"":expertat) + "', ");
		sb.append("'" + TAG + "':'" + (tag==null?"":tag) + "', ");
		sb.append("'" + EMAIL + "':'" + (email==null?"":email) + "'}");*/
		sb.append("'" + NAME + "':'" + name + "', ");
		sb.append("'" + USRPWD + "':'" + usrpwd + "', ");
		sb.append("'" + STATUS + "':'" + status + "', ");
		sb.append("'" + ADMIREFIELD + "':'" +admireField + "', ");
		sb.append("'" + EXPERTAT + "':'" + expertat + "', ");
		sb.append("'" + TAG + "':'" + tag + "', ");
		sb.append("'" + FROM_COLUMN + "':'" + knowfrom + "', ");
		sb.append("'" + EMAIL + "':'" + email + "'}");
		return sb.toString();
	}
	
}
