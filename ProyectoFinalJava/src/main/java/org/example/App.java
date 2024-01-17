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
import java.util.*;


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
            System.out.println("1.Registarse 2.Ver catalogo de productos 3.Ver carrito de compra 4.Pagar 5.Ver historial de compras y deudas 6.Buscar productos 7.Salir ");
            int opcion = 0;
            try {
                opcion = scanner.nextInt();
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
                        buscadorProductos(carrito);
                        break;
                    case 7:
                        System.out.println("Saliendo del sistema....");
                        return;
                    default:
                        System.out.println("Opción no válida");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número de menu válido.");
            }

        }
    }

    public static void registrarse() {
        String nombreUsuario = "";
        String apellidoUsuario = "";
        String emailUsuario= "";

        Scanner scanner = new Scanner(System.in);

        do {

            System.out.println("Ingrese nombre usuario:");
            nombreUsuario = scanner.next();
        }while(!sinNumeros(nombreUsuario));

        do{
            System.out.println("Ingrese apellido usuario:");
            apellidoUsuario = scanner.next();
        }while(!sinNumeros(apellidoUsuario));

        do{
        System.out.println("Ingrese email usuario:");
        emailUsuario = scanner.next();
        }while(!esCorreo(emailUsuario));

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario newUsuario = new Usuario(nombreUsuario, apellidoUsuario, emailUsuario);
        usuarioDAO.insert(newUsuario);
        System.out.println("*********************************************");
        System.out.println("*                                           *");
        System.out.println("*               Bienvenid@!                 *");
        System.out.println("*                                           *");
        System.out.println("*********************************************");
        System.out.println("Bienvenido a nuestra tienda: "+nombreUsuario+" "+apellidoUsuario);
        System.out.println("El correo registrado es: " + emailUsuario);
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

            try{
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

            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número de producto válido.");
            } catch (NullPointerException e) {
            System.out.println("Error: Producto no encontrado en el catalogo.");
        }

        }while (agregar != 0 );
    }

    public static void pagar(Carrito carrito){

        String obtenerCorreo="";
        Boolean despacho = false;
        Double totalCarrito = carrito.calcularTotal();

        Scanner scanner = new Scanner(System.in);

        try{
            do{
            System.out.println("Ingrese su correo electronico:");
            obtenerCorreo = scanner.next();
            }while(!esCorreo(obtenerCorreo));

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario newUsuario2 = usuarioDAO.findByEmail(obtenerCorreo);

            System.out.println("*********************************************");
            System.out.println("*           Bienvenid@                      *");
            System.out.println("*********************************************");

            System.out.println("Bienvenido: " + newUsuario2.getNombre() + " " + newUsuario2.getApellido());

            System.out.println("El total de su compra es de: $" + totalCarrito);

            System.out.println("Desea pagar el carrito: 1.Si 2.Salir");
            int opcion = scanner.nextInt();

            if (opcion == 1) {
                System.out.println("Desea despacho: 1.Si 2.No");
                int opcionDespacho = scanner.nextInt();
                if (opcionDespacho == 1) {
                    despacho = true;
                }
                Date fechaDeHoy = new Date();
                LocalDate localDate = fechaDeHoy.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                Pedido pedido = new Pedido(localDate, totalCarrito, despacho, newUsuario2);
                PedidoDAO pedidoDAO = new PedidoDAO();
                pedidoDAO.insert(pedido);
                List<Producto> imprimirCarrito = new ArrayList<>(carrito.getProductos());
                System.out.println("*********************************************");
                System.out.println("*           Pedido Pagado                   *");
                System.out.println("*********************************************");
                System.out.println("Ha pagado su carrito y el pedido ha sido generado");
                System.out.println("*********************************************");
                System.out.println("*          BOLETA DE COMPRA                 *");
                System.out.println("*********************************************");
                System.out.println("Producto           Precio");
                System.out.println("---------------------------------------------");
                for (Producto producto: imprimirCarrito){
                    System.out.println(producto.getNombreProducto()+"          $"+ producto.getPrecio());
                }
                System.out.println("---------------------------------------------");
                System.out.println("Total                       $" + totalCarrito);
                System.out.println("*********************************************");

                carrito.limpiarCarrito();
            }

        } catch (InputMismatchException e) {
        System.out.println("Error: Debe ingresar un número de opción válido.");
        } catch (NullPointerException e) {
            System.out.println("Error: Correo no registrado como cliente.");}
    }

    public static void verHistorial(){
        String obtenerCorreo ="";

        Scanner scanner = new Scanner(System.in);
        try{
            do{
            System.out.println("Ingrese su correo electronico:");
            obtenerCorreo = scanner.next();
            }while(!esCorreo(obtenerCorreo));

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario newUsuario3 = usuarioDAO.findByEmail(obtenerCorreo);

            System.out.println("*********************************************");
            System.out.println("*           Bienvenid@                      *");
            System.out.println("*********************************************");

            System.out.println("Bienvenido: " + newUsuario3.getNombre() + " " + newUsuario3.getApellido());

            PedidoDAO pedidoDAO = new PedidoDAO();
            List<Pedido> listaDePedidos = pedidoDAO.findByUserID(newUsuario3.getId());

            System.out.println("Su historial de compras es: " );

            int contador = 1;

            for (Pedido pedido : listaDePedidos) {
                String estadoDespacho = "";

                if (pedido.getDespacho() == true) {
                    estadoDespacho = "Enviado";
                } else {
                    estadoDespacho = "Pedido retirado en tienda.";
                }

                System.out.println(" Pedido N°: " + contador +" - Fecha: " + pedido.getFechaPedido() + " - Total pagado de: $" + pedido.getMontoTotal() + " - Estado del despacho: " + estadoDespacho);
                contador++;
            }

        } catch (NullPointerException e) {
            System.out.println("Error: Correo no registrado como cliente.");}
    }

    public static void buscadorProductos(Carrito carrito){
        String buscar = "";
        String productoBuscar = "";
        Long agregar=0L;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre del producto que desea buscar");
        buscar = scanner.next();
        productoBuscar = capitalizarPrimeraLetra(buscar);
        do{
        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> listaDeProductos = productoDAO.findByName(productoBuscar);

        int contador=1;

        for (Producto producto : listaDeProductos) {
            System.out.println("N°: "+contador+" - ID: " + producto.getId() +
                    ", Nombre: " + producto.getNombreProducto() +
                    ", Precio: " + producto.getPrecio()+
                    ", Descripción: " + producto.getDescripcion()+
                    ", Marca: " + producto.getMarca()+
                    ", Cantidad: " + producto.getCantidadProductos());
            contador++;
            try{
                System.out.println("Ingrese el ID del producto que desea agregar al carrito (para salir escriba 0) : ");
                agregar = scanner.nextLong();


                if (agregar != 0 ) {

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

            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número de producto válido.");
            } catch (NullPointerException e) {
                System.out.println("Error: Producto no encontrado en el catalogo.");
            }
        }
    }while (agregar != 0);

    }

    public static void verOEliminarProductosCarrito(Carrito carrito){
        int opcion = 0;
        do {
            carrito.mostrarCarrito();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Desea eliminar productos del carrito: 1.Si 2.Salir");
            opcion = scanner.nextInt();
            if(opcion>2 || opcion<1){
                System.out.println("Ingrese opción valida");
            }else if (opcion == 1) {
                try{
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
                } catch (NullPointerException e) {
                    System.out.println("Error: ID de producto no encontrado en su carrito.");}
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

    private static boolean sinNumeros(String cadena) {
        boolean validador = cadena.matches("[a-zA-Z]+");
        if (validador == false){
            System.out.println("Debe ingresar solo caracteres validos");}
        return validador;
    }

    private static boolean esCorreo(String cadena) {
        boolean validador = cadena.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        if (validador == false){
        System.out.println("Formato de correo no valido");}
        return validador;
    }

    private static String capitalizarPrimeraLetra(String texto) {

        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }

}


