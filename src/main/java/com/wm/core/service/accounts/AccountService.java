
package com.wm.core.service.accounts;

import com.wm.core.model.accounts.Account;
import com.wm.core.model.accounts.AccountType;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.SubGroup;
import com.wm.core.model.user.User;
import com.wm.core.repository.account.AccountRepository;
import com.wm.core.repository.account.AccountTypeRepository;
import com.wm.core.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    AccountRepository accountRepo;



    public BaseResponse createAccount(Account account, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            account.setObjectId(user.getId());
            account.setDate_created(new Date());
            account.setLast_updated(new Date());
            Account newAccount = accountRepo.save(account);
            response.setData(newAccount);
            response.setDescription("Account created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Account not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse deleteAccount(long id) {
            BaseResponse response = new BaseResponse();
            Optional<Account> newAccount = accountRepo.findById(id);
            if (newAccount.isPresent()) {
                accountRepo.deleteById(id);
                response.setDescription("Account deleted succesfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
            } else {
                response.setDescription("Account not found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            }
            return response;
        }

    public BaseResponse editAccount(Account account, long userId) {
        BaseResponse response = new BaseResponse();
        Optional<Account> acc = accountRepo.findById(account.getId());
        if (acc.isPresent()) {
            account.setLast_updated(new Date());
            account.setObjectId(userId);
            Account updatedAccount = accountRepo.save(account);
            response.setData(updatedAccount);
            response.setDescription("Account updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
                response.setDescription("Account not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse getAllAccounts() {
        BaseResponse response = new BaseResponse();
        List<Account> account = accountRepo.findAll();
        if (!account.isEmpty()) {
            response.setData(account);
            response.setDescription("Account found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Account found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getAccountByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (accountRepo.existsById(id)) {
            Optional<Account> account = accountRepo.findById(id);
            response.setData(account);
            response.setDescription("Account found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Account found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getAccountsByAccountTypeID(Long accountTypeId) {
        BaseResponse response = new BaseResponse();
        List<Account> accounts = accountRepo.findByAccountTypeId(accountTypeId);
        if (!accounts.isEmpty()) {
            response.setData(accounts);
            response.setDescription("Accounts found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Account found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

}
