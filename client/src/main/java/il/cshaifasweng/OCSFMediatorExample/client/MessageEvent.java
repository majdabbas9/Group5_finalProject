package il.cshaifasweng.OCSFMediatorExample.client;

import aidClasses.Message;
import aidClasses.Warning;

public class MessageEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public MessageEvent(Message message) {
        this.message = message;
    }
}
