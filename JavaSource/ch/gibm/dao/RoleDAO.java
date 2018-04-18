package ch.gibm.dao;

import java.util.HashMap;
import java.util.Map;

import ch.gibm.entity.Role;

public class RoleDAO extends GenericDAO<Role> {

	private static final long serialVersionUID = 1L;

	public RoleDAO() {
		super(Role.class);
	}
	
	public Role findRoleByName(String roleName) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", roleName);     
 
        return super.findOneResult(Role.FIND_ROLE_BY_NAME, parameters);
	}
}
