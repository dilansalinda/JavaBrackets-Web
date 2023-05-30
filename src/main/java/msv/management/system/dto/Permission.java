package msv.management.system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor

public enum Permission {

    READ("read"),
    WRITE("write"),
    ADD("add");

    @Getter
    private final String value;

}
