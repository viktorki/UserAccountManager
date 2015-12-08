package useraccountmanager.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import useraccountmanager.model.UserAccount;

@Repository
public class UserAccountDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<UserAccount> getUserAccounts() {
	Session session = sessionFactory.getCurrentSession();
	Criteria cr = session.createCriteria(UserAccount.class);

	return cr.list();
    }

    public void addUserAccount(UserAccount userAccount) {
	Session session = sessionFactory.getCurrentSession();

	session.save(userAccount);
    }

    public UserAccount getUserAccountById(Long id) {
	Session session = sessionFactory.getCurrentSession();

	return (UserAccount) session.get(UserAccount.class, id);
    }

    public void updateUserAccount(UserAccount userAccount) {
	Session session = sessionFactory.getCurrentSession();

	session.update(userAccount);
    }

    public void deleteUserAccount(Long id) {
	Session session = sessionFactory.getCurrentSession();
	UserAccount userAccount = (UserAccount) session.get(UserAccount.class,
		id);

	session.delete(userAccount);
    }
}
