package controlador;

import modelo.Producto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.plaf.nimbus.State;
import java.security.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.SortedMap;

public class Operaciones implements InterfaceProxy{
    static private Operaciones operaciones = null;
    private PrivateKey privateKey;
    //private Connection connection;

    public Operaciones() {}

    public static Operaciones crearIFacade() {
        if (operaciones == null) {
            operaciones = new Operaciones();
        }
        return operaciones;
    }

    public int addProducto(byte[] key,String id, String nombre, String descripcion, int cantidad, float precio, String dimensiones){
        try {
            if (this.itsAdmin(key) != 2) {
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
                return 0;
            } else {
                System.out.println("This User Does not Exist");
                return 3;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 1;
        }
    }

    public int setProducto(String id, String nombre, String descripcion, int cantidad, float precio, String dimensiones) {

        try {
            boolean temp = false;
            Connection connection = this.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM Productos WHERE ID = "+id);

            System.out.println("Searching Producto....");

            if (resultSet.next() == false) {
                System.out.println("No results");
                System.out.println("Connection Closed \n");
                return 1;
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
            return 0;
        } catch (Exception e) {
            System.out.println("Error Updating");
            e.printStackTrace();
            return 2;
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
                return new String[1];
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

    public int deleteProducto(String id) {
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
                return 1;
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
            return 0;
        } catch (Exception e) {
            System.out.println("Error Deleting");
            e.printStackTrace();
            return 2;
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
                return temp;
            } else {
                do {
                    temp.add(this.getProducto(resultSet.getString("id")));
                } while (resultSet.next());
                System.out.println("Productos Found");
                System.out.println("Connection Closed \n");
                connection.close();
                return temp;
            }
        } catch (Exception e) {
            System.out.println("Error Searching all Productos");
            e.printStackTrace();
            return null;
        }
    }

    public String[] getUser(byte[] key, String empleadoUser){
        try {
            if(this.itsAdmin(key) == 0){
                String[] temp = new String[5];
                Connection connection = this.createConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE Username = ?");
                ResultSet resultSet = statement.executeQuery();
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
                    System.out.println("Connection Closed \n");
                    return temp;
                } else {
                    System.out.println("No Results");
                    System.out.println("Connection Closed \n");
                    connection.close();
                    return temp = new String[1];
                }
            } else {
                System.out.println("Access Denied");
                System.out.println("Connection Closed \n");
                return new String[2];
            }
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
            return null;
        }

    }

    public boolean createUser(byte[] key, String empleadoUser, String nombre,String documento, String password, int tipo){
        try {
            Connection connection = this.createConnection();
            Statement statement = connection.createStatement();
            if (this.itsAdmin(key) == 0) {
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
            System.out.println("The ID is Already in Use");
            throwables.printStackTrace();
            return false;
        }
    }

    public int deleteUser(byte[] key, String empleadoUser){
        try {
            if(this.itsAdmin(key) == 0){
                Connection connection = this.createConnection();
                Statement statement = connection.createStatement();
                String query = "select count(Username) as usercount from Users";
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.next();
                if(resultSet.getInt("usercount")>1){
                    if(this.getUser(key,empleadoUser) != null){
                        query = "DELETE FROM Users " +
                                "WHERE Username = '" + empleadoUser+"'";
                        System.out.println("Deleting....");
                        statement.executeUpdate(query);
                        System.out.println("Deleted");
                        connection.close();
                        return 0;
                    }else{
                        System.out.println("No results");
                        connection.close();
                        return 1;
                    }
                }else{
                    System.out.println("Cant Delete Last User");
                    return 2;
                }
            }
            System.out.println("This User Does Can Not Do This Operation");
            return 3;
        } catch (Exception e) {
            e.printStackTrace();
            return 4;
        }
    }

    public boolean setUser(byte[] adminUser, String empleadoUser, String nombre,String documento, String password, int tipo){
        if(this.deleteUser(adminUser, empleadoUser) == 0){
            this.createUser(adminUser, empleadoUser, nombre, documento, password, tipo);
            return true;
        }
        return false;
    }

    public ArrayList<String[]> getAllUsers(byte[] adminUser){
        try {
            if(this.itsAdmin(adminUser) == 0){
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

    public boolean login(String user, String password){
        try {
            Connection connection = this.createConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT username FROM Users WHERE Username = ? AND Password = ?");
            statement.setString(1,user);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int itsAdmin(byte[] key){
        try {
            String temp = decrypt(key);
            Connection connection = this.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select Tipo from  Users where Username = '"+temp+"'");
            if(!resultSet.next()){
                return 2;
            } else if(resultSet.getBoolean(1)){
                return 0;
            } else if (!resultSet.getBoolean(1)){
                return 1;
            }
            return 2;
        } catch (Exception e) {
            System.out.println("Unexpected Error");
            e.printStackTrace();
            return 2;
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

    @Override
    public String performOperation() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        return Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
    }

    private String decrypt(byte[] data) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        return new String(cipher.doFinal(data));
    }
}
