package fr.bdd.application.viewmodel.sidewindow.options.category;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import de.saxsys.mvvmfx.utils.validation.CompositeValidator;
import fr.bdd.application.Main;
import fr.bdd.application.viewmodel.taskmanager.TaskArray;
import fr.bdd.application.viewmodel.taskmanager.Task_Custom;
import fr.bdd.application.viewmodel.taskmanager.ThreadArray;
import fr.bdd.custom.remastered.mvvm.utils.CompositeCommand_Remastered;
import fr.bdd.custom.sql.PreparedStatementAware;
import fr.bdd.job.db_project.dao.DAO_Category;
import fr.bdd.job.db_project.dao.DAO_Condition;
import fr.bdd.job.db_project.jobclass.Category;
import fr.bdd.job.db_project.jobclass.Condition;
import fr.bdd.job.jobclass.Request;
import fr.bdd.language.LanguageBundle;
import fr.bdd.log.generate.CustomLogger;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static fr.bdd.application.Main.TASKMANAGER;


/**
 * ViewModel of the view {@link fr.bdd.application.view.sidewindow.options.category.OptionsCategory_GeneralView}.
 * This ViewModel process the accessibility of the general options when changed.
 *
 * @author Gaetan Brenckle
 */
public class OptionsCategory_DashboardViewModel extends CategoryValidator {

    private static final CustomLogger LOGGER = CustomLogger.create(OptionsCategory_DashboardViewModel.class.getName());

    private final ObjectProperty<ResourceBundle> resBundle_ = LanguageBundle.getInstance().bindResourceBundle("properties.language.sidewindow.options.lg_options");

    //Text
    private final StringProperty dashboard_request_title_Label_ = new SimpleStringProperty(this.resBundle_.get().getString("tPaneDashboard_request"));
    private final StringProperty dashboard_request_label_ = new SimpleStringProperty(this.resBundle_.get().getString("txtDashboard_request"));
    private final StringProperty dashboard_request_combo_label_ = new SimpleStringProperty(this.resBundle_.get().getString("cmbDashboard_request"));

    private final StringProperty dashboard_result_title_label_ = new SimpleStringProperty(this.resBundle_.get().getString("tPaneDashboard_result"));

    //Value
    private final ListProperty<Request> listRequest_ = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<Request> selectRequest_ = new SimpleObjectProperty<>(null);
    private final StringProperty webHtml_ = new SimpleStringProperty();

    private final Command requestCommand_ = new DelegateCommand(() -> new Action() {
        @Override
        protected void action() {
            actvm_changeRequest();
        }
    }, true);
    private final CompositeValidator requestValidator_ = new CompositeValidator();

    private final CompositeCommand_Remastered request_Command_ = new CompositeCommand_Remastered(requestCommand_);

    private ChangeListener<ResourceBundle> listener_ChangedValue_bundleLanguage_ = null;

    @InjectScope
    private CategoryScope categoryScope;

    /**
     * Default Constructor.
     * Create a RessourceBundle listener.
     * Regroup all command on a compositeCommand.
     *
     * @author Gaetan Brenckle
     */
    public OptionsCategory_DashboardViewModel() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][constructor] Creation of the OptionsCategory_DashboardViewModel() object.");
        }

        //RessourceBundle listener
        if (this.listener_ChangedValue_bundleLanguage_ == null) {
            this.listener_ChangedValue_bundleLanguage_ = this::listener_bundleLanguage;
            this.resBundle_.addListener(this.listener_ChangedValue_bundleLanguage_);
        }

        load_Request();
    }

    /**
     * Load all request of the database.
     *
     * @author Gaetan Brenckle
     */
    public void load_Request() {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][method] Usage of the OptionsCategory_DashboardViewModel.load_Request()");
        }

        listRequest_.addAll(Arrays.asList(
                new Request("Document spa")
                        .setRequest("SELECT * FROM vw_Document_spa"),

                new Request("Document eng")
                        .setRequest("SELECT * FROM vw_Document_eng"),

                new Request("document Excel")
                        .setRequest("SELECT * FROM vw_Document_excel"))
        );
    }

    /**
     * action that will change the current language of the application with the language currently selectionned.
     *
     * @author Gaetan Brenckle
     */
    private void actvm_changeRequest() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[private][method] usage of OptionsCategory_GeneralViewModel.actvm_changeRequest().");
        }

        if (selectRequest_.isNotNull().get()) {

            Task<Void> task_loadRq = new Task_Custom<Void>(new Image(getClass().getResourceAsStream("/img/add_64.png")), "Load a Request",true) {
                @Override
                protected Void call_Task() throws Exception {
                    updateMessage("Try to load request");
                    updateProgress(0.0, 1.0);

                    try (Connection conn = Main.DB_CONNECTION.getDataSource().getConnection()) {

                        try {
                            conn.setAutoCommit(false);
                            updateProgress(0.4, 1.0);

                            //---------------------
                            StringBuilder htmlBuilder = new StringBuilder("<html><body><table>");

                            final PreparedStatementAware prepRequest = new PreparedStatementAware(conn.prepareStatement(selectRequest_.get().getRequest()));
                            try(final ResultSet resultRequest = prepRequest.executeQuery()) {

                                htmlBuilder.append("<tr>");
                                int colCount = resultRequest.getMetaData().getColumnCount();
                                for (int i = 1; i < colCount; i++) {
                                    htmlBuilder.append(String.format("<th scope=\"col\"  class=\"absorbing-column\">%s</th>", resultRequest.getMetaData().getColumnName(i)));
                                }
                                htmlBuilder.append("</tr>");

                                while(resultRequest.next()) {
                                    htmlBuilder.append("<tr>");
                                    for (int i = 1; i < colCount; i++) {
                                        String res = resultRequest.getString(i);
                                        if (res == null)
                                            res = "";
                                        htmlBuilder.append(String.format("<td>%s</td>", res));
                                    }
                                    htmlBuilder.append("</tr>");
                                }
                            }
                            //-----------------------
                            htmlBuilder.append("</table></body></html>");

                            conn.commit();

                            if (LOGGER.isInfoEnabled()) {
                                LOGGER.info("request loaded");
                            }

                            updateMessage("Successfully loaded a request");
                            updateProgress(1, 1.0);
                            webHtml_.set(htmlBuilder.toString());

                        } catch (Exception e) {
                            if (LOGGER.isErrorEnabled()) {
                                LOGGER.error(String.format("Error when synchronize", e.getMessage()), e);
                            }

                            updateMessage("Failed to load a request");

                            try {
                                conn.rollback();

                            } catch (SQLException e1) {
                                if (LOGGER.isErrorEnabled()) {
                                    LOGGER.error(String.format("Error when try to rollback : %s", e1.getMessage()), e1);
                                }
                            } finally {
                                webHtml_.set("<html>Error on loading</html>");
                            }

                            throw e;
                        }
                    }

                    return null;
                }
            };

            //Load fields
            TASKMANAGER.addArray(new TaskArray(ThreadArray.ExecutionType.PARALLEL)
                    .addTask(new Pair(task_loadRq, new TaskArray(ThreadArray.ExecutionType.END)))
            );
        }
    }


    /**
     * Property of the variable dashboard_request_title_Label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable dashboard_request_title_Label_.
     */
    public StringProperty dashboard_request_title_Label_Property() {
        return dashboard_request_title_Label_;
    }

    /**
     * Property of the variable dashboard_request_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable dashboard_request_label_.
     */
    public StringProperty dashboard_request_label_Property() {
        return dashboard_request_label_;
    }

    /**
     * Property of the variable dashboard_request_combo_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable dashboard_request_combo_label_.
     */
    public StringProperty dashboard_request_combo_label_Property() {
        return dashboard_request_combo_label_;
    }

    /**
     * Property of the variable dashboard_result_title_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable dashboard_result_title_label_.
     */
    public StringProperty dashboard_result_title_label_Property() {
        return dashboard_result_title_label_;
    }

    /**
     * Property of the variable listRequest_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ListProperty} - return the property of the variable listRequest_.
     */
    public ListProperty<Request> listRequest_Property() {
        return listRequest_;
    }

    /**
     * Property of the variable selectRequest_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable selectRequest_.
     */
    public ObjectProperty<Request> selectRequest_Property() {
        return selectRequest_;
    }

    /**
     * Property of the variable webHtml_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable webHtml_.
     */
    public StringProperty webHtml_Property() {
        return webHtml_;
    }


    /**
     * Property of the variable requestValidator_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link CompositeValidator} - return the property of the variable requestValidator_.
     */
    @Override
    public CompositeValidator getCompositeValidator() {
        return requestValidator_;
    }

    /**
     * Getter overrided for the compositeCommand of the class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link CompositeCommand_Remastered} - return the CompositeCommand_Remastered of the class.
     */
    @Override
    public CompositeCommand_Remastered getComposite_Command() {
        return this.request_Command_;
    }


    /**
     * Listener for the ressource bundle.
     *
     * @param observable - {@link ObservableValue} - the value observed
     * @param oldValue - {@link ResourceBundle} - the old value that are remplaced
     * @param newValue - {@link ResourceBundle} - the new value
     */
    private void listener_bundleLanguage(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        this.dashboard_request_title_Label_.set(this.resBundle_.get().getString("tPaneDashboard_request"));
        this.dashboard_request_label_.set(this.resBundle_.get().getString("txtDashboard_request"));
        this.dashboard_request_combo_label_.set(this.resBundle_.get().getString("cmbDashboard_request"));

        this.dashboard_result_title_label_.set(this.resBundle_.get().getString("tPaneDashboard_result"));
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