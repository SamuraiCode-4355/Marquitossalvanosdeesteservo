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
            }
    );

    public static PinpointConfig localizerConfig = new PinpointConfig(
            c -> {
                c.name.set("pinpoint");
                c.xPodOffset.set(-1.1368957279235359);
                c.yPodOffset.set(7.021429106945128);
                c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
                c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
            }
    );

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.13663898587905407);
                Controller secondaryTranslationalForward = Controller.proportional(0.050484454221365244);
                Controller primaryTranslationalLateral = Controller.proportional(0.1883108681156294);
                Controller secondaryTranslationalLateral = Controller.proportional(0.06957583400965779);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.015753348335112615));
                c.brake.set(Controller.proportionalFeedforward(0.013390346084845722));

                c.headingFeedback.set(Controller.proportional(2.4713477739522087));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04118182177376267, 0.005576196457842196));

                c.linearBrakeCoefficients.set(Matrix.diag(0.04051680815215274, 0.03250908455642893));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.001869846724630056, 0.0019981669894143135));

                c.maxAchievableForwardVelocity.set(62.55651510190489);
                c.maxAchievableStrafeVelocity.set(53.56332045443531);
                c.naturalForwardDeceleration.set(37.58993972104029);
                c.naturalStrafeDeceleration.set(52.730395147433164);
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