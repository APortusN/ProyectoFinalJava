package com.tienda.dao;

import com.tienda.model.Pedido;
import com.tienda.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PedidoDAO {

    public Pedido findById(Long id){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Pedido.class, 1l);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public List<Pedido> findAll(){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("FROM Pedido").list();
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public List<Pedido> findByName(String name){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("FROM Pedido WHERE firstName='"+name+"'").list();
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public static void insert(Pedido pedido){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(pedido);
            transaction.commit();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void insertAll(List<Pedido> pedidos) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            for (Pedido pedido : pedidos) {
                session.save(pedido);
            }

            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(Pedido pedido){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(pedido);
            transaction.commit();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void delete(Pedido pedido){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(pedido);
            transaction.commit();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
