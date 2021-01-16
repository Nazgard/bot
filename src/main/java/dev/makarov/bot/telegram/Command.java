package dev.makarov.bot.telegram;

public enum Command {

    DD("/dd"),
    RSS("/rss");

    private final String code;

    Command(String code) {
        this.code = code;
    }

    public static Command parseCommand(String input) throws NotCommandException {
        for (Command command : Command.values()) {
            if (command.getCode().equals(input)) {
                return command;
            }
        }
        throw new NotCommandException();
    }

    public String getCode() {
        return code;
    }
}
