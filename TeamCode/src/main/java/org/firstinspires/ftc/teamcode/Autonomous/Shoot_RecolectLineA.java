
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
import org.firstinspires.ftc.teamcode.pedro.Drawing;

@Autonomous(name = "GARDEN - Disparar, Recoger A")
@Configurable // Panels
public class Shoot_RecolectLineA extends OpMode {

  private TelemetryManager panelsTelemetry; // Panels Telemetry instance
  public Follower follower; // Pathing follower instance
  private final PoseFactory p = PoseFactory.degrees();
  private int pathState; // Current autonomous path state (state machine)
  private ElapsedTime pathTimer; // Timer for path state machine
  private Paths paths; // Paths defined in the Paths class
  ConfigureIMU configureIMU;
  ChasisSusbystem chasisSusbystem;
  ShooterSubsystem shooterSubsystem;
  IntakeSubsystem intakeSubsystem;
  ElapsedTime shootTimer;

  @Override
  public void init() {
    panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
    // ...
    panelsTelemetry.debug("Status", "Initialized");
    panelsTelemetry.update(telemetry);

    follower = Constants.create(hardwareMap);
    follower.setPose(p.of(60.000, 9.640, 90.000));

    configureIMU = new ConfigureIMU(hardwareMap);
    chasisSusbystem = new ChasisSusbystem(hardwareMap, configureIMU);
    shooterSubsystem = new ShooterSubsystem(hardwareMap);
    intakeSubsystem = new IntakeSubsystem(hardwareMap);

    pathTimer = new ElapsedTime();
    shootTimer = new ElapsedTime();

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
    public Path line1;
    public Path line2;
    public Path line3;

    public Paths(Follower follower) {
      line1 = line(p.of(60.000, 9.640, 0.0), p.of(25.217, 34.112, 0.0)).linear(
        Math.toRadians(90),
        Math.toRadians(90)
      );

      line2 = line(p.of(25.217, 34.112, 0.0), p.of(10.000, 14.235, 0.0)).linear(
        Math.toRadians(-90),
        Math.toRadians(-90)
      );

      line3 = line(p.of(10.000, 14.235, 0.0), p.of(10.000, 10.000, 0.0)).linear(
        Math.toRadians(-90),
        Math.toRadians(-90)
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
        follower.hold(follower.pose().withHeading(0.000));
        follower.algorithm().reset();
        setPathState(5);
        break;
      case 5:
        if (!follower.isBusy()) {
          setPathState(6);
        }
        break;
      case 6:
        follower.hold(follower.pose().withHeading(-1.571));
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
        follower.follow(paths.line3);
        intakeSubsystem.start();
        setPathState(11);
        break;
      case 11:
        if (!follower.isBusy()) {
          intakeSubsystem.stop();
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
    shootTimer.reset();
    pathTimer.reset();
  }
}
