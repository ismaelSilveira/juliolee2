
public class GMayorRMayorB implements Propiedad {
	
	/**
	 * Devuelve true si G > R > B
	 */
	@Override
	public boolean verifica(int R, int G, int B) {
		return G > R && R > B;
	}

}
