package controlador;

import modelo.Producto;

import java.sql.*;
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
        System.out.println(operaciones.deleteProducto("123123"));
        String[] temp = operaciones.getProducto("123123");
        for (String c:temp) {
            System.out.println(c);
        }
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
            System.out.println("Connection Closed");
            return true;
        } catch (Exception e) {
            System.out.println("ERROR Inserting Row");
            e.printStackTrace();
            return false;
        }
    }

    public boolean setProducto(String id, String nombre, String descripcion, int cantidad, float precio, String dimensiones) {

        try {
            Connection connection = this.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM Productos WHERE ID = "+id);

            System.out.println("Searching Producto....");

            if (resultSet.next() == false) {
                System.out.println("No results");
                return false;
            } else {
                System.out.println("Producto Found");
            }

            if(resultSet.next()){
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
            System.out.println("Connection Closed");
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
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Searching Producto....");
            if (resultSet.next() == false) {
                System.out.println("No results");
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
                System.out.println("Connection Closed");
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
            Connection connection = this.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM Productos WHERE ID = "+id);

            System.out.println("Searching Producto....");

            if (resultSet.next() == false) {
                System.out.println("No results");
                return false;
            } else {
                System.out.println("Producto Found");
            }

            if(resultSet.next()) {
                String query = "DELETE FROM Productos " +
                        "WHERE ID = " + id;
                System.out.println("Deleting....");
                statement.executeUpdate(query);
                System.out.println("Deleted");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error Deleting");
            e.printStackTrace();
            return false;
        }
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
