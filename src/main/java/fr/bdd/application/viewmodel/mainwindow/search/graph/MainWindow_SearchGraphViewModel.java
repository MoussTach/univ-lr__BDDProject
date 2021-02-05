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
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static fr.bdd.application.Main.TASKMANAGER;

public class MainWindow_SearchGraphViewModel extends ViewModel_SceneCycle {

    private static final CustomLogger LOGGER = CustomLogger.create(MainWindow_SearchGraphViewModel.class.getName());
    private final ObjectProperty<ResourceBundle> resBundle_ = LanguageBundle.getInstance().bindResourceBundle("properties.language.mainwindow.search.lg_search");

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
    }


    /**
     * Load all different conditions from the database.
     *
     * @author Gaetan Brenckle
     */
    public Task<Void> load_Condition() {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][method] Usage of the MainWindow_SearchGraphViewModel.load_Condition()");
        }

        return new Task_Custom<Void>(new Image(getClass().getResourceAsStream("/img/add_64.png")), "Load a list of condition",true) {
            @Override
            protected Void call_Task() throws Exception {
                final List<Condition> listCondition = new ArrayList<>();

                final DAO_Condition dao_condition = new DAO_Condition();

                updateMessage("Try to load a list of condition");
                updateProgress(0.0, 1.0);

                try (Connection conn = Main.DB_CONNECTION.getDataSource().getConnection()) {

                    try {
                        dao_condition.setConnection(conn);
                        conn.setAutoCommit(false);

                        updateProgress(0.4, 1.0);

                        listCondition.addAll(dao_condition.selectAll());
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
                    } finally {
                    }
                }

                return null;
            }
        };
    }


    /**
     * Listener for the ressource bundle.
     *
     * @param observable - {@link ObservableValue} - the value observed
     * @param oldValue - {@link ResourceBundle} - the old value that are remplaced
     * @param newValue - {@link ResourceBundle} - the new value
     */
    private void listener_bundleLanguage(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
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
