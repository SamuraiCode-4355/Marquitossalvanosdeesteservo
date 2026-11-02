package org.firstinspires.ftc.teamcode.Shooter;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

public class ShooterSubsystem extends SubsystemBase {
    private final MotorEx shooterMotor;
    private final PIDFController pidController;
    private double targetVelocity = 0;
    private GamepadEx controller;

    public ShooterSubsystem(HardwareMap hwMap) {
        shooterMotor = new MotorEx(hwMap, "ShooterMotor");
        shooterMotor.setInverted(false);
        shooterMotor.setRunMode(Motor.RunMode.RawPower);

        pidController = new PIDFController(ShooterConfig.kP, ShooterConfig.kI, ShooterConfig.kD, ShooterConfig.kF);
        pidController.setTolerance(30);
    }

    public void setTargetVelocity(double tps) {
        targetVelocity = tps;
        pidController.setSetPoint(tps);
    }

    public void start()   {
        setTargetVelocity(ShooterConfig.targetVelocity);
    }
    public void stop()    {
        setTargetVelocity(0);
    }

    public double getVelocity() { return shooterMotor.getVelocity(); }
    public boolean atTarget()   { return pidController.atSetPoint(); }

    @Override
    public void periodic() {
        if (targetVelocity == 0) {
            shooterMotor.set(0);
            return;
        }
        double out = pidController.calculate(getVelocity());
        shooterMotor.set(Math.max(-1, Math.min(1, out)));
    }
}
