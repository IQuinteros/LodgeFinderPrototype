package com.iquinteros.lodgefinder.models;

public class User {

    private int id;
    private String nombres;
    private String apellidos;
    private String email;
    private int rut;
    private int numeroContacto;
    private int foto;
    private boolean empresa;

    public User(int id, String nombres, String apellidos, String email, int rut, int numeroContacto, int foto, boolean esEmpresa) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.rut = rut;
        this.numeroContacto = numeroContacto;
        this.foto = foto;
        this.empresa = empresa;
    }

    public int getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public int getRut() {
        return rut;
    }

    public int getNumeroContacto() {
        return numeroContacto;
    }

    public int getFoto() {
        return foto;
    }

    public boolean isEmpresa() {
        return empresa;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public void setNumeroContacto(int numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public void setEmpresa(boolean empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", rut=" + rut +
                ", numeroContacto=" + numeroContacto +
                ", foto=" + foto +
                ", Empresa=" + empresa +
                '}';
    }
}
