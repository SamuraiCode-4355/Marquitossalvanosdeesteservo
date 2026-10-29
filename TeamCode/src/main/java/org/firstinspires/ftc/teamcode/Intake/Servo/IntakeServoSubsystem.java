package org.firstinspires.ftc.teamcode.Intake.Servo;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;
import com.seattlesolvers.solverslib.hardware.servos.ServoExGroup;

public class IntakeServoSubsystem extends SubsystemBase {
    private ServoEx servoLeft;
    private ServoEx servoRight;
    private ServoExGroup servoExGroup;

    public IntakeServoSubsystem(HardwareMap hwMap){
        //servoLeft = new ServoEx(hwMap, "IntakeLeft");
        //servoRight = new ServoEx(hwMap, "IntakeRight");

        servoRight.setInverted(true);
        servoLeft.setInverted(false);
        servoExGroup = new ServoExGroup(servoLeft, servoRight);
    }
    public void left(){
        servoExGroup.set(0);
    }
    public void right(){
        servoExGroup.set(0.7);
    }
    public double servoLeftPosition(){
        return servoLeft.get();
    }
    public double servoRightPosition(){
        return servoRight.get();
    }

    @Override
    public void periodic() {
        super.periodic();
    }
}
