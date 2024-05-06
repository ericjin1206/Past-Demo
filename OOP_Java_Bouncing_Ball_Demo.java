package bouncingballs;

import java.awt.Color;
import java.awt.event.KeyEvent;

import edu.princeton.cs.introcs.StdDraw;
import support.cse131.ArgsProcessor;
import support.cse131.Timing;

public class BouncingBalls {
	public static final double ENTITY_RADIUS = 0.008;


	public static boolean touchingBall(int index, double[]Xpositions, double[]Ypositions) {
		for (int i=0; i<Xpositions.length; i++) {
			if (i == index) {
				continue;
			}
			if ((Math.pow(Xpositions[i]-Xpositions[index],2) + Math.pow(Ypositions[i]-Ypositions[index],2)) <= Math.pow(ENTITY_RADIUS * 2, 2)){
				return true;

			}
		}
		return false;
	}


	public static void main(String[] args) {
		ArgsProcessor ap = new ArgsProcessor(args);
		int N = ap.nextInt("How many balls do you want to simulate?");
		int M = ap.nextInt("How  many frames do you want");
		double velocityRange = 0.01;
		double[] Xpositions = new double[N];
		double[] Ypositions = new double[N];
		double[] Xvelocity =  new double[N];
		double[] Yvelocity = new double[N];
		StdDraw.enableDoubleBuffering();
		StdDraw.setPenColor(Color.BLACK);

		for (int i=0; i<N; i++) {
			Xpositions[i] =  Math.random();
			Ypositions[i] = Math.random();
			Xvelocity[i] = (Math.random() * (velocityRange - (-velocityRange))) + (-velocityRange);
			Yvelocity[i] = (Math.random() * (velocityRange - (-velocityRange))) + (-velocityRange);
			StdDraw.filledCircle(Xpositions[i], Ypositions[i], ENTITY_RADIUS);
			
		}



		for (int k=0; k<M; k++) {
			StdDraw.clear();
			for (int j=0; j<N; j++) {

				Xpositions[j]= Xpositions[j]+Xvelocity[j];
				Ypositions[j]= Ypositions[j]+ Yvelocity[j];

				if (Xpositions[j] > (1.0 - velocityRange)) {
					Xvelocity[j] = (-Xvelocity[j]);
				}
				if (Xpositions [j] < velocityRange) {
					Xvelocity[j] = (-Xvelocity[j]);
				}
				if (Ypositions[j] > (1.0 - velocityRange)) {
					Yvelocity[j] = (-Yvelocity[j]);
				}
				if (Ypositions [j] < velocityRange) {
					Yvelocity[j] = (-Yvelocity[j]);
				}

				if (touchingBall(j,Xpositions,Ypositions) == true) {
					Xvelocity[j] = (-Xvelocity[j]);
					Yvelocity[j] = (-Yvelocity[j]);
				}
				StdDraw.filledCircle(Xpositions[j], Ypositions[j], ENTITY_RADIUS);
			}
			StdDraw.show();
			StdDraw.pause(50);



		}

	}
}