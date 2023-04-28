package com.example.gameserver.model.db;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @Column(updatable = false, nullable = false, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private Long userID;

    public Session(){}

    public Session(Long userID){ this.userID=userID; }

    public UUID getId() {
        return id;
    }

    public Long getUserID() {
        return userID;
    }
}
