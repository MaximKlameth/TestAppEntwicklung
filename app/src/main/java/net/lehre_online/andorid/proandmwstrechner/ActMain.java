package net.lehre_online.andorid.proandmwstrechner;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Eine einfache Anwendung zur Mehrwertsteuer-Berechnung. Die Applikation
 * basiert auf einem Beispiel von Becker/Pant und wurde für die Übung
 * angepasst und erweitert
 *
 * @author  Wolfgang Lang
 * @version 2019-10-13
 * @see     "Foliensatz zur Vorlesung"
 */
public class ActMain extends AppCompatActivity {



    static final boolean DBG        = false;
    static final String TAG         = "ActMain";
    static final String KEY_BRUTTO  = "brutto";
    static final String KEY_NETTO   = "netto";
    static final String KEY_STEUER  = "steuer";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        final String MNAME = "onCreate()";
        if( DBG ) Log.d( TAG, MNAME + "entering..." );

        super.onCreate(savedInstanceState);

    /* Toolbar setzen:
    Toolbar myToolbar = (Toolbar) findViewById( R.id.my_toolbar );
    setSupportActionBar( myToolbar );*/

        setContentView( R.layout.activity_main );
        if( DBG ) Log.d( TAG, MNAME + "...exiting" );
    }
/*
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mnu_main, menu);
        return true;
    }

*/
/*
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        final String MNAME = "onOptionsItemSelected()";
        if (DBG) Log.d(TAG, MNAME + "entering...");

        boolean b = false;

        switch (item.getItemId()) {

            case R.id.mniAbout:
                final Dialog dlg = new Dialog(this);
                dlg.setContentView(R.layout.lay_about);
                dlg.setTitle(R.string.pAboutHeader);
                dlg.setCancelable(true);

                String sVersionName = getVersionName();
                if (DBG) Log.v(TAG, "Version: " + sVersionName);
                TextView lblVersion = dlg.findViewById(R.id.lblVersion);
                lblVersion.setText("Version: " + sVersionName);
                (dlg.findViewById(R.id.cmdOk)).setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });

                dlg.show();
                b = true;
                break;

            case R.id.mniExit:
                finish();
                b = true;
                break;

            default:
                Log.w("onOptionsItemSelected()", "Unbekannte Option: " + item);
        }

        if (!b) b = super.onOptionsItemSelected(item);
        if (DBG) Log.v(TAG, MNAME + "...exiting. b=" + b);
        return b;
    }

 */
    /**
     * Event handler für Button 'Berechnen'.
     * Hier findet die Berechnung der Werte und der Aufruf der Ergebnis-Activity (ActErgebnis) statt
     *
     * @param cmd Button
     */
    public void onClickBerechnen(final View cmd) {

        final String MNAME = "onClickBerechnen()";
        if( DBG ) Log.v( TAG, MNAME + "entering..." );

        double dBrutto = 0.0, dNetto = 0.0, dSteuer = 0.0, dBetrag = 0.0;

        // Betrag:

        final EditText txtBetrag = findViewById( R.id.txtBetrag );
        String s = txtBetrag.getText().toString();
        if( (s != null) && (s.length() > 0) ) dBetrag = Double.parseDouble( s );
        if (DBG) Log.v(TAG, MNAME + "HALlo");


        // MwSt-Satz:
        final Spinner spiMwstSatz = findViewById( R.id.spiMwstSatz );
        final int[] anProzente = getResources().getIntArray( R.array.mwstWerte);
    /* Variante:
     * final int mwst = (Integer) spiMwstSatz.getItemAtPosition(
                                  spiMwstSatz.getSelectedItemPosition() ); */
        final double dMwst =
                ((double) anProzente[ spiMwstSatz.getSelectedItemPosition() ]) / 100;

        // Brutto oder Netto?
        final RadioGroup rg = findViewById( R.id.rbgBruttoNetto );
        if( rg.getCheckedRadioButtonId() == R.id.radNetto ){
            dNetto = dBetrag;
            dSteuer = dNetto * dMwst;
            dBrutto = dNetto + dSteuer;
        }
        else {
            // Brutto:
            dBrutto = dBetrag;
            dNetto = dBrutto / ( 1 + dMwst );
            dSteuer = dBrutto - dNetto;
        }

        final Intent intent = new Intent( this, ActErgebnis.class );

        // Daten an Activity Ergebnis übergeben:
        intent.putExtra( KEY_BRUTTO, dBrutto );
        intent.putExtra( KEY_NETTO , dNetto );
        intent.putExtra( KEY_STEUER, dSteuer );

        // Activity Ergebnis aufrufen:
        startActivity( intent );

        if( DBG ) Log.d( TAG, MNAME + "...exiting" );
    }

    /**
     * Liefert android:versionName aus app.build.gradle bzw. einen Dummywert falls
     * versionName nicht verfügbar
     *
     * @return android:versionName
     */
    private String getVersionName() {

        final String MNAME = "getVersionName()";
        if( DBG ) Log.d( TAG, MNAME + "entering..." );

        String sVersionName = null;

        try{
            sVersionName = getPackageManager().getPackageInfo(
                    getPackageName(), 0).versionName;
        } catch( PackageManager.NameNotFoundException ex ) {
            sVersionName = "android:versionName not available.";
        }

        if( DBG ) Log.d( TAG, MNAME + "...exiting" );
        return sVersionName;
    }

}