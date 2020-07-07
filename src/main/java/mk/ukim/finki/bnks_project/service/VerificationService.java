package mk.ukim.finki.bnks_project.service;

import javax.servlet.http.HttpServletRequest;

public interface VerificationService {
    boolean verifyRequestSignature(HttpServletRequest request);
}
