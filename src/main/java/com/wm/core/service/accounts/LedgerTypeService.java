
package com.wm.core.service.accounts;

import com.wm.core.model.accounts.Account;
import com.wm.core.model.accounts.AccountType;
import com.wm.core.model.accounts.LedgerType;
import com.wm.core.model.accounts.SubLedgerAccount;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.User;
import com.wm.core.repository.account.AccountRepository;
import com.wm.core.repository.account.AccountTypeRepository;
import com.wm.core.repository.account.LedgerTypeRepository;
import com.wm.core.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LedgerTypeService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    LedgerTypeRepository ledgerTypeRepo;


    public BaseResponse createLedgerType(LedgerType ledgerType, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            ledgerType.setUserId(user.getId());
            ledgerType.setDate_created(new Date());
            ledgerType.setLast_updated(new Date());
            LedgerType newLedgerType = ledgerTypeRepo.save(ledgerType);
            response.setData(newLedgerType);
            response.setDescription("Ledger Type created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Ledger Type not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse deleteLedgerType(long id) {
        BaseResponse response = new BaseResponse();
        Optional<LedgerType> ledgerType = ledgerTypeRepo.findById(id);
        if (ledgerTypeRepo.existsById(id)) {
            ledgerTypeRepo.deleteById(id);
            response.setDescription("Ledger Type deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("Ledger Type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse editLedgerType(LedgerType ledgerType, long userId) {
        BaseResponse response = new BaseResponse();
        if (ledgerTypeRepo.existsById(ledgerType.getId())) {
            ledgerType.setLast_updated(new Date());
            ledgerType.setUserId(userId);
            LedgerType updatedLedgerType = ledgerTypeRepo.save(ledgerType);
            response.setData(updatedLedgerType);
            response.setDescription("Ledger Type updated successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("Ledger Type not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse getAllLedgerTypes() {
        BaseResponse response = new BaseResponse();
        List<LedgerType> ledgerTypes = ledgerTypeRepo.findAll();
        if (!ledgerTypes.isEmpty()) {
            response.setData(ledgerTypes);
            response.setDescription("Ledger Types found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Ledger Type found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getLedgerTypeByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (ledgerTypeRepo.existsById(id)) {
            Optional<LedgerType> ledgerType = ledgerTypeRepo.findById(id);
            response.setData(ledgerType);
            response.setDescription("Ledger Type found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Ledger Type found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

}
