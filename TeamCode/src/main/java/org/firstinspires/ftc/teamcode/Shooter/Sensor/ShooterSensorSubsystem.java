package org.firstinspires.ftc.teamcode.Shooter.Sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.SensorColor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ShooterSensorSubsystem extends SubsystemBase {
    private SensorColor colorSensor;
    public ShooterSensorSubsystem(HardwareMap hwMap, Telemetry telemetry){
        //colorSensor = new SensorColor(hwMap, "CSL");
    }
    public int alpha(){
        return colorSensor.alpha();
    }
    public int blue(){
        return colorSensor.blue();
    }
    public int green(){
        return colorSensor.green();
    }
    public int red(){
        return colorSensor.red();
    }
    public int[] getARGB() {
        return new int[]{alpha(), red(), green(), blue()};
    }
    @Override
    public void periodic() {
    }
}