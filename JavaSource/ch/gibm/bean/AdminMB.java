package ch.gibm.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.entity.Role;
import ch.gibm.entity.User;
import ch.gibm.facade.UserFacade;

@ViewScoped
@ManagedBean
public class AdminMB extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private User user;
	private List<User> users;
	private List<String> roles;
	private UserFacade userFacade;

	public UserFacade getUserFacade() {
		if (userFacade == null) {
			userFacade = new UserFacade();
		}

		return userFacade;
	}

	public User getUser() {
		if (user == null) {
			user = new User();
		}

		return user;
	}
	
	@SuppressWarnings("serial")
	public List<String> getRoles() {
		if(roles == null) {
			roles = new ArrayList<String>() {{add(Role.ADMIN.toString()); add(Role.USER.toString());}};
		}
		return roles;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void createUser() {
		try {
			getUserFacade().createUser(user);
			closeDialog();
			displayInfoMessageToUser("Created With Sucess");
			loadUsers();
			resetUser();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
			EntityManagerHelper.rollback();
			EntityManagerHelper.closeEntityManager();
		}
	}
	
	public void updateUser() {
		try {
			if(user.getPassword() == null || user.getPassword().trim().equals("")) {
				user.setPassword(new String(Base64.getDecoder().decode(getUserFacade().findUser(user.getId()).getPassword())));
			}
			getUserFacade().updateUser(user);
			closeDialog();
			displayInfoMessageToUser("Updated With Sucess");
			loadUsers();
			resetUser();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
			EntityManagerHelper.rollback();
			EntityManagerHelper.closeEntityManager();
		}
	}
	
	public void deleteUser() {
		try {
			getUserFacade().deleteUser(user);
			closeDialog();
			displayInfoMessageToUser("Deleted With Sucess");
			loadUsers();
			resetUser();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
			EntityManagerHelper.rollback();
			EntityManagerHelper.closeEntityManager();
		}
	}

	public List<User> getAllUsers() {
		if (users == null) {
			loadUsers();
		}

		return users;
	}

	private void loadUsers() {
		users = getUserFacade().listAll();
	}

	public void resetUser() {
		user = new User();
	}
}