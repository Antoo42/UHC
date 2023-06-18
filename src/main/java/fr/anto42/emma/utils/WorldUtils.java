package fr.anto42.emma.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldUtils {

    public static int distance(Location Locp, Location Loc) {
        if (Locp.getWorld() == Loc.getWorld()) {
            double xp = Locp.getBlockX();
            double zp = Locp.getBlockZ();
            double xl = Loc.getBlockX();
            double zl = Loc.getBlockZ();
            double distancex = xp - xl;
            if (distancex < 0.0D)
                distancex = -distancex;
            double distancez;
            if ((distancez = zp - zl) < 0.0D)
                distancez = -distancez;
            double distance = Math.sqrt(Math.pow(distancex, 2.0D) + Math.pow(distancez, 2.0D));
            return (int)distance;
        }
        return -1;
    }

    public static double Angle(Player p, Location Loc) {
        Location Locp = p.getLocation();
        if (Locp.getWorld() == Loc.getWorld()) {
            if (Locp.getBlockX() != Loc.getBlockX() || Locp.getBlockZ() != Loc.getBlockZ()) {
                double xp = Locp.getBlockX();
                double zp = Locp.getBlockZ();
                double xl = Loc.getBlockX();
                double zl = Loc.getBlockZ();
                double distancex = xp - xl;
                double distancecx = (distancex < 0.0D) ? -distancex : distancex;
                double distancez = zp - zl;
                double distancecz = (distancez < 0.0D) ? -distancez : distancez;
                double angle = 180.0D * Math.atan(distancecz / distancecx) / Math.PI;
                if (distancex < 0.0D || distancez < 0.0D)
                    if (distancex < 0.0D && distancez >= 0.0D) {
                        angle = 90.0D - angle + 90.0D;
                    } else if (distancex <= 0.0D && distancez < 0.0D) {
                        angle += 180.0D;
                    } else if (distancex > 0.0D && distancez < 0.0D) {
                        angle = 90.0D - angle + 270.0D;
                    }
                if ((angle += 270.0D) >= 360.0D)
                    angle -= 360.0D;
                if ((angle -= (p.getEyeLocation().getYaw() + 180.0F)) <= 0.0D)
                    angle += 360.0D;
                if (angle <= 0.0D)
                    angle += 360.0D;
                return angle;
            }
            return -1.0D;
        }
        return -2.0D;
    }

    public static String Fleche(double angle) {
        String c = "";
        if (angle == -2.0D) {
            c = "";
        } else if (angle == -1.0D) {
            c = "";
        } else if ((angle < 22.5D && angle >= 0.0D) || angle > 337.5D) {
            c = "↑";
        } else if (angle < 67.5D && angle > 22.5D) {
            c = "↗";
        } else if (angle < 112.5D && angle > 67.5D) {
            c = "→";
        } else if (angle < 157.5D && angle > 112.5D) {
            c = "↘";
        } else if (angle < 202.5D && angle > 157.5D) {
            c = "↓";
        } else if (angle < 247.5D && angle > 202.5D) {
            c = "↙";
        } else if (angle < 292.5D && angle > 247.5D) {
            c = "←";
        } else if (angle < 337.5D && angle > 292.5D) {
            c = "↖";
        }
        return c;
    }

}
