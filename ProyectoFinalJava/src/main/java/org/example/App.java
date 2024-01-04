package org.example;

import com.tienda.dao.UsuarioDAO;
import com.tienda.model.Usuario;


public class App 
{
    public static void main( String[] args ) {


    UsuarioDAO usuarioDAO = new UsuarioDAO();
    Usuario newUsuario = new Usuario("Pedro","Perez","pperezmail.com");
    usuarioDAO.insert(newUsuario);
}
}
