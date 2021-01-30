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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    @FXML
    public BorderPane bPaneMainView;

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

    // Method to to get extension of a file
    private String getExtension(String fileName){
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) //if the name is not empty
            return fileName.substring(i + 1).toLowerCase();

        return extension;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setViewModel(mainWindowViewModel);

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
        statusBarMainView.progressProperty().unbind();
        statusBarMainView.textProperty().unbind();
    }
}
