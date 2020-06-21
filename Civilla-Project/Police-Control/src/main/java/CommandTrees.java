import java.util.ArrayList;

public class CommandTrees {
    static BotCommand basicTree =
            new BotCommand("base","Hello there, citizen! It is civilla system. What do you want to do?", new GoToLabel(), new String[]{"Edit my data", "View my data", "About us"}, new BotCommand[]{
                    new BotCommand("Edit my data", "Please, input your first name", new PutFnameToDatabase(), new String[]{"back"}, new BotCommand[]{
                            new BotCommand("next", "Please, input your second name", new PutSnameToDatabase(), new String[]{"back"}, new BotCommand[]{
                                    new BotCommand("next", "Do you like beer? Answer Yes or No.", new PutBeerToDatabase(), new String[]{"Yes", "No", "back"}, new BotCommand[]{
                                            new BotCommand("next", "Is your data correct? Answer Yes or No.", new IsItCorrect(), new String[]{"Yes", "No", "back"}, new BotCommand[]{})
                                    })
                            })
                    }),
                    new BotCommand("View my data", "What do you want to see?", new ShowProfileData(), new String[]{"Psychopass", "Profile data", "back"}, new BotCommand[]{}),
                    new BotCommand("imapolice", "It is an admin panel. Which access do you want to have?", new AdminProfileStatus(), new String[]{"Citizen", "Policeman", "back"}, new BotCommand[]{}),
                    new BotCommand("About us", "Demo version of civilla system.\nHelps you to be safe and proud of your government.", null, new String[]{"back"}, new BotCommand[]{})
            });

    static BotCommand policeTree =
            new BotCommand("base","Hello there, citizen! It is civilla system. What do you want to do?", new GoToLabel(), new String[]{"Edit my data", "View my data", "About us"}, new BotCommand[]{
                    new BotCommand("Edit my data", "Please, input your first name", new PutFnameToDatabase(), new String[]{"back"}, new BotCommand[]{
                            new BotCommand("next", "Please, input your second name", new PutSnameToDatabase(), new String[]{"back"}, new BotCommand[]{
                                    new BotCommand("next", "Do you like beer? Answer Yes or No.", new PutBeerToDatabase(), new String[]{"Yes", "No", "back"}, new BotCommand[]{
                                            new BotCommand("next", "Is your data correct? Answer Yes or No.", new IsItCorrect(), new String[]{"Yes", "No", "back"}, new BotCommand[]{})
                                    })
                            })
                    }),
                    new BotCommand("View my data", "What do you want to see?", new ShowProfileData(), new String[]{"Psychopass", "Profile data", "back"}, new BotCommand[]{}),
                    new BotCommand("imapolice", "It is an admin panel. Which access do you want to have?", new AdminProfileStatus(), new String[]{"Citizen", "Policeman", "back"}, new BotCommand[]{}),
                    new BotCommand("About us", "Demo version of civilla system.\nHelps you to be safe and proud of your government.", null, new String[]{"back"}, new BotCommand[]{})
            });

}
