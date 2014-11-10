import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.NXTCommConnector;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.RS485;

public class Comunicacion implements Runnable {
	private Thread t;
	private NXTCommConnector conector;
	private NXTConnection conn;
	private DataInputStream dis;
	private DataOutputStream dos;
	private int lectura;

	public static int SENSAR = 2;
	public static int PATEAR = 3;
	public static int DISTANCIA = 4;

	public Comunicacion() {
		conector = RS485.getConnector();
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, "Comunicacion");
			t.start();
		}
	}

	@Override
	public void run() {
		while (true) {
			// espero a que me inicien comunicacion
			conn = null;
			conn = conector.waitForConnection(0, NXTConnection.PACKET);

			dis = conn.openDataInputStream();
			dos = conn.openDataOutputStream();
			
			try{
				while(conn != null){
					lectura = dis.readInt();
				}
			}catch (IOException e){
				
			}

			try {
				dis.close();
				dos.close();
				conn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				// LCD.drawString("error close", 0, 1);
				break;
			}

		}

	}

	/**
	 * Comunica algo al otro brick
	 * 
	 * @param aComunicar
	 */
	public void comunicar(int aComunicar) {
		while (conn == null) {
		}
		try {
			dos.writeInt(aComunicar);
			dos.flush();
		} catch (IOException e) {
			// e.printStackTrace();
			LCD.drawString("error escribir", 0, 1);
		}
	}

	/**
	 * @return the lectura
	 */
	public int getLectura() {
		return lectura;
	}
	
	/**
	 * setea el valor de lectura
	 * 
	 * @param lectura
	 */
	public void setLectura(int lectura){
		this.lectura = lectura;
	}

}
