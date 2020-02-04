package logic.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.CollectionPointBean;
import logic.controller.ControllerManageCollPoint;
import logic.model.CollectionPoint;
import logic.persistence.CollectionPointDAO;

/**
 * Servlet implementation class ModeratorServlet
 */
@WebServlet("/ModeratorServlet")
public class ModeratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModeratorServlet() {
        super();
    }

	/**
	 *   @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		ControllerManageCollPoint conmod = new ControllerManageCollPoint(); 
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(); 
		String script1 ="<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>";
		String script2 ="<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>";
		String script3 ="<script>";
		String script4 ="$(document).ready(function(){";
		String script5 ="</script>";
		
		if("add".equals(action)) {
			
			String name=request.getParameter("name");
			String address=request.getParameter("address");
			int optime = Integer.parseInt(request.getParameter("opening"));
			int closetime = Integer.parseInt(request.getParameter("closing"));
			CollectionPointBean cb = new CollectionPointBean(0, name, address, optime, closetime);
			try {
				conmod.insert(cb);
				List<CollectionPoint> collPoint = (List<CollectionPoint>) CollectionPointDAO.select();
				session.setAttribute("collpoint", collPoint);
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.println(script1);
			out.println(script2);
			out.println(script3);
			out.println(script4);
			out.println("swal ( 'Collection Point successfull inserted !' ,  '' ,  'success' );");
			out.println("});");
			out.println(script5);
			RequestDispatcher rd = request.getRequestDispatcher("moderator.jsp");
			rd.include(request, response);
				
		}
		
		else if("delete".equals(action)) {
			
			int collId = Integer.parseInt(request.getParameter("collId"));
			
			CollectionPoint cp = new CollectionPoint(collId, null , 0, 0, null, 0, 0);
			try {
				if(conmod.delete(cp)) {
					out.println(script1);
					out.println(script2);
					out.println(script3);
					out.println(script4);
					out.println("swal ( 'Collection Point successfull deleted !' ,  '' ,  'success' );");
					out.println("});");
					out.println(script5);
				}
				else {
					out.println(script1);
					out.println(script2);
					out.println(script3);
					out.println(script4);
					out.println("swal ( 'Collection Point not found!' ,  'Try Again' ,  'error' );");
					out.println("});");
					out.println(script5);
				}
				List<CollectionPoint> collPoint = (List<CollectionPoint>) CollectionPointDAO.select();
				session.setAttribute("collpoint", collPoint);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

			
			RequestDispatcher rd = request.getRequestDispatcher("moderator.jsp");
			rd.include(request, response);
		}
			
	}

}
