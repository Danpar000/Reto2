package com.example.demo2;

public class JugadorGana {
    private String idCategoria;
    private int idPuesto;
    private int idImporte;
    private String idNombre;
    private int idRangoF;
    private int idRangoI;

    public JugadorGana(String idCategoria, int idPuesto, int idImporte, String idNombre, int idRangoF, int idRangoI) {
        this.idCategoria = idCategoria;
        this.idPuesto = idPuesto;
        this.idImporte = idImporte;
        this.idNombre = idNombre;
        this.idRangoF = idRangoF;
        this.idRangoI = idRangoI;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdRangoI() {
        return idRangoI;
    }

    public void setIdRangoI(int idRangoI) {
        this.idRangoI = idRangoI;
    }

    public int getIdImporte() {
        return idImporte;
    }

    public void setIdImporte(int idImporte) {
        this.idImporte = idImporte;
    }

    public String getIdNombre() {
        return idNombre;
    }

    public void setIdNombre(String idNombre) {
        this.idNombre = idNombre;
    }

    public int getIdRangoF() {
        return idRangoF;
    }

    public void setIdRangoF(int idRangoF) {
        this.idRangoF = idRangoF;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    @Override
    public String toString() {
        return "JugadorGana{" +
                "idCategoria='" + idCategoria + '\'' +
                ", idRangoI=" + idRangoI +
                ", idImporte=" + idImporte +
                ", idNombre='" + idNombre + '\'' +
                ", idRangoF=" + idRangoF +
                ", idPuesto=" + idPuesto +
                '}';
    }
}
