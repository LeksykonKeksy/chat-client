package pl.leksy.krzysztof.chat.client.room.model;

import lombok.Data;

@Data
public class Room {
    private String name;
    private boolean passwordProtected;
    private String password;
    private int slots;
}
