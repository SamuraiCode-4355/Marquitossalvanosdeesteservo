/* ============================================================= *
 *                 Turtle Tracer — Auto-Generated                *
 *                                                               *
 *  Version: 2.5.1.                                              *
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

import org.firstinspires.ftc.teamcode.Chasis.ChasisSusbystem;
import org.firstinspires.ftc.teamcode.ConfigureIMU;
import org.firstinspires.ftc.teamcode.Intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Shooter.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "FLOWER - Disparar, Recoger y Disparar")
@Configurable // Panels
public class Shoot_RecolectFlower_ShootB extends OpMode {

  private TelemetryManager panelsTelemetry; // Panels Telemetry instance
  public Follower follower; // Pathing follower instance
  private final PoseFactory p = PoseFactory.degrees();
  private int pathState; // Current autonomous path state (state machine)
  private ElapsedTime pathTimer; // Timer for path state machine
  private Paths paths; // Paths defined in the Paths class
  ChasisSusbystem chasisSusbystem;
  ConfigureIMU configureIMU;
  ShooterSubsystem shooterSubsystem;
  IntakeSubsystem intakeSubsystem;
  ElapsedTime shootTimer;
  ElapsedTime intakeTimer;

  @Override
  public void init() {
    configureIMU = new ConfigureIMU(hardwareMap);
    chasisSusbystem = new ChasisSusbystem(hardwareMap, configureIMU);
    shooterSubsystem = new ShooterSubsystem(hardwareMap);
    intakeSubsystem = new IntakeSubsystem(hardwareMap);

    panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    // ...
    panelsTelemetry.debug("Status", "Initialized");
    panelsTelemetry.update(telemetry);

    follower = Constants.create(hardwareMap);
    follower.setPose(p.of(60.000, 135.000, -90.000));

    pathTimer = new ElapsedTime();

    shootTimer = new ElapsedTime();
    intakeTimer = new ElapsedTime();
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

    public Paths(Follower follower) {
      DriveToShoot = line(
        p.of(60.000, 135.000, 0.0),
        p.of(60.000, 119.549, 0.0)
      ).linear(Math.toRadians(-90), Math.toRadians(-90));

      line2 = line(
        p.of(60.000, 119.549, 0.0),
        p.of(51.918, 130.815, 0.0)
      ).linear(Math.toRadians(130), Math.toRadians(130));

      line3 = line(
        p.of(51.918, 130.815, 0.0),
        p.of(60.000, 113.740, 0.0)
      ).linear(Math.toRadians(130), Math.toRadians(130));

      line4 = line(
        p.of(60.000, 113.740, 0.0),
        p.of(60.000, 134.000, 0.0)
      ).linear(Math.toRadians(-90), Math.toRadians(-90));
    }
  }

  public int autonomousPathUpdate() {
    switch (pathState) {
      case 0:
        setPathState(1);
        break;
      case 1:
        if (pathTimer.milliseconds() > 6000) {
          setPathState(2);
        }
        break;
      case 2:
        setPathState(3);
        break;
      case 3:
        if (shootTimer.milliseconds() < 4000) {
          chasisSusbystem.setLock(true);
          shooterSubsystem.start();
        }
        else {
          chasisSusbystem.setLock(false);
          shooterSubsystem.stop();
          setPathState(4);
        }
        break;
      case 4:
        follower.follow(paths.DriveToShoot);
        setPathState(5);
        break;
      case 5:
        if (!follower.isBusy()) {
          setPathState(6);
        }
        break;
      case 6:
        follower.hold(follower.pose().withHeading(2.269));
        follower.algorithm().reset();
        setPathState(7);
        break;
      case 7:
        if (!follower.isBusy()) {
          setPathState(8);
        }
        break;
      case 8:
        follower.follow(paths.line2);
        setPathState(9);
        break;
      case 9:
        if (!follower.isBusy()) {
          setPathState(10);
        }
        break;
      case 10:
        setPathState(11);
        break;
      case 11:
        if (intakeTimer.milliseconds() < 5000) {
          intakeSubsystem.start();
        }
        else {
          intakeSubsystem.stop();
          setPathState(12);
        }
        break;
      case 12:
        follower.follow(paths.line3);
        setPathState(13);
        break;
      case 13:
        if (!follower.isBusy()) {
          setPathState(14);
        }
        break;
      case 14:
        follower.hold(follower.pose().withHeading(-1.571));
        follower.algorithm().reset();
        setPathState(15);
        break;
      case 15:
        if (!follower.isBusy()) {
          setPathState(16);
        }
        break;
      case 16:
        follower.follow(paths.line4);
        setPathState(17);
        break;
      case 17:
        if (!follower.isBusy()) {
          setPathState(18);
        }
        break;
      case 18:
        setPathState(19);
        break;
      case 19:
        if (shootTimer.milliseconds() < 4000) {
          chasisSusbystem.setLock(true);
          shooterSubsystem.start();
        }
        else {
          chasisSusbystem.setLock(false);
          shooterSubsystem.stop();
          setPathState(20);
        }
        break;
      case 20:
        requestOpModeStop();
        pathState = -1;
        break;
    }
    return pathState;
  }

  public void setPathState(int pState) {
    pathState = pState;

    intakeTimer.reset();
    shootTimer.reset();
    pathTimer.reset();
  }
}
