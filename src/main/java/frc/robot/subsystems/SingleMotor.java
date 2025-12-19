package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class SingleMotor extends SubsystemBase {
  private final TalonFX motor = new TalonFX(0);

  private final TalonFXConfiguration motorConfig = new TalonFXConfiguration();

  private VoltageOut voltageControlRequeset = new VoltageOut(0);

  public SingleMotor() {
    motor.getConfigurator().apply(motorConfig);
  }

  private final SysIdRoutine routine = new SysIdRoutine(
    new SysIdRoutine.Config(
      null,
      Volts.of(4),
      null,
      state -> SignalLogger.writeString("SysId Lineaer State", state.toString())
    ),
    new SysIdRoutine.Mechanism(
      output -> motor.setControl(voltageControlRequeset.withOutput(output)),
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