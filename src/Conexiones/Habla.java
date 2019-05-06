package Conexiones;

import java.util.logging.Level;
import java.util.logging.Logger;
import loquendo.tts.engine.*;

public class Habla {

    private TTSSession hSession;
    private TTSReader hReader;

    public Habla() {
        hReader = null;
        hSession = null;
    }

    public void hablar(String texto) {
        try {
            /* Creation of a new session by using default configuration */
            hSession = new TTSSession();
            if (null != hSession) {
                try {
                    /* Creation of a new reader instance */
                    hReader = new TTSReader(hSession);
                    /* Set the sound blaster as audio destination, stereo, linear and 32kHz samples rate */
                    hReader.setAudio("LTTS7AudioBoard", null, 32000, "linear", 2);
                    /* Load of Ludoviko voice with his default language */
                    hReader.loadPersona("Jorge", null, null);
                    //hReader.setLanguage(new TTSLanguage("SpanishEs", "SpanishEs"));
                    /* Read the Ludoviko demo sentence */
                    texto = convertirÑ(texto);
                    hReader.read(texto, false, false);
                    
                } catch (TTSException e) {
                    e.printStackTrace();
                }
            }
        } catch (TTSException e) {
            e.printStackTrace();
        }
    }
    
    private String convertirÑ(String texto) {
        texto = texto.replaceAll("ñ", "ni");
        texto = texto.replaceAll("Ñ", "ni");
        return texto ;
    }
    
    
    
    public boolean finalizar() {
        try {
            hSession.delete();
            return true;
        } catch (TTSException ex) {
            Logger.getLogger(Habla.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
