package visualrobot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/*
 * This class reads the auton file. The Thread stuff is a planned feature, and doesn't do anything.
 */
public class ChooseAuton {
	CommandSet cmdSet = null;
	ArrayList<ArrayList<Command>> threads;
	int[] threadStarts = null;
	ArrayList<Command> main = new ArrayList<Command>();
	VisualRobot robot;
	
	public ChooseAuton(VisualRobot r) {
		robot = r;
	}
	
	public void chooseAuton(String fName) {
		File file = new File("/home/lvuser/" + fName + ".autr"); //Select file

		try {
		
			ObjectInputStream ois;
			ois = new ObjectInputStream(new FileInputStream(file));
			
			cmdSet = (CommandSet) ois.readObject();
			
			int numThreads = cmdSet.getSize();
			threadStarts = cmdSet.getThreadStarts();
			threads = cmdSet.getCommands();
			
			
			for(ArrayList<Command> a: threads) {
				for (Command c: a)
					c.setRobot(robot);
			}
			main = threads.get(0);
			
			ois.close();
		}
		catch(IOException e){e.printStackTrace();} 
		catch (ClassNotFoundException e) {e.printStackTrace();}

				
		int i = 0;
		while(robot.isAutonomous() && !robot.isDisabled() && i <= main.size()) {
			try {
				for(int start = 1; start < threadStarts.length; start++)
					if (threadStarts[start] == i)
						(new Thread(new RunCommands(threads.get(start)))).start();
				if(i != main.size())
					main.get(i).execute();
			}
			catch(NullPointerException e) {SmartDashboard.putString("DB/String 5", e.getLocalizedMessage());}
			finally {
				i++;
			}
		}	

	}
	
}
