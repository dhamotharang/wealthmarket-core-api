package com.wm.core.service.user;

import com.google.i18n.phonenumbers.NumberParseException;
import com.wm.core.model.agency.Agencies;
import com.wm.core.model.business.Businesses;
import com.wm.core.model.business.Producers;
import com.wm.core.model.permission.OrganizationSpecialPermissionGroup;
import com.wm.core.model.permission.Permission;
import com.wm.core.model.permission.PermissionGroupPermission;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.*;
import com.wm.core.repository.agency.AgenciesRepository;
import com.wm.core.repository.business.BusinessesRepository;
import com.wm.core.repository.business.ProducersRepository;
import com.wm.core.repository.permission.OrganizationSpecialPermissionGroupRepository;
import com.wm.core.repository.permission.PermissionGroupPermissionsRepository;
import com.wm.core.repository.permission.PermissionGroupRepository;
import com.wm.core.repository.permission.PermissionRepository;
import com.wm.core.repository.user.*;
import com.wm.core.service.agency.AgenciesService;
import com.wm.core.service.business.BusinessesService;
import com.wm.core.service.business.ProducersService;
import com.wm.core.utility.UtilityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class OrganizationService {
    Logger logger = LoggerFactory.getLogger(OrganizationService.class.getName());

    @Autowired
    OrganizationRepository organizationRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    RoleTypesRepository roleTypesRepo;

    @Autowired
    MemberOrganizationsRepository memberOrganizationsRepo;

    @Autowired
    GroupTypeRepository groupTypeRepo;

    @Autowired
    GroupRepository groupRepo;

    @Autowired
    SubGroupRepository subGroupRepo;

    @Autowired
    MemberTypeRepository memberTypeRepo;

    @Autowired
    SubMemberTypeRepository subMemberTypeRepo;

    @Autowired
    UserService userService;

    @Autowired
    PermissionGroupPermissionsRepository permissionGroupPermissionsRepo;

    @Autowired
    OrganizationSpecialPermissionGroupRepository organizationSpecialPermissionGroupRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    BusinessesService businessesService;

    @Autowired
    BusinessesRepository businessesRepo;

    @Autowired
    AgenciesService agenciesService;

    @Autowired
    AgenciesRepository agenciesRepo;

    @Autowired
    ProducersService producersService;

    @Autowired
    ProducersRepository producersRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    StatusRepository statusRepo;

    @Autowired
    UserVerificationRepository userVerificationRepo;


    UtilityManager utilityManager = new UtilityManager();

    @Autowired
    PermissionRepository permissionRepo;

    @Autowired
    GroupService groupService;

    @Autowired
    GroupTypeService groupTypeService;

    @Autowired
    SubGroupService subGroupService;

    public BaseResponse createNewOrganization(long userId, NewOrganization newOrganization) throws NumberParseException {
        BaseResponse response = new BaseResponse();
        if (groupTypeRepo.existsById(newOrganization.getGroup_typeId())) {
            Optional<GroupType> groupType = groupTypeRepo.findById(newOrganization.getGroup_typeId());
            if (groupRepo.existsById(newOrganization.getGroupId())) {
                Optional<Group> group = groupRepo.findById(newOrganization.getGroupId());

                if (memberTypeRepo.existsById(newOrganization.getMember_typeId())) {
                    Optional<MemberType> memberType = memberTypeRepo.findById(newOrganization.getMember_typeId());

                    if (subMemberTypeRepo.existsById(newOrganization.getSub_member_typeId())) {
                        Optional<SubMemberType> subMemberType = subMemberTypeRepo.findById(newOrganization.getSub_member_typeId());

                        if (utilityManager.isValidEmail(newOrganization.getEmail())) {
                            if (!userService.checkEmailAddressOrPhoneNumberExist(newOrganization.getEmail(), newOrganization.getPhone())) {
                                if (utilityManager.isValidPhone(newOrganization.getPhone(), "NG")) {
                                    Organization organization = new Organization();
                                    Optional<User> loggedInUser = userRepo.findById(userId);//Logged In User
                                    if (newOrganization.getOwner_userId() == 0l) {//myself
                                        organization.setOwneruserId(loggedInUser.get().getId());
                                        organization.setCreatoruserId(loggedInUser.get().getId());
                                    } else {//User A creating for User B
                                        if (userRepo.existsById(newOrganization.getOwner_userId())) {
                                            Optional<User> createdForUser = userRepo.findById(newOrganization.getOwner_userId());
                                            organization.setCreatoruserId(loggedInUser.get().getId());
                                            organization.setOwneruserId(createdForUser.get().getId());
                                        } else {
                                            response.setDescription("You did not specify the user your are creating the organization for.");
                                            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                                            logger.error(response.getDescription());
                                            return response;
                                        }
                                    }
                                    String unique_id = "WM-" + utilityManager.randomAlphaNumeric(16);
                                    organization.setCreated_date(new Date());
                                    organization.setGrouptypeId(groupType.get().getId());
                                    organization.setGroupId(group.get().getId());
                                    organization.setUniquiId(unique_id);
                                    organization.setMembertypeId(memberType.get().getId());
                                    organization.setSubmembertypeId(subMemberType.get().getId());
                                    organization.setEmail(newOrganization.getEmail());
                                    organization.setName(newOrganization.getName());
                                    organization.setPhone(newOrganization.getPhone());
                                    if (newOrganization.getSub_groupId() != -1) {
                                        Optional<SubGroup> subGroup = subGroupRepo.findById(newOrganization.getSub_groupId());
                                        organization.setSubgroupId(subGroup.get().getId());
                                    }
                                    Organization created_organization = organizationRepo.save(organization);
                                    if (created_organization != null) {
                                        response = createOrganization(created_organization, newOrganization);
                                        userService.CreateUserVerifcation(created_organization.getId(), created_organization.getEmail(), "ConfirmEmail", created_organization.getName());
                                    } else {
                                        response.setDescription("Organization(Agency/Business/Producer was not created.");
                                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                                        logger.error(response.getDescription());
                                        return response;
                                    }
                                } else {
                                    response.setDescription("Invalid phone number, please enter correct number.");
                                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                                    logger.error(response.getDescription());
                                }
                            } else {
                                response.setDescription("The email provided has been used by registered User");
                                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                                logger.error(response.getDescription());
                            }
                        } else {
                            response.setDescription("Please, enter a valid email.");
                            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                            logger.error(response.getDescription());
                        }
                    } else {
                        response.setDescription("Please, select a member sub type.");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                    }
                } else {
                    response.setDescription("Please, select a member type.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }

            } else {
                response.setDescription("Please, select a group.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Please, select a group type");//this is selected on default based on the app
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse createOrganization(Organization created_organization, NewOrganization newOrganization) {
        BaseResponse response = new BaseResponse();
        if (newOrganization.getGroup_typeId() == 1) {//Agencies
            response = agenciesService.createNewAgency(created_organization, newOrganization);
        } else if (newOrganization.getGroup_typeId() == 2) {//Businesses
            response = businessesService.createNewBusiness(created_organization, newOrganization);
        } else if (newOrganization.getGroup_typeId() == 3) {//Producers
            response = producersService.createNewProducer(created_organization, newOrganization);
        }
        return response;
    }

    public BaseResponse createMemberOrganization(User user, Role role, Organization organization) {
        BaseResponse response = new BaseResponse();
        MemberOrganizations memberOrganizations = new MemberOrganizations();
        memberOrganizations.setDate_added(new Date());
        memberOrganizations.setOrganizationId(organization.getId());
        memberOrganizations.setUserId(user.getId());
        memberOrganizations.setRoleId(role.getId());
        Optional<Status> status = statusRepo.findByName("Active");
        if (status.isPresent()) {
            memberOrganizations.setStatusId(status.get().getId());
            MemberOrganizations created_memberOrganizations = memberOrganizationsRepo.save(memberOrganizations);
            if (created_memberOrganizations != null) {
                response.setData(created_memberOrganizations);
                response.setDescription("Member Organization created successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", created_memberOrganizations.toString());
            } else {
                response.setDescription("Member Organization was not created.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Member Organization was not created, could not assign role to " + user.getLast_name());
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse addOrganizationStaff(long organizationId, long userId, long roleId) {
        BaseResponse response = new BaseResponse();
        if (!userRepo.existsById(userId)) {
            response.setDescription("Please, select a user to add to the organization.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }
        Optional<User> user = userRepo.findById(userId);

        if (!organizationRepo.existsById(organizationId)) {
            response.setDescription("Please, select an Organization.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }
        Optional<Organization> organization = organizationRepo.findById(organizationId);


        Optional<MemberOrganizations> memberOrganizations = memberOrganizationsRepo.findByOrganizationIdAndUserId(organizationId, userId);
        if (memberOrganizations.isPresent()) {
            Optional<Status> oldstatus = statusRepo.findById(memberOrganizations.get().getStatusId());
            if (oldstatus.get().getName().equals("Removed")) {
                Optional<Status> newstatus = statusRepo.findByName("Active");
                if (newstatus.isPresent()) {
                    response = changeOrganizationStaffStatus(organizationId, userId, newstatus.get().getId());
                    if (response.getStatusCode() == 200) {
                        response.setData(response.getData());
                        response.setDescription(userService.GetUserName(user.get()) + ", has been added successfully");
                        response.setStatusCode(HttpServletResponse.SC_OK);
                        logger.info(response.getDescription() + ": {}", response.getData().toString());
                        return response;
                    } else {
                        response.setDescription(userService.GetUserName(user.get()) + ", was not added to the Organization.");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                        return response;
                    }
                } else {
                    response.setDescription("Member Organization was not created, could not add " + userService.GetUserName(user.get()));
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                    return response;
                }
            } else {
                response.setDescription(userService.GetUserName(user.get()) + ", already exist in the organization");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
                return response;
            }
        }

        if (!roleRepo.existsById(roleId)) {
            response.setDescription("Please, select a role for " + user.get().getLast_name());
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            return response;
        }
        Optional<Role> role = roleRepo.findById(roleId);

        response = createMemberOrganization(user.get(), role.get(), organization.get());
        if (response.getStatusCode() == 200) {
            response.setDescription(user.get().getLast_name() + ", has been added successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            response.setData(response.getData());
            logger.info(response.getDescription() + ": {}", user.toString());
        } else {
            response.setDescription(user.get().getLast_name() + ", was not added to the Organization.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse removeOrganizationStaff(long organizationId, long userId) {
        BaseResponse response = new BaseResponse();
        if (userRepo.existsById(userId)) {
            if (organizationRepo.existsById(organizationId)) {
                Optional<Status> status = statusRepo.findByName("Removed");
                if (!status.isPresent()) {
                    response.setDescription("Removed Status not found, please create Removed status first.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                    return response;
                }


                Optional<MemberOrganizations> staff = memberOrganizationsRepo.findByOrganizationIdAndUserId(organizationId, userId);
                if (!staff.isPresent()) {
                    response.setDescription("No organization was found for the user.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                    return response;
                }

                Role role = roleRepo.findById(staff.get().getRoleId()).get();
                if (role.getName().equals("Owner")) {
                    response.setDescription("Hey, you cannot remove the owner of this Organization.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                    return response;
                }


                staff.get().setStatusId(status.get().getId());
                MemberOrganizations removed_staff = memberOrganizationsRepo.save(staff.get());
                response.setDescription("Staff removed successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                response.setData(removed_staff);
                logger.info(response.getDescription() + ": {}", staff.get().toString());
            } else {
                response.setDescription("Please, select an Organisation.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Please, select a user.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse changeOrganizationStaffStatus(long organizationId, long userId, long statusId) {
        BaseResponse response = new BaseResponse();
        Optional<Organization> organization = organizationRepo.findById(organizationId);
        if (!organization.isPresent()) {
            response.setDescription("Please, select an organization.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }
        Optional<User> user = userRepo.findById(userId);
        if (!user.isPresent()) {
            response.setDescription("Please, select a user.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }

        Optional<Status> status = statusRepo.findById(statusId);
        if (!status.isPresent()) {
            response.setDescription("Status does not exist.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }

        Optional<MemberOrganizations> staff = memberOrganizationsRepo.findByOrganizationIdAndUserId(organizationId, userId);
        if (!staff.isPresent()) {
            response.setDescription("The staff does not exist in this Organisation.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }

        Role role = roleRepo.findById(staff.get().getRoleId()).get();
        if (role.getName().equals("Owner")) {
            response.setDescription("Hey, you cannot change the status of " + userService.GetUserName(user.get()));
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }

        staff.get().setStatusId(status.get().getId());
        MemberOrganizations removed_staff = memberOrganizationsRepo.save(staff.get());
        response.setDescription("Staff status has been changed successfully.");
        response.setStatusCode(HttpServletResponse.SC_OK);
        response.setData(removed_staff);
        logger.info(response.getDescription() + ": {}", staff.get().toString());
        return response;
    }

    public BaseResponse changeOrganizationStaffRole(long organizationId, long userId, long roleId) {
        BaseResponse response = new BaseResponse();

        Optional<Organization> organization = organizationRepo.findById(organizationId);
        if (!organization.isPresent()) {
            response.setDescription("Please, select an organization.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }

        Optional<User> user = userRepo.findById(userId);
        if (!user.isPresent()) {
            response.setDescription("Please, select a user.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }

        Optional<Role> role = roleRepo.findById(roleId);
        if (!role.isPresent()) {
            response.setDescription("Please, select a new role to add to the user.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }

        Optional<MemberOrganizations> staff = memberOrganizationsRepo.findByOrganizationIdAndUserId(organization.get().getId(), user.get().getId());
        if (!staff.isPresent()) {
            response.setDescription("Selected User does not belong to the Organisation ");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }

        if (role.get().getName().equals("Owner")) {
            response.setDescription("Hey, you cannot change the role of " + userService.GetUserName(user.get()));
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }


        staff.get().setRoleId(role.get().getId());
        MemberOrganizations updated_staff = memberOrganizationsRepo.save(staff.get());
        response.setDescription("Staff role changed successfully");
        response.setStatusCode(HttpServletResponse.SC_OK);
        response.setData(updated_staff);
        logger.info(response.getDescription() + ": {}", staff.get().toString());
        return response;
    }

    public BaseResponse getOrganizationStaff(long organizationId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> stafflist = new ArrayList<>();
        if (organizationRepo.existsById(organizationId)) {
            List<MemberOrganizations> staff = memberOrganizationsRepo.findByOrganizationId(organizationId);
            for (MemberOrganizations memb : staff) {
                HashMap<String, Object> staffdet = new HashMap<>();
                staffdet.put("staff", memb);
                Optional<User> user = userRepo.findById(memb.getUserId());
                staffdet.put("user", user);
                Optional<Role> role = roleRepo.findById(memb.getRoleId());
                staffdet.put("role", role);
                Optional<Status> status = statusRepo.findById(memb.getStatusId());
                staffdet.put("status", status);
                if (!role.get().getName().equals("Member")) {
                    if (!status.get().getName().equals("Removed")) {
                        stafflist.add(staffdet);
                    }
                }
            }
            if (!stafflist.isEmpty()) {
                response.setDescription("Organization staff gotten successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                response.setData(stafflist);
                logger.info(response.getDescription());
            } else {
                response.setDescription("No staff found.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No organizations found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getOrganizationRoles(long organizationId) {
        BaseResponse response = new BaseResponse();
        if (organizationRepo.existsById(organizationId)) {
            Optional<RoleTypes> roleTypes = roleTypesRepo.findByName("System Defined");
            if (roleTypes.isPresent()) {
                List<Role> roles = roleRepo.findByRoletypeId(roleTypes.get().getId());
                if (!roles.isEmpty()) {
                    roles.addAll(roleRepo.findByOrganizationId(organizationId));
                    response.setDescription("Organization roles gotten successfully.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    response.setData(roles);
                    logger.info(response.getDescription() + ": {}", roles.toString());
                } else {
                    response.setDescription("No Role found for the specified organization.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            } else {
                response.setDescription("No Role found for the specified organization.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No Role found for the specified organization.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }


        return response;
    }

    public BaseResponse getAllOrganizations() {
        BaseResponse response = new BaseResponse();
        List<Organization> organizations = organizationRepo.findAll();
        if (!organizations.isEmpty()) {
            response.setDescription("Organisations found.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            response.setData(organizations);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No Organisations found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getOrganizationByID(Long id) {
        BaseResponse response = new BaseResponse();
        Optional<Organization> organizations = organizationRepo.findById(id);
        if (organizations.isPresent()) {
            response.setStatusCode(HttpServletResponse.SC_OK);
            response.setDescription("Organisation found.");
            response.setData(organizations);
            logger.info(response.getDescription() + ": {}", organizations.get().toString());
        } else {
            response.setDescription("No Organisations found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse addOrganizationMember(long organizationId, long userId) {
        BaseResponse response = new BaseResponse();

        Optional<User> user = userRepo.findById(userId);
        if (!user.isPresent()) {
            response.setDescription("Please, select a user to add.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }
        Optional<Organization> organization = organizationRepo.findById(organizationId);
        if (!organization.isPresent()) {
            response.setDescription("Please, select an organization.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }

        Optional<MemberOrganizations> memberOrganizations = memberOrganizationsRepo.findByOrganizationIdAndUserId(organizationId, userId);
        if (memberOrganizations.isPresent()) {
            Optional<Status> oldstatus = statusRepo.findById(memberOrganizations.get().getStatusId());
            if (oldstatus.get().getName().equals("Removed")) {
                Optional<Status> newstatus = statusRepo.findByName("Active");
                if (newstatus.isPresent()) {
                    response = changeOrganizationMemberStatus(organizationId, userId, newstatus.get().getId());
                    if (response.getStatusCode() == 200) {
                        response.setData(response.getData());
                        response.setDescription(userService.GetUserName(user.get()) + ", added successfully");
                        response.setStatusCode(HttpServletResponse.SC_OK);
                        logger.info(response.getDescription() + ": {}", response.getData().toString());
                        return response;
                    } else {
                        response.setDescription(userService.GetUserName(user.get()) + ", was not added to the Organization.");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                        return response;
                    }
                } else {
                    response.setDescription("Member Organization was not created, could not add " + userService.GetUserName(user.get()));
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                    return response;
                }
            } else {
                response.setDescription(userService.GetUserName(user.get()) + ", already exist in the organization");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
                return response;
            }

        }


        Optional<Role> role = roleRepo.findByName("Member");
        if (!role.isPresent()) {
            response.setDescription("Please, create Member role for the user.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }
        response = createMemberOrganization(user.get(), role.get(), organization.get());
        response.setDescription("Member added successfully.");
        response.setStatusCode(HttpServletResponse.SC_OK);
        logger.info(response.getDescription() + ": {}", response.getData().toString());
        return response;
    }

    public BaseResponse removeOrganizationMember(long organizationId, long userId) {
        BaseResponse response = new BaseResponse();
        Optional<Organization> organization = organizationRepo.findById(organizationId);
        if (!organization.isPresent()) {
            response.setDescription("Please, select an organization.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }
        Optional<User> user = userRepo.findById(userId);
        if (!user.isPresent()) {
            response.setDescription("Please, select a user to remove.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }
        Optional<Role> role = roleRepo.findByName("Member");
        if (!role.isPresent()) {
            response.setDescription("Please, something went wrong with the role.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }
        Status status = statusRepo.findByName("Removed").get();
        if (!role.isPresent()) {
            response.setDescription("Remove Status does not exist.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }

        Optional<MemberOrganizations> member = memberOrganizationsRepo.findByOrganizationIdAndUserId(organization.get().getId(), user.get().getId());
        if (!member.isPresent()) {
            response.setDescription("The Member does not exist in this organization.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        } else {
            Optional<Status> status1 = statusRepo.findById(member.get().getStatusId());
            if (status1.get().getName().equals("Removed")) {
                response.setDescription("The Member does not exist in this organization.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
                return response;
            }
        }

        Role memberrole = roleRepo.findById(member.get().getRoleId()).get();
        if (memberrole.getName().equals("Owner")) {
            response.setDescription("Hey, you cannot remove the Owner of this Organization.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }
        member.get().setStatusId(status.getId());
        MemberOrganizations removed_staff = memberOrganizationsRepo.save(member.get());
        response.setDescription("Member removed successfully.");
        response.setStatusCode(HttpServletResponse.SC_OK);
        response.setData(removed_staff);
        logger.info(response.getDescription() + ": {}", removed_staff.toString());
        return response;
    }

    public BaseResponse changeOrganizationMemberStatus(long organizationId, long userId, long statusId) {
        BaseResponse response = new BaseResponse();

        Optional<Organization> organization = organizationRepo.findById(organizationId);
        if (!organization.isPresent()) {
            response.setDescription("Please, select an organization.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            return response;
        }
        Optional<User> user = userRepo.findById(userId);
        if (!user.isPresent()) {
            response.setDescription("Please, select a user to add");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            return response;
        }
        Optional<Status> status = statusRepo.findById(statusId);
        if (!status.isPresent()) {
            response.setDescription("Please, select a status to add to the user");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            return response;
        }

        Optional<MemberOrganizations> staff = memberOrganizationsRepo.findByOrganizationIdAndUserId(organization.get().getId(), user.get().getId());
        if (!staff.isPresent()) {
            response.setDescription("No Member with the id found for the specified Organisation");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            return response;
        }

        Role role = roleRepo.findById(staff.get().getRoleId()).get();
        if (role.getName().equals("Owner")) {
            response.setDescription("Hey, you cannot change the status of " + userService.GetUserName(user.get()));
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            return response;
        }

        staff.get().setStatusId(status.get().getId());
        MemberOrganizations removed_staff = memberOrganizationsRepo.save(staff.get());
        response.setDescription("Member status has been changed successfully.");
        response.setStatusCode(HttpServletResponse.SC_OK);
        response.setData(removed_staff);
        return response;
    }

    public BaseResponse getOrganizationMembers(long organizationId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> memberlist = new ArrayList<>();
        if (organizationRepo.existsById(organizationId)) {
            Optional<Status> status = statusRepo.findByName("Active");
            if (status.isPresent()) {
                List<MemberOrganizations> member = memberOrganizationsRepo.findByOrganizationIdAndStatusId(organizationId, status.get().getId());
                for (MemberOrganizations memb : member) {
                    HashMap<String, Object> memberdet = new HashMap<>();
                    memberdet.put("member", memb);
                    Optional<User> user = userRepo.findById(memb.getUserId());
                    memberdet.put("user", user);
                    Optional<Role> role = roleRepo.findById(memb.getRoleId());
                    memberdet.put("role", role);
                    if (role.get().getName().equals("Member")) {
                        memberlist.add(memberdet);
                    }
                }
                if (!memberlist.isEmpty()) {
                    response.setDescription("Organization Members gotten successfully.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    response.setData(memberlist);
                    logger.info(response.getDescription());
                } else {
                    response.setDescription("No Members found for the specified organization.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            } else {
                response.setDescription("No organizations found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No organizations found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getOrganizationsByGroupTypeID(Long id) {
        BaseResponse response = new BaseResponse();
        if (groupTypeRepo.existsById(id)) {
            List<Organization> organizations = organizationRepo.findByGrouptypeId(id);
            if (!organizations.isEmpty()) {
                response.setStatusCode(HttpServletResponse.SC_OK);
                response.setDescription("Organisations found");
                response.setData(organizations);
                logger.info(response.getDescription());
            } else {
                response.setDescription("No Organisations found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No Organisations found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getOrganizationsByGroupID(Long id) {
        BaseResponse response = new BaseResponse();
        if (groupRepo.existsById(id)) {
            List<Organization> organizations = organizationRepo.findByGroupId(id);
            if (!organizations.isEmpty()) {
                response.setStatusCode(HttpServletResponse.SC_OK);
                response.setDescription("Organisations found");
                response.setData(organizations);
                logger.info(response.getDescription());
            } else {
                response.setDescription("No Organisations found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No Organisations found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getOrganizationsBySubGroupID(Long id) {
        BaseResponse response = new BaseResponse();
        if (subGroupRepo.existsById(id)) {
            List<Organization> organizations = organizationRepo.findBySubgroupId(id);
            if (!organizations.isEmpty()) {
                response.setStatusCode(HttpServletResponse.SC_OK);
                response.setDescription("Organisations found");
                response.setData(organizations);
                logger.info(response.getDescription());
            } else {
                response.setDescription("No Organisations found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No Organisations found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getOrganizationAllPermissions(Long organizationId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> orgPermissions = new ArrayList<>();
        if (organizationRepo.existsById(organizationId)) {
            List<OrganizationSpecialPermissionGroup> organizationSpecialPermissionGroups = organizationSpecialPermissionGroupRepo.findByOrganizationId(organizationId);
            if (!organizationSpecialPermissionGroups.isEmpty()) {
                for (OrganizationSpecialPermissionGroup organizationSpecialPermissionGroup : organizationSpecialPermissionGroups) {
                    String actionvalue = organizationSpecialPermissionGroup.getAction();
                    List<PermissionGroupPermission> SpepermissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(organizationSpecialPermissionGroup.getPermissiongroupId());

                    for (PermissionGroupPermission specialPermGroupPerms : SpepermissionGroupPermissions) {
                        Optional<Permission> permission = permissionRepo.findById(specialPermGroupPerms.getPermissionId());
                        if (actionvalue.equals("Add")) {
                            orgPermissions.add(permission);
                        } else if (actionvalue.equals("Restrict")) {
                            orgPermissions.remove(permission);
                        }
                    }
                }
            }

            Optional<Organization> organization = organizationRepo.findById(organizationId);
            ArrayList<Object> perms = new ArrayList<>();
            response = groupTypeService.getGroupTypeAllPermissions(organization.get().getGrouptypeId());
            if (response.getStatusCode() == HttpServletResponse.SC_OK) {
                perms = (ArrayList<Object>) response.getData();
                for (Object perm : perms) {
                    orgPermissions.add(perm);
                }
            }

            response = groupService.getGroupAllPermissions(organization.get().getGroupId());
            if (response.getStatusCode() == HttpServletResponse.SC_OK) {
                perms = (ArrayList<Object>) response.getData();
                for (Object perm : perms) {
                    orgPermissions.add(perm);
                }
            }

            if (organization.get().getSubgroupId() != 0) {
                response = subGroupService.getSubGroupAllPermissions(organization.get().getSubgroupId());
                if (response.getStatusCode() == HttpServletResponse.SC_OK) {
                    perms = (ArrayList<Object>) response.getData();
                    for (Object perm : perms) {
                        orgPermissions.add(perm);
                    }
                }
            }
            if (!orgPermissions.isEmpty()) {
                response.setData(orgPermissions);
                response.setDescription("Organization Permissions found successfully.");
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

        return response;
    }

    public BaseResponse getUserOrganizations(long userId, long grouptypeId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> userOrganizations = new ArrayList<>();
        if (userRepo.existsById(userId)) {
            if (groupTypeRepo.existsById(grouptypeId)) {
                Optional<Status> status = statusRepo.findByName("Active");
                if (status.isPresent()) {
                    List<MemberOrganizations> memberOrganizations = memberOrganizationsRepo.findByUserIdAndStatusId(userId, status.get().getId());
                    if (!memberOrganizations.isEmpty()) {
                        for (MemberOrganizations memberOrganization : memberOrganizations) {
                            Optional<Organization> organization = organizationRepo.findById(memberOrganization.getOrganizationId());
                            if (organization.isPresent()) {
                                if (organization.get().getGrouptypeId() == grouptypeId) {
                                    HashMap<String, Object> orgDetails = new HashMap<>();
                                    ArrayList<Object> orgPermissions = (ArrayList<Object>) getOrganizationAllPermissions(organization.get().getId()).getData();
                                    if (orgPermissions != null) {
                                        orgDetails.put("permcount", orgPermissions.size());
                                    } else {
                                        orgDetails.put("permcount", 0);
                                    }

                                    ArrayList<Object> orgMembers = (ArrayList<Object>) getOrganizationMembers(organization.get().getId()).getData();
                                    if (orgMembers != null) {
                                        orgDetails.put("membercount", orgMembers.size());
                                    } else {
                                        orgDetails.put("membercount", 0);
                                    }


                                    ArrayList<Object> orgRoles = (ArrayList<Object>) getOrganizationRoles(organization.get().getId()).getData();
                                    if (orgRoles != null) {
                                        orgDetails.put("rolecount", orgRoles.size());
                                    } else {
                                        orgDetails.put("rolecount", 0);
                                    }

                                    ArrayList<Object> orgStaff = (ArrayList<Object>) getOrganizationStaff(organization.get().getId()).getData();
                                    if (orgStaff != null) {
                                        orgDetails.put("staffcount", orgStaff.size());
                                    } else {
                                        orgDetails.put("staffcount", 0);
                                    }
                                    orgDetails.put("organization", organization);
                                    userOrganizations.add(orgDetails);
                                }
                            }
                        }
                        if (!userOrganizations.isEmpty()) {
                            response.setData(userOrganizations);
                            response.setStatusCode(HttpServletResponse.SC_OK);
                            response.setDescription("Organizations found");
                            logger.info(response.getDescription());
                        } else {
                            response.setDescription("No Organizations found");
                            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                            logger.error(response.getDescription());
                        }
                    } else {
                        response.setDescription("No Organizations found");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                    }
                } else {
                    response.setDescription("No Organizations found");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }

            } else {
                response.setDescription("No Organizations found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No Organizations found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse RemoveSubAdmin(long userId) {
        BaseResponse response = new BaseResponse();
        if (userRepo.existsById(userId)) {
            User user = userRepo.findById(userId).get();
            Optional<MemberOrganizations> staff = memberOrganizationsRepo.findByUserId(userId);

            if (memberOrganizationsRepo.existsById(userId)) {
                Role role = roleRepo.findById(staff.get().getRoleId()).get();
                if (role.getName().equals("SubAdmin")) {
                    memberOrganizationsRepo.delete(staff.get());
                    response.setDescription("staff removed successfully");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription());
                } else {
                    response.setDescription("Hey, you cannot remove the owner of this organization.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                    return response;
                }
            } else {
                response.setDescription(user.getFirst_name() + " has not been made a SubAdmin.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
                return response;
            }
        } else {
            response.setDescription("User not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse MakeSubAdmin(long userId, long roleId) {
        BaseResponse response = new BaseResponse();
        Optional<User> user = userRepo.findById(userId);
        if (!user.isPresent()) {
            response.setDescription("Please, select a user to Make SubAdmin");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }
        Optional<Role> role = roleRepo.findById(roleId);
        if (!role.isPresent()) {
            response.setDescription("Please, select a role to add to the SubAdmin");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }

        Optional<Status> status = statusRepo.findByName("Active");
        if (!status.isPresent()) {
            response.setDescription("Please, Active status has not been created.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
            return response;
        }

        MemberOrganizations memberOrganizations = new MemberOrganizations();
        memberOrganizations.setDate_added(new Date());
        memberOrganizations.setUserId(user.get().getId());
        memberOrganizations.setRoleId(role.get().getId());
        memberOrganizations.setStatusId(status.get().getId());
        MemberOrganizations createdMemberOrganizations = memberOrganizationsRepo.save(memberOrganizations);

        response.setDescription(user.get().getFirst_name() + ", has been made SubAdmin successfully");
        response.setStatusCode(HttpServletResponse.SC_OK);
        response.setData(createdMemberOrganizations);
        logger.info(response.getDescription() + ": {}", createdMemberOrganizations.toString());
        return response;
    }

    public BaseResponse GetAllSubAdmins(long roleId) {
        BaseResponse response = new BaseResponse();
        if (roleRepo.existsById(roleId)) {
            ArrayList<Object> SubAdmins = new ArrayList<>();
            List<MemberOrganizations> subAdmins = memberOrganizationsRepo.findByRoleId(roleId);
            if (!subAdmins.isEmpty()) {
                for (MemberOrganizations subAdmin : subAdmins) {
                    HashMap<String, Object> subadmins = new HashMap<>();
                    Optional<User> user = userRepo.findById(subAdmin.getUserId());
                    subadmins.put("user", user);

                    Optional<Status> status = statusRepo.findById(subAdmin.getStatusId());
                    subadmins.put("status", status);

                    Optional<Role> role = roleRepo.findById(subAdmin.getRoleId());
                    subadmins.put("role", role);
                    SubAdmins.add(subadmins);
                }
                response.setData(SubAdmins);
                response.setDescription("SubAdmins found successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription());
            } else {
                response.setDescription("SubAdmins not found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Please, select SubAdmin Role");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse updateOrganization(long organizationId, String Name, String Email, String Phone) {
        BaseResponse response = new BaseResponse();
        if (organizationRepo.existsById(organizationId)) {
            Organization organization = organizationRepo.findById(organizationId).get();
            organization.setName(Name);
            organization.setPhone(Phone);
            organization.setEmail(Email);
            if (organization.getUniquiId() == null) {
                String unique_id = "WM-" + utilityManager.randomAlphaNumeric(16);
                organization.setUniquiId(unique_id);
            }
            Organization saved_organization = organizationRepo.save(organization);
            response.setData(saved_organization);
            response.setDescription("Organization updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);

            logger.info(response.getDescription() + ": {}", saved_organization.toString());
        } else {
            response.setDescription("Organization does not exist, please check again");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getUser_Organizations(long userId) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);

        ArrayList<Object> userOrganizations = new ArrayList<>();
        if (userRepo.existsById(userId)) {
            Status status = statusRepo.findByName("Active").orElseThrow(() -> new EntityNotFoundException("active status not found"));
            userOrganizations = getUserMemberOganizations(userId, status.getId());
            if (!userOrganizations.isEmpty()) {
                response.setData(userOrganizations);
                response.setDescription("user organizations found");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", userOrganizations.toString());
            } else {
                response.setDescription("No Organizations found");
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("User not found");
            logger.error(response.getDescription());
        }
        return response;
    }

    public ArrayList<Object> getUserMemberOganizations(long userId, long statusID) {
        ArrayList<Object> userOrganizations = new ArrayList<>();
        List<MemberOrganizations> memberOrganizations = memberOrganizationsRepo.findByUserIdAndStatusId(userId, statusID);
        if (!memberOrganizations.isEmpty()) {
            for (MemberOrganizations memberOrganization : memberOrganizations) {
                Optional<Organization> organization = organizationRepo.findById(memberOrganization.getOrganizationId());
                if (organization.isPresent()) {
                    HashMap<String, Object> details = new HashMap<>();
                    GroupType groupType = groupTypeRepo.findById(organization.get().getGrouptypeId()).get();
                    details.put("grouptypename", groupType);
                    details.put("organization", organization);
                    Optional<Role> role = roleRepo.findById(memberOrganization.getRoleId());
                    details.put("role", role);
                    userOrganizations.add(details);
                }
            }
        }

        return userOrganizations;
    }


    Set<Role> getUserOrganizationRoles(long userId, long statusID){
        Set<Role> roles = new HashSet<>();
        List<MemberOrganizations> memberOrganizations = memberOrganizationsRepo.findByUserIdAndStatusId(userId, statusID);
        if (!memberOrganizations.isEmpty()) {
            for (MemberOrganizations memberOrganization : memberOrganizations) {
                Optional<Role> role = roleRepo.findById(memberOrganization.getRoleId());
                role.ifPresent(roles::add);
            }
        }
        return roles;
    }

    public BaseResponse getUserOrganizationStaffDetails(long organizationId, long userId) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        Optional<Organization> organization = organizationRepo.findById(organizationId);
        if (!organization.isPresent()) {
            response.setDescription("Organization not found.");
            logger.error(response.getDescription());
        } else {
            HashMap<String, Object> staffdet = new HashMap<>();
            Optional<MemberOrganizations> memb = memberOrganizationsRepo.findByOrganizationIdAndUserId(organizationId, userId);
            if (!memb.isPresent()) {
                response.setDescription("user staff details not found for organization.");
                logger.error(response.getDescription());
                return response;
            }
            staffdet.put("organization", organization.get());
            Optional<User> user = userRepo.findById(memb.get().getUserId());
            staffdet.put("user", user.get());
            Optional<Role> role = roleRepo.findById(memb.get().getRoleId());
            staffdet.put("role", role.get());
            Optional<Status> status = statusRepo.findById(memb.get().getStatusId());
            staffdet.put("status", status.get());
            response.setDescription("user staff details gotten successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            response.setData(staffdet);
            logger.info(response.getDescription() + ": {}", staffdet.toString());
        }
        return response;
    }

    public BaseResponse getUserOrganizationMemberDetails(long organizationId, long userId) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        Optional<Organization> organization = organizationRepo.findById(organizationId);
        if (!organization.isPresent()) {
            response.setDescription("Organization not found.");
            logger.error(response.getDescription());
        } else {
            HashMap<String, Object> staffdet = new HashMap<>();
            Optional<MemberOrganizations> memb = memberOrganizationsRepo.findByOrganizationIdAndUserId(organizationId, userId);
            if (!memb.isPresent()) {
                response.setDescription("user staff details not found for organization.");
                logger.error(response.getDescription());
                return response;
            }
            staffdet.put("organization", organization.get());
            Optional<User> user = userRepo.findById(memb.get().getUserId());
            staffdet.put("user", user.get());
            Optional<Role> role = roleRepo.findById(memb.get().getRoleId());
            staffdet.put("role", role.get());
            Optional<Status> status = statusRepo.findById(memb.get().getStatusId());
            staffdet.put("status", status.get());
            response.setDescription("user staff details gotten successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            response.setData(staffdet);
            logger.info(response.getDescription() + ": {}", staffdet.toString());
        }
        return response;
    }

    public BaseResponse confirmOrganizationEmail(String recoveryCode) {
        BaseResponse response = new BaseResponse();
        Optional<UserVerification> passwordReset = userVerificationRepo.findByCode(recoveryCode);
        if (passwordReset.isPresent()) {
            String email = passwordReset.get().getEmail();
            long organizationId = passwordReset.get().getUserId();
            Optional<Organization> organization = organizationRepo.findById(organizationId);

            if (organization.get().getEmail().equals(email)) {
                Optional<Status> status = statusRepo.findByName("Activated");
                Object newObject = null;
                if (status.isPresent()) {
                    long grouptypeId = organization.get().getGrouptypeId();
                    if (grouptypeId == 1l) {//Agencies
                        Optional<Agencies> agencies = agenciesRepo.findByOrganizationId(organizationId);
                        agencies.get().setEmail_statusId(status.get().getId());
                        newObject = agenciesRepo.save(agencies.get());
                        response.setData(newObject);
                    } else if (grouptypeId == 2l) {//Sellers
                        Optional<Businesses> businesses = businessesRepo.findByOrganizationId(organizationId);
                        businesses.get().setEmail_statusId(status.get().getId());
                        newObject = businessesRepo.save(businesses.get());
                        response.setData(newObject);
                    } else {//Producers
                        Optional<Producers> producers = producersRepo.findByOrganizationId(organizationId);
                        producers.get().setEmail_statusId(status.get().getId());
                        newObject = producersRepo.save(producers.get());
                        response.setData(newObject);
                    }
                    response.setDescription("user activated successfully");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription() + ": {}", newObject.toString());
                } else {
                    response.setDescription("User not found, please enter a valid userId");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }

            } else {
                response.setDescription("Activated status not found.");
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
}
