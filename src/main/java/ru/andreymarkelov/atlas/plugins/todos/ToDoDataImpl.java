package ru.andreymarkelov.atlas.plugins.todos;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

public class ToDoDataImpl implements ToDoData {
    private final String PLUGIN_KEY = "ToDoFields";

    private final PluginSettings pluginSettings;

    public ToDoDataImpl(PluginSettingsFactory pluginSettingsFactory) {
        this.pluginSettings = pluginSettingsFactory.createSettingsForKey(PLUGIN_KEY);
    }

    private synchronized PluginSettings getPluginSettings() {
        return pluginSettings;
    }

    @Override
    public ToDoDataItem getToDoDataItem(String cfId) {
        return ToDoDataItemTranslator.getToDoDataItemFromString(getPluginSettings().get(cfId));
    }

    @Override
    public void setToDoDataItem(String cfId, ToDoDataItem item) {
        getPluginSettings().put(cfId, ToDoDataItemTranslator.storeToDoDataItemToString(item));
    }

}
