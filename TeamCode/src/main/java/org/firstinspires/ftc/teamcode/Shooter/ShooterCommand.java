package org.firstinspires.ftc.teamcode.Shooter;

import com.seattlesolvers.solverslib.command.CommandBase;

public class ShooterCommand extends CommandBase {
    ShooterSubsystem shooterSubsystem;
    double velocity;
    public ShooterCommand(ShooterSubsystem shooterSubsystem, double Velocity){
        this.shooterSubsystem = shooterSubsystem;
        this.velocity = Velocity;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
        shooterSubsystem.setTargetVelocity(velocity);
        super.initialize();
    }

    @Override
    public void execute() {
        super.execute();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stop();
    }
}
