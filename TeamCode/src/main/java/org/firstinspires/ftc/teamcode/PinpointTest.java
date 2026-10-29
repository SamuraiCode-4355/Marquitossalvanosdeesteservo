package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Prueba Pinpoint")
public class PinpointTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        GoBildaPinpointDriver pp = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pp.setOffsets(-3.0096, 6.8229, DistanceUnit.INCH); // pon aquí tus offsets
        pp.setEncoderDirections(
                GoBildaPinpointDriver.EncoderDirection.FORWARD,
                GoBildaPinpointDriver.EncoderDirection.REVERSED);
        pp.resetPosAndIMU();

        waitForStart();
        while (opModeIsActive()) {
            pp.update();
            telemetry.addData("X (in)", pp.getPosX(DistanceUnit.INCH));
            telemetry.addData("Y (in)", pp.getPosY(DistanceUnit.INCH));
            telemetry.addData("Heading (deg)", pp.getHeading(AngleUnit.DEGREES));
            telemetry.addData("Enc X", pp.getEncoderX());
            telemetry.addData("Enc Y", pp.getEncoderY());
            telemetry.update();
        }
    }
}