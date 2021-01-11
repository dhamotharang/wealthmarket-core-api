package com.wm.core.service.user;

import com.google.i18n.phonenumbers.NumberParseException;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.*;
import com.wm.core.repository.user.*;
import com.wm.core.utility.UtilityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.*;

@Service
public class DependantService {
    Logger logger = LoggerFactory.getLogger(DependantService.class.getName());

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserTypeRepository userTypeRepo;

    UtilityManager utilityManager = new UtilityManager();

    @Autowired
    MemberTypeRepository memberTypeRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired
    StatusRepository statusRepo;

    @Autowired
    DependantsRepository dependantsRepo;

    public BaseResponse registerDependatnUser(long userid, NewUser newUser, long parentId) throws NumberParseException {
        BaseResponse response = new BaseResponse();
        if (utilityManager.isValidEmail(newUser.getEmail())) {
            if (!userService.checkEmailAddressOrPhoneNumberExist(newUser.getEmail(), newUser.getPhone())) {
                String gender = newUser.getGender().toLowerCase();
                if (gender.equals("male") || gender.equals("female")) {
                    if (utilityManager.isValidPhone(newUser.getPhone(), "NG")) {
                        if (utilityManager.isValidDate(newUser.getDob(), "dd-MM-uuuu")) {
                            if (!userService.checkUsernameExist(newUser.getUserName())) {

                                //to check if you are creating for another user
                                if( parentId != -1) userid = parentId;
                                response = createDependantUser(userid, newUser);
                            } else {
                                response.setDescription("username already picked, please enter another username");
                                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                                logger.error(response.getDescription());
                            }
                        } else {
                            response.setDescription(
                                    "invalid date of birth, please use format dd-MM-yyyy e.g 10-08-1990");
                            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                            logger.error(response.getDescription());
                        }
                    } else {
                        response.setDescription("invalid phone number, please enter correct number");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                    }
                } else {
                    response.setDescription("invalid gender, please enter male or female");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            } else {
                response.setDescription("user already registered");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("please enter a valid email");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }


    public BaseResponse createDependantUser(long parentid, NewUser newUser) {
        BaseResponse response = new BaseResponse();
        try {
            String unique_id = "WM-" + utilityManager.randomAlphaNumeric(7);
            LocalDate birthDate = utilityManager.getJavaDateFromString(newUser.getDob());
            int age = utilityManager.getYearDiffrenceFromLocalDate(birthDate);
            String password = passwordEncoder.encode(newUser.getPassword());
            String email = newUser.getEmail().toLowerCase();

            Optional<UserType> userType = userTypeRepo.findUserTypeByName("Dependant");
            if (userType.isPresent()) {
                Optional<Status> userStatus = statusRepo.findByName("Not Activated");
                if (userStatus.isPresent()) {
                    Optional<Status> dependantStatus = statusRepo.findByName("Connected");
                    if (dependantStatus.isPresent()) {
                        Optional<MemberType> memberType = memberTypeRepo.findById(1l);
                        LocalDate currentDate = LocalDate.now();

                        User user = new User(email, newUser.getPhone(), password, userStatus.get().getId(), newUser.getGender(),
                                newUser.getFirstName(), newUser.getLastName(), newUser.getUserName(), 60, unique_id, age, currentDate, new Date(), 0, 0, memberType.get().getId()
                                , userType.get().getId(), newUser.isAccept_newsletter(), newUser.isAccept_offers());

                        User created_user = userRepo.save(user);
                        Dependants dependants = new Dependants();
                        dependants.setDependantuserId(created_user.getId());
                        dependants.setParentuserId(parentid);
                        dependants.setStatusId(dependantStatus.get().getId());
                        Dependants newDependantUser = dependantsRepo.save(dependants);
                        String name = created_user.getLast_name() + " " + created_user.getFirst_name();
                        userService.CreateUserVerifcation(created_user.getId(), created_user.getEmail(), "ConfirmEmail", name);
                        response.setData(newDependantUser);
                        response.setDescription("Dependant User created successfully");
                        response.setStatusCode(HttpServletResponse.SC_OK);
                        logger.info(response.getDescription() + ": {}", newDependantUser.toString());
                    } else {
                        response.setDescription("Status not found, please create status first");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                    }
                } else {
                    response.setDescription("Status not found, please create status first");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            } else {
                response.setDescription("User type not found, please create Dependant user type first");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("user not registered, check details");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;

    }


    public BaseResponse upgradeDependant(long userId) {
        BaseResponse response = new BaseResponse();
        if (userRepo.existsById(userId)) {
            Optional<User> user = userRepo.findById(userId);
            Optional<UserType> userType = userTypeRepo.findById(user.get().getUsertypeId());
            if (userType.isPresent()) {
                if (userType.get().getName().equals("Dependant")) {
                    Optional<UserType> newType = userTypeRepo.findUserTypeByName("Member");
                    if (newType.isPresent()) {
                        Optional<Dependants> dependant = dependantsRepo.findByDependantuserId(user.get().getId());
                        if (dependant.isPresent()) {
                            Optional<Status> status = statusRepo.findByName("Disconnected");
                            if (status.isPresent()) {
                                dependant.get().setStatusId(status.get().getId());
                                dependantsRepo.save(dependant.get());
                                user.get().setUsertypeId(newType.get().getId());
                                User savedUser = userRepo.save(user.get());
                                response.setData(savedUser);
                                response.setDescription("Dependant User upgraded successfully.");
                                response.setStatusCode(HttpServletResponse.SC_OK);
                                logger.info(response.getDescription() + ": {}", savedUser.toString());
                            } else {
                                response.setDescription("Status not found, please create status first.");
                                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                                logger.error(response.getDescription());
                            }
                        } else {
                            response.setDescription("Dependant User not found.");
                            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                            logger.error(response.getDescription());
                        }
                    } else {
                        response.setDescription("Something went wrong, Usertype not found.");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                    }
                } else {
                    response.setDescription("You can only upgrade a dependant user.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            } else {
                response.setDescription("You can only upgrade a dependant user.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Dependant User not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getUserDependants(long userId, long parentId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> dependantList = new ArrayList<>();

        //to check if you want to get dependency for another user
        if( parentId != -1) userId = parentId;
        List<Dependants> dependants = dependantsRepo.findByParentuserId(userId);
        if (!dependants.isEmpty()) {
            for (Dependants dep : dependants) {
                HashMap<String, Object> depdetails = new HashMap<>();
                depdetails.put("dependantData", dep);
                Optional<Status> status = statusRepo.findById(dep.getStatusId());
                depdetails.put("dependantStatus", status);
                Optional<User> user = userRepo.findById(dep.getDependantuserId());
                depdetails.put("dependantUserData", user);
                Optional<UserType> userType = userTypeRepo.findById(user.get().getUsertypeId());
                depdetails.put("dependantUsertype", userType);
                Optional<MemberType> memberType = memberTypeRepo.findById(user.get().getMembertypeId());
                depdetails.put("dependantMembertype", memberType);
                dependantList.add(depdetails);
            }
            response.setData(dependantList);
            response.setDescription("Dependants found successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No users found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse deleteDependant(long dependantId) {
        BaseResponse response = new BaseResponse();
        if (dependantsRepo.existsById(dependantId)) {
            dependantsRepo.deleteById(dependantId);
            response.setDescription("Dependant deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("Dependant not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }
}
