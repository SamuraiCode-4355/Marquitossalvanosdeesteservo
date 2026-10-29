package org.firstinspires.ftc.teamcode.Shooter;

import com.seattlesolvers.solverslib.command.CommandBase;

public class ShooterCommand extends CommandBase {
    ShooterSubsystem shooterSubsystem;
    public ShooterCommand(ShooterSubsystem shooterSubsystem){
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
        shooterSubsystem.start();
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        shooterSubsystem.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
