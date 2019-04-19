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

    private JFileChooser chooser;
    private final FileNameExtensionFilter filter;

    private static final String config[] = {"C:/Users/Luis/Documents", "", "0", "0"};

    //--------------------------------------------------------------------------

    /*
    * config[0] = Ruta de guardado predeterminada
    * config[1] = puerto COM predeterminado
    * config[2] = estado del bluetooth
    * config[3] = comandos de voz (activado/desactivado)
     */
    public FileManager() {
        filter = new FileNameExtensionFilter("Archivos de TEXTO", "txt", "text");
        chooser = new JFileChooser();
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
            outStream.print(datos);
            //documento = "";
        }
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
        if (!temp.exists()) {
            try {
                temp.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        if (!inFile.exists()) {
            try {
                inFile.createNewFile();
                escribirConfiguracion(config);
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
    
}
