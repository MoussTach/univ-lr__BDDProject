package fr.bdd.application.view.mainwindow.search.document;

import de.saxsys.mvvmfx.InjectViewModel;
import fr.bdd.application.view.FxmlView_SceneCycle;
import fr.bdd.application.viewmodel.mainwindow.search.document.MainWindow_SearchDocumentViewModel;
import fr.bdd.custom.remastered.controls.list.CustomComboBox_R;
import fr.bdd.custom.remastered.controls.tabview.CustomTableView;
import fr.bdd.custom.remastered.controls.text.CustomTextField_R;
import fr.bdd.job.db_project.jobclass.Category;
import fr.bdd.job.db_project.jobclass.Condition;
import fr.bdd.job.db_project.jobclass.Document;
import fr.bdd.log.generate.CustomLogger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.converter.LocalDateStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainWindow_SearchDocumentView extends FxmlView_SceneCycle<MainWindow_SearchDocumentViewModel> implements Initializable {
    private static final CustomLogger LOGGER = CustomLogger.create(MainWindow_SearchDocumentView.class.getName());

    @FXML private TitledPane tPaneSearchDocument_title;

    @FXML private CustomTextField_R tfSearchDocument_ID;
    @FXML private ToggleButton tbtnSearchDocument_StartWith_ID;
    @FXML private CustomTextField_R tfSearchDocument_Author;
    @FXML private ToggleButton tbtnSearchDocument_StartWith_Author;

    @FXML private CustomComboBox_R<Category> cbSearchDocument_Category;
    @FXML private CustomComboBox_R<Condition> cbSearchDocument_Condition;

    @FXML private Label lblSearchDocument_between;
    @FXML private DatePicker dPickerSearchDocument_StartDate;
    @FXML private Label lblSearchDocument_and;
    @FXML private DatePicker dPickerSearchDocument_EndDate;

    @FXML private CustomTableView<Document> tabPaneSearchDocument;
    @FXML private TableColumn<Document, String> tabSearchDocument_ID;
    @FXML private TableColumn<Document, String> tabSearchDocument_Title;
    @FXML private TableColumn<Document, String> tabSearchDocument_Author;
    @FXML private TableColumn<Document, LocalDate> tabSearchDocument_StartDate;
    @FXML private TableColumn<Document, LocalDate> tabSearchDocument_EndDate;
    @FXML private TableColumn<Document, String> tabSearchDocument_Category;
    @FXML private TableColumn<Document, String> tabSearchDocument_Condition;

    @FXML private Button btnSearchDocument_Reinitialize;
    @FXML private Button btnSearchDocument_Research;



    @InjectViewModel
    private MainWindow_SearchDocumentViewModel mainWindow_searchDocumentViewModel;


    @FXML
    public void act_btnReinitialize() {
        LOGGER.input(String.format("Press the button %s", btnSearchDocument_Reinitialize.getId()));

        this.mainWindow_searchDocumentViewModel.actvm_Reinitialize();
    }

    /**
     * Initialize the tablerow
     *
     * @author Gaetan Brenckle
     */
    private void initialize_tab() {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setViewModel(mainWindow_searchDocumentViewModel);
        initialize_tab();

        //Text
        tPaneSearchDocument_title.textProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_title_label_Property());

        tfSearchDocument_ID.promptTextProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_ID_label_Property());
        tfSearchDocument_Author.promptTextProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_Author_label_Property());

        cbSearchDocument_Category.promptTextProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_Category_label_Property());
        cbSearchDocument_Condition.promptTextProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_Condition_label_Property());

        lblSearchDocument_between.textProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_between_label_Property());
        dPickerSearchDocument_StartDate.promptTextProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_StartDate_label_Property());
        lblSearchDocument_and.textProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_and_label_Property());
        dPickerSearchDocument_EndDate.promptTextProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_EndDate_label_Property());

        tabSearchDocument_ID.textProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_ID_tabLabel_Property());
        tabSearchDocument_Title.textProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_Title_tabLabel_Property());
        tabSearchDocument_Author.textProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_Author_tabLabel_Property());
        tabSearchDocument_StartDate.textProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_StartDate_tabLabel_Property());
        tabSearchDocument_EndDate.textProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_EndDate_tabLabel_Property());
        tabSearchDocument_Category.textProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_Category_tabLabel_Property());
        tabSearchDocument_Condition.textProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_Condition_tabLabel_Property());

        btnSearchDocument_Research.textProperty().bind(this.mainWindow_searchDocumentViewModel.searchDocument_Research_label_Property());

        //Value
        tfSearchDocument_ID.textProperty().bindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_ID_value_Property());
        tbtnSearchDocument_StartWith_ID.selectedProperty().bindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_button_StartWith_ID_value_Property());
        tfSearchDocument_Author.textProperty().bindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_Author_value_Property());
        tbtnSearchDocument_StartWith_Author.selectedProperty().bindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_button_StartWith_Author_value_Property());
        cbSearchDocument_Category.itemsProperty().bind(this.mainWindow_searchDocumentViewModel.listSearchDocument_Category_Property());
        cbSearchDocument_Category.valueProperty().bindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_Category_selected_Property());
        cbSearchDocument_Condition.itemsProperty().bind(this.mainWindow_searchDocumentViewModel.listSearchDocument_Condition_Property());
        cbSearchDocument_Condition.valueProperty().bindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_Condition_selected_Property());

        dPickerSearchDocument_StartDate.valueProperty().bindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_StartDate_value_Property());
        dPickerSearchDocument_StartDate.setDayCellFactory(picker -> new DateCell() {

            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                LocalDate timeMax = dPickerSearchDocument_EndDate.getValue();

                if (!empty
                        && dPickerSearchDocument_StartDate.getValue() != null
                        && dPickerSearchDocument_StartDate.getValue().equals(date)) {
                    setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    if ((timeMax != null) && (timeMax.isBefore(date))) {
                        setBackground(new Background(new BackgroundFill(Color.LIGHTSALMON, CornerRadii.EMPTY, Insets.EMPTY)));
                    } else {
                        setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                }
            }
        });
        dPickerSearchDocument_StartDate.setConverter(new LocalDateStringConverter() {
            @Override
            public LocalDate fromString(String string) {
                LocalDate date = super.fromString(string);

                LocalDate timeMax = dPickerSearchDocument_EndDate.getValue();
                if ((timeMax != null) && date.isBefore(timeMax)) {
                    return date;
                } else {
                    return null;
                }
            }
        });

        dPickerSearchDocument_EndDate.valueProperty().bindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_EndDate_value_Property());
        dPickerSearchDocument_EndDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                LocalDate timeMin = dPickerSearchDocument_StartDate.getValue();

                if (!empty
                        && dPickerSearchDocument_EndDate.getValue() != null
                        && dPickerSearchDocument_EndDate.getValue().equals(date)) {
                    setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    if ((timeMin != null) && (timeMin.isAfter(date))) {
                        setBackground(new Background(new BackgroundFill(Color.LIGHTSALMON, CornerRadii.EMPTY, Insets.EMPTY)));
                    } else {
                        setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                }

                if (dPickerSearchDocument_StartDate.getValue() != null) {
                    setDisable(date.isBefore(dPickerSearchDocument_StartDate.getValue()));
                }
            }
        });
        dPickerSearchDocument_EndDate.setConverter(new LocalDateStringConverter() {
            @Override
            public LocalDate fromString(String string) {
                LocalDate date = super.fromString(string);

                LocalDate timeMin = dPickerSearchDocument_StartDate.getValue();
                if ((timeMin != null) && date.isAfter(timeMin)) {
                    return date;
                } else {
                    return null;
                }
            }
        });

    }

    @Override
    public void onViewAdded_Cycle() {
    }

    @Override
    public void onViewRemoved_Cycle() {

        //Text
        tPaneSearchDocument_title.textProperty().unbind();

        tfSearchDocument_ID.promptTextProperty().unbind();
        tfSearchDocument_Author.promptTextProperty().unbind();

        cbSearchDocument_Category.promptTextProperty().unbind();
        cbSearchDocument_Condition.promptTextProperty().unbind();

        lblSearchDocument_between.textProperty().unbind();
        dPickerSearchDocument_StartDate.promptTextProperty().unbind();
        lblSearchDocument_and.textProperty().unbind();
        dPickerSearchDocument_EndDate.promptTextProperty().unbind();

        tabSearchDocument_ID.textProperty().unbind();
        tabSearchDocument_Title.textProperty().unbind();
        tabSearchDocument_Author.textProperty().unbind();
        tabSearchDocument_StartDate.textProperty().unbind();
        tabSearchDocument_EndDate.textProperty().unbind();
        tabSearchDocument_Category.textProperty().unbind();
        tabSearchDocument_Condition.textProperty().unbind();

        btnSearchDocument_Research.textProperty().unbind();

        //Value
        tfSearchDocument_ID.textProperty().unbindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_ID_value_Property());
        tbtnSearchDocument_StartWith_ID.selectedProperty().unbindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_button_StartWith_ID_value_Property());
        tfSearchDocument_Author.textProperty().unbindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_Author_value_Property());
        tbtnSearchDocument_StartWith_Author.selectedProperty().unbindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_button_StartWith_Author_value_Property());
        cbSearchDocument_Category.itemsProperty().unbind();
        cbSearchDocument_Category.valueProperty().unbindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_Category_selected_Property());
        cbSearchDocument_Condition.itemsProperty().unbind();
        cbSearchDocument_Condition.valueProperty().unbindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_Condition_selected_Property());

        dPickerSearchDocument_StartDate.valueProperty().unbindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_StartDate_value_Property());
        dPickerSearchDocument_EndDate.valueProperty().unbindBidirectional(this.mainWindow_searchDocumentViewModel.searchDocument_EndDate_value_Property());

    }
}
