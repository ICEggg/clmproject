package impl;

import service.IMessageService;

public class MessageService implements IMessageService {
    public void getMessage() {
        System.out.println("this is message");
    }
}
