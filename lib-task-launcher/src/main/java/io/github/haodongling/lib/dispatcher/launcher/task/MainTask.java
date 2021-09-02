package io.github.haodongling.lib.dispatcher.launcher.task;

public abstract class MainTask extends Task {

    @Override
    public boolean runOnMainThread() {
        return true;
    }
}
