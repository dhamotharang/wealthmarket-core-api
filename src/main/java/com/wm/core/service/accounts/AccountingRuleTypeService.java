
package com.wm.core.service.accounts;

import com.wm.core.model.accounts.Account;
import com.wm.core.model.accounts.AccountingRuleType;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.User;
import com.wm.core.repository.account.AccountRepository;
import com.wm.core.repository.account.AccountingRuleTypeRepository;
import com.wm.core.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountingRuleTypeService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    AccountingRuleTypeRepository accountingRuleTypeRepo;



    public BaseResponse createAccountingRuleType(AccountingRuleType accountingRuleType, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            accountingRuleType.setUserId(user.getId());
            accountingRuleType.setDate_created(new Date());
            accountingRuleType.setLast_updated(new Date());
            AccountingRuleType newRuleType = accountingRuleTypeRepo.save(accountingRuleType);
            response.setData(newRuleType);
            response.setDescription("Accounting Rule Type created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Accounting Rule Type not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse deleteAccountingRuleType(long id) {
            BaseResponse response = new BaseResponse();
            Optional<AccountingRuleType> newRuleType = accountingRuleTypeRepo.findById(id);
            if (newRuleType.isPresent()) {
                accountingRuleTypeRepo.deleteById(id);
                response.setDescription("Accounting Rule Type deleted succesfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
            } else {
                response.setDescription("Accounting Rule Type not found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            }
            return response;
        }

    public BaseResponse editAccountingRuleType(AccountingRuleType accountingRuleType, long userId) {
        BaseResponse response = new BaseResponse();
        Optional<AccountingRuleType> ruleType = accountingRuleTypeRepo.findById(accountingRuleType.getId());
        if (ruleType.isPresent()) {
            accountingRuleType.setLast_updated(new Date());
            accountingRuleType.setUserId(userId);
            AccountingRuleType updatedRuleType = accountingRuleTypeRepo.save(accountingRuleType);
            response.setData(updatedRuleType);
            response.setDescription("Accounting Rule Type updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
                response.setDescription("Accounting Rule Type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse getAllAccountingRuleTypes() {
        BaseResponse response = new BaseResponse();
        List<AccountingRuleType> ruleTypes = accountingRuleTypeRepo.findAll();
        if (!ruleTypes.isEmpty()) {
            response.setData(ruleTypes);
            response.setDescription("Accounting Rule Type found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Accounting Rule Type found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getAccountingRuleTypeByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (accountingRuleTypeRepo.existsById(id)) {
            Optional<AccountingRuleType> ruleType = accountingRuleTypeRepo.findById(id);
            response.setData(ruleType);
            response.setDescription("Accounting Rule Type found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Accounting Rule Type found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

}
