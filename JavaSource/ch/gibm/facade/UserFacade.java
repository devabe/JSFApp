package ch.gibm.facade;

import java.util.Base64;
import java.util.List;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.dao.UserDAO;
import ch.gibm.entity.User;

public class UserFacade {
	private UserDAO userDAO = new UserDAO();

	public User isValidLogin(String login, String password) {
		EntityManagerHelper.beginTransaction();
		User user = userDAO.findUserByLogin(login);

		String encodedPassword =Base64.getEncoder().encodeToString(password.getBytes());
		if (user == null || !user.getPassword().equals(encodedPassword)) {
			return null;
		}

		EntityManagerHelper.commitAndCloseTransaction();
		return user;
	}
	

	public void createUser(User user) {
		EntityManagerHelper.beginTransaction();
		user.setPassword(Base64.getEncoder().encodeToString(user.getPassword().getBytes()));
		userDAO.save(user);
		EntityManagerHelper.commitAndCloseTransaction();
	}
	
	public void updateUser(User user) {
		EntityManagerHelper.beginTransaction();
		User persistedUser = userDAO.find(user.getId());
		
		persistedUser.setLogin(user.getLogin());
		persistedUser.setName(user.getName());
		persistedUser.setRole(user.getRole());
		persistedUser.setPassword(Base64.getEncoder().encodeToString(user.getPassword().getBytes()));
		
		userDAO.update(persistedUser);
		EntityManagerHelper.commitAndCloseTransaction();
	}

	public User findUser(int userId) {
		EntityManagerHelper.beginTransaction();
		User user = userDAO.find(userId);
		EntityManagerHelper.commitAndCloseTransaction();
		return user;
	}

	public List<User> listAll() {
		EntityManagerHelper.beginTransaction();
		List<User> result = userDAO.findAll();
		EntityManagerHelper.commitAndCloseTransaction();
		return result;
	}

	public void deleteUser(User user) {
		EntityManagerHelper.beginTransaction();
		userDAO.delete(user.getId(), User.class);
		EntityManagerHelper.commitAndCloseTransaction();
	}
}