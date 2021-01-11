package com.wm.core.service.user;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.Status;
import com.wm.core.model.user.User;
import com.wm.core.repository.user.StatusRepository;
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
public class StatusesService {
	Logger logger = LoggerFactory.getLogger(StatusesService.class.getName());

	@Autowired
    StatusRepository statusRepo;

	@Autowired
	UserRepository userRepo;

	public BaseResponse createStatus(Status statuses, long userId) {
		BaseResponse response = new BaseResponse();
		try {
			User user = userRepo.findById(userId).get();
			statuses.setUserId(user.getId());
			statuses.setDate_created(new Date());
			statuses.setLast_updated(new Date());
			Status savedStatus = statusRepo.save(statuses);
			response.setData(savedStatus);
			response.setDescription("Status created successfully");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", savedStatus.toString());
		} catch (IllegalArgumentException ex) {
			response.setDescription("Status not created");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription() + ": {}", ex.getMessage());
		}
		return response;
	}

	public BaseResponse deleteStatus(long id) {
		BaseResponse response = new BaseResponse();
		if (statusRepo.existsById(id)) {
			Optional<Status> type = statusRepo.findById(id);
			statusRepo.deleteById(id);
			response.setDescription("Status deleted successfully");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", type.get().toString());
		} else {
			response.setDescription("Status not found");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription() );
		}
		return response;
	}

	public BaseResponse editStatus(Status status, long userId) {
		BaseResponse response = new BaseResponse();
		if (statusRepo.existsById(status.getId())) {
			status.setLast_updated(new Date());
			status.setUserId(userId);
			Status updatedStatus = statusRepo.save(status);
			response.setData(updatedStatus);
			response.setDescription("Status updated successfully");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", updatedStatus.toString());
		} else {
			response.setDescription("Status not found");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription() );
		}

		return response;
	}

	public BaseResponse getStatuses() {
		BaseResponse response = new BaseResponse();
		List<Status> statuses = statusRepo.findAll();
		if (!statuses.isEmpty()) {
			response.setData(statuses);
			response.setDescription("Statuses found successfully");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription());
		} else {
			response.setDescription("Status not found");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription());
		}
		return response;
	}

	public BaseResponse getStatusByID(long id) {
		BaseResponse response = new BaseResponse();
		if (statusRepo.existsById(id)) {
			Optional<Status> statuses = statusRepo.findById(id);
			response.setData(statuses);
			response.setDescription("Status found successfully");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", statuses.get().toString());
		} else {
			response.setDescription("Status not found");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription());
		}
		return response;
	}
}
