package com.kaikdev.ApiMercado.service;

import com.kaikdev.ApiMercado.exception.EmailSendException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:no-reply@mercado.local}")
    private String remetente;

        public void enviarEmailHtml(String para, String assunto, String html) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                helper.setTo(para);
                helper.setFrom(remetente);
                helper.setSubject(assunto);
                helper.setText(html, true); // HTML ativado

                mailSender.send(message);

            } catch (Exception e) {
                throw new EmailSendException("Erro ao enviar email", e);
            }
        }
    }
