package fr.bdd.application.viewmodel.taskmanager;

import fr.bdd.application.viewmodel.ViewModel_SceneCycle;
import fr.bdd.log.generate.CustomLogger;

/**
 * ViewModel of the view {@link fr.bdd.application.view.taskmanager.TaskManagerWindowView}.
 * This ViewModel process the change of the taskManagerView
 *
 * @author Gaetan Brenckle
 */
public class TaskManagerWindowViewModel extends ViewModel_SceneCycle {

    private static final CustomLogger LOGGER = CustomLogger.create(TaskManagerWindowViewModel.class.getName());

    /**
     * Default Constructor
     *
     * @author Gaetan Brenckle
     */
    public TaskManagerWindowViewModel() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("[public][constructor] Creation of the TaskManagerWindowViewModel() object.");
        }
    }


    @Override
    public void onViewAdded_Cycle() {
    }

    @Override
    public void onViewRemoved_Cycle() {
    }
}
