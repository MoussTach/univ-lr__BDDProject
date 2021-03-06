package fr.bdd.application.view;

import de.saxsys.mvvmfx.InjectViewModel;
import fr.bdd.application.Launch;
import fr.bdd.application.viewmodel.PreloaderWindowViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Preloader class
 *
 * @author Gaetan Brenckle
 */
public class PreloaderWindowView extends FxmlView_SceneCycle<PreloaderWindowViewModel> implements Initializable {

    @FXML
    public ProgressBar progressBar_Preloader;

    @InjectViewModel
    private PreloaderWindowViewModel preloaderWindowViewModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setViewModel(preloaderWindowViewModel);

        progressBar_Preloader.progressProperty().bind(Launch.progressLaunching);
    }


    @Override
    public void onViewAdded_Cycle() {
    }

    @Override
    public void onViewRemoved_Cycle() {
        progressBar_Preloader.progressProperty().unbind();
    }
}
