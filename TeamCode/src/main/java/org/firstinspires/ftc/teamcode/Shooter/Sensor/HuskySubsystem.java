package org.firstinspires.ftc.teamcode.Shooter.Sensor;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HuskySubsystem {
    private HuskyLens huskyLens;
    public HuskySubsystem(HardwareMap hwMap){
        huskyLens = hwMap.get(HuskyLens.class, "HL");
    }
    public boolean getColor(int id) {
        HuskyLens.Block[] blocks = huskyLens.blocks(id);

        return blocks.length > 0;
    }
}
