package programacion.db;

import lombok.Data;
import lombok.Getter;

@Data
public class Producto {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Float precio;
}
