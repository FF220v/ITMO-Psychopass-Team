package org.civilla.PoliceControlServer;
import org.civilla.kubernetes.KubeConfigLoader;
import org.civilla.storage.DummyBotSessionConnector;
import org.civilla.storage.IStorageConnector;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.HashMap;
import org.civilla.storage.BotSession;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class PoliceControlMsgProc {
    protected static HashMap<String, BotCmd> cmdMap = CmdMap.initCmdMap();

    TelegramMessage message;
    String requestId;
    PoliceControlMsgProc(TelegramMessage message, String requestId){
        this.message = message;
        this.requestId = requestId;
    }
    public String processMessage(){
        long chat_id = message.chat_id;
        IStorageConnector<BotSession> sessionsConnector = new DummyBotSessionConnector();
        BotSession session = sessionsConnector.get(Long.toString(chat_id));

        if (session == null) {
            session = new BotSession();
            session.chat_id = Long.toString(message.chat_id);
            session.msgId = CmdMap.START;
        }
        BotCmd cmd = cmdMap.get(session.msgId);
        CallbackResp callbackResp = cmd.callback(message.text, session);
        callbackResp.botSession.msgId = callbackResp.next_id;

        BotCmd newCmd = cmdMap.get(callbackResp.next_id);
        InitResp initResp = newCmd.init(callbackResp.botSession);

        sessionsConnector.update(initResp.botSession);

        SendMessage telegramMessage = new SendMessage().setChatId(message.chat_id);
        telegramMessage.
                setText((callbackResp.response == null ? "" : (callbackResp.response + "\n\n")) +
                        (initResp.response == null ? "" : initResp.response));
        telegramMessage.setReplyMarkup(initResp.keyboard);

        DefaultAbsSender sender = new DefaultAbsSender(new DefaultBotOptions()) {
            @Override
            public String getBotToken() {
                return KubeConfigLoader.getBotSecrets().get("token").toString();
            }
        };
        try {
            sender.execute(telegramMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return "ok";
    }
}

class CmdMap{
    public static String START = "start";
    public static String VIEW_DATA = "view_data";
    public static String ABOUT_US = "about_us";
    public static String FIRST_NAME = "first_name";
    public static String SECOND_NAME = "second_name";
    public static String IS_CORRECT = "is_correct";
    public static String OTHERS_DATA = "others_data";
    public static String BEER = "others_data";
    public static String DOMINATOR = "dominator";
    public static String CHOOSE_ROLE = "choose_role";


    public static HashMap<String, BotCmd> initCmdMap(){
        HashMap<String, BotCmd> cmdMap = new HashMap<>();
        cmdMap.put(START, new Start());
        cmdMap.put(ABOUT_US, new AboutUs());
        cmdMap.put(FIRST_NAME, new FirstName());
        cmdMap.put(SECOND_NAME, new SecondName());
        cmdMap.put(BEER, new Beer());
        cmdMap.put(IS_CORRECT, new IsCorrect());
        cmdMap.put(CHOOSE_ROLE, new ChooseRole());
        //cmdMap.put("view_data", new ViewData());
        //cmdMap.put("others_data", new OthersData());
        //cmdMap.put("dominator", new Dominator());
        return cmdMap;
    }
}


class CallbackResp{
    String next_id;
    String response;
    BotSession botSession;
    CallbackResp(String next_id, String response, BotSession botSession){
        this.next_id = next_id;
        this.response = response;
        this.botSession = botSession;
    }
}

class InitResp{
    String response;
    ReplyKeyboardMarkup keyboard;
    BotSession botSession;

    InitResp(String response, ReplyKeyboardMarkup keyboard, BotSession botSession){
        this.keyboard = keyboard;
        this.response = response;
        this.botSession = botSession;
    }
}

abstract class BotCmd {
    protected static final String WRONG_MESSAGE = "Wrong command. Please use allowed commands only.";
    protected static final String BACK = "Back";

    abstract public InitResp init(BotSession botSession);
    abstract public CallbackResp callback(String msg, BotSession botSession);
    protected ReplyKeyboardMarkup generateKeyboard(String[] options){
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        if (options != null)
        for (String option: options){
            if (option != null) {
                KeyboardRow row = new KeyboardRow();
                row.add(new KeyboardButton(option));
                keyboardRows.add(row);
            }
        }

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup(keyboardRows);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);
        return keyboard;
    }
}

class Start extends BotCmd {
    protected static final String INIT_MSG_CITIZEN = "Hello there, citizen! It is civilla system. What do you want to do?";
    protected static final String INIT_MSG_COP = "Hello there, cop! It is civilla system. Ready to beat some citizens' asses today?";
    protected static final String EDIT_MY_DATA = "Edit my data";
    protected static final String FILL_MY_DATA = "Fill my data";
    protected static final String VIEW_MY_DATA = "View my data";
    protected static final String VIEW_ALL_DATA = "View citizens data";
    protected static final String USE_DOMINATOR = "Use Dominator";
    protected static final String ABOUT_US = "About us";
    @Override
    public InitResp init(BotSession session) {
        return new InitResp(
                session.isPoliceman ? INIT_MSG_COP : INIT_MSG_CITIZEN,
                generateKeyboard(new String[]{
                        session.isPersonalDataFilled ? EDIT_MY_DATA : FILL_MY_DATA,
                        VIEW_MY_DATA,
                        session.isPoliceman ? VIEW_ALL_DATA : null,
                        session.isPoliceman ? USE_DOMINATOR : null,
                        ABOUT_US
                }), session);
    }
    @Override
    public CallbackResp callback(String msg, BotSession session) {
        String next_id = session.msgId;
        String response = null;
        switch (msg){
            case EDIT_MY_DATA: case FILL_MY_DATA: next_id = CmdMap.FIRST_NAME; break;
            case VIEW_MY_DATA: next_id = CmdMap.VIEW_DATA; break;
            case VIEW_ALL_DATA: if(session.isPoliceman) { next_id = CmdMap.OTHERS_DATA; } else { response = WRONG_MESSAGE; } break;
            case USE_DOMINATOR: if(session.isPoliceman) { next_id = CmdMap.DOMINATOR; } else { response = WRONG_MESSAGE; } break;
            case ABOUT_US: next_id = CmdMap.ABOUT_US; break;
            case "imapolice": next_id = CmdMap.CHOOSE_ROLE; break;
            default: response = WRONG_MESSAGE;
        }
        return new CallbackResp(next_id, response, session);
    }
}

class FirstName extends BotCmd {
    protected static final String INIT_MSG = "Please, input your first name.";
    @Override
    public InitResp init(BotSession session) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{BACK}), session);
    }
    @Override
    public CallbackResp callback(String msg, BotSession session) {
        String next_id;
        String response = null;
        if (BACK.equals(msg)) {
            next_id = CmdMap.START;
        } else {
            session.firstNameBuf = msg;
            response = String.join(" ", "Your first name is", msg);
            next_id = CmdMap.SECOND_NAME;
        }
        return new CallbackResp(next_id, response, session);
    }
}


class SecondName extends BotCmd {
    protected static final String INIT_MSG = "Please, input your second name.";
    @Override
    public InitResp init(BotSession session) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{BACK}), session);
    }
    @Override
    public CallbackResp callback(String msg, BotSession session) {
        String next_id;
        String response = null;
        if (BACK.equals(msg)) {
            next_id = CmdMap.FIRST_NAME;
        } else {
            session.lastNameBuf = msg;
            response = String.join(" ", "Your last name is", msg);
            next_id = CmdMap.BEER;
        }
        return new CallbackResp(next_id, response, session);
    }
}

class Beer extends BotCmd {
    protected static final String INIT_MSG = "Do you like beer? Answer Yes or No.";
    protected static final String YES = "Yes";
    protected static final String NO = "No";

    @Override
    public InitResp init(BotSession session) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{YES, NO, BACK}), session);
    }
    @Override
    public CallbackResp callback(String msg, BotSession session) {
        String next_id = session.msgId;
        String response;
        switch (msg){
            case YES: next_id = CmdMap.IS_CORRECT; session.likesBeerBuf = true; response = "Cool!"; break;
            case NO: next_id = CmdMap.IS_CORRECT; session.likesBeerBuf = false; response = "Do not lie =)"; break;
            case BACK: next_id = CmdMap.SECOND_NAME;
            default: response = WRONG_MESSAGE;
        }
        return new CallbackResp(next_id, response, session);
    }
}

class IsCorrect extends BotCmd {
    protected static final String YES = "Yes";
    protected static final String NO = "No";

    @Override
    public InitResp init(BotSession session) {
        String initMsg = String.join("",
                "Is this data correct? Answer Yes or No.\n",
                "First name: ", session.firstNameBuf, "\n",
                "Last name: ", session.lastNameBuf, "\n",
                "You like beer: ", session.likesBeerBuf ? "Yes" : "Well, no, but, actually yes"
        );
        return new InitResp(initMsg, generateKeyboard(new String[]{YES, NO, BACK}), session);
    }

    @Override
    public CallbackResp callback(String msg, BotSession session) {
        String next_id = session.msgId;
        String response;
        switch (msg) {
            case YES:
                next_id = CmdMap.START;
                session.firstName = session.firstNameBuf;
                session.lastName = session.lastNameBuf;
                session.likesBeerBuf = session.likesBeer;
                session.isPersonalDataFilled = true;
                response = "Personal data saved!";
                break;
            case NO:
                next_id = CmdMap.START;
                response = "Personal data was not saved!";
                break;
            case BACK:
                next_id = CmdMap.BEER;
            default:
                response = WRONG_MESSAGE;
        }
        return new CallbackResp(next_id, response, session);
    }
}

class AboutUs extends BotCmd {
    protected static final String INIT_MSG_CIV = "Demo version of civilla system.\nHelps you to be safe and proud of your government.";
    protected static final String INIT_MSG_COP = "Demo version of civilla system.\nHelps you to kick citizens' asses.";

    @Override
    public InitResp init(BotSession session) {
        return new InitResp(session.isPoliceman ? INIT_MSG_COP : INIT_MSG_CIV, generateKeyboard(new String[]{BACK}), session);
    }
    @Override
    public CallbackResp callback(String msg, BotSession session) {
        String next_id = session.msgId;
        String response = null;
        if (BACK.equals(msg))
            next_id = CmdMap.START;
        else
            response = WRONG_MESSAGE;
        return new CallbackResp(next_id, response, session);
    }
}

class ChooseRole extends BotCmd {
    protected static final String INIT_MSG = "It is an admin panel. Which access do you want to have?";
    protected static final String COP = "Cop";
    protected static final String CIVILIAN = "Civilian";

    @Override
    public InitResp init(BotSession session) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{CIVILIAN, COP, BACK}), session);
    }
    @Override
    public CallbackResp callback(String msg, BotSession session) {
        String next_id = session.msgId;
        String response = null;
        switch (msg){
            case COP: session.isPoliceman = true; break;
            case CIVILIAN: session.isPoliceman = false; break;
            default: response = WRONG_MESSAGE;
        }
        return new CallbackResp(next_id, response, session);
    }
}