import java.util.ArrayList;

public class BotSession {
    public String chat_id;
    public BotCommand currentCommand;
    public ArrayList<CivillaDatabaseItem> tempItems;
    public BotCommand baseCommand;
    public boolean isPoliceman;

    BotSession(String chat_id, BotCommand sessionTree){
        this.chat_id = chat_id;
        this.isPoliceman = false;
        this.tempItems = new ArrayList<>();
        this.currentCommand = sessionTree;
        this.baseCommand = sessionTree;
    }

    public void setCurrentCommand(BotCommand command){
        this.currentCommand = command;
    }

}
