package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.gamepad.OI;
import frc.robot.subsystems.DriveTrain;

public class Teleop extends CommandBase
{
    /**
     * Bring in Subsystem and Gamepad instances
     */
    private static final DriveTrain driveTrain = RobotContainer.driveTrain;
    private static final OI oi = RobotContainer.oi;

    /**
     * Joystick inputs
     */
    double inputLeftY = 0;
    double inputLeftX = 0;
    double inputRightX = 0;
    double inputRightY = 0;

    /**
     * Ramp Constants
     */
    double deltaLeftY = 0;
    double deltaLeftX = 0;
    double deltaRightX = 0;
    double deltaRightY = 0;
    double prevLeftY = 0;
    double prevLeftX = 0;
    double prevRightX = 0;
    double prevRightY = 0;

    /**
     * Ramp up Constant
     */
    private static final double RAMP_UP = 0.05;

    /**
     * Ramp down Constant
     */
    private static final double RAMP_DOWN = 0.05;

    /**
     * Delta Limit
     */
    private static final double DELTA_LIMIT = 0.075;

    public Teleop()
    {
        addRequirements(driveTrain);
    }

    /**
     * Code here will run once when the command is called for the first time
     */
    @Override
    public void initialize() 
    {
        driveTrain.resetYaw();
    }

    /**
     * Code here will run continously every robot loop until the command is stopped
     */
    @Override
    public void execute() 
    {
        /**
         * Get Joystick data
         */
        inputLeftX = -oi.getLeftDriveX();
        inputLeftY = oi.getLeftDriveY();
        inputRightX = -oi.getRightDriveX();
        inputRightY = -oi.getRightDriveY();

        /**
         * какието расчеты координат геймпада
         */
        deltaLeftX = inputLeftX - prevLeftX;
        deltaLeftY = inputLeftY - prevLeftY;
        deltaRightX = inputRightX - prevRightX;
        deltaRightY = inputRightY - prevRightY;
        if (deltaLeftX >= DELTA_LIMIT)
            inputLeftX += RAMP_UP;
        else if (deltaLeftX <= -DELTA_LIMIT)
            inputLeftX -= RAMP_DOWN;
        if (deltaLeftY >= DELTA_LIMIT)
            inputLeftY += RAMP_UP;
        else if (deltaLeftY <= -DELTA_LIMIT)
            inputLeftY -= RAMP_DOWN;
        if (deltaRightX >= DELTA_LIMIT)
            inputRightX += RAMP_UP;
        else if (deltaRightX <= -DELTA_LIMIT)
            inputRightX -= RAMP_DOWN;
        if (deltaRightY >= DELTA_LIMIT)
            inputRightY += RAMP_UP;
        else if (deltaRightY <= -DELTA_LIMIT)
            inputRightY -= RAMP_DOWN;
        prevLeftY = inputLeftY;
        prevLeftX = inputLeftX;
        prevRightX = inputRightX;
        prevRightY = inputRightY;

        /*
         * лампочки на кнопки А(X на Dualshok) и В(O на Dualshok)
         */
        if(oi.getDriveAButton() == true)
        {
            driveTrain.LedIntencive_18(1.0);
        }
        else
        {
            driveTrain.LedIntencive_18(0.0);
        }

        if(oi.getDriveBButton() == true)
        {
            driveTrain.LedIntencive_19(1.0);
        }
        else
        {
            driveTrain.LedIntencive_19(0.0);
        }

        /*
         * по идеи движение двух сервоприводов на кнопки X() и Y()
         */
        if(oi.getDriveXButton() == true)
        {
            driveTrain.Servo1(1.0);
        }
        else
        {
            driveTrain.Servo1(0.0);
        }

        if(oi.getDriveYButton() == true)
        {
            driveTrain.Servo2(1.0);
        }
        else
        {
            driveTrain.Servo2(0.0);
        }


        // Send user input to the motors
        driveTrain.drive(inputLeftY, inputRightY);
    }

    /**
     * When the comamnd is stopped or interrupted this code is run
     * <p>
     * Good place to stop motors in case of an error
     */
    @Override
    public void end(boolean interrupted)
    {
        driveTrain.drive(0.0, 0.0);
        driveTrain.LedIntencive_18(0.0);
        driveTrain.LedIntencive_19(0.0);
        driveTrain.Servo1(0.0);
        driveTrain.Servo2(0.0);
    }

    /**
     * Check to see if command is finished
     */
    @Override
    public boolean isFinished()
    {
        return false;
    }

}