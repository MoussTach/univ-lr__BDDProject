package fr.bdd.application.viewmodel.mainwindow.search.graph;

import fr.bdd.application.Main;
import fr.bdd.application.viewmodel.ViewModel_SceneCycle;
import fr.bdd.application.viewmodel.taskmanager.TaskArray;
import fr.bdd.application.viewmodel.taskmanager.Task_Custom;
import fr.bdd.application.viewmodel.taskmanager.ThreadArray;
import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.db_project.dao.DAO_Category;
import fr.bdd.job.db_project.dao.DAO_Condition;
import fr.bdd.job.db_project.dao.DAO_Document;
import fr.bdd.job.db_project.jobclass.Category;
import fr.bdd.job.db_project.jobclass.Condition;
import fr.bdd.job.db_project.jobclass.Document;
import fr.bdd.language.LanguageBundle;
import fr.bdd.log.generate.CustomLogger;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static fr.bdd.application.Main.TASKMANAGER;

public class MainWindow_SearchGraphViewModel extends ViewModel_SceneCycle {

    private static final CustomLogger LOGGER = CustomLogger.create(MainWindow_SearchGraphViewModel.class.getName());
    private final ObjectProperty<ResourceBundle> resBundle_ = LanguageBundle.getInstance().bindResourceBundle("properties.language.mainwindow.search.lg_search");

    //Text
    private final StringProperty searchGraph_DocByCondition_title_label_ = new SimpleStringProperty(this.resBundle_.get().getString("tPaneSearchGraph_DocByCondition_title"));
    private final StringProperty pieChart_DocByCondition_label_ = new SimpleStringProperty(this.resBundle_.get().getString("pieChart_DocByCondition"));

    private final StringProperty searchGraph_LocationByDoc_title_label_ = new SimpleStringProperty(this.resBundle_.get().getString("tPaneSearchGraph_LocationByDoc_title"));
    private final StringProperty barChart_LocationByDoc_label_ = new SimpleStringProperty(this.resBundle_.get().getString("barChart_LocationByDoc"));

    //Value
    private final ListProperty<PieChart.Data> pieChart_DocByCondition_ = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<XYChart.Series> barChart_LocationByDoc_ = new SimpleObjectProperty<>();

    private ChangeListener<ResourceBundle> listener_ChangedValue_bundleLanguage_ = null;


    public MainWindow_SearchGraphViewModel() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][constructor] Creation of the MainWindow_SearchGraphViewModel() object.");
        }

        //RessourceBundle Listener
        if (this.listener_ChangedValue_bundleLanguage_ == null) {
            this.listener_ChangedValue_bundleLanguage_ = this::listener_bundleLanguage;
            this.resBundle_.addListener(this.listener_ChangedValue_bundleLanguage_);
        }

        load_ConditionCircle();
    }


    /**
     * Load all different conditions from the database.
     *
     * @author Gaetan Brenckle

     */
    public void load_ConditionCircle() {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][method] Usage of the MainWindow_SearchGraphViewModel.load_Condition()");
        }

        final ObservableList<PieChart.Data> listPieChat = FXCollections.observableArrayList();
        Task<Void> task_loadPieChart_DocByCondition = new Task_Custom<Void>(new Image(getClass().getResourceAsStream("/img/add_64.png")), "Load a list of condition",true) {
            @Override
            protected Void call_Task() throws Exception {

                updateMessage("Try to load a list of condition");
                updateProgress(0.0, 1.0);

                try (Connection conn = Main.DB_CONNECTION.getDataSource().getConnection()) {

                    try {
                        conn.setAutoCommit(false);
                        updateProgress(0.4, 1.0);

                        final String selectAll_sql = String.format("%s %s %s %s",
                                "SELECT condition_id, COUNT(condition_id) AS countPie",
                                "FROM document",
                                "GROUP BY condition_id",
                                "ORDER BY condition_id");

                        Map<String, Long> resRq = new HashMap<>();
                        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(conn.prepareStatement(selectAll_sql));

                        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
                            while (resultSelectAll.next()) {
                                String condition = resultSelectAll.getString("condition_id");
                                Long value = resultSelectAll.getLong("countPie");

                                if (resRq.containsKey(condition))
                                    value += resRq.get(condition);
                                resRq.put(condition, value);
                            }
                        }

                        for (Map.Entry<String, Long> entry : resRq.entrySet()) {
                            String key = entry.getKey();
                            Long value = entry.getValue();

                            listPieChat.add(new PieChart.Data(key, value));
                        }

                        conn.commit();

                        if (LOGGER.isInfoEnabled()) {
                            LOGGER.info("List of condition loaded");
                        }

                        updateMessage("Successfully loaded a list of condition");
                        updateProgress(1, 1.0);

                    } catch (Exception e) {
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error(String.format("Error when synchronize", e.getMessage()), e);
                        }

                        updateMessage("Failed to load a list of condition");

                        try {
                            conn.rollback();

                        } catch (SQLException e1) {
                            if (LOGGER.isErrorEnabled()) {
                                LOGGER.error(String.format("Error when try to rollback : %s", e1.getMessage()), e1);
                            }
                        }

                        throw e;
                    }
                }

                return null;
            }
        };

        task_loadPieChart_DocByCondition.setOnSucceeded(event -> {
            pieChart_DocByCondition_.setAll(listPieChat);
        });

        //load
        TASKMANAGER.addArray(new TaskArray(ThreadArray.ExecutionType.PARALLEL)
                .addTask(new Pair(task_loadPieChart_DocByCondition, new TaskArray(ThreadArray.ExecutionType.END)))
        );
    }

    /**
     * Load all different conditions from the database.
     *
     * @author Gaetan Brenckle

     */
    public void load_LocationByDoc_Bar() {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][method] Usage of the MainWindow_SearchGraphViewModel.load_LocationByDoc_Bar()");
        }

        final ObjectProperty<XYChart.Series> barChat = new SimpleObjectProperty<>(new XYChart.Series());
        Task<Void> task_loadBarChart_LocationByDoc = new Task_Custom<Void>(new Image(getClass().getResourceAsStream("/img/add_64.png")), "Load a list of doc",true) {
            @Override
            protected Void call_Task() throws Exception {

                updateMessage("Try to load a list of doc");
                updateProgress(0.0, 1.0);

                try (Connection conn = Main.DB_CONNECTION.getDataSource().getConnection()) {

                    try {
                        conn.setAutoCommit(false);
                        updateProgress(0.4, 1.0);

                        final String selectAll_sql = String.format("%s %s %s %s",
                                "SELECT location, COUNT(document_id) AS countPie",
                                "FROM document",
                                "GROUP BY(location)",
                                "ORDER BY location");

                        Map<String, Long> resRq = new HashMap<>();
                        final PreparedStatementAware prepSelectAll = new PreparedStatementAware(conn.prepareStatement(selectAll_sql));

                        try(final ResultSet resultSelectAll = prepSelectAll.executeQuery()) {
                            while (resultSelectAll.next()) {
                                String condition = resultSelectAll.getString("location");
                                Long value = resultSelectAll.getLong("countPie");

                                if (resRq.containsKey(condition))
                                    value += resRq.get(condition);
                                resRq.put(condition, value);
                            }
                        }

                        for (Map.Entry<String, Long> entry : resRq.entrySet()) {
                            String key = entry.getKey();
                            Long value = entry.getValue();

                            barChat.get().getData().add(new XYChart.Data(key, value));
                        }

                        conn.commit();

                        if (LOGGER.isInfoEnabled()) {
                            LOGGER.info("List of doc loaded");
                        }

                        updateMessage("Successfully loaded a list of dic");
                        updateProgress(1, 1.0);

                    } catch (Exception e) {
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error(String.format("Error when synchronize", e.getMessage()), e);
                        }

                        updateMessage("Failed to load a list of doc");

                        try {
                            conn.rollback();

                        } catch (SQLException e1) {
                            if (LOGGER.isErrorEnabled()) {
                                LOGGER.error(String.format("Error when try to rollback : %s", e1.getMessage()), e1);
                            }
                        }

                        throw e;
                    }
                }

                return null;
            }
        };

        task_loadBarChart_LocationByDoc.setOnSucceeded(event -> {
            barChart_LocationByDoc_.set(barChat.get());
        });

        //load
        TASKMANAGER.addArray(new TaskArray(ThreadArray.ExecutionType.PARALLEL)
                .addTask(new Pair(task_loadBarChart_LocationByDoc, new TaskArray(ThreadArray.ExecutionType.END)))
        );
    }



    /**
     * Property of the variable searchGraph_DocByCondition_title_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchGraph_DocByCondition_title_label_.
     */
    public StringProperty searchGraph_DocByCondition_title_label_Property() {
        return searchGraph_DocByCondition_title_label_;
    }


    /**
     * Property of the variable pieChart_DocByCondition_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable pieChart_DocByCondition_label_.
     */
    public StringProperty pieChart_DocByCondition_label_Property() {
        return pieChart_DocByCondition_label_;
    }

    /**
     * Property of the variable pieChart_DocByCondition_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable pieChart_DocByCondition_label_.
     */
    public StringProperty searchGraph_LocationByDoc_title_label_Property() {
        return searchGraph_LocationByDoc_title_label_;
    }

    /**
     * Property of the variable pieChart_DocByCondition_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable pieChart_DocByCondition_label_.
     */
    public StringProperty barChart_LocationByDoc_label_Property() {
        return barChart_LocationByDoc_label_;
    }

    /**
     * Property of the variable pieChart_DocByCondition_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ListProperty} - return the property of the variable pieChart_DocByCondition_.
     */
    public ListProperty<PieChart.Data> pieChart_DocByCondition_Property() {
        return pieChart_DocByCondition_;
    }

    /**
     * Property of the variable barChart_LocationByDoc_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable barChart_LocationByDoc_.
     */
    public ObjectProperty<XYChart.Series> barChart_LocationByDoc_Property() {
        return barChart_LocationByDoc_;
    }

    /**
     * Listener for the ressource bundle.
     *
     * @param observable - {@link ObservableValue} - the value observed
     * @param oldValue - {@link ResourceBundle} - the old value that are remplaced
     * @param newValue - {@link ResourceBundle} - the new value
     */
    private void listener_bundleLanguage(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
       this.searchGraph_DocByCondition_title_label_.set(this.resBundle_.get().getString("tPaneSearchGraph_DocByCondition_title"));
       this.pieChart_DocByCondition_label_.set(this.resBundle_.get().getString("pieChart_DocByCondition"));
    }


    @Override
    public void onViewAdded_Cycle() {
    }

    @Override
    public void onViewRemoved_Cycle() {
        if (this.listener_ChangedValue_bundleLanguage_ != null) {
            this.resBundle_.removeListener(this.listener_ChangedValue_bundleLanguage_);
            this.listener_ChangedValue_bundleLanguage_ = null;
        }
        LanguageBundle.getInstance().unbindRessourceBundle(this.resBundle_);
    }
}
