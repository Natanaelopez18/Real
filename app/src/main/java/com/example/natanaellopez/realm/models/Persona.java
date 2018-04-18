package com.example.natanaellopez.realm.models;

import io.realm.RealmObject;

public class Persona extends RealmObject {

    String Nombre;
    int Edad;

//El método toString nos permite mostrar la información completa de un objeto, es decir, el valor de sus atributos.
    @Override
    public String toString() {
        return "Persona{" +
                "Nombre='" + Nombre + '\'' +
                ", Edad=" + Edad +
                '}';
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }
}
