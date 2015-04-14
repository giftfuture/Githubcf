package chinapub.cf.recommander.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import chinapub.cf.recommander.model.Book;
import chinapub.cf.recommander.model.table.BookTable;

public class BookListServlet extends HttpServlet {

	/**
	 * @dateOct 8,2014
	 * @author Nexus
	 */
	private static final long serialVersionUID = 2209674508943720833L;
	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		String pageNo = request.getParameter(Parameters.CURRENTPAGE);
		if(StringUtils.isEmpty(pageNo)){
			System.out.println("getPagesNofrom session");
			pageNo = request.getSession().getAttribute("pageno").toString();
		}
		List<Book> books = new ArrayList<Book>();
		int bookCount = BookTable.countAllBooks() ;
		request.getSession().setAttribute("bookCount", bookCount);
		request.getSession().setAttribute("allpages", pages(bookCount));
		if(StringUtils.isNotEmpty(pageNo) && !pageNo.equals("NaN")){
			request.getSession().setAttribute("pageno", pageNo);
			int page = Integer.parseInt(pageNo);
			System.out.println("pageNo="+pageNo);
			books = BookTable.getAllBooks(page,Parameters.LIMIT);
		}
		try {
			writeJSON(response,books);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		doGet(request,response);
	}
	
	/**
	 * 
	 * @param response
	 * @param books
	 * @throws IOException
	 */
	private void writeJSON(HttpServletResponse response, List<Book> books)
			throws IOException {
		response.setContentType("text/plain; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean flag = false;
		if(books!=null && books.size()>=1){
			for(Book book: books){
				if(flag){
					sb.append(", ");
				} else {
					flag = true;
				}
				sb.append(book.toJSON());
			}
		}
		sb.append("]");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(sb.toString());
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
