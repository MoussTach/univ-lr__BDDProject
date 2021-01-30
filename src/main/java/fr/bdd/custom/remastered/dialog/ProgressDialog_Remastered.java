//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package fr.bdd.custom.remastered.dialog;

import fr.bdd.custom.remastered.mvvm.utils.CompositeCommand_Remastered;
import impl.org.controlsfx.i18n.Localization;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;


/**
 * A dialogBox used to follow the execution of plenty threads.
 * It allow the usage of {@link de.saxsys.mvvmfx.utils.commands.Command} and {@link Task} classes.
 *
 * @author Gaetan Brenckle
 */
public class ProgressDialog_Remastered extends Dialog<Void> {

    private final ProgressDialog_Remastered.ConcurrentProgressPane content;

    /**
     * Default constructor.
     * Create the ui printedarea of the dialogBox.
     *
     * @author Gaetan Brenckle
     */
    public ProgressDialog_Remastered() {

        this.setResultConverter((dialogButton) -> null);

        DialogPane dialogPane = this.getDialogPane();
        this.setTitle(Localization.getString("progress.dlg.title"));
        dialogPane.setHeaderText(Localization.getString("progress.dlg.header"));
        dialogPane.getStyleClass().add("progress-dialog");
        dialogPane.getStylesheets().add(ProgressDialog_Remastered.class.getResource("/css/controls/dialog/dialogs.css").toExternalForm());

        this.content = new ProgressDialog_Remastered.ConcurrentProgressPane(this);
        this.content.setMaxWidth(Double.MAX_VALUE);

        Label contentText = new Label();
        contentText.setWrapText(true);
        contentText.textProperty().bind(dialogPane.contentTextProperty());

        VBox currentLayout = new VBox(10.0D, contentText, content);

        currentLayout.setMaxWidth(Double.MAX_VALUE);
        currentLayout.setPrefSize(300.0D, 100.0D);

        dialogPane.setContent(currentLayout);
    }

    public void addTask(Task<?>... workers) {

        for (javafx.concurrent.Task<?> Task : workers) {
            addTask(Task);
        }
    }

    public void addTask(Task<?> Task) {

        if (Task != null) {

            Pair<javafx.concurrent.Task<?>, CountDownLatch> pair = new ImmutablePair<>(Task, new CountDownLatch(1));
            this.content.addTask(pair);
        }
    }

    /**
     * This Method add commands on the list of commands followed, with a other method.
     *
     * @see ProgressDialog_Remastered#addListenedCommand(CompositeCommand_Remastered)
     *
     * @param commands {@link de.saxsys.mvvmfx.utils.commands.Command} - a varargs of commands
     */
    public void addListenedCommand(CompositeCommand_Remastered... commands) {

        for (CompositeCommand_Remastered command : commands) {
            addListenedCommand(command);
        }
    }

    /**
     * This Method add commands on the list of commands followed, with the creation of a {@link CountDownLatch}
     *
     * @param command {@link de.saxsys.mvvmfx.utils.commands.Command} - a commmand that you want to execute his actions
     */
    public void addListenedCommand(CompositeCommand_Remastered command) {

        if (command.getSize() > 0) {

            Pair<CompositeCommand_Remastered, CountDownLatch> pair = new ImmutablePair<>(command, new CountDownLatch(1));
            this.content.addCommand(pair);
        }
    }

    /**
     * Launch the execution of both the dialogBox and the list of commands
     *
     * @see ConcurrentProgressPane#begin()
     */
    public void begin() {
        this.content.begin();
    }

    /**
     * SubClass used to contain all the code to execute the concurrency
     */
    private static class ConcurrentProgressPane extends Region {

        private final ProgressDialog_Remastered dialog;
        private final ProgressBar progressBar;

        private final Queue<Pair<Task<?>, CountDownLatch>> taskQueue = new ConcurrentLinkedDeque<>();
        private final ChangeListener<Number> progressTaskListener = this::progressTaskListenerOnChange;

        private final Queue<Pair<CompositeCommand_Remastered, CountDownLatch>> commandQueue = new ConcurrentLinkedDeque<>();
        private final ChangeListener<Number> progressCommandListener = this::progressCommandListenerOnChange;

        private final DoubleProperty lowerProgressProperty = new SimpleDoubleProperty(-1.00);

        private boolean dialogVisible = false;
        private boolean cancelDialogShow = false;

        /**
         * Default constructor.
         * create the printedarea with a progressBar to follow the execution.
         *
         * @param dialog - keep a instance of the current dialogBox
         */
        public ConcurrentProgressPane(ProgressDialog_Remastered dialog) {

            this.dialog = dialog;

            this.progressBar = new ProgressBar();
            this.progressBar.setMaxWidth(Double.MAX_VALUE);

            this.progressBar.progressProperty().bind(lowerProgressProperty);
            this.getChildren().add(this.progressBar);
        }

        /**
         * Start the dialogBox and execute the commands
         */
        public void begin() {

            this.cancelDialogShow = false;
            if (!this.cancelDialogShow) {
                this.dialogVisible = true;
                this.dialog.show();
            }

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {

                    for (Pair<CompositeCommand_Remastered, CountDownLatch> item : ConcurrentProgressPane.this.commandQueue) {
                        item.getRight().await();
                    }

                    for (Pair<Task<?>, CountDownLatch> item : ConcurrentProgressPane.this.taskQueue) {
                        item.getRight().await();
                    }

                    return null;
                }
            };

            task.setOnSucceeded(event -> this.end());
            Thread th = new Thread(task);
            th.start();

            for (Pair<CompositeCommand_Remastered, CountDownLatch> item : this.commandQueue) {
                item.getLeft().execute();
            }

            for (Pair<Task<?>, CountDownLatch> item : ConcurrentProgressPane.this.taskQueue) {
                new Thread(item.getLeft()).start();
            }
        }

        /**
         * End the dialogBox.
         * This will remove all listener and reinitialize the progressExecutionProperty of the {@link CompositeCommand_Remastered} class.
         *
         * This is called when the additionnal task is finished
         *
         * @see CompositeCommand_Remastered#reinitialize()
         */
        private void end() {

            this.progressBar.progressProperty().unbind();
            this.commandQueue.clear();
            this.dialogVisible = false;
            forcefullyHideDialog(this.dialog);

            for (Pair<CompositeCommand_Remastered, CountDownLatch> item : this.commandQueue) {

                item.getLeft().progressExecutionProperty().removeListener(this.progressCommandListener);
                item.getLeft().reinitialize();
            }

            for (Pair<Task<?>, CountDownLatch> item : this.taskQueue) {

                item.getLeft().progressProperty().removeListener(this.progressTaskListener);
            }
        }

        protected void layoutChildren() {
            if (this.progressBar != null) {
                Insets insets = this.getInsets();
                double w = this.getWidth() - insets.getLeft() - insets.getRight();
                double h = this.getHeight() - insets.getTop() - insets.getBottom();
                double prefH = this.progressBar.prefHeight(-1.0D);
                double x = insets.getLeft() + (w - w) / 2.0D;
                double y = insets.getTop() + (h - prefH) / 2.0D;
                this.progressBar.resizeRelocate(x, y, w, prefH);
            }
        }

        private void forcefullyHideDialog(Dialog<?> dialog) {
            DialogPane dialogPane = dialog.getDialogPane();
            if (containsCancelButton(dialog)) {
                dialog.hide();
            } else {
                dialogPane.getButtonTypes().add(ButtonType.CANCEL);
                dialog.hide();
                dialogPane.getButtonTypes().remove(ButtonType.CANCEL);
            }
        }

        private boolean containsCancelButton(Dialog<?> dialog) {
            DialogPane dialogPane = dialog.getDialogPane();
            Iterator<ButtonType> var2 = dialogPane.getButtonTypes().iterator();

            ButtonType type;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                type = var2.next();
            } while(type.getButtonData() != ButtonBar.ButtonData.CANCEL_CLOSE);

            return true;
        }

        public void addTask(Pair<Task<?>, CountDownLatch> info) {
            this.taskQueue.add(info);

            info.getLeft().progressProperty().addListener(this.progressTaskListener);

            info.getLeft().setOnSucceeded(event -> info.getRight().countDown());
        }

        /**
         * add a pair with the command and the {@link CountDownLatch} associatied.
         * Create the necessary listener to show the changes on the ui.
         *
         * @param info {@link Pair}
         */
        public void addCommand(Pair<CompositeCommand_Remastered, CountDownLatch> info) {
            this.commandQueue.add(info);

            ReadOnlyDoubleProperty currentProgressValueListerner = info.getLeft().progressExecutionProperty();
            currentProgressValueListerner.addListener(this.progressCommandListener);

            ReadOnlyBooleanProperty currentRunningValueListerner = info.getLeft().runningProperty();
            currentRunningValueListerner.addListener(new WeakChangeListener<>((observable, oldValue, newValue) -> {
                if (!newValue){
                    info.getRight().countDown();
                }
            }));
        }

        private void progressCommandListenerOnChange(final ObservableValue<? extends Number> observableValue, final Number oldValue, final Number newValue) {
            double lower = 1.00;

            for (Pair<CompositeCommand_Remastered, CountDownLatch> item : this.commandQueue) {

                ReadOnlyDoubleProperty currentValue = item.getLeft().progressExecutionProperty();

                if (currentValue.get() < lower) {
                    lower = currentValue.get();
                }
            }
            this.lowerProgressProperty.set(lower);
        }

        private void progressTaskListenerOnChange(final ObservableValue<? extends Number> observableValue, final Number oldValue, final Number newValue) {
            double lower = 1.00;

            for (Pair<Task<?>, CountDownLatch> item : this.taskQueue) {

                ReadOnlyDoubleProperty currentValue = item.getLeft().progressProperty();

                if (currentValue.get() < lower) {
                    lower = currentValue.get();
                }
            }
            this.lowerProgressProperty.set(lower);
        }
    }
}
