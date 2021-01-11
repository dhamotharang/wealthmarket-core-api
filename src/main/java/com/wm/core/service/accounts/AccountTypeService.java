
package com.wm.core.service.accounts;

import com.wm.core.model.accounts.Account;
import com.wm.core.model.accounts.AccountType;
import com.wm.core.model.accounts.LedgerAccount;
import com.wm.core.model.accounts.SubLedgerAccount;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.*;
import com.wm.core.repository.account.AccountRepository;
import com.wm.core.repository.account.AccountTypeRepository;
import com.wm.core.repository.user.StatusRepository;
import com.wm.core.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountTypeService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    AccountTypeRepository accountTypeRepo;

    @Autowired
    AccountRepository accountRepo;



    public BaseResponse createAccountType(AccountType accountType, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            accountType.setUserId(user.getId());
            accountType.setDate_created(new Date());
            accountType.setLast_updated(new Date());
            AccountType newAccountType = accountTypeRepo.save(accountType);
            response.setData(newAccountType);
            response.setDescription("Account Type created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Account Type not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse deleteAccountType(long accountTypeId) {
        BaseResponse response = new BaseResponse();
        if (accountTypeRepo.existsById(accountTypeId)) {
            List<Account> accounts = accountRepo.findByAccountTypeId(accountTypeId);
            if (!accounts.isEmpty()) {
                accountRepo.deleteAll(accounts);
            }
            accountRepo.deleteById(accountTypeId);
            response.setDescription("Account Type deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("Account Type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse editAccountType(AccountType accountType, long userId) {
        BaseResponse response = new BaseResponse();
        Optional<AccountType> type = accountTypeRepo.findById(accountType.getId());
        if (type.isPresent()) {
            accountType.setLast_updated(new Date());
            accountType.setUserId(userId);
            AccountType updatedAccountType = accountTypeRepo.save(accountType);
            response.setData(updatedAccountType);
            response.setDescription("Account Type updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
                response.setDescription("Account Type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse getAllAccountTypes() {
        BaseResponse response = new BaseResponse();
        List<AccountType> accountTypes = accountTypeRepo.findAll();
        if (!accountTypes.isEmpty()) {
            response.setData(accountTypes);
            response.setDescription("Account Types found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Account Type found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getAccountTypeByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (accountTypeRepo.existsById(id)) {
            Optional<AccountType> accountType = accountTypeRepo.findById(id);
            response.setData(accountType);
            response.setDescription("Account Type found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Account Type found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

}
