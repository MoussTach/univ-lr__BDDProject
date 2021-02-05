package fr.bdd.application.view.mainwindow.search.graph;

import de.saxsys.mvvmfx.InjectViewModel;
import fr.bdd.application.view.FxmlView_SceneCycle;
import fr.bdd.application.viewmodel.mainwindow.search.document.MainWindow_SearchDocumentViewModel;
import fr.bdd.application.viewmodel.mainwindow.search.graph.MainWindow_SearchGraphViewModel;
import fr.bdd.custom.remastered.controls.list.CustomComboBox_R;
import fr.bdd.custom.remastered.controls.tabview.CustomEditingCell_Basic;
import fr.bdd.custom.remastered.controls.tabview.CustomEditingCell_Date;
import fr.bdd.custom.remastered.controls.tabview.CustomTableView;
import fr.bdd.custom.remastered.controls.text.CustomTextField_R;
import fr.bdd.job.db_project.jobclass.Category;
import fr.bdd.job.db_project.jobclass.Condition;
import fr.bdd.job.db_project.jobclass.Document;
import fr.bdd.log.generate.CustomLogger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.converter.LocalDateStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainWindow_SearchGraphView extends FxmlView_SceneCycle<MainWindow_SearchGraphViewModel> implements Initializable {
    private static final CustomLogger LOGGER = CustomLogger.create(MainWindow_SearchGraphView.class.getName());

    @FXML private Accordion accOrdo;

    @FXML private TitledPane tPaneSearchGraph_DocByCondition_title;
    @FXML private Button btnLoad_DocByCondition;
    @FXML private PieChart pieChart_DocByCondition;

    @FXML private TitledPane tPaneSearchGraph_LocationByDoc_title;
    @FXML private Button btnLoad_LocationByDoc;
    @FXML private BarChart barChart_LocationByDoc;


    private ChangeListener<TitledPane> listener_ChangedValue_ExpandedPane_ = null;

    @InjectViewModel
    private MainWindow_SearchGraphViewModel mainWindow_searchGraphViewModel;


    @FXML
    public void act_btnLoad_DocByCondition() {
        LOGGER.input(String.format("Press the button %s", btnLoad_DocByCondition.getId()));

        this.mainWindow_searchGraphViewModel.load_ConditionCircle();
    }

    @FXML
    public void act_btnLoad_LocationByDoc() {
        LOGGER.input(String.format("Press the button %s", btnLoad_LocationByDoc.getId()));

        this.mainWindow_searchGraphViewModel.load_LocationByDoc_Bar();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setViewModel(mainWindow_searchGraphViewModel);

        if (this.listener_ChangedValue_ExpandedPane_ == null) {
            this.listener_ChangedValue_ExpandedPane_ = (ObservableValue<? extends TitledPane> observable, TitledPane oldPane, TitledPane newPane) -> {
                boolean expand = true;
                for (TitledPane pane :  this.accOrdo.getPanes()) {
                    if (pane.isExpanded()) {
                        expand = false;
                    }
                }

                if ((expand) && (oldPane != null)) {
                    Platform.runLater(() ->
                            this.accOrdo.setExpandedPane(oldPane));
                }
            };
            this.accOrdo.expandedPaneProperty().addListener(this.listener_ChangedValue_ExpandedPane_);
            this.accOrdo.setExpandedPane(this.tPaneSearchGraph_DocByCondition_title);

            //Text
            tPaneSearchGraph_DocByCondition_title.textProperty().bind(this.mainWindow_searchGraphViewModel.searchGraph_DocByCondition_title_label_Property());
            pieChart_DocByCondition.titleProperty().bind(this.mainWindow_searchGraphViewModel.pieChart_DocByCondition_label_Property());

            tPaneSearchGraph_LocationByDoc_title.textProperty().bind(this.mainWindow_searchGraphViewModel.searchGraph_LocationByDoc_title_label_Property());
            barChart_LocationByDoc.titleProperty().bind(this.mainWindow_searchGraphViewModel.barChart_LocationByDoc_label_Property());

            //Value
            this.mainWindow_searchGraphViewModel.pieChart_DocByCondition_Property().addListener((observable, oldValue, newValue) -> {
                pieChart_DocByCondition.setData(newValue);
            });

            barChart_LocationByDoc.getXAxis().setLabel("Region");
            barChart_LocationByDoc.getYAxis().setLabel("Population (in millions)");
            barChart_LocationByDoc.setLegendSide(Side.RIGHT);
            this.mainWindow_searchGraphViewModel.barChart_LocationByDoc_Property().addListener((observable, oldValue, newValue) -> {
                barChart_LocationByDoc.getData().clear();
                barChart_LocationByDoc.getData().add(newValue);
            });
        }

    }

    @Override
    public void onViewAdded_Cycle() {
    }

    @Override
    public void onViewRemoved_Cycle() {
        if (this.listener_ChangedValue_ExpandedPane_ != null) {
            this.accOrdo.expandedPaneProperty().removeListener(this.listener_ChangedValue_ExpandedPane_);
            this.listener_ChangedValue_ExpandedPane_ = null;
        }

        //Text
        tPaneSearchGraph_DocByCondition_title.textProperty().unbind();
        pieChart_DocByCondition.titleProperty().unbind();

        //Value
        pieChart_DocByCondition.dataProperty().unbind();
    }
}
