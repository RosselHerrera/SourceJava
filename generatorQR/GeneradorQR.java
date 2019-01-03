package cl.codeQR.generator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import javax.imageio.ImageIO;

import cl.code.BASE64.BASE64EncodeDecode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/**
 * GeneradorQR
 * 
 * @author RosselTech
 * @since  Julio 2018
 */
public class GeneradorQR {
	/*
	 * GeneradorQRwf
	 * Clase pública que genera el QR
	 */
	public int GeneradorQRwf (String rutaArchivo, String recursoWeb, String nombreArch ) {
		int Ret = 0;	
		String qrCodeText = recursoWeb; //"http://rosseltech.cl/emprender/index.html";
		/*String filePath = "C:\\AAA\\2018\\workspace\\rosseltec\\WebContent\\QRdoc\\" + nombreArch + ".png";*/
		String filePath = rutaArchivo + nombreArch + ".png";
		int size = 125;
		String fileType = "png";
		File qrFile = new File(filePath);
		try {	
			createQRImage(qrFile, qrCodeText, size, fileType);
			Ret = 1;
			System.out.println("DONE: Imagen creada" + Ret);
		} catch (Exception e){
			System.out.println("ERROR: Fallo Generacion");
			e.printStackTrace();
		}
		return Ret;
	}
	
	
	@SuppressWarnings("unused")
	private static void createQRImage(File qrFile, String qrCodeText, int size, String fileType)
			throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
		// Make the BufferedImage that are to hold the QRCode
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		
		/* ------------------------------------------------------  */
		BASE64EncodeDecode  encodedecode = new BASE64EncodeDecode();
		/* ------------------------------------------------------  */					
		String imgCod = encodedecode.encodeToString(image, fileType);
		/* ------------------------------------------------------  */					

		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// Paint and save the image using the ByteMatrix
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, fileType, qrFile);
	}

}
