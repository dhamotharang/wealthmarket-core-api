package com.wm.core.service.accounts;

import com.wm.core.model.accounts.LedgerAccount;
import com.wm.core.model.accounts.SubLedgerAccount;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.User;
import com.wm.core.repository.account.LedgerAccountRepository;
import com.wm.core.repository.account.SubLedgerAccountRepository;
import com.wm.core.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LedgerAccountService {

    @Autowired
    LedgerAccountRepository ledgerAccountRepo;

    @Autowired
    SubLedgerAccountRepository subLedgerRepo;

    @Autowired
    UserRepository userRepo;


    public BaseResponse createLedgerAccount(LedgerAccount ledgerAccount, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            ledgerAccount.setUserId(user.getId());
            ledgerAccount.setDate_created(new Date());
            ledgerAccount.setLast_updated(new Date());
            LedgerAccount newLedger = ledgerAccountRepo.save(ledgerAccount);
            response.setData(newLedger);
            response.setDescription("Ledger Account created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Ledger Account not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }


    public BaseResponse deleteLedgerAccount(long ledgerId) {
        BaseResponse response = new BaseResponse();
        if (ledgerAccountRepo.existsById(ledgerId)) {
            List<SubLedgerAccount> subLedgerAccounts = subLedgerRepo.findByLedgerId(ledgerId);
            if (!subLedgerAccounts.isEmpty()) {
                subLedgerRepo.deleteAll(subLedgerAccounts);
            }
            ledgerAccountRepo.deleteById(ledgerId);
            response.setDescription("Ledger Account deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("Ledger Account not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }


    public BaseResponse editLedgerAccount(LedgerAccount ledgerAccount, long userId) {
        BaseResponse response = new BaseResponse();
        Optional<LedgerAccount> ledger = ledgerAccountRepo.findById(ledgerAccount.getId());
        if (ledger.isPresent()) {
            ledgerAccount.setLast_updated(new Date());
            ledgerAccount.setUserId(userId);
            LedgerAccount updatedLedger = ledgerAccountRepo.save(ledgerAccount);
            response.setData(updatedLedger);
            response.setDescription("Ledger account updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("Ledger account not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse getAllLedgerAccounts() {
        BaseResponse response = new BaseResponse();
        List<LedgerAccount> ledgerAccounts = ledgerAccountRepo.findAll();
        if (!ledgerAccounts.isEmpty()) {
            response.setData(ledgerAccounts);
            response.setDescription("Ledger accounts found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Ledger accounts found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getLedgerAccountByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (ledgerAccountRepo.existsById(id)) {
            Optional<LedgerAccount> ledger = ledgerAccountRepo.findById(id);
            response.setData(ledger);
            response.setDescription("Ledger account found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Ledger account found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

}
