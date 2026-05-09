#define IMGUI_DEFINE_MATH_OPERATORS
#include "../vendor/imgui/imgui.h"
#include "../vendor/imgui/imgui_internal.h"
#include <string>
#include "uisettings.h"
#include "../main.h"
#include "../settings.h"

extern CSettings* pSettings;

/* scaling - Base moderna para dispositivos Full HD/4K */
ImVec2 UISettings::m_baseSize = ImVec2(1280.0f, 720.0f); 
ImVec2 UISettings::m_scaleFactor = ImVec2(1.0f, 1.0f);

/* font */
float UISettings::m_fontSize = 32.0f; 
float UISettings::m_outlineSize = 1.5f;

/* widgets */
float UISettings::m_padding = 20.0f;

/* keyboard - Agora pegando a largura total e mais alto */
ImVec2 UISettings::m_keyboardPos = ImVec2(0.0f, 400.0f);
ImVec2 UISettings::m_keyboardSize = ImVec2(1280.0f, 320.0f);
float UISettings::m_keyboardRowHeight = 65.0f;

/* splashscreen - Tela Cheia */
ImVec2 UISettings::m_splashscreenLogoPos = ImVec2(0.0f, 0.0f);
ImVec2 UISettings::m_splashscreenLogoSize = ImVec2(1280.0f, 720.0f);
ImVec2 UISettings::m_splashScreenPBarPos = ImVec2(340.0f, 650.0f);
ImVec2 UISettings::m_splashScreenPBarSize = ImVec2(600.0f, 12.0f);

/* chat - Esticado para ocupar a lateral esquerda igual PC */
int UISettings::m_chatDispMesssages = 10;
int UISettings::m_chatMaxMessages = 50;
ImVec2 UISettings::m_chatPos = ImVec2(25.0f, 25.0f); // Canto superior esquerdo
ImVec2 UISettings::m_chatSize = ImVec2(800.0f, 0.0f); // Largura de 800 para mensagens longas
ImVec2 UISettings::m_chatItemSize = ImVec2(800.0f, 28.0f); // Altura de linha confortável

/* spawn - Centralizado embaixo */
ImVec2 UISettings::m_spawnPos = ImVec2(520.0f, 620.0f);
ImVec2 UISettings::m_spawnSize = ImVec2(240.0f, 60.0f);

/* nametag - Barras de vida maiores para enxergar de longe */
ImVec2 UISettings::m_nametagBarSize = ImVec2(50.0f, 10.0f);

/* dialog - Ajustado para não ficar um quadrado minúsculo */
ImVec2 UISettings::m_dialogButtonPanelSize = ImVec2(200.0f, 60.0f);
ImVec2 UISettings::m_dialogMinSize = ImVec2(400.0f, 300.0f);
ImVec2 UISettings::m_dialogMaxSize = ImVec2(1100.0f, 600.0f);
float UISettings::m_dialogTitleHeight = 45.0f;
float UISettings::m_dialogListItemHeight = 40.0f;

/* buttonpanel (Tags/Comandos laterais) */
ImVec2 UISettings::m_buttonPanelPos = ImVec2(15.0f, 300.0f);
ImVec2 UISettings::m_buttonPanelSize = ImVec2(450.0f, 60.0f);

/* voice button */
ImVec2 UISettings::m_buttonVoicePos = ImVec2(1150.0f, 250.0f);
ImVec2 UISettings::m_buttonVoiceSize = ImVec2(80.0f, 80.0f);

/* ////////////////// colors ////////////////// */
ImColor UISettings::m_buttonColor = ImColor(0, 0, 0, 180);
ImColor UISettings::m_buttonFocusedColor = ImColor(100, 149, 237, 255); // CornflowerBlue
ImColor UISettings::m_keyboardBackgroundColor = ImColor(0, 0, 0, 180);
ImColor UISettings::m_dialogBackgroundColor = ImColor(15, 15, 15, 230);
ImColor UISettings::m_dialogTitleBackgroundColor = ImColor(15, 15, 15, 255);

void UISettings::Initialize(const ImVec2& display_size)
{
    if(!pSettings) return;
    
    // Calcula o fator baseado na tela do seu celular (Ex: 2400 / 1280)
    m_scaleFactor.x = display_size.x / m_baseSize.x;
    m_scaleFactor.y = display_size.y / m_baseSize.y;

    m_chatDispMesssages = pSettings->Get().iChatMaxMessages;
    m_fontSize *= m_scaleFactor.y;

    m_keyboardSize = m_keyboardSize * m_scaleFactor;
    m_keyboardPos = m_keyboardPos * m_scaleFactor;
    m_keyboardRowHeight *= m_scaleFactor.y;

    m_splashscreenLogoSize = m_splashscreenLogoSize * m_scaleFactor;
    m_splashScreenPBarPos = m_splashScreenPBarPos * m_scaleFactor;
    m_splashScreenPBarSize = m_splashScreenPBarSize * m_scaleFactor;

    m_chatPos = m_chatPos * m_scaleFactor;
    m_chatItemSize = m_chatItemSize * m_scaleFactor;
    m_chatSize.x = m_chatItemSize.x;
    m_chatSize.y = m_chatItemSize.y * m_chatDispMesssages;

    m_spawnPos = m_spawnPos * m_scaleFactor;
    m_spawnSize = m_spawnSize * m_scaleFactor;
    m_nametagBarSize = m_nametagBarSize * m_scaleFactor;

    m_dialogButtonPanelSize = m_dialogButtonPanelSize * m_scaleFactor;
    m_dialogMinSize = m_dialogMinSize * m_scaleFactor;
    m_dialogMaxSize = m_dialogMaxSize * m_scaleFactor;
    m_dialogTitleHeight *= m_scaleFactor.y;
    m_dialogListItemHeight *= m_scaleFactor.y;

    m_buttonPanelPos = m_buttonPanelPos * m_scaleFactor;
    m_buttonPanelSize = m_buttonPanelSize * m_scaleFactor;

    m_buttonVoicePos = m_buttonVoicePos * m_scaleFactor;
    m_buttonVoiceSize = m_buttonVoiceSize * m_scaleFactor;
}
