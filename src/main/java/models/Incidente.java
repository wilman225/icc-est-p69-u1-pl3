package models;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Incidente implements Comparable<Incidente> {

    private final String codigo;
    private final int severidad;
    private final int minutosRestantes;
    private final List<ServicioAfectado> servicios;
    private final LocalDateTime registradoEn;

    public Incidente(String codigo, int severidad, int minutosRestantes,
            List<ServicioAfectado> servicios, LocalDateTime registradoEn) {
        Objects.requireNonNull(codigo, "codigo");
        
        if (!codigo.matches("[A-Za-z0-9-]+") || severidad < 1 || severidad > 4) {
            throw new IllegalArgumentException("Código o severidad inválidos");
        }
        this.codigo = codigo;
        this.severidad = severidad;
        this.minutosRestantes = minutosRestantes;
        this.servicios = List.copyOf(servicios);
        this.registradoEn = Objects.requireNonNull(registradoEn, "registradoEn");
        
    }

    public String getCodigo() {
        return codigo;
    }

    public int getSeveridad() {
        return severidad;
    }

    public int getMinutosRestantes() {
        return minutosRestantes;
    }

    public List<ServicioAfectado> getServicios() {
        return servicios;
    }

    public LocalDateTime getRegistradoEn() {
        return registradoEn;

    }

    private int getCantidadServiciosAfectados() {
        return servicios.size();
    }

    private long getCantidadUsuariosAfectados() {
        long sumUserA = 0L;
        for (ServicioAfectado servicioAfectado : servicios) {
            sumUserA += servicioAfectado.usuariosAfectados();
        }
        return sumUserA;
    }

    

    @Override
    public int compareTo(Incidente otro) {
        Objects.requireNonNull(otro, "otro");

        Comparator<Incidente> comparador
                = Comparator.comparingInt(Incidente::getSeveridad)
                        .thenComparingInt(Incidente::getMinutosRestantes)
                        // CANTIDAD DE SERVICIOS AFECTADOS servicios.size()
                        

                        //OTRO METODO, PERO TUVE QUE CREAR UN METODO PRIVADO getCantidadServiciosAfectados()    
                        .thenComparing(Comparator.comparingInt(Incidente::getCantidadServiciosAfectados).reversed())
                        .thenComparing(Comparator.comparingLong(Incidente::getCantidadUsuariosAfectados).reversed())

                    //.thenComparing(Comparator.comparingInt(Incidente::usuariosAfectadosTotal).reversed());
                    .thenComparing(Incidente::getRegistradoEn)
                    .thenComparing(Incidente::getCodigo, String.CASE_INSENSITIVE_ORDER);

        return comparador.compare(this, otro);

    }

    @Override
    public String toString() {
        return "%s | severidad=%d | minutos=%d | servicios=%d | fecha=%s"
                .formatted(codigo, severidad, minutosRestantes, servicios.size(), registradoEn);
    }
}
