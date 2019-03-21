package Conexiones;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileManager {

    private static final String EMPTY_STRING = "";

    private static final String fileSeparator = System.getProperty("file.separator");

    private static final String lineTerminator = System.getProperty("line.separator");

    private File archivo;
    private File directorioPorDefecto;

    private JFileChooser chooser;
    private final FileNameExtensionFilter filter;

    //Fragmentacion de 35 lineas
    private FileWriter fileWriter;
    private StringTokenizer saltoLinea;
    private String documento;
    private int primerc;
    private int ultimoc;
    //--------------------------------------------------------------------------

    public FileManager() {
        filter = new FileNameExtensionFilter("Archivos de TEXTO", "txt", "text");
        chooser = new JFileChooser();

        primerc = 0;
        ultimoc = 35;
    }

    public File abrirAchivo() throws FileNotFoundException, IOException {
        chooser.setFileFilter(filter);
        int respuesta = chooser.showOpenDialog(null);

        if (respuesta == JFileChooser.APPROVE_OPTION) {
            archivo = chooser.getSelectedFile();
        } else {
            return null;
        }
        return archivo;
    }

    public String leerArchivo(File inFile) throws FileNotFoundException, IOException {
        String line;
        StringBuilder document = new StringBuilder(EMPTY_STRING);

        FileReader fileReader = new FileReader(inFile);
        BufferedReader bufReader = new BufferedReader(fileReader);

        while (true) {
            line = bufReader.readLine();

            if (line == null) {
                break;
            }
            document.append(line + lineTerminator);
        }
        return document.toString();
    }

    public void guardarArchivoComo(String datos) throws IOException {
        chooser = new JFileChooser();
        int reply = chooser.showSaveDialog(null);
        if (reply == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile().getName().endsWith(".txt")) {
                guardarArchivo(chooser.getSelectedFile().getAbsolutePath(), datos);
            } else {
                guardarArchivo(chooser.getSelectedFile().getAbsolutePath() + ".txt", datos);
            }

        }
    }

    public void guardarArchivo(String filename, String datos) throws IOException {
        File outFile = new File(filename);
        archivo = outFile;
        //descomposicion(true, datos);
        FileOutputStream outFileStream = new FileOutputStream(outFile);
        try (PrintWriter outStream = new PrintWriter(outFileStream)) {
            outStream.print(documento);
        }
        /*try {
            fileWriter = new FileWriter(outFile);
            descomposicion(true, datos);
            fileWriter.write(documento, 0, documento.length()); //Obtener los bytes de la cadena, leer desde el byte 0 toda la cadena    
            primerc = 0;
            ultimoc = 35;
        } catch (IOException ex) {
            System.out.println("Error al guardar el archivo");
        }

        try //Comprobar si lo pudo abrir
        {
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException ex) {
            System.out.println("Error al cerrar el archivo");
        }*/
    }

    public File elegirDirectorio() {
        File directorio;
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int respuesta = chooser.showOpenDialog(null);

        if (respuesta == JFileChooser.APPROVE_OPTION) {
            directorio = chooser.getSelectedFile();
        } else {
            return null;
        }
        return directorio;
    }

    public static boolean escribirConfiguracion(String[] datos) {
        File temp = new File("config.txt");
        FileOutputStream outFileStream = null;
        try {
            outFileStream = new FileOutputStream(temp);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (PrintWriter outStream = new PrintWriter(outFileStream)) {
            for (int i = 0; i < datos.length; i++) {
                outStream.print(datos[i] + "\n");
            }
        }
        System.out.println(temp.getAbsolutePath());
        return true;
    }

    public static String leerConfiguracion(int dato) {
        String[] line = new String[4];

        File inFile = new File("config.txt");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(inFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader bufReader = new BufferedReader(fileReader);

        for (int i = 0; i < line.length; i++) {
            try {
                line[i] = bufReader.readLine();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        switch (dato) {
            case 0:
                //Ruta de guardado
                return line[0];
            case 1:
                //Puerto Com Predeterminado
                return line[1];
            case 2:
                //Bluetooth activado/desactivado
                return line[2];
            case 3:
                //Comandos de Voz
                return line[3];
        }
        return null;
    }

    public void setDirectorioPorDefecto(String directorio) {
        File temp = new File(directorio);
        chooser.setCurrentDirectory(temp);
    }

    public void setDirectorioPorDefecto(File destino) {
        chooser.setCurrentDirectory(destino);
    }

    public File getDirectorioPorDefecto() {
        return chooser.getCurrentDirectory();
    }

    public File getArchivo() {
        return archivo;
    }

    public void descomposicion(boolean bandera, String textoArea) {
        String linea = "";
        String lineaArchivo = "";
        boolean ban = bandera;
        documento = "";
        while (ban == true) {
            if (textoArea.length() < ultimoc) {
                System.out.println(textoArea.length() + " " + ultimoc);
                ultimoc = textoArea.length();
                System.out.println(primerc + " " + ultimoc);

                linea = textoArea.substring(primerc, ultimoc);// Leer desde el primer caracter hasta el 35
                lineaArchivo = lineaArchivo + " " + linea;
                System.out.println("Linea:" + lineaArchivo);
                //JOptionPane.showMessageDialog(null, "Se ha llegado al final de la linea");

                documento = documento + lineaArchivo + "\n";
                System.out.println("El doc es:" + documento);
                ban = false;
            } else {
                linea = textoArea.substring(primerc, ultimoc);// Leer desde el primer caracter hasta el 35
                StringTokenizer espacio = new StringTokenizer(linea, " ");  //Usar como token un espacio

                int cantidad = espacio.countTokens();

                System.out.println("Leyedo desde:" + primerc + " hasta " + ultimoc);

                for (int i = 1; i < cantidad; i++) {
                    lineaArchivo = lineaArchivo + " " + espacio.nextToken();
                }

                documento = documento + lineaArchivo + "\n";
                System.out.println("Linea:" + lineaArchivo);
                System.out.println("Tamanio:" + lineaArchivo.length());

                primerc += lineaArchivo.length();
                ultimoc = primerc + 35;
                lineaArchivo = "";
            }
        }
    }

}
