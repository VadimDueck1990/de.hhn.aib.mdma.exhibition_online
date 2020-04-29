package com.hhn.vs.centerapp.splanservice.model;


/**
 * id	910
 * version	3
 * name	"Automatisierungstechnik und Elektromaschinenbau"
 * shortName	"AE"
 * tagIdsDenormalized	""
 * systemNotes	""
 * archived	false
 * parentOrgGroupId	61
 * importId	"AEB"
 * visibleOnWeb	true
 * visibleOnlyForLecturerSel	false
 * locationId	null
 * tagIds	[]
 */
public class Course {
    private long id;
    private long version;
    private String name;
    private String shortName;
    private String tagIdsDenormalized;
    private String systemNotes;
    private boolean archived;
    private long parentOrgGroupId;
    private String importId;
    private boolean visibleOnWeb;
    private boolean visibleOnlyForLecturerSel;
    private String locationId;
    private String tagIds;

    public Course() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public void setParentOrgGroupId(long parentOrgGroupId) {
        this.parentOrgGroupId = parentOrgGroupId;
    }

    public boolean isVisibleOnWeb() {
        return visibleOnWeb;
    }

    public void setVisibleOnWeb(boolean visibleOnWeb) {
        this.visibleOnWeb = visibleOnWeb;
    }

    public boolean isVisibleOnlyForLecturerSel() {
        return visibleOnlyForLecturerSel;
    }

    public void setVisibleOnlyForLecturerSel(boolean visibleOnlyForLecturerSel) {
        this.visibleOnlyForLecturerSel = visibleOnlyForLecturerSel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTagIdsDenormalized() {
        return tagIdsDenormalized;
    }

    public void setTagIdsDenormalized(String tagIdsDenormalized) {
        this.tagIdsDenormalized = tagIdsDenormalized;
    }

    public String getSystemNotes() {
        return systemNotes;
    }

    public void setSystemNotes(String systemNotes) {
        this.systemNotes = systemNotes;
    }

    public String getImportId() {
        return importId;
    }

    public void setImportId(String importId) {
        this.importId = importId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }
}
