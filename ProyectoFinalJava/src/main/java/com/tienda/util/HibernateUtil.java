package com.tienda.util;

import com.tienda.model.Pedido;
import com.tienda.model.Producto;
import com.tienda.model.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    //SessionFactory es una clase de Hibernate para guardar la conexion
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Propiedades de conexio de Hibernate
                Properties settings = new Properties();
                //Arlette
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/ProyectoFinalJava");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
                settings.put(Environment.USER, "postgres");
                settings.put(Environment.PASS, "postgres");
                //Robert
                /*settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/proyecto4");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
                settings.put(Environment.USER, "postgres");
                settings.put(Environment.PASS, "postgres");*/
                //Alvaro
                /*settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/proyecto4");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
                settings.put(Environment.USER, "postgres");
                settings.put(Environment.PASS, "postgres");*/
                //Constanza
                /*settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/proyecto4");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
                settings.put(Environment.USER, "postgres");
                settings.put(Environment.PASS, "postgres");*/

                settings.put(Environment.SHOW_SQL, "false");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                //Manejar la construccion de tablas de la base de datos
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);
                //Asociar las clases
                configuration.addAnnotatedClass(Usuario.class);
                configuration.addAnnotatedClass(Producto.class);
                configuration.addAnnotatedClass(Pedido.class);


                //Servicio de parametros de conexion
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                //Crear conexion. Será utilizada por los DAO's
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
