package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Chasis.ChasisCommand;
import org.firstinspires.ftc.teamcode.Chasis.ChasisSusbystem;
import org.firstinspires.ftc.teamcode.Intake.IntakeCommand;
import org.firstinspires.ftc.teamcode.Intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Shooter.Sensor.HuskySubsystem;
import org.firstinspires.ftc.teamcode.Shooter.Sensor.ShooterSensorSubsystem;
import org.firstinspires.ftc.teamcode.Shooter.Servo.ShooterServoCommand;
import org.firstinspires.ftc.teamcode.Shooter.Servo.ShooterServoSubsystem;
import org.firstinspires.ftc.teamcode.Shooter.ShooterCommand;
import org.firstinspires.ftc.teamcode.Shooter.ShooterCommandSensor;
import org.firstinspires.ftc.teamcode.Shooter.ShooterSubsystem;

import java.util.Arrays;

@TeleOp
public class MainController extends CommandOpMode {

    //----------------------------/Variables/----------------------

    GamepadEx driverController;
    GamepadEx mechanismController;

    ChasisSusbystem chasisSusbystem;

    IntakeSubsystem intakeSubsystem;
    ShooterSubsystem shooterSubsystem;
    ShooterServoSubsystem shooterServoSubsystem;
    ShooterSensorSubsystem shooterSensor;

    HuskySubsystem huskySubsystem;

    ConfigureIMU configureIMU;

    @Override
    public void initialize() {

        //----------------------------/Controllers/----------------------

        driverController = new GamepadEx(gamepad1);
        mechanismController = new GamepadEx(gamepad2);

        //----------------------------/Subsystems/----------------------

        configureIMU = new ConfigureIMU(hardwareMap);
        huskySubsystem = new HuskySubsystem(hardwareMap);
        chasisSusbystem = new ChasisSusbystem(hardwareMap, configureIMU);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap, huskySubsystem);
        shooterServoSubsystem = new ShooterServoSubsystem(hardwareMap);

        shooterSensor = new ShooterSensorSubsystem(hardwareMap, telemetry);
        telemetry.update();

        //----------------------------/Commands-Executors/----------------------

        new Trigger(() ->
                driverController.wasJustPressed(GamepadKeys.Button.X)
        ).whenActive(new InstantCommand(() -> configureIMU.resetImu()));

        new Trigger(() ->
                driverController.wasJustPressed(GamepadKeys.Button.Y)
        ).whenActive(new InstantCommand(() -> {
            // Alternamos el estado del bloqueo
            boolean newState = !chasisSusbystem.isLockEnabled();
            chasisSusbystem.setLock(newState);
        }));

        chasisSusbystem.setDefaultCommand(
                new ChasisCommand(chasisSusbystem, driverController));

        new Trigger(() ->
                mechanismController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1
        ).whileActiveContinuous(new IntakeCommand(intakeSubsystem, mechanismController));

        new Trigger(() ->
                mechanismController.getButton(GamepadKeys.Button.A)
        ).whileActiveContinuous(new IntakeCommand(intakeSubsystem, mechanismController));

        new Trigger(() ->
                mechanismController.getButton(GamepadKeys.Button.B)
        ).whileActiveContinuous(new ShooterServoCommand(shooterServoSubsystem));


        //----------------------------/Shooter/----------------------

        new Trigger(() ->
                mechanismController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1
        ).whileActiveContinuous(new ShooterCommand(shooterSubsystem));

        schedule(
                new ShooterCommandSensor(shooterSubsystem)
        );

    }

    //----------------------------/Telemetry/----------------------

    public void run() {
        driverController.readButtons();
        mechanismController.readButtons();

        telemetry.addData("Heading Lock", chasisSusbystem.isLockEnabled() ? "ACTIVADO" : "DESACTIVADO");
        telemetry.addData("Heading", configureIMU.getHeading(AngleUnit.DEGREES));
        telemetry.addData("RX", chasisSusbystem.getOutPID());
        telemetry.addData("Shooter RPM", shooterSubsystem.RPM());
        telemetry.addData("Shooter Encoder Velocity", shooterSubsystem.Power());
        telemetry.addData("Shooter Raw Power", shooterSubsystem.Raw());

        //----------------------------/Telemetry/----------------------

        //telemetry.addData("Colors ARGB", Arrays.toString(shooterSensor.getARGB()));
        if (huskySubsystem.getColor(1)) {
            telemetry.addLine("Color Detected");
        }
        else {
            telemetry.addLine("No color detected");
        }

        telemetry.update();

        super.run();
    }
}