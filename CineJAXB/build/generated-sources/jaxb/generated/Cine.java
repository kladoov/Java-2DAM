
package generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pelicula"
})
@XmlRootElement(name = "cine")
public class Cine {

    protected List<Cine.Pelicula> pelicula;

    public List<Cine.Pelicula> getPelicula() {
        if (pelicula == null) {
            pelicula = new ArrayList<Cine.Pelicula>();
        }
        return this.pelicula;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "titulo",
        "sala",
        "precio"
    })
    public static class Pelicula {

        @XmlElement(required = true)
        protected String titulo;
        protected int sala;
        protected float precio;
        @XmlAttribute(name = "calificacion")
        protected String calificacion;

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String value) {
            this.titulo = value;
        }

        public int getSala() {
            return sala;
        }

        public void setSala(int value) {
            this.sala = value;
        }

        public float getPrecio() {
            return precio;
        }

        public void setPrecio(float value) {
            this.precio = value;
        }

        public String getCalificacion() {
            return calificacion;
        }

        public void setCalificacion(String value) {
            this.calificacion = value;
        }

    }

}
