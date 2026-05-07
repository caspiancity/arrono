package com.samp.mobile.game.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;
import com.samp.mobile.R;
//import com.samp.mobile.MainActivity;

public class LoadingScreen {

    private Activity activity;
    private View mainLayout; // Mudado para View para aceitar qualquer layout (Relative/Constraint)
    private VideoView video;
    private boolean isRunning = true;

    public LoadingScreen(Activity activity) {
        this.activity = activity;

        // Infla o layout usando View genérica para evitar o erro de Cast da print
        mainLayout = activity.getLayoutInflater().inflate(R.layout.loadingscreen, null);
        
        // Adiciona à tela usando LayoutParams genéricos
        activity.addContentView(mainLayout, new ViewGroup.LayoutParams(-1, -1));

        // Busca os componentes dentro do mainLayout
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

        // Força modo Fullscreen/Imersivo
        activity.getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

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

        // Loop de carregamento
        new Thread(() -> {
            for (int i = 0; i <= 100 && isRunning; i++) {
                final int p = i;
                activity.runOnUiThread(() -> {
                    if (!isRunning || mainLayout == null) return;
                    
                    pg.setProgress(p);
                    int logIndex = (int) (p * (logs.length - 1) / 100);
                    files.setText("loading: " + logs[logIndex]);

                    // Textos em Português como pedido
                    if (p < 20) status.setText("INICIANDO NÚCLEO...");
                    else if (p < 40) status.setText("CARREGANDO RESOURCES...");
                    else if (p < 60) status.setText("CARREGANDO TEXTURAS...");
                    else if (p < 80) status.setText("SINCRONIZANDO DADOS...");
                    else if (p < 95) status.setText("CARREGANDO ÁUDIOS...");
                    else status.setText("CONECTANDO!");

                    if (p == 100) {
                        new Handler().postDelayed(this::hide, 500);
                    }
                });
                try { Thread.sleep(150); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }).start();
    }

    // O método hide que a lib chama
    public void hide() {
        isRunning = false; 
        activity.runOnUiThread(() -> {
            if (video != null) {
                video.stopPlayback(); // Para o vídeo e o som imediatamente
            }
            if (mainLayout != null) {
                mainLayout.setVisibility(View.GONE); // Remove da tela
                // Remove fisicamente a view para liberar memória
                ((ViewGroup) mainLayout.getParent()).removeView(mainLayout);
                mainLayout = null;
            }
        });
    }
}
