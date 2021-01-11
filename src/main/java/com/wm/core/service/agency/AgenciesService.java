
package com.wm.core.service.agency;

import com.wm.core.model.agency.Agencies;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.*;
import com.wm.core.repository.agency.AgenciesRepository;
import com.wm.core.repository.user.OrganizationRepository;
import com.wm.core.repository.user.RoleRepository;
import com.wm.core.repository.user.StatusRepository;
import com.wm.core.repository.user.UserRepository;
import com.wm.core.service.user.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class AgenciesService {
    Logger logger = LoggerFactory.getLogger(AgenciesService.class.getName());
    @Autowired
    AgenciesRepository agenciesRepo;

    @Autowired
    StatusRepository statusRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    OrganizationRepository organizationRepo;

    public BaseResponse createNewAgency(Organization organization, NewOrganization newOrganization) {
        BaseResponse response = new BaseResponse();
        Agencies agencies = new Agencies();
        Optional<Status> status = statusRepo.findByName("Pending");
        if (status.isPresent()) {
            User user = userRepo.findById(organization.getOwneruserId()).get();
            Optional<Role> role = roleRepo.findByName("Owner");
            if (role.isPresent()) {
                response = organizationService.createMemberOrganization(user, role.get(), organization);
                if (response.getStatusCode() == 200) {
                    agencies.setLicense_statusId(status.get().getId());
                    agencies.setAgency_cac_number(newOrganization.getAgency_cac_number());
                    agencies.setOrganizationId(organization.getId());
                    agencies.setLicensor_userId(0);
                    agencies.setEmail_statusId(status.get().getId());
                    agencies.setLicence_issued_date(new Date());
                    agencies.setDate(new Date());
                    Agencies created_agencies = agenciesRepo.save(agencies);
                    if (created_agencies != null) {
                        response.setStatusCode(HttpServletResponse.SC_OK);
                        response.setDescription("Agency created successfully.");
                        response.setData(created_agencies);
                        logger.info(response.getDescription() + ": {}", created_agencies.toString());
                    } else {
                        response.setDescription("Agency was not created");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                    }
                } else {
                    response.setDescription("Could not assign the agency to " + user.getLast_name());
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            } else {
                response.setDescription("Could not assign the agency and a role (Owner) to " + user.getLast_name());
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Pending Status has not been created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;

    }


    public BaseResponse getAllAgencies() {
        BaseResponse response = new BaseResponse();
        List<Agencies> agencies = agenciesRepo.findAll();
        ArrayList<Object> allAgencies = new ArrayList<>();
        if (!agencies.isEmpty()) {
            for (Agencies ag : agencies) {
                HashMap<String, Object> details = new HashMap<>();
                details.put("agency", ag);

                Optional<Organization> organization = organizationRepo.findById(ag.getOrganizationId());
                details.put("organization", organization.get());


                Optional<User> user = userRepo.findById(organization.get().getOwneruserId());
                details.put("owner", user);
                allAgencies.add(details);

            }
            if (!allAgencies.isEmpty()) {
                response.setData(allAgencies);
                response.setDescription("Agencies found successfully");
                logger.info(response.getDescription());
            } else {
                response.setDescription("Agencies not found");
                response.setStatusCode(HttpServletResponse.SC_NOT_FOUND);
                logger.error(response.getDescription());
            }

        } else {
            response.setDescription("agencies not found");
            response.setStatusCode(HttpServletResponse.SC_NOT_FOUND);
            logger.error("Agencies not found");
        }
        return response;
    }

    public BaseResponse editAgencyDetails(Agencies agencies) {
        BaseResponse response = new BaseResponse();
        if (agenciesRepo.existsById(agencies.getId())) {
            Agencies updatedAgencies = agenciesRepo.save(agencies);
            response.setData(updatedAgencies);
            response.setDescription("Agency Details updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedAgencies.toString());
        } else {
            response.setDescription("Agency not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }


    public BaseResponse LicenseAgency(long userId, long agencyId, long statusId) {
        BaseResponse response = new BaseResponse();
        if (agenciesRepo.existsById(agencyId) && statusRepo.existsById(statusId)) {
            Agencies currentAgencies = agenciesRepo.findById(agencyId).get();

            Optional<User> licensor = userRepo.findById(userId);
            currentAgencies.setLicensor_userId(licensor.get().getId());

            Status status = statusRepo.findById(statusId).get();
            currentAgencies.setLicense_statusId(status.getId());

            currentAgencies.setLicence_issued_date(new Date());
            Agencies licensed_agencies = agenciesRepo.save(currentAgencies);
            response.setData(licensed_agencies);
            response.setDescription("Agency licensed successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", licensed_agencies.toString());
        } else {
            response.setDescription("Agency not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }


}
