import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;


public class DateiAusgabe {

	private static void standardFileSave(ByteBuffer text) {

		// Test Kommentar

		String dateiName = "Test.dst";

		OutputStream outputStream;
		OutputStreamWriter writer;

		try {
			// Datei oeffnen.
			// Achtung: vorhandene Datei wird ueberschrieben!
			outputStream = new FileOutputStream(dateiName);
			writer = new OutputStreamWriter(outputStream, "ASCII");

			String test = "Hallo:";

			char test2 = (char) 0x83;

			// "ISO-8859-1"
			Byte test3 = (byte) 0b10000011;

			test += test2;
			test += test3;

			byte[] bytes = test.getBytes();

			writer.write(Charset.defaultCharset().displayName());

			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(String.format("%02X ", b));
			}
			System.out.println(sb.toString());
			// prints "FF 00 01 02 03 "

			// b= test.getBytes();

			// Text schreiben
			writer.write(sb.toString());

			// Datei schliessen nicht vergessen, sonst koennen Daten verloren gehen!
			writer.close();

		} catch (IOException e) {
			// Moegliche Fehler: z.B. Datei oder Verzeichnis schreibgeschuetzt
			System.out.println("Fehler beim Datei schreiben: " + e.getMessage());

		}

		
		//byte[] bytes = text.getBytes();
		
		
		

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

	private static FileOutputStream extracted(File file, boolean append) throws FileNotFoundException {
		return new FileOutputStream(file, append);
	}

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
		ausgabe += "+X:100"  + space + space + end2;// TODO noch automatisiern
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
		
		

		// testausgabe
		// ausgabe += "" + end1 + end1 + (char) 0x03 + (char) 0x09 + (char) 0x04 +
		// (char) 0x07;

		// ende
		
		ausgabe = "" + (char) end1 + (char) end1 + (char) charakter;
		bytes = ausgabe.getBytes();
		buffer.put(bytes);

		standardFileSave(buffer);

	}

}