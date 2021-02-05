package fr.bdd.application.viewmodel.mainwindow.search.document;

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

public class MainWindow_SearchDocumentViewModel extends ViewModel_SceneCycle {

    private static final CustomLogger LOGGER = CustomLogger.create(MainWindow_SearchDocumentViewModel.class.getName());
    private final ObjectProperty<ResourceBundle> resBundle_ = LanguageBundle.getInstance().bindResourceBundle("properties.language.mainwindow.search.lg_search");

    //Text
    private final StringProperty searchDocument_title_label_ = new SimpleStringProperty(this.resBundle_.get().getString("tPaneSearchDocument_title"));

    private final StringProperty searchDocument_ID_label_ = new SimpleStringProperty(this.resBundle_.get().getString("tfSearchDocument_ID"));
    private final StringProperty searchDocument_Author_label_ = new SimpleStringProperty(this.resBundle_.get().getString("tfSearchDocument_Author"));
    private final StringProperty searchDocument_Category_label_ = new SimpleStringProperty(this.resBundle_.get().getString("cbSearchDocument_Category"));
    private final StringProperty searchDocument_Condition_label_ = new SimpleStringProperty(this.resBundle_.get().getString("cbSearchDocument_Condition"));

    private final StringProperty searchDocument_between_label_ = new SimpleStringProperty(this.resBundle_.get().getString("lblSearchDocument_between"));
    private final StringProperty searchDocument_StartDate_label_ = new SimpleStringProperty(this.resBundle_.get().getString("dPickerSearchDocument_StartDate"));
    private final StringProperty searchDocument_and_label_ = new SimpleStringProperty(this.resBundle_.get().getString("lblSearchDocument_and"));
    private final StringProperty searchDocument_EndDate_label_ = new SimpleStringProperty(this.resBundle_.get().getString("dPickerSearchDocument_EndDate"));

    private final StringProperty searchDocument_ID_tabLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("tabSearchDocument_ID"));
    private final StringProperty searchDocument_Title_tabLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("tabSearchDocument_Title"));
    private final StringProperty searchDocument_Author_tabLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("tabSearchDocument_Author"));
    private final StringProperty searchDocument_StartDate_tabLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("tabSearchDocument_StartDate"));
    private final StringProperty searchDocument_EndDate_tabLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("tabSearchDocument_EndDate"));
    private final StringProperty searchDocument_Category_tabLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("tabSearchDocument_Category"));
    private final StringProperty searchDocument_Condition_tabLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("tabSearchDocument_Condition"));

    private final StringProperty searchDocument_Delete_label_ = new SimpleStringProperty(this.resBundle_.get().getString("btnSearchDocument_Delete"));
    private final StringProperty searchDocument_Research_label_ = new SimpleStringProperty(this.resBundle_.get().getString("btnSearchDocument_Research"));


    //Value
    private final StringProperty searchDocument_ID_value_ = new SimpleStringProperty("");
    private final BooleanProperty searchDocument_button_StartWith_ID_value_ = new SimpleBooleanProperty(false);
    private final StringProperty searchDocument_Author_value_ = new SimpleStringProperty("");
    private final BooleanProperty SearchDocument_button_StartWith_Author_value_ = new SimpleBooleanProperty(false);
    private final ListProperty<Category> listSearchDocument_Category_ = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<Category> searchDocument_Category_selected_ = new SimpleObjectProperty<>();
    private final ListProperty<Condition> listSearchDocument_Condition_ = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<Condition> searchDocument_Condition_selected_ = new SimpleObjectProperty<>();

    private final ObjectProperty<LocalDate> searchDocument_StartDate_value_ = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> searchDocument_EndDate_value_ = new SimpleObjectProperty<>(null);

    private final ListProperty<Document> list_document_ = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<Document> current_document_ = new SimpleObjectProperty<>();


    private ChangeListener<ResourceBundle> listener_ChangedValue_bundleLanguage_ = null;


    public MainWindow_SearchDocumentViewModel() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][constructor] Creation of the MainWindow_SearchDocumentViewModel() object.");
        }

        //RessourceBundle Listener
        if (this.listener_ChangedValue_bundleLanguage_ == null) {
            this.listener_ChangedValue_bundleLanguage_ = this::listener_bundleLanguage;
            this.resBundle_.addListener(this.listener_ChangedValue_bundleLanguage_);
        }

        //Load fields
        TASKMANAGER.addArray(new TaskArray(ThreadArray.ExecutionType.PARALLEL)
                .addTask(new Pair(load_Category(), new TaskArray(ThreadArray.ExecutionType.END)))
                .addTask(new Pair(load_Condition(), new TaskArray(ThreadArray.ExecutionType.END)))
        );
    }


    /**
     * Action when the button research is pressed.
     * It will search into the db all field required.
     *
     * @author Gaetan Brenckle
     */
    public void actvm_Reinitialize() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][method] Usage of the MainWindow_SearchDocumentViewModel.actvm_Reinitialize()");
        }

        searchDocument_ID_value_.set("");
        searchDocument_Author_value_.set("");
        searchDocument_Category_selected_.set(null);
        searchDocument_Condition_selected_.set(null);

        searchDocument_StartDate_value_.set(null);
        searchDocument_EndDate_value_.set(null);

        //Load fields
        TASKMANAGER.addArray(new TaskArray(ThreadArray.ExecutionType.PARALLEL)
                .addTask(new Pair(load_Category(), new TaskArray(ThreadArray.ExecutionType.END)))
                .addTask(new Pair(load_Condition(), new TaskArray(ThreadArray.ExecutionType.END)))
        );
    }

    /**
     * Action when the button delete is pressed.
     * It will delete the field selected.
     *
     * @author Gaetan Brenckle
     */
    public void actvm_Delete(Document document) {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][method] Usage of the MainWindow_SearchDocumentViewModel.actvm_Delete()");
        }

        Task<Void> task_searchDocument = new Task_Custom<Void>(new Image(getClass().getResourceAsStream("/img/delete_64.png")), "delete a document") {
            @Override
            protected Void call_Task() throws Exception {
                final List<Document> listDocument = new ArrayList<>();

                final DAO_Document dao_document = new DAO_Document();

                updateMessage("Try to delete a document");
                updateProgress(0.0, 1.0);

                try (Connection conn = Main.DB_CONNECTION.getDataSource().getConnection()) {

                    try {
                        dao_document.setConnection(conn);
                        conn.setAutoCommit(false);

                        updateProgress(0.4, 1.0);
                        dao_document.delete(document);
                        conn.commit();

                        if (LOGGER.isInfoEnabled()) {
                            LOGGER.info("Document deleted");
                        }

                        updateMessage("Successfully deleted a document");
                        updateProgress(1, 1.0);

                    } catch (Exception e) {
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error(String.format("Error when synchronize", e.getMessage()), e);
                        }

                        updateMessage("Failed to delete a document");

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

        //Search document
        TASKMANAGER.addArray(new TaskArray(ThreadArray.ExecutionType.PARALLEL)
                .addTask(new Pair(task_searchDocument, new TaskArray(ThreadArray.ExecutionType.END)))
        );
    }

    /**
     * Action when the button research is pressed.
     * It will search into the db all field required.
     *
     * @author Gaetan Brenckle
     */
    public void actvm_Research() {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][method] Usage of the MainWindow_SearchDocumentViewModel.actvm_Research()");
        }

        Task<Void> task_searchDocument = new Task_Custom<Void>(new Image(getClass().getResourceAsStream("/img/add_64.png")), "Load a list of document") {
            @Override
            protected Void call_Task() throws Exception {
                final List<Document> listDocument = new ArrayList<>();

                final DAO_Document dao_document = new DAO_Document();

                updateMessage("Try to load a list of document");
                updateProgress(0.0, 1.0);

                try (Connection conn = Main.DB_CONNECTION.getDataSource().getConnection()) {

                    try {
                        dao_document.setConnection(conn);
                        conn.setAutoCommit(false);

                        updateProgress(0.4, 1.0);

                        HashMap<String, Pair<?, PreparedStatementAware.listType>> listParams = new HashMap<>();

                        if (!searchDocument_ID_value_.get().isEmpty()) {
                            if (searchDocument_button_StartWith_ID_value_.get())
                                listParams.put("AND document_ID LIKE ?", new Pair<>(String.format("%s%%", searchDocument_ID_value_.get()), PreparedStatementAware.listType.STRING));
                            else
                                listParams.put("AND document_ID LIKE ?", new Pair<>(String.format("%%%s%%", searchDocument_ID_value_.get()), PreparedStatementAware.listType.STRING));
                        }

                        if (!searchDocument_Author_value_.get().isEmpty()) {
                            if (searchDocument_button_StartWith_ID_value_.get())
                                listParams.put("AND document_ID LIKE ?", new Pair<>(String.format("%s%%", searchDocument_ID_value_.get()), PreparedStatementAware.listType.STRING));
                            else
                                listParams.put("AND document_ID LIKE ?", new Pair<>(String.format("%%%s%%", searchDocument_ID_value_.get()), PreparedStatementAware.listType.STRING));
                        }

                        if (searchDocument_Category_selected_.get() != null) {
                            listParams.put("AND category_ID = ?", new Pair<>(searchDocument_Category_selected_.get().getcategory_ID(), PreparedStatementAware.listType.STRING));
                        }

                        if (searchDocument_Condition_selected_.get() != null) {
                            listParams.put("AND condition_ID = ?", new Pair<>(searchDocument_Condition_selected_.get().getcondition_ID(), PreparedStatementAware.listType.STRING));
                        }

                        if (searchDocument_StartDate_value_.get() != null) {
                            listParams.put("AND dateCreation_start >= ?", new Pair<>(searchDocument_StartDate_value_.get(), PreparedStatementAware.listType.DATE));
                        }
                        if (searchDocument_EndDate_value_.get() != null) {
                            listParams.put("AND dateCreation_end <= ?", new Pair<>(searchDocument_EndDate_value_.get(), PreparedStatementAware.listType.DATE));
                        }

                        listDocument.addAll(dao_document.selectByMultiCondition(listParams));
                        conn.commit();

                        if (LOGGER.isInfoEnabled()) {
                            LOGGER.info("List of document loaded");
                        }

                        updateMessage("Successfully loaded a list of document");
                        updateProgress(1, 1.0);

                    } catch (Exception e) {
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error(String.format("Error when synchronize", e.getMessage()), e);
                        }

                        updateMessage("Failed to load a list of document");

                        try {
                            conn.rollback();

                        } catch (SQLException e1) {
                            if (LOGGER.isErrorEnabled()) {
                                LOGGER.error(String.format("Error when try to rollback : %s", e1.getMessage()), e1);
                            }
                        }

                        throw e;
                    } finally {
                        list_document_.setAll(listDocument);
                    }
                }

                return null;
            }
        };

        //Search document
        TASKMANAGER.addArray(new TaskArray(ThreadArray.ExecutionType.PARALLEL)
                .addTask(new Pair(task_searchDocument, new TaskArray(ThreadArray.ExecutionType.END)))
        );
    }


    /**
     * Load all different Category from the database.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link Task} - return a task that will be executed
     */
    public Task<Void> load_Category() {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][method] Usage of the MainWindow_SearchDocumentViewModel.load_Category()");
        }

        return new Task_Custom<Void>(new Image(getClass().getResourceAsStream("/img/add_64.png")), "Load a list of category") {
            @Override
            protected Void call_Task() throws Exception {
                final List<Category> listCategory = new ArrayList<>();

                final DAO_Category dao_category = new DAO_Category();

                updateMessage("Try to load a list of Category");
                updateProgress(0.0, 1.0);

                try (Connection conn = Main.DB_CONNECTION.getDataSource().getConnection()) {

                    try {
                        dao_category.setConnection(conn);
                        conn.setAutoCommit(false);

                        updateProgress(0.4, 1.0);

                        listCategory.addAll(dao_category.selectAll());
                        conn.commit();

                        if (LOGGER.isInfoEnabled()) {
                            LOGGER.info("List of category loaded");
                        }

                        updateMessage("Successfully loaded a list of category");
                        updateProgress(1, 1.0);

                    } catch (Exception e) {
                        if (LOGGER.isErrorEnabled()) {
                            LOGGER.error(String.format("Error when synchronize", e.getMessage()), e);
                        }

                        updateMessage("Failed to load a list of category");

                        try {
                            conn.rollback();

                        } catch (SQLException e1) {
                            if (LOGGER.isErrorEnabled()) {
                                LOGGER.error(String.format("Error when try to rollback : %s", e1.getMessage()), e1);
                            }
                        }

                        throw e;
                    } finally {
                        listSearchDocument_Category_.setAll(listCategory);
                    }
                }

                return null;
            }
        };
    }

    /**
     * Load all different conditions from the database.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link Task} - return a task that will be executed
     */
    public Task<Void> load_Condition() {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][method] Usage of the MainWindow_SearchDocumentViewModel.load_Condition()");
        }

        return new Task_Custom<Void>(new Image(getClass().getResourceAsStream("/img/add_64.png")), "Load a list of condition") {
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
                        listSearchDocument_Condition_.setAll(listCondition);
                    }
                }

                return null;
            }
        };
    }


    //Text
    /**
     * Property of the variable searchDocument_title_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_title_label_.
     */
    public StringProperty searchDocument_title_label_Property() {
        return searchDocument_title_label_;
    }

    /**
     * Property of the variable searchDocument_ID_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_ID_label_.
     */
    public StringProperty searchDocument_ID_label_Property() {
        return searchDocument_ID_label_;
    }

    /**
     * Property of the variable searchDocument_Author_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_Author_label_.
     */
    public StringProperty searchDocument_Author_label_Property() {
        return searchDocument_Author_label_;
    }

    /**
     * Property of the variable searchDocument_Category_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_Category_label_.
     */
    public StringProperty searchDocument_Category_label_Property() {
        return searchDocument_Category_label_;
    }

    /**
     * Property of the variable searchDocument_Condition_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_Condition_label_.
     */
    public StringProperty searchDocument_Condition_label_Property() {
        return searchDocument_Condition_label_;
    }

    /**
     * Property of the variable searchDocument_between_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_between_label_.
     */
    public StringProperty searchDocument_between_label_Property() {
        return searchDocument_between_label_;
    }

    /**
     * Property of the variable searchDocument_StartDate_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_StartDate_label_.
     */
    public StringProperty searchDocument_StartDate_label_Property() {
        return searchDocument_StartDate_label_;
    }

    /**
     * Property of the variable searchDocument_and_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_and_label_.
     */
    public StringProperty searchDocument_and_label_Property() {
        return searchDocument_and_label_;
    }

    /**
     * Property of the variable searchDocument_EndDate_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_EndDate_label_.
     */
    public StringProperty searchDocument_EndDate_label_Property() {
        return searchDocument_EndDate_label_;
    }

    /**
     * Property of the variable searchDocument_ID_tabLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_ID_tabLabel_.
     */
    public StringProperty searchDocument_ID_tabLabel_Property() {
        return searchDocument_ID_tabLabel_;
    }

    /**
     * Property of the variable searchDocument_Title_tabLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_Title_tabLabel_.
     */
    public StringProperty searchDocument_Title_tabLabel_Property() {
        return searchDocument_Title_tabLabel_;
    }

    /**
     * Property of the variable searchDocument_Author_tabLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_Author_tabLabel_.
     */
    public StringProperty searchDocument_Author_tabLabel_Property() {
        return searchDocument_Author_tabLabel_;
    }

    /**
     * Property of the variable searchDocument_StartDate_tabLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_StartDate_tabLabel_.
     */
    public StringProperty searchDocument_StartDate_tabLabel_Property() {
        return searchDocument_StartDate_tabLabel_;
    }

    /**
     * Property of the variable searchDocument_EndDate_tabLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_EndDate_tabLabel_.
     */
    public StringProperty searchDocument_EndDate_tabLabel_Property() {
        return searchDocument_EndDate_tabLabel_;
    }

    /**
     * Property of the variable searchDocument_Category_tabLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_Category_tabLabel_.
     */
    public StringProperty searchDocument_Category_tabLabel_Property() {
        return searchDocument_Category_tabLabel_;
    }

    /**
     * Property of the variable searchDocument_Condition_tabLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_Condition_tabLabel_.
     */
    public StringProperty searchDocument_Condition_tabLabel_Property() {
        return searchDocument_Condition_tabLabel_;
    }

    /**
     * Property of the variable searchDocument_Delete_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_Delete_label_.
     */
    public StringProperty searchDocument_Delete_label_Property() {
        return searchDocument_Delete_label_;
    }

    /**
     * Property of the variable searchDocument_Research_label_Property.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_Research_label_Property.
     */
    public StringProperty searchDocument_Research_label_Property() {
        return searchDocument_Research_label_;
    }


    //Value
    /**
     * Property of the variable searchDocument_ID_value_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_ID_value_.
     */
    public StringProperty searchDocument_ID_value_Property() {
        return searchDocument_ID_value_;
    }

    /**
     * Property of the variable searchDocument_button_StartWith_ID_value_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link BooleanProperty} - return the property of the variable searchDocument_button_StartWith_ID_value_.
     */
    public BooleanProperty searchDocument_button_StartWith_ID_value_Property() {
        return searchDocument_button_StartWith_ID_value_;
    }

    /**
     * Property of the variable searchDocument_Author_value_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable searchDocument_Author_value_.
     */
    public StringProperty searchDocument_Author_value_Property() {
        return searchDocument_Author_value_;
    }

    /**
     * Property of the variable SearchDocument_button_StartWith_Author_value_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link BooleanProperty} - return the property of the variable SearchDocument_button_StartWith_Author_value_.
     */
    public BooleanProperty searchDocument_button_StartWith_Author_value_Property() {
        return SearchDocument_button_StartWith_Author_value_;
    }

    /**
     * Property of the variable listSearchDocument_Category_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ListProperty} - return the property of the variable listSearchDocument_Category_.
     */
    public ListProperty<Category> listSearchDocument_Category_Property() {
        return listSearchDocument_Category_;
    }

    /**
     * Property of the variable searchDocument_Category_selected_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable searchDocument_Category_selected_.
     */
    public ObjectProperty<Category> searchDocument_Category_selected_Property() {
        return searchDocument_Category_selected_;
    }

    /**
     * Property of the variable listSearchDocument_Condition_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ListProperty} - return the property of the variable listSearchDocument_Condition_.
     */
    public ListProperty<Condition> listSearchDocument_Condition_Property() {
        return listSearchDocument_Condition_;
    }

    /**
     * Property of the variable searchDocument_Condition_selected_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable searchDocument_Condition_selected_.
     */
    public ObjectProperty<Condition> searchDocument_Condition_selected_Property() {
        return searchDocument_Condition_selected_;
    }

    /**
     * Property of the variable searchDocument_StartDate_value_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable searchDocument_StartDate_value_.
     */
    public ObjectProperty<LocalDate> searchDocument_StartDate_value_Property() {
        return searchDocument_StartDate_value_;
    }

    /**
     * Property of the variable searchDocument_EndDate_value_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable searchDocument_EndDate_value_.
     */
    public ObjectProperty<LocalDate> searchDocument_EndDate_value_Property() {
        return searchDocument_EndDate_value_;
    }

    /**
     * Property of the variable list_document_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ListProperty} - return the property of the variable list_document_.
     */
    public ListProperty<Document> list_document_Property() {
        return list_document_;
    }

    /**
     * Property of the variable current_document_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty} - return the property of the variable current_document_.
     */
    public ObjectProperty<Document> current_document_Property() {
        return current_document_;
    }


    /**
     * Listener for the ressource bundle.
     *
     * @param observable - {@link ObservableValue} - the value observed
     * @param oldValue - {@link ResourceBundle} - the old value that are remplaced
     * @param newValue - {@link ResourceBundle} - the new value
     */
    private void listener_bundleLanguage(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        this.searchDocument_title_label_.set(this.resBundle_.get().getString("tPaneSearchDocument_title"));

        this.searchDocument_ID_label_.set(this.resBundle_.get().getString("tfSearchDocument_ID"));
        this.searchDocument_Author_label_.set(this.resBundle_.get().getString("tfSearchDocument_Author"));
        this.searchDocument_Category_label_.set(this.resBundle_.get().getString("cbSearchDocument_Category"));
        this.searchDocument_Condition_label_.set(this.resBundle_.get().getString("cbSearchDocument_Condition"));

        this.searchDocument_between_label_.set(this.resBundle_.get().getString("lblSearchDocument_between"));
        this.searchDocument_StartDate_label_.set(this.resBundle_.get().getString("dPickerSearchDocument_StartDate"));
        this.searchDocument_and_label_.set(this.resBundle_.get().getString("lblSearchDocument_and"));
        this.searchDocument_EndDate_label_.set(this.resBundle_.get().getString("dPickerSearchDocument_EndDate"));

        this.searchDocument_ID_tabLabel_.set(this.resBundle_.get().getString("tabSearchDocument_ID"));
        this.searchDocument_Title_tabLabel_.set(this.resBundle_.get().getString("tabSearchDocument_Title"));
        this.searchDocument_Author_tabLabel_.set(this.resBundle_.get().getString("tabSearchDocument_Author"));
        this.searchDocument_StartDate_tabLabel_.set(this.resBundle_.get().getString("tabSearchDocument_StartDate"));
        this.searchDocument_EndDate_tabLabel_.set(this.resBundle_.get().getString("tabSearchDocument_EndDate"));
        this.searchDocument_Category_tabLabel_.set(this.resBundle_.get().getString("tabSearchDocument_Category"));
        this.searchDocument_Condition_tabLabel_.set(this.resBundle_.get().getString("tabSearchDocument_Condition"));

        this.searchDocument_Delete_label_.set(this.resBundle_.get().getString("btnSearchDocument_Delete"));
        this.searchDocument_Research_label_.set(this.resBundle_.get().getString("btnSearchDocument_Research"));


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
