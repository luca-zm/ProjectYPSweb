package logic.servlet;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.LogRegUtil;
import logic.bean.AddressBean;
import logic.bean.UserBean;
import logic.controller.ControllerManageCollPoint;

import logic.model.Product;

import logic.persistence.ProductDAO;

/**
 * Servlet implementation class LoginController
 */

@WebServlet("/LoginControllerServlet")
public class LoginControllerServlet extends HttpServlet {
	
	
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(); 
		String action = request.getParameter("action");
		String index = "index.jsp";
		List<Product> catalogo = new ArrayList<>();
		List<Product> catalogomini = new ArrayList<>();
		LogRegUtil lg = new LogRegUtil();
		
		try {
			catalogo = ProductDAO.select();			
			for(Product p: catalogo) {
				if(p.getPrice() > 100) {
					catalogomini.add(p);
			
				}
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		
		ControllerManageCollPoint c = new ControllerManageCollPoint();
		URL mapUrl = null;
		try {
			mapUrl = c.startUrl();
		} catch (MalformedURLException | SQLException e1) {
			e1.printStackTrace();
		}
					
		
		
		
		
		if("login".equals(action)) {
			String mail=request.getParameter("username");
			String pw=request.getParameter("password");
			UserBean ub = new UserBean(0, mail, null, null, pw, null);
			
			lg.loginUtil(session, ub, mapUrl, catalogomini, catalogomini, request, response);
			
		}
		

		if("register".equals(action)) {
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String pass = request.getParameter("pass");
			String confpass = request.getParameter("confpass");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String city = request.getParameter("city");
			String zipcode = request.getParameter("zipcode");
			String telephone = request.getParameter("telephone");
			String state = request.getParameter("state");
			String zone = request.getParameter("zone");
			
			
			
			AddressBean addr = new AddressBean(address, city, zipcode, telephone, state, zone);
			UserBean ub = new UserBean(0, email, name, surname, pass, addr);
			
			if(lg.checkUserReg(name, surname, email, pass, confpass, address, response)) {
				lg.registerUtil(ub, pass, confpass, request, response);
			}
			RequestDispatcher rd = request.getRequestDispatcher(index);
			rd.include(request, response);
		}
		
		
		if("logout".equals(action)) {
			session.invalidate(); 
			response.sendRedirect(index);
		}

	}
	
	
	
	

}
