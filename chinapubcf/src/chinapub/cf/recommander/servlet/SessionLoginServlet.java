package chinapub.cf.recommander.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import chinapub.cf.recommander.model.User;
import chinapub.cf.recommander.model.table.BookTable;
import chinapub.cf.recommander.model.table.UserTable;
import chinapub.cf.recommander.utils.StringUtil;

/**
 * Servlet implementation class SessionLoginServlet
 */
public class SessionLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter(Parameters.EMAIL);
		String usrpwd = request.getParameter(Parameters.USER_PWD);
		String name = request.getParameter(Parameters.NAME);
		User user = null;
		int bookCount = BookTable.countAllBooks() ;
		if(StringUtils.isNotEmpty(email) && !email.equals("null") && StringUtils.isNotEmpty(usrpwd) && !usrpwd.equals("null")){
			user = UserTable.usrIsLogin(email, usrpwd);
			if(user!=null && user.getEmail().equals(email) && user.getUsrpwd().equals(usrpwd)){
/*				session.setAttribute("pageno", 1);
				session.setAttribute("bookCount", bookCount);
				session.setAttribute("allpages", pages(bookCount));
*/				writeJSON(response, user);	
			}
		}else if(StringUtils.isNotEmpty(name) && !name.equals("null") && StringUtils.isNotEmpty(usrpwd)&& !usrpwd.equals("null")){
			user = UserTable.usrIsLogin(name, usrpwd);
			if(user!=null && user.getName().equals(name) && user.getUsrpwd().equals(usrpwd)){
				//request.getSession(true).setAttribute("", "");
				writeJSON(response, user);	
			}
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
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
}
