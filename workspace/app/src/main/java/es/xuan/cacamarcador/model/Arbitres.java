package es.xuan.cacamarcador.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Arbitres implements Serializable {
	private static final long serialVersionUID = 1L;

	private ArrayList<Arbitre> llista = null;
	private String CTE_SEPARADOR_ARBITRES = " / ";

	public Arbitres(String pStrArbitres) {
		// 1er.:MONICA TORRENTS REYNES/Anotador:ANNA YUE DE LA RUA CHICO
		llista = new ArrayList<Arbitre>();
		if (pStrArbitres != null && !pStrArbitres.equals("")) {
			String strArb[] = pStrArbitres.split(CTE_SEPARADOR_ARBITRES);
			for (String strAux : strArb) {
				Arbitre arbitre = new Arbitre(strAux);
				llista.add(arbitre);
			}
		}
	}

    public Arbitres() {
    }

    public ArrayList<Arbitre> getLlista() {
		return llista;
	}

	public void setLlista(ArrayList<Arbitre> llista) {
		this.llista = llista;
	}

	public void add(Arbitre pArbitre) {
		if (llista == null)
			llista = new ArrayList<Arbitre>();
		llista.add(pArbitre);
		
	}
	
	@Override
	public String toString() {
		String strRes = "";
		if (llista != null) {
			for (Arbitre arb : llista) {
				if (!strRes.equals(""))
					strRes += CTE_SEPARADOR_ARBITRES;
				strRes += arb.toString();
			}
		}
		return strRes;
	}	
}
