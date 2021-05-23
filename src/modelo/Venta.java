package modelo;

public class Venta {
    private String id;
    private String fecha;
    private String nombre_cliente;
    private String valor_Venta;

    public Venta(String id, String fecha, String nombre_cliente, String valor_Venta) {
        this.id = id;
        this.fecha = fecha;
        this.nombre_cliente = nombre_cliente;
        this.valor_Venta = valor_Venta;
    }

    public Venta() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getValor_Venta() {
        return valor_Venta;
    }

    public void setValor_Venta(String valor_Venta) {
        this.valor_Venta = valor_Venta;
    }
}
