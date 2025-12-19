package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.controls.VoltageOut;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class Arm extends SubsystemBase {
    private final SparkMax motor = new SparkMax(6, MotorType.kBrushless);
    private final RelativeEncoder encoder = motor.getEncoder();
    
    public Arm() {
        SparkMaxConfig config = new SparkMaxConfig();
        config
            .smartCurrentLimit(20);

        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    private final SysIdRoutine routine = new SysIdRoutine(
        new SysIdRoutine.Config(
        null,
            Volts.of(4),
            null,
            state -> SignalLogger.writeString("SysId Lineaer State", state.toString())
            ),
        new SysIdRoutine.Mechanism(
            output -> motor.setVoltage(output),
            null,
            this
        )
    );

    public Command sysidQuasistatic(SysIdRoutine.Direction direction) {
        return routine.quasistatic(direction);
    }

    public Command sysidDynamic(SysIdRoutine.Direction direction) {
        return routine.dynamic(direction);
    }
}
