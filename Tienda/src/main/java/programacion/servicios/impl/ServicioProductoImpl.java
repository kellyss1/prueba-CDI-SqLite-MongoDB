package programacion.servicios.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.bson.Document;
import programacion.config.DbConfig;
import programacion.db.Producto;
import programacion.servicios.interfaces.ServicioProducto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class ServicioProductoImpl implements ServicioProducto {

    @Inject
    DbConfig dbConfig;

    @Override
    public void consultarPrecio(String nombre) {
        Producto productoDB1 = consultarProductoEnDB1(dbConfig.dataSource(), nombre);
        Producto productoDB2 = consultarProductoEnDB2(dbConfig.mongoDatabaseDB2(), nombre);

        if (productoDB1 != null && productoDB2 != null) {
            // Comparar los precios y retornar el producto con el precio más bajo
            if (productoDB1.getPrecio() < productoDB2.getPrecio()) {
                System.out.println("Producto con menor precio en DB1: " + productoDB1);
            } else {
                System.out.println("Producto con menor precio en DB2: " + productoDB2);
            }
        } else if (productoDB1 != null) {
            System.out.println("Producto encontrado en DB1: " + productoDB1);
        } else if (productoDB2 != null) {
            System.out.println("Producto encontrado en DB2: " + productoDB2);
        } else {
            System.out.println("Producto no encontrado en ninguna base de datos.");
        }
    }

    private Producto consultarProductoEnDB1(DataSource dataSource, String nombre) {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stat = con.prepareStatement("SELECT * FROM productos WHERE nombre = ?");
            stat.setString(1, nombre);
            ResultSet rs = stat.executeQuery();

            if (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getFloat("precio"));
                return producto;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();  // Manejo de errores
        }
        return null;  // Producto no encontrado
    }

    private Producto consultarProductoEnDB2(MongoDatabase mongoDatabase, String nombre) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("productos");

        // Encontrar el documento de MongoDB con el nombre
        FindIterable<Document> result = collection.find(Filters.eq("nombre", nombre));

        if (result.first() != null) {
            Document doc = result.first();
            Producto producto = new Producto();
            assert doc != null;
            producto.setId(doc.getInteger("id"));
            producto.setNombre(doc.getString("nombre"));
            producto.setDescripcion(doc.getString("descripcion"));

            // Obtener el precio y convertirlo a float
            Object precioObj = doc.get("precio");
            if (precioObj instanceof Double) {
                producto.setPrecio(((Double) precioObj).floatValue());
            } else if (precioObj instanceof Integer) {
                producto.setPrecio(((Integer) precioObj).floatValue());
            }
            return producto;
        }

        return null;  // Producto no encontrado
    }
}
