package com.example.gameserver.jpa;
import com.example.gameserver.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findByUsername(String username);

    @Query(value = "SELECT u FROM User u INNER JOIN Session s ON u.id = s.userID WHERE s.id=:session_id")
    User getUserBySessionId(@Param("session_id") UUID sessionId);
}
