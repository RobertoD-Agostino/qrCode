package it.twentyfive.demoqrcode.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.BorderUIResource.MatteBorderUIResource;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.RoundRectangle2D;


// import it.twentyfive.demoqrcode.utils.MethodUtils.QRCodePart;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MethodUtils {

        //AGGIUNGI COLORI FUNZIONANTE
        public static byte[] generateQrCodeImage(String text, int width, int height) throws WriterException, IOException {
            return generateQrCodeImage(text, width, height, Color.blue, Color.white);
        }

        public static byte[] generateQrCodeImage(String text, int width, int height, Color qrCodeColor, Color backgroundColor) throws WriterException, IOException {
            //Questa riga crea un'istanza di QRCodeWriter, che sarà utilizzata per generare il codice QR.
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            //Qui, il testo (text), il formato del codice a barre (BarcodeFormat.QR_CODE), la larghezza (width) e l'altezza (height) vengono passati al qrCodeWriter per generare una matrice di bit (BitMatrix) rappresentante il codice QR.
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            //Viene creato un ByteArrayOutputStream per memorizzare l'immagine PNG del codice QR.
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

            //Qui viene creato un oggetto MatrixToImageConfig utilizzando i colori specificati per il codice QR e lo sfondo.
            MatrixToImageConfig con = new MatrixToImageConfig(qrCodeColor.getRGB(), backgroundColor.getRGB());

            // Qui viene scritta l'immagine basata sulla matrice di bit, che verrà generata e memorizzata all'interno del pngOutputStream, ovvero un flusso di output
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);

            return pngOutputStream.toByteArray();
        }



        //AGGIUNGI BORDI FUNZIONANTE (si possono scegliere le dimensioni dei lati e il colore della cornice)
        public static byte[] addBorder(byte[] qrCodeImage, int topBorder, int bottomBorder, int leftBorder, int rightBorder, Color borderColor) throws IOException {

            //Questa riga converte l'array di byte qrCodeImage in un'immagine BufferedImage utilizzando ImageIO.read() per manipolare l'immagine 
            BufferedImage qrImage = ImageIO.read(new ByteArrayInputStream(qrCodeImage));
        
            // Calcola le nuove dimensioni dell'immagine con la cornice
            int newWidth = qrImage.getWidth() + leftBorder + rightBorder;
            int newHeight = qrImage.getHeight() + topBorder + bottomBorder;
        
            // Crea una nuova immagine con le nuove dimensioni
            BufferedImage borderedImage = new BufferedImage(newWidth, newHeight, qrImage.getType());
        
            //Qui creiamo un oggetto Graphics2D per disegnare sul bordereImage l'immagine del codice QR all'interno del bordo
            Graphics2D graphics = borderedImage.createGraphics();
            graphics.setColor(borderColor);
            graphics.fillRect(0, 0, newWidth, newHeight);
            graphics.drawImage(qrImage, leftBorder, topBorder, null);
            graphics.dispose();
        
            //Qui convertiamo l'immagine borderedImage in un array di byte in formato PNG utilizzando ImageIO.write. L'array di byte rappresenta l'immagine del codice QR con il bordo aggiunto
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(borderedImage, "png", outputStream);
            return outputStream.toByteArray();
        }
        

        //AGGIUNGI TESTO ALLA CORNICE FUNZIONANTE
        public static byte[] addTextToBorder(byte[] borderedQrCodeBytes, String text, Font font, Color textColor) throws IOException {
            BufferedImage borderedImage = ImageIO.read(new ByteArrayInputStream(borderedQrCodeBytes));
        
            // Crea un oggetto Graphics2D per disegnare il testo sull'immagine con la cornice
            Graphics2D graphics = borderedImage.createGraphics();
            graphics.setColor(textColor);
            graphics.setFont(font);
        
            // Calcola le dimensioni del testo
            FontMetrics fontMetrics = graphics.getFontMetrics(font);
            int textWidth = fontMetrics.stringWidth(text);
            int textHeight = fontMetrics.getHeight();
        
            // Posiziona il testo al centro della parte inferiore dell'immagine
            int x = (borderedImage.getWidth() - textWidth) / 2;
            int y = borderedImage.getHeight() - textHeight; // Posiziona il testo in fondo all'immagine
        
            // Disegna il testo sull'immagine
            graphics.drawString(text, x, y);
        
            graphics.dispose();
        
            // Converti l'immagine con il testo in un array di byte
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(borderedImage, "png", outputStream);
            return outputStream.toByteArray();
        }

        
        public static byte[] addLogoToBorder(byte[] borderedQrCodeBytes, byte[] logoBytes) throws IOException {
            //Questa riga legge l'array di byte che rappresenta l'immagine con la cornice
            BufferedImage borderedImage = ImageIO.read(new ByteArrayInputStream(borderedQrCodeBytes));
            //Questa riga legge l'array di byte che rappresenta il logo da aggiungere
            BufferedImage logoImage = ImageIO.read(new ByteArrayInputStream(logoBytes));
        
            // // Calcola le coordinate per posizionare il logo al centro della parte inferiore della cornice
            // int logoWidth = logoImage.getWidth();
            // int logoHeight = logoImage.getHeight();
            // int x = (borderedImage.getWidth() - logoWidth) / 2;
            // int y = borderedImage.getHeight() - logoHeight;

            // Calcola le coordinate per posizionare il logo all'inizio dell'angolo basso a sinistra
            int logoWidth = logoImage.getWidth();
            int logoHeight = logoImage.getHeight();
            int x = 1; // Margine di 1 pixel dal lato sinistro
            int y = borderedImage.getHeight() - logoHeight - 1;
        
            // Disegna il logo sull'immagine con la cornice
            Graphics2D graphics = borderedImage.createGraphics();
            graphics.drawImage(logoImage, x, y, null);
            graphics.dispose();
        
            // Converti l'immagine con il logo in un array di byte
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(borderedImage, "png", outputStream);
            return outputStream.toByteArray();
        }
        


    }





    // // Metodo originale per generare il QR Code senza logo
    // public static byte[] generateQrCodeImage(String text, int width, int height) throws WriterException, IOException {
    //     QRCodeWriter qrCodeWriter = new QRCodeWriter();
    //     BitMatrix bitMatrix = qrCodeWriter.encode(text, com.google.zxing.BarcodeFormat.QR_CODE, width, height);

    //     ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
    //     MatrixToImageConfig con = new MatrixToImageConfig(0xFFFFFFFF, 0xFF000000);

    //     MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
    //     return pngOutputStream.toByteArray();
    // }




//INSERIMENTO DEL LOGO FATTO SOLO CHE NON SCANSIONA

// import com.google.zxing.WriterException;
// import com.google.zxing.client.j2se.MatrixToImageConfig;
// import com.google.zxing.client.j2se.MatrixToImageWriter;
// import com.google.zxing.common.BitMatrix;
// import com.google.zxing.qrcode.QRCodeWriter;

// import javax.imageio.ImageIO;
// import java.awt.*;
// import java.awt.image.BufferedImage;
// import java.io.ByteArrayOutputStream;
// import java.io.IOException;
// import java.io.InputStream;

// public class MethodUtils {

//     // Metodo per generare un QR Code con logo al centro
//     public static byte[] generateQrCodeImageWithLogo(String text, int width, int height, InputStream logoStream) throws IOException, WriterException {
//         // Genera il QR Code base
//         QRCodeWriter qrCodeWriter = new QRCodeWriter();
//         BitMatrix bitMatrix = qrCodeWriter.encode(text, com.google.zxing.BarcodeFormat.QR_CODE, width, height);

//         // Carica il logo come BufferedImage
//         BufferedImage logoImage = ImageIO.read(logoStream);

//         // Calcola la posizione del logo all'interno del QR Code
//         int logoPosX = (width - logoImage.getWidth()) / 2;
//         int logoPosY = (height - logoImage.getHeight()) / 2;

//         // Crea un BufferedImage per il QR Code con il logo
//         BufferedImage qrCodeWithLogo = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//         Graphics2D g2 = qrCodeWithLogo.createGraphics();

//         // Disegna il QR Code sul BufferedImage
//         g2.setColor(Color.WHITE);
//         g2.fillRect(0, 0, width, height);
//         g2.setColor(Color.BLACK);
//         for (int x = 0; x < width; x++) {
//             for (int y = 0; y < height; y++) {
//                 if (bitMatrix.get(x, y)) {
//                     qrCodeWithLogo.setRGB(x, y, Color.BLACK.getRGB());
//                 }
//             }
//         }

//         // Disegna il logo al centro del QR Code
//         g2.drawImage(logoImage, logoPosX, logoPosY, null);
//         g2.dispose();

//         // Converte il BufferedImage con il logo in byte[]
//         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//         ImageIO.write(qrCodeWithLogo, "png", outputStream);
//         return outputStream.toByteArray();
//     }



// }


//INSERIMENTO LOGO
// int matrixWidth = bitMatrix.getWidth();
// BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
// image.createGraphics();
// Graphics2D graphics = (Graphics2D) image.getGraphics();
// graphics.setColor(Color.white);
// graphics.fillRect(0, 0, matrixWidth, matrixWidth);
// Color mainColor = new Color(51, 102, 153);
// graphics.setColor(mainColor);
// //Write Bit Matrix as imagefor (int i = 0; i < matrixWidth; i++) {
// 	for (int j = 0; j < matrixWidth; j++) {
// 		if (bitMatrix.get(i, j)) {
// 			graphics.fillRect(i, j, 1, 1);
// 		}
// 	}
// }

// BufferedImage logo = ImageIO.read( this.getLogoFile());
// double scale = calcScaleRate(image, logo);
// logo = getScaledImage( logo,
// 		(int)( logo.getWidth() * scale),
// 		(int)( logo.getHeight() * scale) );
// graphics.drawImage( logo,
// 		image.getWidth()/2 - logo.getWidth()/2,
// 		image.getHeight()/2 - logo.getHeight()/2,
// 		image.getWidth()/2 + logo.getWidth()/2,
// 		image.getHeight()/2 + logo.getHeight()/2,
// 		0, 0, logo.getWidth(), logo.getHeight(), null);
// private BufferedImage getScaledImage(BufferedImage image, int width, int height)throws IOException {
// 	int imageWidth  = image.getWidth();
// 	int imageHeight = image.getHeight();
// 	double scaleX = (double)width/imageWidth;
// 	double scaleY = (double)height/imageHeight;
// 	AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
// 	AffineTransformOp bilinearScaleOp = new AffineTransformOp( scaleTransform, AffineTransformOp.TYPE_BILINEAR);
// 	return bilinearScaleOp.filter( image, new BufferedImage(width, height, image.getType()));
// }

// if ( isQRCodeCorrect(content, image)) {
// 	ImageIO.write(image, imageFormat, this.getGeneratedFileStream());
// }
// privatebooleanisQRCodeCorrect(String content, BufferedImage image){
// 	boolean result = false;
// 	Result qrResult = decode(image);
// 	if (qrResult != null && content != null && content.equals(qrResult.getText())){
// 		result = true;
// 	}		
// 	return result;
// }
// private Result decode(BufferedImage image){
// 	if (image == null) {
// 		returnnull;
// 	}
// 	try {
// 		LuminanceSource source = new BufferedImageLuminanceSource(image);	      
// 		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));	      
// 		Result result = new MultiFormatReader().decode(bitmap, Collections.EMPTY_MAP);	      
// 		return result;
// 	} catch (NotFoundException nfe) {
// 		returnnull;
// 	}
// }
