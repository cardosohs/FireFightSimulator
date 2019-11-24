package pt.iul.poo.firefight.main;

public class GerarValores {

	public static void main(String[] args) {
		int i = 0;
		double humidadeAtual=0.50;
		double temperaturaAtual=44;
		while (i < 10000) {
			// 1ºVariavel
//			cartesianaCoord p = bivariateNormal(10,9,10,9);
//			System.out.println(p.x + "," + p.y);
	//		System.out.println(distibuicao());

			// 2ºVariavel
			// System.out.println(lognormal(0.25));
			//System.out.println(temp(44.0));
			//System.out.println(normal(44,4)); //temperatura inicial
//			System.out.println(normal(0,1));
			temperaturaAtual=temperaturaAtual+normal(0,1);
			temperaturaAtual= (temperaturaAtual>50)?50:temperaturaAtual;
			temperaturaAtual= (temperaturaAtual<25)?25:temperaturaAtual;
			System.out.println(temperaturaAtual);
			
			// 3ºVariavel
			// System.out.println(natural3());

			// 4ºVariavel
//			 System.out.println(geraHumidade(0.2, 0.8, 0.5));
			
//			humidadeAtual=geraHumidade(0.2, 0.8, humidadeAtual);
//			 System.out.println(humidadeAtual);

			// 5ºVariavel
			// System.out.println(exponencial());
			i++;
		}
	}

	// Distribuicao da vegetacao pelo mapa - Variavel Discreta

//	public static cartesianaCoord bivariateNormal(double muX, double sigmaX, double muY, double sigmaY) {
//		assert (sigmaX > 0. && sigmaY > 0.);
//		cartesianaCoord p = new cartesianaCoord(0, 0);
//		p.x = (int)(normal(muX, sigmaX));
//		p.y = (int)(normal(muY, sigmaY));
//		return p;
//	}
	
	public static int distibuicao(){
		Double d = Math.random();
		if(d < 0.35)
			return 0;
		if(d < 0.65)
			return 1;
		if(d < 0.85)	
			return 2;
		else
			return 3;
	}


	// Temperatura - Variavel Continua
//
//	public static double temp(double media) {	
//		return media + Math.tan(Math.PI*(Math.random()-0.5));
//	}
//	public static double geraTemperatura(double sigma) {
//		return Math.exp(normal(0.0, 0.25) * sigma);
//	}

	public static double normal(double mu, double sigma) { //gera temperatura
		assert (sigma > 0.);
		double p, p1, p2;
		do {
			p1 = Math.random() * 2 - 1;
			p2 = Math.random() * 2 - 1;
			p = p1 * p1 + p2 * p2;
		} while (p >= 1.);
		double r =mu + sigma * p1 * Math.sqrt(-2. * Math.log(p) / p); //vamos truncar os resultados 
		if (r>mu+3*sigma ||r<mu-3*sigma) { //se estiver a 3 desvios padrões da média
			r = normal( mu,  sigma); // cria novo valor
		}
		return r;
	}

	// Direcao do Vento - Variavel Discreta
	public static int geraVento() {
		Double d = new Double(Math.random() * 4);
		return d.intValue();
	}

	// Humidade do Ar - Variavel Continua
	public static double geraHumidade(double xMin, double xMax, double c) {
		assert (xMin < xMax && xMin <= c && c <= xMax);
		double p = Math.random();
		double q = 1. - p;
		if (p <= (c - xMin) / (xMax - xMin))
			return xMin + Math.sqrt((xMax - xMin) * (c - xMin) * p);
		else
			return xMax - Math.sqrt((xMax - xMin) * (xMax - c) * q);
	}

	// Densidade da Vegetacao - Variavel Continua
	public static double geraDensidade() {
		return 1 - Math.log(Math.random());
	}

}
