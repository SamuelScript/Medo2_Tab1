package Main;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        //Trab1 trab = new Trab1(1,0.1,0.1,0.001,60,30,1,0,1,1000,1000);
        //Trab1 trab = new Trab1(20.0, 2, 0.1, 0.001, 60, 30, 1, 0, 2, 200, 10, method);
        //Trab1 trab = new Trab1(20.0, 2, 0.1, 0.1, 120, 60, 1, 0, 2, 1000, 10, method);
        try {
            Method mtrab1 = Trab1.class.getDeclaredMethod("mtrab1", int.class);
            Method ftbs = Trab1.class.getDeclaredMethod("FTBS", int.class);
            Method laxfr = Trab1.class.getDeclaredMethod("Lax_Friedrichs", int.class);
            Method laxwe = Trab1.class.getDeclaredMethod("Lax_Wendroff", int.class);
            Method beamw = Trab1.class.getDeclaredMethod("Beam_Warming", int.class);
            Trab1 trab = new Trab1(5, 0.5, 0.1, 0.001, 60, 30, 5, 2, 10, 1000, 5, laxwe);

            trab.run();
        } catch(Exception e) {e.printStackTrace();}
    }
}