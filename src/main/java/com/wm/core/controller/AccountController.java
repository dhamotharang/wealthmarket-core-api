package com.wm.core.controller;

import com.wm.core.model.accounts.*;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.service.accounts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("${app.title}")
public class AccountController {

    @Autowired
    LedgerAccountService ledgerAccountService;

    @Autowired
    SubLedgerAccountService subLedgerAccountService;

    @Autowired
    AccountTypeService accountTypeService;

    @Autowired
    LedgerTypeService ledgerTypeService;

    @Autowired
    InstrumentService instrumentService;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountingRuleService accountingRuleService;

    @Autowired
    UserAccountRuleService userAccountRuleService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    SpecialAccountRuleService specialAccountRuleService;

    @PostMapping("/accounts/ledgeraccount/create")
    ResponseEntity<?> createLedgerAccount(@RequestAttribute("userId") long userid, @Valid @RequestBody LedgerAccount ledgerAccount) {
        BaseResponse response = ledgerAccountService.createLedgerAccount(ledgerAccount, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/accounts/ledgeraccount/delete/{id}")
    ResponseEntity<?> deleteLedgerAccount(@PathVariable long id) {
        BaseResponse response = ledgerAccountService.deleteLedgerAccount(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/ledgeraccount/get/{id}")
    ResponseEntity<?> getLedgerAccountByID(@PathVariable long id) {
        BaseResponse response = ledgerAccountService.getLedgerAccountByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/ledgeraccount/getall")
    ResponseEntity<?> getAllLedgerAccounts() {
        BaseResponse response = ledgerAccountService.getAllLedgerAccounts();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accounts/ledgeraccount/update")
    ResponseEntity<?> updateLedgerAccount(@RequestAttribute("userId") long userid, @RequestBody LedgerAccount ledgerAccount) {
        BaseResponse response = ledgerAccountService.editLedgerAccount(ledgerAccount, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accounts/subledgeraccount/create")
    ResponseEntity<?> createSubLedgerAccount(@RequestAttribute("userId") long userid, @Valid @RequestBody SubLedgerAccount subLedgerAccount) {
        BaseResponse response = subLedgerAccountService.createSubLedgerAccount(subLedgerAccount, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/accounts/subledgeraccount/delete/{id}")
    ResponseEntity<?> deleteSubLedgerAccount(@PathVariable long id) {
        BaseResponse response = subLedgerAccountService.deleteSubLedgerAccount(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/subledgeraccount/get/{id}")
    ResponseEntity<?> getSubLedgerAccountByID(@PathVariable long id) {
        BaseResponse response = subLedgerAccountService.getSubLedgerAccountByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/subledgeraccounts/getall")
    ResponseEntity<?> getAllSubLedgerAccounts() {
        BaseResponse response = subLedgerAccountService.getAllSubLedgerAccounts();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accounts/subledgeraccount/update")
    ResponseEntity<?> updateSubLedgerAccount(@RequestAttribute("userId") long userid, @RequestBody SubLedgerAccount subLedgerAccount) {
        BaseResponse response = subLedgerAccountService.editSubLedgerAccount(subLedgerAccount, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/ledger/subledger/get/{id}")
    ResponseEntity<?> getSubLedgerAccountsByLedgerID(@PathVariable long id) {
        BaseResponse response = subLedgerAccountService.getSubLedgerAccountsByLedgerID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accounts/accounttype/create")
    ResponseEntity<?> createAccountType(@RequestAttribute("userId") long userid, @Valid @RequestBody AccountType accountType) {
        BaseResponse response = accountTypeService.createAccountType(accountType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/accounts/accounttype/delete/{id}")
    ResponseEntity<?> deleteAccountType(@PathVariable long id) {
        BaseResponse response = accountTypeService.deleteAccountType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/accounttype/get/{id}")
    ResponseEntity<?> getAccountTypeByID(@PathVariable long id) {
        BaseResponse response = accountTypeService.getAccountTypeByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/accounttypes/getall")
    ResponseEntity<?> getAllAccountTypes() {
        BaseResponse response = accountTypeService.getAllAccountTypes();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accounts/accounttype/update")
    ResponseEntity<?> updateAccountType(@RequestAttribute("userId") long userid, @RequestBody AccountType accountType) {
        BaseResponse response = accountTypeService.editAccountType(accountType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accounts/ledgertype/create")
    ResponseEntity<?> createLedgerType(@RequestAttribute("userId") long userid, @Valid @RequestBody LedgerType ledgerType) {
        BaseResponse response = ledgerTypeService.createLedgerType(ledgerType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/accounts/ledgertype/delete/{id}")
    ResponseEntity<?> deleteLedgerType(@PathVariable long id) {
        BaseResponse response = ledgerTypeService.deleteLedgerType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/ledgertype/get/{id}")
    ResponseEntity<?> getLedgerTypeByID(@PathVariable long id) {
        BaseResponse response = ledgerTypeService.getLedgerTypeByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/ledgertypes/getall")
    ResponseEntity<?> getAllLedgerTypes() {
        BaseResponse response = ledgerTypeService.getAllLedgerTypes();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accounts/ledgertype/update")
    ResponseEntity<?> updateLedgerType(@RequestAttribute("userId") long userid, @RequestBody LedgerType ledgerType) {
        BaseResponse response = ledgerTypeService.editLedgerType(ledgerType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accounts/financialinstrument/create")
    ResponseEntity<?> createFinancialInstrument(@RequestAttribute("userId") long userid, @Valid @RequestBody Instrument instrument) {
        BaseResponse response = instrumentService.createFinancialInstrument(instrument, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/accounts/financialinstrument/delete/{id}")
    ResponseEntity<?> deleteFinancialInstrument(@PathVariable long id) {
        BaseResponse response = instrumentService.deleteFinancialInstrument(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/financialinstrument/get/{id}")
    ResponseEntity<?> getFinancialInstrumentByID(@PathVariable long id) {
        BaseResponse response = instrumentService.getFinancialInstrumentByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/financialinstruments/getall")
    ResponseEntity<?> getAllFinancialInstruments() {
        BaseResponse response = instrumentService.getAllFinancialInstruments();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accounts/financialinstrument/update")
    ResponseEntity<?> updateFinancialInstrument(@RequestAttribute("userId") long userid, @RequestBody Instrument instrument) {
        BaseResponse response = instrumentService.editFinancialInstrument(instrument, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accounts/account/create")
    ResponseEntity<?> createAccount(@RequestAttribute("userId") long userid, @Valid @RequestBody Account acccount) {
        BaseResponse response = accountService.createAccount(acccount, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/accounts/account/delete/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable long id) {
        BaseResponse response = accountService.deleteAccount(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/account/get/{id}")
    ResponseEntity<?> getAccountByID(@PathVariable long id) {
        BaseResponse response = accountService.getAccountByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/account/getall")
    ResponseEntity<?> getAllAccounts() {
        BaseResponse response = accountService.getAllAccounts();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/accounts/accounttype/get/{id}")
    ResponseEntity<?> getAccountsByAccountTypeID(@PathVariable long id) {
        BaseResponse response = accountService.getAccountsByAccountTypeID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accounts/account/update")
    ResponseEntity<?> updateAccount(@RequestAttribute("userId") long userid, @RequestBody Account account) {
        BaseResponse response = accountService.editAccount(account, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accounts/rules/accountingrule/create")
    ResponseEntity<?> createAccountingRule(@RequestAttribute("userId") long userid, @Valid @RequestBody AccountingRule acccountingRule) {
        BaseResponse response = accountingRuleService.createAccountingRule(acccountingRule, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/accounts/rules/accountingrule/delete/{id}")
    ResponseEntity<?> deleteAccountingRule(@PathVariable long id) {
        BaseResponse response = accountingRuleService.deleteAccountingRule(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/rules/accountingrule/get/{id}")
    ResponseEntity<?> getAccountingRuleByID(@PathVariable long id) {
        BaseResponse response = accountingRuleService.getAccountingRuleByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/rules/accountingrules/getall")
    ResponseEntity<?> getAllAccountingRules() {
        BaseResponse response = accountingRuleService.getAllAccountingRules();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/accountingrules/get/{id}")
    ResponseEntity<?> getAccountingRuleByRulesTypeID(@PathVariable long id) {
        BaseResponse response = accountingRuleService.getAccountingRulesByRuleTypeID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accounts/rules/accountingrule/update")
    ResponseEntity<?> updateAccountingRule(@RequestAttribute("userId") long userid, @RequestBody AccountingRule accountingRule) {
        BaseResponse response = accountingRuleService.editAccountingRule(accountingRule, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accounts/rules/account/accountingrules/assign")
    ResponseEntity<?> assignAccountingRulesToAccount(@RequestParam long accountID, @RequestParam List<Long> ruleIDs) {
        BaseResponse response = userAccountRuleService.assignAccountingRulesToAccount(accountID, ruleIDs);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/accounts/rules/account/accountingrules/delete")
    ResponseEntity<?> removeAccountAccountingRules(@RequestParam List<Long> userAccountRuleIds) {
        BaseResponse response = userAccountRuleService.deleteUserAccountRule(userAccountRuleIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accounts/rules/accountrule/create")
    ResponseEntity<?> createAccountRule(@RequestAttribute("userId") long userid, @Valid @RequestBody UserAccountRule acccountRule) {
        BaseResponse response = userAccountRuleService.createAccountRule(acccountRule, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/accounts/rules/accountrule/delete/{id}")
    ResponseEntity<?> deleteAccountRule(@PathVariable long id) {
        BaseResponse response = userAccountRuleService.deleteAccountRule(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/rules/accountrule/get/{id}")
    ResponseEntity<?> getAccountRuleByID(@PathVariable long id) {
        BaseResponse response = userAccountRuleService.getAccountRuleByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/rules/accountrules/getall")
    ResponseEntity<?> getAllAccountRules() {
        BaseResponse response = userAccountRuleService.getAllAccountRules();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accounts/rules/accountrule/update")
    ResponseEntity<?> updateAccountRule(@RequestAttribute("userId") long userid, @RequestBody UserAccountRule userAccountRule) {
        BaseResponse response = userAccountRuleService.editAccountRule(userAccountRule, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accounts/useraccount/create")
    ResponseEntity<?> createUserAccount(@RequestAttribute("userId") long userid, @Valid @RequestBody UserAccount userAccount) {
        BaseResponse response = userAccountService.createUserAccount(userAccount, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/accounts/useraccount/delete/{id}")
    ResponseEntity<?> deleteUserAccount(@PathVariable long id) {
        BaseResponse response = userAccountService.deleteUserAccount(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/useraccount/get/{id}")
    ResponseEntity<?> getUserAccountById(@PathVariable long id) {
        BaseResponse response = userAccountService.getUserAccountByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/useraccounts/getall")
    ResponseEntity<?> getAllUserAccounts() {
        BaseResponse response = userAccountService.getAllUserAccounts();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accounts/useraccount/update")
    ResponseEntity<?> updateUserAccount(@RequestAttribute("userId") long userid, @RequestBody UserAccount userAccount) {
        BaseResponse response = userAccountService.editUserAccount(userAccount, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accounts/rule/specialaccountrule/create")
    ResponseEntity<?> createSpecialAccountRule(@RequestAttribute("userId") long userid, @Valid @RequestBody SpecialAccountRules specialAccountRules) {
        BaseResponse response = specialAccountRuleService.createSpecialAccountRule(specialAccountRules, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/accounts/rule/specialaccountrule/delete/{id}")
    ResponseEntity<?> deleteSpecialAccountRule(@PathVariable long id) {
        BaseResponse response = specialAccountRuleService.deleteSpecialAccountRule(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/rules/specialaccountrule/get/{id}")
    ResponseEntity<?> getSpecialAccountRuleId(@PathVariable long id) {
        BaseResponse response = specialAccountRuleService.getSpecialAccountRuleByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accounts/rules/specialaccountrules/getall")
    ResponseEntity<?> getAllSpecialAccountRules() {
        BaseResponse response = specialAccountRuleService.getAllSpecialAccountRules();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accounts/rules/specialaccountrule/update")
    ResponseEntity<?> updateSpecialAccountRule(@RequestAttribute("userId") long userid, @RequestBody SpecialAccountRules specialAccountRules) {
        BaseResponse response = specialAccountRuleService.editSpecialAccountRule(specialAccountRules, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
