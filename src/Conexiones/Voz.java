package Conexiones;

import Ventanas.Inicio;
import java.util.concurrent.Future;
import com.microsoft.cognitiveservices.speech.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.swing.JOptionPane;

public class Voz {

    private String ApiKEY;
    private final String REGION_SERVICE = "westus";
    private String resultado;
    private Inicio inicio;

    public Voz(Inicio inicio) {
        this.inicio = inicio;
    }
    
    public void iniciarDictado() {
        try {
            // Replace below with your own subscription key
            ApiKEY = "1f1a11d6c6994bf289bd9c51aab7d9ac";
            // Replace below with your own service region (e.g., "westus").

            int exitCode = 1;
            SpeechConfig config = SpeechConfig.fromSubscription(ApiKEY, REGION_SERVICE);
            config.setSpeechRecognitionLanguage("es-MX");
            assert (config != null);

            SpeechRecognizer reco = new SpeechRecognizer(config);
            assert (reco != null);

            System.out.println("Di algo...");
            Future<SpeechRecognitionResult> task = reco.recognizeOnceAsync();
            assert (task != null);

            SpeechRecognitionResult result = task.get();
            assert (result != null);

            if (result.getReason() == ResultReason.RecognizedSpeech) {
                System.out.println("We recognized: " + result.getText());
                
                inicio.setTexto(result.getText());
         
                exitCode = 0;
            } else if (result.getReason() == ResultReason.NoMatch) {
                System.out.println("NOMATCH: Speech could not be recognized.");
                JOptionPane.showMessageDialog(inicio, "No se pudo reconocer nada", "Alerta", JOptionPane.WARNING_MESSAGE);
            } else if (result.getReason() == ResultReason.Canceled) {
                CancellationDetails cancellation = CancellationDetails.fromResult(result);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you update the subscription info?");
                    JOptionPane.showMessageDialog(null, "No se puede conectar con el host remoto, verifique su conexion a Internet");
                }
            }
            reco.close();
            
            //System.exit(exitCode);
        } catch (Exception ex) {
            System.out.println("Unexpected exception: " + ex.getMessage());
            JOptionPane.showMessageDialog(inicio, "Error en el dictado, verifique su conexion a Internet o su microfono e intentelo mas tarde", "Error", JOptionPane.ERROR_MESSAGE);
            assert (false);
            System.exit(1);
        } 
    }

    public String getTextoDictado() {
        return resultado;
    }
    
    public String getApiKEY() {
        return ApiKEY;
    }

    public void setApiKEY(String ApiKEY) {
        this.ApiKEY = ApiKEY;
    }
    
    public void setInicio(Inicio inicio) {
        this.inicio = inicio;
    }
}
