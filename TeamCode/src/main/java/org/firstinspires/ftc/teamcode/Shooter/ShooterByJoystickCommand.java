package org.firstinspires.ftc.teamcode.Shooter;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

public class ShooterByJoystickCommand extends CommandBase {
    ShooterSubsystem shooterSubsystem;
    GamepadEx controller;
    double velocity;
    public ShooterByJoystickCommand(ShooterSubsystem shooterSubsystem, GamepadEx gamepadEx){
        this.shooterSubsystem = shooterSubsystem;
        this.controller = gamepadEx;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        shooterSubsystem.joystickPower(controller);
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }
}
