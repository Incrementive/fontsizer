package com.jitterted;

import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class IncreaseFontAction extends FontAction {
  public IncreaseFontAction() {
    super("Increase Font Size");
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    FontSizerComponent.increaseFontSizes();
  }

}
