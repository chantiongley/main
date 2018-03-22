package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String catalogueFilePath = "data/addressbook.xml";
    private String catalogueBookName = "MyAddressBook";

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public String getCatalogueFilePath() {
        return catalogueFilePath;
    }

    public void setCatalogueFilePath(String catalogueFilePath) {
        this.catalogueFilePath = catalogueFilePath;
    }

    public String getCatalogueBookName() {
        return catalogueBookName;
    }

    public void setCatalogueBookName(String catalogueBookName) {
        this.catalogueBookName = catalogueBookName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(catalogueFilePath, o.catalogueFilePath)
                && Objects.equals(catalogueBookName, o.catalogueBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, catalogueFilePath, catalogueBookName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + catalogueFilePath);
        sb.append("\nCatalogue name : " + catalogueBookName);
        return sb.toString();
    }

}
