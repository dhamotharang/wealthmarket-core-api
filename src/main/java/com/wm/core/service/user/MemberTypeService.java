package com.wm.core.service.user;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.MemberType;
import com.wm.core.model.user.SubMemberType;
import com.wm.core.model.user.User;
import com.wm.core.repository.user.MemberTypeRepository;
import com.wm.core.repository.user.SubMemberTypeRepository;
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
public class MemberTypeService {
    Logger logger = LoggerFactory.getLogger(MemberTypeService.class.getName());

    @Autowired
    MemberTypeRepository memberTypeRepo;

    @Autowired
    SubMemberTypeRepository subMemberTypeRepo;

    @Autowired
    UserRepository userRepo;

    public BaseResponse createMemberType(MemberType memberType, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            memberType.setUserId(user.getId());
            memberType.setDate_created(new Date());
            memberType.setLast_updated(new Date());
            MemberType newType = memberTypeRepo.save(memberType);
            response.setData(newType);
            response.setDescription("Member type created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", newType.toString());
        } catch (IllegalArgumentException ex) {
            response.setDescription("Member type not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription(), ex.getMessage());
        }
        return response;
    }


    public BaseResponse deleteMemberType(long membertypeId) {
        BaseResponse response = new BaseResponse();
        if (memberTypeRepo.existsById(membertypeId)) {
            List<SubMemberType> subMemberTypes = subMemberTypeRepo.findByMembertypeId(membertypeId);
            if (!subMemberTypes.isEmpty()) {
                subMemberTypeRepo.deleteAll(subMemberTypes);
            }
            memberTypeRepo.deleteById(membertypeId);
            response.setDescription("Member type deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("Member type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse editMemberType(MemberType memberType, long userId) {
        BaseResponse response = new BaseResponse();
        if (memberTypeRepo.existsById(memberType.getId())) {
            memberType.setLast_updated(new Date());
            memberType.setUserId(userId);
            MemberType updatedGroup = memberTypeRepo.save(memberType);
            response.setData(updatedGroup);
            response.setDescription("Member type updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedGroup.toString());
        } else {
            response.setDescription("Member type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getAllMemberTypes() {
        BaseResponse response = new BaseResponse();
        List<MemberType> memberTypes = memberTypeRepo.findAll();
        if (!memberTypes.isEmpty()) {
            response.setData(memberTypes);
            response.setDescription("Member types found succesfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No Member types found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getMemberTypeByID(Long id) {
        BaseResponse response = new BaseResponse();
        Optional<MemberType> type = memberTypeRepo.findById(id);
        if (type.isPresent()) {
            response.setData(type);
            response.setDescription("Member type found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", type.toString());
        } else {
            response.setDescription("No Member types found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

}
