package com.ho.edcustom.DTO;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class KeyCaps {
    private Map<String, String> keyColors = new HashMap<>();

    private static final List<String> ALL_KEYS = List.of(
            "keycap_Esc","keycap_F1","keycap_F2","keycap_F3","keycap_F4","keycap_F5","keycap_F6","keycap_F7","keycap_F8",
            "keycap_F9","keycap_F10","keycap_F11","keycap_F12",

            "keycap_Grave","keycap_1","keycap_2","keycap_3","keycap_4","keycap_5","keycap_6","keycap_7",
            "keycap_8","keycap_9","keycap_0","keycap_Minus","keycap_Equals","keycap_BackSpace",

            "keycap_Tab","keycap_Q","keycap_W","keycap_E","keycap_R","keycap_T","keycap_Y","keycap_U",
            "keycap_I","keycap_O","keycap_P","keycap_LeftBracket","keycap_RightBracket","keycap_ReverseSlash",

            "keycap_CapsLock","keycap_A","keycap_S","keycap_D","keycap_F","keycap_G","keycap_H",
            "keycap_J","keycap_K","keycap_L","keycap_Semicolon","keycap_Quote","keycap_Enter",

            "keycap_LShift","keycap_Z","keycap_X","keycap_C","keycap_V","keycap_B","keycap_N","keycap_M",
            "keycap_Comma","keycap_Dot","keycap_Slash","keycap_RShift",

            "keycap_LCtrl","keycap_Window","keycap_LAlt","keycap_Space","keycap_RAlt","keycap_Fn",
            "keycap_Menu","keycap_RCtrl",

            "keycap_Print","keycap_ScrollLock","keycap_Pause",
            "keycap_Insert","keycap_Home","keycap_PgUp","keycap_Delete","keycap_End","keycap_PgDn",
            "keycap_Up","keycap_Left","keycap_Down","keycap_Right",

            "keycap_KeyNumLock","keycap_KeySlash","keycap_KeyStar","keycap_KeyMinus","keycap_KeyPlus","keycap_KeyEnter","keycap_KeyDot",
            "keycap_Key0","keycap_Key1","keycap_Key2","keycap_Key3","keycap_Key4","keycap_Key5","keycap_Key6",
            "keycap_Key7","keycap_Key8","keycap_Key9"
    );

    public KeyCaps() {
        for (String key : ALL_KEYS) {
            keyColors.put(key, "#FFFFFF");
        }
    }

    public String getColor(String key) {
        return keyColors.getOrDefault(key, "#FFFFFF");
    }

    public void setColor(String key, String color) {
        if (keyColors.containsKey(key)) {
            keyColors.put(key, color);
        }
    }

    public  void setAllColors(String colors) {
        for (String key : ALL_KEYS) {
            keyColors.put(key, colors);
        }
    }

    public Map<String, String> getKeyColors() {
        return keyColors;
    }
}
