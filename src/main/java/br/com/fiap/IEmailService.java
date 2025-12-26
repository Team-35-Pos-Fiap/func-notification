package br.com.fiap;

import java.util.List;

public interface IEmailService {
    void sendEmail(String subject, String body, List<String> recipients);
}
