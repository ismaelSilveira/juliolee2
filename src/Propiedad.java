/**
 * Esta interfaz representa una propiedad que se chequea en una terna (R,G,B)
 *
 */
public interface Propiedad {
	/**
	 * Indica si la terna (R,G,B) verifica o no la propiedad chequeada.
	 * 
	 * @param R valor de rojo
	 * @param G valor de verde
	 * @param B valor de azul
	 * @return
	 */
	public boolean verifica(int R, int G, int B);
}
