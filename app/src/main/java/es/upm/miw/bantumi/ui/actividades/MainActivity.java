package es.upm.miw.bantumi.ui.actividades;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Locale;

import es.upm.miw.bantumi.data.database.AppDatabase;
import es.upm.miw.bantumi.data.entities.Partida;
import es.upm.miw.bantumi.ui.fragmentos.FinalAlertDialog;
import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.dominio.logica.JuegoBantumi;
import es.upm.miw.bantumi.ui.viewmodel.BantumiViewModel;

public class MainActivity extends AppCompatActivity {
    protected final String LOG_TAG = "MiW";
    public JuegoBantumi juegoBantumi;
    private BantumiViewModel bantumiVM;
    int numInicialSemillas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        numInicialSemillas = getResources().getInteger(R.integer.intNumInicialSemillas);
        bantumiVM = new ViewModelProvider(this).get(BantumiViewModel.class);
        juegoBantumi = new JuegoBantumi(bantumiVM, JuegoBantumi.Turno.turnoJ1, numInicialSemillas);
        crearObservadores();
    }

    private void crearObservadores() {
        for (int i = 0; i < JuegoBantumi.NUM_POSICIONES; i++) {
            int finalI = i;
            bantumiVM.getNumSemillas(i).observe(this, integer -> mostrarValor(finalI, juegoBantumi.getSemillas(finalI)));
        }
        bantumiVM.getTurno().observe(this, turno -> marcarTurno(juegoBantumi.turnoActual()));
    }

    private void marcarTurno(@NonNull JuegoBantumi.Turno turnoActual) {
        TextView tvJugador1 = findViewById(R.id.tvPlayer1);
        TextView tvJugador2 = findViewById(R.id.tvPlayer2);
        switch (turnoActual) {
            case turnoJ1:
                tvJugador1.setTextColor(getColor(R.color.white));
                tvJugador1.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                tvJugador2.setTextColor(getColor(R.color.black));
                tvJugador2.setBackgroundColor(getColor(R.color.white));
                break;
            case turnoJ2:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador1.setBackgroundColor(getColor(R.color.white));
                tvJugador2.setTextColor(getColor(R.color.white));
                tvJugador2.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                break;
            default:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador2.setTextColor(getColor(R.color.black));
        }
    }

    private void mostrarValor(int pos, int valor) {
        String num2digitos = String.format(Locale.getDefault(), "%02d", pos);
        int idBoton = getResources().getIdentifier("casilla_" + num2digitos, "id", getPackageName());
        if (idBoton != 0) {
            TextView viewHueco = findViewById(idBoton);
            viewHueco.setText(String.valueOf(valor));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcAcercaDe:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.aboutTitle)
                        .setMessage(R.string.aboutMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;
            case R.id.opcReiniciarPartida:
                mostrarDialogoReinicio();
                return true;
            case R.id.opcGuardarPartida:
                guardarPartida();
                return true;
            case R.id.opcRecuperarPartida:
                recuperarPartida();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mostrarDialogoReinicio() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.txtReiniciarPartida)
                .setMessage(R.string.txtReiniciarPartida)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> reiniciarPartida())
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void reiniciarPartida() {
        juegoBantumi.reiniciarPartida(JuegoBantumi.Turno.turnoJ1);
        actualizarUI();
    }

    private void actualizarUI() {
        for (int i = 0; i < JuegoBantumi.NUM_POSICIONES; i++) {
            mostrarValor(i, juegoBantumi.getSemillas(i));
        }
        marcarTurno(juegoBantumi.turnoActual());
    }

    public void huecoPulsado(@NonNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int num = Integer.parseInt(resourceName.substring(resourceName.length() - 2));
        Log.i(LOG_TAG, "huecoPulsado(" + resourceName + ") num=" + num);
        switch (juegoBantumi.turnoActual()) {
            case turnoJ1:
                Log.i(LOG_TAG, "* Juega Jugador");
                juegoBantumi.jugar(num);
                break;
            case turnoJ2:
                Log.i(LOG_TAG, "* Juega Computador");
                juegoBantumi.juegaComputador();
                break;
            default: // JUEGO TERMINADO
                finJuego();
        }
        if (juegoBantumi.juegoTerminado()) {
            finJuego();
        }
    }

    private void finJuego() {
        String texto;
        int semillasJ1 = juegoBantumi.getSemillas(6);
        int semillasJ2 = juegoBantumi.getSemillas(13);

        if (semillasJ1 > semillasJ2) {
            texto = "Gana Jugador 1";
        } else if (semillasJ1 < semillasJ2) {
            texto = "Gana Jugador 2";
        } else {
            texto = "¡¡¡ EMPATE !!!";
        }

        new FinalAlertDialog(texto).show(getSupportFragmentManager(), "ALERT_DIALOG");

        new AlertDialog.Builder(this)
                .setTitle("Guardar puntaje")
                .setMessage("¿Deseas guardar el puntaje?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Guardar el puntaje en la base de datos si el usuario confirma
                    guardarPuntuacion(semillasJ1, semillasJ2);
                    Toast.makeText(getApplicationContext(), "Puntaje guardado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Si el usuario elige no guardar, cerrar el diálogo
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Puntaje no guardado", Toast.LENGTH_SHORT).show();
                })
                .setCancelable(false)
                .show();
    }

    public void guardarPartida() {
        try {
            String estadoJuego = juegoBantumi.serializa(); // Serializa el estado del juego
            FileOutputStream fos = openFileOutput("partida_guardada.txt", Context.MODE_PRIVATE);
            fos.write(estadoJuego.getBytes());
            fos.close();
            Toast.makeText(this, "Partida guardada exitosamente.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error al guardar partida", e);
            Toast.makeText(this, "Error al guardar partida.", Toast.LENGTH_SHORT).show();
        }
    }


    public void recuperarPartida() {
        try {
            File file = new File(getFilesDir(), "partida_guardada.txt");
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    sb.append(linea);
                }
                br.close();
                juegoBantumi.deserializa(sb.toString()); // Deserializa el estado del juego
                Toast.makeText(this, "Partida recuperada exitosamente.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No hay partida guardada.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error al recuperar partida", e);
            Toast.makeText(this, "Error al recuperar partida.", Toast.LENGTH_SHORT).show();
        }
    }


    private void mostrarDialogo(String mensaje) {
        new AlertDialog.Builder(this)
                .setMessage(mensaje)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }



    private void guardarPuntuacion(int puntuacionJ1, int puntuacionJ2) {
        // Implementar la lógica para guardar la puntuación en la base de datos
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        Partida partida = new Partida(new Date(), puntuacionJ1, puntuacionJ2);
        db.partidaDao().insertarPartida(partida);
    }
}
