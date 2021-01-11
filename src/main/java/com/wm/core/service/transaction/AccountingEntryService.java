package com.wm.core.service.transaction;

import com.wm.core.model.accounts.LedgerAccount;
import com.wm.core.model.accounts.SubLedgerAccount;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.transaction.AccountingEntry;
import com.wm.core.model.transaction.TransactionParameter;
import com.wm.core.model.transaction.TransactionParty;
import com.wm.core.model.transaction.TransactionType;
import com.wm.core.model.user.User;
import com.wm.core.repository.account.LedgerAccountRepository;
import com.wm.core.repository.account.SubLedgerAccountRepository;
import com.wm.core.repository.transaction.AccountingEntryRepository;
import com.wm.core.repository.transaction.TransactionParameterRepository;
import com.wm.core.repository.transaction.TransactionPartyRepository;
import com.wm.core.repository.transaction.TransactionTypeRepository;
import com.wm.core.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class AccountingEntryService {

    Logger logger = LoggerFactory.getLogger(AccountingEntryService.class.getName());

    @Autowired
    AccountingEntryRepository accountingEntryRepo;

    @Autowired
    TransactionTypeRepository transactionTypeRepo;

    @Autowired
    TransactionPartyRepository transactionPartyRepo;

    @Autowired
    TransactionParameterRepository transactionParameterRepo;

    @Autowired
    LedgerAccountRepository ledgerAccountRepo;

    @Autowired
    SubLedgerAccountRepository subLedgerAccountRepo;

    @Autowired
    UserRepository userRepo;

    public BaseResponse getAllAccountingEntries() {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> listAccEntries = new ArrayList<>();
        List<AccountingEntry> accountingEntries = accountingEntryRepo.findAll();
        if (!accountingEntries.isEmpty()) {
            for (AccountingEntry accountingEntry : accountingEntries) {
                HashMap<String, Object> details = new HashMap<>();
                details.put("accountingEntries", accountingEntry);

                Optional<TransactionType> transactionType = transactionTypeRepo.findById(accountingEntry.getTransactionTypeId());
                details.put("t_type", transactionType.get());

                Optional<TransactionParameter> transactionParameter = transactionParameterRepo.findById(accountingEntry.getTransactionParameterId());
                details.put("t_parameter", transactionParameter.get());

                Optional<LedgerAccount> ledgerAccount = ledgerAccountRepo.findById(accountingEntry.getId());
                details.put("ledger_acc", ledgerAccount.get());

                Optional<SubLedgerAccount> subLedgerAccount = subLedgerAccountRepo.findById(accountingEntry.getId());
                details.put("sub_ledger_acc", subLedgerAccount.get());

                Optional<TransactionParty> transactionParty = transactionPartyRepo.findById(accountingEntry.getId());
                details.put("transactionParty", transactionParty.get());

                listAccEntries.add(details);
            }
            if (!listAccEntries.isEmpty()) {
                response.setData(listAccEntries);
                response.setDescription("Accounting entry found successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription());
            } else {
                response.setDescription("Accounting entry not found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Accounting entry not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getAccountingEntryById(Long id) {
        BaseResponse response = new BaseResponse();
        if (accountingEntryRepo.existsById(id)) {
            Optional<AccountingEntry> accountingEntry = accountingEntryRepo.findById(id);
            response.setData(accountingEntry);
            response.setDescription("Accounting entry found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", accountingEntry.toString());
        } else {
            response.setDescription("No accounting entry found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse createNewAccountingEntry(AccountingEntry accountingEntry, TransactionType transactionType, long userId) {
        BaseResponse response = new BaseResponse();
        if (transactionTypeRepo.existsById(transactionType.getId())) {
            Optional<TransactionType> transactionTypeDb = transactionTypeRepo.findById(accountingEntry.getId());
            response.setData(transactionTypeDb);
            response.setDescription("Transaction type found");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionTypeDb.toString());

            if (transactionType != null) {
                User user = userRepo.findById(userId).get();
                accountingEntry.setCreatedBy(user.getId());
                accountingEntry.setDateCreated(new Date());
                accountingEntry.setLastUpdated(new Date());
                AccountingEntry newAccountingEntry = accountingEntryRepo.save(accountingEntry);
                response.setData(newAccountingEntry);
                response.setDescription("Accounting entry created successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", accountingEntry.toString());

            } else {
                response.setDescription("Accounting entry not created");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Transaction type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse editAccountingEntry(AccountingEntry accountingEntry, long userId) {
        BaseResponse response = new BaseResponse();
        if (accountingEntryRepo.existsById(accountingEntry.getId())) {
            accountingEntry.setCreatedBy(userId);
            accountingEntry.setLastUpdated(new Date());
            AccountingEntry updatedAccountingEntry = accountingEntryRepo.save(accountingEntry);
            response.setData(updatedAccountingEntry);
            response.setDescription("Accounting entry updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedAccountingEntry.toString());
        } else {
            response.setDescription("No accounting entry found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse deleteAccountingEntry(Long id) {
        BaseResponse response = new BaseResponse();
        Optional<AccountingEntry> accountingEntry = accountingEntryRepo.findById(id);
        if (accountingEntryRepo.existsById(id)) {
            response.setData(accountingEntry);
            response.setDescription("Accounting entry found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", accountingEntry.toString());
        } else {
            response.setDescription("No Accounting entry found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }
}