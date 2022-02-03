/**
 * Created by hug.
 */
import edu.princeton.cs.introcs.StdRandom;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;

public class Experiments {
    public static void experiment1() {
        BST<Integer> bst = new BST<Integer>();
        List<Integer> itemsNum = new ArrayList<>();
        List<Double> avgDepth = new ArrayList<>();
        List<Double> avgOptDepth = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < 5000; i++) {
            bst.add(StdRandom.uniform(5000));
            itemsNum.add(i);
            avgDepth.add(bst.averageDepth());
            avgOptDepth.add(ExperimentHelper.optimalAverageDepth(i));
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Number of items").yAxisTitle("Average depth").build();
        chart.addSeries("Average depth of BST", itemsNum, avgDepth);
        chart.addSeries("Average depth of Optimal BST", itemsNum, avgOptDepth);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        BST<Integer> bst = new BST<Integer>();
        List<Integer> itemsNum = new ArrayList<>();
        List<Double> avgDepth = new ArrayList<>();
        List<Double> avgOptDepth = new ArrayList<>();
        int max = 0;
        int N = 5000;
        int M = 5000;
        for (int i = 0; i < N; i++) {
            bst.add(StdRandom.uniform(N));
        }
        for (int i = 0; i < M; i++) {
            itemsNum.add(i);
            avgDepth.add(ExperimentHelper.deleteAndInsert(bst, M));
            avgOptDepth.add(ExperimentHelper.optimalAverageDepth(i));
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Number of items").yAxisTitle("Average depth").build();
        chart.addSeries("Average depth of BST", itemsNum, avgDepth);
        chart.addSeries("Average depth of Optimal BST", itemsNum, avgOptDepth);

        new SwingWrapper(chart).displayChart();

    }

    public static void experiment3() {
        BST<Integer> bst = new BST<Integer>();
        List<Integer> itemsNum = new ArrayList<>();
        List<Double> avgDepth = new ArrayList<>();
        List<Double> avgOptDepth = new ArrayList<>();
        int max = 0;
        int N = 5000;
        int M = 5000;
        for (int i = 0; i < N; i++) {
            bst.add(StdRandom.uniform(N));
        }
        for (int i = 0; i < M; i++) {
            itemsNum.add(i);
            avgDepth.add(ExperimentHelper.deleteAndInsertEx3(bst, M));
            avgOptDepth.add(ExperimentHelper.optimalAverageDepth(i));
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Number of items").yAxisTitle("Average depth").build();
        chart.addSeries("Average depth of BST", itemsNum, avgDepth);
        chart.addSeries("Average depth of Optimal BST", itemsNum, avgOptDepth);

        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
        experiment3();
    }
}
