package dennys.iBluPrint.gui;

import dennys.iBluPrint.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import scala.Console;

public class GuiNumberField extends GuiButton {

	private GuiTextField textField;
	public GuiButton decreaseBtn;
	public GuiButton increaseBtn;
	private boolean wasFocused;
	private String previous = "0";

	public GuiNumberField(FontRenderer fontRenderer, int id, int x, int y, int width, int height) {
		super(id, 0, 0, width, height, "");
		this.textField = new GuiTextField(0, fontRenderer, x + 1, y + 1, width - 12 * 2 - 2, height - 2);
		this.decreaseBtn = new GuiButton(id++, x + width - 12 * 2, y, 12, height, "-");
		this.increaseBtn = new GuiButton(id++, x + width - 12 * 1, y, 12, height, "+");
		this.textField.setText(String.valueOf(0));
	}

	@Override
	public boolean mousePressed(Minecraft minecraft, int x, int y) {
		if (this.wasFocused == true && this.textField.isFocused() == false) {
			this.wasFocused = false;
			return true;
		}
		this.wasFocused = this.textField.isFocused();
		if (this.decreaseBtn.mousePressed(minecraft,  x,  y) == true || this.increaseBtn.mousePressed(minecraft,  x,  y) == true) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void drawButton(Minecraft minecraft, int x, int y, float ticks) {
		if (this.visible) {
			this.textField.drawTextBox();
			this.increaseBtn.drawButton(minecraft,  x,  y,  ticks);
			this.decreaseBtn.drawButton(minecraft,  x,  y,  ticks);
		}
	}

	public void mouseClicked(int x, int y, int action) {
		this.textField.mouseClicked(x,  y,  action);
		if (this.increaseBtn.mousePressed(Minecraft.getMinecraft(),  x,  y) == true) {
			this.textField.setText(String.valueOf(Integer.parseInt(this.textField.getText()) + 1));
		}
		if (this.decreaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true) {
			this.textField.setText(String.valueOf(Integer.parseInt(this.textField.getText()) - 1));
		}
		if (this.decreaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.decreaseBtn.id == 1) {
			ClientEvents.updatePosA(-1, 0, 0);
		}
		if (this.increaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.increaseBtn.id == 2) {
			ClientEvents.updatePosA(1, 0, 0);
		}
		if (this.decreaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.decreaseBtn.id == 3) {
			ClientEvents.updatePosA(0, -1, 0);
		}
		if (this.increaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.increaseBtn.id == 4) {
			ClientEvents.updatePosA(0, 1, 0);
		}
		if (this.decreaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.decreaseBtn.id == 5) {
			ClientEvents.updatePosA(0, 0, -1);
		}
		if (this.increaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.increaseBtn.id == 6) {
			ClientEvents.updatePosA(0, 0, 1);
		}
		if (this.decreaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.decreaseBtn.id == 8) {
			ClientEvents.updatePosB(-1, 0, 0);
		}
		if (this.increaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.increaseBtn.id == 9) {
			ClientEvents.updatePosB(1, 0, 0);
		}
		if (this.decreaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.decreaseBtn.id == 10) {
			ClientEvents.updatePosB(0, -1, 0);
		}
		if (this.increaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.increaseBtn.id == 11) {
			ClientEvents.updatePosB(0, 1, 0);
		}
		if (this.decreaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.decreaseBtn.id == 12) {
			ClientEvents.updatePosB(0, 0, -1);
		}
		if (this.increaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.increaseBtn.id == 13) {
			ClientEvents.updatePosB(0, 0, 1);
		}
		if (this.decreaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.decreaseBtn.id == 20) {
			ClientEvents.loadBlueprint(Load.fileName, true, -1, 0, 0);
		}
		if (this.increaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.increaseBtn.id == 21) {
			ClientEvents.loadBlueprint(Load.fileName, true, 1, 0, 0);
		}
		if (this.decreaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.decreaseBtn.id == 22) {
			ClientEvents.loadBlueprint(Load.fileName, true, 0, -1, 0);
		}
		if (this.increaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.increaseBtn.id == 23) {
			ClientEvents.loadBlueprint(Load.fileName, true, 0, 1, 0);
		}
		if (this.decreaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.decreaseBtn.id == 24) {
			ClientEvents.loadBlueprint(Load.fileName, true, 0, 0, -1);
		}
		if (this.increaseBtn.mousePressed(Minecraft.getMinecraft(), x, y) == true && this.increaseBtn.id == 25) {
			ClientEvents.loadBlueprint(Load.fileName, true, 0, 0, 1);
		}
	}
	
    public boolean keyTyped(final char character, final int code) {
        if (!this.textField.isFocused()) {
            return false;
        }

        int cursorPositionOld = this.textField.getCursorPosition();

        this.textField.textboxKeyTyped(character, code);

        final int cursorPositionNew = this.textField.getCursorPosition();

        if (this.textField.getText().length() == 0 || this.textField.getText().equals("-")) {
            return true;
        }

        try {
            long value = Long.parseLong(this.textField.getText());

            String text = String.valueOf(value);

            if (!text.equals(this.previous)) {
                this.textField.setText(String.valueOf(value));
                this.textField.setCursorPosition(cursorPositionNew);
            }

            this.previous = text;

            return true;
        } 
        catch (final NumberFormatException nfe) {
            this.textField.setText(this.previous);
            this.textField.setCursorPosition(cursorPositionOld);
        }

        return false;

    }
	
	public void updateCursorCounter() {
		this.textField.updateCursorCounter();
	}
	
	public boolean isFocused() {
		return this.textField.isFocused();
	}
	
    public void setPosition(int x, int y) {
        this.textField.x = x + 1;
        this.textField.y = y + 1;
        this.increaseBtn.x = x + width - 12 * 2;
        this.increaseBtn.y = y;
        this.decreaseBtn.x = x + width - 12 * 1;
        this.decreaseBtn.y = y;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.textField.setEnabled(enabled);
        this.increaseBtn.enabled = enabled;
        this.decreaseBtn.enabled = enabled;
    }
    
    public void setValue(int value) {
    	this.textField.setText(String.valueOf(value));
    }
    
}
