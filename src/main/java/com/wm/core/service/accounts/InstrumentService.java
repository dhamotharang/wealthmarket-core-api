package com.wm.core.service.accounts;

import com.wm.core.model.accounts.Instrument;
import com.wm.core.model.accounts.LedgerAccount;
import com.wm.core.model.accounts.LedgerType;
import com.wm.core.model.accounts.SubLedgerAccount;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.User;
import com.wm.core.repository.account.FinancialInstrumentRepository;
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
public class InstrumentService {


    @Autowired
    UserRepository userRepo;

    @Autowired
    FinancialInstrumentRepository instrumentRepo;




    public BaseResponse createFinancialInstrument(Instrument instrument, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            instrument.setUserId(user.getId());
            instrument.setDate_created(new Date());
            instrument.setLast_updated(new Date());
            Instrument newInstrument = instrumentRepo.save(instrument);
            response.setData(newInstrument);
            response.setDescription("Financial Instrument created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Financial Instrument not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }


    public BaseResponse deleteFinancialInstrument(long id) {
        BaseResponse response = new BaseResponse();
        Optional<Instrument> instrument = instrumentRepo.findById(id);
        if (instrumentRepo.existsById(id)) {
            instrumentRepo.deleteById(id);
            response.setDescription("Financial Instrument deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("Financial Instrument not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }


    public BaseResponse editFinancialInstrument(Instrument instrument, long userId) {
        BaseResponse response = new BaseResponse();
        Optional<Instrument> financialInstrument = instrumentRepo.findById(instrument.getId());
        if (financialInstrument.isPresent()) {
            instrument.setLast_updated(new Date());
            instrument.setUserId(userId);
            Instrument updatedInstrument = instrumentRepo.save(instrument);
            response.setData(updatedInstrument);
            response.setDescription("Financial Instrument updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("Financial Instrument not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;
    }

    public BaseResponse getAllFinancialInstruments() {
        BaseResponse response = new BaseResponse();
        List<Instrument> instruments = instrumentRepo.findAll();
        if (!instruments.isEmpty()) {
            response.setData(instruments);
            response.setDescription("Financial Instrument(s) found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Financial Instrument found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse getFinancialInstrumentByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (instrumentRepo.existsById(id)) {
            Optional<Instrument> instrument = instrumentRepo.findById(id);
            response.setData(instrument);
            response.setDescription("Financial Instrument found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No Financial Instrument found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

}
