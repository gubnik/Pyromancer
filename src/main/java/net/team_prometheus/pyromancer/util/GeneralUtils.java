package net.team_prometheus.pyromancer.util;

import java.util.Arrays;

public class GeneralUtils {
    public static int rgbToColorInteger(int red, int green, int blue){
        return 65536 * red + 256 * green + blue;
    }
    public static int rgbaToColorInteger(int red, int green, int blue, int alpha){
        return 16777216 * alpha + 65536 * red + 256 * green + blue;
    }
}
