package Main;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        //Trab1 trab = new Trab1(1,0.1,0.1,0.001,60,30,1,0,1,1000,1000);
        //Trab1 trab = new Trab1(20.0, 2, 0.1, 0.001, 60, 30, 1, 0, 2, 200, 10, method);
        //Trab1 trab = new Trab1(20.0, 2, 0.1, 0.1, 120, 60, 1, 0, 2, 1000, 10, method);
        try {
            final Method trab1 = Trab1.class.getDeclaredMethod("Algoritmo_Trabalho_1", int.class);
            final Method ftbs = Trab1.class.getDeclaredMethod("FTBS", int.class);
            final Method laxfr = Trab1.class.getDeclaredMethod("Lax_Friedrichs", int.class);
            final Method laxwe = Trab1.class.getDeclaredMethod("Lax_Wendroff", int.class);
            final Method beamw = Trab1.class.getDeclaredMethod("Beam_Warming", int.class);

            Trab1 trab = new Trab1(length_x, length_xf, u, a, t_max, t_int, Ca, Cb, Cc, s_partition, draw, beamw);
            trab.run();
        } catch(Exception e) {e.printStackTrace();}
    }
}