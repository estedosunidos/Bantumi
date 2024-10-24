package es.upm.miw.bantumi.ui.actividades;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
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
    private AppDatabase db;

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
        if (0 != idBoton) {
            TextView viewHueco = findViewById(idBoton);
            viewHueco.setText(String.valueOf(valor));
        }
    }

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
            case R.id.opcMejoresResultados:
                mostrarResultados();
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
        int semillasJ1 = juegoBantumi.getSemillas(6); // Puntaje Jugador 1
        int semillasJ2 = juegoBantumi.getSemillas(13); // Puntaje Jugador 2

        if (semillasJ1 > semillasJ2) {
            texto = "Gana Jugador 1";
        } else if (semillasJ1 < semillasJ2) {
            texto = "Gana Jugador 2";
        } else {
            texto = "¡¡¡ EMPATE !!!";
        }

        // Mostrar el diálogo de fin de juego
        new FinalAlertDialog(texto).show(getSupportFragmentManager(), "ALERT_DIALOG");

        // Crear el diálogo para confirmar si se quiere guardar el puntaje
        new AlertDialog.Builder(this)
                .setTitle("Guardar puntaje")
                .setMessage("¿Deseas guardar el puntaje?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Guardar el puntaje en la base de datos si el usuario confirma
                        guardarPuntuacion(semillasJ1, semillasJ2);
                        Toast.makeText(getApplicationContext(), "Puntaje guardado", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Si el usuario elige no guardar, cerrar el diálogo
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Puntaje no guardado", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)  // Evitar que el diálogo se cierre si tocan fuera
                .show();
    }


    private void guardarPartida() {
        try (FileOutputStream fos = openFileOutput("partidaGuard.json", Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(juegoBantumi);
            Log.i(LOG_TAG, "Click botón Guardar -> Partida guardada correctamente");
            Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.txtOpcionGuardar ),
                    Snackbar.LENGTH_SHORT
            ).show();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error guardando la partida", e);
        }
    }

    private void recuperarPartida() {
        File archivo = getFileStreamPath("partidaGuard.json");

        if (archivo.exists() && archivo.length() > 0) { // Verificar si el archivo existe y no está vacío
            try (FileReader reader = new FileReader(archivo)) {
                Gson gson = new Gson();
                juegoBantumi = gson.fromJson(reader, JuegoBantumi.class);

                // Verificar los datos recuperados
                Log.d(LOG_TAG, "Datos recuperados de juegoBantumi: " + juegoBantumi.toString());

                actualizarUI(); // Reiniciar UI con los datos del juego

                Log.i(LOG_TAG, "Partida recuperada");
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error recuperando la partida", e);
            }
        } else {
            Log.w(LOG_TAG, "Archivo de partida no existe o está vacío");
            // Mostrar un mensaje si no hay partida guardada
        }
    }
    private void guardarPuntuacion(int semillasJ1, int semillasJ2) {
        String nombreJugador = "Jugador"; // Obtener el nombre del jugador de los ajustes del juego
        Date fechaHora = new Date();

        // Crear una nueva partida
        Partida partida = new Partida(nombreJugador, fechaHora, semillasJ1, semillasJ2);

        // Guardar la partida en la base de datos usando Room
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            db.partidaDao().insertarPartida(partida);
            Log.i(LOG_TAG, "Puntuación guardada: " + partida.toString());
        }).start();
    }
    private void mostrarResultados() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            List<Partida> partidas = db.partidaDao().obtenerTodasPartidas();

            runOnUiThread(() -> {
                // Aquí podrías mostrar las partidas en un RecyclerView o ListView
                for (Partida partida : partidas) {
                    Log.i(LOG_TAG, "Partida: " + partida.toString());
                }
            });
        }).start();
    }




}
