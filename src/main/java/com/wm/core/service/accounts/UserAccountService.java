
package com.wm.core.service.accounts;

import com.wm.core.model.accounts.Account;
import com.wm.core.model.accounts.UserAccount;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.User;
import com.wm.core.repository.account.UserAccountRepository;
import com.wm.core.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserAccountRepository userAccountRepo;



    public BaseResponse createUserAccount(UserAccount userAccount, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            userAccount.setObjectId(user.getId());
            userAccount.setDate_created(new Date());
            userAccount.setLast_updated(new Date());
            UserAccount newUserAccount = userAccountRepo.save(userAccount);
            response.setData(newUserAccount);
            response.setDescription("User Account created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("User Account not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse deleteUserAccount(long id) {
            BaseResponse response = new BaseResponse();
            Optional<UserAccount> newUserAccount = userAccountRepo.findById(id);
            if (newUserAccount.isPresent()) {
                userAccountRepo.deleteById(id);
                response.setDescription("User Account deleted succesfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
            } else {
                response.setDescription("User Account not found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            }
            return response;
        }

    public BaseResponse editUserAccount(UserAccount userAccount, long userId) {
        BaseResponse response = new BaseResponse();
        Optional<UserAccount> newUserAccount = userAccountRepo.findById(userAccount.getId());
        if (newUserAccount.isPresent()) {
            userAccount.setLast_updated(new Date());
            userAccount.setObjectId(userId);
            UserAccount updatedAccount = userAccountRepo.save(userAccount);
            response.setData(updatedAccount);
            response.setDescription("User Account updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
                response.setDescription("User Account not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse getAllUserAccounts() {
        BaseResponse response = new BaseResponse();
        List<UserAccount> userAccount = userAccountRepo.findAll();
        if (!userAccount.isEmpty()) {
            response.setData(userAccount);
            response.setDescription("User Account found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No User Account found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getUserAccountByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (userAccountRepo.existsById(id)) {
            Optional<UserAccount> userAccount = userAccountRepo.findById(id);
            response.setData(userAccount);
            response.setDescription("User Account found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No User Account found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

}
