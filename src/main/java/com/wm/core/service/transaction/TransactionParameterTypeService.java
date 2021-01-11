package com.wm.core.service.transaction;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.transaction.TransactionParameterType;
import com.wm.core.model.user.User;
import com.wm.core.repository.transaction.TransactionParameterTypeRepository;
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
public class TransactionParameterTypeService {

    Logger logger = LoggerFactory.getLogger(TransactionParameterTypeService.class.getName());

    @Autowired
    TransactionParameterTypeRepository transactionParameterTypeRepo;

    @Autowired
    UserRepository userRepo;

    public BaseResponse getAllTransactionParameterTypes() {
        BaseResponse response = new BaseResponse();
        List<TransactionParameterType> transactionParameterTypes = transactionParameterTypeRepo.findAll();
        if (!transactionParameterTypes.isEmpty()) {
            response.setData(transactionParameterTypes);
            response.setDescription("Transaction parameter type is found");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParameterTypes.toString());
        } else {
            response.setDescription("No Transaction parameter type found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getTransactionParameterTypeById(Long id) {
        BaseResponse response = new BaseResponse();
        if (transactionParameterTypeRepo.existsById(id)) {
            Optional<TransactionParameterType> transactionParameterTypeDb = transactionParameterTypeRepo.findById(id);
            response.setData(transactionParameterTypeDb);
            response.setDescription("Transaction parameter type found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParameterTypeDb.toString());
        } else {
            response.setDescription("No Transaction parameter type found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse createNewTransactionParameterType(TransactionParameterType transactionParameterType, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            transactionParameterType.setCreatedBy(user.getId());
            transactionParameterType.setDateCreated(new Date());
            transactionParameterType.setLastUpdated(new Date());
            TransactionParameterType newTransactionParameterType = transactionParameterTypeRepo.save(transactionParameterType);
            response.setData(newTransactionParameterType);
            response.setDescription("Transaction parameter type created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParameterType.toString());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Transaction parameter type not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse editTransactionParameterType(TransactionParameterType transactionParameterType, long userId) {
        BaseResponse response = new BaseResponse();
        if (transactionParameterTypeRepo.existsById(transactionParameterType.getId())) {
            transactionParameterType.setLastUpdated(new Date());
            transactionParameterType.setCreatedBy(userId);
            TransactionParameterType updatedTransactionParameterType = transactionParameterTypeRepo.save(transactionParameterType);
            response.setData(updatedTransactionParameterType);
            response.setDescription("Transaction parameter type updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParameterType.toString());
        } else {
            response.setDescription("Transaction parameter type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse deleteTransactionParameterType(Long id){
        BaseResponse response = new BaseResponse();
        Optional<TransactionParameterType> transactionParameterType = transactionParameterTypeRepo.findById(id);
        if(transactionParameterTypeRepo.existsById(id)){
            transactionParameterTypeRepo.deleteById(id);
            response.setData(transactionParameterType);
            response.setDescription("Transaction parameter type deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        }else{
            response.setDescription("No Transaction parameter found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }
}