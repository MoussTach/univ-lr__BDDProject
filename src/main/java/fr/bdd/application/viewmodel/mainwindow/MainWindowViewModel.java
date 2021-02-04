package fr.bdd.application.viewmodel.mainwindow;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ScopeProvider;
import de.saxsys.mvvmfx.ViewTuple;
import fr.bdd.application.Launch;
import fr.bdd.application.view.mainwindow.MainWindowView;
import fr.bdd.application.view.taskmanager.TaskManagerWindowView;
import fr.bdd.application.viewmodel.ViewModel_SceneCycle;
import fr.bdd.application.viewmodel.taskmanager.TaskManagerWindowViewModel;
import fr.bdd.language.LanguageBundle;
import fr.bdd.log.generate.CustomLogger;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * ViewModel of the view {@link MainWindowView}.
 * This ViewModel process some fonction for the whole application.
 *
 * @author Gaetan Brenckle
 */
@ScopeProvider(scopes= {MainScope.class})
public class MainWindowViewModel extends ViewModel_SceneCycle {

    private final ObjectProperty<ResourceBundle> resBundleWindow_ = LanguageBundle.getInstance().bindResourceBundle("properties.language.lg_window");
    private final ObjectProperty<ResourceBundle> resBundleSearch_ = LanguageBundle.getInstance().bindResourceBundle("properties.language.mainwindow.search.lg_search");

    private static final CustomLogger LOGGER = CustomLogger.create(MainWindowViewModel.class.getName());

    //Text
    private final StringProperty taskWindow_title_ = new SimpleStringProperty(this.resBundleWindow_.get().getString("taskWindow_title"));
    private final StringProperty tabCategory_SearchDocument_label_ = new SimpleStringProperty(this.resBundleSearch_.get().getString("lblTabCategory_SearchDocument_label"));
    private final StringProperty tabCategory_SearchProject_label_ = new SimpleStringProperty(this.resBundleSearch_.get().getString("lblTabCategory_SearchProject_label"));
    private final StringProperty tabCategory_SearchPerson_label_ = new SimpleStringProperty(this.resBundleSearch_.get().getString("lblTabCategory_SearchPerson_label"));
    private final StringProperty tabCategory_SearchPublication_label_ = new SimpleStringProperty(this.resBundleSearch_.get().getString("lblTabCategory_SearchPublication_label"));

    //Value
    private final BooleanProperty taskWindow_isShowed_ = new SimpleBooleanProperty(false);

    private ChangeListener<ResourceBundle> listener_ChangedValue_bundleLanguage_Window_ = null;
    private ChangeListener<ResourceBundle> listener_ChangedValue_bundleLanguage_Search_ = null;

    @InjectScope
    private MainScope mainScope;


    /**
     * Default constructor
     *
     * @author Gaetan Brenckle
     */
    public MainWindowViewModel() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][constructor] Creation of the MainWindowViewModel() object.");
        }

        //RessourceBundle Listener
        if (this.listener_ChangedValue_bundleLanguage_Window_ == null) {
            this.listener_ChangedValue_bundleLanguage_Window_ = this::listener_bundleLanguage_window;
            this.resBundleWindow_.addListener(this.listener_ChangedValue_bundleLanguage_Window_);
        }

        //RessourceBundle Listener
        if (this.listener_ChangedValue_bundleLanguage_Search_ == null) {
            this.listener_ChangedValue_bundleLanguage_Search_ = this::listener_bundleLanguage_search;
            this.resBundleSearch_.addListener(this.listener_ChangedValue_bundleLanguage_Search_);
        }
    }

    /**
     * action to open the external view of status.
     *
     * @author Gaetan Brenckle
     */
    public void act_openTaskExternal() {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][method] Usage of the MainWindowViewModel.act_openTaskExternal()");
        }

        if (!taskWindow_isShowed_.get()) {
            try {
                final ViewTuple<TaskManagerWindowView, TaskManagerWindowViewModel> taskExternalViewTuple = FluentViewLoader.fxmlView(TaskManagerWindowView.class)
                        .load();
                final Scene scene = new Scene(taskExternalViewTuple.getView());
                final Stage stage = new Stage();

                stage.titleProperty().bind(this.taskWindow_title_);
                stage.initOwner(Launch.PRIMARYSTAGE);

                final Image ico = new Image(this.getClass().getResourceAsStream("/img/logo/Logo_univLR_64.png"));
                stage.getIcons().add(ico);
                stage.setScene(scene);

                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("JavaFX information correctly loaded");
                }

                stage.show();
                taskWindow_isShowed_.set(true);
                stage.setOnCloseRequest(event -> taskWindow_isShowed_.set(false));

            } catch (Exception e) {
                if (LOGGER.isFatalEnabled()) {
                    LOGGER.fatal("FATAL ERROR - external status windows can't be loaded", e);
                }
            }
        }
    }


    /**
     * Property of the variable tabCategory_SearchDocument_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable tabCategory_SearchDocument_label_.
     */
    public StringProperty tabCategory_SearchDocument_label_Property() {
        return tabCategory_SearchDocument_label_;
    }

    /**
     * Property of the variable tabCategory_SearchProject_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable tabCategory_SearchProject_label_.
     */
    public StringProperty tabCategory_SearchProject_label_Property() {
        return tabCategory_SearchProject_label_;
    }

    /**
     * Property of the variable tabCategory_SearchPerson_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable tabCategory_SearchPerson_label_.
     */
    public StringProperty tabCategory_SearchPerson_label_Property() {
        return tabCategory_SearchPerson_label_;
    }

    /**
     * Property of the variable tabCategory_SearchPublication_label_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable tabCategory_SearchPublication_label_.
     */
    public StringProperty tabCategory_SearchPublication_label_Property() {
        return tabCategory_SearchPublication_label_;
    }


    /**
     * Setter for the scope property mainScope.bPaneNodeProperty().
     *
     * @author Gaetan Brenckle
     * @param bPaneMain - borderPane to link
     */
    public void setbPaneMainProperty(BorderPane bPaneMain) {
        mainScope.bPane_main_Property().set(bPaneMain);
    }

    /**
     * Bind the progress property with the scope.
     *
     * @author Gaetan Brenckle
     * @param doubleProperty - progress property to link
     */
    public void bindProgressProperty(DoubleProperty doubleProperty) {
        doubleProperty.bind(mainScope.progressProperty());
    }

    /**
     * Bind the progress_label property with the scope.
     *
     * @author Gaetan Brenckle
     * @param labelProperty - progress_label to link
     */
    public void bindProgress_labelProperty(StringProperty labelProperty) {
        labelProperty.bind(mainScope.progress_labelProperty());
    }


    /**
     * Listener for the ressource bundle.
     *
     * @param observable - {@link ObservableValue} - the value observed
     * @param oldValue - {@link ResourceBundle} - the old value that are remplaced
     * @param newValue - {@link ResourceBundle} - the new value
     */
    private void listener_bundleLanguage_window(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        this.taskWindow_title_.set(this.resBundleWindow_.get().getString("taskWindow_title"));
    }

    /**
     * Listener for the ressource bundle.
     *
     * @param observable - {@link ObservableValue} - the value observed
     * @param oldValue - {@link ResourceBundle} - the old value that are remplaced
     * @param newValue - {@link ResourceBundle} - the new value
     */
    private void listener_bundleLanguage_search(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        this.tabCategory_SearchDocument_label_.set(this.resBundleSearch_.get().getString("lblTabCategory_SearchDocument_label"));
        this.tabCategory_SearchProject_label_.set(this.resBundleSearch_.get().getString("lblTabCategory_SearchProject_label"));
        this.tabCategory_SearchPerson_label_.set(this.resBundleSearch_.get().getString("lblTabCategory_SearchPerson_label"));
        this.tabCategory_SearchPublication_label_.set(this.resBundleSearch_.get().getString("lblTabCategory_SearchPublication_label"));
    }


    @Override
    public void onViewAdded_Cycle() {
    }

    @Override
    public void onViewRemoved_Cycle() {
        if (this.listener_ChangedValue_bundleLanguage_Window_ != null) {
            this.resBundleWindow_.removeListener(this.listener_ChangedValue_bundleLanguage_Window_);
            this.listener_ChangedValue_bundleLanguage_Window_ = null;
        }
        LanguageBundle.getInstance().unbindRessourceBundle(this.resBundleWindow_);

        if (this.listener_ChangedValue_bundleLanguage_Search_ != null) {
            this.resBundleSearch_.removeListener(this.listener_ChangedValue_bundleLanguage_Search_);
            this.listener_ChangedValue_bundleLanguage_Search_ = null;
        }
        LanguageBundle.getInstance().unbindRessourceBundle(this.resBundleSearch_);
    }
}