#define IMGUI_DEFINE_MATH_OPERATORS
#include "../vendor/imgui/imgui.h"
#include "../vendor/imgui/imgui_internal.h"

#include <string>
#include "uisettings.h"
#include "../main.h"
#include "../settings.h"

extern CSettings* pSettings;

/* scaling */
// Mudado para 1280x720 para suportar telas 20:9 sem bugar
ImVec2 UISettings::m_baseSize = ImVec2(1280.0f, 720.0f);
ImVec2 UISettings::m_scaleFactor = ImVec2(1.0f, 1.0f);

/* font */
float UISettings::m_fontSize = 42.0f; // Aumentado para visibilidade HD
float UISettings::m_outlineSize = 2.0f;

/* ////////////////// pos & size ////////////////// */

/* widgets */
float UISettings::m_padding = 20.0f;

/* keyboard */
ImVec2 UISettings::m_keyboardPos = ImVec2(0.0f, 400.0f); // Desce o teclado para o fundo
ImVec2 UISettings::m_keyboardSize = ImVec2(1280.0f, 320.0f);
float UISettings::m_keyboardRowHeight = 60.0f;

/* splashscreen */
ImVec2 UISettings::m_splashscreenLogoPos = ImVec2(0.0f, 0.0f);
ImVec2 UISettings::m_splashscreenLogoSize = ImVec2(1280.0f, 720.0f);
ImVec2 UISettings::m_splashScreenPBarPos = ImVec2(340.0f, 650.0f);
ImVec2 UISettings::m_splashScreenPBarSize = ImVec2(600.0f, 12.0f);

/* chat */
int UISettings::m_chatDispMesssages = 9;
int UISettings::m_chatMaxMessages = 30;
// Posição ajustada: X=170 afasta o chat do mini-mapa
ImVec2 UISettings::m_chatPos = ImVec2(170.0f, 25.0f); 
ImVec2 UISettings::m_chatSize = ImVec2(600.0f, 0.0f);
ImVec2 UISettings::m_chatItemSize = ImVec2(600.0f, 22.0f); // Altura da linha aumentada

/* spawn */
ImVec2 UISettings::m_spawnPos = ImVec2(520.0f, 600.0f);
ImVec2 UISettings::m_spawnSize = ImVec2(240.0f, 60.0f);

/* nametag */
ImVec2 UISettings::m_nametagBarSize = ImVec2(40.0f, 8.0f);

/* dialog */
ImVec2 UISettings::m_dialogButtonPanelSize = ImVec2(200.0f, 50.0f);
ImVec2 UISettings::m_dialogMinSize = ImVec2(400.0f, 300.0f);
ImVec2 UISettings::m_dialogMaxSize = ImVec2(1000.0f, 600.0f);
float UISettings::m_dialogTitleHeight = 40.0f;
float UISettings::m_dialogListItemHeight = 45.0f;

/* buttonpanel */
ImVec2 UISettings::m_buttonPanelPos = ImVec2(20.0f, 250.0f);
ImVec2 UISettings::m_buttonPanelSize = ImVec2(400.0f, 60.0f);

/* voice button */
ImVec2 UISettings::m_buttonVoicePos = ImVec2(1100.0f, 250.0f);
ImVec2 UISettings::m_buttonVoiceSize = ImVec2(80.0f, 80.0f);

/* ////////////////// colors ////////////////// */

/* button */
ImColor UISettings::m_buttonColor = ImColor(0.0f, 0.0f, 0.0f, 0.70f);
ImColor UISettings::m_buttonFocusedColor = ImColor(0x64, 0x95, 0xED);

/* keyboard */
ImColor UISettings::m_keyboardBackgroundColor = ImColor(0, 0, 0, 180);

/* dialog */
ImColor UISettings::m_dialogBackgroundColor = ImColor(0, 0, 0, 220);
ImColor UISettings::m_dialogTitleBackgroundColor = ImColor(0, 0, 0, 255);

void UISettings::Initialize(const ImVec2& display_size)
{
    if(!pSettings) return;

    // A base agora é o tamanho real do seu Redmi
    m_baseSize = display_size; 
    m_scaleFactor = ImVec2(1.0f, 1.0f);

    // Ajuste da Fonte (5.5% da altura da tela = ~60px no seu Redmi)
    m_fontSize = display_size.y * 0.055f; 

    /* Chat - Posicionado para não bater no radar */
    // X em 15% da tela (360px) tira o chat de cima do mapa
    m_chatPos = ImVec2(display_size.x * 0.15f, display_size.y * 0.05f);
    m_chatItemSize = ImVec2(display_size.x * 0.45f, m_fontSize * 1.2f);
    
    /* Teclado - Ocupando a parte de baixo */
    m_keyboardSize = ImVec2(display_size.x, display_size.y * 0.40f);
    m_keyboardPos = ImVec2(0.0f, display_size.y - m_keyboardSize.y);
	m_keyboardRowHeight *= m_scaleFactor.y;

	/* splashscreen */
	m_splashscreenLogoSize = m_splashscreenLogoSize * m_scaleFactor;
	m_splashscreenLogoPos = ImVec2(0.0f, 0.0f);
	m_splashScreenPBarPos = m_splashScreenPBarPos * m_scaleFactor;
	m_splashScreenPBarSize = m_splashScreenPBarSize * m_scaleFactor;

	/* chat */
	m_chatPos = m_chatPos * m_scaleFactor;
	m_chatSize.y = m_chatItemSize.y * (float)m_chatDispMesssages;
	m_chatSize = m_chatSize * m_scaleFactor;
	m_chatItemSize = m_chatItemSize * m_scaleFactor;

	/* spawn */
	m_spawnPos = m_spawnPos * m_scaleFactor;
	m_spawnSize = m_spawnSize * m_scaleFactor;

	/* nametag */
	m_nametagBarSize = m_nametagBarSize * m_scaleFactor;

	/* dialog */
	m_dialogButtonPanelSize = m_dialogButtonPanelSize * m_scaleFactor;
	m_dialogMinSize = m_dialogMinSize * m_scaleFactor;
	m_dialogMaxSize = m_dialogMaxSize * m_scaleFactor;
	m_dialogTitleHeight *= m_scaleFactor.y;
	m_dialogListItemHeight *= m_scaleFactor.y;

	/* buttonpanel */
	m_buttonPanelPos = m_buttonPanelPos * m_scaleFactor;
	m_buttonPanelSize = m_buttonPanelSize * m_scaleFactor;

	/* button voice */
	m_buttonVoicePos = m_buttonVoicePos * m_scaleFactor;
	m_buttonVoiceSize = m_buttonVoiceSize * m_scaleFactor;
}
