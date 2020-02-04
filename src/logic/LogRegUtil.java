package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.UserBean;
import logic.controller.ControllerLogin;
import logic.controller.ControllerRegistration;
import logic.enums.Roles;
import logic.model.AbstractUser;
import logic.model.CollectionPoint;
import logic.model.Product;
import logic.persistence.AddressDAO;
import logic.persistence.CollectionPointDAO;
import logic.persistence.UserDAO;

public class LogRegUtil {
	ControllerLogin cl = new ControllerLogin();
	ControllerRegistration cr = new ControllerRegistration();

	String scr = "<script>";
	String scr2 = "</script>";
	String scr3 = "$(document).ready(function(){";
	String script = "<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>";
	String scriptcloud = "<script src='https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.11.4/sweetalert2.all.js'></script>";
	String index = "index.jsp";
    
	public void loginUtil(HttpSession session, UserBean ub, URL mapUrl,
					List<Product> catalogomini, List<Product> catalogo,
					HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		
		try {
			cl.login(ub, session);
			AbstractUser user = (AbstractUser) session.getAttribute("user");
			if(user != null && user.getType().equals(Roles.USER)) {
				List<CollectionPoint> collpoint = CollectionPointDAO.select();

				session.setAttribute("mapImage", mapUrl);
				session.setAttribute("collpoint", collpoint);
				session.setAttribute("catalogomini", catalogomini);
				session.setAttribute("catalogo", catalogo);
					
				session.setAttribute("indirizzo", AddressDAO.findAddressById(user.getId()));
					
				request.getRequestDispatcher("homepage.jsp").forward(request, response);
				return;
					
			}
				
			if(user != null && user.getType().equals(Roles.COLLECTIONPOINTMAN)) {
				List<CollectionPoint> collpoint = CollectionPointDAO.select();
				session.setAttribute("collpoint", collpoint);
				request.getRequestDispatcher("moderator.jsp").forward(request, response);
				return;
			}
				
				
			if(user != null && user.getType().equals(Roles.ADMIN)) {
				List<AbstractUser> users = UserDAO.findUsers();
				session.setAttribute("catalogo", catalogo);
				session.setAttribute("users", users);
				request.getRequestDispatcher("admin.jsp").forward(request, response);
				return;
			}
			
			String msg="'Wrong email or password' ,  'Try again !' ,  'error'";
			printPopUp(msg, out);
			RequestDispatcher rd = request.getRequestDispatcher(index);
			rd.include(request, response);
			}

		    catch (SQLException | ServletException | IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
	
	
	
	
	public void registerUtil(UserBean ub, String pass, String confpass,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		PrintWriter out = response.getWriter();
		
		if(pass.contentEquals(confpass)){
			
			try {
				if(cr.register(ub)) {
					String msg="'Successfull Registration !' ,  'Login in ' ,  'success'";
					printPopUp(msg, out);
					RequestDispatcher rd = request.getRequestDispatcher(index);
					rd.include(request, response);
				}
				else {
					String msg="'User already Registered' ,  'Try Again !' ,  'error'";
					printPopUp(msg, out);
					RequestDispatcher rd = request.getRequestDispatcher(index);
					rd.include(request, response);
				}
			} catch (SQLException | ServletException | IOException e) {
				// empty
				e.printStackTrace();
			}
	}
	else {
		String msg="'Password and confirm password are different' ,  'Try again !' ,  'error'";
		printPopUp(msg, out);
		RequestDispatcher rd = request.getRequestDispatcher(index);
		rd.include(request, response);
	}

	}
	
	public Boolean checkUserReg(String name, String surname, String mail, String pass, String confpass, String address, HttpServletResponse response) throws IOException {
		
		if (name.contentEquals("") || surname.contentEquals("") || mail.contentEquals("") || pass.contentEquals("") ||
				confpass.contentEquals("") || address.contentEquals("")) {
			PrintWriter out = response.getWriter();
			String msg="'Invalid fields' ,  'Try again !' ,  'error'";
			printPopUp(msg, out);
			return false;
		}
		return true;
		
	}
	
	private void printPopUp(String msg, PrintWriter out) {
		
		out.println(scriptcloud);
		out.println(script);
		out.println(scr);
		out.println(scr3);
		out.println("swal ( "+ msg +" );");
		out.println("});");
		out.println(scr2);
		
	}

}
