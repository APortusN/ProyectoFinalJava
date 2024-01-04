package com.tienda.dao;

import com.tienda.model.Producto;
import com.tienda.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductoDAO {
    public Producto findById(Long id){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Producto.class, 1l);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public List<Producto> findAll(){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("FROM Producto").list();
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public List<Producto> findByName(String name){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("FROM Producto WHERE firstName='"+name+"'").list();
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public static void insert(Producto producto){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(producto);
            transaction.commit();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void insertAll(List<Producto> productos) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            for (Producto producto : productos) {
                session.save(producto);
            }

            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(Producto producto){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(producto);
            transaction.commit();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void delete(Producto producto){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(producto);
            transaction.commit();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
