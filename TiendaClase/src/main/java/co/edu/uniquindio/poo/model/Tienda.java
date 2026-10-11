package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.*;

public class Tienda {
    private final String nombre;
    private final String nit;// documentar
    private String telefono;

    private final ArrayList<Cliente> listaClientes = new ArrayList<>();
    private final List<Factura> listaFacturas = new LinkedList<>();
    private Map<String,Producto> hashMaplistaProductos = new HashMap<>();


    public Tienda(String nombre, String nit,String telefono){
        this.nombre = nombre;
        this.nit = nit;
        this.telefono = telefono;
    }
    public String getNombre() {
        return nombre;
    }

    public String getNit() {
        return nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // CRUD CLIENTE - REGISTRAR, BUSCAR, ELIMINAR

    public String registrarCliente (Cliente cliente) {
        Cliente clienteEncontrado = buscarCliente(cliente.getDocumentoIdentidad());
        if (clienteEncontrado == null){
            listaClientes.add(cliente);
            return "El cliente fue registrado exitosamente";
        } else return "No se puede registrar, ya existe un cliente con esa informacion registrado anteriormente.";
    }

    // Cambiar == null por un optional

    public String registrarCliente2(Cliente cliente) {
        Optional<Cliente> clienteEncontrado = buscarCliente2(cliente.getDocumentoIdentidad());

        if (clienteEncontrado.isEmpty()) {
            listaClientes.add(cliente);
            return "El cliente fue registrado exitosamente";
        } else return "No se puede registrar, ya existe un cliente con esa informacion registrado anteriormente.";

    }

    public Cliente buscarCliente (String documentoIdentidad) {
        for (Cliente cliente : listaClientes) {
            if (documentoIdentidad.equals(cliente.getDocumentoIdentidad())) {
                return cliente;
            }
        } return null;
    }
    // Buscar Cliente con optional

    public Optional<Cliente> buscarCliente2(String documentoIdentidad) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getDocumentoIdentidad().equals(documentoIdentidad)) {
                return Optional.of(cliente);
            }
        }
        return Optional.empty();
    }

    public boolean clienteTieneFacturas (String documentoIdentidad) {
        for (Factura factura : listaFacturas) {
            if (factura.cliente().getDocumentoIdentidad().equals(documentoIdentidad)) {
                return true;
            }
       } return false;
    }

    public boolean eliminarCliente (String documentoIdentidad) {
        Cliente clienteEncontrado = buscarCliente(documentoIdentidad);
        if (clienteEncontrado == null || clienteTieneFacturas(documentoIdentidad)) {
            return false;
        }
        return listaClientes.remove(clienteEncontrado);
    }

    // CRUD PRODUCTO

    public String registrarProducto (Producto producto) {
        if (hashMaplistaProductos.containsKey(producto.getCodigo())) {
            return "No se puede registrar, ya existe un producto con ese código.";
        }
        hashMaplistaProductos.put(producto.getCodigo(), producto);
        return "El producto fue registrado exitosamente.";
    }

    public Producto buscarProducto (String codigo) {
        return hashMaplistaProductos.get(codigo);
    }

    public boolean productoFueVendido (String codigo) {
        for (Factura factura : listaFacturas) {
            for (DetalleFactura detalle : factura.listaDetallesFactura()) {
                if (detalle.getProducto().getCodigo().equals(codigo)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean eliminarProducto(String codigo) {
        if (!hashMaplistaProductos.containsKey(codigo) || productoFueVendido(codigo)) {
            return false;
        }
        hashMaplistaProductos.remove(codigo);
        return true;
    }

    // CRUD FACTURA

    public String registrarFactura (Factura factura) {
        if (buscarFactura(factura.codigo()) != null) {
            return "No se puede registrar, ya existe una factura con ese código.";
        }
        if (buscarCliente(factura.cliente().getDocumentoIdentidad()) == null) {
            return "El cliente no está registrado en la tienda.";
        }
        if (factura.listaDetallesFactura().isEmpty()) {
            return "La factura debe tener al menos un producto.";
        }
        listaFacturas.add(factura);
        factura.cliente().getListaFacturas().add(factura);
        return "La factura fue registrada exitosamente.";
    }

    public Factura buscarFactura (String codigo) {
        for (Factura factura : listaFacturas) {
            if (factura.codigo().equals(codigo)) {
                return factura;
            }
        }
        return null;
    }

    public Optional<Factura> obtenerFactura (String codigo) {
        return listaFacturas.stream().filter(f -> f.codigo().equals(codigo)).findFirst();
    }

    // Ejercicios ----- Punto 1
    // Obtener la lista de los productos con una cantidad disponible mayor igual a 10

    public List <Producto> obtenerMayoresDiez () {
        List<Producto> productoAdecuado = new ArrayList<>();
        for (Producto productosBuenos : hashMaplistaProductos.values()) {
            if (productosBuenos.getCantidadDisponible() >= 10) {
                productoAdecuado.add(productosBuenos);
            }
        } return productoAdecuado;
    }

    // Punto 2
    // Obtener la lista de codigos de los productos con una cantidad disponible mayor igual a 10 y menor que 50

    public ArrayList<String> obtenerCodigosProductosAgotados2(int limiteInferior, int limiteSuperior){
        ArrayList<String> resultado = new ArrayList<>();
        for (Producto productoAux : hashMaplistaProductos.values()) {
            if (productoAux.getCantidadDisponible() >= 10 && productoAux.getCantidadDisponible() < 50) {
                resultado.add(productoAux.getCodigo());
            }
        }
        return resultado;
    }

    // Punto 3
    // Obtener la lista de clientes que hayan comprado el 07 de octubre de 2026

    public ArrayList<Cliente> obtenerClientesCompras(){
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        LocalDate fechaConsulta = LocalDate.of(2026,10,7);
        for (Factura factura : listaFacturas){
            if(factura.fecha().isEqual(fechaConsulta)){
                listaClientes.add(factura.cliente());
            }
        }
        return listaClientes;
    }

    // Punto 4
    // Obtener las facturas que tenga un cliente donde su nombre empiece por R

    public ArrayList<Factura> obtenerFacturasClienteConR(){
        ArrayList<Factura> resultado = new ArrayList<>();

        for (Factura factura : listaFacturas){
            if(factura.tieneClienteConR()){
                resultado.add(factura);
            }
        }
        return resultado;

    }



    }



