package test;

import org.dresign.command.Command;

@Command
public class CommandUnderTest {
private String contenu;
public String getContenu() {
	return contenu;
}

public void setContenu(String contenu) {
	this.contenu = contenu;
}
}
