package programacion;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import programacion.servicios.interfaces.ServicioProducto;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SeContainer container = SeContainerInitializer.newInstance().initialize();
        ServicioProducto servicioProducto = container.select(ServicioProducto.class).get();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();

        servicioProducto.consultarPrecio(nombre);
        container.close();
    }
}

