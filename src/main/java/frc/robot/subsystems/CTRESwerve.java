package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class CTRESwerve extends SwerveDrivetrain<TalonFX, TalonFX, CANcoder> implements Subsystem {
    public CTRESwerve(
        SwerveDrivetrainConstants drivetrainConstants,
        SwerveModuleConstants<?, ?, ?>... modules
    ) {
        super(
            TalonFX::new, TalonFX::new, CANcoder::new,
            drivetrainConstants, modules
        );
    }

    private final SwerveRequest.SysIdSwerveTranslation translationCharactarization = new SwerveRequest.SysIdSwerveTranslation();

    private final SysIdRoutine sysIdRoutineTranslation = new SysIdRoutine(
        new SysIdRoutine.Config(
            null,
            Volts.of(4),
            null,
            state -> SignalLogger.writeString("SysIdTranslation_State", state.toString())
        ),
        new SysIdRoutine.Mechanism(
            output -> setControl(translationCharactarization.withVolts(output)),
            null,
            this
        )
    );

    public class Translation {
        public void dynamic(SysIdRoutine.Direction direction) {
            sysIdRoutineTranslation.dynamic(direction);
        }
        public void quasistatic(SysIdRoutine.Direction direction) {
            sysIdRoutineTranslation.quasistatic(direction);
        }
    }

    private final SwerveRequest.SysIdSwerveSteerGains steerCharactarization = new SwerveRequest.SysIdSwerveSteerGains();

    private final SysIdRoutine sysIdRoutineSteer = new SysIdRoutine(
        new SysIdRoutine.Config(
            null,
            Volts.of(7),
            null,
            state -> SignalLogger.writeString("SysIdSteer_State", state.toString())
        ),
        new SysIdRoutine.Mechanism(
            output -> setControl(steerCharactarization.withVolts(output)),
            null,
            this
        )
    );

    public class Steer {
        public void dynamic(SysIdRoutine.Direction direction) {
            sysIdRoutineSteer.dynamic(direction);
        }
        public void quasistatic(SysIdRoutine.Direction direction) {
            sysIdRoutineSteer.quasistatic(direction);
        }
    }

    private final SwerveRequest.SysIdSwerveRotation rotationCharactarization = new SwerveRequest.SysIdSwerveRotation();

    private final SysIdRoutine sysIdRoutineRotation = new SysIdRoutine(
        new SysIdRoutine.Config(
            Volts.of(Math.PI / 6).per(Second),
            Volts.of(Math.PI),
            null,
            state -> SignalLogger.writeString("SysIdRotation_State", state.toString())
        ),
        new SysIdRoutine.Mechanism(
            output -> setControl(rotationCharactarization.withRotationalRate(output.in(Volts))),
            null,
            this
        )
    );

    public class Rotation {
        public void dynamic(SysIdRoutine.Direction direction) {
            sysIdRoutineRotation.dynamic(direction);
        }
        public void quasistatic(SysIdRoutine.Direction direction) {
            sysIdRoutineRotation.quasistatic(direction);
        }
    }
}
