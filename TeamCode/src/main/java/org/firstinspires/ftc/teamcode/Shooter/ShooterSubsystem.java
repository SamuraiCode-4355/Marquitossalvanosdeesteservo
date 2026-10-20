package org.firstinspires.ftc.teamcode.Shooter;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

public class ShooterSubsystem extends SubsystemBase {
    private MotorEx shooterMotor;
    public ShooterSubsystem(HardwareMap hwMap){
        shooterMotor = new MotorEx(hwMap, "ShooterMotor");
        shooterMotor.setInverted(true);
    }
    public void start(){
        shooterMotor.set(0.8);
    }
    public void stop(){
        shooterMotor.set(0);
    }
    public double RPM(){
        return shooterMotor.getVelocity();
    }
    public double Power(){
        return shooterMotor.getRawPower();
    }
    public double Raw(){
        return shooterMotor.encoder.getRevolutions();
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}
