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

import org.firstinspires.ftc.teamcode.Intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Shooter.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(
  name = "Disparar Abajo, Human, Aparcarse",
  group = "Autonomous"
)
@Configurable // Panels
public class BLUE_DOWN_ShootDown_IntakeHuman_Park extends OpMode {

  private TelemetryManager panelsTelemetry; // Panels Telemetry instance
  public Follower follower; // Pathing follower instance
  private final PoseFactory p = PoseFactory.degrees();
  private int pathState; // Current autonomous path state (state machine)
  private ElapsedTime pathTimer; // Timer for path state machine
  private Paths paths; // Paths defined in the Paths class
  ShooterSubsystem ShooterSubsistem;
IntakeSubsystem IntakeSubsistem;
ElapsedTime ShootTime;
ElapsedTime IntakeTime;
  @Override
  public void init() {
    panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    ShooterSubsistem = new ShooterSubsystem(hardwareMap);
    IntakeSubsistem = new IntakeSubsystem(hardwareMap);
    //  ...
    panelsTelemetry.debug("Status", "Initialized");
    panelsTelemetry.update(telemetry);

    follower = Constants.create(hardwareMap);
    follower.setPose(p.of(60.000, 9.561, 90.000));

    pathTimer = new ElapsedTime();
    ShootTime = new ElapsedTime();
    IntakeTime = new ElapsedTime();
    paths = new Paths(follower); // Build paths
  }

  @Override
  public void loop() {
    follower.update(); // Update follower
    pathState = autonomousPathUpdate(); // Update autonomous state machine

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
    public Path line2;
    public Path line3;

    public Paths(Follower follower) {
      DriveToShoot = curve(
        p.of(60.000, 9.561, 0.0),
        p.of(20.200, 43.757, 0.0),
        p.of(19.618, 106.935, 0.0)
      ).linear(Math.toRadians(90), Math.toRadians(90));

      line2 = line(
        p.of(19.618, 106.935, 0.0),
        p.of(13.274, 106.935, 0.0)
      ).linear(Math.toRadians(180), Math.toRadians(180));

      line3 = line(
        p.of(13.274, 106.935, 0.0),
        p.of(13.274, 125.545, 0.0)
      ).linear(Math.toRadians(180), Math.toRadians(180));
    }
  }

  public int autonomousPathUpdate() {
    switch (pathState) {
      case 0:
        setPathState(1);
        break;
      case 1:
        if (ShootTime.milliseconds() < 4000) {
          ShooterSubsistem.start();
        }
        else {
          ShooterSubsistem.stop();
          setPathState(2);
        }
        break;
      case 2:
        follower.follow(paths.DriveToShoot);
        setPathState(3);
        break;
      case 3:
        if (!follower.isBusy()) {
          setPathState(4);
        }
        break;
      case 4:
        follower.hold(follower.pose().withHeading(3.142));
        follower.algorithm().reset();
        setPathState(5);
        break;
      case 5:
        if (
          Math.abs(
            com.pedropathing.utils.Angle.error(follower.pose().heading(), 3.142)
          ) < 0.01
        ) {
          setPathState(6);
        }
        break;
      case 6:
        follower.follow(paths.line2);
        setPathState(7);
        break;
      case 7:
        if (!follower.isBusy()) {
          setPathState(8);
        }
        break;
      case 8:
        setPathState(9);
        break;
      case 9:
        if (IntakeTime.milliseconds() < 4000) {
          IntakeSubsistem.start();
        }
        else {
          IntakeSubsistem.stop();
          setPathState(10);
        }
        break;
      case 10:
        follower.follow(paths.line3);
        setPathState(11);
        break;
      case 11:
        if (!follower.isBusy()) {
          setPathState(12);
        }
        break;
      case 12:
        requestOpModeStop();
        pathState = -1;
        break;
    }
    return pathState;
  }

  public void setPathState(int pState) {
    pathState = pState;
    pathTimer.reset();
    ShootTime.reset();
    IntakeTime.reset();
  }
}
