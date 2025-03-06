package com.anda.rest.service;

/**
 * Interface for email service
 * @author Gleb Bereziuk (gl3bert)
 */

public interface EmailService {
    public void sendEmail(String to, String subject, String text);
}
