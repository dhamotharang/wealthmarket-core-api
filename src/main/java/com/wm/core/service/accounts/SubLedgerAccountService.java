package com.wm.core.service.accounts;

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
public class SubLedgerAccountService {

    @Autowired
    SubLedgerAccountRepository subLedgerRepo;

    @Autowired
    LedgerAccountRepository ledgerAccountRepo;

    @Autowired
    UserRepository userRepo;

    public BaseResponse createSubLedgerAccount(SubLedgerAccount subLedgerAccount, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            subLedgerAccount.setUserId(user.getId());
            subLedgerAccount.setDate_created(new Date());
            subLedgerAccount.setLast_updated(new Date());
            SubLedgerAccount savedSubLedger = subLedgerRepo.save(subLedgerAccount);
            response.setData(savedSubLedger);
            response.setDescription("Sub-Ledger account created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Sub-Ledger account not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }


    public BaseResponse deleteSubLedgerAccount(long id) {
        BaseResponse response = new BaseResponse();
        Optional<SubLedgerAccount> subLedger = subLedgerRepo.findById(id);
        if (subLedgerRepo.existsById(id)) {
            subLedgerRepo.deleteById(id);
            response.setDescription("Sub-Ledger account deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("Sub-Ledger account not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }


    public BaseResponse editSubLedgerAccount(SubLedgerAccount subLedgerAccount, long userId) {
        BaseResponse response = new BaseResponse();
        if (subLedgerRepo.existsById(subLedgerAccount.getId())) {
            subLedgerAccount.setLast_updated(new Date());
            subLedgerAccount.setUserId(userId);
            SubLedgerAccount updatedSubLedger = subLedgerRepo.save(subLedgerAccount);
            response.setData(updatedSubLedger);
            response.setDescription("Sub-Ledger account updated successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("Sub-Ledger account not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse getAllSubLedgerAccounts() {
        BaseResponse response = new BaseResponse();
        List<SubLedgerAccount> subLedgers = subLedgerRepo.findAll();
        if (!subLedgers.isEmpty()) {
            response.setData(subLedgers);
            response.setDescription("Sub-Ledger accounts found successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Sub-Ledger account found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getSubLedgerAccountsByLedgerID(long ledgerId) {
        BaseResponse response = new BaseResponse();
        if (ledgerAccountRepo.existsById(ledgerId)) {
            List<SubLedgerAccount> subLedgerAccounts = subLedgerRepo.findByLedgerId(ledgerId);
            if (!subLedgerAccounts.isEmpty()) {
                response.setData(subLedgerAccounts);
                response.setDescription("Sub-Ledger accounts found successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
            } else {
                response.setDescription("Sub-Ledger account found.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.setDescription("Ledger account not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse getSubLedgerAccountByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (subLedgerRepo.existsById(id)) {
            Optional<SubLedgerAccount> subLedgerAccount = subLedgerRepo.findById(id);
            response.setData(subLedgerAccount);
            response.setDescription("Sub-Ledger account found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Sub-Ledger account found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }
}
