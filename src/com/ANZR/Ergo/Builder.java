package com.ANZR.Ergo;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Builder implements ProgramRunner{
    ProgramRunner g;
    ExecutionEnvironment h = new ExecutionEnvironment();

    @NotNull
    @Override
    public String getRunnerId() {

        return null;
    }

    @Override
    public boolean canRun(@NotNull String s, @NotNull RunProfile runProfile) {
        return false;
    }

    @Nullable
    @Override
    public RunnerSettings createConfigurationData(ConfigurationInfoProvider configurationInfoProvider) {
        return null;
    }

    @Override
    public void checkConfiguration(RunnerSettings runnerSettings, @Nullable ConfigurationPerRunnerSettings configurationPerRunnerSettings) throws RuntimeConfigurationException {

    }

    @Override
    public void onProcessStarted(RunnerSettings runnerSettings, ExecutionResult executionResult) {

    }

    @Nullable
    @Override
    public SettingsEditor getSettingsEditor(Executor executor, RunConfiguration runConfiguration) {
        return null;
    }

    @Override
    public void execute(@NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {

    }

    @Override
    public void execute(@NotNull ExecutionEnvironment executionEnvironment, @Nullable Callback callback) throws ExecutionException {

    }
}
