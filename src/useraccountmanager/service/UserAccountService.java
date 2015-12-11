package useraccountmanager.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import useraccountmanager.dao.UserAccountDAO;
import useraccountmanager.model.UserAccount;

@Service
@Transactional
public class UserAccountService {

	@Autowired
	private UserAccountDAO userAccountDAO;

	public List<UserAccount> getUserAccounts() {
		return userAccountDAO.getUserAccounts();
	}

	public void addUserAccount(UserAccount userAccount) {
		userAccountDAO.addUserAccount(userAccount);
	}

	public UserAccount getUserAccountById(Long id) {
		return userAccountDAO.getUserAccountById(id);
	}

	public void updateUserAccount(UserAccount userAccount) {
		userAccountDAO.updateUserAccount(userAccount);
	}

	public void deleteUserAccount(Long id) {
		userAccountDAO.deleteUserAccount(id);
	}
}
