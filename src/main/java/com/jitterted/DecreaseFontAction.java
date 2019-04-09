package com.jitterted;

import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class DecreaseFontAction extends FontAction {
  public DecreaseFontAction() {
    super("Decrease Font Size");
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    FontSizerComponent.decreaseFontSizes();
  }

}
