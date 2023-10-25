package Main;

import java.lang.reflect.Method;

public class Main {
    static double length_x = 1; //Lx : entre 1 e 20 [metros]
    static double length_xf = length_x*0.2; //Uma fração de Lx
    static double u = 0.001; //Coeficiente de advecção : entre 0.1 a 2.0 [metros/segundo]
    static double a = 0.001; //Coeficiente de difusão : entre 0.001 a 0.1 [metros²/segundo]
    static double t_max = 60; // tempo máximo : entre 60 e 600 [segundos]
    static double t_int = t_max*0.5; // tempo intermediário : % t_max
    static double Ca = 5;// 5_1_2_ //Concentração inicial A
    static double Cb = 1;// 2_0_1_ //Concentração inicial B
    static double Cc = 10;//10_1_1_ //Concentração inicial C
    static final int s_partition = 200;
    static final int draw = 5;

    public static void main(String[] args) {
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