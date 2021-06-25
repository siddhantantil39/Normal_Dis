package com.example.normaldist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {


    public EditText Score;
    public EditText Mean;
    public EditText Std;
    public EditText X;
    public EditText Probe;
    public Button Less;
    public Button Greater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Score = findViewById(R.id.Z);
        Mean = findViewById(R.id.mean);
        Std = findViewById(R.id.std);
        X = findViewById(R.id.X);
        Less = findViewById(R.id.lesser);
        Greater = findViewById(R.id.greater);
        Probe = findViewById(R.id.P);


        Less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double xd = Double.parseDouble(String.valueOf(X.getText()));
                double mu = Double.parseDouble(String.valueOf(Mean.getText()));
                double sigma = Double.parseDouble(String.valueOf(Std.getText()));


                double zval = (xd - mu) / sigma;
                Score.setText(String.valueOf(zval));

                /*double interim1=(-0.5)*Math.pow(zval,2);
                double interim2=Math.exp(interim1)/Math.sqrt((2*Math.PI));
                double pdf=interim2/sigma;*/
                double a = -5 * sigma;
                double b = xd;

                double pdf2 = integrate(a,b);

                Probe.setText(String.valueOf(pdf2));

                GraphView graph = (GraphView) findViewById(R.id.graph);
                graph.removeAllSeries();
                LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>();
                BarGraphSeries barGraphSeries = new BarGraphSeries((DataPointInterface[])new DataPoint[0]);
                double x = -3.0;
                for (int i = 0; i < 600; ++i) {
                    x = 0.01 + x;
                    double y = Math.exp((double)(-x * x / 2.0)) / Math.sqrt((double)6.283185307179586);
                    lineGraphSeries.appendData(new DataPoint(x,y), true,2000);
                    if (!(x > -3) || !(x < xd)) continue;
                    barGraphSeries.appendData(new DataPoint(x, y), true, 2000);
                }
                graph.addSeries(lineGraphSeries);
                graph.addSeries(barGraphSeries);

            }
        });
        Greater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double xd = Double.parseDouble(String.valueOf(X.getText()));
                double mu = Double.parseDouble(String.valueOf(Mean.getText()));
                double sigma = Double.parseDouble(String.valueOf(Std.getText()));


                double zval = (xd - mu) / sigma;
                //Score.setText(String.valueOf(zval));

                /*double interim1=(-0.5)*Math.pow(zval,2);
                double interim2=Math.exp(interim1)/Math.sqrt((2*Math.PI));
                double pdf=interim2/sigma;*/
                double a = -5 * sigma;
                double b = xd;

                double pdf2 = 1-integrate(a,b);

                Probe.setText(String.valueOf(pdf2));

                GraphView graph = (GraphView) findViewById(R.id.graph);
                graph.removeAllSeries();
                LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<DataPoint>();
                BarGraphSeries barGraphSeries = new BarGraphSeries((DataPointInterface[])new DataPoint[0]);
                double x = -3.0;
                for (int i = 0; i <600; ++i) {
                    x = 0.01 + x;
                    double y = Math.exp((double)(-x * x / 2.0)) / Math.sqrt((double)6.283185307179586);
                    lineGraphSeries.appendData(new DataPoint(x,y), true,2000);
                    if (!(x > xd) || !(x < 3)) continue;
                    barGraphSeries.appendData(new DataPoint(x, y), true, 2000);
                }
                graph.addSeries(lineGraphSeries);
                graph.addSeries(barGraphSeries);

            }
        });

    }

    public double f(double x) {

        return Math.exp(-(Math.pow(x, 2)) / 2) / Math.sqrt(2 * Math.PI);
    }

    /**********************************************************************
     * Integrate f from a to b using Simpson's rule.
     * Increase N for more precision.
     **********************************************************************/
    public double integrate(double a, double b) {
        int N = 10000;                    // precision parameter
        double h = (b - a) / (N - 1);     // step size

        // 1/3 terms
        double sum = 1.0 / 3.0 * (f(a) + f(b));

        // 4/3 terms
        for (int i = 1; i < N - 1; i += 2) {
            double x = a + h * i;
            sum += 4.0 / 3.0 * f(x);
        }

        // 2/3 terms
        for (int i = 2; i < N - 1; i += 2) {
            double x = a + h * i;
            sum += 2.0 / 3.0 * f(x);
        }

        return sum * h;
    }


    }







