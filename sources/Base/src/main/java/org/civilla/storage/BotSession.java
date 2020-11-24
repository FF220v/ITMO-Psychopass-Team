package org.civilla.storage;

public class BotSession{
    public String chat_id = null;
    public String msgId = null;
    public String firstName = null;
    public String lastName = null;
    public Boolean likesBeer = null;
    public String firstNameBuf = null;
    public String lastNameBuf = null;
    public Boolean likesBeerBuf = null;
    public Boolean isPersonalDataFilled = false;
    public Boolean isPoliceman = false;
    public Boolean isAdmin = false;

    public BotSession(){}

    BotSession(String chat_id,
               String msgId,
               String firstName,
               String lastName,
               Boolean likesBeer,
               String firstNameBuf,
               String lastNameBuf,
               Boolean likesBeerBuf,
               Boolean isPersonalDataFilled,
               Boolean isPoliceman,
               Boolean isAdmin
    ){
        this.chat_id = chat_id;
        this.msgId = msgId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.likesBeer = likesBeer;
        this.firstNameBuf = firstNameBuf;
        this.lastNameBuf = lastNameBuf;
        this.likesBeerBuf = likesBeerBuf;
        this.isPersonalDataFilled = isPersonalDataFilled;
        this.isPoliceman = isPoliceman;
        this.isAdmin = isAdmin;
    }
}