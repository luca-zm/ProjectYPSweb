package logic.servlet;

import java.io.IOException;


import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.bean.ActivationCodeBean;
import logic.bean.BeanValidate;
import logic.controller.ControllerShopCartCheckOut;

/**
 * Servlet implementation class UserProfileServlet
 */
@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserProfileServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(); 
		ControllerShopCartCheckOut ac = new ControllerShopCartCheckOut();
		BeanValidate bv = new BeanValidate();
		PrintWriter out = response.getWriter();

		
		String action = request.getParameter("activate");
		
		if("activate".equals(action)) {
			String code=request.getParameter("code");
			try {
				if (bv.isInteger(code)) {
					ActivationCodeBean acb = new ActivationCodeBean(Integer.parseInt(code), 0);
					ac.enabledActivationCode(acb, session);
					
					out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>");
					out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
					out.println("<script>");
					out.println("$(document).ready(function(){");
					out.println("swal ( 'Code correctly collected!' ,  '' ,  'success' );");
					out.println("});");
					out.println("</script>");
					
				}
				else { out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>");
						out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>");
						out.println("<script>");
						out.println("$(document).ready(function(){");
						out.println("swal ( 'Error, please insert a valid ActivationCode' ,  'Try Again' ,  'error' );");
						out.println("});");
						out.println("</script>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			
			request.getRequestDispatcher("userprofile.jsp").include(request, response);
		
		}
	}

}
