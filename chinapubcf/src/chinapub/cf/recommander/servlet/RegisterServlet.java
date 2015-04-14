package chinapub.cf.recommander.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.MessageDigest;

import chinapub.cf.recommander.model.User;
import chinapub.cf.recommander.model.table.UserTable;
import chinapub.cf.recommander.utils.MD5Util;
import chinapub.cf.recommander.utils.StringUtil;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter(Parameters.EMAIL);
		String usrpwd = request.getParameter(Parameters.USER_PWD);
		String name = request.getParameter(Parameters.NAME);
		String knowfrom = request.getParameter(Parameters.KNOW_FROM);
		String status = request.getParameter(Parameters.STATUS);
		String admirefield = request.getParameter(Parameters.ADMIRE_FIELD);
		String expertat = request.getParameter(Parameters.EXPERT_AT);
		String tag = request.getParameter(Parameters.TAG);
		User user = new User();
			email = StringUtil.isNotNull(email)?email:"";
			user.setEmail(email);
			String md5pwd = MD5Util.MD5(usrpwd);
			String nullpwd = MD5Util.MD5("");
			usrpwd = StringUtil.isNotNull(usrpwd)?md5pwd : nullpwd;
			user.setUsrpwd(usrpwd);
			name = StringUtil.isNotNull(name)?name:"";
			user.setName(name);
			status = StringUtil.isNotNull(status)?status:"";
			user.setStatus(status);;
			admirefield = StringUtil.isNotNull(admirefield)?admirefield:"";
			user.setAdmireField(admirefield);
			expertat = StringUtil.isNotNull(expertat)?expertat:"";
			user.setExpertat(expertat);
			tag = StringUtil.isNotNull(tag)?tag:"";
			user.setTag(tag);
			knowfrom = StringUtil.isNotNull(knowfrom)?knowfrom:"";
			user.setKnowfrom(knowfrom);
		/*if (userID != null) {
			user = UserTable.getUserByID(userID);
		} else*/
			 UserTable.insertUser(user);
	/*	if(user != null){
			try {
				writeJSON(response, user);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
