package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(
            c -> {
                c.frontLeftName.set("FLMotor");
                c.backLeftName.set("BLMotor");
                c.frontRightName.set("FRMotor");
                c.backRightName.set("BRMotor");

                c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
                c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
                c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
                c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);

                c.manualBrakeMode.set(false);
            }
    );

    public static PinpointConfig localizerConfig = new PinpointConfig(
            c -> {
                c.name.set("pinpoint");
                c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
                c.xPodOffset.set(-3.4165);
                c.yPodOffset.set(3.3400); //2.2420772041861468)
                c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
                c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
                c.globalDistanceUnit.set(DistanceUnit.INCH);
                c.offsetUnits.set(DistanceUnit.INCH);            }
    );

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.14338131171669477);
                Controller secondaryTranslationalForward = Controller.proportional(0.052975563460109065);
                Controller primaryTranslationalLateral = Controller.proportional(0.21077632825938653);
                Controller secondaryTranslationalLateral = Controller.proportional(0.07787622124462533);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.016561099659956238));
                c.brake.set(Controller.proportionalFeedforward(0.014076934710962802));

                c.headingFeedback.set(Controller.proportional(2.494181610297416));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.042495594853843355, 0.005431588919086847));

                c.linearBrakeCoefficients.set(Matrix.diag(0.05419897201621683, 0.04109739274134701));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0012866883617421747, 0.0017357635669297013));

                c.maxAchievableForwardVelocity.set(60.43166225215391);
                c.maxAchievableStrafeVelocity.set(51.431092372206294);
                c.naturalForwardDeceleration.set(39.785956548317486);
                c.naturalStrafeDeceleration.set(58.16597387487336);
            }
    );


    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}