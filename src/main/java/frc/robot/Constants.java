package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public class Constants {
    public static final CommandGenericHID ps4Controller = new CommandPS4Controller(0);
    public static final CommandGenericHID xboxController = new CommandXboxController(0);
    
    public static class SparkMaxContants {
        public static final int motorId = 6;
        public static final boolean brushed = false;
    }
    
    public static class TalonFXConstants {
        public static final int motorId = 3;
    }

    public interface SysIdSubsystem {
        public Command quasistatic(SysIdRoutine.Direction direction);
        public Command dynamic(SysIdRoutine.Direction direction);
    }
}