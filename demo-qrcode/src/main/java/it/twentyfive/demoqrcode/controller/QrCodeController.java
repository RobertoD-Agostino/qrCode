package it.twentyfive.demoqrcode.controller;

import it.twentyfive.demoqrcode.model.ResponseImage;
import it.twentyfive.demoqrcode.utils.MethodUtils;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import java.awt.Font;


@RestController
public class QrCodeController {

    public static final int DEFAULT_QR_WIDTH = 350;
    public static final int DEFAULT_QR_HEIGHT = 350;

    @GetMapping("/generate")
    // public ResponseEntity<ResponseImage> downloadQrCodeBase64(@RequestParam String requestUrl) {
    //     try {
    //         // Carica il logo come InputStream
    //         // InputStream logoStream = getClass().getResourceAsStream("/img/logo2.png");
    //         byte[] bytes = MethodUtils.generateQrCodeImage(requestUrl, DEFAULT_QR_WIDTH, DEFAULT_QR_HEIGHT);
    //         String base64 = Base64.getEncoder().encodeToString(bytes);
    //         base64 = "data:image/png;base64," + base64;
    //         ResponseImage response = new ResponseImage();
    //         response.setImageBase64(base64);
    //         return ResponseEntity.status(HttpStatus.OK).body(response);
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    //     }
    // }  

    // public ResponseEntity<ResponseImage> downloadQrCodeBase64(@RequestParam String requestUrl) {
    //     try {
    //         byte[] qrCodeBytes = MethodUtils.generateQrCodeImage(requestUrl, DEFAULT_QR_WIDTH, DEFAULT_QR_HEIGHT);
    //         byte[] borderedQrCodeBytes = MethodUtils.addBorder(qrCodeBytes, 20, 80, 30, 30, Color.RED); // Esempio di bordo con diverse dimensioni sui lati con colore rosso
            
    //         Font font = new Font("Arial", Font.BOLD, 24);
    //         byte[] finalImageBytes = MethodUtils.addTextToBorder(borderedQrCodeBytes, "SCAN ME", font, Color.BLACK);
    
    //         String base64 = Base64.getEncoder().encodeToString(finalImageBytes); // Converti finalImageBytes in Base64 anziché borderedQrCodeBytes
    //         base64 = "data:image/png;base64," + base64;
            
    //         ResponseImage response = new ResponseImage();
    //         response.setImageBase64(base64);
            
    //         return ResponseEntity.status(HttpStatus.OK).body(response);
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    //     }
    // }



//     public ResponseEntity<ResponseImage> downloadQrCodeBase64(@RequestParam String requestUrl) {
//         try {
//             byte[] qrCodeBytes = MethodUtils.generateQrCodeImage(requestUrl, DEFAULT_QR_WIDTH, DEFAULT_QR_HEIGHT);
//             byte[] borderedQrCodeBytes = MethodUtils.addBorder(qrCodeBytes, 20, 80, 30, 30, Color.RED); // Esempio di bordo con diverse dimensioni sui lati con colore rosso
            
//             Font font = new Font("Arial", Font.BOLD, 24);
//             byte[] textAddedBytes = MethodUtils.addTextToBorder(borderedQrCodeBytes, "SCAN ME", font, Color.BLACK);
            
// // Inserisci il percorso del file del logo
// String logoFilePath = "/img/logoGoogle.png";

// // Leggi i byte del file del logo
// byte[] logoBytes = Files.readAllBytes(Paths.get(logoFilePath));


//             // Aggiungi il logo all'immagine con il testo
//             byte[] finalImageBytes = MethodUtils.addLogoToBorder(textAddedBytes, logoBytes); // Assicurati di avere il byte array del logo
            
//             String base64 = Base64.getEncoder().encodeToString(finalImageBytes); // Converti finalImageBytes in Base64 anziché borderedQrCodeBytes
//             base64 = "data:image/png;base64," + base64;
            
//             ResponseImage response = new ResponseImage();
//             response.setImageBase64(base64);
            
//             return ResponseEntity.status(HttpStatus.OK).body(response);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
//         }
//     }
    


























    public ResponseEntity<ResponseImage> downloadQrCodeBase64(@RequestParam String requestUrl) {
        try {
            byte[] qrCodeBytes = MethodUtils.generateQrCodeImage(requestUrl, DEFAULT_QR_WIDTH, DEFAULT_QR_HEIGHT);
            byte[] borderedQrCodeBytes = MethodUtils.addBorder(qrCodeBytes, 20, 50, 30, 30, Color.RED); // Esempio di bordo con diverse dimensioni sui lati con colore rosso
            
            Font font = new Font("Arial", Font.BOLD, 24);
            byte[] borderedQrCodeWithTextBytes = MethodUtils.addTextToBorder(borderedQrCodeBytes, "SCAN ME", font, Color.BLACK);
    
            // Leggi l'immagine del logo da un file e convertila in un array di byte
            InputStream logoStream = getClass().getResourceAsStream("/img/scanMe.png");
            ByteArrayOutputStream logoByteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = logoStream.read(buffer)) != -1) {
                logoByteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            byte[] logoBytes = logoByteArrayOutputStream.toByteArray();
            logoStream.close();
    
            // Aggiungi il logo
            byte[] finalImageBytes = MethodUtils.addLogoToBorder(borderedQrCodeWithTextBytes, logoBytes);
    
            // Converti l'immagine risultante in una stringa Base64
            String base64 = Base64.getEncoder().encodeToString(finalImageBytes);
            base64 = "data:image/png;base64," + base64;
            
            // Crea e restituisce l'oggetto ResponseImage
            ResponseImage response = new ResponseImage();
            response.setImageBase64(base64);
            
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }
    

    
}



