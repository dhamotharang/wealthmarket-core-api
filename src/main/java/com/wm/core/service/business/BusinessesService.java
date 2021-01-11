package com.wm.core.service.business;

import com.wm.core.model.business.BusinessSector;
import com.wm.core.model.business.BusinessType;
import com.wm.core.model.business.Businesses;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.*;
import com.wm.core.repository.business.BusinessSectorRepository;
import com.wm.core.repository.business.BusinessTypeRepository;
import com.wm.core.repository.business.BusinessesRepository;
import com.wm.core.repository.user.*;
import com.wm.core.service.user.OrganizationService;
import com.wm.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class BusinessesService {
    Logger logger = LoggerFactory.getLogger(BusinessesService.class.getName());
    @Autowired
    BusinessesRepository businessesRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleTypeRepo;

    @Autowired
    BusinessSectorRepository businessSectorRepo;

    @Autowired
    BusinessTypeRepository businessTypeRepo;

    @Autowired
    GroupTypeRepository groupTypeRepo;

    @Autowired
    GroupRepository groupRepo;

    @Autowired
    StatusRepository statusRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    OrganizationRepository organizationRepo;

    @Autowired
    MemberTypeRepository memberTypeRepo;

    @Autowired
    UserService userService;


    public BaseResponse createNewBusiness(Organization organization, NewOrganization newOrganization) {
        BaseResponse response = new BaseResponse();
        if (businessSectorRepo.existsById(newOrganization.getBusiness_sectorId())) {
            Optional<BusinessSector> businessSector = businessSectorRepo.findById(newOrganization.getBusiness_sectorId());
            if (businessTypeRepo.existsById(newOrganization.getBusiness_typeId())) {
                Optional<BusinessType> businessType = businessTypeRepo.findById(newOrganization.getBusiness_typeId());

                Optional<Role> role = roleRepo.findByName("Owner");
                if (role.isPresent()) {
                    Optional<Status> status = statusRepo.findByName("Pending");
                    if (status.isPresent()) {
                        User user = userRepo.findById(organization.getOwneruserId()).get();
                        response = organizationService.createMemberOrganization(user, role.get(), organization);
                        if (response.getStatusCode() == 200) {
                            Businesses business = new Businesses(newOrganization.getBusiness_description(), businessSector.get().getId(),
                                    businessType.get().getId(), new Date(), status.get().getId(), status.get().getId(), 0, newOrganization.getBusiness_cac_number(), newOrganization.getDate_founded(),
                                    organization.getId(), newOrganization.getWebsite_link());
                            Businesses new_business = businessesRepo.save(business);
                            if (new_business != null) {
                                response.setData(new_business);
                                response.setDescription("Business registered successfully");
                                response.setStatusCode(HttpServletResponse.SC_OK);
                                logger.info(response.getDescription() + ": {}", new_business.toString());
                            } else {
                                response.setDescription("Business was not created");
                                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                                logger.error(response.getDescription());
                            }
                        } else {
                            response.setDescription("Could not assign the business to " + user.getLast_name());
                            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                            logger.error(response.getDescription());
                        }
                    } else {
                        response.setDescription("Pending Status has not been created");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                    }
                } else {
                    response.setDescription("Could not assign a role.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            } else {
                response.setDescription("Business type was not specified, please, select correct business type id");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Business sector not found, please enter correct business sector id");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getBusinessesByType(long typeId) {
        BaseResponse response = new BaseResponse();
        if (businessTypeRepo.existsById(typeId)) {
            List<Businesses> businesses = businessesRepo.findByBusinesstypeId(typeId);
            if (!businesses.isEmpty()) {
                response.setData(businesses);
                response.setDescription("Businesses found successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ":{}", businesses.toString());
            } else {
                response.setDescription("No businesses found for this type");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Selected business type not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getBusinessesBySector(long sectorId) {
        BaseResponse response = new BaseResponse();
        if (businessSectorRepo.existsById(sectorId)) {
            List<Businesses> businesses = businessesRepo.findByBusinesssectorId(sectorId);
            if (!businesses.isEmpty()) {
                response.setData(businesses);
                response.setDescription("Businesses found successfully");
                logger.info(response.getDescription() + ": {}", businesses.toString());
                response.setStatusCode(HttpServletResponse.SC_OK);
            } else {
                response.setDescription("No businesses found for this type");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Selected business sector not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getAllBusinesses() {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> bizes = new ArrayList<>();
        List<Businesses> businesses = businessesRepo.findAll();
        if (!businesses.isEmpty()) {
            for (Businesses biz : businesses) {
                HashMap<String, Object> details = new HashMap<>();
                details.put("business", biz);

                Optional<BusinessSector> businessSector = businessSectorRepo.findById(biz.getBusinesssectorId());
                details.put("sector", businessSector.get());

                Optional<BusinessType> businessType = businessTypeRepo.findById(biz.getBusinesstypeId());
                details.put("businesstype", businessType.get());

                Optional<Organization> organization = organizationRepo.findById(biz.getOrganizationId());
                details.put("organization", organization.get());


                Optional<User> user = userRepo.findById(organization.get().getOwneruserId());
                details.put("owner", user);

                Optional<MemberType> memberType = memberTypeRepo.findById(organization.get().getMembertypeId());
                details.put("membertype", memberType);
                bizes.add(details);

            }
            if (!bizes.isEmpty()) {
                response.setData(bizes);
                response.setDescription("Businesses found successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription());
            } else {
                response.setDescription("Businesses not found");
                response.setStatusCode(HttpServletResponse.SC_NOT_FOUND);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Businesses not found");
            response.setStatusCode(HttpServletResponse.SC_NOT_FOUND);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getBusinessDetailsByOrganizationId(long id) {
        BaseResponse response = new BaseResponse();
        Optional<Organization> organization = organizationRepo.findById(id);
        if (organization.isPresent()) {
            HashMap<String, Object> details = new HashMap<>();
            details.put("organization", organization.get());
            if (organization.get().getUniquiId() == null) {
                organizationService.updateOrganization(id, organization.get().getName(), organization.get().getEmail(), organization.get().getPhone());
            }


            Optional<Businesses> business = businessesRepo.findByOrganizationId(id);
            details.put("business", business.get());

            Optional<BusinessSector> businessSector = businessSectorRepo.findById(business.get().getBusinesssectorId());
            details.put("sector", businessSector.get());

            Optional<BusinessType> businessType = businessTypeRepo.findById(business.get().getBusinesstypeId());
            details.put("businesstype", businessType.get());

            Optional<Status> status = statusRepo.findById(business.get().getApproval_statusId());
            details.put("approvalstatus", status.get());

            Optional<Status> emailstatus = statusRepo.findById(business.get().getEmail_statusId());
            if (emailstatus.get().getName().equals("Pending")) {
                userService.CreateUserVerifcation(organization.get().getId(), organization.get().getEmail(), "ConfirmEmail", organization.get().getName());
            }
            details.put("emailstatus", emailstatus);


            Optional<User> user = userRepo.findById(organization.get().getOwneruserId());
            details.put("owner", user);

            Optional<User> creator = userRepo.findById(organization.get().getCreatoruserId());
            details.put("creator", creator);


            Optional<MemberType> memberType = memberTypeRepo.findById(organization.get().getMembertypeId());
            details.put("membertype", memberType);
            response.setData(details);
            String description = "Business found successfully";
            response.setDescription(description);
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", details.toString());
        } else {
            response.setDescription("Organization not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse editBusinessDetails(Businesses businesses) {
        BaseResponse response = new BaseResponse();
        if (businessesRepo.existsById(businesses.getId())) {
            Businesses editedBusiness = businessesRepo.save(businesses);
            response.setData(editedBusiness);
            response.setDescription("Business Details Updated Successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", editedBusiness.toString());
        } else {
            response.setDescription("Business doesn't exist, please check again");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


}
