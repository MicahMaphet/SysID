package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator2025 extends SubsystemBase {
    private static final int THROUGHBORE_TICKS_PER_REVOLUTION = 8192;
    
    private final SparkMax leader = new SparkMax(0, MotorType.kBrushless);
    private final SparkMax follower = new SparkMax(0, MotorType.kBrushless);

    private RelativeEncoder encoder = leader.getAlternateEncoder();

    public Elevator2025() {
        encoder.setPosition(0);

        SparkMaxConfig leaderConfig = new SparkMaxConfig();
        SparkMaxConfig followerConfig = new SparkMaxConfig();
    }
}
