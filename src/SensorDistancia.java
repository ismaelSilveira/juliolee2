import lejos.nxt.SensorPort;
import lejos.nxt.addon.LnrActrFirgelliNXT;
import lejos.nxt.addon.OpticalDistanceSensor;

public class SensorDistancia implements Runnable {
	private Thread t;
	LnrActrFirgelliNXT actuador;
	OpticalDistanceSensor sensor;
	volatile int distancia = 0;
	
	public SensorDistancia(LnrActrFirgelliNXT actuador, SensorPort puerto){
		this.actuador = actuador;
		this.actuador.move(200, true);
		this.sensor = new OpticalDistanceSensor(puerto);
	}
	
	public void start() {
		if (t == null) {
			t = new Thread(this, "SensorDistancia");
			t.start();
		}
	}

	@Override
	public void run() {
		while (true) {
			distancia = sensor.getDistance();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getDistancia(){
		return this.distancia;
	}
}
