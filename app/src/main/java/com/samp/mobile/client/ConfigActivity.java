package com.my.newproject18;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.samp.mobile.R; // Certifique-se que o package do R está correto

public class ConfigActivity extends Activity {
	
    // Definimos os componentes como variáveis da classe
    private EditText editNick;
    private Button btn30, btn60, btn120, btnSalvar;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		
        // 1. Inflar o layout manualmente (Sem Binding)
		setContentView(R.layout.config); // Verifique se o nome do XML é settings

		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
        // 2. Vincular os IDs
        editNick = findViewById(R.id.editNickname);
        btn30 = findViewById(R.id.btnFps30);
        btn60 = findViewById(R.id.btnFps60);
        btn120 = findViewById(R.id.btnFps120);
        btnSalvar = findViewById(R.id.btnSalvar);
        
        settings = getSharedPreferences("Settings", MODE_PRIVATE);
        editor = settings.edit();
	}
	
	private void initializeLogic() {
		// Carregar dados salvos
		String nickSalvo = settings.getString("nick", "Novo_Jogador");
		editNick.setText(nickSalvo);
        
        // Estilo inicial do EditText
		editNick.setBackgroundColor(Color.parseColor("#1AFFFFFF"));
		
        // Reset visual inicial dos botões
		btn30.setBackgroundColor(Color.parseColor("#1AFFFFFF"));
		btn60.setBackgroundColor(Color.parseColor("#1AFFFFFF"));
		btn120.setBackgroundColor(Color.parseColor("#1AFFFFFF"));

        // Marcar o botão que já estava salvo (exemplo para o de 60 FPS)
        int fpsSalvo = settings.getInt("fps", 60);
        if (fpsSalvo == 30) destacaBotao(btn30);
        else if (fpsSalvo == 60) destacaBotao(btn60);
        else if (fpsSalvo == 120) destacaBotao(btn120);
		
		// Função de clique dos FPS
		View.OnClickListener fpsListener = v -> {
			// Reseta todos
			btn30.setBackgroundColor(Color.parseColor("#1AFFFFFF"));
			btn30.setTextColor(Color.WHITE);
			btn60.setBackgroundColor(Color.parseColor("#1AFFFFFF"));
			btn60.setTextColor(Color.WHITE);
			btn120.setBackgroundColor(Color.parseColor("#1AFFFFFF"));
			btn120.setTextColor(Color.WHITE);
			
			// Destaca o selecionado
            destacaBotao((Button)v);
			
			// Guarda o valor
			if(v.getId() == R.id.btnFps30) editor.putInt("fps", 30);
			else if(v.getId() == R.id.btnFps60) editor.putInt("fps", 60);
			else if(v.getId() == R.id.btnFps120) editor.putInt("fps", 120);
			
			editor.apply(); 
		};
		
		btn30.setOnClickListener(fpsListener);
		btn60.setOnClickListener(fpsListener);
		btn120.setOnClickListener(fpsListener);
		
		// Ação de Salvar
		btnSalvar.setOnClickListener(v -> {
			String nick = editNick.getText().toString();
			
			if (nick.contains("_") && nick.length() > 5) {
				editor.putString("nick", nick);
				editor.apply();
				Toast.makeText(this, "Configurações Aplicadas!", Toast.LENGTH_SHORT).show();
                
                // Volta para a MainActivity
				Intent it = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(it);
				finish();
			} else {
				editNick.setError("Use o formato Nome_Sobrenome!");
			}
		});
	}

    // Helper para não repetir código de estilo
    private void destacaBotao(Button b) {
        b.setBackgroundResource(R.drawable.fps_btn_selected);
        b.setTextColor(Color.BLACK);
    }
}
