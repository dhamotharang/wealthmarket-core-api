package com.wm.core.service.user;

import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    Logger logger = LoggerFactory.getLogger(EmailService.class.getName());

    @Autowired
    SendGrid sendGrid;
    @Value("${wm.sendgrid.confirm-template-id}")
    private String confirmEmailTemplateID;

    @Value("${wm.sendgrid.password-reset-template-id}")
    private String passwordResetEmailTemplateID;

    public void sendEmail(String email, String userName, String verificationcode, String emailType) {

//        Mail mail = prepareMailWithVerificationCode(email, userName, verificationcode, emailType);
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sendGrid.api(request);
//            if (response != null) {
//                System.out.println("response code from sendgrid " + response.getBody());
//                System.out.println("response code from sendgrid " + response.getHeaders());
//                System.out.println("response code from sendgrid " + response.getStatusCode());
//        logger.info(response.getDescription() + ": {}", savedUser.toString());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        logger.error(e.printStackTrace() + ": {}", e.printStackTrace());
//        }
    }

    public Mail prepareMail(String email, String userName, String verificationCode, String EmailType) {

        Mail mail = new Mail();
        Email fromEmail = new Email();
        fromEmail.setEmail("support@thewealthmarket.com");
        fromEmail.setName("The WealthMarket");
        mail.setFrom(fromEmail);

        Email toEmail = new Email();
        toEmail.setEmail(email);
        toEmail.setName(userName);
        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("name", userName);
        if (EmailType.equals("ConfirmEmail")) {
            personalization.addDynamicTemplateData("code", verificationCode);
            mail.setTemplateId(confirmEmailTemplateID);
        } else if (EmailType.equals("PasswordReset")) {
            personalization.addDynamicTemplateData("code", verificationCode);
            mail.setTemplateId(passwordResetEmailTemplateID);
        }else if(EmailType.equals("Change Request")){

        }


        personalization.addTo(toEmail);
        mail.addPersonalization(personalization);

        Email replyTo = new Email();
        replyTo.setName("The WealthMarket");
        replyTo.setEmail("support@thewealthmarket.com");
        mail.setReplyTo(replyTo);
        ;


        return mail;
    }


}
