package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            Categoria verduraleria = Categoria.builder()
                    .denominacion("Verduralería")
                    .build();

            Categoria carniceria = Categoria.builder()
                    .denominacion("Carnicería")
                    .build();

            Categoria conVencimiento = Categoria.builder()
                    .denominacion("Con vencimiento")
                    .build();

            Articulo tomate = Articulo.builder()
                    .cantidad(10)
                    .denominacion("Tomate")
                    .precio(20)
                    .build();

            tomate.getCategorias().add(verduraleria);
            tomate.getCategorias().add(conVencimiento);

            Articulo lechuga = Articulo.builder()
                    .cantidad(20)
                    .denominacion("Lechuga")
                    .precio(10)
                    .build();

            lechuga.getCategorias().add(verduraleria);
            lechuga.getCategorias().add(conVencimiento);

            Articulo carneVacuna = Articulo.builder()
                    .cantidad(5)
                    .denominacion("Carne Vacuna")
                    .precio(100)
                    .build();

            carneVacuna.getCategorias().add(carniceria);
            carneVacuna.getCategorias().add(conVencimiento);

            Articulo pollo = Articulo.builder()
                    .cantidad(10)
                    .denominacion("Pollo")
                    .precio(50)
                    .build();

            pollo.getCategorias().add(carniceria);
            pollo.getCategorias().add(conVencimiento);

            Domicilio domicilio = Domicilio.builder()
                    .nombreCalle("Paso de los Andes")
                    .numero(245)
                    .build();

            Cliente cliente = Cliente.builder()
                    .nombre("Agustin")
                    .apellido("Leyes")
                    .dni(40123456)
                    .domicilio(domicilio)
                    .build();

            DetalleFactura detalleFactura1 = DetalleFactura.builder()
                    .cantidad(2)
                    .articulo(tomate)
                    .subtotal(40)
                    .build();

            DetalleFactura detalleFactura2 = DetalleFactura.builder()
                    .cantidad(1)
                    .articulo(carneVacuna)
                    .subtotal(100)
                    .build();

            Set<DetalleFactura> detalleFacturas = new HashSet<>();
            detalleFacturas.add(detalleFactura1);
            detalleFacturas.add(detalleFactura2);

            Factura factura = Factura.builder()
                    .numero(98765)
                    .fecha(Date.valueOf("2024-09-04"))
                    .cliente(cliente)
                    .detalleFacturas(detalleFacturas)
                    .build();

            entityManager.persist(factura);
            entityManager.flush();
            entityManager.getTransaction().commit();

            Factura facturaDB = entityManager.find(Factura.class, factura.getId());
            System.out.println("Se recupera factura n° " + facturaDB.getNumero());

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase Factura");
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}
