/* ============================================================= *
 *                 Turtle Tracer — Auto-Generated                *
 *                                                               *
 *  Version: 2.5.0.                                              *
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

@Autonomous(name = "FLOWER - Disparar y Recolectar")
@Configurable // Panels
public class Shoot_RecolectFlowerA extends OpMode {

  private TelemetryManager panelsTelemetry; // Panels Telemetry instance
  public Follower follower; // Pathing follower instance
  private final PoseFactory p = PoseFactory.degrees();
  private int pathState; // Current autonomous path state (state machine)
  private ElapsedTime pathTimer; // Timer for path state machine
  private ElapsedTime shootTimer;
  private Paths paths; // Paths defined in the Paths class
  ChasisSusbystem chasisSusbystem;
  ShooterSubsystem shooterSubsystem;
  IntakeSubsystem intakeSubsystem;
  ConfigureIMU configureIMU;

  @Override
  public void init() {
    panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    configureIMU = new ConfigureIMU(hardwareMap);
    chasisSusbystem = new ChasisSusbystem(hardwareMap,configureIMU);
    shooterSubsystem = new ShooterSubsystem(hardwareMap);
    intakeSubsystem = new IntakeSubsystem(hardwareMap);
    // ...
    panelsTelemetry.debug("Status", "Initialized");
    panelsTelemetry.update(telemetry);

    follower = Constants.create(hardwareMap);
    follower.setPose(p.of(60.000, 9.527, 90.000));

    pathTimer = new ElapsedTime();
    shootTimer = new ElapsedTime();
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
    public Path line1;
    public Path line2;

    public Paths(Follower follower) {
      line1 = curve(
        p.of(60.000, 9.527, 0.0),
        p.of(18.523, 32.511, 0.0),
        p.of(17.443, 52.000, 0.0)
      ).linear(Math.toRadians(90), Math.toRadians(180));

      line2 = line(p.of(17.443, 52.000, 0.0), p.of(10.170, 52.000, 0.0)).linear(
        Math.toRadians(180),
        Math.toRadians(180)
      );
    }
  }

  public int autonomousPathUpdate() {
    switch (pathState) {
      case 0:
        setPathState(1);
        break;
      case 1:
        if (shootTimer.milliseconds() < 4000) {
          chasisSusbystem.setLock(true);
          shooterSubsystem.start();
        }
        else {
          chasisSusbystem.setLock(false);
          shooterSubsystem.stop();
          setPathState(2);
        }
        break;
      case 2:
        follower.follow(paths.line1);
        setPathState(3);
        break;
      case 3:
        if (!follower.isBusy()) {
          setPathState(4);
        }
        break;
      case 4:
        follower.follow(paths.line2);
        intakeSubsystem.start();
        setPathState(5);
        break;
      case 5:
        if (!follower.isBusy()) {
          intakeSubsystem.stop();
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
    shootTimer.reset();
  }
}
