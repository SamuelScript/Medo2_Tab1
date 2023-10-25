package Main;

import sun.reflect.Reflection;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Trab1 {
    private final double length_x; //Lx : entre 1 e 20 [metros]
    private final double length_xf; //Uma fração de Lx
    private final double u; //Coeficiente de advecção : entre 0.1 a 2.0 [metros/segundo]
    private final double a; //Coeficiente de difusão : entre 0.001 a 0.1 [metros²/segundo]
    private final double t_max; // tempo máximo : entre 60 e 600 [segundos]
    private final double t_int; // tempo intermediário : % t_max
    private final double deltaX; //Comprimento da malha espacial
    private final double deltaT; //Intervalo da malha temporal
    private final double Ca; //Concentração inicial A
    private final double Cb; //Concentração inicial B
    private final double Cc; //Concentração inicial C
    private final Method method; //O método que vai ser usado por este objeto. Em Java, um "Method" é basicamente uma referência a uma função.
    private double[] Qs; //Valores no interior do domínio
    private double t_draw;
    private double dt_draw;
    private GChart chart;

    private double mtrab1( int Xi ) {
        return Qs[Xi] - (deltaT/deltaX)*(u*(Qs[Xi] - Qs[Xi-1]) - a*((Qs[Xi+1] - 2*Qs[Xi] + Qs[Xi-1])/deltaX));
    }

    private double FTBS( int Xi ) {
        return Qs[Xi] - u*(deltaT/deltaX)*(Qs[Xi] - Qs[Xi-1]);
    }

    private double Lax_Friedrichs( int Xi ) {
        return (Qs[Xi+1] + Qs[Xi-1])/2 - u*(deltaT/deltaX)*(Qs[Xi+1] - Qs[Xi-1]);
    }

    private double Lax_Wendroff( int Xi ) {
        return Qs[Xi] - u*(deltaT/deltaX)*(Qs[Xi+1] - Qs[Xi-1]) + ((u*u*deltaT*deltaT) / (2*deltaX*deltaX))*(Qs[Xi+1] - 2*Qs[Xi] + Qs[Xi-1]);
    }

    private double Beam_Warming( int Xi ) {
        int x_2 = Math.max(0, Xi-2);
        return Qs[Xi] - ((u*deltaT) / (2*deltaX))*(3*Qs[Xi] - 4*Qs[Xi-1] + Qs[x_2]) + ((u*u*deltaT*deltaT) / (2*deltaX*deltaX))*(Qs[Xi] - 2*Qs[Xi-1] + Qs[x_2]);
    }

    Trab1(double length_x, double length_xf, double u, double a, double t_max, double t_int, double Ca, double Cb, double Cc, int s_partition, int draw, Method method) { //Inicializa todos os valores para o tempo 0.
        this.length_x = length_x;
        this.length_xf = length_xf;
        this.u = u;
        this.a = a;
        this.t_max = t_max;
        this.t_int = t_int;
        this.deltaX = length_x/s_partition;
        this.deltaT = 0.8*(1/((u/deltaX) + (2*a/(deltaX*deltaX))));
        this.Ca = Ca;
        this.Cb = Cb;
        this.Cc = Cc;
        this.method = method;

        dt_draw = t_max / draw; //Intervalo para desenhar no gráfico
        t_draw  = dt_draw;
        chart = new GChart(method.getName(), draw + 1);

        Qs = new double[s_partition+1];
        Qs[s_partition] = 0; //Contorno direito explicitado.

        int i;
        for(i = 0; i < Math.round((length_xf/length_x)*s_partition); i++) Qs[i] = Ca;
        for(i = i; i < s_partition; i++) Qs[i] = Cb;
    }

    public void run() throws InvocationTargetException, IllegalAccessException { //Roda quantas iterações forem necessárias até que T alcançe t_max.
        double t = 0; //Nosso tempo inicial.
        int c = 0; //Qual curva está sendo desenhada.
        double[] Qss = new double[Qs.length];
        final double dtdx = deltaT/deltaX;
        final int length = Qs.length;

        for(int i = 0; i < length; i++) chart.addData(0, i * deltaX, Qs[i]);
        chart.setLabel(0, "T = 0.0");
        c++;

        Qss[0] = Ca; //Precisamos inicializar o contorno em nosso array auxiliar também.

        for(t = deltaT; t <= t_int; t += deltaT){ //Iterar em 0 < t <= t_int.
            for(int i = 1; i < length-1; i++) { //Iterar todos os volumes da malha (Exceto contornos!)
                Qss[i] = (double) method.invoke(this, i);
            }
            double[] tmp = Qss;
            Qss = Qs;
            Qs = tmp;

            if(t_draw <= t) {
                for(int i = 0; i < length; i++) chart.addData(c, i * deltaX, Qs[i]);
                chart.setLabel(c, "T = " + t);
                c++;
                t_draw += dt_draw;
            }
        }

        Qs[0] = Cc; //Após o tempo intermediário, c(0,t), t > t_int passa a ter valor Cc.
        Qss[0] = Cc; //Mesma coisa com nosso array auxiliar.

        for(t = t_int; t <= t_max; t += deltaT) { //Iterar em t_int < t <= t_max,
            for(int i = 1; i < length-1; i++) { //Iterar todos os volumes da malha (Exceto contornos!)
                Qss[i] = (double) method.invoke(this, i);
            }
            double[] tmp = Qss;
            Qss = Qs;
            Qs = tmp;

            if(t_draw <= t) {
                for(int i = 0; i < length; i++) chart.addData(c, i * deltaX, Qs[i]);
                chart.setLabel(c, "T = " + t);
                c++;
                t_draw += dt_draw;
            }
        }

        for(int i = 0; i < length; i++) chart.addData(c, i * deltaX, Qs[i]);
        chart.setLabel(c, "T = " + t);

        chart.setVisible(true);
        chart.setDefaultCloseOperation(3); //Exit on Close.
    }

}
