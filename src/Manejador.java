import modelos.Alumno;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class Manejador extends DefaultHandler {
    private String valor;
    final ArrayList<Alumno> alumnos;
    private Alumno alumno;


    public Manejador(ArrayList<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        valor = null; //Limpiamos la variable
        //comprobamos si la etiqueta coincide con lo q buscamos
        if (qName.equals("alumno")){
            alumno = new Alumno();
            String id = attributes.getValue("id");//Obtenemos el id
            alumno.setId(id);//lo añadimos al objeto
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        valor = new String(ch,start,length); //Guardamos el texto q recogemos temporalmente
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //Segun la etiqueta que tengamos al cerrar, guardamos atributo dnd toca
        switch (qName){
            case "nombre"-> alumno.setNombre(valor);
            case "apellidos"-> alumno.setApellidos(valor);
            case "edad"-> alumno.setEdad(Integer.parseInt(valor));
            case "estado"-> alumno.setEstado(valor);
            case "alumno"-> alumnos.add(alumno);
        }
    }


}
