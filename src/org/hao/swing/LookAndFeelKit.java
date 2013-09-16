package org.hao.swing;

import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LookAndFeelKit {

	public static void setLookAndFeel(LookAndFeel laf)
			throws UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel(laf);
		boolean supportsDecorations = laf.getSupportsWindowDecorations();
		JFrame.setDefaultLookAndFeelDecorated(supportsDecorations);
		JDialog.setDefaultLookAndFeelDecorated(supportsDecorations);
		Window[] windows = Window.getWindows();
		for (int i = 0; i < windows.length; ++i) {
			boolean isVisible = windows[i].isVisible();
			if (isVisible)
				windows[i].dispose();
			if (windows[i] instanceof JFrame) {
				JFrame frame = (JFrame) windows[i];
				frame.setUndecorated(supportsDecorations);
				frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
				frame.getRootPane().setWindowDecorationStyle(
						JRootPane.PLAIN_DIALOG);
			} else if (windows[i] instanceof JDialog) {
				JDialog dialog = (JDialog) windows[i];
				dialog.setUndecorated(supportsDecorations);
				dialog.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
				dialog.getRootPane().setWindowDecorationStyle(
						JRootPane.PLAIN_DIALOG);
			}
			windows[i].update(windows[i].getGraphics());
			if (isVisible)
				windows[i].setVisible(true);
			SwingUtilities.updateComponentTreeUI(windows[i]);
		}
	}

	public static void setLookAndFeel(String lookAndFeelClass)
			throws UnsupportedLookAndFeelException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		setLookAndFeel((LookAndFeel) Class.forName(lookAndFeelClass)
				.newInstance());
	}
}
