/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.view;

import com.agung.covid19.util.TextUtil;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.Timer;
import lombok.Getter;
import lombok.Setter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.stereotype.Component;

/**
 *
 * @author agung
 */

@Component
public class PieChart {
    
    @Getter
    @Setter
    private Long Confirm;
    @Getter
    @Setter
    private Long Deaths;
    @Getter
    @Setter
    private Long Recovered;
    
    @Getter @Setter
    private Long total;

    public ChartPanel generChartPanel() {

        JFreeChart chart = ChartFactory.createPieChart(
                "Prosentase Kasus",
                createDataset(),
                true, // include legend
                true,
                false
        );

        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Terkonfirmasi", Color.ORANGE);
        plot.setSectionPaint("Meninggal", Color.RED);
        plot.setSectionPaint("Sembuh", Color.GREEN);
        plot.setSimpleLabels(true);

        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
            "{1}({2})", NumberFormat.getInstance(), NumberFormat.getPercentInstance());
        plot.setLabelGenerator(gen);
//        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
//        plot.setStartAngle(270);
//        plot.setDirection(org.jfree.chart.util.Rotation.ANTICLOCKWISE);
//        plot.setForegroundAlpha(0.60f);
//        plot.setInteriorGap(0.33);
        // add the chart to a panel...

        ChartPanel chartPanel = new ChartPanel(chart);
//        final Rotator rotator = new Rotator(plot);
//        rotator.start();
        return chartPanel;
    }

    private PieDataset createDataset() {
        
        Double totalAllCase = Double.valueOf((getDeaths()+getConfirm()+getRecovered()));
        
        DefaultPieDataset data = new DefaultPieDataset();
        data.setValue("Meninggal", TextUtil.calculatePercentage(getDeaths(), totalAllCase));
        data.setValue("Terkonfirmasi", TextUtil.calculatePercentage(getConfirm(),totalAllCase));
        data.setValue("Sembuh", TextUtil.calculatePercentage(getRecovered(),totalAllCase));
        return data;
    }

    class Rotator extends Timer implements ActionListener {

        /**
         * The plot.
         */
        private PiePlot3D plot;

        /**
         * The angle.
         */
        private int angle = 270;

        /**
         * Constructor.
         *
         * @param plot the plot.
         */
        Rotator(final PiePlot3D plot) {
            super(100, null);
            this.plot = plot;
            addActionListener(this);
        }

        /**
         * Modifies the starting angle.
         *
         * @param event the action event.
         */
        public void actionPerformed(final ActionEvent event) {
            this.plot.setStartAngle(this.angle);
            this.angle = this.angle + 1;
            if (this.angle == 360) {
                this.angle = 0;
            }
        }
    }
}
