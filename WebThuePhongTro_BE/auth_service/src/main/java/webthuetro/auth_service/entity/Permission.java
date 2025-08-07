package webthuetro.auth_service.entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;


public class Permission {

    @Id
    @GeneratedValue
    private UUID permissionId;
    private String name;
}