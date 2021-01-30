package fr.bdd.application.service;

import de.saxsys.mvvmfx.*;
import fr.bdd.application.viewmodel.taskmanager.Service_Custom;
import fr.bdd.application.viewmodel.taskmanager.Task_Custom;
import fr.bdd.language.LanguageBundle;
import fr.bdd.log.generate.CustomLogger;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import java.util.ResourceBundle;

/**
 * Service class.
 *
 * Used to load a view on the main Window of the application.
 *
 * @author Gaetan Brenckle
 *
 * @param <T> a view
 * @param <V> a ViewModel of the class defined for {@link T}
 */
public class LoadMainViewService<T extends FxmlView<? extends V>, V extends ViewModel> extends Service_Custom<Void> {

    private static final CustomLogger LOGGER = CustomLogger.create(LoadMainViewService.class.getName());

    private final ObjectProperty<ResourceBundle> resBundle_ = LanguageBundle.getInstance().bindResourceBundle("properties.language.service.lg_service");


    private BorderPane bPaneNode_ = null;
    private Class<T> sceneClassView_ = null;
    private Context context_ = null;

    /**
     * Default Contructor.
     *
     * @author Gaetan Brenckle
     *
     * @param image - {@link Image} - image to show on taskManagerView
     */
    public LoadMainViewService(Image image) {
        super(image);
    }


    /**
     * Setter the borderPane targeted.
     *
     * @author Gaetan Brenckle
     *
     * @param bPaneNode_ - {@link BorderPane} - targeted pane
     * @return - {@link LoadMainViewService} - builder pattern
     */
    public LoadMainViewService setbPaneNode(BorderPane bPaneNode_) {
        this.bPaneNode_ = bPaneNode_;
        return this;
    }

    /**
     * Change the view loaded by the service.
     *
     * @author Gaetan Brenckle
     *
     * @param sceneClassView - {@link Class} - class of the view loaded.
     * @return - {@link LoadMainViewService} - builder pattern
     */
    public LoadMainViewService setNewView(Class<T> sceneClassView) {
        this.sceneClassView_ = sceneClassView;
        return this;
    }

    /**
     * Setter for the Context used on this service.
     *
     * @author Gaetan Brenckle
     *
     * @param context - {@link Context} - set the context
     * @return - {@link LoadMainViewService} - builder pattern
     */
    public LoadMainViewService setContext(Context context) {
        this.context_ = context;
        return this;
    }


    /**
     * Property of the variable bPaneNodeProperty.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link BorderPane}- return the property of the variable bPaneNodeProperty.
     */
    public final BorderPane bPaneNode() {
        return bPaneNode_;
    }


    /**
     * Task executed on concurrency.
     *
     * @author Gaetan Brenckle
     *
     * @return Void
     */
    @Override
    protected Task_Custom<Void> call_Task() {

        return new Task_Custom<Void>("Charge main view") {
            protected Void call_Task() {

                updateMessage(LoadMainViewService.this.resBundle_.get().getString("load_mainView_start_label"));
                updateProgress(1, 2);

                Platform.runLater(() -> {
                final ViewTuple<T, V> viewTuple = FluentViewLoader.fxmlView(sceneClassView_)
                        .context(context_)
                        .load();

                    bPaneNode().setCenter(viewTuple.getView());
                });

                updateMessage(LoadMainViewService.this.resBundle_.get().getString("load_mainView_ended_label"));
                updateProgress(2, 2);

                return null;
            }

            @Override
            protected void setException(Throwable t) {

                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("ERROR on loading the view", t);
                }
                super.setException(t);
            }
        };
    }
}