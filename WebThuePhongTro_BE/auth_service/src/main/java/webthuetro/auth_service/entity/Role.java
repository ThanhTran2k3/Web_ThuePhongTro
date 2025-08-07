package webthuetro.auth_service.entity;

import jakarta.persistence.*;


import java.util.Set;
import java.util.UUID;


public class Role {


    private UUID roleId;
    private String name;


    private Set<Permission> permissions;
}