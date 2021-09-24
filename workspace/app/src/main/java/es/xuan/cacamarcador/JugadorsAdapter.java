package es.xuan.cacamarcador;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import es.xuan.cacamarcador.altres.Utils;
import es.xuan.cacamarcador.model.Jugador;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by Jcamposp on 08/10/2017.
 */

public class JugadorsAdapter extends BaseAdapter implements View.OnTouchListener {

    private ArrayList<Jugador> m_jugadors = null;
    private Activity m_anActivity = null;
    private Boolean m_esLocal = Boolean.TRUE;
    private Vibrator m_vibe = null;

    public JugadorsAdapter(Activity pConsolaAct, ArrayList<Jugador> pJugadors, Boolean pEsLocal) {
        m_anActivity = pConsolaAct;
        if (pJugadors != null && pJugadors.size() > 0)
            Collections.sort(pJugadors);
        m_jugadors = pJugadors;
        m_esLocal = pEsLocal;
        m_vibe = (Vibrator) pConsolaAct.getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    public int getCount() {
        return (m_jugadors!=null ? m_jugadors.size() : 0);
    }

    @Override
    public Jugador getItem(int position) {
        return m_jugadors.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = m_anActivity.getLayoutInflater().inflate(R.layout.element_jugador, null);
        TextView tvJugador = (TextView) row.findViewById(R.id.tvNomNumJugador);
        //
        String nomJugador = "";
        if (m_esLocal) {
            nomJugador = getItem(position).getNom() +
                            (getItem(position).getCognoms()==null || getItem(position).getCognoms().equals("") ? "" : " " + getItem(position).getCognoms().substring(0,1) + ".") +
                            " " +
                            Utils.num2String(getItem(position).getDorsal(),2);
            tvJugador.setGravity(Gravity.END);
            tvJugador.setOnTouchListener(this);
        }
        else {
            nomJugador = Utils.num2String(getItem(position).getDorsal(),2) +
                    " " +
                    getItem(position).getNom() +
                    (getItem(position).getCognoms()==null || getItem(position).getCognoms().equals("") ? "" : " " + getItem(position).getCognoms().substring(0,1) + ".") ;
            tvJugador.setGravity(Gravity.START);
            tvJugador.setOnTouchListener(this);
        }
        tvJugador.setText(nomJugador);
        if (getItem(position).getPosicio() != null && getItem(position).getPosicio().equals(m_anActivity.getString(R.string.libero)))
            // Si és Libero canvia el fons del nom
            tvJugador.setTextColor(Color.MAGENTA);
        else if (getItem(position).getPosicio() != null && getItem(position).getPosicio().equals(m_anActivity.getString(R.string.central)))
            // No és Libero
            tvJugador.setTextColor(Color.RED);
        else
            tvJugador.setTextColor(Color.WHITE);
        return row;
    }

    public synchronized boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            //m_vibe.vibrate(Constants.CTE_VIBRATION_MS);
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);
            view.startDrag(data, shadowBuilder, view, 0);
            return true;
        }
        return false;
    }
}
