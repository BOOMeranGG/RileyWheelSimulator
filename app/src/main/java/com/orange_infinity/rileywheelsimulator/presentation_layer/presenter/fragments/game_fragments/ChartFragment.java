package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.orange_infinity.rileywheelsimulator.R;
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.ChartActivity;
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.crash.ChartGameController;
import com.orange_infinity.rileywheelsimulator.util.AndroidLoggerKt;

public class ChartFragment extends Fragment {

    private final Handler handler = new Handler();
    private TextView tvMultiplier;
    private Runnable timerChart;
    private Runnable timerMultiplier;
    private LineGraphSeries<DataPoint> series;
    private ChartActivity gameListener;
    private double winnerMultiplier = 1.0;
    private double graphLastXValue = 0d;
    private boolean isStopped = false;
    private double currentMultiplier = 1d;

    public static ChartFragment newInstance() {
        return new ChartFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gameListener = (ChartActivity) getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);

        GraphView graph = rootView.findViewById(R.id.graph2);
        series = new LineGraphSeries<>();

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(12);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(150);

        graph.addSeries(series);

        tvMultiplier = rootView.findViewById(R.id.tvMultiplier);
        ChartGameController chartGameController = new ChartGameController();
        winnerMultiplier = chartGameController.getMultiplier();
        AndroidLoggerKt.logInf(AndroidLoggerKt.MAIN_LOGGER_TAG, "winnerMultiplier = " + winnerMultiplier);
        return rootView;
    }

    public void startChart() {
        timerChart = new Runnable() {
            @Override
            public void run() {
                if (isStopped) {
                    tvMultiplier.setTextColor(getResources().getColor(R.color.colorGreen));
                    return;
                }
                if (currentMultiplier > winnerMultiplier) {
                    tvMultiplier.setTextColor(getResources().getColor(R.color.colorRed));
                    gameListener.loseGame();
                    return;
                }
                graphLastXValue += 0.03d;
                series.appendData(new DataPoint(graphLastXValue, graphLastXValue * graphLastXValue), false, 2500);
                handler.postDelayed(this, 20);
            }
        };
        handler.postDelayed(timerChart, 1);

        timerMultiplier = new Runnable() {
            @Override
            public void run() {
                if (isStopped || currentMultiplier > winnerMultiplier) {
                    return;
                }
                tvMultiplier.setText((Math.round(currentMultiplier * 100.0) / 100.0) + "X");
                currentMultiplier += 0.01;
                handler.postDelayed(this, 70);
            }
        };
        handler.postDelayed(timerMultiplier, 1);
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(timerChart);
        handler.removeCallbacks(timerMultiplier);
        super.onPause();
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    public double getCurrentMultiplier() {
        return currentMultiplier;
    }

    public interface OnLoseGame {
        void loseGame();
    }
}
