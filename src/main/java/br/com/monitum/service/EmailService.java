package br.com.monitum.service;


import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.monitum.entity.Usuario;
@Service
public class EmailService {
	@Resource
	private Environment env;
	@Autowired
	private JavaMailSender mailSender;
	
	Logger logger = Logger.getLogger(EmailService.class);

	public void enviar(final Usuario usuario, final String template) {
		
		new Thread(new Runnable() {
            public void run() {
                try {
                	/*SimpleMailMessage email = new SimpleMailMessage();
                	email.setTo(usuario.getEmail());
                	email.setSubject("Assunto");
                	email.setText(template);*/     
                	MimeMessage mensagem = mailSender.createMimeMessage();
                	
                	MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);
                	helper.setTo(usuario.getEmail());
                	helper.setSubject("Assunto");
                	helper.setText(template, true);
                	try {
                		mailSender.send(mensagem);
                	} catch (Exception e) {
                		e.printStackTrace();
                	}
                } catch (Throwable t) {
                	logger.error("erro na aplicação: " + t.toString());
                }
            }
        }).start();
	}
}
