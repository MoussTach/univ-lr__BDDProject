package fr.bdd.application.view.mainwindow;

import de.saxsys.mvvmfx.InjectViewModel;
import fr.bdd.application.Launch;
import fr.bdd.application.view.FxmlView_SceneCycle;
import fr.bdd.application.viewmodel.mainwindow.MainWindowViewModel;
import fr.bdd.language.LanguageBundle;
import fr.bdd.log.generate.CustomLogger;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import org.controlsfx.control.StatusBar;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * View of the main window.
 *
 * @author Gaetan Brenckle
 */
public class MainWindowView extends FxmlView_SceneCycle<MainWindowViewModel> implements Initializable {

    private final ObjectProperty<ResourceBundle> resBundleWindow_ = LanguageBundle.getInstance().bindResourceBundle("properties.language.lg_window");
    private static final CustomLogger LOGGER = CustomLogger.create(MainWindowView.class.getName());

    @FXML public BorderPane bPaneMainView;

    @FXML private Label lblTabCategory_SearchDocument_label;
    @FXML private Label lblTabCategory_SearchProject_label;
    @FXML private Label lblTabCategory_SearchPerson_label;
    @FXML private Label lblTabCategory_SearchPublication_label;

    @FXML
    public StatusBar statusBarMainView;
    @FXML
    public Button btnExternalTask_MainView;

    @InjectViewModel
    private MainWindowViewModel mainWindowViewModel;

    /**
     * action of the button btnExternalTask_MainView when pressed.
     *
     * @author Gaetan Brenckle
     */
    @FXML
    public void act_btnExternalTask_MainView() {
        LOGGER.input(String.format("Press the button %s", btnExternalTask_MainView.getId()));

        mainWindowViewModel.act_openTaskExternal();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setViewModel(mainWindowViewModel);

        //Text
        lblTabCategory_SearchDocument_label.textProperty().bind(this.mainWindowViewModel.tabCategory_SearchDocument_label_Property());
        lblTabCategory_SearchProject_label.textProperty().bind(this.mainWindowViewModel.tabCategory_SearchProject_label_Property());
        lblTabCategory_SearchPerson_label.textProperty().bind(this.mainWindowViewModel.tabCategory_SearchPerson_label_Property());
        lblTabCategory_SearchPublication_label.textProperty().bind(this.mainWindowViewModel.tabCategory_SearchPublication_label_Property());

        //Value
        mainWindowViewModel.setbPaneMainProperty(bPaneMainView);
        mainWindowViewModel.bindProgressProperty(statusBarMainView.progressProperty());
        mainWindowViewModel.bindProgress_labelProperty(statusBarMainView.textProperty());

        Launch.PRIMARYSTAGE.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(Launch.PRIMARYSTAGE);
            alert.initModality(Modality.APPLICATION_MODAL);

            alert.setHeaderText(this.resBundleWindow_.get().getString("application_Exit_Header"));

            alert.getDialogPane().getButtonTypes().setAll(ButtonType.NO, ButtonType.YES);
            Optional<ButtonType> optional = alert.showAndWait();

            if (optional.isPresent() && optional.get() == ButtonType.YES) {
                return;
            }
            event.consume();
        });
    }


    @Override
    public void onViewAdded_Cycle() {
    }

    @Override
    public void onViewRemoved_Cycle() {
        //Text
        lblTabCategory_SearchDocument_label.textProperty().unbind();
        lblTabCategory_SearchProject_label.textProperty().unbind();
        lblTabCategory_SearchPerson_label.textProperty().unbind();
        lblTabCategory_SearchPublication_label.textProperty().unbind();

        //Value
        statusBarMainView.progressProperty().unbind();
        statusBarMainView.textProperty().unbind();
    }
}
