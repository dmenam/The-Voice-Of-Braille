package Ventanas;

import Braille.Braille;
import Conexiones.Comandos_Voz;
import Conexiones.FileManager;
import Conexiones.Voz;
import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class Principal {

    public static void main(String[] args) {
        //Splash s = new Splash(); 
        Inicio i = new Inicio();
        //Configuracion c = new Configuracion();
        //Archivo a = new Archivo();
        //Creditos c = new Creditos();
        Ayuda a = new Ayuda(i);
        //Comandos_Voz c = new Comandos_Voz();       
        //c.setInicio(i);
        //Pruebas pruebas = new Pruebas();

        //Voz v = new Voz(i);
        
    }
}
