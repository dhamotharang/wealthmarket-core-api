package com.wm.core.service.user;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.UserType;
import com.wm.core.repository.user.UserRepository;
import com.wm.core.repository.user.UserTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserTypeService {
	Logger logger = LoggerFactory.getLogger(UserTypeService.class.getName());

	@Autowired
	UserTypeRepository userTypeRepo;

	@Autowired
	UserRepository userRepo;

	public BaseResponse createUserType(UserType userType) {
		BaseResponse response = new BaseResponse();
		try {
			userType.setUserId(1);
			userType.setDate_created(new Date());
			userType.setLast_updated(new Date());
			UserType newType = userTypeRepo.save(userType);
			response.setData(newType);
			response.setDescription("User type created successfully");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", newType.toString());
		} catch (IllegalArgumentException ex) {
			response.setDescription("User type not created");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription() + ": {}", ex.getMessage());
		}
		return response;
	}

	public BaseResponse deleteUserType(long id) {
		BaseResponse response = new BaseResponse();
		Optional<UserType> type = userTypeRepo.findById(id);
		if (type.isPresent()) {
			userTypeRepo.deleteById(id);
			response.setDescription("User type deleted successfully");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", type.get().toString());
		} else {
			response.setDescription("User type not found");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription());
		}
		return response;
	}

	public BaseResponse editUserType(UserType userType, long userId) {
		BaseResponse response = new BaseResponse();
		if (userTypeRepo.existsById(userType.getId())) {
			userType.setLast_updated(new Date());
			userType.setUserId(userId);
			UserType updatedGroup = userTypeRepo.save(userType);
			response.setData(updatedGroup);
			response.setDescription("User type updated successfully");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", updatedGroup.toString());
		} else {
			response.setDescription("User type not found");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription());
		}

		return response;
	}

		public BaseResponse getUserTypes() {
			BaseResponse response = new BaseResponse();
			List<UserType> userTypes = userTypeRepo.findAll();
			if (!userTypes.isEmpty()) {
				response.setData(userTypes);
				response.setDescription("User types found successfully");
				response.setStatusCode(HttpServletResponse.SC_OK);
				logger.info(response.getDescription());
			} else {
				response.setDescription("No User types found");
				response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
				logger.error(response.getDescription());
			}
			return response;
		}

	public BaseResponse getUserTypes(long UsertypeID) {
		BaseResponse response = new BaseResponse();
		Optional<UserType> userTypes = userTypeRepo.findById(UsertypeID);
		if (userTypes.isPresent()) {
			response.setData(userTypes);
			response.setDescription("User types found successfully");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", userTypes.toString());
		} else {
			response.setDescription("No User types found");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription());
		}
		return response;
	}
}
