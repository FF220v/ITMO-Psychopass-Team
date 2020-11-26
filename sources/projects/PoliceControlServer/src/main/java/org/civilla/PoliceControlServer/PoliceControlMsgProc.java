package org.civilla.PoliceControlServer;
import org.civilla.common.StringUtils;
import org.civilla.dataclasses.database.BotSession;
import org.civilla.dataclasses.database.User;
import org.civilla.kubernetes.KubeConfigLoader;
import org.civilla.storage.DatabaseConnectorBotSessions;
import org.civilla.storage.DatabaseConnectorUsers;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

class UserData{
    public BotSession session;
    public User user;
    public String requestId;
}

public class PoliceControlMsgProc {
    protected static HashMap<String, BotCmd> cmdMap = CmdMap.initCmdMap();

    protected DefaultAbsSender getSender(){
        DefaultBotOptions options = new DefaultBotOptions();
        options.setMaxThreads(100);
        return new DefaultAbsSender(options) {
            @Override
            public String getBotToken() {
                return KubeConfigLoader.getBotSecrets().get("token").toString();
            }
        };
    }

    TelegramMessage message;
    String requestId;
    PoliceControlMsgProc(TelegramMessage message, String requestId){
        this.message = message;
        this.requestId = requestId;
    }
    public PoliceControlMsgProcResponse processMessage(){
        try {
            long objectId = message.objectId;
            DatabaseConnectorBotSessions sessionsConnector = new DatabaseConnectorBotSessions();
            DatabaseConnectorUsers usersConnector = new DatabaseConnectorUsers();

            User user = usersConnector.get(Long.toString(objectId), requestId);
            BotSession session = sessionsConnector.get(Long.toString(objectId), requestId);

            if (session == null) {
                session = new BotSession();
                session.objectId = Long.toString(message.objectId);
                session.msgId = CmdMap.START;
            }

            if (user == null) {
                user = new User();
                user.objectId = Long.toString(message.objectId);
            }

            UserData userData = new UserData();
            userData.user = user;
            userData.session = session;
            userData.requestId = requestId;

            BotCmd cmd = cmdMap.get(userData.session.msgId);

            CallbackResp callbackResp = cmd.callback(message.text, userData);
            String next_id = callbackResp.next_id;
            BotCmd newCmd = cmdMap.get(next_id);
            InitResp initResp = newCmd.init(callbackResp.userData);

            initResp.userData.session.msgId = next_id;
            sessionsConnector.update(initResp.userData.session, requestId);

            SendMessage telegramMessage = new SendMessage().setChatId(message.objectId);
            telegramMessage.
                    setText((callbackResp.response == null ? "" : (callbackResp.response + "\n\n")) +
                            (initResp.response == null ? "" : initResp.response));
            telegramMessage.setReplyMarkup(initResp.keyboard);
            telegramMessage.setParseMode("HTML");
            DefaultAbsSender sender = getSender();

            sender.execute(telegramMessage);

            return new PoliceControlMsgProcResponse("ok", "completed");
        } catch (Exception e) {
            return new PoliceControlMsgProcResponse("exception", e.getMessage());
        }
    }
}

class CmdMap{
    public static String START = "start";
    public static String VIEW_DATA = "view_data";
    public static String ABOUT_US = "about_us";
    public static String FIRST_NAME = "first_name";
    public static String SECOND_NAME = "second_name";
    public static String IS_CORRECT = "is_correct";
    public static String BEER = "others_data";
    public static String CHOOSE_ROLE = "choose_role";
    public static String DEVICES = "devices";
    public static String SHOW_DEVICES = "show_devices";
    public static String REGISTER_DEVICES = "register_devices";

    public static HashMap<String, BotCmd> initCmdMap(){
        HashMap<String, BotCmd> cmdMap = new HashMap<>();
        cmdMap.put(START, new Start());
        cmdMap.put(ABOUT_US, new AboutUs());
        cmdMap.put(FIRST_NAME, new FirstName());
        cmdMap.put(SECOND_NAME, new SecondName());
        cmdMap.put(BEER, new Beer());
        cmdMap.put(IS_CORRECT, new IsCorrect());
        cmdMap.put(CHOOSE_ROLE, new ChooseRole());
        cmdMap.put(VIEW_DATA, new ViewData());
        cmdMap.put(DEVICES, new Devices());
        cmdMap.put(SHOW_DEVICES, new ShowDevices());
        cmdMap.put(REGISTER_DEVICES, new RegisterDevices());

        return cmdMap;
    }
}


class CallbackResp{
    String next_id;
    String response;
    UserData userData;

    CallbackResp(String next_id, String response, UserData userData){
        this.next_id = next_id;
        this.response = response;
        this.userData = userData;
    }
}


class InitResp{
    String response;
    ReplyKeyboardMarkup keyboard;
    UserData userData;

    InitResp(String response, ReplyKeyboardMarkup keyboard, UserData userData){
        this.keyboard = keyboard;
        this.response = response;
        this.userData = userData;
    }
}


abstract class BotCmd {
    protected static final String WRONG_MESSAGE = "Wrong command. Please use allowed commands only.";
    protected static final String BACK = "Back";

    abstract public InitResp init(UserData userData);
    abstract public CallbackResp callback(String msg, UserData userData);
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
    protected static final String INIT_MSG_CITIZEN_F = "Hello there, citizen %s %s! It is civilla system. What do you want to do?";
    protected static final String INIT_MSG_COP_F = "Hello there, cop %s %s! It is civilla system. Ready to beat some citizens' asses today?";
    protected static final String UNREGISTERED = "Hello! It is civilla system. You have not registred yet, please fill your data.";

    protected static final String EDIT_MY_DATA = "Edit my data";
    protected static final String FILL_MY_DATA = "Fill my data";
    protected static final String VIEW_DATA = "View data";
    protected static final String DEVICES = "Devices";
    protected static final String ABOUT_US = "About us";
    @Override
    public InitResp init(UserData userData) {
        ReplyKeyboardMarkup keyboard;
        if(!userData.session.isPersonalDataFilled)
            keyboard = generateKeyboard(new String[]{
                    FILL_MY_DATA,
                    ABOUT_US});
        else
            keyboard = generateKeyboard(new String[]{
                    EDIT_MY_DATA,
                    VIEW_DATA,
                    userData.user.isPoliceman ? DEVICES : null,
                    ABOUT_US});

        String msg;
        if(userData.session.isPersonalDataFilled)
            msg = userData.user.isPoliceman ?
                String.format(INIT_MSG_COP_F, userData.user.firstName, userData.user.lastName) :
                String.format(INIT_MSG_CITIZEN_F, userData.user.firstName, userData.user.lastName);
        else
            msg = UNREGISTERED;

        return new InitResp(msg, keyboard, userData);
    }

    @Override
    public CallbackResp callback(String msg, UserData userData) {
        String next_id = userData.session.msgId;
        String response = null;

        if(userData.session.isPersonalDataFilled)
            switch (msg){
                case EDIT_MY_DATA: next_id = CmdMap.FIRST_NAME; break;
                case VIEW_DATA: next_id = CmdMap.VIEW_DATA; break;
                case DEVICES: if(userData.user.isPoliceman) { next_id = CmdMap.DEVICES; } else { response = WRONG_MESSAGE; } break;
                case ABOUT_US: next_id = CmdMap.ABOUT_US; break;
                case "imapolice": next_id = CmdMap.CHOOSE_ROLE; break;
                default: response = WRONG_MESSAGE;
            }
        else
            switch (msg){
                case FILL_MY_DATA: next_id = CmdMap.FIRST_NAME; break;
                case ABOUT_US: next_id = CmdMap.ABOUT_US; break;
                case "imapolice": next_id = CmdMap.CHOOSE_ROLE; break;
                default: response = WRONG_MESSAGE;
            }
        return new CallbackResp(next_id, response, userData);
    }
}

class FirstName extends BotCmd {
    protected static final String INIT_MSG = "Please, input your first name.";
    @Override
    public InitResp init(UserData userData) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{BACK}), userData);
    }
    @Override
    public CallbackResp callback(String msg, UserData userData) {
        String next_id;
        String response = null;
        if (BACK.equals(msg)) {
            next_id = CmdMap.START;
        } else {
            userData.session.firstNameBuf = msg;
            response = String.join(" ", "Your first name is", msg);
            next_id = CmdMap.SECOND_NAME;
        }
        return new CallbackResp(next_id, response, userData);
    }
}


class SecondName extends BotCmd {
    protected static final String INIT_MSG = "Please, input your second name.";
    @Override
    public InitResp init(UserData userData) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{BACK}), userData);
    }
    @Override
    public CallbackResp callback(String msg, UserData userData) {
        String next_id;
        String response = null;
        if (BACK.equals(msg)) {
            next_id = CmdMap.FIRST_NAME;
        } else {
            userData.session.lastNameBuf = msg;
            response = String.join(" ", "Your last name is", msg);
            next_id = CmdMap.BEER;
        }
        return new CallbackResp(next_id, response, userData);
    }
}

class Beer extends BotCmd {
    protected static final String INIT_MSG = "Do you like beer? Answer Yes or No.";
    protected static final String YES = "Yes";
    protected static final String NO = "No";

    @Override
    public InitResp init(UserData userData) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{YES, NO, BACK}), userData);
    }
    @Override
    public CallbackResp callback(String msg, UserData userData) {
        String next_id = userData.session.msgId;
        String response = null;
        switch (msg){
            case YES: next_id = CmdMap.IS_CORRECT; userData.session.likesBeerBuf = true; response = "Cool!"; break;
            case NO: next_id = CmdMap.IS_CORRECT; userData.session.likesBeerBuf = false; response = "Do not lie =)"; break;
            case BACK: next_id = CmdMap.SECOND_NAME; break;
            default: response = WRONG_MESSAGE;
        }
        return new CallbackResp(next_id, response, userData);
    }
}

class IsCorrect extends BotCmd {
    protected static final String YES = "Yes";
    protected static final String NO = "No";

    @Override
    public InitResp init(UserData userData) {
        String initMsg = String.join("",
                "Is this data correct? Answer Yes or No.\n",
                "First name: ", userData.session.firstNameBuf, "\n",
                "Last name: ", userData.session.lastNameBuf, "\n",
                "You like beer: ", userData.session.likesBeerBuf ? "Yes" : "Well, no, but, actually yes"
        );
        return new InitResp(initMsg, generateKeyboard(new String[]{YES, NO, BACK}), userData);
    }

    @Override
    public CallbackResp callback(String msg, UserData userData) {
        String next_id = userData.session.msgId;
        String response = null;
        switch (msg) {
            case YES:
                next_id = CmdMap.START;
                userData.user.firstName = userData.session.firstNameBuf;
                userData.user.lastName = userData.session.lastNameBuf;
                userData.user.likesBeer = userData.session.likesBeerBuf;
                if (userData.user.isPoliceman == null) {
                    userData.user.isPoliceman = false;
                    userData.user.isPolicemanStr = "no";
                }
                try {
                    DatabaseConnectorUsers conn = new DatabaseConnectorUsers();
                    conn.update(userData.user, userData.requestId);
                    userData.session.isPersonalDataFilled = true;
                    response = "Personal data saved!";
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    response = "An error occured! Personal data was not saved.";
                }
                break;
            case NO:
                next_id = CmdMap.START;
                response = "Personal data was not saved!";
                break;
            case BACK:
                next_id = CmdMap.BEER; break;
            default:
                response = WRONG_MESSAGE;
        }
        return new CallbackResp(next_id, response, userData);
    }
}

class AboutUs extends BotCmd {
    protected static final String INIT_MSG_CIV = "Demo version of civilla system.\nHelps you to be safe and proud of your government.";
    protected static final String INIT_MSG_COP = "Demo version of civilla system.\nHelps you to kick citizens' asses.";

    @Override
    public InitResp init(UserData userData) {
        return new InitResp(userData.user.isPoliceman ? INIT_MSG_COP : INIT_MSG_CIV, generateKeyboard(new String[]{BACK}), userData);
    }
    @Override
    public CallbackResp callback(String msg, UserData userData) {
        String next_id = userData.session.msgId;
        String response = null;
        if (BACK.equals(msg))
            next_id = CmdMap.START;
        else
            response = WRONG_MESSAGE;
        return new CallbackResp(next_id, response, userData);
    }
}

class ChooseRole extends BotCmd {
    protected static final String INIT_MSG = "It is an admin panel. Which access do you want to have?";
    protected static final String COP = "Cop";
    protected static final String CIVILIAN = "Civilian";

    @Override
    public InitResp init(UserData userData) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{CIVILIAN, COP, BACK}), userData);
    }
    @Override
    public CallbackResp callback(String msg, UserData userData) {
        String next_id = CmdMap.START;
        String response = null;
        try {
            switch (msg){
                case COP: userData.user.isPoliceman = true; userData.user.isPolicemanStr = "yes"; new DatabaseConnectorUsers().update(userData.user, userData.requestId); break;
                case CIVILIAN: userData.user.isPoliceman = false; userData.user.isPolicemanStr = "no"; new DatabaseConnectorUsers().update(userData.user, userData.requestId); break;
                case BACK: break;
                default: response = WRONG_MESSAGE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "Could not update roles data";
        }
        return new CallbackResp(next_id, response, userData);
    }
}

class ViewData extends BotCmd {
    protected static final String INIT_MSG = "Which data do you want to see?";

    protected static final String COPS = "Cops";
    protected static final String CITIZEN = "Citizens";
    protected static final String MY_DATA = "My data";

    @Override
    public InitResp init(UserData userData) {
        if(userData.user.isPoliceman)
            return new InitResp(INIT_MSG, generateKeyboard(new String[]{MY_DATA, CITIZEN, COPS, BACK}), userData);
        else
            return new InitResp(INIT_MSG, generateKeyboard(new String[]{MY_DATA, BACK}), userData);
    }

    @Override
    public CallbackResp callback(String msg, UserData userData) {
        String next_id = userData.session.msgId;
        String response = null;
        try {
        ArrayList<User> usersToShow;
            if(userData.user.isPoliceman)
                switch (msg){
                    case COPS:
                        response = "Cops:\n";
                        usersToShow = new DatabaseConnectorUsers().getByField("isPolicemanStr", "yes", userData.requestId); break;
                    case CITIZEN:
                        response = "Citizens:\n";
                        usersToShow = new DatabaseConnectorUsers().getByField("isPolicemanStr", "no", userData.requestId); break;
                    case MY_DATA:
                        response = "My data:\n";
                        usersToShow = new ArrayList<>();
                        usersToShow.add(userData.user);
                        break;
                    case BACK: next_id = CmdMap.START; return new CallbackResp(next_id, response, userData);
                    default: response = WRONG_MESSAGE; return new CallbackResp(next_id, response, userData);
                }
            else
                switch (msg) {
                    case MY_DATA:
                        usersToShow = new ArrayList<>();
                        usersToShow.add(userData.user);
                    case BACK: next_id = CmdMap.START; return new CallbackResp(next_id, response, userData);
                    default: response = WRONG_MESSAGE; return new CallbackResp(next_id, response, userData);
                }

            int fieldsize = 16;
            response = response + "<pre>" + "|" + String.join(
                    "|",
                    StringUtils.center("objectId", fieldsize),
                    StringUtils.center("First Name", fieldsize),
                    StringUtils.center("Last Name", fieldsize),
                    StringUtils.center("Personal info", fieldsize)) + "|" + "\n";
            response = response + "|" + String.join(
                    "|",
                    StringUtils.center("-----", fieldsize),
                    StringUtils.center("-----", fieldsize),
                    StringUtils.center("-----", fieldsize),
                    StringUtils.center("-----", fieldsize)) + "|" + "\n";
            for (User user: usersToShow) {
                response = response + "|" + String.join(
                        "|",
                        StringUtils.center(user.objectId, fieldsize),
                        StringUtils.center(user.firstName, fieldsize),
                        StringUtils.center(user.lastName, fieldsize),
                        StringUtils.center((user.likesBeer ? "Likes beer": "Prob likes beer"),fieldsize)) + "|" + "\n";
            }
            response = response + "</pre>";
        } catch (Exception e) {
            e.printStackTrace();
            response = "Could not acquire data.";
        }
        return new CallbackResp(next_id, response, userData);
    }
}

class Devices extends BotCmd {
    protected static final String INIT_MSG = "What to do with devices?";
    protected static final String SHOW_DEVICES = "Show devices";
    protected static final String REGISTER_DEVICE = "Register device";

    @Override
    public InitResp init(UserData userData) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{SHOW_DEVICES, REGISTER_DEVICE, BACK}), userData);
    }
    @Override
    public CallbackResp callback(String msg, UserData userData) {
        String next_id = CmdMap.DEVICES;
        String response = null;
        switch (msg){
            case SHOW_DEVICES: next_id = CmdMap.SHOW_DEVICES; break;
            case REGISTER_DEVICE: next_id = CmdMap.REGISTER_DEVICES; break;
            case BACK: next_id = CmdMap.START; break;
            default: response = WRONG_MESSAGE;
        }
        return new CallbackResp(next_id, response, userData);
    }
}

class ShowDevices extends BotCmd {
    protected static final String INIT_MSG = "What kind of devices to show?";
    protected static final String CAMERAS = "Cameras";
    protected static final String DOMINATORS = "Dominators";

    @Override
    public InitResp init(UserData userData) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{CAMERAS, DOMINATORS, BACK}), userData);
    }

    @Override
    public CallbackResp callback(String msg, UserData userData) {
        String next_id = CmdMap.SHOW_DEVICES;
        String response = null;
        try {
            switch (msg) {
                case CAMERAS:
                    break;
                case DOMINATORS:
                    break;
                case BACK: next_id = CmdMap.DEVICES; break;
                default:
                    response = WRONG_MESSAGE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "Could not load devices";
        }
        return new CallbackResp(next_id, response, userData);
    }
}

class RegisterDevices extends BotCmd {
    protected static final String INIT_MSG = "What kind of device to register?";
    protected static final String CAMERA = "Camera";
    protected static final String DOMINATOR = "Dominator";

    @Override
    public InitResp init(UserData userData) {
        return new InitResp(INIT_MSG, generateKeyboard(new String[]{CAMERA, DOMINATOR, BACK}), userData);
    }

    @Override
    public CallbackResp callback(String msg, UserData userData) {
        String next_id = CmdMap.REGISTER_DEVICES;
        String response = null;
        try {
            switch (msg) {
                case CAMERA:
                    break;
                case DOMINATOR:
                    break;
                case BACK: next_id = CmdMap.DEVICES; break;
                default:
                    response = WRONG_MESSAGE;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "Could register device";
        }
        return new CallbackResp(next_id, response, userData);
    }
}