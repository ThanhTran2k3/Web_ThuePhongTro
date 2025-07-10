package webthuetro.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "Users")
public class User {

    @Id
    @Column(length = 36)
    private String userId;
    private String avatar = "https://res.cloudinary.com/duywrzel9/image/upload/v1750787388/default.png";
    private String userName;
    private byte gender;
    private LocalDate birthDay;
    private LocalDate joinDay = LocalDate.now();

    @PrePersist
    public void generateId() {
        if (userId == null) {
            userId = UUID.randomUUID().toString();
        }
    }
}
