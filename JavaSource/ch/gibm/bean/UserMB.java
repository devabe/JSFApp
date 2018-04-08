package ch.gibm.bean;

import java.io.Serializable;
import java.util.Base64;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.entity.User;
import ch.gibm.facade.UserFacade;

@SessionScoped
@ManagedBean(name="userMB")
public class UserMB extends AbstractBean implements Serializable {
	public static final String INJECTION_NAME = "#{userMB}";
	private static final long serialVersionUID = 1L;

	private User user;
	private UserFacade userFacade;

	public boolean isAdmin() {
		return user.isAdmin();
	}

	public String createUser() {
		try {
			getUserFacade().createUser(user);
			displayInfoMessageToUser("Created with success");
			resetUser();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("A problem occurred while saving : " + e.getMessage());
			e.printStackTrace();
			EntityManagerHelper.rollback();
			EntityManagerHelper.closeEntityManager();
		}
		
		return "/pages/public/login.xhtml";
	}
	
	public void updateUser() {
		try {
			if(user.getPassword() == null || user.getPassword().trim().equals("")) {
				user.setPassword(new String(Base64.getDecoder().decode(getUserFacade().findUser(user.getId()).getPassword())));
			}
			getUserFacade().updateUser(user);
			displayInfoMessageToUser("Updated With Sucess");

		} catch (Exception e) {
			displayErrorMessageToUser("Ops, we could not create. Error " + e.getMessage());
			e.printStackTrace();
			EntityManagerHelper.rollback();
			EntityManagerHelper.closeEntityManager();
		}
	}
	
	private void resetUser() {
		this.user = new User();
	}
	
	public boolean isDefaultUser() {
		return user.isUser();
	}

	public String logOut() {
		getRequest().getSession().invalidate();
		return "/pages/public/login.xhtml";
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public User getUser() {
		if (user == null) {
			user = new User();
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public UserFacade getUserFacade() {
		if (userFacade == null) {
			userFacade = new UserFacade();
		}

		return userFacade;
	}

}