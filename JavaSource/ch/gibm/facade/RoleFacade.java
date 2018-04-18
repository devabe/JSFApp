package ch.gibm.facade;

import java.util.List;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.dao.RoleDAO;
import ch.gibm.entity.Role;

public class RoleFacade {
	
	private RoleDAO roleDAO = new RoleDAO();

	public void createRole(Role role) {
		if(roleExists(role.getName())) {
			throw new RuntimeException("A role with this name already exists in database !");
		}
		EntityManagerHelper.beginTransaction();
		roleDAO.save(role);
		EntityManagerHelper.commitAndCloseTransaction();
	}
	
	public void updateRole(Role role) {
		if(roleExists(role.getName())) {
			throw new RuntimeException("A role with this name already exists in database !");
		}
		EntityManagerHelper.beginTransaction();
		Role persistedRole = roleDAO.find(role.getId());
		
		persistedRole.setName(role.getName());
		
		roleDAO.update(persistedRole);
		EntityManagerHelper.commitAndCloseTransaction();
	}

	public Role findRole(int roleId) {
		EntityManagerHelper.beginTransaction();
		Role persistedRole = roleDAO.find(roleId);
		EntityManagerHelper.commitAndCloseTransaction();
		return persistedRole;
	}

	public Role findRole(String roleName) {
		EntityManagerHelper.beginTransaction();
		Role persistedRole = roleDAO.findRoleByName(roleName);
		EntityManagerHelper.commitAndCloseTransaction();
		return persistedRole;
	}
	
	public boolean roleExists(String roleName) {
		EntityManagerHelper.beginTransaction();
		Role persistedRole = roleDAO.findRoleByName(roleName);
		EntityManagerHelper.commitAndCloseTransaction();
		return persistedRole != null;
	}

	public List<Role> listAll() {
		EntityManagerHelper.beginTransaction();
		List<Role> result = roleDAO.findAll();
		EntityManagerHelper.commitAndCloseTransaction();
		return result;
	}

	public void deleteRole(Role role) {
		if(role.getUsers().size() > 0) {
			throw new RuntimeException("This role can't be deleted cause users are using it !");
		}
		EntityManagerHelper.beginTransaction();
		roleDAO.delete(role.getId(), Role.class);
		EntityManagerHelper.commitAndCloseTransaction();
	}
}