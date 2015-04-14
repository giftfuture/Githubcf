package chinapub.cf.recommander.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chinapub.cf.recommander.model.User;
import chinapub.cf.recommander.model.table.BookTable;
import chinapub.cf.recommander.model.table.UserTable;
import chinapub.cf.recommander.utils.StringUtil;


public class UserServlet extends HttpServlet {
	/**
	 * @date Jul 31,2014
	 */
	private static final long serialVersionUID = -9198610291896258019L;
		
	/**
	 * 登录servlet方法
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		//String userID = request.getParameter(Parameters.USER_ID);
		String email = request.getParameter(Parameters.EMAIL);
		String usrpwd = request.getParameter(Parameters.USER_PWD);
		User user = null;
		int bookCount = BookTable.countAllBooks() ;
		System.out.println("bookCount="+bookCount);
		/*if (userID != null) {
			user = UserTable.getUserByID(userID);
		} else*/
		if(StringUtil.isNotNull(email) && StringUtil.isNotNull(usrpwd)){
			user = UserTable.usrLogin(email, usrpwd);
		} else {
			throw new ServletException("user was not specified");
		}
		request.getSession().removeAttribute("pageno");
		request.getSession().removeAttribute("bookCount");
		request.getSession().removeAttribute("allpages");
		request.getSession().setAttribute("pageno", 1);
		request.getSession().setAttribute("bookCount", bookCount);
		request.getSession().setAttribute("allpages", pages(bookCount));
		if(user != null){
			try {
				request.getSession().setAttribute("username",user.getName());//username存入的是用户名字符串 
				request.getSession().setAttribute("uemail", user.getEmail());
				request.getSession().setAttribute("userId", user.getUserId());
				request.getSession().setAttribute("usrpwd", user.getUsrpwd());
				writeJSON(response, user);				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		doGet(request,response);
	}
	/**
	 * 
	 * @param response
	 * @param user
	 * @throws IOException
	 */
	private void writeJSON(HttpServletResponse response, User user) throws IOException {
	    response.setContentType("text/plain; charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter writer = null;
	    try {
			writer = response.getWriter();
			writer.print(user.toJSON());
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	
	/**
	 * 
	 * @param counts
	 * @return
	 */
	private int pages(int counts){
		if(counts%10>0){
			return counts/10+1;
		}else{
			return counts/10;
		}
	}
}
