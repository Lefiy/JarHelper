package lefiydev.frame;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;

import lefiydev.utils.Path;

public class Screen extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTextArea textArea;
	
	public Screen() {
		
		this.setup();
		
	}
	
	private void setup() {
		
		setTitle("Jar Helper (Made by Lefiy)");
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea("ファイルをドロップしてください。");
		scrollPane.setViewportView(textArea);
		
		textArea.setTransferHandler(new DropableTransfer());
	}
	
	private void run(File file) {
		
		ArrayList<String> args = new ArrayList<>();
		
		args.add(Path.getJavaSystem());
		
		args.add("-jar");
		
		args.add(file.getAbsolutePath());
		
		ProcessBuilder builder =  new ProcessBuilder(args);
		
		builder.redirectErrorStream();
		
		try {
				
			Process process = builder.start();
			
			process.waitFor();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class DropableTransfer extends TransferHandler {
		
		private static final long serialVersionUID = 1L;

		@Override
		public boolean canImport(TransferSupport support) {
			if (!support.isDrop()) {
		        return false;
		    }

			if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
		        return false;
		    }

			return true;
		}

		@Override
		@SuppressWarnings("unchecked")
		
		public boolean importData(TransferSupport support) {
			
			if (!canImport(support)) {
				JOptionPane.showMessageDialog(null, "正常なファイルではありません。", "読み込みエラー", JOptionPane.ERROR_MESSAGE);
		        return false;
		    }

			Transferable t = support.getTransferable();
			
			try {
				
				List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
				
				for(File file : files) {
					String name = file.getName();
					System.out.println(name);
					if(!name.substring(name.lastIndexOf("."), name.length()).equals(".jar")) {
						JOptionPane.showMessageDialog(null, "Jarファイルでないものが含まれています。", "読み込みエラー", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}

				StringBuffer fileList = new StringBuffer();
				
				for (File file : files) {
					fileList.append(file.getName() + " ファイルを読み込みに成功しました。");
					fileList.append("\n");
					run(file);
				}
				
				textArea.setText(fileList.toString());
				
			} catch (UnsupportedFlavorException | IOException e) {
				e.printStackTrace();
			}
			
			return true;
		}
	}
}
