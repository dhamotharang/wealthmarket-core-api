
package com.wm.core.service.accounts;

import com.wm.core.model.accounts.UserAccountRule;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.User;
import com.wm.core.repository.account.AccountRepository;
import com.wm.core.repository.account.UserAccountRuleRepository;
import com.wm.core.repository.account.AccountingRuleRepository;
import com.wm.core.repository.account.AccountingRuleTypeRepository;
import com.wm.core.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountRuleService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    AccountingRuleTypeRepository accountingRuleTypeRepo;

    @Autowired
    AccountingRuleRepository accountingRuleRepo;

    @Autowired
    UserAccountRuleRepository userAccountRuleRepo;


    public BaseResponse assignAccountingRulesToAccount(Long accountID, List<Long> ruleIDs) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> acc = new ArrayList<>();
        if (accountRepo.existsById(accountID)) {
            // check the accountID;
            for (long ruleid : ruleIDs) {
                // check the ruleid
                if (accountingRuleRepo.existsById(ruleid)) {

                    Optional<UserAccountRule> existAcctAcctingRules = userAccountRuleRepo.findByAccountIdAndRuleId(accountID, ruleid);
                    if (!existAcctAcctingRules.isPresent()) {
                        UserAccountRule userAccountRules = new UserAccountRule();
                        userAccountRules.setAccountId(accountID);
                        userAccountRules.setRuleId(ruleid);

                        UserAccountRule newUserAccountRule = new UserAccountRule();
                        UserAccountRule createdAccountRule = userAccountRuleRepo.save(userAccountRules);
                        acc.add(createdAccountRule);

                    } else {
                        response.setDescription("Accounting rule has already been assigned.");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.setDescription("Accounting rule does not exit.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            if (!acc.isEmpty()) {
                response.setData(acc);
                response.setDescription("Accounting rule(s) assigned to account successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
            } else {
                response.setDescription("Accounting rule(s) assigned to account was not successfully.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            }

        } else {
            response.setDescription("Account not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse deleteUserAccountRule(List<Long> userAccountRuleIds) {
        BaseResponse response = new BaseResponse();

        if (!userAccountRuleIds.isEmpty()) {
            // check the accountid;
            for (long acctacctingruleid : userAccountRuleIds) {
                // check the ruleid
                Optional<UserAccountRule> userAccountRules = userAccountRuleRepo.findById(acctacctingruleid);
                if (userAccountRules.isPresent()) {
                    userAccountRuleRepo.deleteById(acctacctingruleid);
                } else {
                    response.setDescription("Could not find Account Accounting Rule.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            response.setDescription("Accounting Rule(s) has been removed from the Account.");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("Account not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse createAccountRule(UserAccountRule userAccountRule, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            userAccountRule.setUserId(user.getId());
            userAccountRule.setDate_created(new Date());
            userAccountRule.setLast_updated(new Date());
            UserAccountRule newRule = userAccountRuleRepo.save(userAccountRule);
            response.setData(newRule);
            response.setDescription("Account Rule created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Account Rule not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse deleteAccountRule(long id) {
            BaseResponse response = new BaseResponse();
            Optional<UserAccountRule> newRule = userAccountRuleRepo.findById(id);
            if (newRule.isPresent()) {
                userAccountRuleRepo.deleteById(id);
                response.setDescription("Account Rule deleted succesfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
            } else {
                response.setDescription("Account Rule not found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            }
            return response;
        }

    public BaseResponse editAccountRule(UserAccountRule userAccountRule, long userId) {
        BaseResponse response = new BaseResponse();
        Optional<UserAccountRule> rule = userAccountRuleRepo.findById(userAccountRule.getId());
        if (rule.isPresent()) {
            userAccountRule.setLast_updated(new Date());
            userAccountRule.setUserId(userId);
            UserAccountRule updatedRule = userAccountRuleRepo.save(userAccountRule);
            response.setData(updatedRule);
            response.setDescription("Account Rule updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
                response.setDescription("Account Rule not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse getAllAccountRules() {
        BaseResponse response = new BaseResponse();
        List<UserAccountRule> rules = userAccountRuleRepo.findAll();
        if (!rules.isEmpty()) {
            response.setData(rules);
            response.setDescription("Account Rule found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Account Rule found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getAccountRuleByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (userAccountRuleRepo.existsById(id)) {
            Optional<UserAccountRule> rule = userAccountRuleRepo.findById(id);
            response.setData(rule);
            response.setDescription("Account Rule found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Account Rule found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

}
