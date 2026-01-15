package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.SignalLogger;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Contants.SysIdSubsystem;

public class SparkMaxSysId extends SubsystemBase implements SysIdSubsystem {
    private SparkMax motor;

    public SparkMaxSysId(int motorId, boolean brushed, SparkMaxConfig config) {
        motor = new SparkMax(motorId, brushed ? MotorType.kBrushed : MotorType.kBrushless);
        if (config != null)
            motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public SparkMaxSysId(int motorId, boolean brushed) {
        this(motorId, brushed, null);
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

    @Override
    public Command quasistatic(SysIdRoutine.Direction direction) {
        return routine.quasistatic(direction);
    }

    @Override
    public Command dynamic(SysIdRoutine.Direction direction) {
        return routine.dynamic(direction);
    }
}
