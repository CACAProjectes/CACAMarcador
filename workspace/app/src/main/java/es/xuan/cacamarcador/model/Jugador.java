/**
 * 
 */
package es.xuan.cacamarcador.model;
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.Calendar;

import es.xuan.cacamarcador.altres.Utils;

/**
 * @author jcamposp
 *
 */
public class Jugador extends Persona implements Serializable, Comparable<Jugador> {
	private static final long serialVersionUID = 1L;
	
	protected Integer dorsal;
	protected String posicio;	// Líbero, central, punta, opuesto, zaguero
	
	public Jugador(String pStrDatos) {
		setDorsal(0);
		setNom("");
		setCognoms("");
		setDataNaixement(Calendar.getInstance());
		setSexe("");
		setPosicio("");
		if (pStrDatos != null && !pStrDatos.equals("")) {
			String strAux[] = pStrDatos.split(";");
			if (strAux.length > 0)
				setDorsal(Utils.convertString2Integer(strAux[0]));
			if (strAux.length == 1 || strAux[1].equals(""))
				setNom("Jug.");
			else
				setNom(strAux[1]);
			if (strAux.length > 2)
				setCognoms(strAux[2]);
			if (strAux.length > 3)
				setDataNaixement(Utils.convertString2Calendar(strAux[3]));
			if (strAux.length > 4)
				setSexe(strAux[4]);
			if (strAux.length > 5)
				setPosicio(strAux[5]);
		}
	}

	public Jugador() {
	}

	public String getPosicio() {
		return posicio;
	}

	public void setPosicio(String posicio) {
		this.posicio = posicio;
	}

	public Integer getDorsal() {
		return dorsal;
	}

	public void setDorsal(Integer dorsal) {
		this.dorsal = dorsal;
	}

	@Override
	public int compareTo(@NonNull Jugador pJug) {
		Integer iDorsal = pJug.getDorsal();
		//ascending order
		return this.dorsal - iDorsal;
	}
}
