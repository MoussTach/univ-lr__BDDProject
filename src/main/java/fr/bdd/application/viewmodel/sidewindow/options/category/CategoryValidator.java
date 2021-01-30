package fr.bdd.application.viewmodel.sidewindow.options.category;

import de.saxsys.mvvmfx.utils.validation.CompositeValidator;
import fr.bdd.application.viewmodel.ViewModel_SceneCycle;
import fr.bdd.custom.remastered.mvvm.utils.CompositeCommand_Remastered;

/**
 * CategoryValidator, a class extended by listViewItem when they want to update a parent pane when selectionned.
 *
 * @author Gaetan Brenckle
 */
public abstract class CategoryValidator extends ViewModel_SceneCycle {

    /**
     * Getter of the compositeCommand for the associed view that the extended class need to override.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link CompositeValidator} - compositeCommand of the associed view.
     */
    public abstract CompositeValidator getCompositeValidator();

    /**
     * Getter of the compositeCommand for the associed view that the extended class need to override.
     *
     * @author Gaetan Brenckle
     *
     * @return {@link CompositeCommand_Remastered} - CompositeCommand_Remastered of the associed view.
     */
    public abstract CompositeCommand_Remastered getComposite_Command();
}
