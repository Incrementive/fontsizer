package com.jitterted;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.UISettings;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.impl.ActionToolbarImpl;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.extensions.PluginId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "FontSizer", storages = @Storage("fontsizer.xml"))
public class FontSizerComponent implements ApplicationComponent, PersistentStateComponent<FontSizerComponent.State> {
  public static final String PLUGIN_ID = "com.jitterted.fontsizer";
  private static State state;

  private static void refreshDisplay(UISettings uiSettingsInstance) {
    uiSettingsInstance.fireUISettingsChanged();
    EditorFactory.getInstance().refreshAllEditors();
    LafManager.getInstance().updateUI();
    ActionToolbarImpl.updateAllToolbarsImmediately();
  }

  private static void updateSizesFromState() {
    UISettings uiSettingsInstance = UISettings.getInstance();

    uiSettingsInstance.setFontSize(state.uiSize);
    uiSettingsInstance.setOverrideLafFonts(true);

    EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getGlobalScheme();
    globalScheme.setEditorFontSize(state.editorSize);
    globalScheme.setConsoleFontSize(state.consoleSize);

    refreshDisplay(uiSettingsInstance);

    ApplicationManager.getApplication().getMessageBus()
                      .syncPublisher(EditorColorsManager.TOPIC)
                      .globalSchemeChange(globalScheme);
  }

  private static void adjustState(int sizeDelta) {
    state.consoleSize += sizeDelta;
    state.editorSize += sizeDelta;
    state.uiSize += sizeDelta;
  }

  private static void adjustFontSizes(int sizeDelta) {
    adjustState(sizeDelta);
    updateSizesFromState();
  }

  public static void increaseFontSizes() {
    adjustFontSizes(state.sizeDelta);
  }

  public static void decreaseFontSizes() {
    adjustFontSizes(-state.sizeDelta);
  }

  @Override
  public void initComponent() {
    String version = pluginVersion();

    if (version.equals(state.version)) {
      return;
    }

    NotificationGroup group = new NotificationGroup(
        "com.jitterted.fontsizer",
        NotificationDisplayType.STICKY_BALLOON,
        true);

    Notification notification = group.createNotification(
        "Thank You for Downloading Font Sizer " + version,
        "If you find this plugin helpful, <b><a href=\"https://ko-fi.com/U7U48BNU\">please donate a cup of coffee</a>.</b>",
        NotificationType.INFORMATION,
        NotificationListener.URL_OPENING_LISTENER);

    Notifications.Bus.notify(notification);

    state.version = version;
  }

  private String pluginVersion() {
    IdeaPluginDescriptor descriptor = PluginManager.getPlugin(PluginId.getId(PLUGIN_ID));
    if (descriptor != null) {
      return descriptor.getVersion();
    } else {
      return "descriptor_empty";
    }
  }

  @Override
  public void noStateLoaded() {
    state = new State();
    state.sizeDelta = 1;

    UISettings uiSettingsInstance = UISettings.getInstance();
    state.uiSize = uiSettingsInstance.getFontSize();

    EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getGlobalScheme();
    state.editorSize = globalScheme.getEditorFontSize();
    state.consoleSize = globalScheme.getConsoleFontSize();

    state.version = pluginVersion();
  }

  @Nullable
  @Override
  public State getState() {
    return state;
  }

  @Override
  public void loadState(@NotNull State state) {
    FontSizerComponent.state = state;
    updateSizesFromState();
  }

  @NotNull
  @Override
  public String getComponentName() {
    return "Font Sizer";
  }

  public static class State {
    public int editorSize;
    public int consoleSize;
    public int uiSize;
    public int sizeDelta;
    public String version = "unset";

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      State state = (State) o;

      if (editorSize != state.editorSize) return false;
      if (consoleSize != state.consoleSize) return false;
      if (uiSize != state.uiSize) return false;
      if (sizeDelta != state.sizeDelta) return false;
      return version.equals(state.version);

    }

    @Override
    public int hashCode() {
      int result = editorSize;
      result = 31 * result + consoleSize;
      result = 31 * result + uiSize;
      result = 31 * result + sizeDelta;
      result = 31 * result + version.hashCode();
      return result;
    }
  }
}
