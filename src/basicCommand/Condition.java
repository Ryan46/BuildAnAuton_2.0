package basicCommand;

import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.SensorBase;
import visualrobot.VisualRobot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class Condition<T extends SensorBase> {
	
	private T sensor;
	GyroBase gyro;		
	double amount;
	int sign;
	String name;
	
	public static final int LESS_THAN = 0;
	public static final int GREATER_THAN = 1;

	public boolean check() {
		
		if(sensor != null) {
			if(GyroBase.class.isInstance(sensor)) {
				GyroBase gyro = (GyroBase) sensor;
				if(sign == 0) {
					return gyro.getAngle() < amount;
				}
				else if(sign == 1) {
					return gyro.getAngle() > amount;
				}
			}
			else if(Encoder.class.isInstance(sensor)) {
				Encoder e = (Encoder) sensor;
				if(sign == 0) {
					return e.getDistance() < amount;
				}
				else if(sign == 1) {
					return e.getDistance() > amount;
				}

			}
			else if(DigitalInput.class.isInstance(sensor)) {
				return ((DigitalInput) sensor).get() == (amount != 0);
			}
		
		}
		else {
			System.out.println(sensor.getClass().getName() + " not found");
		}
		return false;

	}

	public Condition(String name, int sign, double amount) {
		 this.sign = sign;
		 this.amount = amount;
		 this.name = name;
	}
	
	public void setRobot(VisualRobot r) {
		 Object o = r.getSensors().get(name);
		 if(o != null && SensorBase.class.isInstance(o)) 
			 sensor = (T) o;

	}

	public String getUnit() {
		if(Encoder.class.isInstance(sensor)) {
			return "Inches";
		}
		else if(GyroBase.class.isInstance(sensor)) {
			return "Degrees";
		}
		else if(DigitalInput.class.isInstance(sensor)) {
			return "off: 0, on: 1";
		}
		else {
			return "Idk";
		}
	}
	
}
