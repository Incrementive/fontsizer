package com.jitterted;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public abstract class FontAction extends AnAction {
  public FontAction(String text) {
    super(text);
  }

  @Override
  public void update(AnActionEvent anActionEvent) {
    anActionEvent.getPresentation().setEnabledAndVisible(true);
  }

}
