package es.xuan.cacamarcador.altres;

import android.content.SharedPreferences;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String CTE_FORMAT_DATA_HORA = "HH:mm";
    private static final String CTE_FORMAT_DATA_RED = "dd/MM/yyyy";

    public static String num2String(int piNumber, int iTamano) {
        if (iTamano == 2 && piNumber < 10)
            return (" " + piNumber);
        return "" + piNumber;
    }
    public static String num2String(int piNumber, int iTamano, boolean bIzquierda) {
        if (iTamano == 2 && piNumber < 10)
            return (bIzquierda ? " " + piNumber : piNumber + " ");
        return "" + piNumber;
    }

    public static String parserTextMipmap(String pText) {
        String strText = pText.toLowerCase();
        strText = strText.replaceAll("ç", "c");         // BARÇA CVB GRANA
        strText = strText.replaceAll("‘", "_");         // CV ESPLUGUES ‘B‘
        strText = strText.replaceAll(" ", "_");         // CV ESPLUGUES ‘B‘
        strText = strText.replaceAll("ò", "o");         // VÒLEI SANT ESTEVE
        strText = strText.replaceAll("à", "a");         // CV GAVÀ VERMELL
        return strText;
    }

    public static Calendar convertString2Calendar(String pStrFecha) {
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
    public static Integer convertString2Integer(String pStrNumero) {
        Integer numero = new Integer(0);
        try {
            if (pStrNumero != null && !pStrNumero.equals(""))
                numero = new Integer(pStrNumero);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numero;
    }

    public static String parserNumIText(String pText) {
        String strResultat = "0";
        if (pText.startsWith(" ") ||        // Visitant
                pText.startsWith("0") ||
                pText.startsWith("1") ||
                pText.startsWith("2") ||
                pText.startsWith("3") ||
                pText.startsWith("4") ||
                pText.startsWith("5") ||
                pText.startsWith("6") ||
                pText.startsWith("7") ||
                pText.startsWith("8") ||
                pText.startsWith("9")) {
            int ind1 = pText.indexOf(" ",1);
            strResultat = pText.substring(0,ind1);
        }
        else {                              // Local
            int ind1 = pText.lastIndexOf(" ");
            strResultat = pText.substring(ind1 + 1);
        }
        return strResultat.trim();
    }

    public static String convertDate2String(Date pTemps) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return formatter.format(pTemps);
        } catch (Exception ex){}
        return "";
    }
    public static String convertDate2StringFile(Date pTemps) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        return formatter.format(pTemps);
    }
    public static String data2StringRed(Date p_data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_RED, Locale.getDefault());
        try {
            if (p_data != null)
                return dateFormat.format(p_data);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
    public static String data2StringHora(Date p_data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CTE_FORMAT_DATA_HORA, Locale.getDefault());
        try {
            if (p_data != null)
                return dateFormat.format(p_data);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
    public static void putValorSP(SharedPreferences p_spDades, String p_strKey, String p_strValor) {
        if (p_spDades != null) {
            SharedPreferences.Editor ed = p_spDades.edit();
            ed.putString(p_strKey, p_strValor);
            ed.commit();
        }
    }
    public static String getValorSP(SharedPreferences p_spDades, String p_strKey) {
        //To retrieve data from shared preference
        if (p_spDades != null)
            return p_spDades.getString(p_strKey, "");
        return "";
    }
}
