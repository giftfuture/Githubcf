package chinapub.cf.recommander.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import chinapub.cf.recommander.model.Rating;
import chinapub.cf.recommander.model.table.RatingTable;

/**
 * Servlet implementation class RatingServlet
 */
public class RatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RatingServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookIdScores = request.getParameter(Parameters.BOOKIDSCORES);
		String userId = request.getParameter(Parameters.USER_ID);
	   String [] bidscores = bookIdScores.split(",");
	   List<Rating> ratings = new ArrayList<Rating>();
	   Rating rat = null;
	   if(bidscores.length>0){
		   for(String bidscore:bidscores){
			   String [] temp= bidscore.split("-");
			   if(StringUtils.isNotEmpty(userId)&&StringUtils.isNotEmpty(temp[0])&&StringUtils.isNotEmpty(temp[1])) {
			   rat = new Rating(Integer.parseInt(userId),Integer.parseInt(temp[0]),Float.parseFloat(temp[1]),df.format(new Date()));
			   ratings.add(rat);
		    }
		   }
		   RatingTable.insertRatings(ratings);
	   }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
