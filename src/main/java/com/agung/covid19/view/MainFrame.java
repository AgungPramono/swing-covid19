/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agung.covid19.view;

import com.agung.covid19.controller.MainController;
import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author agung
 */
@Slf4j
public class MainFrame extends javax.swing.JFrame {

    @Autowired
    private MainController baseController;

    @Autowired
    private CaseBarChart caseBarChart;

    @Autowired
    private PieChart pieChart;

    private Map<String, String> countryMap = new HashMap<>();

    private static final String COUNTRY_CODE = "ID";

    private Worker worker;

    public MainFrame() {
        initComponents();
        setResizable(true);
        setTitle(".:: COVID19-Dashboard ::.");
        initTextComponent("loading...");
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
    }

    public void cmbAction() {
        cmbCountry.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });
    }

    public void initCountryCombo() throws IOException {
        countryMap = baseController.getCountries();
        for (String countryName : countryMap.keySet()) {
            cmbCountry.addItem(countryName);
        }
//        cmbCountry.setSelectedItem("Indonesia");
    }

    public void initData() {
        try {
            initCountryCombo();
            baseController.loadCaseByCountry(COUNTRY_CODE);
        } catch (IOException ex) {
            log.debug(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal Download Data, periksa koneksi kembali");
        }
        initBarChart();
        initPieChart();
    }

    private void initTextComponent(String label) {
        lblConfirmed.setText(label);
        lblNewConfirmed.setText(label);
        lblDeath.setText(label);
        lblNewDeath.setText(label);
        lblRecovered.setText(label);
        lblNewRecovered.setText(label);
        lblGlobalConfirmed.setText(label);
        lblGlobalDeath.setText(label);
        lblGlobalRecovered.setText(label);
//        lblLastUpdate.setText(label);
    }

    private void initBarChart() {
        if (baseController.getCountry() != null) {
            caseBarChart.setConfirm(baseController.getCountry().getTotalConfirmed());
            caseBarChart.setDeaths(baseController.getCountry().getTotalDeaths());
            caseBarChart.setRecovered(baseController.getCountry().getTotalRecovered());
            pnlBarChart.removeAll();
            pnlBarChart.revalidate();
            pnlBarChart.add(caseBarChart.generateChart());
            pnlBarChart.updateUI();
            pnlBarChart.repaint();
        }
    }

    private void initPieChart() {
        if (baseController.getCountry() != null) {
            pieChart.setConfirm(baseController.getCountry().getTotalConfirmed());
            pieChart.setDeaths(baseController.getCountry().getTotalDeaths());
            pieChart.setRecovered(baseController.getCountry().getTotalRecovered());
            pnlPieChart.removeAll();
            pnlPieChart.revalidate();
            pnlPieChart.add(pieChart.generChartPanel());
            pnlPieChart.updateUI();
            pnlPieChart.repaint();
        }
    }

    public JLabel getLblConfirmed() {
        return lblConfirmed;
    }

    public JLabel getLblNewConfirmed() {
        return lblNewConfirmed;
    }

    public JLabel getLblNewRecovered() {
        return lblNewRecovered;
    }

    public CaseBarChart getBarChart() {
        return caseBarChart;
    }

    public JLabel getLblDeath() {
        return lblDeath;
    }

    public JLabel getLblNewDeath() {
        return lblNewDeath;
    }

    public JLabel getLblRecovered() {
        return lblRecovered;
    }

    public JLabel getLblGlobalConfirmed() {
        return lblGlobalConfirmed;
    }

    public JLabel getLblGlobalDeath() {
        return lblGlobalDeath;
    }

    public JLabel getLblGlobalRecovered() {
        return lblGlobalRecovered;
    }

    public JLabel getLblLastUpdate() {
        return lblLastUpdate;
    }

    public void setTypeCursor(Integer typeCursor) {
        this.setCursor(Cursor.getPredefinedCursor(typeCursor));
    }

    private void removeAllChart() {
        pnlBarChart.removeAll();
        pnlBarChart.repaint();
        pnlPieChart.removeAll();
        pnlPieChart.repaint();
    }

    private class Worker extends SwingWorker<String, Void> {

        @Override
        protected String doInBackground() {
            try {
                Thread.sleep(1000);
                initTextComponent("Loading...");
                setTypeCursor(Cursor.WAIT_CURSOR);
                baseController.loadCaseByCountry(countryMap.get(cmbCountry.getSelectedItem().toString()));
                jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
                        cmbCountry.getSelectedItem().toString(),
                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(255, 255, 255))); // NOI18N

                initBarChart();
                initPieChart();
                return "done";
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                setTypeCursor(Cursor.DEFAULT_CURSOR);
                removeAllChart();
                JOptionPane.showMessageDialog(rootPane, "Gagal mengambil data", "Gagal", JOptionPane.ERROR_MESSAGE);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

        @Override
        protected void done() {
            try {
                if (get() != null) {
                    log.debug("data updated");
                    JOptionPane.showMessageDialog(rootPane, "Data Berhasil di update");
                    setTypeCursor(Cursor.DEFAULT_CURSOR);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void runWorker() {
        worker = new Worker();
        worker.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        rootContainer = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lblConfirmed = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblNewConfirmed = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblDeath = new javax.swing.JLabel();
        lblNewDeath = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblRecovered = new javax.swing.JLabel();
        lblNewRecovered = new javax.swing.JLabel();
        lblLastUpdate = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        lblGlobalConfirmed = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblGlobalDeath = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblGlobalRecovered = new javax.swing.JLabel();
        cmbCountry = new javax.swing.JComboBox<>();
        pnlBarChart = new javax.swing.JPanel();
        pnlPieChart = new javax.swing.JPanel();

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("KASUS GLOBAL");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        rootContainer.setBackground(new java.awt.Color(153, 153, 153));

        jPanel11.setBackground(new java.awt.Color(102, 102, 102));

        jPanel12.setBackground(new java.awt.Color(153, 153, 153));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Indonesia", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        lblConfirmed.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblConfirmed.setForeground(new java.awt.Color(255, 153, 0));
        lblConfirmed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConfirmed.setText("21,09031");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Terkonfirmasi");

        lblNewConfirmed.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblNewConfirmed.setForeground(new java.awt.Color(255, 153, 0));
        lblNewConfirmed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNewConfirmed.setText("jLabel1");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblConfirmed, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNewConfirmed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(lblConfirmed, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblNewConfirmed)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Meninggal");

        lblDeath.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblDeath.setForeground(new java.awt.Color(255, 0, 51));
        lblDeath.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDeath.setText("21,09031");

        lblNewDeath.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblNewDeath.setForeground(new java.awt.Color(255, 0, 51));
        lblNewDeath.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNewDeath.setText("jLabel1");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDeath, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblNewDeath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(lblDeath, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(lblNewDeath)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Sembuh");

        lblRecovered.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblRecovered.setForeground(new java.awt.Color(0, 204, 102));
        lblRecovered.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRecovered.setText("21,09031");

        lblNewRecovered.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblNewRecovered.setForeground(new java.awt.Color(0, 204, 102));
        lblNewRecovered.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNewRecovered.setText("jLabel1");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblRecovered, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblNewRecovered, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(lblRecovered, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblNewRecovered)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        lblLastUpdate.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblLastUpdate.setForeground(new java.awt.Color(255, 255, 255));
        lblLastUpdate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblLastUpdate.setText("LastUpdate");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblLastUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLastUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel13, jPanel7, jPanel9});

        jPanel14.setBackground(new java.awt.Color(153, 153, 153));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Global", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(255, 255, 255))); // NOI18N

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        lblGlobalConfirmed.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblGlobalConfirmed.setForeground(new java.awt.Color(255, 153, 0));
        lblGlobalConfirmed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGlobalConfirmed.setText("21,09031");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Terkonfirmasi");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblGlobalConfirmed, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(lblGlobalConfirmed, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Meninggal");

        lblGlobalDeath.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblGlobalDeath.setForeground(new java.awt.Color(255, 0, 51));
        lblGlobalDeath.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGlobalDeath.setText("21,09031");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblGlobalDeath, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(lblGlobalDeath, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Sembuh");

        lblGlobalRecovered.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblGlobalRecovered.setForeground(new java.awt.Color(0, 204, 102));
        lblGlobalRecovered.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGlobalRecovered.setText("21,09031");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblGlobalRecovered, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(lblGlobalRecovered, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel10, jPanel15, jPanel8});

        cmbCountry.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        cmbCountry.setSelectedItem("Indonesia");
        cmbCountry.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCountryItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(cmbCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cmbCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlBarChart.setLayout(new java.awt.BorderLayout());

        pnlPieChart.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout rootContainerLayout = new javax.swing.GroupLayout(rootContainer);
        rootContainer.setLayout(rootContainerLayout);
        rootContainerLayout.setHorizontalGroup(
            rootContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(rootContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBarChart, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        rootContainerLayout.setVerticalGroup(
            rootContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootContainerLayout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(rootContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlBarChart, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                    .addGroup(rootContainerLayout.createSequentialGroup()
                        .addComponent(pnlPieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rootContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rootContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        initBarChart();
    }//GEN-LAST:event_jPanel12MouseClicked

    private void cmbCountryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCountryItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            runWorker();
        }
    }//GEN-LAST:event_cmbCountryItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbCountry;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblConfirmed;
    private javax.swing.JLabel lblDeath;
    private javax.swing.JLabel lblGlobalConfirmed;
    private javax.swing.JLabel lblGlobalDeath;
    private javax.swing.JLabel lblGlobalRecovered;
    private javax.swing.JLabel lblLastUpdate;
    private javax.swing.JLabel lblNewConfirmed;
    private javax.swing.JLabel lblNewDeath;
    private javax.swing.JLabel lblNewRecovered;
    private javax.swing.JLabel lblRecovered;
    private javax.swing.JPanel pnlBarChart;
    private javax.swing.JPanel pnlPieChart;
    private javax.swing.JPanel rootContainer;
    // End of variables declaration//GEN-END:variables
}
