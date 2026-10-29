package org.firstinspires.ftc.teamcode.Intake.Servo;

import com.seattlesolvers.solverslib.command.CommandBase;

public class IntakeCommandSubsystem extends CommandBase {
    private final IntakeServoSubsystem servoSubsystem;
    public IntakeCommandSubsystem(IntakeServoSubsystem servoSubsystem){
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
        servoSubsystem.right();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        servoSubsystem.left();
    }
}
