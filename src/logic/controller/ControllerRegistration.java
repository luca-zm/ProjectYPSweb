package logic.controller;

import java.sql.SQLException;
import logic.bean.AddressBean;
import logic.bean.UserBean;
import logic.model.AbstractUser;
import logic.model.Address;
import logic.model.FactoryUsers;
import logic.persistence.AddressDAO;
import logic.persistence.UserDAO;

public class ControllerRegistration {
	public Boolean register(UserBean userBean) throws SQLException {
		String mail = userBean.getMail();
		String pass = userBean.getPass();
		String name = userBean.getName();
		String surname = userBean.getSurname();
		AddressBean addrBean = userBean.getAddress();

		
		if(UserDAO.findRegisteredUser(mail) != null) {
			return false;
		}
		
		AbstractUser user = FactoryUsers.get(0, mail, name, surname, pass, "USER");
		
		Address addr = new Address(0, addrBean.getAddrBean(), addrBean.getCityBean(), addrBean.getPostalCodeBean(), addrBean.getTelephoneBean(), addrBean.getStateBean(),  addrBean.getZoneBean());
		AddressDAO.insert(addr);
	
		user.setAddress(addr);
		
		UserDAO.insert(user);
		return true;
	}
}
