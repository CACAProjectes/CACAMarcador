package es.xuan.cacamarcador;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.ContextThemeWrapper;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;

import es.xuan.cacamarcador.altres.Serializar;
import es.xuan.cacamarcador.altres.Utils;
import es.xuan.cacamarcador.files.FilesDao;
import es.xuan.cacamarcador.model.Estadistica;
import es.xuan.cacamarcador.model.Jugador;
import es.xuan.cacamarcador.model.PartitMarcador;
import es.xuan.generic.model.PartitGeneric;

public class ConsolaActivity extends Activity implements View.OnDragListener, View.OnTouchListener  {

    private TextView m_tvTitolLocal = null;
    private TextView m_tvTitolVisitant = null;
    private TextView m_tvMarcadorLocal = null;
    private TextView m_tvMarcadorVisitant = null;
    private TextView m_tvSetActual = null;
    private TextView m_tvSet1 = null;
    private TextView m_tvSet2= null;
    private TextView m_tvSet3 = null;
    private TextView m_tvSet4 = null;
    private TextView m_tvSet5 = null;
    private TextView m_tvMarcadorPuntsLocal = null;
    private TextView m_tvMarcadorPuntsVisitant = null;
    private ImageView m_ivTempsMortLocal1 = null;
    private ImageView m_ivTempsMortLocal2 = null;
    private ImageView m_ivTempsMortVisitant1 = null;
    private ImageView m_ivTempsMortVisitant2 = null;
    private ImageView m_ivEscutLocal = null;
    private ImageView m_ivEscutVisitant = null;
    private FloatingActionButton m_fabAceServe = null;
    private FloatingActionButton m_fabAltres = null;
    private FloatingActionButton m_fabAttackError = null;
    private FloatingActionButton m_fabAttackPoint = null;
    private FloatingActionButton m_fabErrorOnSet = null;
    private FloatingActionButton m_fabServeError = null;
    private FloatingActionButton m_fabWiningBlock = null;
    private FloatingActionButton m_fabAddJugador = null;
    private FloatingActionButton m_fabResetMarcador = null;
    private FloatingActionButton m_fabBackMarcador = null;
    private FloatingActionButton m_fabAltresError = null;
    private FloatingActionButton m_fabAltresCancel = null;
    private TextView m_tvPosicio11 = null;
    private TextView m_tvPosicio12 = null;
    private TextView m_tvPosicio13 = null;
    private TextView m_tvPosicio14 = null;
    private TextView m_tvPosicio15 = null;
    private TextView m_tvPosicio16 = null;
    private TextView m_tvPosicio21 = null;
    private TextView m_tvPosicio22 = null;
    private TextView m_tvPosicio23 = null;
    private TextView m_tvPosicio24 = null;
    private TextView m_tvPosicio25 = null;
    private TextView m_tvPosicio26 = null;
    private AlertDialog m_adAlertDialog = null;
    private View m_vJugadorNou = null;
    private LinearLayout m_llTempsMortLocal = null;
    private LinearLayout m_llTempsMortVisitant = null;
    //
    private PartitMarcador m_partit = null;
    private ArrayList<Estadistica> m_estadistiques = null;
    private Estadistica m_darreraEstadistica = null;
    private SharedPreferences m_spDades = null;
    private boolean m_rotarLocal = false;
    private boolean m_rotarVisitant = false;
    //
    private ArrayList<PartitMarcador> m_partitsHistorial = null;
    //private ArrayList<Estadistica> m_estadistiquesHistorial = null;
    //
    private JugadorsAdapter m_anAdapterLocal = null;
    private JugadorsAdapter m_anAdapterVisitant = null;
    private ListView m_lvJugadorsLocal = null;
    private ListView m_lvJugadorsVisitant = null;
    //
    private final long CTE_VIBRATION_MS = 50;
    private final String CTE_CLAVE_COMPETICIO_PARTIT_MARCADOR = "SP_COMPETICIO_PARTIT";
    private final String CTE_CLAVE_COMPETICIO_PARTIT = "SP_COMPETICIO_PARTIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_consola);
        // Inicialitzar permissos fitxers externs
        sollicitarPermissos();
        //
        inicialitzarPantalla();
        //
        obtenirDadesPartit();
        //
        //inicialitzarJugadors();
        //
        actualitzarDadesPantalla(true, true, true); // Omplir pantalla completa: TRUE, TRUE, TRUE
    }
    /*
    private void inicialitzarJugadors() {
        // Jugadors Locals
        String idKey = "jugadors_" + Utils.parserTextMipmap(m_partit.getEquipLocal());
        //String idKey = "jugadors_" + Utils.parserTextMipmap("CV ESPLUGUES ‘B‘");
        int id = getResources().getIdentifier(idKey, "array", getPackageName());
        if (id > 0 ) {
            String[] jugadors = getResources().getStringArray(id);
            ArrayList<Jugador> arrJugadors = new ArrayList<>();
            for(String strJugador : jugadors) {
                arrJugadors.add(new Jugador(strJugador));
            }
            m_partit.setJugadorsEquipLocal(arrJugadors);
        }
        // Jugadors Visitants
        idKey = "jugadors_" + Utils.parserTextMipmap(m_partit.getEquipVisitant());
        //idKey = "jugadors_" + Utils.parserTextMipmap("BARÇA CVB GRANA");
        id = getResources().getIdentifier(idKey, "array", getPackageName());
        if (id > 0 ) {
            String[] jugadors = getResources().getStringArray(id);
            ArrayList<Jugador> arrJugadors = new ArrayList<>();
            for(String strJugador : jugadors) {
                arrJugadors.add(new Jugador(strJugador));
            }
            m_partit.setJugadorsEquipVisitant(arrJugadors);
        }
    }
     */
    /*
        Omple la pantalla segons les condicions d'entrada
            pOmplirPantallaEquips
            pOmplirPunts
            pOmplirJugadors
     */
    private void actualitzarDadesPantalla(boolean pOmplirPantallaEquips, boolean pOmplirPunts, boolean pOmplirJugadors) {
        //
        guardarHistorialPartit();
        //
        if (pOmplirPantallaEquips) {
            m_tvTitolLocal.setText(m_partit.getEquipLocal());
            m_tvTitolVisitant.setText(m_partit.getEquipVisitant());
            int id = getResources().getIdentifier(getPackageName() + ":mipmap/ic_escut_" + Utils.parserTextMipmap(m_partit.getEquipLocal()), null, null);
            if (id > 0 )
                m_ivEscutLocal.setImageResource(id);
            else
                m_ivEscutLocal.setImageResource(R.mipmap.ic_no_disponible);
            id = getResources().getIdentifier(getPackageName() + ":mipmap/ic_escut_" + Utils.parserTextMipmap(m_partit.getEquipVisitant()), null, null);
            if (id > 0 )
                m_ivEscutVisitant.setImageResource(id);
            else
                m_ivEscutVisitant.setImageResource(R.mipmap.ic_no_disponible);
        }
        if (pOmplirPunts) {
            if (m_partit.getNumSetActual() == 0) {
                // Fi de partit
                m_tvMarcadorPuntsLocal.setText("-");
                m_tvMarcadorPuntsVisitant.setText("-");
                m_tvSetActual.setText("-");
            }
            else {
                m_tvMarcadorPuntsLocal.setText(Utils.num2String(m_partit.getResultatPuntsLocal(), 2));
                m_tvMarcadorPuntsVisitant.setText(Utils.num2String(m_partit.getResultatPuntsVisitant(), 2, false));
                m_tvSetActual.setText(Utils.num2String(m_partit.getNumSetActual(), 1));
            }
            //  L'eqquip que fa punt parpadeja el seu marcador
            blinkMarcador();
            m_tvMarcadorLocal.setText(Utils.num2String(m_partit.getResultatSetLocal(), 1));
            m_tvMarcadorVisitant.setText(Utils.num2String(m_partit.getResultatSetVisitant(), 1));
            //
            m_tvSet1.setText(Utils.num2String(m_partit.getPuntsSets()[0][0], 2) + " - " + Utils.num2String(m_partit.getPuntsSets()[0][1], 2, false));
            m_tvSet1.setTextColor(Color.GRAY);
            m_tvSet2.setText(Utils.num2String(m_partit.getPuntsSets()[1][0], 2) + " - " + Utils.num2String(m_partit.getPuntsSets()[1][1], 2, false));
            m_tvSet2.setTextColor(Color.GRAY);
            m_tvSet3.setText(Utils.num2String(m_partit.getPuntsSets()[2][0], 2) + " - " + Utils.num2String(m_partit.getPuntsSets()[2][1], 2, false));
            m_tvSet3.setTextColor(Color.GRAY);
            m_tvSet4.setText(Utils.num2String(m_partit.getPuntsSets()[3][0], 2) + " - " + Utils.num2String(m_partit.getPuntsSets()[3][1], 2, false));
            m_tvSet4.setTextColor(Color.GRAY);
            m_tvSet5.setText(Utils.num2String(m_partit.getPuntsSets()[4][0], 2) + " - " + Utils.num2String(m_partit.getPuntsSets()[4][1], 2, false));
            m_tvSet5.setTextColor(Color.GRAY);
            int iSumaSets = Utils.convertString2Integer(m_tvMarcadorLocal.getText().toString()).intValue() +
                    Utils.convertString2Integer(m_tvMarcadorVisitant.getText().toString()).intValue();
            if (iSumaSets >= 0)
                m_tvSet1.setTextColor(Color.BLACK);
            if (iSumaSets >= 1)
                m_tvSet2.setTextColor(Color.BLACK);
            if (iSumaSets >= 2)
                m_tvSet3.setTextColor(Color.BLACK);
            if (iSumaSets >= 3 && m_partit.getNumSetActual() > 0)
                m_tvSet4.setTextColor(Color.BLACK);
            if (iSumaSets >= 4 && m_partit.getNumSetActual() > 0)
                m_tvSet5.setTextColor(Color.BLACK);
        }
        if (pOmplirJugadors) {
            //
            if (m_rotarLocal) {
                int iJugador = m_partit.getJugadorsLocal()[0];
                int i=0;
                for (i=0;i<5;i++) {
                    m_partit.getJugadorsLocal()[i] = m_partit.getJugadorsLocal()[i + 1];
                }
                // El 6è jugador passa a tenir el primer
                m_partit.getJugadorsLocal()[i] = iJugador;
                //  Si n'hi ha rotació Local, i el sacador de l'equip contrari és Central, ha de fer el canvi amb el Libero
                comprobarCanviLibero1(m_partit.getJugadorsEquipVisitant(), true);
                comprobarCanviLibero4(m_partit.getJugadorsEquipLocal(), true);
            }
            //
            if (m_rotarVisitant) {
                int iJugador = m_partit.getJugadorsVisitant()[0];
                int i=0;
                for (i=0;i<5;i++) {
                    m_partit.getJugadorsVisitant()[i] = m_partit.getJugadorsVisitant()[i + 1];
                }
                // El 6è jugador passa a tenir el primer
                m_partit.getJugadorsVisitant()[i] = iJugador;
                //  Si n'hi ha rotació Visitant, i el sacador de l'equip contrari és Central, ha de fer el canvi amb el Libero
                comprobarCanviLibero1(m_partit.getJugadorsEquipLocal(), false);
                comprobarCanviLibero4(m_partit.getJugadorsEquipVisitant(), false);
            }
            m_tvPosicio11.setText(Utils.num2String(m_partit.getJugadorsLocal()[0], 1));
            getJugadorPosicio(m_tvPosicio11, m_partit.getJugadorsEquipLocal(), m_partit.getJugadorsLocal()[0]);
            m_tvPosicio12.setText(Utils.num2String(m_partit.getJugadorsLocal()[1], 1));
            getJugadorPosicio(m_tvPosicio12, m_partit.getJugadorsEquipLocal(), m_partit.getJugadorsLocal()[1]);
            m_tvPosicio13.setText(Utils.num2String(m_partit.getJugadorsLocal()[2], 1));
            getJugadorPosicio(m_tvPosicio13, m_partit.getJugadorsEquipLocal(), m_partit.getJugadorsLocal()[2]);
            m_tvPosicio14.setText(Utils.num2String(m_partit.getJugadorsLocal()[3], 1));
            getJugadorPosicio(m_tvPosicio14, m_partit.getJugadorsEquipLocal(), m_partit.getJugadorsLocal()[3]);
            m_tvPosicio15.setText(Utils.num2String(m_partit.getJugadorsLocal()[4], 1));
            getJugadorPosicio(m_tvPosicio15, m_partit.getJugadorsEquipLocal(), m_partit.getJugadorsLocal()[4]);
            m_tvPosicio16.setText(Utils.num2String(m_partit.getJugadorsLocal()[5], 1));
            getJugadorPosicio(m_tvPosicio16, m_partit.getJugadorsEquipLocal(), m_partit.getJugadorsLocal()[5]);
            //
            m_tvPosicio21.setText(Utils.num2String(m_partit.getJugadorsVisitant()[0],1));
            getJugadorPosicio(m_tvPosicio21, m_partit.getJugadorsEquipVisitant(), m_partit.getJugadorsVisitant()[0]);
            m_tvPosicio22.setText(Utils.num2String(m_partit.getJugadorsVisitant()[1],1));
            getJugadorPosicio(m_tvPosicio22, m_partit.getJugadorsEquipVisitant(), m_partit.getJugadorsVisitant()[1]);
            m_tvPosicio23.setText(Utils.num2String(m_partit.getJugadorsVisitant()[2],1));
            getJugadorPosicio(m_tvPosicio23, m_partit.getJugadorsEquipVisitant(), m_partit.getJugadorsVisitant()[2]);
            m_tvPosicio24.setText(Utils.num2String(m_partit.getJugadorsVisitant()[3],1));
            getJugadorPosicio(m_tvPosicio24, m_partit.getJugadorsEquipVisitant(), m_partit.getJugadorsVisitant()[3]);
            m_tvPosicio25.setText(Utils.num2String(m_partit.getJugadorsVisitant()[4],1));
            getJugadorPosicio(m_tvPosicio25, m_partit.getJugadorsEquipVisitant(), m_partit.getJugadorsVisitant()[4]);
            m_tvPosicio26.setText(Utils.num2String(m_partit.getJugadorsVisitant()[5],1));
            getJugadorPosicio(m_tvPosicio26, m_partit.getJugadorsEquipVisitant(), m_partit.getJugadorsVisitant()[5]);
            // Llista de Jugadors
            actualizarAdapter();
        }
        // Temps morts Local
        if (m_partit.getTempsMortLocal() == 0) {
            m_ivTempsMortLocal1.setImageResource(R.mipmap.ic_bola_gris_comp);
            m_ivTempsMortLocal2.setImageResource(R.mipmap.ic_bola_gris_comp);
        }
        else if (m_partit.getTempsMortLocal() == 1) {
            m_ivTempsMortLocal1.setImageResource(R.mipmap.ic_bola_roja_comp);
            m_ivTempsMortLocal2.setImageResource(R.mipmap.ic_bola_gris_comp);
        }
        else {
            m_ivTempsMortLocal1.setImageResource(R.mipmap.ic_bola_roja_comp);
            m_ivTempsMortLocal2.setImageResource(R.mipmap.ic_bola_roja_comp);
        }
        // Temps morts Visitant
        if (m_partit.getTempsMortVisitant() == 0) {
            m_ivTempsMortVisitant1.setImageResource(R.mipmap.ic_bola_gris_comp);
            m_ivTempsMortVisitant2.setImageResource(R.mipmap.ic_bola_gris_comp);
        }
        else if (m_partit.getTempsMortVisitant() == 1) {
            m_ivTempsMortVisitant1.setImageResource(R.mipmap.ic_bola_roja_comp);
            m_ivTempsMortVisitant2.setImageResource(R.mipmap.ic_bola_gris_comp);
        }
        else {
            m_ivTempsMortVisitant1.setImageResource(R.mipmap.ic_bola_roja_comp);
            m_ivTempsMortVisitant2.setImageResource(R.mipmap.ic_bola_roja_comp);
        }
    }

    private void comprobarCanviLibero1(ArrayList<Jugador> pJugadors, boolean pIsLocal) {
        ArrayList<Integer> iLiberos = new ArrayList<Integer>();
        ArrayList<Integer> iCentrales = new ArrayList<Integer>();
        // Obtiene todos los liberos y centrales del equipo
        for (Jugador jugador : pJugadors) {
            if (jugador.getPosicio().equals(getString(R.string.libero)))
                iLiberos.add(jugador.getDorsal());
            else if (jugador.getPosicio().equals(getString(R.string.central)))
                iCentrales.add(jugador.getDorsal());
        }
        // Si en la posición 1 hay un central, hay que sustuirlo por un Líbero. En este caso el primero de la lista de líberos
        for (Integer iJugador : iCentrales) {
            // Comprovar canvi a la posició 1
            //iDorsal = Utils.convertString2Integer(m_tvPosicio11.getText().toString());
            String strDorsal = (pIsLocal ? m_tvPosicio21.getText().toString() : m_tvPosicio11.getText().toString());
            if (iJugador.intValue() == Utils.convertString2Integer(strDorsal).intValue()) {
                if (pIsLocal)
                    m_partit.getJugadorsVisitant()[0] = Utils.convertString2Integer(iLiberos.get(0).toString()).intValue();
                else
                    m_partit.getJugadorsLocal()[0] = Utils.convertString2Integer(iLiberos.get(0).toString()).intValue();
                //break;
            }
        }
    }
    private void comprobarCanviLibero4(ArrayList<Jugador> pJugadors, boolean pIsLocal) {
        ArrayList<Integer> iLiberos = new ArrayList<Integer>();
        ArrayList<Integer> iCentrales = new ArrayList<Integer>();
        Integer iDorsal = null;
        // Obtiene todos los liberos y centrales del equipo
        for (Jugador jugador : pJugadors) {
            if (jugador.getPosicio().equals(getString(R.string.libero)))
                iLiberos.add(jugador.getDorsal());
            else if (jugador.getPosicio().equals(getString(R.string.central)))
                iCentrales.add(jugador.getDorsal());
        }
        // Si en la posición 4 hay un líbero, hay que sustuirlo por un Central. En este caso el central que no esté en el campo
        for (Integer iJugador : iLiberos) {
            // Comprovar canvi a la posició 4
            //iDorsal = Utils.convertString2Integer(m_tvPosicio14.getText().toString());
            String strDorsal = (pIsLocal ? m_tvPosicio15.getText().toString() : m_tvPosicio25.getText().toString());
            if (iJugador.intValue() == Utils.convertString2Integer(strDorsal).intValue()) {
                if (pIsLocal)
                    m_partit.getJugadorsLocal()[3] = centralLliure(pIsLocal, iCentrales);
                else
                    m_partit.getJugadorsVisitant()[3] = centralLliure(pIsLocal, iCentrales);
                break;
            }
        }
    }

    private int centralLliure(boolean pIsLocal, ArrayList<Integer> pCentrales) {
        int iCentral = 0;
        for (Integer iJugador : pCentrales) {
            if (pIsLocal) {
                if (iJugador.intValue() != m_partit.getJugadorsLocal()[0] &&
                        iJugador.intValue() != m_partit.getJugadorsLocal()[1] &&
                        iJugador.intValue() != m_partit.getJugadorsLocal()[2] &&
                        iJugador.intValue() != m_partit.getJugadorsLocal()[3] &&
                        iJugador.intValue() != m_partit.getJugadorsLocal()[4] &&
                        iJugador.intValue() != m_partit.getJugadorsLocal()[5]) {
                    iCentral = iJugador.intValue();
                    break;
                }
            } else {
                if (iJugador.intValue() != m_partit.getJugadorsVisitant()[0] &&
                        iJugador.intValue() != m_partit.getJugadorsVisitant()[1] &&
                        iJugador.intValue() != m_partit.getJugadorsVisitant()[2] &&
                        iJugador.intValue() != m_partit.getJugadorsVisitant()[3] &&
                        iJugador.intValue() != m_partit.getJugadorsVisitant()[4] &&
                        iJugador.intValue() != m_partit.getJugadorsVisitant()[5]) {
                    iCentral = iJugador.intValue();
                    break;
                }
            }
        }
        return iCentral;
    }

    private void getJugadorPosicio(TextView m_tvPosicio, ArrayList<Jugador> arrJugador, int pDorsal) {
        for (Jugador jugador : arrJugador) {
            if (jugador.getDorsal() == pDorsal) {
                if (jugador.getPosicio().equals(getString(R.string.libero))) {        // LI
                    m_tvPosicio.setTextColor(Color.MAGENTA);
                    m_tvPosicio.setTypeface(Typeface.DEFAULT_BOLD);
                    return;
                }
                else if (jugador.getPosicio().equals(getString(R.string.central))) {   // CE
                    m_tvPosicio.setTextColor(Color.RED);
                    m_tvPosicio.setTypeface(Typeface.DEFAULT_BOLD);
                    return;
                }
                else {
                    m_tvPosicio.setTextColor(Color.BLACK);
                    m_tvPosicio.setTypeface(Typeface.DEFAULT_BOLD);
                    return;
                }
            }
        }
        m_tvPosicio.setTextColor(Color.GRAY);
        m_tvPosicio.setTypeface(Typeface.DEFAULT);
    }

    /*
    private void guardarHistorialEstadistiques() {
        Estadistica estadistica = null;
        try {
            estadistica = (Estadistica)m_darreraEstadistica.clone();
            m_estadistiquesHistorial.add(estadistica);
        } catch (CloneNotSupportedException e) {
            System.out.println("Error - guardarHistorialEstadistiques: " + e);
        }
    }
     */
    private void guardarHistorialPartit() {
        PartitMarcador partit = null;
        try {
            partit = (PartitMarcador)m_partit.clone();
            m_partitsHistorial.add(partit);
        } catch (CloneNotSupportedException e) {
            System.out.println("Error - guardarHistorialPartit: " + e);
        }
    }
    /*
    Sol·licita els permissos de READ i WRITE del dispositiu
     */
    private void sollicitarPermissos() {
        FilesDao.sollicitarPermissos(this);
    }

    private void obtenirDadesPartit() {
        PartitGeneric partitMarcador1 = null;
        PartitMarcador partitMarcador2 = null;
        try {
            //partit = (Partit) recuperarPartit2SPComp(CTE_CLAVE_COMPETICIO_PARTIT);
            Intent anIntent = getIntent();
            Bundle bParams = anIntent.getExtras();
            if(bParams!=null)
            {
                /*
                String strAux = (String)bParams.get(CTE_CLAVE_COMPETICIO_PARTIT);
                String[] arrAux = strAux.split(";");
                partit = new Partit();
                partit.setEquipLocal(arrAux[0]);
                partit.setEquipVisitant(arrAux[1]);
                 */
                partitMarcador1 = (PartitGeneric) Serializar.desSerializar((String)bParams.get(CTE_CLAVE_COMPETICIO_PARTIT));
            }
        } catch(Exception ex) {
            System.out.println("Error - obtenirDadesPartit: " + ex);
        }
        try{
            partitMarcador2 = (PartitMarcador) recuperarPartit2SP(CTE_CLAVE_COMPETICIO_PARTIT_MARCADOR);
        } catch(Exception ex) {
            System.out.println("Error - obtenirDadesPartit" + ex);
        }
        //  Copia los atributos del partido externo y los del propio Marcador
        m_partit = copyPropertiesPartit(partitMarcador1, partitMarcador2);
        //  Inicializa las estadísticas
        m_estadistiques = new ArrayList<Estadistica>();
    }

    private PartitMarcador copyPropertiesPartit(PartitGeneric pPartit1, PartitMarcador pPartit2) {
        PartitMarcador partitMarcador = new PartitMarcador();
        //  Noms d'equips LOCAL i VISITANT
        if (pPartit1 != null && !pPartit1.getEquipLocal().equals("")) {
            partitMarcador.setEquipLocal(pPartit1.getEquipLocal());
            partitMarcador.setEquipVisitant(pPartit1.getEquipVisitant());
        }
        else {
            if (pPartit2 != null && !pPartit2.getEquipLocal().equals("")) {
                partitMarcador.setEquipLocal(pPartit2.getEquipLocal());
                partitMarcador.setEquipVisitant(pPartit2.getEquipVisitant());
            }
            else {
                partitMarcador.setEquipLocal(getString(R.string.titol_local));
                partitMarcador.setEquipVisitant(getString(R.string.titol_visitant));
            }
        }
        if (pPartit2 != null) {
            // Sets i resultats del SH
            partitMarcador.setNumSetActual(pPartit2.getNumSetActual());
            partitMarcador.setResultatSetLocal(pPartit2.getResultatSetLocal());
            partitMarcador.setResultatSetVisitant(pPartit2.getResultatSetVisitant());
            partitMarcador.setResultatPuntsLocal(pPartit2.getResultatPuntsLocal());
            partitMarcador.setResultatPuntsVisitant(pPartit2.getResultatPuntsVisitant());
            partitMarcador.setPuntsSets(pPartit2.getPuntsSets());
            partitMarcador.setTempsMortLocal(pPartit2.getTempsMortLocal());
            partitMarcador.setTempsMortVisitant(pPartit2.getTempsMortVisitant());
            partitMarcador.setPilotaSaqueLocal(pPartit2.isPilotaSaqueLocal());
            partitMarcador.setJugadorsLocal(pPartit2.getJugadorsLocal());
            partitMarcador.setJugadorsVisitant(pPartit2.getJugadorsVisitant());
            //  Jugadors equip local
            partitMarcador.setJugadorsEquipLocal(new ArrayList<Jugador>());
            if (pPartit2.getJugadorsEquipLocal() != null &&  pPartit2.getJugadorsEquipLocal().size() > 0) {
                // Jugadors del Marcador introduits a mà
                for (Jugador jugador : pPartit2.getJugadorsEquipLocal()) {
                    partitMarcador.getJugadorsEquipLocal().add(jugador);
                }
            }
            else if (pPartit1!=null && pPartit1.getJugadorsEquipLocal()!=null && pPartit1.getJugadorsEquipLocal().size() > 0) {
                // Jugadors de l'agenda de partits introduits a la BBDD
                for (es.xuan.generic.model.Jugador jugador : pPartit1.getJugadorsEquipLocal()) {
                    copyPropertiesJugador(partitMarcador.getJugadorsEquipLocal(), jugador);
                    //partitMarcador.getJugadorsEquipLocal().add(jugador);
                }
            }
            //  Jugadors equip visitant
            partitMarcador.setJugadorsEquipVisitant(new ArrayList<Jugador>());
            if (pPartit2.getJugadorsEquipVisitant() != null &&  pPartit2.getJugadorsEquipVisitant().size() > 0) {
                // Jugadors del Marcador introduits a mà
                for (Jugador jugador : pPartit2.getJugadorsEquipVisitant()) {
                    partitMarcador.getJugadorsEquipVisitant().add(jugador);
                }
            }
            else if (pPartit1!=null && pPartit1.getJugadorsEquipVisitant()!=null && pPartit1.getJugadorsEquipVisitant().size() > 0) {
                // Jugadors de l'agenda de partits introduits a la BBDD
                for (es.xuan.generic.model.Jugador jugador : pPartit1.getJugadorsEquipVisitant()) {
                    copyPropertiesJugador(partitMarcador.getJugadorsEquipVisitant(), jugador);
                    //partitMarcador.getJugadorsEquipVisitant().add(jugador);
                }
            }
        }
        else {
            // Sets i resultats NOUS - INICIALITZACIÓ
            partitMarcador.setNumSetActual(1);
            partitMarcador.setResultatSetLocal(0);
            partitMarcador.setResultatSetVisitant(0);
            partitMarcador.setResultatPuntsLocal(0);
            partitMarcador.setResultatPuntsVisitant(0);
            int[][] puntsSets = {{0,0},{0,0},{0,0},{0,0},{0,0}};
            partitMarcador.setPuntsSets(puntsSets);
            int[] jugadorsLocal = {0,0,0,0,0,0};
            partitMarcador.setJugadorsLocal(jugadorsLocal);
            int[] jugadorsVisitant = {0,0,0,0,0,0};
            partitMarcador.setJugadorsVisitant(jugadorsVisitant);
            partitMarcador.setTempsMortLocal(0);
            partitMarcador.setTempsMortVisitant(0);
            partitMarcador.setPilotaSaqueLocal(true);
            partitMarcador.setJugadorsEquipLocal(new ArrayList<Jugador>());
            partitMarcador.setJugadorsEquipVisitant(new ArrayList<Jugador>());
            partitMarcador.setEquipLocal(getString(R.string.titol_local));
            partitMarcador.setEquipVisitant(getString(R.string.titol_visitant));
        }
        return partitMarcador;
    }

    private void copyPropertiesJugador(ArrayList<Jugador> pJugadorsMarcador, es.xuan.generic.model.Jugador pJugadorGen) {
        Jugador jugadorAux = new Jugador();
        jugadorAux.setNom(pJugadorGen.getNom());
        jugadorAux.setCognoms(pJugadorGen.getCognoms());
        jugadorAux.setPosicio(pJugadorGen.getPosicio());
        jugadorAux.setDorsal(pJugadorGen.getDorsal());
        pJugadorsMarcador.add(jugadorAux);
    }

    // Vibrate for 150 milliseconds
    private void vibrar(long iTempsMS) {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(iTempsMS,10));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(iTempsMS);
        }
    }
    private void inicialitzarPantalla() {
        //  Inicialitzar Historial
        m_partitsHistorial = new ArrayList<>();
        //m_estadistiquesHistorial = new ArrayList<>();
        // SharedPreferences
        m_spDades = getSharedPreferences(getString(R.string.app_name_sh), MODE_PRIVATE);
        //
        m_llTempsMortLocal = (LinearLayout)findViewById(R.id.llTempsMortLocal);
        m_llTempsMortLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                vibrar(CTE_VIBRATION_MS);
                addTempsMort(true);
            }
        });
        m_llTempsMortVisitant = (LinearLayout)findViewById(R.id.llTempsMortVisitant);
        m_llTempsMortVisitant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                vibrar(CTE_VIBRATION_MS);
                addTempsMort(false);
            }
        });
        m_tvTitolLocal = (TextView)findViewById(R.id.tvTitolLocal);
        m_tvTitolVisitant = (TextView)findViewById(R.id.tvTitolVisitant);
        m_tvMarcadorLocal = (TextView)findViewById(R.id.tvMarcadorLocal);
        m_tvMarcadorVisitant = (TextView)findViewById(R.id.tvMarcadorVisitant);
        m_tvSetActual = (TextView)findViewById(R.id.tvSetActual);
        m_tvSet1 = (TextView)findViewById(R.id.tvSet1);
        m_tvSet2 = (TextView)findViewById(R.id.tvSet2);
        m_tvSet3 = (TextView)findViewById(R.id.tvSet3);
        m_tvSet4 = (TextView)findViewById(R.id.tvSet4);
        m_tvSet5 = (TextView)findViewById(R.id.tvSet5);
        m_tvMarcadorPuntsLocal = (TextView)findViewById(R.id.tvMarcadorPuntsLocal);
        m_tvMarcadorPuntsLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                vibrar(CTE_VIBRATION_MS);
                realitzarAccio(true);
            }
        });
        m_tvMarcadorPuntsLocal.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                m_partit.setPilotaSaqueLocal(!m_partit.isPilotaSaqueLocal());
                blinkMarcador();
                return true;
            }
        });
        m_tvMarcadorPuntsVisitant = (TextView)findViewById(R.id.tvMarcadorPuntsVisitant);
        m_tvMarcadorPuntsVisitant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                vibrar(CTE_VIBRATION_MS);
                realitzarAccio(false);
            }
        });
        m_tvMarcadorPuntsVisitant.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                m_partit.setPilotaSaqueLocal(!m_partit.isPilotaSaqueLocal());
                blinkMarcador();
                return true;
            }
        });
        m_ivTempsMortLocal1 = (ImageView)findViewById(R.id.ivTempsMortLocal1);
        m_ivTempsMortLocal1.setImageResource(R.mipmap.ic_bola_gris_comp);
        m_ivTempsMortLocal2 = (ImageView)findViewById(R.id.ivTempsMortLocal2);
        m_ivTempsMortLocal2.setImageResource(R.mipmap.ic_bola_gris_comp);
        m_ivTempsMortVisitant1 = (ImageView)findViewById(R.id.ivTempsMortVisitant1);
        m_ivTempsMortVisitant1.setImageResource(R.mipmap.ic_bola_gris_comp);
        m_ivTempsMortVisitant2 = (ImageView)findViewById(R.id.ivTempsMortVisitant2);
        m_ivTempsMortVisitant2.setImageResource(R.mipmap.ic_bola_gris_comp);
        m_ivEscutLocal = (ImageView)findViewById(R.id.ivEscutLocal);
        m_ivEscutVisitant = (ImageView)findViewById(R.id.ivEscutVisitant);
        m_fabAceServe = (FloatingActionButton)findViewById(R.id.fabAceServe);
        m_fabAceServe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                vibrar(CTE_VIBRATION_MS);
                afegirAccioEstadistica(pView);
            }
        });
        m_fabAltres = (FloatingActionButton)findViewById(R.id.fabAltresPoint);
        m_fabAltres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                vibrar(CTE_VIBRATION_MS);
                afegirAccioEstadistica(pView);
            }
        });
        m_fabAltresError = (FloatingActionButton)findViewById(R.id.fabAltresError);
        m_fabAltresError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                vibrar(CTE_VIBRATION_MS);
                afegirAccioEstadistica(pView);
            }
        });
        m_fabAltresCancel = (FloatingActionButton)findViewById(R.id.fabCancella);
        m_fabAltresCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                vibrar(CTE_VIBRATION_MS);
                afegirAccioEstadistica(pView);
            }
        });
        m_fabAttackError = (FloatingActionButton)findViewById(R.id.fabAttackError);
        m_fabAttackError.setOnTouchListener(this);
        m_fabAttackPoint = (FloatingActionButton)findViewById(R.id.fabAttackPoint);
        m_fabAttackPoint.setOnTouchListener(this);
        m_fabErrorOnSet = (FloatingActionButton)findViewById(R.id.fabErrorOnSet);
        m_fabErrorOnSet.setOnTouchListener(this);
        m_fabServeError = (FloatingActionButton)findViewById(R.id.fabServeError);
        m_fabServeError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                vibrar(CTE_VIBRATION_MS);
                afegirAccioEstadistica(pView);
            }
        });
        m_fabWiningBlock = (FloatingActionButton)findViewById(R.id.fabWiningBlock);
        m_fabWiningBlock.setOnTouchListener(this);
        m_fabAddJugador = (FloatingActionButton)findViewById(R.id.fabAddJugador);
        m_fabAddJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                vibrar(CTE_VIBRATION_MS);
                afegirJugador(pView, true);
            }
        });
        m_fabResetMarcador = (FloatingActionButton)findViewById(R.id.fabResetMarcador);
        m_fabResetMarcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                vibrar(CTE_VIBRATION_MS);
                resetMarcadorAlert(pView, true);
            }
        });
        m_fabBackMarcador = (FloatingActionButton)findViewById(R.id.fabBackMarcador);
        m_fabBackMarcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                vibrar(CTE_VIBRATION_MS);
                backupMarcadorAlert(pView, true);
            }
        });
        m_tvPosicio11 = (TextView)findViewById(R.id.tvPosicio11);
        m_tvPosicio11.setOnDragListener(this);
        m_tvPosicio12 = (TextView)findViewById(R.id.tvPosicio12);
        m_tvPosicio12.setOnDragListener(this);
        m_tvPosicio13 = (TextView)findViewById(R.id.tvPosicio13);
        m_tvPosicio13.setOnDragListener(this);
        m_tvPosicio14 = (TextView)findViewById(R.id.tvPosicio14);
        m_tvPosicio14.setOnDragListener(this);
        m_tvPosicio15 = (TextView)findViewById(R.id.tvPosicio15);
        m_tvPosicio15.setOnDragListener(this);
        m_tvPosicio16 = (TextView)findViewById(R.id.tvPosicio16);
        m_tvPosicio16.setOnDragListener(this);
        m_tvPosicio21 = (TextView)findViewById(R.id.tvPosicio21);
        m_tvPosicio21.setOnDragListener(this);
        m_tvPosicio22 = (TextView)findViewById(R.id.tvPosicio22);
        m_tvPosicio22.setOnDragListener(this);
        m_tvPosicio23 = (TextView)findViewById(R.id.tvPosicio23);
        m_tvPosicio23.setOnDragListener(this);
        m_tvPosicio24 = (TextView)findViewById(R.id.tvPosicio24);
        m_tvPosicio24.setOnDragListener(this);
        m_tvPosicio25 = (TextView)findViewById(R.id.tvPosicio25);
        m_tvPosicio25.setOnDragListener(this);
        m_tvPosicio26 = (TextView)findViewById(R.id.tvPosicio26);
        m_tvPosicio26.setOnDragListener(this);
        m_lvJugadorsLocal = (ListView)findViewById(R.id.lvJugadorsLocal);
        m_lvJugadorsVisitant = (ListView)findViewById(R.id.lvJugadorsVisitant);
        //
        LayoutInflater inflater = LayoutInflater.from(this);
        m_vJugadorNou = inflater.inflate(R.layout.alert_jugador_new, null);
        m_adAlertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_AppCompat))
                .setView(m_vJugadorNou)
                .setPositiveButton(getString(R.string.bt_afegir_jugador_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        afegirJugadorLlistaAlertDialog(m_vJugadorNou);

                    }
                })
                .setNegativeButton(getString(R.string.bt_afegir_jugador_cancel), null)
                .create();
    }

    private void backupMarcadorAlert(View pView, boolean b) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_AppCompat));
        alertDialogBuilder
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.alert_backup_marcador))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.reset_si),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        backupMarcador();
                        actualitzarDadesPantalla(true, true, true);
                        guardarInfoPartit2SP(CTE_CLAVE_COMPETICIO_PARTIT_MARCADOR, m_partit);
                    }
                })
                .setNegativeButton(getString(R.string.reset_no),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void backupMarcador() {
        int iLastReg = m_partitsHistorial.size() - 1;
        if (iLastReg > 0) {
            m_partit = m_partitsHistorial.get(iLastReg - 1);
            m_partitsHistorial.remove(iLastReg);
            m_partitsHistorial.remove(iLastReg-1);
        }
    }

    private void addTempsMort(boolean pIsLccal) {
        int iTempsMort = 0;
        if (pIsLccal) {
            iTempsMort = ((m_partit.getTempsMortLocal() + 1) % 3);
            if (iTempsMort == 0) {
                m_ivTempsMortLocal1.setImageResource(R.mipmap.ic_bola_gris_comp);
                m_ivTempsMortLocal2.setImageResource(R.mipmap.ic_bola_gris_comp);
            }
            else if (iTempsMort == 1) {
                m_ivTempsMortLocal1.setImageResource(R.mipmap.ic_bola_roja_comp);
                m_ivTempsMortLocal2.setImageResource(R.mipmap.ic_bola_gris_comp);
            }
            else {
                m_ivTempsMortLocal1.setImageResource(R.mipmap.ic_bola_roja_comp);
                m_ivTempsMortLocal2.setImageResource(R.mipmap.ic_bola_roja_comp);
            }
            m_partit.setTempsMortLocal(iTempsMort);
        }
        else {
            iTempsMort = ((m_partit.getTempsMortVisitant() + 1) % 3);
            if (iTempsMort == 0) {
                m_ivTempsMortVisitant1.setImageResource(R.mipmap.ic_bola_gris_comp);
                m_ivTempsMortVisitant2.setImageResource(R.mipmap.ic_bola_gris_comp);
            }
            else if (iTempsMort == 1) {
                m_ivTempsMortVisitant1.setImageResource(R.mipmap.ic_bola_roja_comp);
                m_ivTempsMortVisitant2.setImageResource(R.mipmap.ic_bola_gris_comp);
            }
            else {
                m_ivTempsMortVisitant1.setImageResource(R.mipmap.ic_bola_roja_comp);
                m_ivTempsMortVisitant2.setImageResource(R.mipmap.ic_bola_roja_comp);
            }
            m_partit.setTempsMortVisitant(iTempsMort);
        }
        afegirEstadisticaTempsMort(pIsLccal);
    }

    private void afegirEstadisticaCanviJugador(boolean pIsLccal, int pJugador1, int pJugador2) {
        Estadistica estadistica = new Estadistica();
        estadistica.setTemps(new Date());
        estadistica.setAccio(getString(R.string.seleccio_accio_canvi_jugador));
        estadistica.setNum_set(Utils.convertString2Integer(m_tvSetActual.getText().toString()));
        estadistica.setSetLocal(Utils.convertString2Integer(m_tvMarcadorLocal.getText().toString()));
        estadistica.setSetVisitant(Utils.convertString2Integer(m_tvMarcadorVisitant.getText().toString()));
        estadistica.setNum_jugador(0);
        if (pIsLccal)
            estadistica.setNom_equip(m_tvTitolLocal.getText().toString());
        else
            estadistica.setNom_equip(m_tvTitolVisitant.getText().toString());
        estadistica.setPuntsLocal(pJugador1);       // Jugador que surt
        estadistica.setPuntsVisitant(pJugador2);    // Jugador qeu entra
        //
        m_estadistiques.add(estadistica);
    }
    private void afegirEstadisticaTempsMort(boolean pIsLccal) {
        Estadistica estadistica = new Estadistica();
        estadistica.setTemps(new Date());
        estadistica.setAccio(getString(R.string.seleccio_accio_temps_mort));
        estadistica.setNum_set(Utils.convertString2Integer(m_tvSetActual.getText().toString()));
        estadistica.setSetLocal(Utils.convertString2Integer(m_tvMarcadorLocal.getText().toString()));
        estadistica.setSetVisitant(Utils.convertString2Integer(m_tvMarcadorVisitant.getText().toString()));
        estadistica.setNum_jugador(0);
        if (pIsLccal)
            estadistica.setNom_equip(m_tvTitolLocal.getText().toString());
        else
            estadistica.setNom_equip(m_tvTitolVisitant.getText().toString());
        estadistica.setPuntsLocal(m_partit.getTempsMortLocal());
        estadistica.setPuntsVisitant(m_partit.getTempsMortVisitant());
        //
        m_estadistiques.add(estadistica);
    }

    private void resetMarcador() {
        // Sets i resultats NOUS - INICIALITZACIÓ
        m_partit.setEquipLocal(getString(R.string.titol_local));
        m_partit.setEquipVisitant(getString(R.string.titol_visitant));
        m_partit.setNumSetActual(1);
        m_partit.setResultatSetLocal(0);
        m_partit.setResultatSetVisitant(0);
        m_partit.setResultatPuntsLocal(0);
        m_partit.setResultatPuntsVisitant(0);
        int[][] puntsSets = {{0,0},{0,0},{0,0},{0,0},{0,0}};
        m_partit.setPuntsSets(puntsSets);
        m_partit.setTempsMortLocal(0);
        m_partit.setTempsMortVisitant(0);
        m_partit.setPilotaSaqueLocal(true);
        m_partit.setJugadorsEquipLocal(new ArrayList<Jugador>());
        m_partit.setJugadorsEquipVisitant(new ArrayList<Jugador>());
        int[] jugadorsLocal = {0,0,0,0,0,0};
        m_partit.setJugadorsLocal(jugadorsLocal);
        int[] jugadorsVisitant = {0,0,0,0,0,0};
        m_partit.setJugadorsVisitant(jugadorsVisitant);
        //
        m_darreraEstadistica = new Estadistica();
        m_darreraEstadistica.setNum_set(1);
        m_darreraEstadistica.setPuntsLocal(0);
        m_darreraEstadistica.setPuntsVisitant(0);
        m_darreraEstadistica.setSetLocal(0);
        m_darreraEstadistica.setSetVisitant(0);
    }

    private void resetMarcadorAlert(View pView, boolean b) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_AppCompat));
        alertDialogBuilder
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.alert_reset_marcador))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.reset_si),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        resetMarcador();
                        actualitzarDadesPantalla(true, true, true);
                        guardarInfoPartit2SP(CTE_CLAVE_COMPETICIO_PARTIT_MARCADOR, m_partit);
                    }
                })
                .setNegativeButton(getString(R.string.reset_no),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void afegirJugadorLlistaAlertDialog(View dialog) {
        Jugador jugador = crearJugador(dialog);
        afegirJugadorLlista(dialog, jugador);
        // Actualitza Pantalla
        actualitzarDadesPantalla(false, false, true);
    }

    private Jugador crearJugador(View dialog) {
        EditText etNumJugador = (EditText)dialog.findViewById(R.id.etNumJugador);
        EditText etNomJugador = (EditText)dialog.findViewById(R.id.etNomJugador);
        RadioButton rbLibero = (RadioButton)dialog.findViewById(R.id.rbEsLibero);
        RadioButton rbCentral = (RadioButton)dialog.findViewById(R.id.rbEsCentral);
        RadioButton rbAltre = (RadioButton)dialog.findViewById(R.id.rbAltre);
        String strJugaodr = etNumJugador.getText() + ";" +
                //(etNomJugador.getText().toString() == null || etNomJugador.getText().toString().equals("") ? getString(R.string.nom_jugador_aux) : etNomJugador.getText().toString()) + ";" +
                etNomJugador.getText().toString() +
                ";" +
                ";" +
                ";" +
                ";" +
                getPosicionJugador(rbLibero, rbCentral, rbAltre);
        return new Jugador(strJugaodr);
    }

    private String getPosicionJugador(RadioButton pRbLibero, RadioButton pRbCentral, RadioButton pRbAltre) {
        return (pRbLibero.isChecked() ? getString(R.string.libero) : "") +
                (pRbCentral.isChecked() ? getString(R.string.central) : "") +
                (pRbAltre.isChecked() ? getString(R.string.altres) : "");
    }

    private void afegirJugadorLlista(View pDialog, Jugador pJugador) {
        Switch swSwitchEquip = (Switch)pDialog.findViewById(R.id.swEquipLocal);
        if (!swSwitchEquip.isChecked()) {    // TRUE -> és Local
            // Afegir jugador a la llista de Locals
            afegirJugadorLlistaArr(m_partit.getJugadorsEquipLocal(), pJugador);
            ArrayList<Jugador> arrJugAux = ordenarJugadorLlistaArr(m_partit.getJugadorsEquipLocal());
            m_partit.setJugadorsEquipLocal(arrJugAux);
        }
        else {
            // Afegir jugador a la llista de Visitants
            afegirJugadorLlistaArr(m_partit.getJugadorsEquipVisitant(), pJugador);
            ArrayList<Jugador> arrJugAux = ordenarJugadorLlistaArr(m_partit.getJugadorsEquipVisitant());
            m_partit.setJugadorsEquipVisitant(arrJugAux);
        }
    }

    private ArrayList<Jugador> ordenarJugadorLlistaArr(ArrayList<Jugador> pArrJugadors) {
        Hashtable<Integer, Jugador> hmJugadors = new Hashtable<Integer, Jugador>();
        // Comprova si n'hi ha de repetits - Es deixa el darrer en arribar
        for (Jugador jugador : pArrJugadors) {
            if (jugador.getDorsal() != null)
                hmJugadors.put(new Integer(jugador.getDorsal()), jugador);
        }
        // Ordenar llista
        ArrayList<Jugador> arrJugadors = new ArrayList<Jugador>(hmJugadors.values());
        Collections.sort(arrJugadors);
        return arrJugadors;
    }

    private void afegirJugadorLlistaArr(ArrayList<Jugador> pArrJugadors, Jugador pJugador) {
        pArrJugadors.add(pJugador);
    }

    private void afegirJugador(View pView, boolean b) {
        m_adAlertDialog.show();
    }

    private void afegirAccioEstadistica(View pView) {
        String strAccio = pView.getContentDescription().toString().trim();
        if (strAccio.equals(getString(R.string.seleccio_accio_cancel))) {
            // Acció de Cancel·lar
            mostrarBotonsAccions(View.INVISIBLE);
            return;
        }
        // Altres accions
        if (m_darreraEstadistica != null && m_darreraEstadistica.getNum_set() > 0) {
            m_darreraEstadistica.setAccio(strAccio);
            m_darreraEstadistica.setNum_set(Utils.convertString2Integer(m_tvSetActual.getText().toString()));
            m_darreraEstadistica.setSetLocal(Utils.convertString2Integer(m_tvMarcadorLocal.getText().toString()));
            m_darreraEstadistica.setSetVisitant(Utils.convertString2Integer(m_tvMarcadorVisitant.getText().toString()));
            if (m_darreraEstadistica.isLocal()) {
                // Si l'acció és ALTRES, el jugador és el #0
                if (strAccio.equals(getString(R.string.seleccio_accio_altres_error))) {
                    m_darreraEstadistica.setNum_jugador(0);
                    m_darreraEstadistica.setNom_equip(m_tvTitolVisitant.getText().toString());
                }
                else if (strAccio.equals(getString(R.string.seleccio_accio_altres))) {
                    m_darreraEstadistica.setNum_jugador(0);
                    m_darreraEstadistica.setNom_equip(m_tvTitolLocal.getText().toString());
                }
                else if (strAccio.equals(getString(R.string.seleccio_accio_ace_server))) {
                    m_darreraEstadistica.setNum_jugador(Utils.convertString2Integer(m_tvPosicio11.getText().toString()));
                    m_darreraEstadistica.setNom_equip(m_tvTitolLocal.getText().toString());
                }
                else if (strAccio.equals(getString(R.string.seleccio_accio_serve_error))) {
                    m_darreraEstadistica.setNum_jugador(Utils.convertString2Integer(m_tvPosicio21.getText().toString()));
                    m_darreraEstadistica.setNom_equip(m_tvTitolVisitant.getText().toString());
                }
            }
            else {
                // Si l'acció és ALTRES, el jugador és el #0
                if (strAccio.equals(getString(R.string.seleccio_accio_altres_error))) {
                    m_darreraEstadistica.setNum_jugador(0);
                    m_darreraEstadistica.setNom_equip(m_tvTitolLocal.getText().toString());
                }
                else if (strAccio.equals(getString(R.string.seleccio_accio_altres))) {
                    m_darreraEstadistica.setNum_jugador(0);
                    m_darreraEstadistica.setNom_equip(m_tvTitolVisitant.getText().toString());
                }
                else if (strAccio.equals(getString(R.string.seleccio_accio_ace_server))) {
                    m_darreraEstadistica.setNum_jugador(Utils.convertString2Integer(m_tvPosicio21.getText().toString()));
                    m_darreraEstadistica.setNom_equip(m_tvTitolVisitant.getText().toString());
                }
                else if (strAccio.equals(getString(R.string.seleccio_accio_serve_error))) {
                    m_darreraEstadistica.setNum_jugador(Utils.convertString2Integer(m_tvPosicio11.getText().toString()));
                    m_darreraEstadistica.setNom_equip(m_tvTitolLocal.getText().toString());
                }
            }
            // Sumaritzar punts
            sumaritzarPunts();

        }
        else {
            mostrarBotonsAccions(View.INVISIBLE);
        }
    }

    private void sumaritzarPunts() {
        sumarPunts();
        tancarEstadistica();
        gestionarRotacio();
        guardarEstadistica2Partit();
        gestionarSets();
        guardarEstadistica2Partit();
        actualitzarDadesPantalla(false, true, true);
        guardarEstadisticas(false);
    }

    private void gestionarRotacio() {
        // Només fa la rotació quan canvia de equip el saque
        if (m_darreraEstadistica.isLocal()) {
            // De VISITANT a LOCAL
            m_rotarLocal = m_partit.isPilotaSaqueLocal() != m_darreraEstadistica.isLocal();
            if (m_rotarLocal)
                m_rotarVisitant = false;
        }
        else {
            // De LOCAL a VISITANT
            m_rotarVisitant = !m_partit.isPilotaSaqueLocal() != !m_darreraEstadistica.isLocal();
            if (m_rotarVisitant)
                m_rotarLocal = false;
        }
    }

    private void guardarEstadistica2Partit() {
        if (m_darreraEstadistica != null && m_darreraEstadistica.getNum_set() > 0) {
            // Actualiza els SETs amb el partit actiu
            if (m_partit.getNumSetActual() != m_darreraEstadistica.getNum_set()) {
                // Hi ha canvi de SET. S'ha de resetear varies coses
                // 1.- Reiniciar els temps morts
                m_partit.setTempsMortVisitant(0);
                m_partit.setTempsMortLocal(0);
                // 2.- Buidar les casselles dels jugadors del camp de joc
                int[] jugadorsLocal = {0,0,0,0,0,0};
                m_partit.setJugadorsLocal(jugadorsLocal);
                int[] jugadorsVisitant = {0,0,0,0,0,0};
                m_partit.setJugadorsVisitant(jugadorsVisitant);
            }
            m_partit.setResultatPuntsLocal(m_darreraEstadistica.getPuntsLocal());
            m_partit.setResultatPuntsVisitant(m_darreraEstadistica.getPuntsVisitant());
            m_partit.setNumSetActual(m_darreraEstadistica.getNum_set());
            m_partit.setResultatSetLocal(m_darreraEstadistica.getSetLocal());
            m_partit.setResultatSetVisitant(m_darreraEstadistica.getSetVisitant());
            m_partit.getPuntsSets()[m_darreraEstadistica.getNum_set() - 1][0] = m_darreraEstadistica.getPuntsLocal();
            m_partit.getPuntsSets()[m_darreraEstadistica.getNum_set() - 1][1] = m_darreraEstadistica.getPuntsVisitant();
            m_partit.setPilotaSaqueLocal(m_darreraEstadistica.isLocal());
        }
        else {
            // Actualitza els SETs si ha acabat el partit
            if (m_darreraEstadistica != null) {
                m_partit.setNumSetActual(m_darreraEstadistica.getNum_set());
                m_partit.setResultatSetLocal(m_darreraEstadistica.getSetLocal());
                m_partit.setResultatSetVisitant(m_darreraEstadistica.getSetVisitant());
            }
        }
    }

    private void guardarEstadisticas(boolean pIsBackup) {
        if (pIsBackup || m_darreraEstadistica.getNum_set() == 0) {
            // És la fi del partit
            // "/apps/competicio/estadístiques_2019-09-20_12-24.csv"
            String strPath = "/apps/competicio/estadístiques_" +
                    Utils.convertDate2StringFile(new Date()) +
                    (pIsBackup ? "_bac" : "") +
                    ".csv";
            ArrayList<String> arrEstadistiques = new ArrayList<String>();
            // Convertir les estadítiques a format CSV
            for (Estadistica estadistica : m_estadistiques) {
                arrEstadistiques.add(estadistica.toString());
            }
            FilesDao.guardarRegistresExcel(strPath, arrEstadistiques);
        }
    }

    private void afegirAccioEstadisticaJugador(TextView tvPosicio, FloatingActionButton p_fabAccio) {
        String strAccio = p_fabAccio.getContentDescription().toString().trim();
        String strJugador = tvPosicio.getText().toString();
        //
        if (m_darreraEstadistica != null && m_darreraEstadistica.getNum_set() > 0) {
            m_darreraEstadistica.setAccio(strAccio);
            m_darreraEstadistica.setNum_set(Utils.convertString2Integer(m_tvSetActual.getText().toString()));
            if (m_darreraEstadistica.isLocal()) {
                if (strAccio.equals(getString(R.string.seleccio_accio_attack_point)) ||
                        strAccio.equals(getString(R.string.seleccio_accio_wining_block))) {
                    m_darreraEstadistica.setNom_equip(m_tvTitolLocal.getText().toString());
                }
                else {
                    m_darreraEstadistica.setNom_equip(m_tvTitolVisitant.getText().toString());
                }
            }
            else {
                if (strAccio.equals(getString(R.string.seleccio_accio_attack_point)) ||
                        strAccio.equals(getString(R.string.seleccio_accio_wining_block))) {
                    m_darreraEstadistica.setNom_equip(m_tvTitolVisitant.getText().toString());
                }
                else {
                    m_darreraEstadistica.setNom_equip(m_tvTitolLocal.getText().toString());
                }
            }
            m_darreraEstadistica.setNum_jugador(Utils.convertString2Integer(strJugador));
            // Sumaritzar punts
            sumaritzarPunts();
        }
        else {
            mostrarBotonsAccions(View.INVISIBLE);
        }
    }

    private void sumarPunts() {
        boolean pIslocal = m_darreraEstadistica.isLocal();
        /*
        int puntsLocal = m_darreraEstadistica.getPuntsLocal();
        int puntsVisitant = m_darreraEstadistica.getPuntsVisitant();
         */
        int puntsLocal = m_partit.getResultatPuntsLocal();
        int puntsVisitant = m_partit.getResultatPuntsVisitant();
        if (pIslocal)
            puntsLocal++;
        else
            puntsVisitant++;
        m_darreraEstadistica.setPuntsLocal(puntsLocal);              // Actualitza els marcadors
        m_darreraEstadistica.setPuntsVisitant(puntsVisitant);
        //
    }

    private void realitzarAccio(boolean pIsLocal) {
        inicialitzarEstadistica(pIsLocal);
        mostrarBotonsAccions(View.VISIBLE);

    }

    private void inicialitzarEstadistica(boolean pIsLocal) {
        int iPuntsLocal = 0;
        int iPuntsVisitant = 0;
        int iNumSet = 1;
        int iSetLocal = 0;
        int iSetVisitant = 0;
        if (m_darreraEstadistica != null) {
            iPuntsLocal = m_darreraEstadistica.getPuntsLocal();
            iPuntsVisitant = m_darreraEstadistica.getPuntsVisitant();
            iNumSet = m_darreraEstadistica.getNum_set();
            iSetLocal = m_darreraEstadistica.getSetLocal();
            iSetVisitant = m_darreraEstadistica.getSetVisitant();
        }
        m_darreraEstadistica = new Estadistica();
        m_darreraEstadistica.setLocal(pIsLocal);
        m_darreraEstadistica.setTemps(new Date());
        m_darreraEstadistica.setPuntsLocal(iPuntsLocal);
        m_darreraEstadistica.setPuntsVisitant(iPuntsVisitant);
        m_darreraEstadistica.setNum_set(iNumSet);
        m_darreraEstadistica.setSetLocal(iSetLocal);
        m_darreraEstadistica.setSetVisitant(iSetVisitant);
    }

    private void tancarEstadistica() {
        mostrarBotonsAccions(View.INVISIBLE);
        afegirDarreraEstadistica();
        //
        //System.out.println(m_darreraEstadistica.toString());
    }

    private void gestionarSets() {
        Estadistica newEstadistica = new Estadistica();
        int numSet = m_partit.getNumSetActual();
        // Actualitzar els SETs principals
        m_darreraEstadistica.setSetLocal(m_partit.getResultatSetLocal());
        m_darreraEstadistica.setSetVisitant(m_partit.getResultatSetVisitant());
        if (numSet < 5) {
            // SETS 1,2,3,4
            if ((m_darreraEstadistica.getPuntsLocal() >= 25 ||
                    m_darreraEstadistica.getPuntsVisitant() >= 25) &&
                    Math.abs(m_darreraEstadistica.getPuntsVisitant() - m_darreraEstadistica.getPuntsLocal()) >= 2) {
                // Canvi de SET
                newEstadistica.setNum_set(numSet + 1);
                if ((m_darreraEstadistica.getPuntsLocal() > m_darreraEstadistica.getPuntsVisitant())) {
                    newEstadistica.setSetLocal(m_darreraEstadistica.getSetLocal() + 1);
                }
                else
                    newEstadistica.setSetVisitant(m_darreraEstadistica.getSetVisitant() + 1);
                newEstadistica.setPuntsLocal(0);
                newEstadistica.setPuntsVisitant(0);
                //
                if (Math.abs(newEstadistica.getSetLocal() - newEstadistica.getSetVisitant()) >= 2 &&
                        (newEstadistica.getSetLocal() >= 3 || newEstadistica.getSetVisitant() >= 3)) {
                    // Fi de partit: 3-0,3-1
                    newEstadistica.setNum_set(0);
                    newEstadistica.setPuntsLocal(0);
                    newEstadistica.setPuntsVisitant(0);
                }
                // Inicialitzar una nova estadística
                m_darreraEstadistica = new Estadistica();
                m_darreraEstadistica.setNum_set(newEstadistica.getNum_set());
                m_darreraEstadistica.setSetLocal(newEstadistica.getSetLocal());
                m_darreraEstadistica.setSetVisitant(newEstadistica.getSetVisitant());
                m_darreraEstadistica.setPuntsLocal(newEstadistica.getPuntsLocal());
                m_darreraEstadistica.setPuntsVisitant(newEstadistica.getPuntsVisitant());
            }
        }
        else {
            // SET 5
            if ((m_darreraEstadistica.getPuntsLocal() >= 15 ||
                    m_darreraEstadistica.getPuntsVisitant() >= 15) &&
                    Math.abs(m_darreraEstadistica.getPuntsVisitant() - m_darreraEstadistica.getPuntsLocal()) >= 2) {
                // Fi de partit
                newEstadistica.setNum_set(0);
                if ((m_darreraEstadistica.getPuntsLocal() > m_darreraEstadistica.getPuntsVisitant()))
                    newEstadistica.setSetLocal(m_darreraEstadistica.getSetLocal() + 1);
                else
                    newEstadistica.setSetVisitant(m_darreraEstadistica.getSetVisitant() + 1);
                // Inicialitzar una nova estadística
                m_darreraEstadistica = new Estadistica();
                m_darreraEstadistica.setNum_set(newEstadistica.getNum_set());
                m_darreraEstadistica.setSetLocal(newEstadistica.getSetLocal());
                m_darreraEstadistica.setSetVisitant(newEstadistica.getSetVisitant());
            }
        }
    }

    private void afegirDarreraEstadistica() {
        //
        //guardarHistorialEstadistiques();
        //
        m_estadistiques.add(m_darreraEstadistica);
    }

    @SuppressLint("RestrictedApi")
    private void mostrarBotonsAccions(int p_iEsVisible) {
        m_fabAttackError.setVisibility(p_iEsVisible);
        m_fabErrorOnSet.setVisibility(p_iEsVisible);
        m_fabServeError.setVisibility(p_iEsVisible);
        m_fabAceServe.setVisibility(p_iEsVisible);
        m_fabWiningBlock.setVisibility(p_iEsVisible);
        m_fabAttackPoint.setVisibility(p_iEsVisible);
        m_fabAltres.setVisibility(p_iEsVisible);
        m_fabAltresError.setVisibility(p_iEsVisible);
        m_fabAltresCancel.setVisibility(p_iEsVisible);
        //
        m_fabAddJugador.setVisibility(p_iEsVisible!=View.VISIBLE ? View.VISIBLE : View.INVISIBLE);
        m_fabResetMarcador.setVisibility(p_iEsVisible!=View.VISIBLE ? View.VISIBLE : View.INVISIBLE);
        m_fabBackMarcador.setVisibility(p_iEsVisible!=View.VISIBLE ? View.VISIBLE : View.INVISIBLE);
    }

    private void actualizarAdapter() {
        // Adapter Local
        m_anAdapterLocal = new JugadorsAdapter(this, m_partit.getJugadorsEquipLocal(), Boolean.TRUE);
        m_lvJugadorsLocal.setAdapter(m_anAdapterLocal);
        m_lvJugadorsLocal.setChoiceMode(ListView.CHOICE_MODE_NONE);
        m_lvJugadorsLocal.refreshDrawableState();
        // Adapter Visitant
        m_anAdapterVisitant = new JugadorsAdapter(this, m_partit.getJugadorsEquipVisitant(), Boolean.FALSE);
        m_lvJugadorsVisitant.setAdapter(m_anAdapterVisitant);
        m_lvJugadorsVisitant.setChoiceMode(ListView.CHOICE_MODE_NONE);
        m_lvJugadorsVisitant.refreshDrawableState();
    }
    public synchronized boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            //vibrar(CTE_VIBRATION_MS);
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);
            view.startDrag(data, shadowBuilder, view, 0);
            return true;
        }
        return false;
    }
    public synchronized boolean onDrag(View layoutview, DragEvent dragevent) {
        try {
            int action = dragevent.getAction();
            View view = (View) dragevent.getLocalState();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    vibrar(CTE_VIBRATION_MS);
                    //System.out.println("Drag event started");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //System.out.println("Drag event entered into "+layoutview.toString());
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //System.out.println("Drag event exited from "+layoutview.toString());
                    break;
                case DragEvent.ACTION_DROP:
                    //System.out.println("Dropped");
                    if (layoutview.getClass() == TextView.class && view.getClass() == FloatingActionButton.class) {
                        vibrar(CTE_VIBRATION_MS);
                        // Acció realitzada
                        TextView tvPosicio = (TextView) layoutview;
                        FloatingActionButton fabJugador = (FloatingActionButton) view;
                        afegirAccioEstadisticaJugador(tvPosicio, fabJugador);
                    }
                    else if (layoutview.getClass() == TextView.class && view.getClass() == TextView.class) {
                        vibrar(CTE_VIBRATION_MS);
                        // Moure jugadors
                        TextView tvPosicio = (TextView) layoutview;
                        TextView tvJugador = (TextView) view;
                        moureJugador(tvJugador, tvPosicio);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //System.out.println("Drag ended");
                    break;
                default:
                    break;
            }
            return true;
        } catch (Throwable t) {
            System.err.println("Error onDrag: " + t);
        }
        return false;
    }

    private void moureJugador(TextView tvJugador, TextView tvPosicio) {
        String strJugador = Utils.parserNumIText(tvJugador.getText().toString());  //  Local: Marta C. 12 y Visitant: 11 Helena C.
        tvPosicio.setText(strJugador);
        //
        String cdCelda = tvPosicio.getContentDescription().toString();  // 1L, 2L,..., 1V, 2V,...
        // Guardar estadística
        afegirEstadisticaCanviJugador(cdCelda.substring(1).equals(getString(R.string.libero)), m_partit.getJugadorsLocal()[Utils.convertString2Integer(cdCelda.substring(0,1)).intValue() - 1], Utils.convertString2Integer(strJugador));
        // Actualitzar PARTTT
        if (cdCelda.substring(1).equals("L")) {  // L - Local, V - Visitant
            // És local
            m_partit.getJugadorsLocal()[Utils.convertString2Integer(cdCelda.substring(0, 1)).intValue() - 1] = Utils.convertString2Integer(strJugador);
            getJugadorPosicio(tvPosicio, m_partit.getJugadorsEquipLocal(), m_partit.getJugadorsLocal()[Utils.convertString2Integer(cdCelda.substring(0, 1)).intValue() - 1]);
        }
        else {
            // És visitant
            m_partit.getJugadorsVisitant()[Utils.convertString2Integer(cdCelda.substring(0, 1)).intValue() - 1] = Utils.convertString2Integer(strJugador);
            getJugadorPosicio(tvPosicio, m_partit.getJugadorsEquipVisitant(), m_partit.getJugadorsVisitant()[Utils.convertString2Integer(cdCelda.substring(0, 1)).intValue() - 1]);
        }
    }

    private void guardarInfoPartit2SP(String pStrKey, Object pObject) {
        try {
            if (pStrKey.equals(CTE_CLAVE_COMPETICIO_PARTIT_MARCADOR))
                Utils.putValorSP(m_spDades, CTE_CLAVE_COMPETICIO_PARTIT_MARCADOR, Serializar.serializar(pObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    private Object recuperarPartit2SPComp(String pStrKey) {
        try {
            return Serializar.desSerializar(Utils.getValorSP(m_spDades, pStrKey));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
     */
    private Object recuperarPartit2SP(String pStrKey) {
        try {
            return Serializar.desSerializar(Utils.getValorSP(m_spDades, pStrKey));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        guardarInfoPartit2SP(CTE_CLAVE_COMPETICIO_PARTIT_MARCADOR, m_partit);
        guardarEstadisticas(true);
        //System.out.println("CACAMarcador - onDestroy");
    }

    private void blinkMarcador() {
        // Blinking del Marcador que saca
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        if (m_partit.getNumSetActual() == 0) {
            // Fi de partit
            m_tvMarcadorPuntsVisitant.clearAnimation();
            m_tvMarcadorPuntsLocal.clearAnimation();
        }
        else {
            // el partit segueix en marxa
            if (m_partit.isPilotaSaqueLocal()) {
                m_tvMarcadorPuntsLocal.startAnimation(anim);
                m_tvMarcadorPuntsVisitant.clearAnimation();
            } else {
                m_tvMarcadorPuntsVisitant.startAnimation(anim);
                m_tvMarcadorPuntsLocal.clearAnimation();
            }
        }
    }
}
