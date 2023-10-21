package Main;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        //Trab1 trab = new Trab1(1,0.1,0.1,0.001,60,30,1,0,1,1000,1000);
        try {
            Method method = Trab1.class.getDeclaredMethod("Beam_Warming", int.class);
            Trab1 trab = new Trab1(20.0, 2, 0.1, 0.001, 60, 30, 1, 0, 2, 200, 10, method);
            trab.run();
        } catch(Exception e) {e.printStackTrace();}
    }
}