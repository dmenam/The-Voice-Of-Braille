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

public class Comandos_Voz extends ResultAdapter {

    static Recognizer recognizer;
    private String gst;
    private Inicio inicio;

    public Comandos_Voz() {
        iniciarComandos();
    }

    public void iniciarComandos() {
        try {
            recognizer = Central.createRecognizer(new EngineModeDesc(Locale.ROOT));
            // Poner en marcha el reconocedor
            recognizer.allocate();

            // Cargar la gramática de un archivo y habilitarla
            FileReader grammar1 = new FileReader("comandos.txt");
            RuleGrammar rg = recognizer.loadJSGF(grammar1);
            rg.setEnabled(true);

            // Añadir el oyente para obtener resultados.
            recognizer.addResultListener(this);

            System.out.println("Empieze Dictado");
            recognizer.commitChanges();

            recognizer.requestFocus();
            recognizer.resume();
        } catch (Exception e) {
            System.out.println("Exception en " + e.toString());
            e.printStackTrace();
            System.exit(0);
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
                    break;
                case "Apagar impresora":
                    System.out.println("caso " + args);
                    break;
                case "Imprimir texto":
                    System.out.println("caso " + args);
                    inicio.imprimir();
                    break;
                case "Desactivar comandos de voz":
                    System.out.println("caso " + args);
                    finalizarUsoComandos();
                    System.out.println("Comandos desactivados");
                    break;
                case "Guardar texto":
                    System.out.println("caso " + args);
                    inicio.guardarArchivo();
                    break;
                case "Iniciar dictado":
                    System.out.println("caso " + args);
                    break;
                case "Finalizar dictado":
                    System.out.println("caso " + args);
                    break;
                case "Salir":
                    System.out.println("caso " + args);
                    recognizer.deallocate();
                    args = "Hasta la proxima!";
                    System.out.println(args);
                    //Lee.main(args);
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
    }

    public void reaunudarComandos() {
        try {
            recognizer.resume();
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
        } catch (EngineException ex) {
            Logger.getLogger(Comandos_Voz.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EngineStateError ex) {
            Logger.getLogger(Comandos_Voz.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
}
