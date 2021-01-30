package fr.bdd.application.viewmodel.sidewindow.options;

import de.saxsys.mvvmfx.utils.commands.CompositeCommand;
import de.saxsys.mvvmfx.utils.validation.CompositeValidator;
import fr.bdd.application.view.sidewindow.options.category.OptionsCategory_DatabaseView;
import fr.bdd.application.view.sidewindow.options.category.OptionsCategory_GeneralView;
import fr.bdd.application.viewmodel.ViewModel_SceneCycle;
import fr.bdd.application.viewmodel.sidewindow.options.category.CategoryListItemViewModel;
import fr.bdd.application.viewmodel.sidewindow.options.category.CategoryScope;
import fr.bdd.custom.remastered.dialog.ProgressDialog_Remastered;
import fr.bdd.custom.remastered.mvvm.utils.CompositeCommand_Remastered;
import fr.bdd.language.LanguageBundle;
import fr.bdd.log.generate.CustomLogger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * ViewModel of the view {@link fr.bdd.application.view.sidewindow.options.OptionsWindowView}.
 * This ViewModel process thr action for the whole options window.
 *
 * @author Gaetan Brenckle
 */
public class OptionsWindowViewModel extends ViewModel_SceneCycle {

    private final CategoryScope categoryScope_ = new CategoryScope();

    private static final CustomLogger LOGGER = CustomLogger.create(OptionsWindowViewModel.class.getName());
    private final ObjectProperty<ResourceBundle> resBundle_ = LanguageBundle.getInstance().bindResourceBundle("properties.language.sidewindow.options.lg_options");


    private final StringProperty btnOptions_okLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("btnOptions_ok"));
    private final StringProperty btnOptions_cancelLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("btnOptions_cancel"));
    private final StringProperty btnOptions_applyLabel_ = new SimpleStringProperty(this.resBundle_.get().getString("btnOptions_apply"));

    private final ObjectProperty<Node> optionRootNode_ = new SimpleObjectProperty<>();
    private final ObjectProperty<Node> currentSceneNode_ = new SimpleObjectProperty<>();

    private final ObjectProperty<CategoryListItemViewModel> selectedCategory_ = new SimpleObjectProperty<>();
    private final ObservableList<CategoryListItemViewModel> olistCategoryItemViews_;

    private CompositeValidator optionValidator_;

    private CompositeCommand_Remastered option_Command_;

    private final ObjectProperty<CompositeValidator> currentSceneValidator_ = new SimpleObjectProperty<>(new CompositeValidator());
    private final ObjectProperty<CompositeCommand_Remastered> currentScene_Command_ = new SimpleObjectProperty<>(new CompositeCommand_Remastered());

    private ChangeListener<ResourceBundle> listener_ChangedValue_bundleLanguage_ = null;
    private ChangeListener<CategoryListItemViewModel> listener_ChangedValue_categoryItem_ = null;

    /**
     * Default Constructor.
     * Create a RessourceBundle listener.
     * Create a listener on the RIGHTS to perform a update of available category when the db_sav_log change.
     *
     * @author Gaetan Brenckle
     */
    public OptionsWindowViewModel() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][constructor] Creation of the OptionsWindowsViewModel() object.");
        }

        //RessourceBundle listener
        if (this.listener_ChangedValue_bundleLanguage_ == null) {
            this.listener_ChangedValue_bundleLanguage_ = this::listener_bundleLanguage;
            this.resBundle_.addListener(this.listener_ChangedValue_bundleLanguage_);
        }

        if (this.listener_ChangedValue_categoryItem_ == null) {
            this.listener_ChangedValue_categoryItem_ = this::listener_selectCategory;
            this.selectedCategory_.addListener(this.listener_ChangedValue_categoryItem_);
        }

        //Insert the first category and launch a fonction to add the other
        this.olistCategoryItemViews_ = FXCollections.observableArrayList(
                new CategoryListItemViewModel<>(
                        "generalCategory",
                        new Image(this.getClass().getResourceAsStream("/img/option/option_gear.png")),
                        OptionsCategory_GeneralView.class,
                        this.categoryScope_));


        addCategory();
        bindCommandFromCategory();
        this.categoryScope_.optionsBaseSceneProperty().bind(this.optionRootNode_);
    }

    /**
     * Method used to bind the command and validator from the category scene listed into the listView.
     *
     * @author Gaetan Brenckle
     */
    private void bindCommandFromCategory() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][method] usage of OptionsWindowViewModel.bindCommandFromCategory().");
        }

        this.optionValidator_ = new CompositeValidator();
        this.option_Command_ = new CompositeCommand_Remastered();

        final ArrayList<CompositeValidator> validatorList = new ArrayList<>();
        for (CategoryListItemViewModel category : this.olistCategoryItemViews_) {
            validatorList.add(category.getSceneClassViewModel_().getCompositeValidator());

            this.option_Command_.register(category.getSceneClassViewModel_().getComposite_Command());
        }
        this.optionValidator_.addValidators(validatorList.toArray(new CompositeValidator[0]));
    }

    /**
     * Method to add category panel on the list with the reference of user db_sav_log.
     *
     * @author Gaetan Brenckle
     */
    private void addCategory() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][method] usage of OptionsWindowViewModel.addCategory().");
        }

        CategoryListItemViewModel currentViewModel = null;

        if (true) {
            if (this.olistCategoryItemViews_.filtered(viewModel -> viewModel.getCategoryNamePassed_().equals("databaseCategory")).size() == 0) {

                this.olistCategoryItemViews_.add(new CategoryListItemViewModel<>(
                        "databaseCategory",
                        new Image(this.getClass().getResourceAsStream("/img/option/option_database.png")),
                        OptionsCategory_DatabaseView.class,
                        this.categoryScope_));
            }
        } else {
            this.olistCategoryItemViews_.removeAll(this.olistCategoryItemViews_.filtered(viewModel -> viewModel.getCategoryNamePassed_().equals("databaseCategory")));
        }
    }

    /**
     * Create the default {@link ProgressDialog_Remastered} of this window.
     * It's different that the {@link org.controlsfx.dialog.ProgressDialog} because the dialog must contain two progressBar
     * at the same time. The first is for the worker, and the other for all action that demand a change on the ui.
     *
     * @param commands  - The additionnal command
     * @return {@link ProgressDialog_Remastered} - return the dialog created
     */
    private ProgressDialog_Remastered default_ProgressDialog(CompositeCommand_Remastered... commands) {
        ProgressDialog_Remastered dialog = new ProgressDialog_Remastered();
        dialog.addListenedCommand(commands);

        dialog.initStyle(StageStyle.UTILITY);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);

        dialog.getDialogPane().contentTextProperty().set(this.resBundle_.get().getString("loadingDialogBox_message"));
        dialog.begin();
        return dialog;
    }

    /**
     * action when the button to accept is pressed.
     *
     * @author Gaetan Brenckle
     */
    public void act_btnOk() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[private][method] usage of OptionsWindowsViewModel.act_btnOk().");
        }

        ProgressDialog_Remastered dialogOk = default_ProgressDialog(option_Command_);
    }

    /**
     * action when the button to cancel is pressed.
     * Show a dialogbox to wait until the thread of the command is finished.
     *
     * @author Gaetan Brenckle
     */
    public void act_btnCancel() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[private][method] usage of OptionsWindowsViewModel.act_btnCancel().");
        }
    }

    /**
     * action when the button to apply is pressed.
     * Show a dialogbox to wait until the thread of the command is finished.
     *
     * @author Gaetan Brenckle
     */
    public void act_btnApply() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[private][method] usage of OptionsWindowsViewModel.act_btnApply().");
        }

        ProgressDialog_Remastered dialogOk = default_ProgressDialog(currentScene_Command_.get());
    }

    /**
     * Getter of a list with all category listed to be filled into a listView.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObservableList}{@literal <}{@link CategoryListItemViewModel}{@literal >} - return a list with all category.
     */
    public ObservableList<CategoryListItemViewModel> getOlistCategoryItemViews() {
        return this.olistCategoryItemViews_;
    }

    /**
     * Getter of the CompositeValidator that regroup all other CompositeValidator of the window. Even if the scene is
     * not loaded.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link CompositeValidator} - return the CompositeValidator of the window.
     */
    public CompositeValidator getOptionValidator() {
        return this.optionValidator_;
    }

    /**
     * Getter of the CompositeCommand that regroup all other CompositeCommand_Remastered of the window. Even if the scene is
     * not loaded.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link CompositeCommand} - return the compositeCommand of the window.
     */
    public CompositeCommand_Remastered getOptionCommand() {
        return this.option_Command_;
    }

    /**
     * Getter for the CompositeValidator of the current scene loaded dynamicly with the selection of the listView.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link CompositeValidator} - return the CompositeValidator of the current scene.
     */
    public CompositeValidator getCurrentSceneValidator() {
        return this.currentSceneValidator_.get();
    }

    /**
     * Getter for the compositeCommand of the current scene loaded dynamicly with the selection of the listView.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link CompositeCommand_Remastered} - return the CompositeCommand_Remastered of the current scene.
     */
    public CompositeCommand_Remastered getCurrentSceneCommand() {
        return this.currentScene_Command_.get();
    }

    /**
     * Property of the variable btnOptions_okLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable btnOptions_okLabel_.
     */
    public StringProperty btnOptions_okLabelProperty() {
        return this.btnOptions_okLabel_;
    }

    /**
     * Property of the variable btnOptions_cancelLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable btnOptions_cancelLabel_.
     */
    public StringProperty btnOptions_cancelLabelProperty() {
        return this.btnOptions_cancelLabel_;
    }

    /**
     * Property of the variable btnOptions_applyLabel_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link StringProperty} - return the property of the variable btnOptions_applyLabel_.
     */
    public StringProperty btnOptions_applyLabelProperty() {
        return this.btnOptions_applyLabel_;
    }

    /**
     * Property of the variable selectedCategory_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty}{@literal <}{@link CategoryListItemViewModel}{@literal >} - return the property of the variable selectedCategory_.
     */
    public ObjectProperty<CategoryListItemViewModel> selectedCategoryProperty() {
        return this.selectedCategory_;
    }

    /**
     * Property of the variable currentSceneNode_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty}{@literal <}{@link Node}{@literal >} - return the property of the variable currentSceneNode_.
     */
    public ObjectProperty<Node> currentSceneNode_Property() {
        return this.currentSceneNode_;
    }

    /**
     * Property of the variable optionRootNode_.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link ObjectProperty}{@literal <}{@link Node}{@literal >} - return the property of the variable optionRootNode_.
     */
    public ObjectProperty<Node> optionRootNodeProperty() {
        return this.optionRootNode_;
    }


    /**
     * Listener for the ressource bundle.
     *
     * @param observable - {@link ObservableValue} - the value observed
     * @param oldValue - {@link ResourceBundle} - the old value that are remplaced
     * @param newValue - {@link ResourceBundle} - the new value
     */
    private void listener_bundleLanguage(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        this.btnOptions_okLabel_.set(this.resBundle_.get().getString("btnOptions_ok"));
        this.btnOptions_cancelLabel_.set(this.resBundle_.get().getString("btnOptions_cancel"));
        this.btnOptions_applyLabel_.set(this.resBundle_.get().getString("btnOptions_apply"));
    }

    private void listener_selectCategory(ObservableValue<? extends CategoryListItemViewModel> observable, CategoryListItemViewModel oldValue, CategoryListItemViewModel newValue) {
        this.currentSceneNode_.set(newValue.getSceneNode());
        this.currentSceneValidator_.set(newValue.getSceneClassViewModel_().getCompositeValidator());

        this.currentScene_Command_.set(newValue.getSceneClassViewModel_().getComposite_Command());
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

        if (this.listener_ChangedValue_categoryItem_ != null) {
            this.selectedCategory_.removeListener(this.listener_ChangedValue_categoryItem_);
            this.listener_ChangedValue_bundleLanguage_ = null;
        }

        this.categoryScope_.optionsBaseSceneProperty().unbind();
    }
}