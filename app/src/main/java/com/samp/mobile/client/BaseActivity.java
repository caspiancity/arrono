package com.samp.mobile.client;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.ImageView;
import com.samp.mobile.R;

public class BaseActivity extends Activity {
	
    private ImageView imageview5;
    private Button btnJogar;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.base); 

		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
        imageview5 = findViewById(R.id.imageview5);
        btnJogar = findViewById(R.id.btnJogar);

		imageview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
                // AGORA FUNCIONA PORQUE ESTÃO NO MESMO PACOTE
				Intent it = new Intent(BaseActivity.this, ConfigActivity.class);
				startActivity(it);
			}
		});
		
		btnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                // Inicia o Jogo
                Intent intent = new Intent(BaseActivity.this, com.samp.mobile.game.SAMP.class);
                startActivity(intent);
                finish();
            }
        });
	}
	
	private void initializeLogic() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { 
			final WindowInsetsController controller = getWindow().getInsetsController();
			if (controller != null) {
				controller.hide(WindowInsets.Type.navigationBars() | WindowInsets.Type.statusBars());
				controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
			}
		} else {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | 
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | 
                View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
	}
}
