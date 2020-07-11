/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.ui;

import java.awt.Color;
import lombok.Getter;
import lombok.Setter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author agung
 */
public class CaseBarChart {

    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private Long Confirm;
    @Getter
    @Setter
    private Long Deaths;
    @Getter
    @Setter
    private Long Recovered;

    public ChartPanel generateChart() {
        CategoryDataset dataset = createDataSet();
        JFreeChart barChart = ChartFactory.createBarChart(
                "Grafik Kasus",
                "Jenis Kasus",
                "Jumlah Kasus",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        barChart.getPlot().setBackgroundPaint(Color.WHITE);
        final CategoryPlot plot = barChart.getCategoryPlot();
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.toRadians(22.5)));

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setMaximumBarWidth(0.10);
        renderer.setGradientPaintTransformer(null);
        renderer.setBarPainter(new StandardBarPainter());

        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
                org.jfree.chart.ui.TextAnchor.TOP_CENTER);
        renderer.setDefaultPositiveItemLabelPosition(position);

        ChartPanel chartPanel = new ChartPanel(barChart);
        return chartPanel;
    }

    private CategoryDataset createDataSet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(getDeaths(), "Meninggal", "");
        dataset.addValue(getConfirm(), "Terkonfirmasi", "");
        dataset.addValue(getRecovered(), "Sembuh", "");
//        dataset.addValue(50, "Confirm", "");
//        dataset.addValue(200,"Deaths", "");
//        dataset.addValue(2000, "Recovered", "");
        return dataset;
    }

}
