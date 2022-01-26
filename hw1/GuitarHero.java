import edu.princeton.cs.algs4.StdAudio;
import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {
    public static final String kerbord = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static void main(String[] args) {
        double sample = 0;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = kerbord.indexOf(key);
                if (index != -1) {
                    double frequency = 440 * Math.pow(2.0, (index - 24) / 12);
                    GuitarString Gstring = new GuitarString(frequency);
                    Gstring.pluck();
                    sample += Gstring.sample();
                    StdAudio.play(sample);
                    Gstring.tic();
                }
            }
        }
    }
}
