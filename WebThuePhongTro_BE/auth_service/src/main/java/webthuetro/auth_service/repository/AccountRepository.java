package webthuetro.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webthuetro.auth_service.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByPhoneNumber(String phoneNumber);

    Optional<Account> findByGoogleId(String googleId);
}
