package programacion.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import javax.sql.DataSource;

@ApplicationScoped
public class DbConfig {
    @Produces
    @ApplicationScoped
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:productos.db");
        config.setUsername("sa");
        config.setPassword("");

        return new HikariDataSource(config);
    }

    @Produces
    @ApplicationScoped
    public MongoDatabase mongoDatabaseDB2() {
        // MongoDB (DB2)
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        return mongoClient.getDatabase("productos");  // Nombre de la base de datos en MongoDB
    }
}
