/**
 * 
 */
package com.adbhelper.adb;

/**
 * @author Uladzimir Baraznouski
 *
 */
public interface IKeyCode {
    /** Key code constant: Unknown key code. */
    public static final int KEYCODE_UNKNOWN         = 0;
    /** Key code constant: Soft Left key.
     * Usually situated below the display on phones and used as a multi-function
     * feature key for selecting a software defined function shown on the bottom left
     * of the display. */
    public static final int KEYCODE_SOFT_LEFT       = 1;
    /** Key code constant: Soft Right key.
     * Usually situated below the display on phones and used as a multi-function
     * feature key for selecting a software defined function shown on the bottom right
     * of the display. */
    public static final int KEYCODE_SOFT_RIGHT      = 2;
    /** Key code constant: Home key.
     * This key is handled by the framework and is never delivered to applications. */
    public static final int KEYCODE_HOME            = 3;
    /** Key code constant: Back key. */
    public static final int KEYCODE_BACK            = 4;
    /** Key code constant: Call key. */
    public static final int KEYCODE_CALL            = 5;
    /** Key code constant: End Call key. */
    public static final int KEYCODE_ENDCALL         = 6;
    /** Key code constant: '0' key. */
    public static final int KEYCODE_0               = 7;
    /** Key code constant: '1' key. */
    public static final int KEYCODE_1               = 8;
    /** Key code constant: '2' key. */
    public static final int KEYCODE_2               = 9;
    /** Key code constant: '3' key. */
    public static final int KEYCODE_3               = 10;
    /** Key code constant: '4' key. */
    public static final int KEYCODE_4               = 11;
    /** Key code constant: '5' key. */
    public static final int KEYCODE_5               = 12;
    /** Key code constant: '6' key. */
    public static final int KEYCODE_6               = 13;
    /** Key code constant: '7' key. */
    public static final int KEYCODE_7               = 14;
    /** Key code constant: '8' key. */
    public static final int KEYCODE_8               = 15;
    /** Key code constant: '9' key. */
    public static final int KEYCODE_9               = 16;
    /** Key code constant: '*' key. */
    public static final int KEYCODE_STAR            = 17;
    /** Key code constant: '#' key. */
    public static final int KEYCODE_POUND           = 18;
    /** Key code constant: Directional Pad Up key.
     * May also be synthesized from trackball motions. */
    public static final int KEYCODE_DPAD_UP         = 19;
    /** Key code constant: Directional Pad Down key.
     * May also be synthesized from trackball motions. */
    public static final int KEYCODE_DPAD_DOWN       = 20;
    /** Key code constant: Directional Pad Left key.
     * May also be synthesized from trackball motions. */
    public static final int KEYCODE_DPAD_LEFT       = 21;
    /** Key code constant: Directional Pad Right key.
     * May also be synthesized from trackball motions. */
    public static final int KEYCODE_DPAD_RIGHT      = 22;
    /** Key code constant: Directional Pad Center key.
     * May also be synthesized from trackball motions. */
    public static final int KEYCODE_DPAD_CENTER     = 23;
    /** Key code constant: Volume Up key. */
    public static final int KEYCODE_VOLUME_UP       = 24;
    /** Key code constant: Volume Down key. */
    public static final int KEYCODE_VOLUME_DOWN     = 25;
    /** Key code constant: Power key. */
    public static final int KEYCODE_POWER           = 26;
    /** Key code constant: Camera key.
     * Used to launch a camera application or take pictures. */
    public static final int KEYCODE_CAMERA          = 27;
    /** Key code constant: Clear key. */
    public static final int KEYCODE_CLEAR           = 28;
    /** Key code constant: 'A' key. */
    public static final int KEYCODE_A               = 29;
    /** Key code constant: 'B' key. */
    public static final int KEYCODE_B               = 30;
    /** Key code constant: 'C' key. */
    public static final int KEYCODE_C               = 31;
    /** Key code constant: 'D' key. */
    public static final int KEYCODE_D               = 32;
    /** Key code constant: 'E' key. */
    public static final int KEYCODE_E               = 33;
    /** Key code constant: 'F' key. */
    public static final int KEYCODE_F               = 34;
    /** Key code constant: 'G' key. */
    public static final int KEYCODE_G               = 35;
    /** Key code constant: 'H' key. */
    public static final int KEYCODE_H               = 36;
    /** Key code constant: 'I' key. */
    public static final int KEYCODE_I               = 37;
    /** Key code constant: 'J' key. */
    public static final int KEYCODE_J               = 38;
    /** Key code constant: 'K' key. */
    public static final int KEYCODE_K               = 39;
    /** Key code constant: 'L' key. */
    public static final int KEYCODE_L               = 40;
    /** Key code constant: 'M' key. */
    public static final int KEYCODE_M               = 41;
    /** Key code constant: 'N' key. */
    public static final int KEYCODE_N               = 42;
    /** Key code constant: 'O' key. */
    public static final int KEYCODE_O               = 43;
    /** Key code constant: 'P' key. */
    public static final int KEYCODE_P               = 44;
    /** Key code constant: 'Q' key. */
    public static final int KEYCODE_Q               = 45;
    /** Key code constant: 'R' key. */
    public static final int KEYCODE_R               = 46;
    /** Key code constant: 'S' key. */
    public static final int KEYCODE_S               = 47;
    /** Key code constant: 'T' key. */
    public static final int KEYCODE_T               = 48;
    /** Key code constant: 'U' key. */
    public static final int KEYCODE_U               = 49;
    /** Key code constant: 'V' key. */
    public static final int KEYCODE_V               = 50;
    /** Key code constant: 'W' key. */
    public static final int KEYCODE_W               = 51;
    /** Key code constant: 'X' key. */
    public static final int KEYCODE_X               = 52;
    /** Key code constant: 'Y' key. */
    public static final int KEYCODE_Y               = 53;
    /** Key code constant: 'Z' key. */
    public static final int KEYCODE_Z               = 54;
    /** Key code constant: ',' key. */
    public static final int KEYCODE_COMMA           = 55;
    /** Key code constant: '.' key. */
    public static final int KEYCODE_PERIOD          = 56;
    /** Key code constant: Left Alt modifier key. */
    public static final int KEYCODE_ALT_LEFT        = 57;
    /** Key code constant: Right Alt modifier key. */
    public static final int KEYCODE_ALT_RIGHT       = 58;
    /** Key code constant: Left Shift modifier key. */
    public static final int KEYCODE_SHIFT_LEFT      = 59;
    /** Key code constant: Right Shift modifier key. */
    public static final int KEYCODE_SHIFT_RIGHT     = 60;
    /** Key code constant: Tab key. */
    public static final int KEYCODE_TAB             = 61;
    /** Key code constant: Space key. */
    public static final int KEYCODE_SPACE           = 62;
    /** Key code constant: Symbol modifier key.
     * Used to enter alternate symbols. */
    public static final int KEYCODE_SYM             = 63;
    /** Key code constant: Explorer special function key.
     * Used to launch a browser application. */
    public static final int KEYCODE_EXPLORER        = 64;
    /** Key code constant: Envelope special function key.
     * Used to launch a mail application. */
    public static final int KEYCODE_ENVELOPE        = 65;
    /** Key code constant: Enter key. */
    public static final int KEYCODE_ENTER           = 66;
    /** Key code constant: Backspace key.
     * Deletes characters before the insertion point. */
    public static final int KEYCODE_DEL             = 67;
    /** Key code constant: '`' (backtick) key. */
    public static final int KEYCODE_GRAVE           = 68;
    /** Key code constant: '-'. */
    public static final int KEYCODE_MINUS           = 69;
    /** Key code constant: '=' key. */
    public static final int KEYCODE_EQUALS          = 70;
    /** Key code constant: '[' key. */
    public static final int KEYCODE_LEFT_BRACKET    = 71;
    /** Key code constant: ']' key. */
    public static final int KEYCODE_RIGHT_BRACKET   = 72;
    /** Key code constant: '\' key. */
    public static final int KEYCODE_BACKSLASH       = 73;
    /** Key code constant: ';' key. */
    public static final int KEYCODE_SEMICOLON       = 74;
    /** Key code constant: ''' (apostrophe) key. */
    public static final int KEYCODE_APOSTROPHE      = 75;
    /** Key code constant: '/' key. */
    public static final int KEYCODE_SLASH           = 76;
    /** Key code constant: '@' key. */
    public static final int KEYCODE_AT              = 77;
    /** Key code constant: Number modifier key.
     * Used to enter numeric symbols.
     * This key is not Num Lock; it is more like {@link #KEYCODE_ALT_LEFT} and is
     * interpreted as an ALT key by {@link android.text.method.MetaKeyKeyListener}. */
    public static final int KEYCODE_NUM             = 78;
    /** Key code constant: Headset Hook key.
     * Used to hang up calls and stop media. */
    public static final int KEYCODE_HEADSETHOOK     = 79;
    /** Key code constant: Camera Focus key.
     * Used to focus the camera. */
    public static final int KEYCODE_FOCUS           = 80;   // *Camera* focus
    /** Key code constant: '+' key. */
    public static final int KEYCODE_PLUS            = 81;
    /** Key code constant: Menu key. */
    public static final int KEYCODE_MENU            = 82;
    /** Key code constant: Notification key. */
    public static final int KEYCODE_NOTIFICATION    = 83;
    /** Key code constant: Search key. */
    public static final int KEYCODE_SEARCH          = 84;
    /** Key code constant: Play/Pause media key. */
    public static final int KEYCODE_MEDIA_PLAY_PAUSE= 85;
    /** Key code constant: Stop media key. */
    public static final int KEYCODE_MEDIA_STOP      = 86;
    /** Key code constant: Play Next media key. */
    public static final int KEYCODE_MEDIA_NEXT      = 87;
    /** Key code constant: Play Previous media key. */
    public static final int KEYCODE_MEDIA_PREVIOUS  = 88;
    /** Key code constant: Rewind media key. */
    public static final int KEYCODE_MEDIA_REWIND    = 89;
    /** Key code constant: Fast Forward media key. */
    public static final int KEYCODE_MEDIA_FAST_FORWARD = 90;
    /** Key code constant: Mute key. */
    public static final int KEYCODE_MUTE            = 91;
    /** Key code constant: Page Up key. */
    public static final int KEYCODE_PAGE_UP         = 92;
    /** Key code constant: Page Down key. */
    public static final int KEYCODE_PAGE_DOWN       = 93;
    /** Key code constant: Picture Symbols modifier key.
     * Used to switch symbol sets (Emoji, Kao-moji). */
    public static final int KEYCODE_PICTSYMBOLS     = 94;   // switch symbol-sets (Emoji,Kao-moji)
    /** Key code constant: Switch Charset modifier key.
     * Used to switch character sets (Kanji, Katakana). */
    public static final int KEYCODE_SWITCH_CHARSET  = 95;   // switch char-sets (Kanji,Katakana)
    /** Key code constant: A Button key.
     * On a game controller, the A button should be either the button labeled A
     * or the first button on the upper row of controller buttons. */
    public static final int KEYCODE_BUTTON_A        = 96;
    /** Key code constant: B Button key.
     * On a game controller, the B button should be either the button labeled B
     * or the second button on the upper row of controller buttons. */
    public static final int KEYCODE_BUTTON_B        = 97;
    /** Key code constant: C Button key.
     * On a game controller, the C button should be either the button labeled C
     * or the third button on the upper row of controller buttons. */
    public static final int KEYCODE_BUTTON_C        = 98;
    /** Key code constant: X Button key.
     * On a game controller, the X button should be either the button labeled X
     * or the first button on the lower row of controller buttons. */
    public static final int KEYCODE_BUTTON_X        = 99;
    /** Key code constant: Y Button key.
     * On a game controller, the Y button should be either the button labeled Y
     * or the second button on the lower row of controller buttons. */
    public static final int KEYCODE_BUTTON_Y        = 100;
    /** Key code constant: Z Button key.
     * On a game controller, the Z button should be either the button labeled Z
     * or the third button on the lower row of controller buttons. */
    public static final int KEYCODE_BUTTON_Z        = 101;
    /** Key code constant: L1 Button key.
     * On a game controller, the L1 button should be either the button labeled L1 (or L)
     * or the top left trigger button. */
    public static final int KEYCODE_BUTTON_L1       = 102;
    /** Key code constant: R1 Button key.
     * On a game controller, the R1 button should be either the button labeled R1 (or R)
     * or the top right trigger button. */
    public static final int KEYCODE_BUTTON_R1       = 103;
    /** Key code constant: L2 Button key.
     * On a game controller, the L2 button should be either the button labeled L2
     * or the bottom left trigger button. */
    public static final int KEYCODE_BUTTON_L2       = 104;
    /** Key code constant: R2 Button key.
     * On a game controller, the R2 button should be either the button labeled R2
     * or the bottom right trigger button. */
    public static final int KEYCODE_BUTTON_R2       = 105;
    /** Key code constant: Left Thumb Button key.
     * On a game controller, the left thumb button indicates that the left (or only)
     * joystick is pressed. */
    public static final int KEYCODE_BUTTON_THUMBL   = 106;
    /** Key code constant: Right Thumb Button key.
     * On a game controller, the right thumb button indicates that the right
     * joystick is pressed. */
    public static final int KEYCODE_BUTTON_THUMBR   = 107;
    /** Key code constant: Start Button key.
     * On a game controller, the button labeled Start. */
    public static final int KEYCODE_BUTTON_START    = 108;
    /** Key code constant: Select Button key.
     * On a game controller, the button labeled Select. */
    public static final int KEYCODE_BUTTON_SELECT   = 109;
    /** Key code constant: Mode Button key.
     * On a game controller, the button labeled Mode. */
    public static final int KEYCODE_BUTTON_MODE     = 110;

    
    
    
}
