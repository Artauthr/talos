package com.talosvfx.talos.editor.notifications.commands.enums;

import java.lang.String;

/**
 * This is a generated class. It shouldn't be modified by hand, as the changes would be overridden.
 * To regenerate this class, call generateCommandsEnum task from Gradle.
 * The XML file is located in editor/assets/commands.xml
 */
public class Commands {
  public enum CommandGroup {
    GENERAL
  }

  public enum CommandType {
    COPY("copy_command", "Copy", "HERE IS A FULL DESCRIPTION OF COPY", "GENERAL"),

    PASTE("paste_command", "Paste", "HERE IS A FULL DESCRIPTION OF PASTE", "GENERAL"),

    SAVE("save_command", "Save", "HERE IS A FULL DESCRIPTION OF SAVE", "GENERAL"),

    OPEN("open_command", "Open", "HERE IS A FULL DESCRIPTION OF OPEN", "GENERAL"),

    UNDO("undo_command", "Undo", "HERE IS A FULL DESCRIPTION OF UNDO", "GENERAL"),

    REDO("redo_command", "Redo", "HERE IS A FULL DESCRIPTION OF REDO", "GENERAL");

    public final String uniqueName;

    public final String name;

    public final String description;

    public final CommandGroup commandType;

    CommandType(String uniqueName, String name, String description, String commandType) {
      this.uniqueName = uniqueName;
          this.name = name;
          this.description = description;
          this.commandType = CommandGroup.valueOf(commandType);;
    }
  }
}
