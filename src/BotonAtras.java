import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;


public class BotonAtras implements Runnable {
	private Thread t;
	TouchSensor boton;
	int apretado = 0;
	
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
			apretado = boton.isPressed() ? 1 : 0; 
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
