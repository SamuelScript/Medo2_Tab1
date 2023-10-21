package Main;

import javax.swing.*;
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
    private double[] Qs; //Valores no interior do domínio

    Trab1(double length_x, double length_xf, double u, double a, double t_max, double t_int, double Ca, double Cb, double Cc, int s_partition, int t_partition) { //Inicializa todos os valores para o tempo 0.
        this.length_x = length_x;
        this.length_xf = length_xf;
        this.u = u;
        this.a = a;
        this.t_max = t_max;
        this.t_int = t_int;
        this.deltaX = length_x/s_partition;
        this.deltaT = 0.95*(1/((u/deltaX) + (2*a/(deltaX*deltaX))));
        //this.deltaT = t_max/t_partition;
        this.Ca = Ca;
        this.Cb = Cb;
        this.Cc = Cc;

        Qs = new double[s_partition];

        int i;
        for(i = 0; i < Math.round((length_xf/length_x)*s_partition); i++) Qs[i] = Ca;
        for(i = i; i < s_partition; i++) Qs[i] = Cb;
    }

    public void run() { //Roda quantas iterações forem necessárias até que T alcançe t_max.
        double t; //Nosso tempo inicial.
        double[] Qss = new double[Qs.length];
        final double dtdx = deltaT/deltaX;
        final int length = Qs.length;

        System.out.println(Arrays.toString(Qs));

        Qss[0] = Ca; //Precisamos inicializar o contorno em nosso array auxiliar também.

        for(t = deltaT; t <= t_int; t += deltaT){ //Iterar em 0 < t <= t_int.
            for(int i = 1; i < length-1; i++) { //Iterar todos os volumes da malha (Exceto contornos!)
                Qss[i] = Qs[i] - dtdx*(u*(Qs[i] - Qs[i-1]) - a*((Qs[i+1] - 2*Qs[i] + Qs[i-1])/deltaX));
            }
            Qss[length-1] = Qs[length-1] - dtdx*(u*(Qs[length-1] - Qs[length-2]) - a*((-2*Qs[length-1] + Qs[length-2])/deltaX));
            double[] tmp = Qss;
            Qss = Qs;
            Qs = tmp;
            System.out.println(Arrays.toString(Qs));
        }

        Qs[0] = Cc; //Após o tempo intermediário, c(0,t), t > t_int passa a ter valor Cc.
        Qss[0] = Cc; //Mesma coisa com nosso array auxiliar.

        for(t = t_int; t <= t_max; t += deltaT) { //Iterar em t_int < t <= t_max,
            for(int i = 1; i < length-1; i++) { //Iterar todos os volumes da malha (Exceto contornos!)
                Qss[i] = Qs[i] - dtdx*(u*(Qs[i] - Qs[i-1]) - a*((Qs[i+1] - 2*Qs[i] + Qs[i-1])/deltaX));
            }
            Qss[length-1] = Qs[length-1] - dtdx*(u*(Qs[length-1] - Qs[length-2]) - a*((-2*Qs[length-1] + Qs[length-2])/deltaX));
            double[] tmp = Qss;
            Qss = Qs;
            Qs = tmp;
            System.out.println(Arrays.toString(Qs));
        }
    }
}
