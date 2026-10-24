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

                c.manualBrakeMode.set(false);
            }
    );

    public static PinpointConfig localizerConfig = new PinpointConfig(
            c -> {
                c.name.set("pinpoint");
                c.xPodOffset.set(-3.009627034345011);
                c.yPodOffset.set(6.822864802803581);
                c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
                c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED
                );
            }
    );

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.14983429492135317);
                Controller secondaryTranslationalForward = Controller.proportional(0.05535976832734348);
                Controller primaryTranslationalLateral = Controller.proportional(0.19710994147833227);
                Controller secondaryTranslationalLateral = Controller.proportional(0.07282685650160606);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.016847608734007882));
                c.brake.set(Controller.proportionalFeedforward(0.0143204674239067));

                c.headingFeedback.set(Controller.proportional(3.854874403289254));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.03799736994024063, 0.007267469974911497));

                c.linearBrakeCoefficients.set(Matrix.diag(0.05378980944995157, 0.0673224375248383));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0011374227856247934, 0.0010648404640034128));

                c.maxAchievableForwardVelocity.set(60.63623527953912);
                c.maxAchievableStrafeVelocity.set(53.69105190622613);
                c.naturalForwardDeceleration.set(42.20143645762868);
                c.naturalStrafeDeceleration.set(67.92943821077255);
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