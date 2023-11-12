import modelos.Alumno;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static ArrayList<Alumno> alumnos = new ArrayList<>();


    public static void main(String[] args) {

        try {
            cargarInfoXml();
            menu();



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


    }

    private static void cargarInfoXml() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxpf = SAXParserFactory.newInstance();
        SAXParser saxParser = saxpf.newSAXParser();
        XMLReader reader = saxParser.getXMLReader();

        reader.setContentHandler(new Manejador(alumnos));
        reader.parse(new InputSource(new FileInputStream("src/escuela.xml")));
    }

    private static void mostrarLista() {
        System.out.println("--- Resultado ---");
        for (Alumno a: alumnos){
            System.out.println(a.toString());
            System.out.println("--------------");
        }
    }

    private static void menu() {
        String respuesta = "";
        System.out.println("Elige una opción:");

        do {
            System.out.println("1. Mostrar la lista de alumnos");
            System.out.println("2. Añadir un alumno");
            System.out.println("3. Guardar la información en el archivo .xml");
            System.out.println("4. Salir");
            try {
                respuesta = br.readLine();
            } catch (IOException e) {
                System.out.println("Error al leer la respuesta. "+e);
            }

            switch (respuesta){
                case "1"-> mostrarLista();
                case "2"-> addAlumno();
                case "3"-> guardarDOM();
            }

        }while (!respuesta.equals("4"));



    }

    private static void guardarDOM() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            //Creamos raiz
            Element raiz = doc.createElement("escuela");
            doc.appendChild(raiz);
            raiz.appendChild(doc.createTextNode("\n"));

            for (Alumno a: alumnos){

                //Creamos obj alumno
                Element alumnooo = doc.createElement("alumno");
                //raiz.appendChild(doc.createTextNode("\n"));

                //Añadimos atributo ID
                Attr id = doc.createAttribute("id");
                id.setValue(a.getId());
                alumnooo.setAttributeNode(id);
              //  raiz.appendChild(doc.createTextNode("\n"));

                //Añadimos sus otros atributos
                Element nombre = doc.createElement("nombre");
                nombre.setTextContent(a.getNombre());
                alumnooo.appendChild(nombre);
              //  raiz.appendChild(doc.createTextNode("\n"));

                Element apell = doc.createElement("apellidos");
                apell.setTextContent(a.getApellidos());
                alumnooo.appendChild(apell);
               // raiz.appendChild(doc.createTextNode("\n"));

                Element edad = doc.createElement("edad");
                edad.setTextContent(String.valueOf(a.getEdad()));
                alumnooo.appendChild(edad);
             //   raiz.appendChild(doc.createTextNode("\n"));

                Element estado = doc.createElement("estado");
                estado.setTextContent(a.getEstado());
                alumnooo.appendChild(estado);
                raiz.appendChild(doc.createTextNode("\n"));

                raiz.appendChild(alumnooo);
            }

            //GUARDAMOS LA INFO
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource fuente = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/escuela.xml"));//No usar el new File, ya q hasta el programa no acaba no lo ejecuta
            transformer.transform(fuente,result);

            System.out.println("La información se ha guardado con éxito");

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }


    }

    private static void addAlumno() {
        String nombre, apell, id, estado, edad;
        System.out.println("Introduce la informacion del alumno");
        try {
            System.out.println("Id:");
            id = br.readLine();
            System.out.println("Nombre:");
            nombre = br.readLine();
            System.out.println("Apellidos:");
            apell = br.readLine();
            System.out.println("Edad:");
            edad = br.readLine();
            System.out.println("Estado:     (activo/inactivo)");
            estado = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (nombre.isEmpty() || apell.isEmpty() || id.isEmpty() || estado.isEmpty() || edad.isEmpty()){
            System.out.println("Error: todos los campos deben estar rellenados");
        }else {
            Alumno a = new Alumno(id,nombre,apell,Integer.parseInt(edad),estado);
            alumnos.add(a);
            System.out.println("El alumno se ha añadido con éxito");
        }

    }
}