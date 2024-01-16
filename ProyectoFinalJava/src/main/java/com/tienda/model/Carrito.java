package com.tienda.model;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private List<Producto> productos = new ArrayList<>();

    public Carrito() {

    }

    public Carrito(List<Producto> productos) {
        this.productos = productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void quitarProducto(Producto producto) {
        productos.remove(producto);
    }

    public double calcularTotal(){
        double total = 0;

        for (Producto producto: productos){
            total += producto.getPrecio();
        }

        return total;
    }

    public void mostrarCarrito(){
        System.out.println("*********************************************");
        System.out.println("*                                           *");
        System.out.println("*           CARRITO DE COMPRAS:             *");
        System.out.println("*                                           *");
        System.out.println("*********************************************");
        for (Producto producto: productos){
            System.out.println(" ID producto: "+producto.getId()+" Producto: "+producto.getNombreProducto()+" Precio: $"+ producto.getPrecio());
        }
        System.out.println("Total: $ " + calcularTotal());
    }

    public void limpiarCarrito(){
        productos.clear();
    }
}
