package Conexiones;
/*
import Ventanas.Ayuda;
import Ventanas.Inicio;
import javax.speech.*;
import javax.speech.recognition.*;
import java.io.FileReader;
import java.util.Locale;

public class Voz extends ResultAdapter {

    static Recognizer recognizer;
    String gst;
    private Inicio inicio;

    public Voz(Inicio inicio) {
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
            if(gst.equals("Ayuda")) {
                Ayuda a = new Ayuda(inicio);
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

            recognizer.addResultListener(new Voz());

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
}*/