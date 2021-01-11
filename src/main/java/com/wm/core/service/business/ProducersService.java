package com.wm.core.service.business;

import com.wm.core.model.business.Producers;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.*;
import com.wm.core.repository.business.ProducersRepository;
import com.wm.core.repository.user.RoleRepository;
import com.wm.core.repository.user.StatusRepository;
import com.wm.core.repository.user.UserRepository;
import com.wm.core.service.user.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProducersService {
    Logger logger = LoggerFactory.getLogger(ProducersService.class.getName());

    @Autowired
    ProducersRepository producersRepo;

    @Autowired
    UserRepository userRepo;


    @Autowired
    RoleRepository roleTypeRepo;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    StatusRepository statusRepo;

    public BaseResponse createNewProducer(Organization organization, NewOrganization newOrganization) {
        BaseResponse response = new BaseResponse();
        Optional<Role> role = roleTypeRepo.findByName("Owner");
        if (role.isPresent()) {
            Optional<Status> status = statusRepo.findByName("Pending");
            if (status.isPresent()) {
                User user = userRepo.findById(organization.getOwneruserId()).get();
                response = organizationService.createMemberOrganization(user, role.get(), organization);
                if(response.getStatusCode() == 200){
                    Producers producers = new Producers(newOrganization.getProducer_cac_number(),newOrganization.getDate_founded()
                    ,new Date(),status.get().getId(),status.get().getId(),0,organization.getId());
                    Producers new_producer = producersRepo.save(producers);
                    if (new_producer != null) {
                        response.setData(new_producer);
                        response.setDescription("Producer created successfully");
                        response.setStatusCode(HttpServletResponse.SC_OK);
                        logger.info(response.getDescription() + ": {}", new_producer.toString());
                    } else {
                        response.setDescription("Producer was not created");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                    }
                }else{
                    response.setDescription("Could not assign the producer organization to " + user.getLast_name());
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }else{
                response.setDescription("Pending Status has not been created");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        }else{
            response.setDescription("Could not assign a role.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }


        return response;
    }


    public BaseResponse editProducersDetails(Producers producers) {
        BaseResponse response = new BaseResponse();
        if (producersRepo.existsById(producers.getId())) {
            Producers updatedAgencies = producersRepo.save(producers);
            response.setData(updatedAgencies);
            response.setDescription("Producer Details updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedAgencies.toString());
        } else {
            response.setDescription("Business doesn't exist, please check again");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse getAllProducers() {
        BaseResponse response = new BaseResponse();
        List<Producers> producer = producersRepo.findAll();
        if (!producer.isEmpty()) {
            response.setData(producer);
            response.setDescription("Producers found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", producer.toString());
        } else {
            response.setDescription("No Producers found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

}
