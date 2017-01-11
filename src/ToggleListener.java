import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JButton;

public class ToggleListener implements KeyListener{
	private HashMap<Integer, Boolean> keysBound;
	public BuildAnAuton2 frame;
	
	public ToggleListener(BuildAnAuton2 b, HashMap<Integer, Boolean> keys) {
		frame = b;
		keysBound = keys;
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		keysBound.replace(e.getKeyCode(), true);
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			frame.tool = BuildAnAuton2.SelectedTool.NONE;
			
			for(JButton b: frame.tools) {
				b.setEnabled(true);
			}
			frame.p.repaint();
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_MINUS) {
			frame.zoom(-.05);
		}
		else if(e.getKeyCode() == KeyEvent.VK_EQUALS) {
			frame.zoom(+.05);
		}
		
		if((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0 || (e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			frame.save.doClick();
		}
		
		if((e.getKeyCode() == KeyEvent.VK_O) && ((e.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0 || (e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			frame.load.doClick();
		}
	}

	public void keyReleased(KeyEvent e) {
		keysBound.replace(e.getKeyCode(), false);
	}
	
	
	
}
