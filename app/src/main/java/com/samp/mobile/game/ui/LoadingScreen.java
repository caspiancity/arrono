package com.samp.mobile.game.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.samp.mobile.R;
import com.samp.mobile.MainActivity;

public class LoadingScreen {

    private Activity activity;
    private ConstraintLayout mainLayout;
    private VideoView video;
    private boolean isRunning = true; // Controle para parar a thread

    public LoadingScreen(Activity activity) {
        this.activity = activity;

        // Infla o layout e adiciona à tela
        mainLayout = (ConstraintLayout) activity.getLayoutInflater().inflate(R.layout.loadingscreen, null);
        activity.addContentView(mainLayout, new ConstraintLayout.LayoutParams(-1, -1));

        // Referências corrigidas (usando mainLayout.findViewById)
        video = mainLayout.findViewById(R.id.videoBackground);
        final ProgressBar pg = mainLayout.findViewById(R.id.progressLoading);
        final TextView status = mainLayout.findViewById(R.id.txtStatus);
        final TextView files = mainLayout.findViewById(R.id.txtFiles);

        final String[] logs = {
            "mounting_data_vfs.cpp", "checking_game_version", "loading_nexus_textures.txd", 
            "init_render_hooks.oal", "load_player_anims.ifp", "parsing_water_vortex.dat", 
            "streaming_map_sectors", "loading_skin_cache", "init_audio_engine.bass", 
            "patching_stream_memory", "setup_gui_components", "loading_weather_system", 
            "connecting_to_nexus_api", "verifying_user_assets", "loading_custom_models.dff", 
            "applying_shader_optimizations", "init_camera_matrix", "pre_loading_soundtrack", 
            "fetching_server_info", "syncing_objects_at_pos", "loading_particle_effects", 
            "setup_voice_chat_buffers", "loading_vehicle_handling.cfg", "preparing_world_rendering", 
            "allocating_texture_memory", "finalizing_handshake", "checking_permissions", 
            "loading_map_icons", "clearing_temp_cache", "starting_game_instance"
        };

        // Esconder barras (Sistema)
        activity.getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        // Caminho do vídeo
        String path = "android.resource://" + activity.getPackageName() + "/" + R.raw.loading_video;
        video.setVideoPath(path);
        
        video.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            video.start();
        });

        video.setOnInfoListener((mp, what, extra) -> {
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                video.setBackgroundColor(Color.TRANSPARENT);
                return true;
            }
            return false;
        });

        // Thread de carregamento
        new Thread(() -> {
            for (int i = 0; i <= 100 && isRunning; i++) {
                final int p = i;
                activity.runOnUiThread(() -> {
                    if (!isRunning) return;
                    
                    pg.setProgress(p);
                    int logIndex = (int) (p * (logs.length - 1) / 100);
                    files.setText("loading: " + logs[logIndex]);

                    if (p < 20) status.setText("INICIANDO NÚCLEO...");
                    else if (p < 40) status.setText("CARREGANDO RESOURCES...");
                    else if (p < 60) status.setText("CARREGANDO TEXTURAS...");
                    else if (p < 80) status.setText("SINCRONIZANDO DADOS...");
                    else if (p < 95) status.setText("CARREGANDO ÁUDIOS...");
                    else status.setText("CONECTANDO!");

                    if (p == 1200) {
                        new Handler().postDelayed(this::hide, 300);
                    }
                });
                try { Thread.sleep(1200); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }).start();
    }

    public void hide() {
        isRunning = false; // Para a contagem da Thread
        if (video != null) {
            video.stopPlayback(); // Mata o vídeo e o áudio imediatamente
        }
        mainLayout.setVisibility(View.GONE); // GONE é melhor que INVISIBLE pra liberar a tela
    }
}
