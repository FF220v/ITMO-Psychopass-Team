import java.util.ArrayList;
import java.util.Random;

interface BotAction{
    String execute(BotSession session, String input);
}

class AdminProfileStatus implements BotAction{
    @Override
    public String execute(BotSession session, String input) {
        switch (input) {
            case "Policeman":
                session.isPoliceman = true;
                session.baseCommand = CommandTrees.policeTree;
                session.currentCommand = session.baseCommand;
                return "Now you are policeman";
            case "Citizen":
                session.isPoliceman = false;
                session.baseCommand = CommandTrees.basicTree;
                session.currentCommand = session.baseCommand;
                return "Now you are citizen";
            default:
                return "Unsupported input";
        }

    }
}

class GoToLabel implements BotAction{
    @Override
    public String execute(BotSession session, String input) {
        BotCommand newBotCommand = session.currentCommand.getCommandByLabel(input);
        if (newBotCommand != null) {
            session.setCurrentCommand(newBotCommand);
        } else if (!input.equals("back"))
            return "Unsupported input";
        return "";
        }
    }


class PutFnameToDatabase implements BotAction{
    @Override
    public String execute(BotSession session, String input) {
        CivillaDatabaseItem item;
        if (!session.tempItems.isEmpty()) {
            item = session.tempItems.get(0);
        }else{
            item = new CivillaDatabaseItem(null, null, null, 0, false);
            session.tempItems.add(0, item);
        }
        item.id = session.chat_id;
        item.firstName = input;

        session.setCurrentCommand(session.currentCommand.getCommandByLabel("next"));
        return "First name saved.";

    }
}

class PutSnameToDatabase implements BotAction{
    @Override
    public String execute(BotSession session, String input) {
        CivillaDatabaseItem item = session.tempItems.get(0);
        item.secondName = input;

        session.setCurrentCommand(session.currentCommand.getCommandByLabel("next"));
        return "Second name saved.";
    }
}

class PutBeerToDatabase implements BotAction{
    @Override
    public String execute(BotSession session, String input) {
        CivillaDatabaseItem item = session.tempItems.get(0);
        input = input.toLowerCase();

        if (!(input.equals("yes") || input.equals("no"))){
            return "Incorrect input. Should answer yes or no";
        }

        item.likesBeer = input.toLowerCase().equals("yes");

        session.setCurrentCommand(session.currentCommand.getCommandByLabel("next"));
        return String.format("Beer info saved.\n\nFollowing info acquired: \nFirst Name: %s\nSecond name: %s\nLikes beer: %s",
                item.firstName, item.secondName, item.likesBeer);
    }
}


class IsItCorrect implements BotAction{
    @Override
    public String execute(BotSession session, String input) {
        CivillaDatabaseItem item = session.tempItems.get(0);
        session.setCurrentCommand(session.baseCommand);
        switch (input.toLowerCase()){
            case "yes":
                ICivillaDatabase databaseClient = new CivillaDatabaseDummyConnector();
                ICivillaAnalisys analisysClient = new CivillaAnalisysDummyConnector();

                CivillaAnalisysResponse analisysResponse = analisysClient.analyse(session.chat_id, String.join(" ", item.firstName, item.secondName));
                float crimeCoef = analisysResponse.content.get(session.chat_id);

                CivillaDatabaseResponse response = databaseClient.put(new CivillaDatabaseItem(item.id, item.firstName, item.secondName, crimeCoef, item.likesBeer));
                String res;
                if (response.status == 200)
                    res = "Data was submitted.";
                else
                    res = String.join(" ","Data was not submitted. Error:", response.response);
                session.setCurrentCommand(session.baseCommand);
                return res;
            case "no":
                session.setCurrentCommand(session.baseCommand);
                return "Data submission was declined.";
            default:
                return "Unsupported input";
        }
    }
}

class ShowProfileData implements  BotAction{
    @Override
    public String execute(BotSession session, String input) {
        ICivillaDatabase client = new CivillaDatabaseDummyConnector();
        CivillaDatabaseResponse response = client.get(session.chat_id);
        if (response.status == 404)
            return "Your data still not in database. Try adding it. It is free.";
        if (response.status != 200)
            return "Something wrong happened with us, please try again";
        else{
            CivillaDatabaseItem item = response.content.get(0);
            switch (input) {
                case "Profile data":
                    return String.format("Your profile info:\nFirst Name: %s\nSecond name: %s\nLikes beer: %s",
                            item.firstName, item.secondName, item.likesBeer);
                case "Psychopass":
                    return String.format("Your crime coefficient: %s", item.coefficient);
                default:
                    return "Unsupported input";
            }
        }
    }
}

class ShowCitizensData implements BotAction{
    @Override
    public String execute(BotSession session, String input) {
        ICivillaDatabase client = new CivillaDatabaseDummyConnector();
        CivillaDatabaseResponse response;

        if (input.equals("Full list"))
            response = client.query("some filter)))");
        else
            response = client.get(input);
        if (response.status == 404)
            return "Data cannot be found.";
        else if (response.status != 200)
            return String.format("Something went wrong. Error: %s", response.response);

        ArrayList<String> arr = new ArrayList<>();
        for (CivillaDatabaseItem item : response.content){
            arr.add(String.format("Citizen profile info:\nId: %s\nFirst Name: %s\nSecond name: %s\nLikes beer: %s\nCrime coefficient: %s",
                    item.id, item.firstName, item.secondName, item.likesBeer, item.coefficient));
        }
        return String.join("\n", arr);
    }
}

class UseDominator implements BotAction{
    @Override
    public String execute(BotSession session, String input) {
        ICivillaAnalisys analisysClient = new CivillaAnalisysDummyConnector();
        CivillaAnalisysResponse response = analisysClient.analyse("immediate_analysis", input);
        float result = response.content.get("immediate_analysis");
        if (result > 0.5)
            return String.format("Subject %s\n Coefficient of crime %s\n Potentially dangerous.\n Use of dominator allowed.", input, result);
        else
            return String.format("Subject %s\n Coefficient of crime %s\n Not dangerous.\n Use of dominator restricted.", input, result);
    }
}