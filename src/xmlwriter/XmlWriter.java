/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlwriter;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


/**
 * Esta Clase contiene un algoritmo que permite crear documentos en formato xml y guardarlos en el directorio acutal de trabajo.
 *@version 1.0
 *@author Cristian Camilo Isaza
 */

//Donde se deben inicializar las variables constructor?
//Para Que sirve todo lo que viene en la carpeta jdom?? solo se usa el jar necesario

public class XmlWriter {

    /**
     * Variables Globales de la aplicación.
     */
    final static Logger LOGGER = Logger.getLogger("");
    private static final BufferedReader read = new BufferedReader(new InputStreamReader(System.in)); 
    
    /**
     * Metodo principal que funciona como punto de entrada de la aplicación.
     * @param args 
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // <editor-fold defaultstate="collapsed" desc="Codigo pedido pero incompleto"> 
//            String attribName = "";
//            String attribContent = "";
//            int attribNumber = 0;        
//            System.out.print("Número de aributos a crear en su archivo XML: ");
//            attribNumber = Integer.valueOf(read.next());
//            
//            for (int i = 0; i < attribNumber; i++) {
//                System.out.print("Nombre del atributo "+ i +": ");
//                attribName = read.next();
//                
//                System.out.print("Digite el contenido del atributo "+ attribName +": ");
//                attribContent = read.next();
//                
//                Element child = new Element(attribName).setText(attribContent);
//                doc.getRootElement().addContent(child);
//            }
          // </editor-fold> 
        System.err.println("\t\tConstructor XML\n\tFundamentos de Investigación\n\t\t26/08/2016\n");
        Element root = createRoot();
        Document doc = buildDocument(root);
        boolean saved = saveDocument(doc);
        
        String msg = saved?"Archivo guardado con éxito":"Ocurrio un Error al guardar el archivo";
        System.err.println(msg);    
        
    }
    
    /**
     * Este método es utilizado por el método buildDocument para construir el documento xml de forma recursiva, agregando un nodo a la vez.
     * @param padre Nodo inicialmente vácio al cual se le agregara un contenido(texto plano) o bien uno o mas subelementos hijos.
     * @return padre Nodo que contiene uno o mas subelementos o contenido almacenado en texto plano.
     * 
     */
    private static Element addElementsTo(Element padre) {
        
        try {
            System.out.print("Nombre del nuevo elemento que será anidado en <"+padre.getName()+">: ");
            String elementName = read.readLine();
            elementName = validateElementName(elementName);
            Element newElement = new Element(elementName);
            
            String continua;
            do {

                System.out.print("Desea agregar más elementos hijos a <" + elementName +">?\ts/n: ");
                continua = read.readLine().trim().toLowerCase();
                if("s".equals(continua)){

                    addElementsTo(newElement);

                }else if("n".equals(continua)){

                    int numHijos = newElement.getChildren().size();
                    if (numHijos==0) {
                        System.out.print("Digite el contenido del elemento <" + elementName +">:\t");
                        String content = read.readLine();
                        newElement.setText(content);
                    }

                }else{

                    System.err.println("Porfavor elija una opción válida, para ello digite 's' para elegir Sí o 'n' para elejir No.");
                    continua = "s";
                }
            } while (continua.equals("s"));
            padre.addContent(newElement);
            
            return padre;
        } catch (IOException ex) {
            Logger.getLogger(XmlWriter.class.getName()).log(Level.SEVERE, "Error de Entrada/Salida - addElemensTo", ex);
            return null;
        }
    }

    /**
     * Es usado para crear el Nodo raiz del documento xml.
     * @return root Retorna el elemento raiz del documento.
     */
    private static Element createRoot() {
        
        try {
            System.out.print("Ingrese el nombre del elemento raíz: ");
            String strRoot = read.readLine();
            strRoot = validateElementName(strRoot);
            return(new Element(strRoot));
        } catch (IOException ex) {
            Logger.getLogger(XmlWriter.class.getName()).log(Level.SEVERE, "Error de Entrada/Salida - createRoot", ex);
            return null;
        }
    }

    /**
     * Crea un documento y comienza el algoritmo recursivo addElements que construye el documento de nodo en nodo.
     * @param root Elemento que será usado como raiz del documento.
     * @return Document documento xml formado después de la interaccion con el usuario.
     */
    private static Document buildDocument(Element root) {
        Document doc = new Document(addElementsTo(root));
        return doc;
    }
    
    /**
     * Es usado para leer el nombre de el archivo que posteriormente será escrito en un archivo con extension .xml
     * @param doc Documento Generado despues de la interaccion con el usuario y que será escrito en un archivo con extension xml.
     * @return booleano que indica si encontraron errores al momento de escribir el archivo en el disco. Devuelve true si el archivo se guardo con exito y false de lo contrario.
     */
    private static boolean saveDocument(Document doc) {
        
        try {
            String fileName = "";
            System.out.print("Nombre del archivo XML (sin la extensión): ");
            fileName = read.readLine();
            
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(fileName + ".xml"));
            return (true);
        } catch (IOException ex) {
            Logger.getLogger(XmlWriter.class.getName()).log(Level.SEVERE, "Error al escribir el archivo - saveDocument", ex);
            return (false);
        }
                
    }
    
    /**
     * Este método es usado para validar y corregir el nombre que será usado como nombre del atributo,
     * para lo cual reemplaza los espacios por el caracter underline y transforma dicho nombre en mayusculas.
     * @param candidateName Nombre ingresado por el usuario y que es candidato a ser el nombre de un atributo. ej: "nombre de atributo"
     * @return Nombre bien formado. ej: <PRE> "NOMBRE_DE_ATRIBUTO" </PRE>
     */
    private static String validateElementName(String candidateName){
        String correctName = candidateName.replace(" ", "_");
        correctName = correctName.toUpperCase();
        return correctName;
    }
    
    
}
