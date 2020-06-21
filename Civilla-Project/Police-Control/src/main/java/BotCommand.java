import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Hashtable;

public class BotCommand {
    String label;
    String outputText;
    BotAction action;
    BotCommand[] commands;
    Hashtable <String, BotCommand> _commands;
    ReplyKeyboardMarkup commandKeyboard;
    BotCommand prevCmd = null;
    String[] options;

    BotCommand(String label,
               String outputText,
               BotAction action,
               String[] options,
               BotCommand[] commands){

        this.label = label;
        this.action = action;
        this.commands = commands;
        this.outputText = outputText;
        this._commands = new Hashtable <>();
        this.options = options;

        for (BotCommand cmd: commands) {
            cmd.prevCmd = this;
            _commands.put(cmd.label, cmd);
        }

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        if (options != null)
        for (String option: options){
            KeyboardRow row = new KeyboardRow();
            row.add(new KeyboardButton(option));
            keyboardRows.add(row);
        }

        this.commandKeyboard = new ReplyKeyboardMarkup(keyboardRows);
        this.commandKeyboard.setOneTimeKeyboard(true);
        this.commandKeyboard.setResizeKeyboard(true);
    }

    public BotCommand getCommandByLabel(String label){
        return _commands.getOrDefault(label, null);
    }

    public ReplyKeyboardMarkup getKeyboard(){
        return this.commandKeyboard;
    }
}