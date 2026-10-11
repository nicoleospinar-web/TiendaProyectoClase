package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;

public record Factura(String codigo, LocalDate fecha, double total,
                      EstadoFactura estadoFactura, MetodoPago metodoPago, Cliente cliente,
                      ArrayList<DetalleFactura> listaDetallesFactura, Tienda ownedByTienda) {


    // cacular el valor de la factura (sumar todos los detalles factura)

    public double calcularTotal() {
        double total = 0;
        for (DetalleFactura detalle : listaDetallesFactura) {
            total += detalle.calcularSubTotal();
        }
        return total;
    }

    public boolean tieneClienteConR() {

        boolean resultado = false;
        resultado = cliente.verificarNombreConR();
        return resultado ;
    }

    public boolean comproCelularIphone() {
        for (DetalleFactura detalle : listaDetallesFactura) {
            if (detalle.verificarCompraCelular()) {
                return true;
            }
        }
        return false;
    }

    public boolean clienteSeLlamaJuan () {
        boolean resultado = false;
        resultado = cliente.verificarNombreJuan();
        return resultado ;
    }
}

