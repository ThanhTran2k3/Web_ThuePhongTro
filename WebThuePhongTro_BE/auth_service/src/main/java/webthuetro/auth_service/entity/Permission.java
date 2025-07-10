package webthuetro.auth_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "Permissions")
public class Permission {

    @Id
    @GeneratedValue
    private UUID permissionId;
    private String name;
}