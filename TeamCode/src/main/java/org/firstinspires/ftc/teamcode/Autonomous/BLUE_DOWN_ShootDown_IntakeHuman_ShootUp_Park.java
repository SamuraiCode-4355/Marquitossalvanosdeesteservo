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

@Autonomous(name = "Disparar Abajo, Human, Disparar Arriba, Aparcarse")
@Configurable // Panels
public class BLUE_DOWN_ShootDown_IntakeHuman_ShootUp_Park extends OpMode {

  private TelemetryManager panelsTelemetry; // Panels Telemetry instance
  public Follower follower; // Pathing follower instance
  private final PoseFactory p = PoseFactory.degrees();
  private int pathState; // Current autonomous path state (state machine)
  private ElapsedTime pathTimer; // Timer for path state machine
  private Paths paths; // Paths defined in the Paths class
  private ShooterSubsystem shooterSubsystem;
  private IntakeSubsystem intakeSubsystem;
  ElapsedTime shootTime;
  ElapsedTime intakeTime;

  @Override
  public void init() {
    panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    shooterSubsystem = new ShooterSubsystem(hardwareMap);
    intakeSubsystem = new IntakeSubsystem(hardwareMap);
    // ...
    panelsTelemetry.debug("Status", "Initialized");
    panelsTelemetry.update(telemetry);

    follower = Constants.create(hardwareMap);
    follower.setPose(p.of(60.000, 9.782, 90.000));

    pathTimer = new ElapsedTime();
    intakeTime = new ElapsedTime();
    shootTime = new ElapsedTime();
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
    public Path line4;
    public Path line5;
    public Path line6;
    public Path line7;

    public Paths(Follower follower) {
      DriveToShoot = line(
        p.of(60.000, 9.782, 0.0),
        p.of(24.283, 48.494, 0.0)
      ).linear(Math.toRadians(90), Math.toRadians(90));

      line2 = line(
        p.of(24.283, 48.494, 0.0),
        p.of(24.283, 107.710, 0.0)
      ).linear(Math.toRadians(90), Math.toRadians(90));

      line3 = line(
        p.of(24.283, 107.710, 0.0),
        p.of(12.900, 107.710, 0.0)
      ).linear(Math.toRadians(180), Math.toRadians(180));

      line4 = line(
        p.of(12.900, 107.710, 0.0),
        p.of(60.000, 107.710, 0.0)
      ).linear(Math.toRadians(180), Math.toRadians(180));

      line5 = line(
        p.of(60.000, 107.710, 0.0),
        p.of(60.000, 134.000, 0.0)
      ).linear(Math.toRadians(-90), Math.toRadians(-90));

      line6 = line(
        p.of(60.000, 134.000, 0.0),
        p.of(60.000, 124.114, 0.0)
      ).linear(Math.toRadians(-90), Math.toRadians(-90));

      line7 = line(
        p.of(60.000, 124.114, 0.0),
        p.of(10.000, 124.114, 0.0)
      ).linear(Math.toRadians(-90), Math.toRadians(-90));
    }
  }

  public int autonomousPathUpdate() {
    switch (pathState) {

      case 0:
        setPathState(1);
        break;
      case 1:
        if (shootTime.milliseconds() < 4000) {
          shooterSubsystem.start();
        }
        else {
          shooterSubsystem.stop();
          setPathState(8);
        }
        break;
      case 2:
        follower.follow(paths.DriveToShoot);
        setPathState(3);
        break;
      case 3:
        if(!follower.isBusy()) {
          setPathState(4);
        }
        break;
      case 4:
        follower.follow(paths.line2);
        setPathState(5);
        break;
      case 5:
        if(!follower.isBusy()) {
          setPathState(6);
        }
        break;
      case 6:
        follower.hold(follower.pose().withHeading(3.142));
        follower.algorithm().reset();
        setPathState(7);
        break;
      case 7:
        if(Math.abs(com.pedropathing.utils.Angle.error(follower.pose().heading(), 3.142)) < 0.01) {
          setPathState(8);
        }
        break;
      case 8:
        follower.follow(paths.line3);
        setPathState(9);
        break;
      case 9:
        if(!follower.isBusy()) {
          setPathState(10);
        }
        break;
      case 10:
        setPathState(11);
        break;
      case 11:
        if (intakeTime.milliseconds() < 4000) {
          intakeSubsystem.start();
        }
        else {
          intakeSubsystem.stop();
          setPathState(12);
        }
        break;
      case 12:
        follower.follow(paths.line4);
        setPathState(13);
        break;
      case 13:
        if(!follower.isBusy()) {
          setPathState(14);
        }
        break;
      case 14:
        follower.hold(follower.pose().withHeading(-1.571));
        follower.algorithm().reset();
        setPathState(15);
        break;
      case 15:
        if(Math.abs(com.pedropathing.utils.Angle.error(follower.pose().heading(), -1.571)) < 0.01) {
          setPathState(16);
        }
        break;
      case 16:
        follower.follow(paths.line5);
        setPathState(17);
        break;
      case 17:
        if(!follower.isBusy()) {
          setPathState(18);
        }
        break;
      case 18:
        setPathState(19);
        break;
      case 19:
        if (shootTime.milliseconds() < 4000) {
          shooterSubsystem.start();
        }
        else {
          shooterSubsystem.stop();
          setPathState(20);
        }
        break;
      case 20:
        follower.follow(paths.line6);
        setPathState(21);
        break;
      case 21:
        if(!follower.isBusy()) {
          setPathState(22);
        }
        break;
      case 22:
        follower.follow(paths.line7);
        setPathState(23);
        break;
      case 23:
        if(!follower.isBusy()) {
          setPathState(24);
        }
        break;
      case 24:
        requestOpModeStop();
        pathState = -1;
        break;
    }
    return pathState;
  }

  public void setPathState(int pState) {
    pathState = pState;
    pathTimer.reset();
    shootTime.reset();
    intakeTime.reset();
  }
}
