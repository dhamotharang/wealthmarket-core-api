
package com.wm.core.service.accounts;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.repository.account.UserAccountRepository;
import com.wm.core.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class DefaultUserAccountService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserAccountRepository userAccountRepo;


    public BaseResponse createDefaultAccountsForUser(long accountTypeId, long objectId) {
        BaseResponse response = new BaseResponse();
        try {

            //STEP 1 get a list of all the default accounts for that User using the accountTypeId(userType, groupTypeId, etc.)

            //create user account (instance of that account) for the user or organization using the accountTypeId and objectId

            response.setDescription("User Accounts created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("User Account not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    public BaseResponse addDefaultAccountsForAccountType(long accountTypeId, Long objectId, List<Long> accountIds) {
        BaseResponse response = new BaseResponse();

        //for each of the accountId create default with the accountId, the objectId and accountTypeId
        return response;

    }

    public BaseResponse removeDefaultAccountsForAccountType(long accountTypeId, Long objectId, List<Long> accountIds) {
        BaseResponse response = new BaseResponse();

        //check that the account type exists
        //check that the default account exist
        //remove default accounts with the accountId ,objectId and accountTypeId
        return response;

    }


}
