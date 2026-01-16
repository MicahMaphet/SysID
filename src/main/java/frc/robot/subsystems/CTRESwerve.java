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

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.Constants.SysIdSubsystem;

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

    /** SysID routine to compute drive motor constants */
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

    public class Translation implements SysIdSubsystem {
        @Override
        public Command dynamic(SysIdRoutine.Direction direction) {
            return sysIdRoutineTranslation.dynamic(direction);
        }

        @Override
        public Command quasistatic(SysIdRoutine.Direction direction) {
            return sysIdRoutineTranslation.quasistatic(direction);
        }
    }

    public final Translation translation = new Translation();

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

    public class Steer implements SysIdSubsystem {
        @Override
        public Command dynamic(SysIdRoutine.Direction direction) {
            return sysIdRoutineSteer.dynamic(direction);
        }

        @Override
        public Command quasistatic(SysIdRoutine.Direction direction) {
            return sysIdRoutineSteer.quasistatic(direction);
        }
    }

    public final Steer steer = new Steer();

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

    public class Rotation implements SysIdSubsystem {
        @Override
        public Command dynamic(SysIdRoutine.Direction direction) {
            return sysIdRoutineRotation.dynamic(direction);
        }

        @Override
        public Command quasistatic(SysIdRoutine.Direction direction) {
            return sysIdRoutineRotation.quasistatic(direction);
        }
    }

    public final Rotation rotation = new Rotation();
}
