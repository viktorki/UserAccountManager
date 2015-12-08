package useraccountmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import useraccountmanager.model.UserAccount;
import useraccountmanager.service.UserAccountService;

@RestController
@RequestMapping("/userAccounts")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(method = RequestMethod.GET)
    public List<UserAccount> getUserAccounts() {
	return userAccountService.getUserAccounts();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addUserAccount(@RequestBody UserAccount userAccount) {
	userAccountService.addUserAccount(userAccount);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public UserAccount getUserAccountById(@PathVariable Long id) {
	return userAccountService.getUserAccountById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void updateUserAccount(@PathVariable Long id,
	    @RequestBody UserAccount userAccount) {
	userAccount.setId(id);
	userAccountService.updateUserAccount(userAccount);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteUserAccount(@PathVariable Long id) {
	userAccountService.deleteUserAccount(id);
    }
}
