package heo.web.assignment.repository;

import heo.web.assignment.entity.token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByVerificationCode(String verificationCode);
}
