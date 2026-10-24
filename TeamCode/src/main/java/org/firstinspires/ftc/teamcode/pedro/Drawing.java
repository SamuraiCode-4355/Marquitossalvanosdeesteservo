package org.firstinspires.ftc.teamcode.pedro;

import com.bylazar.field.FieldManager;
import com.bylazar.field.PanelsField;
import com.bylazar.field.Style;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ConfigureIMU;

public class Drawing {
    public static final double ROBOT_RADIUS = 9;
    private static final FieldManager panelsField = PanelsField.INSTANCE.getField();
    private static final Style robotLook = new Style("", "#3F51B5", 0.75);

    public static void init() {
        panelsField.setOffsets(PanelsField.INSTANCE.getPresets().getPEDRO_PATHING());
    }

    public static final double ROBOT_WIDTH = 18;
    public static final double ROBOT_LENGTH = 17.5;

    public static void drawRobot(Pose pose) {
        if (pose == null) return;

        double cos = Math.cos(pose.heading());
        double sin = Math.sin(pose.heading());

        double hl = ROBOT_LENGTH / 2;  // mitad en la dirección en la que mira
        double hw = ROBOT_WIDTH / 2;   // mitad a los lados

        // Esquinas relativas al centro: {adelante, izquierda}
        double[][] local = {
                { hl,  hw},   // frente izquierda
                { hl, -hw},   // frente derecha
                {-hl, -hw},   // atrás derecha
                {-hl,  hw}    // atrás izquierda
        };

        double[] xs = new double[4];
        double[] ys = new double[4];
        for (int i = 0; i < 4; i++) {
            double f = local[i][0];
            double l = local[i][1];
            xs[i] = pose.x() + f * cos - l * sin;
            ys[i] = pose.y() + f * sin + l * cos;
        }

        panelsField.setStyle(robotLook);

        // Contorno del robot
        for (int i = 0; i < 4; i++) {
            int j = (i + 1) % 4;
            panelsField.moveCursor(xs[i], ys[i]);
            panelsField.line(xs[j], ys[j]);
        }

        // Línea de heading desde el centro hasta el frente
        panelsField.moveCursor(pose.x(), pose.y());
        panelsField.line(pose.x() + cos * hl, pose.y() + sin * hl);
    }

    public static void sendPacket() {
        panelsField.update();
    }
}