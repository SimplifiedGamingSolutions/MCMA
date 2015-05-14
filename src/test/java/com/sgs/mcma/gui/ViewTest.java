package com.sgs.mcma.gui;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.junit.Test;

public class ViewTest
{

	@Test
	public void test()
	{
		Robot bot = null;
		try
		{
			bot = new Robot();
		} catch (AWTException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bot.mouseMove(10, 10);
		bot.mousePress(InputEvent.BUTTON1_MASK);
		// add time between press and release or the input event system may
		// not think it is a click
		try
		{
			Thread.sleep(250);
		} catch (InterruptedException e)
		{
		}
		bot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

}
