package ch.gibm.bean;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.entity.Role;
import ch.gibm.entity.User;
import ch.gibm.facade.RoleFacade;
import ch.gibm.facade.UserFacade;

@ViewScoped
@ManagedBean
public class AdminMB extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private User user;
	private Role role;
	private String selectedRole;
	private List<User> users;
	private List<Role> roles;
	private List<String> roleNames;
	private UserFacade userFacade;
	private RoleFacade roleFacade;

	public UserFacade getUserFacade() {
		if (userFacade == null) {
			userFacade = new UserFacade();
		}

		return userFacade;
	}	
	
	public RoleFacade getRoleFacade() {
		if (roleFacade == null) {
			roleFacade = new RoleFacade();
		}

		return roleFacade;
	}

	
	public Role getRole() {
		if (role == null) {
			role = new Role();
		}

		return role;
	}	
	
	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		if (user == null) {
			user = new User();
		}

		return user;
	}
	
	public String getSelectedRole() {
		if(selectedRole == null) {
			getRoleNames();
			selectedRole = roleNames.iterator().next();
		}
		return selectedRole;
	}
	
	
	public List<String> getRoleNames() {
		if(roleNames == null) {
			List<Role> persistedRoles =  getRoleFacade().listAll();
			roleNames = persistedRoles.stream().map(role -> role.getName()).collect(Collectors.toList());
		}
		return roleNames;
	}

	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}

	public void setSelectedRole(String role) {
		this.selectedRole = role;
	}
	
	public List<Role> getRoles() {
		if(roles == null) {
			roles =  getRoleFacade().listAll();
		}
		return roles;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void createRole() {
		try {
			getRoleFacade().createRole(role);
			closeDialog();
			displayInfoMessageToUser("Created With Sucess");
			loadRoles();
			resetRole();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
			EntityManagerHelper.rollback();
			EntityManagerHelper.closeEntityManager();
		}
	}

	
	public void updateRole() {
		try {
			getRoleFacade().updateRole(role);
			closeDialog();
			displayInfoMessageToUser("Updated With Sucess");
			loadRoles();
			resetRole();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
			EntityManagerHelper.rollback();
			EntityManagerHelper.closeEntityManager();
		}
	}
	
	public void deleteRole() {
		try {
			getRoleFacade().deleteRole(role);
			closeDialog();
			displayInfoMessageToUser("Deleted With Sucess");
			loadRoles();
			resetRole();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, we could not create. Try again later");
			e.printStackTrace();
			EntityManagerHelper.rollback();
			EntityManagerHelper.closeEntityManager();
		}
	}

	public List<Role> getAllRoles() {
		if (roles == null) {
			loadRoles();
		}

		return roles;
	}

	private void loadRoles() {
		roles = getRoleFacade().listAll();
	}


	public void resetRole() {
		role = new Role();
	}

	
	public void createUser() {
		try {
			user.setRole(roleFacade.findRole(selectedRole));
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
			user.setRole(roleFacade.findRole(selectedRole));
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
		selectedRole = null;
	}
}