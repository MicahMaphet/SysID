package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class Contants {
    public interface SysIdSubsystem {
        public Command quasistatic(SysIdRoutine.Direction direction);
        public Command dynamic(SysIdRoutine.Direction direction);
    }
}