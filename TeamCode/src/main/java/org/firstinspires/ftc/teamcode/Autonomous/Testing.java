/* ============================================================= *
 *                 Turtle Tracer — Auto-Generated                *
 *                                                               *
 *  Version: 2.6.0.                                              *
 *  Copyright (c) 2026 Matthew Allen                             *
 *                                                               *
 *  THIS FILE IS AUTO-GENERATED — DO NOT EDIT MANUALLY.          *
 *  Changes will be overwritten when regenerated.                *
 * ============================================================= */

package org.firstinspires.ftc.teamcode.Autonomous;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;
import static com.pedropathing.api.Paths.path;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.pedro.Drawing;

@Autonomous(name = "Testingggg!")
@Configurable // Panels
public class Testing extends OpMode {

  private TelemetryManager panelsTelemetry; // Panels Telemetry instance
  public Follower follower; // Pathing follower instance
  private final PoseFactory p = PoseFactory.degrees();
  private int pathState; // Current autonomous path state (state machine)
  private ElapsedTime pathTimer; // Timer for path state machine
  private Paths paths; // Paths defined in the Paths class

  @Override
  public void init() {
    panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    // ...
    panelsTelemetry.debug("Status", "Initialized");
    panelsTelemetry.update(telemetry);

    follower = Constants.create(hardwareMap);
    follower.setPose(p.of(60.000, 13.061, 90.000));

    pathTimer = new ElapsedTime();
    paths = new Paths(follower); // Build paths
  }

  @Override
  public void loop() {
    follower.update(); // Update follower
    pathState = autonomousPathUpdate(); // Update autonomous state machine

    Drawing.drawRobot(follower.pose());   // usa el heading del follower, no el IMU
    Drawing.sendPacket();

    // Log values to Panels and Driver Station
    panelsTelemetry.debug("Path State", pathState);
    panelsTelemetry.debug("X", follower.pose().x());
    panelsTelemetry.debug("Y", follower.pose().y());
    panelsTelemetry.debug("Heading", follower.pose().heading());
    panelsTelemetry.update(telemetry);
  }

  public static class Paths {

    private static final PoseFactory p = PoseFactory.degrees();
    public Path DriveToShoot;

    public Paths(Follower follower) {
      DriveToShoot = line(
        p.of(60.000, 13.061, 0.0),
        p.of(60.000, 37.407, 0.0)
      ).linear(Math.toRadians(90), Math.toRadians(90));
    }
  }

  public int autonomousPathUpdate() {
    switch (pathState) {
      case 0:
        follower.follow(paths.DriveToShoot);
        setPathState(1);
        break;
      case 1:
        if (!follower.isBusy()) {
          setPathState(2);
        }
        break;
      case 2:
        follower.hold(follower.pose().withHeading(3.142));
        follower.algorithm().reset();
        setPathState(3);
        break;
      case 3:
        if (
          Math.abs(
            com.pedropathing.utils.Angle.error(follower.pose().heading(), 3.142)
          ) < 0.01
        ) {
          setPathState(4);
        }
        break;
      case 4:
        follower.hold(follower.pose().withHeading(0.000));
        follower.algorithm().reset();
        setPathState(5);
        break;
      case 5:
        if (
          Math.abs(
            com.pedropathing.utils.Angle.error(follower.pose().heading(), 0.000)
          ) < 0.01
        ) {
          setPathState(6);
        }
        break;
      case 6:
        requestOpModeStop();
        pathState = -1;
        break;
    }
    return pathState;
  }

  public void setPathState(int pState) {
    pathState = pState;
    pathTimer.reset();
  }
}
