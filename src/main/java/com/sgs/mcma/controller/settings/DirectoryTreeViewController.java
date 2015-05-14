package com.sgs.mcma.controller.settings;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.tree.DefaultMutableTreeNode;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.sgs.mcma.view.settings.ServerSettingsTab;

public class DirectoryTreeViewController 
{
	public static void selectedNodeChanged(String filePath, RSyntaxTextArea textArea)
	{
        if(textArea != null && !new File(filePath).isDirectory())
        {
        	String text = "";
			try 
			{
				text = new String(Files.readAllBytes(Paths.get(filePath)), Charset.defaultCharset());
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
        	textArea.setText(text);
        }
    }
}
