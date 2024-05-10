package org.uneev;

import org.uneev.utils.Definer;

public class App {
    public static void main( String[] args ) {
        Definer definer = Definer.TOPMOST_STAKEHOLDER_DEFINER;
        definer.writeTopmostStakeholdersToFile();
    }
}
