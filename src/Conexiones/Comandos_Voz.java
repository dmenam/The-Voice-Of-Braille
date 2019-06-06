package Conexiones;

import Ventanas.Ayuda;
import Ventanas.Configuracion;
import Ventanas.Creditos;
import Ventanas.Inicio;
import javax.speech.*;
import javax.speech.recognition.*;
import java.io.FileReader;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Comandos_Voz extends ResultAdapter {

    static Recognizer recognizer;
    private String gst;
    private Inicio inicio;
    private boolean estado;

    public Comandos_Voz(Inicio inicio) {
        try {
            this.inicio = inicio;
            estado = false;
            recognizer = Central.createRecognizer(new EngineModeDesc(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Comandos_Voz.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EngineException ex) {
            Logger.getLogger(Comandos_Voz.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Comandos_Voz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciarComandos() {
        try {
            // Poner en marcha el reconocedor
            recognizer.allocate();

            // Cargar la gramática de un archivo y habilitarla
            FileReader grammar1 = new FileReader("comandos.txt");
            RuleGrammar rg = recognizer.loadJSGF(grammar1);
            rg.setEnabled(true);

            // Añadir el oyente para obtener resultados.
            recognizer.addResultListener(this);

            System.out.println("Empieze con los Comandos");
            recognizer.commitChanges();

            recognizer.requestFocus();
            recognizer.resume();
            estado = true;
        } catch (Exception e) {
            System.out.println("Exception en " + e.toString());
            e.printStackTrace();
            System.exit(0);
            estado = false;
        }
    }

    @Override
    public void resultAccepted(ResultEvent re) {
        try {
            Result res = (Result) (re.getSource());
            ResultToken tokens[] = res.getBestTokens();

            String args;
            args = "";
            for (int i = 0; i < tokens.length; i++) {
                gst = tokens[i].getSpokenText();
                args += gst + " ";
            }
            //------------------------------------------------------------------
            System.out.println(args);
            //EL resultado tiene un espacio al final
            args = args.trim();
            switch (args) {
                case "Encender impresora":
                    System.out.println("caso " + args);
                    inicio.hablar("Comando reconocido.");
                    inicio.setEstadoArduino(true);
                    args = "";
                    break;
                case "Apagar impresora":
                    System.out.println("caso " + args);
                    inicio.hablar("Comando reconocido. ");                    
                    inicio.setEstadoArduino(false);
                    args = "";
                    break;
                case "Imprimir archivo de texto":
                    System.out.println("caso " + args);
                    inicio.hablar("Comando reconocido. ");
                    inicio.imprimir();
                    args = "";
                    break;
                case "Desactivar comandos de voz":
                    System.out.println("caso " + args);
                    inicio.hablar("Comando reconocido. ");
                    if (estado) {
                        inicio.suspenderComandos();
                        String config[] = {FileManager.leerConfiguracion(0), "", FileManager.leerConfiguracion(2), "0"};
                        FileManager.escribirConfiguracion(config);
                    }
                    args = "";
                    break;
                case "Activar comandos de voz":
                    System.out.println("caso" + args);
                    inicio.hablar("Comando reconocido. ");
                    if (!estado) {
                        inicio.reaunudarComandos();
                    }
                    args = "";
                    break;
                case "Guardar archivo de texto":
                    System.out.println("caso " + args);
                    inicio.hablar("Comando reconocido. ");
                    inicio.guardarArchivo();
                    args = "";
                    break;
                case "Iniciar dictado por voz":
                    System.out.println("caso " + args);
                    inicio.hablar("Comando reconocido. ");
                    recognizer.releaseFocus();
                    //JOptionPane.showMessageDialog(null, "Comenzara el dictado...");
                    inicio.iniciarDictado();
                    inicio.contarCarcateres();
                    //JOptionPane.showMessageDialog(null, "Finalizo el dictado...");
                    recognizer.requestFocus();
                    args = "";
                    break;
                case "Leer texto introducido":
                    System.out.println("caso " + args);
                    inicio.hablar("Comando reconocido. ");
                    inicio.leerTexto();
                    args = "";
                    break;
                case "Salir de la aplicacion":
                    System.out.println("caso " + args);
                    inicio.hablar("Comando reconocido. ");
                    recognizer.deallocate();
                    args = "Hasta la proxima!";
                    inicio.vozSalir();
                    System.exit(0);
                    break;
            }
            //------------------------------------------------------------------
        } catch (Exception ex) {
            System.out.println("Ha ocurrido algo inesperado " + ex);
        }
    }

    public void setInicio(Inicio inicio) {
        this.inicio = inicio;
    }

    public void suspenerComandos() {
        recognizer.suspend();
        recognizer.releaseFocus();
        estado = false;
        System.out.println("Comandos suspendidos");
    }

    public void reaunudarComandos() {
        try {
            recognizer.requestFocus();
            recognizer.resume();
            estado = true;
            System.out.println("Comandos reaunudados");
        } catch (AudioException ex) {
            Logger.getLogger(Comandos_Voz.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EngineStateError ex) {
            Logger.getLogger(Comandos_Voz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void finalizarUsoComandos() {
        try {
            recognizer.suspend();
            recognizer.deallocate();
            estado = false;
            System.out.println("Comandos Finalizados");
        } catch (EngineException ex) {
            Logger.getLogger(Comandos_Voz.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EngineStateError ex) {
            Logger.getLogger(Comandos_Voz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean getEstadoComandos() {
        return estado;
    }
}
