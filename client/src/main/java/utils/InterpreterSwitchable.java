package utils;

import clientInterpreter.ClientCommandInterpreter;

import java.io.IOException;

public interface InterpreterSwitchable {
    void switchInterpreter(ClientCommandInterpreter interpreter);
}
