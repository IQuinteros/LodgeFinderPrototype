package com.iquinteros.lodgefinder.models;

public class User {

    private int id;
    private String names;
    private String lastNames;
    private String email;
    private int rut;
    private int contactNumber;
    private int photoUrl;
    private boolean isBusiness;

    public User(int id, String names, String lastNames, String email, int rut, int contactNumber, int photoUrl, boolean isBusiness) {
        this.id = id;
        this.names = names;
        this.lastNames = lastNames;
        this.email = email;
        this.rut = rut;
        this.contactNumber = contactNumber;
        this.photoUrl = photoUrl;
        this.isBusiness = isBusiness;
    }

    public int getId() {
        return id;
    }

    public String getNombres() {
        return names;
    }

    public String getApellidos() {
        return lastNames;
    }

    public String getEmail() {
        return email;
    }

    public int getRut() {
        return rut;
    }

    public int getNumeroContacto() {
        return contactNumber;
    }

    public int getFoto() {
        return photoUrl;
    }

    public boolean isEmpresa() {
        return isBusiness;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombres(String names) {
        this.names = names;
    }

    public void setApellidos(String lastNames) {
        this.lastNames = lastNames;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public void setNumeroContacto(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setFoto(int photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setEmpresa(boolean isBusiness) {
        this.isBusiness = isBusiness;
    }

    @Override
    public String toString() {
        return getNombres() + " " + getApellidos() + "\n" + getRut();
    }

    public String toComplexString() {
        return  "ID: " + id +
                "\nNombre: '" + names + '\'' +
                "\nApellidos: '" + lastNames + '\'' +
                "\nEmail: '" + email + '\'' +
                "\nRut: " + rut +
                "\nNúmeroContacto: " + contactNumber +
                "\nFoto: " + photoUrl +
                "\nEmpresa: " + isBusiness;
    }
}
