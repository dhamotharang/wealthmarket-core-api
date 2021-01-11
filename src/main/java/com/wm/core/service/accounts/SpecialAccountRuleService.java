
package com.wm.core.service.accounts;

import com.wm.core.model.accounts.SpecialAccountRules;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.User;
import com.wm.core.repository.account.AccountingRuleRepository;
import com.wm.core.repository.account.AccountingRuleTypeRepository;
import com.wm.core.repository.account.SpecialAccountRuleRepository;
import com.wm.core.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialAccountRuleService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    AccountingRuleTypeRepository accountingRuleTypeRepo;

    @Autowired
    AccountingRuleRepository accountingRuleRepo;

    @Autowired
    SpecialAccountRuleRepository specialAccountRuleRepo;



    public BaseResponse createSpecialAccountRule(SpecialAccountRules specialAccountRule, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            specialAccountRule.setUserId(user.getId());
            specialAccountRule.setDate_created(new Date());
            specialAccountRule.setLast_updated(new Date());
            SpecialAccountRules newRule = specialAccountRuleRepo.save(specialAccountRule);
            response.setData(newRule);
            response.setDescription("Special Account Rule created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Special Account Rule not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse deleteSpecialAccountRule(long id) {
            BaseResponse response = new BaseResponse();
            Optional<SpecialAccountRules> newRule = specialAccountRuleRepo.findById(id);
            if (newRule.isPresent()) {
                specialAccountRuleRepo.deleteById(id);
                response.setDescription("Special Account Rule deleted succesfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
            } else {
                response.setDescription("Special Account Rule not found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            }
            return response;
        }

    public BaseResponse editSpecialAccountRule(SpecialAccountRules specialAccountRule, long userId) {
        BaseResponse response = new BaseResponse();
        Optional<SpecialAccountRules> rule = specialAccountRuleRepo.findById(specialAccountRule.getId());
        if (rule.isPresent()) {
            specialAccountRule.setLast_updated(new Date());
            specialAccountRule.setUserId(userId);
            SpecialAccountRules updatedRule = specialAccountRuleRepo.save(specialAccountRule);
            response.setData(updatedRule);
            response.setDescription("Special Account Rule updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
                response.setDescription("Special Account Rule not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse getAllSpecialAccountRules() {
        BaseResponse response = new BaseResponse();
        List<SpecialAccountRules> rules = specialAccountRuleRepo.findAll();
        if (!rules.isEmpty()) {
            response.setData(rules);
            response.setDescription("Special Account Rules found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Special Account Rule found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getSpecialAccountRuleByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (specialAccountRuleRepo.existsById(id)) {
            Optional<SpecialAccountRules> rule = specialAccountRuleRepo.findById(id);
            response.setData(rule);
            response.setDescription("Special Account Rule found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Special Account Rule found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

}
