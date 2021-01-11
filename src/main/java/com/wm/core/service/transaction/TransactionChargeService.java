package com.wm.core.service.transaction;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.transaction.TransactionCharge;
import com.wm.core.model.transaction.TransactionType;
import com.wm.core.model.user.User;
import com.wm.core.repository.transaction.TransactionChargeRepository;
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
public class TransactionChargeService {

    Logger logger = LoggerFactory.getLogger(TransactionChargeService.class.getName());

    @Autowired
    TransactionChargeRepository transactionChargeRepo;

    @Autowired
    UserRepository userRepo;

    public BaseResponse getAllTransactionCharges() {
        BaseResponse response = new BaseResponse();
        List<TransactionCharge> transactionCharges = transactionChargeRepo.findAll();
        if (!transactionCharges.isEmpty()) {
            response.setData(transactionCharges);
            response.setDescription("Transaction charge exist");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionCharges.toString());
        } else {
            response.setDescription("No Transaction charge found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getTransactionChargeById(Long id){
        BaseResponse response = new BaseResponse();
        if(transactionChargeRepo.existsById(id)){
            Optional<TransactionCharge> transactionCharge = transactionChargeRepo.findById(id);
            response.setData(transactionCharge);
            response.setDescription("Transaction charge found");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionCharge.toString());
        }else{
            response.setDescription("No Transaction charge found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse createNewTransactionCharge(TransactionCharge transactionCharge, long userId){
        BaseResponse response = new BaseResponse();
        try{
            User user = userRepo.findById(userId).get();
            transactionCharge.setCreatedBy(user.getId());
            transactionCharge.setDateCreated(new Date());
            transactionCharge.setLastUpdated(new Date());
            TransactionCharge newTransactionCharge = transactionChargeRepo.save(transactionCharge);
            response.setData(newTransactionCharge);
            response.setDescription("Transaction charge created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionCharge.toString());
        }catch(IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            response.setDescription("Transaction charge not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse editTransactionCharge(TransactionCharge transactionCharge, long userId){
        BaseResponse response = new BaseResponse();
        if(transactionChargeRepo.existsById(transactionCharge.getId())){
            transactionCharge.setCreatedBy(userId);
            transactionCharge.setLastUpdated(new Date());
            TransactionCharge transactionChargeDb = transactionChargeRepo.save(transactionCharge);
            response.setData(transactionCharge);
            response.setDescription("Transaction charge updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionCharge.toString());
        }else{
            response.setDescription("Transaction charge not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse deleteTransactionCharge(Long id){
        BaseResponse response = new BaseResponse();
        Optional<TransactionCharge> transactionCharge = transactionChargeRepo.findById(id);
        if(transactionChargeRepo.existsById(id)){
            transactionChargeRepo.deleteById(id);
            response.setDescription("Transaction charge deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", transactionCharge.toString());
        }else{
            response.setDescription("No Transaction charge found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }
}