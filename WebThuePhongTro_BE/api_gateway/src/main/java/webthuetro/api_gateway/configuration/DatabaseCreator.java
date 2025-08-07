package webthuetro.api_gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
public class DatabaseCreator implements CommandLineRunner {

    @Value("${DB_USER}")
    private String username;

    @Value("${DB_PASS}")
    private String password;

    @Value("${DB_HOST}")
    private String endpoint;

    @Override
    public void run(String... args) throws Exception {
        String baseUrl = "jdbc:mysql://" + endpoint + "/?useSSL=false&allowPublicKeyRetrieval=true";
        try (Connection conn = DriverManager.getConnection(baseUrl, username, password)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS webthuetro_accounts;");
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS webthuetro_users;");
        }

        // Tạo bảng accounts trong webthuetro_accounts
        String accountDbUrl = "jdbc:mysql://" + endpoint + "/webthuetro_accounts?useSSL=false&allowPublicKeyRetrieval=true";
        try (Connection conn = DriverManager.getConnection(accountDbUrl, username, password)) {
            Statement stmt = conn.createStatement();
            String createTableAccounts = """
            CREATE TABLE IF NOT EXISTS accounts (
                account_id VARCHAR(255) NOT NULL,
                email VARCHAR(255) DEFAULT NULL,
                is_delete BIT(1) NOT NULL,
                password VARCHAR(255) DEFAULT NULL,
                phone_number VARCHAR(255) DEFAULT NULL,
                google_id VARCHAR(255) DEFAULT NULL,
                google_name VARCHAR(255) DEFAULT NULL,
                email_verified BIT(1) NOT NULL,
                linked_google BIT(1) NOT NULL,
                phone_verified BIT(1) NOT NULL,
                PRIMARY KEY (account_id)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
        """;
            stmt.executeUpdate(createTableAccounts);
        }

        String usersDbUrl = "jdbc:mysql://" + endpoint + "/webthuetro_users?useSSL=false&allowPublicKeyRetrieval=true";
        try (Connection conn = DriverManager.getConnection(usersDbUrl, username, password)) {
            Statement stmt = conn.createStatement();
            String createTableUsers = """
            CREATE TABLE IF NOT EXISTS users (
              user_id VARCHAR(36) NOT NULL,
              birth_day DATE DEFAULT NULL,
              join_day DATE DEFAULT NULL,
              user_name VARCHAR(255) DEFAULT NULL,
              avatar VARCHAR(255) DEFAULT NULL,
              gender TINYINT NOT NULL,
              PRIMARY KEY (user_id)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
        """;
            stmt.executeUpdate(createTableUsers);
        }
    }

}
