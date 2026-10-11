package co.edu.uniquindio.poo.model;

public class DetalleFactura {
    private final int cantidadComprada;
    private final double subTotal;
    private final Producto producto;
    private Factura ownedByFactura;

    public DetalleFactura(int cantidadComprada, double subTotal, Producto producto, Factura owndByFactura) {
        this.cantidadComprada = cantidadComprada;
        this.subTotal = subTotal;
        this.producto = producto;
        this.ownedByFactura = owndByFactura;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setOwnedByFactura(Factura owndByFactura) {
        this.ownedByFactura = owndByFactura;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public Factura getOwndByFactura() {
        return ownedByFactura;
    }

    public float calcularSubTotal(){
        return (float) (cantidadComprada * getProducto().getValor());
    }
}
