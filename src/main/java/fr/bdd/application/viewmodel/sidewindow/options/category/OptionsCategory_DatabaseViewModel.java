package fr.bdd.application.viewmodel.sidewindow.options.category;

import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import de.saxsys.mvvmfx.utils.validation.CompositeValidator;
import de.saxsys.mvvmfx.utils.validation.ObservableRuleBasedValidator;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import fr.bdd.application.Main;
import fr.bdd.custom.remastered.mvvm.utils.CompositeCommand_Remastered;
import fr.bdd.dataconnection.bdd.DbProperties_postgres;
import fr.bdd.language.LanguageBundle;
import fr.bdd.log.generate.CustomLogger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

import java.util.ResourceBundle;


/**
 * ViewModel of the view {@link fr.bdd.application.view.sidewindow.options.category.OptionsCategory_DatabaseView}.
 * This ViewModel process the accessibility for the database when listener_isDbConnected.
 *
 * @author Gaetan Brenckle
 */
public class OptionsCategory_DatabaseViewModel extends CategoryValidator {

    private static final CustomLogger LOGGER = CustomLogger.create(OptionsCategory_DatabaseViewModel.class.getName());

    private final ObjectProperty<ResourceBundle> resBundle_ = LanguageBundle.getInstance().bindResourceBundle("properties.language.sidewindow.options.lg_options");

    //Database
    private final StringProperty tPaneDatabase_pathLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("titleCategory_databasePath"));

    private final StringProperty txtDatabase_dbServerLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("txtDatabase_dbServerLabel"));
    private final StringProperty tfDatabase_dbServer_ = new SimpleStringProperty(Main.DB_CONNECTION.getServer());
    private final StringProperty tfDatabase_dbPort_ = new SimpleStringProperty(Main.DB_CONNECTION.getPort());

    private final StringProperty txtDatabase_dbNameLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("txtDatabase_dbNameLabel"));
    private final StringProperty tfDatabase_dbName_ = new SimpleStringProperty(Main.DB_CONNECTION.getServerName());

    private final StringProperty txtDatabase_dbUserLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("txtDatabase_dbUserLabel"));
    private final StringProperty pwdDatabase_dbUser_ = new SimpleStringProperty(Main.DB_CONNECTION.getUser());

    private final StringProperty txtDatabase_dbPasswordLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("txtDatabase_dbPasswordLabel"));
    private final StringProperty pwdDatabase_dbPassword_ = new SimpleStringProperty(Main.DB_CONNECTION.getPassword());

    private final StringProperty txtDatabase_dbConnectedLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("database_default"));
    private final ObjectProperty<Image> imgDatabase_dbConnected_ = new SimpleObjectProperty<>(new Image(this.getClass().getResourceAsStream("/img/neutral_32x32.png")));
    private final BooleanProperty isDatabaseConnected_ = new SimpleBooleanProperty(false);

    private final Command dataBasePathCommand_ = new DelegateCommand(() -> new Action() {
        @Override
        protected void action() {
            actvm_DataBasePath();
        }
    }, true);

    private final ObservableRuleBasedValidator validator_tfDbPath_ = validator_tfDbServer();
    private final CompositeValidator categoryDatabaseValidator_ = new CompositeValidator(validator_tfDbPath_);

    private final CompositeCommand_Remastered categoryDatabase_Command_ = new CompositeCommand_Remastered(dataBasePathCommand_);

    private ChangeListener<ResourceBundle> listener_ChangedValue_bundleLanguage_ = null;
    private ChangeListener<Boolean> listener_ChangedValue_isDbConnected_ = null;

    /**
     * Default Constructor.
     * Create a RessourceBundle listener.
     *
     * @author Gaetan Brenckle
     */
    public OptionsCategory_DatabaseViewModel() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][constructor] Creation of the OptionsCategory_DatabaseViewModel() object.");
        }

        //RessourceBundle listener
        if (this.listener_ChangedValue_bundleLanguage_ == null) {
            this.listener_ChangedValue_bundleLanguage_ = this::listener_bundleLanguage;
            this.resBundle_.addListener(this.listener_ChangedValue_bundleLanguage_);
        }

        //Database
        listener_isDbConnected(null, null, Main.DB_CONNECTION.isConnectedProperty().get());
        this.isDatabaseConnected_.bind(Main.DB_CONNECTION.isConnectedProperty());
        if (this.listener_ChangedValue_isDbConnected_ == null) {
            this.listener_ChangedValue_isDbConnected_ = this::listener_isDbConnected;
            this.isDatabaseConnected_.addListener(this.listener_ChangedValue_isDbConnected_);
        }
    }

    /**
     * Validator of the dbPath textfield.
     * can return a validation error in cause of:
     *     - null or empty path
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObservableRuleBasedValidator} - a group of rules.
     */
    private ObservableRuleBasedValidator validator_tfDbServer() {

        final ObjectBinding<ValidationMessage> rule_tfDbPath = Bindings.createObjectBinding(() -> {
            if (this.tfDatabase_dbServer_.get() == null || this.tfDatabase_dbServer_.get().isEmpty()) {
                return ValidationMessage.error(this.resBundle_.get().getString("error_dbPath"));
            }
            return null;
        }, this.tfDatabase_dbServer_);

        final ObservableRuleBasedValidator validator_tfDbPath = new ObservableRuleBasedValidator();
        validator_tfDbPath.addRule(rule_tfDbPath);

        return validator_tfDbPath;
    }

    /**
     * action that will check if the current path is correct or not for the database.
     *
     * @author Gaetan Brenckle
     */
    private void actvm_DataBasePath() {
        DbProperties_postgres currDbConnection;

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[private][method] usage of OptionsCategory_DatabaseViewModel.actvm_DataBasePath().");
        }
        if (!(currDbConnection = Main.DB_CONNECTION).createConnection(this.tfDatabase_dbServer_.get(), this.tfDatabase_dbPort_.get(), this.tfDatabase_dbName_.get(), this.pwdDatabase_dbUser_.get(), this.pwdDatabase_dbPassword_.get())) {
            this.isDatabaseConnected_.set(false);
            return;
        }
        this.isDatabaseConnected_.set(true);
    }


    //Database property
    /**
     * Property of the variable tPaneDatabase_pathLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable tPaneDatabase_pathLabel_.
     */
    public StringProperty tPaneDatabase_pathLabel_Property() {
        return this.tPaneDatabase_pathLabel_;
    }

    /**
     * Property of the variable txtDatabase_dbServerLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable txtDatabase_dbServerLabel_.
     */
    public StringProperty txtDatabase_dbServerLabelProperty() {
        return this.txtDatabase_dbServerLabel_;
    }

    /**
     * Property of the variable tfDatabase_dbPath_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable tfDatabase_dbPath_.
     */
    public StringProperty tfDatabase_dbServerProperty() {
        return this.tfDatabase_dbServer_;
    }

    /**
     * Property of the variable tfDatabase_dbPort_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable tfDatabase_dbPort_.
     */
    public StringProperty tfDatabase_dbPortProperty() {
        return this.tfDatabase_dbPort_;
    }

    /**
     * Property of the variable txtDatabase_dbNameLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable txtDatabase_dbNameLabel_.
     */
    public StringProperty txtDatabase_dbNameLabelProperty() {
        return this.txtDatabase_dbNameLabel_;
    }

    /**
     * Property of the variable tfDatabase_dbName_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable tfDatabase_dbName_.
     */
    public StringProperty tfDatabase_dbNameProperty() {
        return this.tfDatabase_dbName_;
    }

    /**
     * Property of the variable txtDatabase_dbPasswordLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable txtDatabase_dbPasswordLabel_.
     */
    public StringProperty txtDatabase_dbPasswordLabelProperty() {
        return this.txtDatabase_dbPasswordLabel_;
    }

    /**
     * Property of the variable txtDatabase_dbUserLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable txtDatabase_dbUserLabel_.
     */
    public StringProperty txtDatabase_dbUserLabel_Property() {
        return txtDatabase_dbUserLabel_;
    }

    /**
     * Property of the variable pwdDatabase_dbUser_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable pwdDatabase_dbUser_.
     */
    public StringProperty pwdDatabase_dbUser_Property() {
        return pwdDatabase_dbUser_;
    }

    /**
     * Property of the variable pwdDatabase_dbPassword_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable pwdDatabase_dbPassword_.
     */
    public StringProperty ptfDatabase_dbPasswordProperty() {
        return this.pwdDatabase_dbPassword_;
    }

    /**
     * Property of the variable txtDatabase_dbConnectedLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable txtDatabase_dbConnectedLabel_.
     */
    public StringProperty txtDatabase_dbConnectedLabelProperty() {
        return this.txtDatabase_dbConnectedLabel_;
    }

    /**
     * Property of the variable imgDatabase_dbConnected_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty}{@literal <}{@link Image}{@literal >} - return the property of the variable imgDatabase_dbConnected_.
     */
    public ObjectProperty<Image> imgDatabase_dbConnectedProperty() {
        return this.imgDatabase_dbConnected_;
    }

    /**
     * Property of the variable isDatabaseConnected_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link BooleanProperty} - return the property of the variable isDatabaseConnected_.
     */
    public BooleanProperty isDatabaseConnectedProperty() {
        return this.isDatabaseConnected_;
    }


    /**
     * Getter overrided for the CompositeValidator of the class.
     *
     * @author Gaetan Brenckle
     *
     * @return - {@link CompositeValidator} - return the CompositeValidator of the class.
     */
    @Override
    public CompositeValidator getCompositeValidator() {
        return this.categoryDatabaseValidator_;
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
        return this.categoryDatabase_Command_;
    }


    /**
     * Listener for the ressource bundle.
     *
     * @param observable - {@link ObservableValue} - the value observed
     * @param oldValue - {@link ResourceBundle} - the old value that are remplaced
     * @param newValue - {@link ResourceBundle} - the new value
     */
    private void listener_bundleLanguage(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        this.txtDatabase_dbServerLabel_.set(this.resBundle_.get().getString("txtDatabase_dbServerLabel"));
        this.txtDatabase_dbNameLabel_.set(this.resBundle_.get().getString("txtDatabase_dbNameLabel"));
        this.txtDatabase_dbPasswordLabel_.set(this.resBundle_.get().getString("txtDatabase_dbPasswordLabel"));

        if (this.isDatabaseConnected_.get()) {
            this.txtDatabase_dbConnectedLabel_.set(this.resBundle_.get().getString("database_connected"));
        } else {
            this.txtDatabase_dbConnectedLabel_.set(this.resBundle_.get().getString("database_disconnected"));
        }
    }

    /**
     * listener of the connected value of the db.
     *
     * @author Gaetan Brenckle
     *
     * @param observable - {@link ObservableValue} - value observed
     * @param oldValue - {@link Boolean} - old value
     * @param newValue - {@link Boolean} - new value
     */
    private void listener_isDbConnected(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            this.imgDatabase_dbConnected_.set(new Image(this.getClass().getResourceAsStream("/img/success_32x32.png")));
            this.txtDatabase_dbConnectedLabel_.set(this.resBundle_.get().getString("database_connected"));
        } else {
            this.imgDatabase_dbConnected_.set(new Image(this.getClass().getResourceAsStream("/img/failed_32x32.png")));
            this.txtDatabase_dbConnectedLabel_.set(this.resBundle_.get().getString("database_disconnected"));
        }
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

        if (this.listener_ChangedValue_isDbConnected_ != null) {
            this.isDatabaseConnected_.removeListener(this.listener_ChangedValue_isDbConnected_);
            this.listener_ChangedValue_isDbConnected_ = null;
        }

        this.isDatabaseConnected_.unbind();
    }
}