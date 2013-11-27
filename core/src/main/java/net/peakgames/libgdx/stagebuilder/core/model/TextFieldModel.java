package net.peakgames.libgdx.stagebuilder.core.model;

public class TextFieldModel extends ButtonModel {

	private String text;
	private String fontName;
	private String fontColor;
	private String cursorImageName;
	private String selectionImageName;
	private String backgroundImageName;
	private int backGroundOffset;
	private int cursorOffset;
	private int selectionOffset;
	private boolean password;
	private String passwordChar = "*";

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getCursorImageName() {
		return cursorImageName;
	}

	public void setCursorImageName(String cursorImageName) {
		this.cursorImageName = cursorImageName;
	}

	public String getSelectionImageName() {
		return selectionImageName;
	}

	public void setSelectionImageName(String selectionImageName) {
		this.selectionImageName = selectionImageName;
	}

	public String getBackgroundImageName() {
		return backgroundImageName;
	}

	public void setBackgroundImageName(String backgroundImageName) {
		this.backgroundImageName = backgroundImageName;
	}

	public int getBackGroundOffset() {
		return backGroundOffset;
	}

	public void setBackGroundOffset(int backGroundOffset) {
		this.backGroundOffset = backGroundOffset;
	}

	public int getCursorOffset() {
		return cursorOffset;
	}

	public void setCursorOffset(int cursorOffset) {
		this.cursorOffset = cursorOffset;
	}

	public int getSelectionOffset() {
		return selectionOffset;
	}

	public void setSelectionOffset(int selectionOffset) {
		this.selectionOffset = selectionOffset;
	}

	public boolean isPassword() {
		return password;
	}

	public void setPassword(boolean password) {
		this.password = password;
	}

	public String getPasswordChar() {
		return passwordChar;
	}

	public void setPasswordChar(String passwordChar) {
		this.passwordChar = passwordChar;
	}
	
}
