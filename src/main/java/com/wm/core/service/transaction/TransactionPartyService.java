package com.wm.core.service.transaction;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.transaction.TransactionParty;
import com.wm.core.model.user.User;
import com.wm.core.repository.transaction.TransactionPartyRepository;
import com.wm.core.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionPartyService {

    Logger logger = LoggerFactory.getLogger(TransactionPartyService.class.getName());

    @Autowired
    TransactionPartyRepository transactionPartyRepo;

    @Autowired
    UserRepository userRepo;

    public BaseResponse getAllTransactionParties() {
        BaseResponse response = new BaseResponse();
        List<TransactionParty> transactionParties = transactionPartyRepo.findAll();
        if (!transactionParties.isEmpty()) {
            response.setData(transactionParties);
            response.setDescription("Transaction parties exist");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParties.toString());
        } else {
            response.setDescription("Transaction party not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getTransactionPartyById(Long id) {
        BaseResponse response = new BaseResponse();
        if (transactionPartyRepo.existsById(id)) {
            Optional<TransactionParty> transactionParty = transactionPartyRepo.findById(id);
            response.setData(transactionParty);
            response.setDescription("Transaction party found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParty.toString());
        } else {
            response.setDescription("Transaction party id " + id + " not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse createNewTransactionParty(TransactionParty transactionParty, long id) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(id).get();
            transactionParty.setCreatedBy(user.getId());
            transactionParty.setDateCreated(new Date());
            transactionParty.setLastUpdated(new Date());
            TransactionParty newTransactionType = transactionPartyRepo.save(transactionParty);
            response.setData(newTransactionType);
            response.setDescription("Transaction type created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParty.toString());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Transaction type not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse editTransactionParty(TransactionParty transactionParty, long userId) {
        BaseResponse response = new BaseResponse();
        if (transactionPartyRepo.existsById(transactionParty.getId())) {
            transactionParty.setCreatedBy(userId);
            transactionParty.setLastUpdated(new Date());
            TransactionParty updatedTransactionType = transactionPartyRepo.save(transactionParty);
            response.setData(transactionParty);
            response.setDescription("Transaction type updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParty.toString());
        } else {
            response.setDescription("Transaction type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse deleteTransactionParty(Long id){
        BaseResponse response = new BaseResponse();
        Optional<TransactionParty> transactionParty = transactionPartyRepo.findById(id);
        if (transactionPartyRepo.existsById(id)) {
            transactionPartyRepo.deleteById(id);
            response.setData(transactionParty);
            response.setDescription("Transaction party deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParty.toString());
        } else {
            response.setDescription("No Transaction party found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }
}