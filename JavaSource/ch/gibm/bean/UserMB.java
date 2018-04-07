package ch.gibm.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import ch.gibm.entity.Person;

@SessionScoped
@ManagedBean(name="userMB")
public class UserMB implements Serializable {
	public static final String INJECTION_NAME = "#{userMB}";
	private static final long serialVersionUID = 1L;

	private Person person;

	public boolean isAdmin() {
		return person.isAdmin();
	}

	public boolean isDefaultUser() {
		return person.isUser();
	}

	public String logOut() {
		getRequest().getSession().invalidate();
		return "/pages/public/login.xhtml";
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	
}