package fr.bdd.application.view.sidewindow.options.category;

import de.saxsys.mvvmfx.InjectViewModel;
import fr.bdd.application.view.FxmlView_SceneCycle;
import fr.bdd.application.viewmodel.sidewindow.options.category.OptionsCategory_DashboardViewModel;
import fr.bdd.application.viewmodel.sidewindow.options.category.OptionsCategory_DatabaseViewModel;
import fr.bdd.application.viewmodel.sidewindow.options.category.OptionsCategory_GeneralViewModel;
import fr.bdd.custom.remastered.controls.list.CustomComboBox_R;
import fr.bdd.custom.remastered.controls.text.CustomPasswordField_R;
import fr.bdd.custom.remastered.controls.text.CustomTextField_R;
import fr.bdd.job.jobclass.Request;
import fr.bdd.language.LanguageBundle;
import fr.bdd.log.generate.CustomLogger;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View of the category general.
 * This view represent the accessibility of general options
 *
 * @author Gaetan Brenckle
 */
public class OptionsCategory_DashboardView extends FxmlView_SceneCycle<OptionsCategory_DashboardViewModel> implements Initializable {

    private static final CustomLogger LOGGER = CustomLogger.create(OptionsCategory_DashboardView.class.getName());

    @FXML private TitledPane tPaneDashboard_request;

    @FXML private Text txtDashboard_request;
    @FXML private CustomComboBox_R<Request> cmbDashboard_request;

    @FXML private TitledPane tPaneDashboard_result;
    @FXML private WebView webDashboard_result;

    private final StringProperty webContent_ = new SimpleStringProperty("");
    private ChangeListener<String> listerner_WebContent_ = null;

    @InjectViewModel
    private OptionsCategory_DashboardViewModel optionsCategory_dashboardViewModel;


    public void initialize(URL location, ResourceBundle resources) {
        this.setViewModel(optionsCategory_dashboardViewModel);

        //Text
        this.tPaneDashboard_request.textProperty().bind(this.optionsCategory_dashboardViewModel.dashboard_request_title_Label_Property());
        this.txtDashboard_request.textProperty().bind(this.optionsCategory_dashboardViewModel.dashboard_request_label_Property());
        this.cmbDashboard_request.promptTextProperty().bind(this.optionsCategory_dashboardViewModel.dashboard_request_combo_label_Property());

        //Value
        this.cmbDashboard_request.listItemsProperty().bind(this.optionsCategory_dashboardViewModel.listRequest_Property());
        this.cmbDashboard_request.valueProperty().bindBidirectional(this.optionsCategory_dashboardViewModel.selectRequest_Property());

        this.tPaneDashboard_result.textProperty().bind(this.optionsCategory_dashboardViewModel.dashboard_result_title_label_Property());
        if (listerner_WebContent_ == null) {
            this.listerner_WebContent_ = (observable, oldValue, newValue) ->
                    Platform.runLater(() ->
                            this.webDashboard_result.getEngine().loadContent(newValue, "text/html")
                    );
            this.webContent_.addListener(this.listerner_WebContent_);
        }
        this.webContent_.bind(this.optionsCategory_dashboardViewModel.webHtml_Property());
        webDashboard_result.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/sidewindow/options/dashboard/optionDashboard_webView.css").toExternalForm());
    }


    @Override
    public void onViewAdded_Cycle() {
    }

    @Override
    public void onViewRemoved_Cycle() {
        /*
        if (listerner_WebContent_ != null) {
            this.webContent_.removeListener(this.listerner_WebContent_);
            this.listerner_WebContent_ = null;
        }

        //Text
        this.tPaneDashboard_request.textProperty().unbind();
        this.txtDashboard_request.textProperty().unbind();
        this.cmbDashboard_request.promptTextProperty().unbind();

        //Value
        this.cmbDashboard_request.listItemsProperty().unbind();
        this.cmbDashboard_request.valueProperty().unbindBidirectional(this.optionsCategory_dashboardViewModel.selectRequest_Property());

        this.tPaneDashboard_result.textProperty().unbind();
        this.webContent_.unbind();
         */
    }
}
