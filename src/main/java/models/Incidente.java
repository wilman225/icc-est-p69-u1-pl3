package models;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class Incidente implements Comparable<Incidente> {

    private final String codigo;
    private final int severidad;
    private final int minutosRestantes;
    private final List<ServicioAfectado> servicios;
    private final LocalDateTime registradoEn;

    public Incidente(String codigo, int severidad, int minutosRestantes,
            List<ServicioAfectado> servicios, LocalDateTime registradoEn) {
        // Objects.requireNonNull(codigo, "codigo");
        // if (!codigo.matches("[A-Za-z0-9-]+") || severidad < 1 || severidad > 4) {
        // throw new IllegalArgumentException("Código o severidad inválidos");
        // }
        this.codigo = codigo;
        this.severidad = severidad;
        this.minutosRestantes = minutosRestantes;
        this.servicios = List.copyOf(servicios);
        // this.registradoEn = Objects.requireNonNull(registradoEn, "registradoEn");
        this.registradoEn = registradoEn;
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

    private int getCantidadUsuariosAfectados() {
        int sumUserA = 0;
        for (ServicioAfectado servicioAfectado : servicios) {
            sumUserA += servicioAfectado.usuariosAfectados();
        }
        return sumUserA;
    }

    

    @Override
    public int compareTo(Incidente otro) {

        Comparator<Incidente> comparador
                = Comparator.comparingInt(Incidente::getSeveridad)
                        .thenComparingInt(Incidente::getMinutosRestantes)
                        // CANTIDAD DE SERVICIOS AFECTADOS servicios.size()
                        //.thenComparing((Incidente i)-> i.getServicios().size()).reversed();

                        //OTRO METODO, PERO TUVE QUE CREAR UN METODO PRIVADO getCantidadServiciosAfectados()    
                        .thenComparingInt(Incidente::getCantidadServiciosAfectados).reversed()

                        .thenComparing((Incidente i) -> {
                            int sumUserA=0;
                            for (ServicioAfectado servicioAfectado : i.getServicios()) {
                                sumUserA += servicioAfectado.usuariosAfectados();
                            }
                            return sumUserA;
                        })

                    //.thenComparing(Comparator.comparingInt(Incidente::usuariosAfectadosTotal).reversed());
                    .thenComparing(Incidente::getRegistradoEn)
                    .thenComparing(Incidente::getCodigo);

        return comparador.compare(this, otro);

    }

    @Override
    public String toString() {
        return "%s | severidad=%d | minutos=%d | servicios=%d | fecha=%s"
                .formatted(codigo, severidad, minutosRestantes, servicios.size(), registradoEn);
    }
}
