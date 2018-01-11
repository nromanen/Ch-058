package com.shrralis.ssdemo1.mail;

import com.shrralis.ssdemo1.mail.interfaces.ICitizenEmailMessage;
import com.shrralis.ssdemo1.mail.interfaces.IMailCitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/2/18 at 11:20 PM
 */
@Service
public class MailCitizenService implements IMailCitizenService {

	private final JavaMailSender mailSender;

	@Autowired
	public MailCitizenService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void send(final ICitizenEmailMessage message) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, message.getEncoding());

		helper.setTo(message.getDestEmail());
		helper.setSubject(message.getSubject());
		mimeMessage.setContent(message.getPreparedEmailContent(),
				message.getContentType() +
						"; charset=" +
						message.getEncoding());
		mailSender.send(mimeMessage);
	}
}
