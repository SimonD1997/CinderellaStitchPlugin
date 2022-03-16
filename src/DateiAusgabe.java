/* 
# ------------------------------------------------------------------
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public License
# License as published by the Free Software Foundation; either
# version 3 of the License, or (at your option) any later version.s
# ------------------------------------------------------------------
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import javax.swing.JFileChooser;


/**
 * Gibt eine Stitchdatei im DST-Format aus. 
 * Fügt Header und Stitchcode zusammen und ruft ein Dateiauswahlmenü aus. 
 * @author Simon Doubleday
 *
 */
public class DateiAusgabe {
	
	//Verzeichnispfad
	private static File verzeichnis;

	/**
	 * Gibt den die Stichbefehle in eine Datei aus. 
	 * @param text Stichcode als Bytebuffer
	 */
	private static void standardFileSave(ByteBuffer text) {

		
		//Dateiausgabe nach https://examples.javacodegeeks.com/core-java/nio/bytebuffer/write-append-to-file-with-byte-buffer/
		try {

			File file = new File("out.dst");

			// append or overwrite the file
			boolean append = false;

			FileChannel channel = extracted(file, append).getChannel();
			// Flips this buffer. The limit is set to the current position and then
			// the position is set to zero. If the mark is defined then it is discarded.
			text.flip();

			// Writes a sequence of bytes to this channel from the given buffer.
			channel.write(text);

			// close the channel
			channel.close();

		} catch (IOException e) {
			System.out.println("I/O Error: " + e.getMessage());
		}

	}
	/**
	 * Stellt ein Dateiauswahlmenue zur Verfügung und gibt in die entsprechende Datei die Stichbefehle aus. 
	 * @param text Stichcode als Bytebuffer
	 */
	private static void fileAuswahl(ByteBuffer text) {
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(verzeichnis);
		int retrival = chooser.showSaveDialog(null);
		
		if (retrival == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			
			if (!file.getName().toLowerCase().endsWith(".dst")) {
		        file = new File(file.getParentFile(), file.getName() + ".dst");
		      }
			
			//Dateiausgabe nach https://examples.javacodegeeks.com/core-java/nio/bytebuffer/write-append-to-file-with-byte-buffer/
	        try {

				// append or overwrite the file
				boolean append = false;

				FileChannel channel = extracted(file, append).getChannel();
				// Flips this buffer. The limit is set to the current position and then
				// the position is set to zero. If the mark is defined then it is discarded.
				text.flip();

				// Writes a sequence of bytes to this channel from the given buffer.
				channel.write(text);

				// close the channel
				channel.close();

	            
	            
	        } catch (IOException e) {
				System.out.println("I/O Error: " + e.getMessage());
			}
	        /*
	        catch (Exception ex) {
	            ex.printStackTrace();
	        }*/
	    }
		//Um beim nächsten Speichern im selben Verzeichnis zu starten
		verzeichnis = chooser.getCurrentDirectory();
		
	}

	private static FileOutputStream extracted(File file, boolean append) throws FileNotFoundException {
		return new FileOutputStream(file, append);
	}

	/**
	 * Fügt Stichbefehle und Header zusammen.
	 * @param text Stichbefehle ohne Header
	 */
	public static void dstDatei(ByteBuffer text) {

		String ausgabe;
		ByteBuffer buffer = ByteBuffer.allocate(10000);

		// Header
		char space = 0x20;
		char charakter = 0xF3;
		char end1 = 0x00;
		char end2 = 0x0D;
		ausgabe = "LA:turtlestitch" + space + space + space + end1 + end2;
		ausgabe += "ST:3" + space + space + space + space + space + space + end2;// TODO noch automatisiern
		ausgabe += "CO:1" + space + space + end2;
		ausgabe += "+X:100"  + space + space + end2;// TODO noch automatisieren
		ausgabe += "-X:0" + space + space + space + space + end2;// TODO noch automatisiern
		ausgabe += "+Y:0" + space + space + space + space + end2;// TODO noch automatisiern
		ausgabe += "-Y:0" + space + space + space + space + end2; // TODO noch automatisiern
		ausgabe += "AX:0" + space + space + space + space + space + end2;
		ausgabe += "AY:0" + space + space + space + space + space + end2;
		ausgabe += "MX:0" + space + space + space + space + space + end2;
		ausgabe += "MY:0" + space + space + space + space + space + end2;
		ausgabe += "PD:******" + end2;
		char headerEnd = 0x1a;
		ausgabe += "" + headerEnd + end1 + end1 + end1;

		for (int i = 0; i < 384; i++) {
			ausgabe += space;
		}

		byte[] bytes = ausgabe.getBytes();
		buffer.put(bytes);
		
		text.flip();
		byte[] textBytes = text.array();
		buffer.put(textBytes);
		
		
		ausgabe = "" + (char) end1 + (char) end1 + (char) charakter;
		bytes = ausgabe.getBytes();
		buffer.put(bytes);

		//standardFileSave(buffer);
		fileAuswahl(buffer);

	}

}