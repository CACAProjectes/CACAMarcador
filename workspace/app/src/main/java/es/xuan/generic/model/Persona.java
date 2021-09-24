package es.xuan.generic.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author jcamposp
 *
 */
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String cognoms;
	protected String sexe;
	protected long id;
	protected String nom;
	protected String nota;
	protected String email;
	protected Calendar dataNaixement;
	protected String telefon;
	protected Bitmap foto;

	public Bitmap getFoto() {
		return foto;
	}

	public void setFoto(Bitmap foto) {
		this.foto = foto;
	}

	public Calendar getDataNaixement() {
		return dataNaixement;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDataNaixement(Calendar dataNaixement) {
		this.dataNaixement = dataNaixement;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCognoms() {
		return cognoms;
	}
	public void setCognoms(String cognoms) {
		this.cognoms = cognoms;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String toString() {
		StringBuffer strRes = new StringBuffer();
		strRes.append("Nom: ");
		strRes.append(getNom());
		strRes.append(" ");
		strRes.append(getCognoms());
		//strRes.append(Constants.CTE_CANVI_LINEA);
		strRes.append("Sexe: ");
		strRes.append(getSexe());
		//strRes.append(Constants.CTE_CANVI_LINEA);
		strRes.append("Data neixement: ");
		//strRes.append(Utils.convertCalendar2String(getDataNaixement()));
		return strRes.toString();
	}
}
