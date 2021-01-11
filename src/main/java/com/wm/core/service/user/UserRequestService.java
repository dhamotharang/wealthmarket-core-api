package com.wm.core.service.user;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.ChangeDetails;
import com.wm.core.model.user.ChangeRequest;
import com.wm.core.model.user.Status;
import com.wm.core.model.user.User;
import com.wm.core.repository.user.ChangeDetailsRepository;
import com.wm.core.repository.user.StatusRepository;
import com.wm.core.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserRequestService {
    Logger logger = LoggerFactory.getLogger(UserRequestService.class.getName());

    @Autowired
    ChangeDetailsRepository requestRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StatusRepository statusRepo;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    public BaseResponse approveOrRejectUserRequest(long requestId) {
        BaseResponse response = new BaseResponse();
        if (requestRepo.existsById(requestId)) {
            Optional<ChangeDetails> request = requestRepo.findById(requestId);
            User user = userRepo.findById(request.get().getUserId()).get();
            Optional<Status> approvedstatus = statusRepo.findByName("Approved");
            Optional<Status> rejectedstatus = statusRepo.findByName("Rejected");
            if (approvedstatus.isPresent() && rejectedstatus.isPresent()) {

                if (!userService.checkEmailAddressOrPhoneNumberExist(request.get().getNew_detail(), (request.get().getNew_detail()))) {
                    if (request.get().getSubject().equalsIgnoreCase("email")) {
                        user.setEmail(request.get().getNew_detail());
                    } else {
                        user.setPhone_number(request.get().getNew_detail());
                    }

                    userRepo.save(user);
                    request.get().setStatusId(approvedstatus.get().getId());
                    request.get().setReason("Request has been approved.");
                    ChangeDetails updatedRequest = requestRepo.save(request.get());
                    response.setData(updatedRequest);
                    response.setDescription("User request approved..");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription() + ": {}", updatedRequest.toString());
                    String UserName = user.getFirst_name() + " " + user.getLast_name();
                    emailService.sendEmail(user.getEmail(), UserName, "", "Change Request");
                } else {
                    request.get().setStatusId(rejectedstatus.get().getId());
                    request.get().setReason("Request has been rejected, the Email or Phone number already exist.");
                    ChangeDetails updatedRequest = requestRepo.save(request.get());
                    response.setData(updatedRequest);

                    response.setDescription("User request rejected.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.error(response.getDescription() );
                }


            } else {
                response.setDescription("Approved/Rejected Status not found, please, create Approved/Rejected Status first.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription() );
            }


        } else {
            response.setDescription("request not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription() );
        }

        return response;
    }

    public BaseResponse getAllRequestedChanges() {
        BaseResponse response = new BaseResponse();
        List<ChangeDetails> requests = requestRepo.findAll();
        if (!requests.isEmpty()) {
            response.setData(requests);
            response.setDescription("Requests found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() );
        } else {
            response.setDescription("No requests found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription() );
        }

        return response;
    }

    public BaseResponse getUserRequestedChanges(long userId) {
        BaseResponse response = new BaseResponse();
        if (userRepo.existsById(userId)) {
            List<ChangeDetails> requests = requestRepo.findByUserId(userId);
            if (!requests.isEmpty()) {
                response.setData(requests);
                response.setDescription("requested changes found successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() );
            } else {
                response.setDescription("no requested change found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription() );
            }
        } else {
            response.setDescription("no requested change found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription() );
        }

        return response;
    }

    public BaseResponse requestDetailsChange(long userId, ChangeRequest requestDetails) {
        BaseResponse response = new BaseResponse();
        if (userRepo.existsById(userId)) {
            Optional<User> user = userRepo.findById(userId);
            if (!passwordEncoder.matches(requestDetails.getPassword(), user.get().getPassword())) {
                response.setDescription("Invalid password, please, enter your current password");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
                return response;
            }
            if (!(requestDetails.getSubject().equalsIgnoreCase("email") || requestDetails.getSubject().equalsIgnoreCase("phone"))) {
                response.setDescription("Invalid subject, Please, use email or phone");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
                return response;
            }

            Optional<Status> status = statusRepo.findByName("Pending");
            if (!status.isPresent()) {
                response.setDescription("Please, create Pending Status first.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
                return response;
            }


            String old_detail = "";
            if (requestDetails.getSubject().equalsIgnoreCase("phone")) {
                old_detail = user.get().getPhone_number();
            } else if (requestDetails.getSubject().equalsIgnoreCase("email")) {
                old_detail = user.get().getEmail();
            }

            ChangeDetails details = new ChangeDetails(new Date(), status.get().getId(), requestDetails.getSubject(), old_detail, requestDetails.getNew_detail(), userId, "");
            ChangeDetails newDetails = requestRepo.save(details);
            response.setData(newDetails);
            response.setDescription("user request submitted succesfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", newDetails.toString());

        } else {
            response.setDescription("can't create request, user not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


}
