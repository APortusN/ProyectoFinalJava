package org.example;

import com.tienda.dao.PedidoDAO;
import com.tienda.dao.ProductoDAO;
import com.tienda.dao.UsuarioDAO;
import com.tienda.model.Carrito;
import com.tienda.model.Pedido;
import com.tienda.model.Producto;
import com.tienda.model.Usuario;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {


        //Para cargar productos y probar la app.

        /*ProductoDAO productoDAO = new ProductoDAO();
        Producto newProducto1 = new Producto("Vestido",99990.0,"Vestido rojo con puntos","Channel",50);
        productoDAO.insert(newProducto1);

        Producto newProducto2 = new Producto("Televisor",199990.0,"Televisor 4K smartTV 60'","Samsung",50);
        productoDAO.insert(newProducto2);

        Producto newProducto3 = new Producto("Refrigerador",299990.0,"Refrigerador noFrost","LG",50);
        productoDAO.insert(newProducto3);

        Producto newProducto4 = new Producto("Pelota", 5990.0,"Pelota futbol ColoColo","Adidas",50);
        productoDAO.insert(newProducto4);*/

        Carrito carrito = new Carrito();

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("*********************************************");
            System.out.println("*                                           *");
            System.out.println("*  Bienvenido a la Mega tienda HOLA MUNDO!  *");
            System.out.println("*                                           *");
            System.out.println("*********************************************");
            System.out.println("********        MENU PRINCIPAL       ********");
            System.out.println("*********************************************");
            System.out.println("1.Registarse 2.Ver catalogo de productos 3.Ver carrito de compra 4.Pagar 5.Ver historial de compras y deudas 6.Salir ");
            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    registrarse();
                    break;
                case 2:
                    verProductos(carrito);
                    break;
                case 3:
                    if (carrito.getProductos().isEmpty()){
                        System.out.println("No hay productos en el carrito");
                    } else {
                        verOEliminarProductosCarrito(carrito);}
                    break;
                case 4:
                    if (carrito.getProductos().isEmpty()){
                        System.out.println("No hay productos en el carrito");
                    }else {pagar(carrito);}
                    break;
                case 5:
                    verHistorial();
                    break;
                case 6:
                    System.out.println("Saliendo del sistema....");
                    return;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

    public static void registrarse() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese nombre usuario:");
        String nombreUsuario = scanner.next();

        System.out.println("Ingrese apellido usuario:");
        String apellidoUsuario = scanner.next();

        System.out.println("Ingrese email usuario:");
        String emailUsuario = scanner.next();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario newUsuario = new Usuario(nombreUsuario, apellidoUsuario, emailUsuario);
        usuarioDAO.insert(newUsuario);
        System.out.println("*********************************************");
        System.out.println("*                                           *");
        System.out.println("*               Bienvenid@!                 *");
        System.out.println("*                                           *");
        System.out.println("*********************************************");
        System.out.println("Bienvenido a nuestra tienda: "+nombreUsuario+" "+apellidoUsuario);
    }

    public static void verProductos(Carrito carrito) {

        System.out.println("Los productos disponibles en el catalogo son:");

        Long agregar = 0L;

        do {

            System.out.println("*********************************************");
            System.out.println("*                                           *");
            System.out.println("*           LISTA DE PRODUCTOS:             *");
            System.out.println("*                                           *");
            System.out.println("*********************************************");
            ProductoDAO productoDAO = new ProductoDAO();
            List<Producto> productos = productoDAO.findAll();

            for (Producto producto : productos) {
                System.out.println("ID: " + producto.getId() +
                        ", Nombre: " + producto.getNombreProducto() +
                        ", Precio: " + producto.getPrecio()+
                                ", Descripción: " + producto.getDescripcion()+
                                ", Marca: " + producto.getMarca()+
                                ", Cantidad: " + producto.getCantidadProductos());
            }
            System.out.println();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el ID del producto que desea agregar al carrito (para salir escriba 0) : ");
            agregar = scanner.nextLong();

            if (agregar != 0 ) {

                Producto producto = productoDAO.findById(agregar);
                int cantidad = producto.getCantidadProductos();
                if (cantidad> 0 ){
                    producto.setCantidadProductos(cantidad-1);
                    productoDAO.update(producto);
                    System.out.println("*********************************************");
                    System.out.println("*      Producto agregado                    *");
                    System.out.println("*********************************************");
                    System.out.println("El producto: " + producto.getNombreProducto() + " ha sido agregado a su carrito.");
                    carrito.agregarProducto(producto);
                }else{
                    System.out.println("No queda stock del producto seleccionado");
                }

            }
        }while (agregar != 0 );
    }

    public static void pagar(Carrito carrito){

        Boolean despacho = false;
        Double totalCarrito = carrito.calcularTotal();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su correo electronico:");
        String obtenerCorreo = scanner.next();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario newUsuario2 = usuarioDAO.findByEmail(obtenerCorreo);

        System.out.println("*********************************************");
        System.out.println("*           Bienvenid@                      *");
        System.out.println("*********************************************");

        System.out.println("Bienvenido: " + newUsuario2.getNombre() + " " + newUsuario2.getApellido());

        System.out.println("El total de su compra es de: $" + totalCarrito);
        System.out.println("Desea pagar el carrito: 1.Si 2.Salir");
        int opcion = scanner.nextInt();

        if(opcion == 1){
            System.out.println("Desea despacho: 1.Si 2.No");
            int opcionDespacho = scanner.nextInt();
            if (opcionDespacho==1){
                despacho = true;
            }
            Date fechaDeHoy = new Date();
            LocalDate localDate = fechaDeHoy.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Pedido pedido = new Pedido(localDate,totalCarrito,despacho,newUsuario2);
            PedidoDAO pedidoDAO = new PedidoDAO();
            pedidoDAO.insert(pedido);
            System.out.println("*********************************************");
            System.out.println("*           Pedido Pagado                   *");
            System.out.println("*********************************************");
            System.out.println("Ha pagado su carrito y el pedido ha sido generado");
            carrito.limpiarCarrito();
        }

    }

    public static void verHistorial(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su correo electronico:");
        String obtenerCorreo = scanner.next();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario newUsuario3 = usuarioDAO.findByEmail(obtenerCorreo);

        PedidoDAO pedidoDAO = new PedidoDAO();

        System.out.println("*********************************************");
        System.out.println("*           Bienvenid@                      *");
        System.out.println("*********************************************");

        System.out.println("Bienvenido: " + newUsuario3.getNombre() + " " + newUsuario3.getApellido());

        List<Pedido> listaDePedidos = pedidoDAO.findByUserID(newUsuario3.getId());

        System.out.println("Su historial de compras es: " );

        for (Pedido pedido : listaDePedidos) {
            String estadoDespacho = "";

            if(pedido.getDespacho()==true){
                estadoDespacho = "Enviado";
            }else{
                estadoDespacho = "Pedido retirado en tienda.";
            }

            System.out.println("Pedido con fecha: "+ pedido.getFechaPedido() +" con un total pagado de: $"+  pedido.getMontoTotal() +" Estado del despacho: "+ estadoDespacho);

        }

    }

    public static void verOEliminarProductosCarrito(Carrito carrito){
        int opcion = 0;
        do {
            carrito.mostrarCarrito();


            Scanner scanner = new Scanner(System.in);
            System.out.println("Desea eliminar productos del carrito: 1.Si 2.Salir");
            opcion = scanner.nextInt();

            if (opcion == 1) {
                System.out.println("Ingrese el ID del producto que desea eliminar: ");
                Long idEliminar = scanner.nextLong();
                Producto productoAEliminar = buscarProductoPorId(carrito.getProductos(), idEliminar);

                System.out.println(productoAEliminar.getNombreProducto());

                carrito.quitarProducto(productoAEliminar);

                ProductoDAO productoDAO = new ProductoDAO();

                int cantidad = productoAEliminar.getCantidadProductos();
                productoAEliminar.setCantidadProductos(cantidad + 1);
                productoDAO.update(productoAEliminar);

                System.out.println("*********************************************");
                System.out.println("*           Producto eliminado              *");
                System.out.println("*********************************************");

                System.out.println("El producto: " + productoAEliminar.getNombreProducto() + " ha sido elimando del carrito");
            }
        }while (opcion != 2);
    }

    public static Producto buscarProductoPorId(List<Producto> productos, Long id) {
        for (Producto producto : productos) {
            if (producto.getId() == id) {
                return producto;
            }
        }
        return null;
    }
}


