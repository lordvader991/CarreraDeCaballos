package practica.univalle.basicretrofitadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import practica.univalle.basicretrofitadapter.models.Horse;

public class HorseRaceActivity extends AppCompatActivity {

    private static final int NUM_CABALLOS = 10;
    boolean ProgresoCarrera = false;
    boolean BotonParar = false;
    Button PararCaballo, stop;
    EditText NumeroCaballos;
    private Horse[] horses = new Horse[NUM_CABALLOS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse_race);
        LinearLayout layout = findViewById(R.id.horsesLayout);
        String[] horseNames = getResources().getStringArray(R.array.horse_names); // Agrega el array de nombres
        for (int i = 0; i < NUM_CABALLOS; i++) {
            View horseView = getLayoutInflater().inflate(R.layout.horse_layout, layout, false);
            int horseImageResource = getImageResource(i);
            horses[i] = new Horse(horseView,horseImageResource,horseNames[i]);
            layout.addView(horseView);
        }
        Button startRaceButton = findViewById(R.id.startRaceButton);
        stop = findViewById(R.id.stop);
        PararCaballo = findViewById(R.id.PararCaballo);
        NumeroCaballos = findViewById(R.id.NumeroCaballos);
        startRaceButton.setOnClickListener(v -> startRace());
        stop.setOnClickListener(view -> stopRace());
        PararCaballo.setOnClickListener(view -> pararCaballo());

    }

    private void startRace() { //duda si funcione .-.
        if(!ProgresoCarrera && !BotonParar) {
            ProgresoCarrera = true;
            for (Horse horse : horses) {
                new Thread(horse).start();
            }
        }
    }
    private int[] horseImageResources = { //ayuda
            R.drawable.horse1,
            R.drawable.caballo2,
            R.drawable.caballo3,
            R.drawable.caballo4,
            R.drawable.caballo5,
            R.drawable.caballo6,
            R.drawable.caballo7,
            R.drawable.caballo8,
            R.drawable.caballo9
    };
    private int getImageResource(int index){
      if (index < horseImageResources.length){
          return horseImageResources[index];
      }
      return R.drawable.caballo3;
    }
    private void stopRace(){
        if(ProgresoCarrera && !BotonParar) {
            ProgresoCarrera = false;
            for (Horse horse : horses) {
                    horse.stopRace();
            }
        }
    }
    private void pararCaballo(){
        try{
            int numCaballos = Integer.parseInt(NumeroCaballos.getText().toString());
            if (numCaballos >=1 && numCaballos <=NUM_CABALLOS){
                Horse pararCaballo = horses[numCaballos -1];
                pararCaballo.stopRace();
            }else{
                //la verdad no se que poner
            }
        }catch (NumberFormatException e){
            Toast.makeText(this, "Ingrese algo gil", Toast.LENGTH_SHORT).show();
        }
    }
    private void mostrarGanador() {
        for (Horse horse : horses) {
            if (horse.isPrimerGanador()) {
                Toast.makeText(this, "¡Ganó " + horse.getClass() + "!", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}