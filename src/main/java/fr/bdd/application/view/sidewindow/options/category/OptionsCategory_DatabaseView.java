package fr.bdd.application.view.sidewindow.options.category;

import de.saxsys.mvvmfx.InjectViewModel;
import fr.bdd.application.view.FxmlView_SceneCycle;
import fr.bdd.application.viewmodel.sidewindow.options.category.OptionsCategory_DatabaseViewModel;
import fr.bdd.custom.remastered.controls.text.CustomPasswordField_R;
import fr.bdd.custom.remastered.controls.text.CustomTextField_R;
import fr.bdd.log.generate.CustomLogger;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View of the category database.
 * This view represent the accessibility for the database.
 *
 * @author Gaetan Brenckle
 */
public class OptionsCategory_DatabaseView extends FxmlView_SceneCycle<OptionsCategory_DatabaseViewModel> implements Initializable {

    private static final CustomLogger LOGGER = CustomLogger.create(OptionsCategory_DatabaseView.class.getName());

    @FXML private TitledPane tPaneDatabase_path;

    //Database
    @FXML private Text txtDatabase_dbServerLabel;
    @FXML private CustomTextField_R tfDatabase_dbServer;
    @FXML private CustomTextField_R tfDatabase_dbPort;

    @FXML private Text txtDatabase_dbNameLabel;
    @FXML private CustomTextField_R tfDatabase_dbName;

    @FXML private Text txtDatabase_dbUserLabel;
    @FXML private CustomTextField_R tfDatabase_dbUser;

    @FXML private Text txtDatabase_dbPasswordLabel;
    @FXML private CustomPasswordField_R pwdDatabase_dbPassword;

    @FXML private ImageView imgDatabase_dbConnected;
    @FXML private Text txtDatabase_dbConnectedLabel;

    private ChangeListener<String> listener_ChangedValue_logger_dbServer_ = null;
    private ChangeListener<String> listener_ChangedValue_logger_dbName_ = null;
    private ChangeListener<String> listener_ChangedValue_logger_dbUser_ = null;
    private ChangeListener<String> listener_ChangedValue_logger_dbPassword_ = null;

    @InjectViewModel
    private OptionsCategory_DatabaseViewModel optionsCategory_databaseViewModel;

    public OptionsCategory_DatabaseView() {
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.setViewModel(optionsCategory_databaseViewModel);

        tPaneDatabase_path.textProperty().bind(optionsCategory_databaseViewModel.tPaneDatabase_pathLabel_Property());

        txtDatabase_dbServerLabel.textProperty().bind(optionsCategory_databaseViewModel.txtDatabase_dbServerLabelProperty());
        tfDatabase_dbServer.textProperty().bindBidirectional(optionsCategory_databaseViewModel.tfDatabase_dbServerProperty());
        tfDatabase_dbPort.textProperty().bindBidirectional(optionsCategory_databaseViewModel.tfDatabase_dbPortProperty());

        txtDatabase_dbNameLabel.textProperty().bind(optionsCategory_databaseViewModel.txtDatabase_dbNameLabelProperty());
        tfDatabase_dbName.textProperty().bindBidirectional(optionsCategory_databaseViewModel.tfDatabase_dbNameProperty());

        txtDatabase_dbUserLabel.textProperty().bind(optionsCategory_databaseViewModel.txtDatabase_dbUserLabel_Property());
        tfDatabase_dbUser.textProperty().bindBidirectional(optionsCategory_databaseViewModel.pwdDatabase_dbUser_Property());

        txtDatabase_dbPasswordLabel.textProperty().bind(optionsCategory_databaseViewModel.txtDatabase_dbPasswordLabelProperty());
        pwdDatabase_dbPassword.textProperty().bindBidirectional(optionsCategory_databaseViewModel.ptfDatabase_dbPasswordProperty());

        imgDatabase_dbConnected.imageProperty().bind(optionsCategory_databaseViewModel.imgDatabase_dbConnectedProperty());
        txtDatabase_dbConnectedLabel.textProperty().bindBidirectional(optionsCategory_databaseViewModel.txtDatabase_dbConnectedLabelProperty());
    }


    @Override
    public void onViewAdded_Cycle() {
    }

    @Override
    public void onViewRemoved_Cycle() {
        if (this.listener_ChangedValue_logger_dbServer_ != null) {
            tfDatabase_dbServer.onChangeWhenReleaseProperty().removeListener(this.listener_ChangedValue_logger_dbServer_);
            this.listener_ChangedValue_logger_dbServer_ = null;
        }

        if (this.listener_ChangedValue_logger_dbName_ != null) {
            tfDatabase_dbName.onChangeWhenReleaseProperty().removeListener(this.listener_ChangedValue_logger_dbName_);
            this.listener_ChangedValue_logger_dbName_ = null;
        }

        if (this.listener_ChangedValue_logger_dbUser_ != null) {
            tfDatabase_dbUser.onChangeWhenReleaseProperty().removeListener(this.listener_ChangedValue_logger_dbUser_);
            this.listener_ChangedValue_logger_dbUser_ = null;
        }

        if (this.listener_ChangedValue_logger_dbPassword_ != null) {
            pwdDatabase_dbPassword.onChangeWhenReleaseProperty().removeListener(this.listener_ChangedValue_logger_dbPassword_);
            this.listener_ChangedValue_logger_dbPassword_ = null;
        }

        tPaneDatabase_path.textProperty().unbind();

        txtDatabase_dbServerLabel.textProperty().unbind();
        tfDatabase_dbServer.textProperty().unbindBidirectional(optionsCategory_databaseViewModel.tfDatabase_dbServerProperty());
        tfDatabase_dbPort.textProperty().unbindBidirectional(optionsCategory_databaseViewModel.tfDatabase_dbPortProperty());
        txtDatabase_dbNameLabel.textProperty().unbind();
        tfDatabase_dbName.textProperty().unbindBidirectional(optionsCategory_databaseViewModel.tfDatabase_dbNameProperty());
        txtDatabase_dbUserLabel.textProperty().unbind();
        tfDatabase_dbUser.textProperty().unbindBidirectional(optionsCategory_databaseViewModel.pwdDatabase_dbUser_Property());
        txtDatabase_dbPasswordLabel.textProperty().unbind();
        pwdDatabase_dbPassword.textProperty().unbindBidirectional(optionsCategory_databaseViewModel.ptfDatabase_dbPasswordProperty());
        imgDatabase_dbConnected.imageProperty().unbind();
        txtDatabase_dbConnectedLabel.textProperty().unbindBidirectional(optionsCategory_databaseViewModel.txtDatabase_dbConnectedLabelProperty());
    }
}
