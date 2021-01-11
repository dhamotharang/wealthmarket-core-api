package com.wm.core.service.transaction;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.transaction.TransactionType;
import com.wm.core.model.user.User;
import com.wm.core.repository.transaction.TransactionTypeRepository;
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
public class TransactionTypeService {

    Logger logger = LoggerFactory.getLogger(TransactionTypeService.class.getName());

    @Autowired
    TransactionTypeRepository transactionTypeRepo;

    @Autowired
    UserRepository userRepository;

    public BaseResponse getAllTransactionTypes() {
        BaseResponse response = new BaseResponse();
        List<TransactionType> transactionTypes = transactionTypeRepo.findAll();
        if (!transactionTypes.isEmpty()) {
            response.setData(transactionTypes);
            response.setDescription("Transaction type exist");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionTypes.toString());
        } else {
            response.setDescription("Transaction type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getTransactionTypeById(Long id) {
        BaseResponse response = new BaseResponse();
        if (transactionTypeRepo.existsById(id)) {
            Optional<TransactionType> transactionType = transactionTypeRepo.findById(id);
            response.setData(transactionType);
            response.setDescription("Transaction type found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionType.toString());
        } else {
            response.setDescription("Transaction type id " + id + "not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse createNewTransactionType(TransactionType transactionType, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepository.findById(userId).get();
            transactionType.setCreatedBy(user.getId());
            transactionType.setDateCreated(new Date());
            transactionType.setLastUpdated(new Date());
            TransactionType newTransactionType = transactionTypeRepo.save(transactionType);
            response.setData(newTransactionType);
            response.setDescription("Transaction type created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionType.toString());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Transaction type not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse editTransactionType(TransactionType transactionType, long userId) {
        BaseResponse response = new BaseResponse();
        if(transactionTypeRepo.existsById(transactionType.getId())){
            transactionType.setLastUpdated(new Date());
            transactionType.setCreatedBy(userId);
            TransactionType updatedTransactionType = transactionTypeRepo.save(transactionType);
            response.setData(updatedTransactionType);
            response.setDescription("Transaction type updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionType.toString());
        }else{
            response.setDescription("Transaction type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse deleteTransactionType(Long id) {
        BaseResponse response = new BaseResponse();
        Optional<TransactionType> transactionType = transactionTypeRepo.findById(id);
        if (transactionTypeRepo.existsById(id)) {
            transactionTypeRepo.deleteById(id);
            response.setDescription("Transaction type deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionType.toString());
        } else {
            response.setDescription("No Transaction type found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }
}