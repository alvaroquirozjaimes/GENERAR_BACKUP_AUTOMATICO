package org.BaseDeDatos;
// Clase que almacena la configuración de una base de datos
public class DataBaseConfig {
    // Variables privadas que almacenan el usuario, la contraseña y el nombre de la base de datos
    private String user;
    private String password;
    private String dbdname;

    // Constructor que inicializa los atributos user, password y dbdname
    public DataBaseConfig(String user, String password, String dbdname) {
        this.user = user;  // Asigna el nombre de usuario a la base de datos
        this.password = password;  // Asigna la contraseña
        this.dbdname = dbdname;  // Asigna el nombre de la base de datos
    }

    // Getter para obtener el nombre de usuario
    public String getUser() {
        return user;
    }

    // Setter para actualizar el nombre de usuario
    public void setUser(String user) {
        this.user = user;
    }

    // Getter para obtener la contraseña
    public String getPassword() {
        return password;
    }

    // Setter para actualizar la contraseña
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter para obtener el nombre de la base de datos
    public String getDbdname() {
        return dbdname;
    }

    // Setter para actualizar el nombre de la base de datos
    public void setDbdname(String dbdname) {
        this.dbdname = dbdname;
    }
}
