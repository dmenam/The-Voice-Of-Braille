package Conexiones;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class LeerTTS {

    private Synthesizer synth;

    public LeerTTS() {
        try {
            synth = Central.createSynthesizer(new SynthesizerModeDesc(Locale.ROOT));    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void leerTexto(String texto) {
        try {
            // Get it ready to speak
            synth.allocate();
            synth.resume();
            
            // Speak the "Hello world" string
            synth.speakPlainText(texto, null);
            
            // Espera a que termine de hablar
            synth.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (InterruptedException ex) {
            Logger.getLogger(LeerTTS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(LeerTTS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EngineException ex) {
            Logger.getLogger(LeerTTS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EngineStateError ex) {
            Logger.getLogger(LeerTTS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AudioException ex) {
            Logger.getLogger(LeerTTS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean finalizarTodo() {    
        try {
            // Limpiamos todo
            synth.deallocate();
            return true;
        } catch (EngineException ex) {
            Logger.getLogger(LeerTTS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (EngineStateError ex) {
            Logger.getLogger(LeerTTS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
