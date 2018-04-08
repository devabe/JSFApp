package ch.gibm.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "User.findUserByLogin", query = "select u from User u where u.login = :login")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String FIND_USER_BY_LOGIN = "User.findUserByLogin";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	@Column(unique = true)
	private String login;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return id;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isAdmin() {
		return Role.ADMIN.equals(role);
	}

	public boolean isUser() {
		return Role.USER.equals(role);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Person) {
			User user = (User) obj;
			return user.getId() == id;
		}

		return false;
	}
}