package modelo;

public class Producto {
    private String id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private float precio;
    private String dimensiones;

    public Producto() {
        this.id = "";
        this.nombre = "";
        this.descripcion = "";
        this.precio = 0;
        this.cantidad = 0;
        this.dimensiones = "";
    }

    public Producto(String id, String nombre, String descripcion,int cantidad, float precio, String dimensiones) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.dimensiones = dimensiones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }
}
