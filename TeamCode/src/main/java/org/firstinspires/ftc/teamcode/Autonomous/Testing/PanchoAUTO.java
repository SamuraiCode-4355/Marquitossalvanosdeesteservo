/* ============================================================= *
 *                 Turtle Tracer — Auto-Generated                *
 *                                                               *
 *  Version: 2.4.0.                                              *
 *  Copyright (c) 2026 Matthew Allen                             *
 *                                                               *
 *  THIS FILE IS AUTO-GENERATED — DO NOT EDIT MANUALLY.          *
 *  Changes will be overwritten when regenerated.                *
 * ============================================================= */

package org.firstinspires.ftc.teamcode.Autonomous.Testing;

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

@Autonomous(name = "xd67")
@Configurable // Panels
public class PanchoAUTO extends OpMode {

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
    follower.setPose(p.of(11.570, 28.736, 0.000));

    pathTimer = new ElapsedTime();
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
    public Path ac;
    public Path id;
    public Path od;
    public Path es;
    public Path line5;
    public Path line6;
    public Path line7;
    public Path line8;

    public Paths(Follower follower) {
      ac = line(p.of(11.570, 28.736, 0.0), p.of(60.181, 28.736, 0.0)).linear(
        Math.toRadians(0),
        Math.toRadians(90)
      );

      id = line(p.of(60.181, 28.736, 0.0), p.of(13.793, 47.625, 0.0)).linear(
        Math.toRadians(90),
        Math.toRadians(180)
      );

      od = line(p.of(13.793, 47.625, 0.0), p.of(13.793, 105.125, 0.0)).linear(
        Math.toRadians(180),
        Math.toRadians(180)
      );

      es = line(p.of(13.793, 105.125, 0.0), p.of(60.181, 106.792, 0.0)).linear(
        Math.toRadians(90),
        Math.toRadians(-90)
      );

      line5 = line(
        p.of(60.181, 106.792, 0.0),
        p.of(49.000, 130.125, 0.0)
      ).linear(Math.toRadians(-90), Math.toRadians(90));

      line6 = line(
        p.of(49.000, 130.125, 0.0),
        p.of(24.348, 130.403, 0.0)
      ).linear(Math.toRadians(90), Math.toRadians(180));

      line7 = line(
        p.of(24.348, 130.403, 0.0),
        p.of(22.126, 22.625, 0.0)
      ).linear(Math.toRadians(180), Math.toRadians(-90));

      line8 = line(p.of(22.126, 22.625, 0.0), p.of(59.626, 22.903, 0.0)).linear(
        Math.toRadians(-90),
        Math.toRadians(90)
      );
    }
  }

  public int autonomousPathUpdate() {
    switch (pathState) {
      case 0:
        follower.follow(paths.ac);
        setPathState(1);
        break;
      case 1:
        if (!follower.isBusy()) {
          setPathState(2);
        }
        break;
      case 2:
        setPathState(3);
        break;
      case 3:
        if (pathTimer.milliseconds() > 1500) {
          setPathState(4);
        }
        break;
      case 4:
        follower.follow(paths.id);
        setPathState(5);
        break;
      case 5:
        if (!follower.isBusy()) {
          setPathState(6);
        }
        break;
      case 6:
        setPathState(7);
        break;
      case 7:
        if (pathTimer.milliseconds() > 2000) {
          setPathState(8);
        }
        break;
      case 8:
        follower.follow(paths.od);
        setPathState(9);
        break;
      case 9:
        if (!follower.isBusy()) {
          setPathState(10);
        }
        break;
      case 10:
        follower.follow(paths.es);
        setPathState(11);
        break;
      case 11:
        if (!follower.isBusy()) {
          setPathState(12);
        }
        break;
      case 12:
        setPathState(13);
        break;
      case 13:
        if (pathTimer.milliseconds() > 1200) {
          setPathState(14);
        }
        break;
      case 14:
        follower.follow(paths.line5);
        setPathState(15);
        break;
      case 15:
        if (!follower.isBusy()) {
          setPathState(16);
        }
        break;
      case 16:
        setPathState(17);
        break;
      case 17:
        if (pathTimer.milliseconds() > 1350) {
          setPathState(18);
        }
        break;
      case 18:
        follower.follow(paths.line6);
        setPathState(19);
        break;
      case 19:
        if (!follower.isBusy()) {
          setPathState(20);
        }
        break;
      case 20:
        follower.follow(paths.line7);
        setPathState(21);
        break;
      case 21:
        if (!follower.isBusy()) {
          setPathState(22);
        }
        break;
      case 22:
        follower.follow(paths.line8);
        setPathState(23);
        break;
      case 23:
        if (!follower.isBusy()) {
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
  }
}
