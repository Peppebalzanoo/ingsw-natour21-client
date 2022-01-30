package com.example.natour2.utilities;

public class POITypeMapper {

    private String type;
    private String typeName;
    private String typeIcon;

    public POITypeMapper(){

    }

    public POITypeMapper(String type, String typeName, String typeIcon) {
        this.type = type;
        this.typeName = typeName;
        this.typeIcon = typeIcon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(String typeIcon) {
        this.typeIcon = typeIcon;
    }

    @Override
    public String toString() {
        return typeName;
    }
}
