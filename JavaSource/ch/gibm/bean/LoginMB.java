package ch.gibm.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import ch.gibm.entity.User;
import ch.gibm.facade.UserFacade;

@RequestScoped
@ManagedBean
public class LoginMB extends AbstractBean {
	
	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private String login;
	private String password;

	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		UserFacade userFacade = new UserFacade();

		User user = userFacade.isValidLogin(login, password);
		
		if(user != null){
			userMB.setUser(user);
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().setAttribute("user", user);
			return "/pages/protected/index.xhtml";
		}

		displayErrorMessageToUser("Check your login/password");
		
		return null;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}	
}