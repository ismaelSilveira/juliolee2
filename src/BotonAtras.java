import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;


public class BotonAtras implements Runnable {
	private final static int APRETADO = 1;
	private final static int NO_APRETADO = 2;
	private Thread t;
	TouchSensor boton;
	int apretado = NO_APRETADO;

	public BotonAtras(SensorPort puerto){
		this.boton = new TouchSensor(puerto);
	}
	
	public void start() {
		if (t == null) {
			t = new Thread(this, "BotonAtras");
			t.start();
		}
	}
	
	@Override
	public void run() {
		while (true) {
			apretado = boton.isPressed() ? APRETADO : NO_APRETADO; 
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int getApretado(){
		return this.apretado;
	}
}
