package cn.jzvid;

public class JZVideoPlayerManager {
    public static JZVideoPlayer FIRST_FLOOR_JZVD;
    public static JZVideoPlayer SECOND_FLOOR_JZVD;

    public static JZVideoPlayer getFirstFloor() {
        return FIRST_FLOOR_JZVD;
    }

    public static void setFirstFloor(JZVideoPlayer jZVideoPlayer) {
        FIRST_FLOOR_JZVD = jZVideoPlayer;
    }

    public static JZVideoPlayer getSecondFloor() {
        return SECOND_FLOOR_JZVD;
    }

    public static void setSecondFloor(JZVideoPlayer jZVideoPlayer) {
        SECOND_FLOOR_JZVD = jZVideoPlayer;
    }

    public static JZVideoPlayer getCurrentJzvd() {
        if (getSecondFloor() != null) {
            return getSecondFloor();
        }
        return getFirstFloor();
    }

    public static void completeAll() {
        if (SECOND_FLOOR_JZVD != null) {
            SECOND_FLOOR_JZVD.onCompletion();
            SECOND_FLOOR_JZVD = null;
        }
        if (FIRST_FLOOR_JZVD != null) {
            FIRST_FLOOR_JZVD.onCompletion();
            FIRST_FLOOR_JZVD = null;
        }
    }
}
