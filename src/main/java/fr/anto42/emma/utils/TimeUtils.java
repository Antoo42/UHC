package fr.anto42.emma.utils;

public class TimeUtils {
    public static int minutes(int m){
        return m*20*60;
    }

    public static int seconds(int s) {
        return s * 20;
    }

    public static String getFormattedTime(long time){
        int h,m;
        h = (int) time / (60 * 60);
        time -= h * (60 * 60);
        m = (int) time / 60;
        time -= m * 60;

        if (h == 0){
            if (m == 0){
                return time+"s";
            }else{
                return m+"m "+time+"s";
            }
        }else{
            return h+"h "+m+"m "+time+"s";
        }
    }


    public static String getEcartTimer(long initial, long other){
        int time = (int) (initial - other);
        int h,m;
        h = time / (60 * 60);
        time -= h * (60 * 60);
        m = time / 60;
        time -= m * 60;

        if (h == 0){
            if (m == 0){
                return time+"s";
            }else{
                return m+"m "+time+"s";
            }
        }else{
            return h+"h "+m+"m "+time+"s";
        }
    }
}
