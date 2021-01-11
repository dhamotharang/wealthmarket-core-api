
package com.wm.core.service.accounts;

import com.wm.core.model.accounts.AccountingRule;
import com.wm.core.model.accounts.AccountingRuleType;
import com.wm.core.model.accounts.SubLedgerAccount;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.User;
import com.wm.core.repository.account.AccountingRuleTypeRepository;
import com.wm.core.repository.account.AccountingRuleRepository;
import com.wm.core.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountingRuleService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    AccountingRuleTypeRepository accountingRuleTypeRepo;

    @Autowired
    AccountingRuleRepository accountingRuleRepo;



    public BaseResponse createAccountingRule(AccountingRule accountingRule, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            accountingRule.setUserId(user.getId());
            accountingRule.setDate_created(new Date());
            accountingRule.setLast_updated(new Date());
            AccountingRule newRule = accountingRuleRepo.save(accountingRule);
            response.setData(newRule);
            response.setDescription("Accounting Rule created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Accounting Rule not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse deleteAccountingRule(long id) {
            BaseResponse response = new BaseResponse();
            Optional<AccountingRule> newRule = accountingRuleRepo.findById(id);
            if (newRule.isPresent()) {
                accountingRuleRepo.deleteById(id);
                response.setDescription("Accounting Rule deleted succesfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
            } else {
                response.setDescription("Accounting Rule not found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            }
            return response;
        }

    public BaseResponse editAccountingRule(AccountingRule accountingRule, long userId) {
        BaseResponse response = new BaseResponse();
        Optional<AccountingRule> rule = accountingRuleRepo.findById(accountingRule.getId());
        if (rule.isPresent()) {
            accountingRule.setLast_updated(new Date());
            accountingRule.setUserId(userId);
            AccountingRule updatedRule = accountingRuleRepo.save(accountingRule);
            response.setData(updatedRule);
            response.setDescription("Accounting Rule updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
                response.setDescription("Accounting Rule not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse getAllAccountingRules() {
        BaseResponse response = new BaseResponse();
        List<AccountingRule> rules = accountingRuleRepo.findAll();
        if (!rules.isEmpty()) {
            response.setData(rules);
            response.setDescription("Accounting Rule found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Accounting Rule found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getAccountingRuleByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (accountingRuleRepo.existsById(id)) {
            Optional<AccountingRule> rule = accountingRuleRepo.findById(id);
            response.setData(rule);
            response.setDescription("Accounting Rule found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Accounting Rule found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getAccountingRulesByRuleTypeID(long ruleTypeId) {
        BaseResponse response = new BaseResponse();
        if (accountingRuleTypeRepo.existsById(ruleTypeId)) {
            List<AccountingRule> accountingRules = accountingRuleRepo.findByRuleTypeId(ruleTypeId);
            if (!accountingRules.isEmpty()) {
                response.setData(accountingRules);
                response.setDescription("Accounting Rule found successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
            } else {
                response.setDescription("Accounting Rule found.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.setDescription("Accounting Rule Type not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

}
