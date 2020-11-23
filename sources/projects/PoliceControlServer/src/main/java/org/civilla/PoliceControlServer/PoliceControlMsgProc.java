package org.civilla.PoliceControlServer;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashMap;

public class PoliceControlMsgProc {
    protected static HashMap<String, BotCmd> cmdMap = CmdMap.initCmdMap();

    TelegramMessage message;
    String requestId;
    PoliceControlMsgProc(TelegramMessage message, String requestId){
        this.message = message;
        this.requestId = requestId;
    }
    public String processMessage(){
        return "ok";
    }
}

class CmdMap{
    public static String START = "start";
    public static String EDIT_DATA = "edit_data";
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
        cmdMap.put("start", new Start());
        cmdMap.put("about_us", new AboutUs());
        cmdMap.put("first_name", new FirstName());
        cmdMap.put("second_name", new SecondName());
        cmdMap.put("beer", new Beer());
        cmdMap.put("is_correct", new IsCorrect());
        cmdMap.put("choose_role", new ChooseRole());

        //cmdMap.put("view_data", new ViewData());
        //cmdMap.put("others_data", new OthersData());
        //cmdMap.put("dominator", new Dominator());
        return cmdMap;
    }
}

class SessionState{
    String chat_id;
    String msgId;
    String firstName;
    String lastName;
    boolean likesBeer;
    String firstNameBuf;
    String lastNameBuf;
    boolean likesBeerBuf;
    boolean isPersonalDataFilled;
    boolean isPoliceman;
    boolean isAdmin;

    SessionState(String msgId,
                 boolean isPoliceman){
        this.msgId = msgId;
        this.isPoliceman = isPoliceman;
    }
}

class CallbackResp{
    String new_id;
    String response;
    SessionState sessionState;
    CallbackResp(String new_id, String response, SessionState sessionState){
        this.new_id = new_id;
        this.response = response;
        this.sessionState = sessionState;
    }
}

class InitResp{
    String response;
    ReplyKeyboardMarkup keyboard;
    InitResp(String response, ReplyKeyboardMarkup keyboard){
        this.keyboard = keyboard;
        this.response = response;
    }
}

abstract class BotCmd {
    protected static final String WRONG_MESSAGE = "Wrong command. Please use allowed commands only.";
    protected static final String BACK = "Back";

    abstract public InitResp init(SessionState sessionState);
    abstract public CallbackResp callback(String msg, SessionState sessionState);
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
    public InitResp init(SessionState session) {
        return new InitResp(
                session.isPoliceman ? INIT_MSG_CITIZEN : INIT_MSG_COP,
                generateKeyboard(new String[]{
                        session.isPersonalDataFilled ? EDIT_MY_DATA : FILL_MY_DATA,
                        VIEW_MY_DATA,
                        session.isPoliceman ? VIEW_ALL_DATA : null,
                        session.isPoliceman ? USE_DOMINATOR : null,
                        ABOUT_US
                }));
    }
    @Override
    public CallbackResp callback(String msg, SessionState session) {
        String next_id = session.msgId;
        String response = null;
        switch (msg){
            case EDIT_MY_DATA: case FILL_MY_DATA: next_id = CmdMap.EDIT_DATA; break;
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
    public InitResp init(SessionState sessionState) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{BACK}));
    }
    @Override
    public CallbackResp callback(String msg, SessionState session) {
        String next_id;
        String response = null;
        if (BACK.equals(msg)) {
            next_id = CmdMap.FIRST_NAME;
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
    public InitResp init(SessionState sessionState) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{BACK}));
    }
    @Override
    public CallbackResp callback(String msg, SessionState session) {
        String next_id;
        String response = null;
        if (BACK.equals(msg)) {
            next_id = CmdMap.START;
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
    public InitResp init(SessionState sessionState) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{YES, NO, BACK}));
    }
    @Override
    public CallbackResp callback(String msg, SessionState session) {
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
    public InitResp init(SessionState sessionState) {
        String initMsg = String.join("",
                "Is this data correct? Answer Yes or No.\n",
                "First name: ", sessionState.firstNameBuf, "\n",
                "Last name: ", sessionState.lastNameBuf, "\n",
                "You like beer: ", sessionState.likesBeerBuf ? "Yes" : "Well, no, but, actually yes"
        );
        return new InitResp(initMsg, generateKeyboard(new String[]{YES, NO, BACK}));
    }

    @Override
    public CallbackResp callback(String msg, SessionState session) {
        String next_id = session.msgId;
        String response;
        switch (msg) {
            case YES:
                next_id = CmdMap.START;
                session.firstName = session.firstNameBuf;
                session.lastName = session.lastNameBuf;
                session.likesBeerBuf = session.likesBeer;
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
    public InitResp init(SessionState session) {
        return new InitResp(session.isPoliceman ? INIT_MSG_COP : INIT_MSG_CIV, generateKeyboard(new String[]{BACK}));
    }
    @Override
    public CallbackResp callback(String msg, SessionState session) {
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
    public InitResp init(SessionState session) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{BACK}));
    }
    @Override
    public CallbackResp callback(String msg, SessionState session) {
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