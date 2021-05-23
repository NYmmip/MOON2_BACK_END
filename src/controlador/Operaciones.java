package controlador;

import modelo.Producto;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

public class Operaciones {
    static private Operaciones operaciones = null;
    //private Connection connection;

    public Operaciones() {}

    public static Operaciones crearIFacade() {
        if (operaciones == null) {
            operaciones = new Operaciones();
        }
        return operaciones;
    }

    public static void main(String[] args) throws SQLException {
        operaciones = Operaciones.crearIFacade();
        //operaciones.addProducto("32124","nombew","asdsaeews",53322,6734323,"3x53x23");

        //String[] temp = operaciones.getProducto("123123");
        //for (String[] c:operaciones.getAllProductos()) {
            //for (String x:c) {
                //.out.println(x);
            //}
        //}
        //operaciones.createUser("Admin1","user2","Usuario2","312112","pass1",0);
        //operaciones.setUser("Admin1","user1","Usuario2","312112","pass1",0);
        System.out.println(operaciones.getAllProductos().size());
    }

    public boolean addProducto(String id, String nombre, String descripcion, int cantidad, float precio, String dimensiones) throws SQLException {
        try {
            Connection connection = this.createConnection();
            Statement statement = connection.createStatement();
            String query = "INSERT INTO PRODUCTOS (ID,Nombre,Descripcion,Cantida,Precio,Dimensiones)" +
                    "VALUES ('" + id + "','" + nombre + "','" + descripcion + "'," + cantidad + "," + precio + ",'" + dimensiones + "')";
            System.out.println("Inserting Row....");
            int rows = statement.executeUpdate(query);
            if (rows > 0)
                System.out.println("Row Inserted");
            connection.close();
            System.out.println("Connection Closed \n");
            return true;
        } catch (Exception e) {
            System.out.println("ERROR Inserting Row");
            e.printStackTrace();
            return false;
        }
    }

    public boolean setProducto(String id, String nombre, String descripcion, int cantidad, float precio, String dimensiones) {

        try {
            boolean temp = false;
            Connection connection = this.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM Productos WHERE ID = "+id);

            System.out.println("Searching Producto....");

            if (resultSet.next() == false) {
                System.out.println("No results");
                System.out.println("Connection Closed \n");
                return false;
            } else {
                System.out.println("Producto Found");
                temp = true;
            }

            if(temp){
                String query = "DELETE FROM Productos "+
                        "WHERE ID = "+id;
                System.out.println("Deleting....");
                statement.executeUpdate(query);
                System.out.println("Deleted");
                query = "INSERT INTO PRODUCTOS (ID,Nombre,Descripcion,Cantida,Precio,Dimensiones)" +
                        "VALUES ('" + id + "','" + nombre + "','" + descripcion + "'," + cantidad + "," + precio + ",'" + dimensiones + "')";
                System.out.println("Inserting Row....");
                statement.executeUpdate(query);
                System.out.println("Row Inserted");
            }

            connection.close();
            System.out.println("Connection Closed \n");
            return true;
        } catch (Exception e) {
            System.out.println("Error Updating");
            e.printStackTrace();
            return false;
        }
    }

    public String[] getProducto(String id) {
        try {
            Connection connection = this.createConnection();
            String[] temp = new String[6];
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Productos WHERE ID = ?");
            statement.setString(1,id);
            System.out.println("Searching Producto....");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() == false) {
                System.out.println("No results");
                System.out.println("Connection Closed \n");
                connection.close();
                return null;
            } else {
                do {
                    temp[0] = resultSet.getString("id");
                    temp[1] = resultSet.getString("nombre");
                    temp[2] = resultSet.getString("descripcion");
                    temp[3] = resultSet.getString("cantida");
                    temp[4] = resultSet.getString("precio");
                    temp[5] = resultSet.getString("dimensiones");
                } while (resultSet.next());
                System.out.println("Producto Found");
                System.out.println("Connection Closed \n");
                connection.close();
                return temp;
            }
        } catch (Exception e) {
            System.out.println("Error Searching Producto");
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteProducto(String id) {
        try {
            boolean temp = false;
            Connection connection = this.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM Productos WHERE ID = '"+id+"'");

            System.out.println("Searching Producto....");

            if (resultSet.next() == false) {
                System.out.println("No results");
                System.out.println("Connection Closed \n");
                connection.close();
                return false;
            } else {
                System.out.println("Producto Found");
                temp = true;
            }

            if(temp) {
                String query = "DELETE FROM Productos " +
                        "WHERE ID = " + id;
                System.out.println("Deleting....");
                statement.executeUpdate(query);
                System.out.println("Deleted");
            }
            System.out.println("Connection Closed \n");
            return true;
        } catch (Exception e) {
            System.out.println("Error Deleting");
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<String[]> getAllProductos(){
        try {
            ArrayList<String[]> temp = new ArrayList<>();
            Connection connection = this.createConnection();
            Statement statement = connection.createStatement();
            System.out.println("Searching Producto....");
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM Productos");
            if (resultSet.next() == false) {
                System.out.println("No results");
                connection.close();
                return null;
            } else {
                do {
                    temp.add(this.getProducto(resultSet.getString("id")));
                } while (resultSet.next());
                System.out.println("Productos Found");
                System.out.println("Connection Closed \n");
                connection.close();
                return temp;
            }
        } catch (SQLException throwables) {
            System.out.println("Error Searching all Productos");
            throwables.printStackTrace();
            return null;
        }
    }

    public String[] getUser(String adminUser, String empleadoUser){
        try {
            String[] temp = new String[5];
            Connection connection = this.createConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE Username = ?");
            statement.setString(1,adminUser);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                if(resultSet.getBoolean("tipo")){
                    System.out.println("Access Granted");
                    System.out.println("Searching User....");
                    statement.setString(1,empleadoUser);
                    resultSet = statement.executeQuery();
                    if(resultSet.next()){
                        System.out.println("User Found");
                        temp[0] = resultSet.getString("nombre");
                        temp[1] = resultSet.getString("documento");
                        temp[2] = resultSet.getString("username");
                        temp[3] = resultSet.getString("password");
                        temp[4] = resultSet.getString("tipo");
                    } else {
                        System.out.println("No Results");
                        System.out.println("Connection Closed \n");
                        connection.close();
                        return null;
                    }
                } else {
                    System.out.println("Access Denied");
                    System.out.println("Connection Closed \n");
                    return null;
                }
            } else {
                System.out.println("Access Denied");
                System.out.println("Connection Closed \n");
                return null;
            }
            System.out.println("Connection Closed \n");
            return temp;
        } catch (SQLException throwables) {
            System.out.println("Error");
            throwables.printStackTrace();
            return null;
        }

    }

    public boolean createUser(String adminUser, String empleadoUser, String nombre,String documento, String password, int tipo){
        try {
            Connection connection = this.createConnection();
            Statement statement = connection.createStatement();
            if (this.getUser(adminUser,adminUser) != null) {
                String query = "INSERT INTO Users " +
                        "VALUES ('" + nombre + "','" + documento + "','" + empleadoUser + "','" + password + "'," + tipo+")";
                System.out.println("Inserting Row....");
                int rows = statement.executeUpdate(query);
                if (rows > 0)
                    System.out.println("Row Inserted");
                connection.close();
                System.out.println("Connection Closed \n");
            }
            return true;
        } catch (SQLException throwables) {
            System.out.println("Error Creating User");
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(String adminUser, String empleadoUser){
        try {
            if(this.itsAdmin(adminUser)){
                Connection connection = this.createConnection();
                Statement statement = connection.createStatement();
                String query = "select count(Username) as usercount from Users";
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.next();
                if(resultSet.getInt("usercount")>1){
                    if(this.getUser(adminUser,empleadoUser) != null){
                        query = "DELETE FROM Users " +
                                "WHERE Username = '" + empleadoUser+"'";
                        System.out.println("Deleting....");
                        statement.executeUpdate(query);
                        System.out.println("Deleted");
                        connection.close();
                        return true;
                    }else{
                        System.out.println("No results");
                        connection.close();
                        return false;
                    }
                }else{
                    System.out.println("Cant Delete Last User");
                    return false;
                }
            }
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean setUser(String adminUser, String empleadoUser, String nombre,String documento, String password, int tipo){
        if(this.deleteUser(adminUser, empleadoUser)){
            this.createUser(adminUser, empleadoUser, nombre, documento, password, tipo);
            return true;
        }
        return false;
    }

    public ArrayList<String[]> getAllUsers(String adminUser){
        try {
            if(this.itsAdmin(adminUser)){
                ArrayList<String[]> temp = new ArrayList<>();
                Connection connection = this.createConnection();
                Statement statement = connection.createStatement();
                System.out.println("Searching Users....");
                ResultSet resultSet = statement.executeQuery("SELECT Username FROM Users");
                if (resultSet.next() == false) {
                    System.out.println("No results");
                    connection.close();
                    return null;
                } else {
                    do {
                        temp.add(this.getUser(adminUser,resultSet.getString("username")));
                    } while (resultSet.next());
                    System.out.println("Users Found");
                    System.out.println("Connection Closed \n");
                    connection.close();
                    return temp;
                }
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    private boolean itsAdmin(String adminUser){
        if(this.getUser(adminUser,adminUser) != null){
            return true;
        }
        return false;
    }

    private Connection createConnection(){
        try {
            System.out.println("Connecting....");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://THE-PC\\GBASES:1433;database=MOON2",
                    "moon2",
                    "11223344");
            System.out.println("Connection Successful");
            return connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Connection Error");
            return null;
        }
    }

}
