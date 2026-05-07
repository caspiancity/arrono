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
import android.widget.TextView;
import com.samp.mobile.R;
import com.samp.mobile.game.SAMP;

public class BaseActivity extends Activity {
	
    // Definimos os componentes aqui
    private ImageView imageview5;
    private Button btnJogar;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		
        // 1. Define o layout XML (Tenha certeza que o nome do arquivo é activity_main ou o nome que você deu)
		setContentView(R.layout.base); 

		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
        // 2. Vincula os IDs do XML manualmente (Sem Binding)
        imageview5 = findViewById(R.id.imageview5);
        btnJogar = findViewById(R.id.btnJogar); // No XML que te passei era btn_jogar

		imageview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent it = new Intent(getApplicationContext(), ConfigActivity.class);
				startActivity(it);
				// finish(); // Remova o finish se você quiser poder voltar para a Main
			}
		});
		
		btnJogar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View _view) {
        // 1. Criar a Intent corretamente
        Intent intent = new Intent(BaseActivity.this, com.samp.mobile.game.SAMP.class);
        
        // 2. Iniciar a Activity do Jogo
        startActivity(intent);
        
        // 3. Fechar o Launcher para liberar memória pro jogo
        finish();
        
        // dismiss() removido (só se usa em Dialogs/Popups)
    }
});
    
	}
	
	private void initializeLogic() {
        // Esconder barras de navegação
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { 
			final WindowInsetsController controller = getWindow().getInsetsController();
			if (controller != null) {
				controller.hide(WindowInsets.Type.navigationBars() | WindowInsets.Type.statusBars());
				controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
			}
		} else {
            // Suporte para Androids antigos
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | 
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | 
                View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
	}
}
