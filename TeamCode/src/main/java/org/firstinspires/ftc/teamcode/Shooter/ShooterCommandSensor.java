package org.firstinspires.ftc.teamcode.Shooter;

import com.seattlesolvers.solverslib.command.CommandBase;

public class ShooterCommandSensor extends CommandBase {
    ShooterSubsystem shooterSubsystem;
    public ShooterCommandSensor(ShooterSubsystem shooterSubsystem){
        this.shooterSubsystem = shooterSubsystem;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        super.execute();
        shooterSubsystem.startWithSensor();
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
