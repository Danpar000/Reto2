package com.example.demo2;

import javafx.fxml.FXML;

import javax.swing.table.TableColumn;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jugador {
    private int rangoI;
    private String titulo;
    private String nomjug;
    private String nacionalidad;
    private int elo;
    private int nac;
    private int fide_id;
    private int id_nac;
    private String club;
    private boolean hotel;
    private boolean cv;
    private int rangoF;
    private enum tipotorneo{A, B};


    public Jugador(int rangoI, String titulo, String nomjug, String nacionalidad, int elo, int nac, int fide_id, int id_nac, String club, boolean hotel, boolean cv, int rangoF) {
        this.rangoI = rangoI;
        this.titulo = titulo;
        this.nomjug = nomjug;
        this.nacionalidad = nacionalidad;
        this.elo = elo;
        this.nac = nac;
        this.fide_id = fide_id;
        this.id_nac = id_nac;
        this.club = club;
        this.hotel = hotel;
        this.cv = cv;
        this.rangoF = rangoF;
    }

    public int getRangoI() {
        return rangoI;
    }

    public void setRangoI(int rangoI) {
        this.rangoI = rangoI;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNomjug() {
        return nomjug;
    }

    public void setNomjug(String nomjug) {
        this.nomjug = nomjug;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getNac() {
        return nac;
    }

    public void setNac(int nac) {
        this.nac = nac;
    }

    public int getFide_id() {
        return fide_id;
    }

    public void setFide_id(int fide_id) {
        this.fide_id = fide_id;
    }

    public int getId_nac() {
        return id_nac;
    }

    public void setId_nac(int id_nac) {
        this.id_nac = id_nac;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public boolean isHotel() {
        return hotel;
    }

    public void setHotel(boolean hotel) {
        this.hotel = hotel;
    }

    public boolean isCv() {
        return cv;
    }

    public void setCv(boolean cv) {
        this.cv = cv;
    }

    public int getRangoF() {
        return rangoF;
    }

    public void setRangoF(int rangoF) {
        this.rangoF = rangoF;
    }
}
