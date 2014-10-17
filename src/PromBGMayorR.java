
public class PromBGMayorR implements Propiedad {
	
	/**
	 * Devuelve true si el promedio de B y G es mayor a R
	 */
	@Override
	public boolean verifica(int R, int G, int B) {
		return (B + G) >> 1 > R;
	}

}
