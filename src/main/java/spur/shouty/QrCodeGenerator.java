package spur.shouty;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import android.graphics.Bitmap;


public class QrCodeGenerator {
	private static final int BLACK = android.graphics.Color.BLACK; 
	private static final int WHITE = android.graphics.Color.WHITE;
	
	/**
	 * Returns an Bitmap object with the input string encoded as a QR code   
	                          
	                          
	@param  input  the string to be encoded
	 *  
	                          
	@param  approximateSize the resulting image will be scaled by an integer value
	 *                      up to this size.  So if the resulting bitmap were 30dp, and
	 *                      this value was set to 250, then the resulting bitmap would be
	 *                      240x240 dp, scaled up 8 times.  Set to 0 or 1 for no scaling.
	 *                      
	 *                      
	                          
	@return      a Bitmap with the encoded string
	 *  
	                          

	 */
	static Bitmap encode(String input, int approximateSize) throws WriterException {
		// TODO - check for string length
		QRCode qrc = new QRCode();
		Encoder.encode(input, ErrorCorrectionLevel.L, qrc);
		ByteMatrix matrix = qrc.getMatrix();
		return QrCodeGenerator.toBitmap(matrix, approximateSize);
	
	}
	
	private static Bitmap toBitmap(ByteMatrix matrix, int approxSize) {
		  int width = matrix.getWidth();
		  int height = matrix.getHeight();
		 
		  int scale = 1;
		
		  if(width < approxSize){
			  scale = approxSize/width;
			  if (scale < 1) {
				  scale = 1;
			  }
		  }
		  Bitmap image = Bitmap.createBitmap(width * scale, height *scale, Bitmap.Config.RGB_565);
//		These are the available specs (can't get alpha to work):
//		  Bitmap.Config  	ALPHA_8  	Each pixel is stored as a single translucency (alpha) channel. 
//		  Bitmap.Config  	ARGB_4444  	This field is deprecated. Because of the poor quality of this configuration, it is advised to use ARGB_8888 instead.  
//		  Bitmap.Config  	ARGB_8888  	Each pixel is stored on 4 bytes. 
//		  Bitmap.Config  	RGB_565  	Each pixel is stored on 2 bytes and only the RGB channels are encoded: red is stored with 5 bits of precision (32 possible values), 
//		  								green is stored with 6 bits of precision (64 possible values) and blue is stored with 5 bits of precision. 
//		TODO: see if a more efficient bitmap value can be used, or make it user definable in the API		 

		  for (int x = 0; x < width * scale; x++) {
				  for (int y = 0; y < height * scale; y++) {
					  image.setPixel(x, y, matrix.get(x/scale, y/scale) == 1 ? BLACK : WHITE);
				  }		  
			  }
		  
	    return image;
	} 

}
