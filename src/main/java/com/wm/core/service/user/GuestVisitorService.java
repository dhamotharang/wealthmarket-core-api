package com.wm.core.service.user;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.GuestVisitor;
import com.wm.core.model.user.UserType;
import com.wm.core.repository.user.GuestVisitorRepository;
import com.wm.core.repository.user.UserTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Optional;

@Service
public class GuestVisitorService {
    Logger logger = LoggerFactory.getLogger(GuestVisitorService.class.getName());

    @Autowired
    GuestVisitorRepository guestVisitorRepo;

    @Autowired
    UserTypeRepository userTypeRepo;

    public BaseResponse createGuest(String IpAddress, String Location) {
        BaseResponse response = new BaseResponse();
        GuestVisitor guestVisitor = new GuestVisitor();
        guestVisitor.setDate(new Date());
        guestVisitor.setLocation(Location);
        if (IpAddress.equals("")) {
            String ip = getMachineIPAddress();
            guestVisitor.setIpaddress(ip);
        } else {
            guestVisitor.setIpaddress(IpAddress);
        }
        String macAddress = getMachineMacAddress();
        guestVisitor.setMac_address(macAddress);
        Optional<UserType> userType = userTypeRepo.findUserTypeByName("Visitor");
        if (userType.isPresent()) {
            guestVisitor.setUsertypeId(userType.get().getId());
            try {
                GuestVisitor newGuest = guestVisitorRepo.save(guestVisitor);
                response.setData(newGuest);
                response.setDescription("Visitor's record captured and created successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", newGuest.toString());
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
                response.setDescription("Visitor not created.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription(), ex.getMessage());
            }
        } else {
            response.setDescription("Visitor User type has not been created.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse updateGuestVisitor(String ipAddress, String email, String phone) {
        BaseResponse response = new BaseResponse();
        Optional<GuestVisitor> guestVisitor = guestVisitorRepo.findByIpaddress(ipAddress);
        if (guestVisitor.isPresent()) {
            guestVisitor.get().setEmail(email);
            guestVisitor.get().setPhone_number(phone);
            Optional<UserType> userType = userTypeRepo.findUserTypeByName("Guest");
            if (userType.isPresent()) {
                guestVisitor.get().setUsertypeId(userType.get().getId());
                GuestVisitor savedVisitor = guestVisitorRepo.save(guestVisitor.get());
                response.setData(savedVisitor);
                response.setDescription("Guest User type updated successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", savedVisitor.toString());
            } else {
                response.setDescription("Guest User type not found.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }

        } else {
            response.setDescription("Guest User not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public String getMachineIPAddress() {
        String ipAddress = "";
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            ipAddress = ip.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

    public String getMachineMacAddress() {
        String macAddress = "";
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            if (mac != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                macAddress = sb.toString();
            } else {
                macAddress = "";
            }

        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return macAddress;
    }

}
