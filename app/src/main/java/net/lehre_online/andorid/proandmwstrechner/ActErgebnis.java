package net.lehre_online.andorid.proandmwstrechner;

/*
 * ActErgebnis.java
 * Android-Anwendungsentwicklung
 */

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

/**
 * Ausgabe der Ergebnisse (MwstRechner)
 *
 * @author  Wolfgang Lang
 * @version 2019-10-13
 * @see     "Foliensatz zur Vorlesung"
 */
public class ActErgebnis extends AppCompatActivity {

    static final boolean DBG = ActMain.DBG;
    static final String TAG = "ActErgebnis";

    @Override
    public void onCreate( Bundle bundle ) {

        final String MNAME = "onCreate()";
        if( DBG ) Log.d(TAG, MNAME + "entering...");

        super.onCreate( bundle );
        setContentView( R.layout.activity_main);

        setTitle( "MwSt-Ergebnis" );

        final Bundle extras = getIntent().getExtras();

        if( extras != null ){
            ((TextView) findViewById( R.id.txtNetto )).setText(	 String.format( Locale.GERMAN,
                    "%,6.2f", extras.getDouble( ActMain.KEY_NETTO  )));
            ((TextView) findViewById( R.id.txtSteuer )).setText( String.format( Locale.GERMAN,
                    "%,6.2f", extras.getDouble( ActMain.KEY_STEUER )));
            ((TextView) findViewById( R.id.txtBrutto )).setText( String.format( Locale.GERMAN,
                    "%,6.2f", extras.getDouble( ActMain.KEY_BRUTTO )));

        }

        if( DBG ) Log.d( TAG, MNAME + "...exiting" );
    }
}