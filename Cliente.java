public class Cliente {
    private int turno;
    private String nombre;
    private String motivo;
    private int caja;
    private String estado;

    public Cliente() {
    }

    public int getTurno() {
        return turno;
    }
    public void setTurno(int turno) {
        this.turno = turno;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public int getCaja() {
        return caja;
    }
    public void setCaja(int caja) {
        this.caja = caja;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Turno: " + turno + " | Cliente: " + nombre + " | Motivo: " + motivo
                + " | Caja: " + caja + " | Estado: " + estado;
    }
}