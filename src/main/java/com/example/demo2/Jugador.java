package com.example.demo2;

import java.util.Objects;

public class Jugador {
    private int rangoI;
    private String titulo;
    private String nomjug;
    private String federacion;
    private int elo;
    private int nac;
    private int fide_id;
    private int id_nac;
    private String club;
    private boolean hotel;
    private boolean cv;
    private Integer rangoF;
    private String tipotorneo = "";
    private boolean descalificado;


    public Jugador(int rangoI, String titulo, String nomjug, String federacion, int elo, int nac, int fide_id, int id_nac, String club, boolean hotel, boolean cv, Integer rangoF, String tipotorneo, boolean descalificado) {
        this.rangoI = rangoI;
        this.titulo = titulo;
        this.nomjug = nomjug;
        this.federacion = federacion;
        this.elo = elo;
        this.nac = nac;
        this.fide_id = fide_id;
        this.id_nac = id_nac;
        this.club = club;
        this.hotel = hotel;
        this.cv = cv;
        this.rangoF = rangoF;
        this.tipotorneo = tipotorneo;
        this.descalificado = descalificado;
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

    public String getFederacion() {
        return federacion;
    }

    public void setFederacion(String federacion) {
        this.federacion = federacion;
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

    public Integer getRangoF() {
        return rangoF;
    }

    public void setRangoF(Integer rangoF) {
        this.rangoF = rangoF;
    }

    public String getTipotorneo() {
        return tipotorneo;
    }

    public void setTipotorneo(String tipotorneo) {
        this.tipotorneo = tipotorneo;
    }

    public boolean isDescalificado() {
        return descalificado;
    }

    public void setDescalificado(boolean descalificado) {
        this.descalificado = descalificado;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "rangoI=" + rangoI +
                ", titulo='" + titulo + '\'' +
                ", nomjug='" + nomjug + '\'' +
                ", federacion='" + federacion + '\'' +
                ", elo=" + elo +
                ", nac=" + nac +
                ", fide_id=" + fide_id +
                ", id_nac=" + id_nac +
                ", club='" + club + '\'' +
                ", hotel=" + hotel +
                ", cv=" + cv +
                ", rangoF=" + rangoF +
                ", tipotorneo='" + tipotorneo + '\'' +
                ", descalificado=" + descalificado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return rangoI == jugador.rangoI && elo == jugador.elo && nac == jugador.nac && fide_id == jugador.fide_id && id_nac == jugador.id_nac && hotel == jugador.hotel && cv == jugador.cv && descalificado == jugador.descalificado && Objects.equals(titulo, jugador.titulo) && Objects.equals(nomjug, jugador.nomjug) && Objects.equals(federacion, jugador.federacion) && Objects.equals(club, jugador.club) && Objects.equals(rangoF, jugador.rangoF) && Objects.equals(tipotorneo, jugador.tipotorneo);
    }
}
