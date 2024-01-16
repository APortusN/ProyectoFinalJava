package com.tienda.dao;

import com.tienda.model.Usuario;
import com.tienda.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UsuarioDAO {

    public Usuario findById(Long id){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Usuario.class, 1l);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public List<Usuario> findAll(){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            return session.createQuery("FROM Usuario").list();
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public Usuario findByEmail(String email){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Usuario WHERE email=:email");
            query.setParameter("email", email);
            return (Usuario) query.uniqueResult();
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static void insert(Usuario usuario){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(usuario);
            transaction.commit();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void insertAll(List<Usuario> usuarios) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            for (Usuario usuario : usuarios) {
                session.save(usuario);
            }

            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(Usuario usuario){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(usuario);
            transaction.commit();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void delete(Usuario usuario){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(usuario);
            transaction.commit();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
