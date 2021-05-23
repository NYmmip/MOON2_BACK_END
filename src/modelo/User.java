package modelo;

public class User {
    private String nombre;
    private String documento;
    private String username;
    private String password;
    private String tipo;

    public User(){
        this.nombre = "";
        this.password = "";
        this.documento = "";
        this.username = "";
        this.password = "";
        this.tipo = "";
    }

    public User(String nombre, String documento, String username, String password, String tipo) {
        this.nombre = nombre;
        this.documento = documento;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
