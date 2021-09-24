/**
 * 
 */
package es.xuan.generic.model;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author jcamposp
 *
 */
public class Jugador extends Persona implements Serializable, Comparable<Jugador> {
	private static final long serialVersionUID = 1L;
	
	protected Integer dorsal;
	protected String posicio;	// Líbero, central, punta, opuesto, zaguero

	private Integer convertString2Integer(String pStrNumero) {
		Integer numero = new Integer(0);
		try {
			if (pStrNumero != null && !pStrNumero.equals(""))
				numero = new Integer(pStrNumero);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numero;
	}
	private Calendar convertString2Calendar(String pStrFecha) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = null;
		try {
			if (pStrFecha != null && !pStrFecha.equals("")) {
				cal = Calendar.getInstance();
				Date dateStr = new Date();
				dateStr = formatter.parse(pStrFecha);
				cal.setTime(dateStr);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal;
	}
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
				setDorsal(convertString2Integer(strAux[0]));
			if (strAux.length > 1)
				setNom(strAux[1]);
			if (strAux.length > 2)
				setCognoms(strAux[2]);
			if (strAux.length > 3)
				setDataNaixement(convertString2Calendar(strAux[3]));
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

	/*
	public static Comparator<Jugador> JugadorComparator
			= new Comparator<Jugador>() {
		public int compare(Jugador pJug1, Jugador pJug2) {
			Integer jug1 = pJug1.getDorsal();
			Integer jug2 = pJug2.getDorsal();
			//ascending order
			return jug1.compareTo(jug2);
		}
	};
	*/
	@Override
	public int compareTo(@NonNull Jugador pJug) {
		Integer iDorsal = pJug.getDorsal();
		//ascending order
		return this.dorsal - iDorsal;
	}
}
