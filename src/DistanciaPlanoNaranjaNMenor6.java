
public class DistanciaPlanoNaranjaNMenor6 implements Propiedad {

	/*
	 * 
	#coefs del plano son tales que z = c(1)*x + c(2)*y + c(3)
	#distancia se calcula con 
	a = coefs_plano(1);
	b = coefs_plano(2);
	d = coefs_plano(3);	
	plano = [a b -1 d];
    r = abs(([punto 1] * plano')) / sqrt(sum(plano(1:3).^2));
   
	 */
	@Override
	public boolean verifica(int R, int G, int B) {
		return distPlano(R, G, B) < 6;
	}
	
	/**
	 * Calcula la distancia de un punto al plano segun la formula en 
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	private double distPlano(int r, int g, int b ) {
		double[] c = {-0.41292, 1.59088, -1, -39.56286}; //coeficientes del plano ajustado por minimos cuadrados
		double[] P = {r, g, b, 1};
		double prod_esc = P[0] * c[0] + P[1] * c[1] + P[2] * c[2] + P[3] * c[3];
		if (prod_esc < 0)
			prod_esc = -prod_esc;
		double norma_vect_norm = Math.sqrt(c[0] * c[0] + c[1] * c[1] + c[2] * c[2]); 
		return prod_esc / norma_vect_norm;
	}

}
