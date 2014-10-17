
public class DistanciaGRMayorDistanciaBR implements Propiedad {

	/**
	 * Devuelve true si la distancia de R a G es mayor a la de B a R
	 */
	@Override
	public boolean verifica(int R, int G, int B) {
		int dRG = R > G ? R - G : G - R;
		int dBR = B > R ? B - R : R - B; 
		return dRG > dBR;
	}

}
