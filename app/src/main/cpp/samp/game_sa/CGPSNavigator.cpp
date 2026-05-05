#include "CGPSNavigator.h"
#include <imgui.h>
#include "../game/game.h"
#include "../game/RW/RenderWare.h"
#include "../util/CUtil.h"
#include "../main.h"

extern CGame* pGame;

CGPSNavigator::CGPSNavigator()
{
    m_bActive = false;
    m_vecTarget.x = 0.0f;
    m_vecTarget.y = 0.0f;
    m_vecTarget.z = 0.0f;

    // โหลด texture จาก TXD (samp.txt)
    texId = CUtil::LoadTextureFromDB("samp", "gps_icon");  
    FLog("CGPSNavigator::LoadTexture : gps_icon ...");
}

void CGPSNavigator::SetTarget(const CVector& vecTarget)
{
    m_vecTarget = vecTarget;
    m_bActive = true;
    FLog("CGPSNavigator::SetTarget");
}

void CGPSNavigator::Clear()
{
    m_bActive = false;
    FLog("CGPSNavigator::Clear");
}


void CGPSNavigator::Render()
{
    if (!m_bActive) return;

    // 1. Verificação de segurança (essencial no Android 15)
    if (!pGame || !pGame->FindPlayerPed() || !pGame->FindPlayerPed()->m_pPed) return;

    CVector vecScreen;
    float w, h; 
    
    // 2. Chamada corrigida baseada no seu dump:
    // CSprite::CalcScreenCoors(CVector const&, CVector*, float*, float*, bool, bool)
    typedef bool (*CalcScreenCoors_t)(const CVector&, CVector*, float*, float*, bool, bool);
    CalcScreenCoors_t CalcScreenCoors = (CalcScreenCoors_t)(g_libGTASA + 0x6E9DF8);
    
    // Chamando com todos os argumentos que o dump mostrou
    if (!CalcScreenCoors(m_vecTarget, &vecScreen, &w, &h, true, true)) return;

    // Se o ponto estiver atrás da câmera, vecScreen.z geralmente é < 1.0
    if (vecScreen.z < 1.0f) return;

    // 3. Pegando a posição com GetPosition() que confirmamos ser o correto
    CVector playerPos = pGame->FindPlayerPed()->m_pPed->GetPosition();

    // 4. Cálculo de distância
    float dist = sqrtf(
        (playerPos.x - m_vecTarget.x) * (playerPos.x - m_vecTarget.x) +
        (playerPos.y - m_vecTarget.y) * (playerPos.y - m_vecTarget.y) +
        (playerPos.z - m_vecTarget.z) * (playerPos.z - m_vecTarget.z)
    );

    // 5. Desenho com ImGui
    if(texId && texId->raster) 
    {
        float size = 64.0f;
        ImVec2 posIcon(vecScreen.x - size * 0.5f, vecScreen.y - size * 0.5f);
        
        ImGui::GetBackgroundDrawList()->AddImage(
            (ImTextureID)texId->raster, 
            posIcon, 
            ImVec2(posIcon.x + size, posIcon.y + size)
        );

        // Texto de distância
        char szText[32];
        if (dist < 1000.0f) snprintf(szText, sizeof(szText), "%.0fm", dist);
        else snprintf(szText, sizeof(szText), "%.2fkm", dist / 1000.0f);

        ImVec2 textSize = ImGui::CalcTextSize(szText);
        ImGui::GetBackgroundDrawList()->AddText(
            ImVec2(vecScreen.x - textSize.x * 0.5f, posIcon.y + size + 2.0f), 
            IM_COL32(255, 255, 255, 255), 
            szText
        );
    }
}
