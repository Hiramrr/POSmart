package controllers;

public class Empleado {
    private int id;
    private String nombre;
    private String contraseña;
    private String nombreCompleto;
    private String rol;
    private String telefono;
    private String ciudad;
    private String direccion;

    // Constructor
    public Empleado(int id, String nombre, String contraseña, String nombreCompleto, String rol, String telefono, String ciudad, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getContraseña() { return contraseña; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getRol() { return rol; }
    public String getTelefono() { return telefono; }
    public String getCiudad() { return ciudad; }
    public String getDireccion() { return direccion; }
}

