package org.firstinspires.ftc.teamcode.Shooter.Servo;

import com.seattlesolvers.solverslib.command.CommandBase;

public class ShooterServoCommand extends CommandBase {
    private final ShooterServoSubsystem servoSubsystem;
    public ShooterServoCommand(ShooterServoSubsystem servoSubsystem){
        this.servoSubsystem = servoSubsystem;
        addRequirements(servoSubsystem);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        super.execute();
        servoSubsystem.start();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        servoSubsystem.stop();
    }
}
