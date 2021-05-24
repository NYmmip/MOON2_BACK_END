package controlador;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Run {
    private Operaciones operaciones;
    private Proxy proxy;
    private byte key[];

    public static void main(String[] args) throws SQLException {
        new Run();
    }

    public Run(){
        try {
            this.operaciones = Operaciones.crearIFacade();
            this.proxy = new Proxy("admin", "pass123");
            this.key = this.proxy.performOperation();
            this.operaciones.addProducto(this.key,"31876","Producto76","Descipcion 173",3,8778,"2x3x1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
