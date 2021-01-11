package com.wm.core.service.transaction;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.transaction.TransactionParameter;
import com.wm.core.model.transaction.TransactionParameterType;
import com.wm.core.model.user.User;
import com.wm.core.repository.transaction.TransactionParameterRepository;
import com.wm.core.repository.transaction.TransactionParameterTypeRepository;
import com.wm.core.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class TransactionParameterService {

    Logger logger = LoggerFactory.getLogger(TransactionParameterService.class.getName());

    @Autowired
    TransactionParameterRepository transactionParameterRepo;

    @Autowired
    TransactionParameterTypeRepository transactionParameterTypeRepo;

    @Autowired
    UserRepository userRepo;

    public BaseResponse getAllTransactionParameters() {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> listTParameter = new ArrayList<>();
        List<TransactionParameter> transactionParameters = transactionParameterRepo.findAll();
        if (!transactionParameters.isEmpty()) {
            for (TransactionParameter transactionParameter : transactionParameters) {
                HashMap<String, Object> details = new HashMap<>();
                details.put("t_parameter", transactionParameter);

                Optional<TransactionParameterType> transactionParameterType = transactionParameterTypeRepo.findById(transactionParameter.getTransactionParameterTypeId());
                details.put("t_parameterType", transactionParameterType.get());

                listTParameter.add(details);
            }
            if (!listTParameter.isEmpty()) {
                response.setData(listTParameter);
                response.setDescription("Transaction parameter found successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription());
            } else {
                response.setDescription("No Transaction parameter found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No Transaction parameter found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getTransactionParameterById(Long id) {
        BaseResponse response = new BaseResponse();
        if (transactionParameterRepo.existsById(id)) {
            Optional<TransactionParameter> transactionParameter = transactionParameterRepo.findById(id);
            response.setData(transactionParameter);
            response.setDescription("Transaction parameter found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParameter.toString());
        } else {
            response.setDescription("No Transaction parameter found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse createNewTransactionParameter(TransactionParameter transactionParameter, long userId) {
        BaseResponse response = new BaseResponse();
        try{
            User user = userRepo.findById(userId).get();
            transactionParameter.setCreatedBy(user.getId());
            transactionParameter.setDateCreated(new Date());
            transactionParameter.setLastUpdated(new Date());
            TransactionParameter newTransactionParameter = transactionParameterRepo.save(transactionParameter);
            response.setData(newTransactionParameter);
            response.setDescription("Transaction parameter created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParameter.toString());
        }catch(IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            response.setDescription("No Transaction parameter found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse editTransactionParameter(TransactionParameter transactionParameter, long userId) {
        BaseResponse response = new BaseResponse();
        if (transactionParameterRepo.existsById(transactionParameter.getId())) {
            transactionParameter.setCreatedBy(userId);
            transactionParameter.setLastUpdated(new Date());
            TransactionParameter updatedTransactionParameter = transactionParameterRepo.save(transactionParameter);
            response.setData(updatedTransactionParameter);
            response.setDescription("Transaction parameter updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParameter.toString());
        } else {
            response.setDescription("No transaction parameter found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse deleteTransactionParameter(Long id) {
        BaseResponse response = new BaseResponse();
        Optional<TransactionParameter> transactionParameter = transactionParameterRepo.findById(id);
        if (transactionParameterRepo.existsById(id)) {
            transactionParameterRepo.deleteById(id);
            response.setData(transactionParameter);
            response.setDescription("Transaction parameter deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionParameter.toString());
        } else {
            response.setDescription("No Transaction parameter found");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.error(response.getDescription());
        }
        return response;
    }
}