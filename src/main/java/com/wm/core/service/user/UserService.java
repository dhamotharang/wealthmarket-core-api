package com.wm.core.service.user;

import com.google.i18n.phonenumbers.NumberParseException;
import com.wm.core.model.permission.Permission;
import com.wm.core.model.permission.PermissionGroupPermission;
import com.wm.core.model.permission.UserSpecialPermissionGroup;
import com.wm.core.model.permission.UserTypePermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.*;
import com.wm.core.repository.permission.*;
import com.wm.core.repository.user.*;
import com.wm.core.service.agency.AgenciesService;
import com.wm.core.service.business.BusinessesService;
import com.wm.core.service.business.ProducersService;
import com.wm.core.utility.UtilityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserTypeRepository userTypeRepo;

    @Autowired
    GuestVisitorRepository guestVisitorRepo;

    @Autowired
    UserTypeService userTypeService;

    @Autowired
    AgenciesService agenciesService;

    @Autowired
    BusinessesService businessesService;

    @Autowired
    ProducersService producersService;

    @Autowired
    MemberTypeRepository memberTypeRepo;

    UtilityManager utilityManager = new UtilityManager();

    @Autowired
    StatusRepository statusRepo;

    @Autowired
    UserTypePermissionGroupRepository userTypePermissionGroupRepo;

    @Autowired
    PermissionGroupPermissionsRepository permissionGroupPermissionsRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    UserSpecialPermissionGroupRepository userSpecialPermissionGroupRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserVerificationRepository userVerificationRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    PermissionRepository permissionRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    RoleService roleService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${wm.prestashop.user.api-url}")
    String ps_api_url ;

    @Value("${wm.prestashop.user.access-token}")
    String ps_access_token ;


    public BaseResponse registerUser(NewUser newUser) throws NumberParseException {
        BaseResponse response = new BaseResponse();
        if (utilityManager.isValidEmail(newUser.getEmail())) {
            if (!checkEmailAddressOrPhoneNumberExist(newUser.getEmail(), newUser.getPhone())) {
                String gender = newUser.getGender().toLowerCase();
                if (gender.equals("male") || gender.equals("female")) {
                    if (utilityManager.isValidPhone(newUser.getPhone(), "NG")) {
                        if (utilityManager.isValidDate(newUser.getDob(), "dd-MM-yyyy")) {
                            if (!checkUsernameExist(newUser.getUserName())) {
                                response = createUser(newUser);
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

    public boolean checkEmailAddressOrPhoneNumberExist(String email, String phoneNumber) {
        boolean result = false;
        Optional<User> user = userRepo.findByEmailOrPhoneNumber(email, phoneNumber);
        if (user.isPresent()) {
            result = true;
        }
        return result;
    }

    public boolean checkUsernameExist(String username) {
        boolean result = false;
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            result = true;
        }
        return result;
    }

    public BaseResponse createUser(NewUser newUser) {
        BaseResponse response = new BaseResponse();
        try {
            String unique_id = "WM-" + utilityManager.randomAlphaNumeric(7);
            LocalDate birthDate = utilityManager.getJavaDateFromString(newUser.getDob());
            int age = utilityManager.getYearDiffrenceFromLocalDate(birthDate);
            if (age >= 18) {


                String password = passwordEncoder.encode(newUser.getPassword());
                String email = newUser.getEmail().toLowerCase();
                Optional<UserType> userType = userTypeRepo.findUserTypeByName("Member");//Members
                if (userType.isPresent()) {
                    Optional<Status> status = statusRepo.findByName("Not Activated");
                    if (status.isPresent()) {
                        Optional<MemberType> memberType = memberTypeRepo.findById(1l);
                        if (memberType.isPresent()) {
                            //send verification Email
                            User user = new User(email, newUser.getPhone(), password, status.get().getId(), newUser.getGender(), newUser.getFirstName()
                                    , newUser.getLastName(), newUser.getUserName(), 60, unique_id, age, birthDate, new Date(), 0,
                                    0, memberType.get().getId(), userType.get().getId(), newUser.isAccept_newsletter(), newUser.isAccept_offers());
                            User created_user = userRepo.save(user);

                            //set the raw password in the user object to pass to Prestashop
                            created_user.setPassword(newUser.getPassword());
                            //register the user as a customer on prestashop automatically
                            Object customer = createPrestashopCustomer(created_user);
                            System.out.print(customer);

                            response.setData(created_user);
                            String name = user.getLast_name() + " " + user.getFirst_name();
                            CreateUserVerifcation(user.getId(), user.getEmail(), "ConfirmEmail", name);
                            response.setDescription("User registered successfully");
                            response.setStatusCode(HttpServletResponse.SC_OK);
                            logger.info(response.getDescription() + ": {}", created_user.toString());
                        } else {
                            response.setDescription("Member Account Type not found, please create Individual/Corporate Member Type first");
                            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                            logger.error(response.getDescription());
                        }

                    } else {
                        response.setDescription("Status not found, please create Not Activated status first");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                    }
                } else {
                    response.setDescription("User type not found, please create Member User Type first");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            } else {
                response.setDescription("You are not eligble becasue of your age.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("User not registered, check details");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;

    }

    public Object createPrestashopCustomer(User user){
        PrestashopCustomer customer = new PrestashopCustomer();
        customer.setBirthday(user.getDob());
        customer.setEmail(user.getEmail());
        customer.setFirstname(user.getFirst_name());
        customer.setLastname(user.getLast_name());
        customer.setPassword(user.getPassword());
        customer.setWmuniqueid(user.getUnique_id());
        customer.setPhone(user.getPhone_number());
        int gender = 0;
        if(user.getGender().equalsIgnoreCase("male")) gender = 1;
        int acceptOffer = 0;
        if(user.isAccept_offers() == true) acceptOffer = 1;
        int acceptNewsletter = 0;
        if(user.isAccept_newsletter() == true) acceptNewsletter = 1;
        customer.setGender(gender);
        customer.setReceiveoffer(acceptOffer);
        customer.setNewsletter(acceptNewsletter);

        try {
            // create headers
            HttpHeaders headers = new HttpHeaders();
            // set `content-type` header
            headers.setContentType(MediaType.APPLICATION_JSON);
            // set `accept` header
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            // set `access token` as authorization header
            headers.set("Authorization", ps_access_token);

            // build the request
            HttpEntity<PrestashopCustomer> newCustomer = new HttpEntity<>(customer, headers);
            // send POST request
            return restTemplate.postForObject(ps_api_url, newCustomer, PrestashopCustomer.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return "customer creation failed";
        }

    }

    public BaseResponse getUserDetails(long userId) {
        BaseResponse response = new BaseResponse();
        if (userRepo.existsById(userId)) {
            HashMap<String, Object> userData = new HashMap<>();
            Optional<User> user = userRepo.findById(userId);
            userData.put("user", user);
            Optional<Status> status = statusRepo.findById(user.get().getStatusId());
            userData.put("status", status);
            Optional<UserType> userType = userTypeRepo.findById(user.get().getUsertypeId());
            userData.put("usertype", userType);
            Optional<MemberType> memberType = memberTypeRepo.findById(user.get().getMembertypeId());
            userData.put("membertype", memberType);
            response.setData(userData);
            response.setDescription("User found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", userData.toString());
        } else {
            response.setDescription("User not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public void CreateUserVerifcation(long Id, String email, String emailtype, String name) {
        String verificationCode = "WM-" + utilityManager.randomAlphaNumeric(8);
        UserVerification userVerification = new UserVerification();
        userVerification.setCode(verificationCode);
        userVerification.setEmail(email);
        userVerification.setUserId(Id);
        userVerification.setDate(new Date());
        userVerificationRepo.save(userVerification);
        emailService.sendEmail(email, name, verificationCode, emailtype);
        logger.info("User verification code created" + ": {}", userVerification.toString());
    }

    public BaseResponse getUsersByType(long UserTypeID) {
        BaseResponse response = new BaseResponse();
        if (userTypeRepo.existsById(UserTypeID)) {
            UserType userType = userTypeRepo.findById(UserTypeID).get();
            ArrayList<Object> users = new ArrayList<>();
            if (userType.getName().equals("Member") || userType.getName().equals("Dependant")) {//Member,Dependant
                List<User> user = userRepo.findByUsertypeId(userType.getId());
                if (!user.isEmpty()) {
                    users.addAll(user);
                }

            } else if (userType.getName().equals("Guest") || userType.getName().equals("Visitor")) {//Guest,Visitor
                List<GuestVisitor> gvisitors = guestVisitorRepo.findByUsertypeId(UserTypeID);
                if (!gvisitors.isEmpty()) {
                    users.addAll(gvisitors);
                }

            }
            if (!users.isEmpty()) {
                response.setData(users);
                response.setDescription("Users found successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription());
            } else {
                response.setDescription("No users found.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No users found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse blockUser(long userId) {
        BaseResponse response = new BaseResponse();
        if (userRepo.existsById(userId)) {
            Optional<User> user = userRepo.findById(userId);
            User currentUser = user.get();
            currentUser.setBlocked_status(1);
            User updatedUser = userRepo.save(currentUser);
            response.setData(updatedUser);
            response.setDescription("User blocked successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedUser.toString());
        } else {
            response.setDescription("User not found, please enter a valid userId.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse updateUserName(long userId, String firstname, String lastname) {
        BaseResponse response = new BaseResponse();
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            User realUser = user.get();
            realUser.setFirst_name(firstname);
            realUser.setLast_name(lastname);
            User updatedUser = userRepo.save(realUser);
            response.setData(updatedUser);
            response.setDescription("User name updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedUser.toString());
        } else {
            response.setDescription("User does not exist, please check again");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse setResetPassword(String verificationCode, String newPassword) {
        BaseResponse response = new BaseResponse();
        Optional<UserVerification> passwordReset = userVerificationRepo.findByCode(verificationCode);
        if (passwordReset.isPresent()) {
            if (userRepo.existsById(passwordReset.get().getUserId())) {
                Optional<User> user = userRepo.findById(passwordReset.get().getUserId());
                user.get().setPassword(passwordEncoder.encode(newPassword));
                User updatedUser = userRepo.save(user.get());
                response.setData(updatedUser);
                response.setDescription("New password set successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", updatedUser.toString());
            } else {
                response.setDescription("Account does not exist, please check again");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }

        } else {
            response.setDescription("Wrong verification code, please, check and try again.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }


        return response;
    }

    public BaseResponse setNewPassword(long userId, String newPassword, String oldPassword) {
        BaseResponse response = new BaseResponse();
        if (userRepo.existsById(userId)) {
            Optional<User> user = userRepo.findById(userId);
            if (passwordEncoder.matches(oldPassword, user.get().getPassword())) {
                user.get().setPassword(passwordEncoder.encode(newPassword));
                User updatedUser = userRepo.save(user.get());
                response.setData(updatedUser);
                response.setDescription("New password set successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", updatedUser.toString());
            } else {
                response.setDescription("Wrong password, Please enter the current password correctly");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Account does not exist, please check again");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse unblockUser(long userId) {
        BaseResponse response = new BaseResponse();
        if (userRepo.existsById(userId)) {
            Optional<User> user = userRepo.findById(userId);
            User currentUser = user.get();
            currentUser.setBlocked_status(0);
            User updatedUser = userRepo.save(currentUser);
            response.setData(updatedUser);
            response.setDescription("User unblocked successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedUser.toString());
        } else {
            response.setDescription("User not found, please enter a valid userId");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse confirmEmailAndActivateUser(String recoveryCode) {
        BaseResponse response = new BaseResponse();
        Optional<UserVerification> passwordReset = userVerificationRepo.findByCode(recoveryCode);
        if (passwordReset.isPresent()) {
            String email = passwordReset.get().getEmail();
            long userid = passwordReset.get().getUserId();
            Optional<User> user = userRepo.findById(userid);
            if (user.get().getEmail().equals(email)) {
                response = activateUser(user.get().getId());
            } else {
                response.setDescription("User not found, please enter a valid userId");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Invalid Verification Code.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse activateUser(long userId) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        Optional<Status> status = statusRepo.findByName("Activated");
        Optional<User> user = userRepo.findById(userId);
        if (status.isPresent()) {
            user.get().setStatusId(status.get().getId());
            User updatedUser = userRepo.save(user.get());
            response.setData(updatedUser);
            response.setDescription("user activated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedUser.toString());
        } else {
            response.setDescription("Activated status not found.");
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse sendUserPasswordResetEmail(String email) {
        BaseResponse response = new BaseResponse();
        Optional<User> user = userRepo.findByEmailOrPhoneNumber(email, email);
        if (user.isPresent()) {
            if (user.get().getEmail().equalsIgnoreCase(email)) {
                String name = user.get().getLast_name() + " " + user.get().getFirst_name();
                CreateUserVerifcation(user.get().getId(), user.get().getEmail(), "PasswordReset", name);//Send email
                response.setDescription("Password reset email sent successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", user.toString());
            } else {
                response.setDescription("Invalid email, please enter correct email");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Account does not exist, please check again");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse searchUsers(String searchText) {
        BaseResponse response = new BaseResponse();
        List<User> users = userRepo.searchForUsers(searchText, searchText, searchText, searchText, searchText,
                searchText);
        if (!users.isEmpty()) {
            response.setData(users);
            response.setDescription("Users found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("User not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getUserAllPermissions(Long userId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Permission> userPermissions = new ArrayList<>();

        if (userRepo.existsById(userId)) {
            User user = userRepo.findById(userId).get();
            UserType userType = userTypeRepo.findById(user.getUsertypeId()).get();
            List<UserTypePermissionGroup> UserTypePermissionGroups = userTypePermissionGroupRepo.findByUsertypeId(userType.getId());
            if (!UserTypePermissionGroups.isEmpty()) {
                for (UserTypePermissionGroup usertypepermgroup : UserTypePermissionGroups) {
                    List<PermissionGroupPermission> permissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(usertypepermgroup.getPermissiongroupId());
                    for (PermissionGroupPermission permissionGroupPermission : permissionGroupPermissions) {
                        Optional<Permission> permission = permissionRepo.findById(permissionGroupPermission.getPermissionId());
                        userPermissions.add(permission.get());
                    }
                }

                List<UserSpecialPermissionGroup> UserSpecialPermissionGroups = userSpecialPermissionGroupRepo.findByUserId(userId);
                if (!UserSpecialPermissionGroups.isEmpty()) {
                    for (UserSpecialPermissionGroup userspcialpermissiongroup : UserSpecialPermissionGroups) {
                        List<PermissionGroupPermission> SpepermissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(userspcialpermissiongroup.getPermissiongroupId());
                        String actionvalue = userspcialpermissiongroup.getAction();
                        for (PermissionGroupPermission specialPermGroupPerms : SpepermissionGroupPermissions) {
                            Optional<Permission> permission = permissionRepo.findById(specialPermGroupPerms.getPermissionId());
                            if (actionvalue.equals("Add")) {
                                userPermissions.add(permission.get());
                            } else if (actionvalue.equals("Restrict")) {
                                userPermissions.remove(permission.get());
                            }
                        }
                    }
                }
                if (!userPermissions.isEmpty()) {
                    response.setData(userPermissions);
                    response.setDescription("User Permissions found successfully.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription());
                } else {
                    response.setDescription("No results found.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());

                }
            } else {
                response.setDescription("No results found.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        }

        return response;
    }

    public BaseResponse getAllUserRolePermissions(Long userId) {
        BaseResponse response = new BaseResponse();

        Set<Permission> permissions = getUserRolePermissions(userId);
        if (!permissions.isEmpty()) {
            response.setData(permissions);
            response.setDescription("User role Permissions found successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No results found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public Set<Permission> getUserRolePermissions(Long userId){
        Set<Permission> permissions = new HashSet<>();

        Status status = statusRepo.findByName("Active").orElseThrow(() -> new EntityNotFoundException("active status not found"));
        Set<Role> roles = organizationService.getUserOrganizationRoles(userId, status.getId());
        if(!roles.isEmpty()){
            for (Role role: roles){
                Set<Permission> rolePermissions = roleService.getAllRolePermissions(role.getId());
                if(!rolePermissions.isEmpty()) permissions.addAll(rolePermissions);
            }
        }

        return permissions;
    }

    public String GetUserName(User user) {
        String username = user.getLast_name() + " " + user.getFirst_name();
        return username;
    }


}
