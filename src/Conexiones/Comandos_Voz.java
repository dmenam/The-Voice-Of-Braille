package Conexiones;

import Ventanas.Ayuda;
import Ventanas.Inicio;
import javax.speech.*;
import javax.speech.recognition.*;
import java.io.FileReader;
import java.util.Locale;

public class Comandos_Voz extends ResultAdapter {

    static Recognizer recognizer;
    String gst;
    private Inicio inicio;

    public Comandos_Voz() {
        this.inicio = inicio;
        try {
            recognizer = Central.createRecognizer(new EngineModeDesc(Locale.ROOT));
            recognizer.allocate();

            FileReader grammar1 = new FileReader("comandos.txt");

            RuleGrammar rg = recognizer.loadJSGF(grammar1);
            rg.setEnabled(true);

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

            String args[] = new String[1];
            args[0] = "";
            for (int i = 0; i < tokens.length; i++) {
                gst = tokens[i].getSpokenText();
                args[0] += gst + " ";
                System.out.print(gst + " ");
            }
            System.out.println();
            //------------------------------------------------------------------
            if(gst.equals("Inicio")) {
                if(inicio.getAyuda() != null) {
                    inicio.cerrarAyuda();
                }
                if(inicio.getConfiguracion()!= null) {
                    inicio.cerrarConfiguracion();
                }
                if(inicio.getCreditos()!= null) {
                    inicio.cerrarCreditos();
                }
            } else {
                recognizer.suspend();
                //Lee.main(args);
                recognizer.resume();
            }
            //------------------------------------------------------------------
            if(gst.equals("Encender impresora")) {
                
            } else {
                recognizer.suspend();
                //Lee.main(args);
                recognizer.resume();
            }
            //------------------------------------------------------------------
            if(gst.equals("Apagar Impresora")) {
                
            } else {
                recognizer.suspend();
                //Lee.main(args);
                recognizer.resume();
            }
            //------------------------------------------------------------------
            if(gst.equals("Imprimir")) {
                
            }
            //------------------------------------------------------------------
            if(gst.equals("Cancelar")) {
                
            } else {
                recognizer.suspend();
                //Lee.main(args);
                recognizer.resume();
            }
            //------------------------------------------------------------------
            if(gst.equals("Guardar")) {
                
            } else {
                recognizer.suspend();
                //Lee.main(args);
                recognizer.resume();
            }
            //------------------------------------------------------------------
            if(gst.equals("Leer Texto")) {
                
            } else {
                recognizer.suspend();
                //Lee.main(args);
                recognizer.resume();
            }
            //------------------------------------------------------------------
            if(gst.equals("Ayuda")) {
                if(inicio.getAyuda() == null) {
                    Ayuda a = new Ayuda(inicio);
                } else {
                    inicio.cerrarAyuda();
                }
            } else {
                recognizer.suspend();
                //Lee.main(args);
                recognizer.resume();
            }
            //------------------------------------------------------------------
            if (gst.equals("Salir")) {
                recognizer.deallocate();
                args[0] = "Hasta la proxima!";
                System.out.println(args[0]);
                //Lee.main(args);
                System.exit(0);
            } else {
                recognizer.suspend();
                //Lee.main(args);
                recognizer.resume();
            }
            //------------------------------------------------------------------
        } catch (Exception ex) {
            System.out.println("Ha ocurrido algo inesperado " + ex);
        }
    }

    /*public void primero() {
        try {
            recognizer = Central.createRecognizer(new EngineModeDesc(Locale.ROOT));
            recognizer.allocate();

            FileReader grammar1 = new FileReader("comandos.txt");

            RuleGrammar rg = recognizer.loadJSGF(grammar1);
            rg.setEnabled(true);

            recognizer.addResultListener(new Comandos_Voz());

            System.out.println("Empieze Dictado");
            recognizer.commitChanges();

            recognizer.requestFocus();
            recognizer.resume();
        } catch (Exception e) {
            System.out.println("Exception en " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }*/
}