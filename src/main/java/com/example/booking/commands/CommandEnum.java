package com.example.booking.commands;

import java.util.function.Supplier;

public enum CommandEnum {
    PREPARELOGIN(PrepareLoginCommand::new),
    LOGIN(LoginCommand::new),
    PREPARESIGNUP(PrepareSignupCommand::new),
    LOGOUT(LogoutCommand::new),
    SIGNUP(SignupCommand::new),
    PREPAREBOOKING(PrepareBookingCommand::new),
    BOOKING(BookingCommand::new),
    VIEWUSERREQUESTS(ViewUserRequestsCommand::new),
    VIEWUSERRESPONSES(ViewUserResponsesCommand::new),
    VIEWREQUESTS(ViewRequestsCommand::new),
    VIEWRESPONSES(ViewResponsesCommand::new),
    VIEWUSERS(ViewUsersCommand::new),
    VIEWSUITES(ViewSuitesCommand::new),
    VIEWSUITECLASSES(ViewSuiteClassesCommand::new),
    CANCELREQUEST(CancelRequestCommand::new),
    STARTANSWERREQUEST(StartAnswerRequestCommand::new),
    PREPAREANSWERREQUEST(PrepareAnswerRequestCommand::new),
    SELECTSUITEFORREQUEST(SelectSuiteForRequestCommand::new);
    private final Command command;

    private CommandEnum(Supplier<Command> command) {
        this.command = command.get();
    }

    public Command getCommand() {
        return command;
    }
}
