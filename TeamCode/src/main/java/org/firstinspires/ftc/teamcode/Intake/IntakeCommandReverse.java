package org.firstinspires.ftc.teamcode.Intake;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

public class IntakeCommandReverse extends CommandBase {
    IntakeSubsystem intakeSubsystem;
    private GamepadEx controller;
    public IntakeCommandReverse(IntakeSubsystem intakeSubsystem, GamepadEx mechanismController) {
        this.intakeSubsystem = intakeSubsystem;
        this.controller = mechanismController;
        addRequirements(intakeSubsystem);
    }
    @Override
    public void execute() {
        super.execute();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }
    @Override
    public boolean isFinished() {
        return false;
    }

}
